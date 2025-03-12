package edu.bbte.idde.krim2244.dataaccess.jdbc;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarDaoJdbcImpl extends AbstractJdbcDao<Car> implements CarDao {

    private static final Logger LOG = LoggerFactory.getLogger(CarDaoJdbcImpl.class);

    private Car getCar(ResultSet resultSet) throws SQLException {
        String dateString = resultSet.getString("uploadDate");

        Car car = new Car(
                resultSet.getString("name"),
                resultSet.getString("brand"),
                resultSet.getInt("year"),
                resultSet.getDouble("price"),
                LocalDate.parse(dateString)
        );
        car.setId(resultSet.getLong("id"));
        return car;
    }

    @Override
    public List<Car> findByBrand(String brand) {
        List<Car> cars = new ArrayList<>();

        LOG.info("Finding car by brand: {}", brand);

        try (Connection conn = connectionPool.getConnection();

             PreparedStatement statement = conn.prepareStatement("SELECT * FROM car WHERE brand = ?")) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();

            // alakitsunk ki egy listat
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoOperationFailedException("Error retrieving cars by brand", e);
        }

        return cars;
    }

    @Override
    public List<Car> findByYear(int year) {
        List<Car> cars = new ArrayList<>();
        LOG.info("Finding car by year: {}", year);

        try (Connection conn = connectionPool.getConnection();

             PreparedStatement statement = conn.prepareStatement("SELECT * FROM car WHERE year = ?")) {
            statement.setInt(1, year);
            ResultSet resultSet = statement.executeQuery();

            // alakitsunk ki egy listat
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoOperationFailedException("Error retrieving cars by year", e);
        }

        return cars;
    }

    @Override
    public void create(Car car) {
        LOG.info("Create new car");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO car (name, brand, year, price, uploadDate) "
                             + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getName());
            statement.setString(2, car.getBrand());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getUploadDate().toString());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoOperationFailedException("Could not create car", e);
        }
    }

    @Override
    public void update(Car car) {
        LOG.info("Update car");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "UPDATE car SET name = ?, brand = ?, year = ?, price = ?, uploadDate = ? WHERE id = ?",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getName());
            statement.setString(2, car.getBrand());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getUploadDate().toString());
            statement.setLong(6, car.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoOperationFailedException("Could not update car", e);
        }
    }

    @Override
    public void delete(Car car) {
        LOG.info("Delete car");

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM car WHERE id = ?")) {

            statement.setLong(1, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoNotFoundException("Could not delete car", e);
        }
    }

    @Override
    public List<Car> findAll() {
        LOG.info("Finding all cars");
        List<Car> cars = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection();

             PreparedStatement statement = conn.prepareStatement("SELECT * FROM car")) {
            ResultSet resultSet = statement.executeQuery();

            // alakitsunk ki egy listat
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoOperationFailedException("Could not list all car", e);
        }

        return cars;
    }

    @Override
    public Car findById(Long id) {
        LOG.info("Finding car by id: {}", id);
        try (Connection conn = connectionPool.getConnection();

             PreparedStatement statement = conn.prepareStatement("SELECT * FROM car WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return getCar(resultSet);
            } else {
                throw new RepoNotFoundException("Car with id " + id + " could not be found");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RepoOperationFailedException("Could not be found car by id", e);
        }
    }
}