package edu.bbte.idde.krim2244.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;
import edu.bbte.idde.krim2244.service.CarService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import edu.bbte.idde.krim2244.dataaccess.AbstractDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application {

    private final CarService carService;
    private final JTextField nameField;
    private final JTextField brandField;
    private final JTextField yearField;
    private final JTextField priceField;
    private final JTextField searchField;
    private final DefaultTableModel tableModel;
    private final JTable carTable;

    private final JFrame frame;
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public Application() {
        super();
        frame = new JFrame();
        CarDao carDao = AbstractDaoFactory.getInstance().getCarDao();
        carService = new CarService(carDao);

        frame.setTitle("CarApplication");
        frame.setSize(1000, 400);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // create car
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        inputPanel.add(brandField);

        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton addButton = new JButton("ADD");
        inputPanel.add(addButton);
        frame.add(inputPanel, BorderLayout.NORTH);

        // car list
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Brand", "Year", "Price"}, 0);
        carTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(carTable);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // text input, fitlering, delete
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchField = new JTextField(15);
        searchPanel.add(searchField);

        JButton searchByModelButton = new JButton("Filter by Year");
        JButton searchByBrandButton = new JButton("Filter by Brand");
        JButton listAllButton = new JButton("LIST");
        JButton updateButton = new JButton("UPDATE");
        JButton deleteButton = new JButton("DELETE");

        searchPanel.add(searchByModelButton);
        searchPanel.add(searchByBrandButton);
        searchPanel.add(listAllButton);
        searchPanel.add(updateButton);
        searchPanel.add(deleteButton);

        frame.add(searchPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addCar();
                } catch (RepoNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        listAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAllCars();
            }
        });

        searchByModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCarByYear();
            }
        });

        searchByBrandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCarByBrand();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCar();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteCar();
                } catch (RepoNotFoundException ex) {
                    LOG.error(ex.getMessage());
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
    }

    private void addCar() throws RepoNotFoundException {

        LOG.info("Creating new car");

        // check inputs
        if (!validateInputs()) {
            return;
        }

        LocalDate date = LocalDate.now();

        int year = Integer.parseInt(yearField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());

        Car car = new Car(nameField.getText(), brandField.getText(), year, price, date);
        try {
            carService.addCar(car);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }

        clearInputFields();
        listAllCars();
    }

    private boolean validateInputs() {
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name field cannot be empty.");
            return false;
        }

        if (brandField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Brand field cannot be empty.");
            return false;
        }

        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 0 || year > LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(frame, "Year must be between 0 and \" + LocalDate.now().getYear().");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Year cannot be negative.");
            return false;
        }

        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(frame, "Price cannot be negative.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
            return false;
        }

        return true;
    }

    private void listAllCars() {
        LOG.info("Listing all cars");

        clearTable();
        List<Car> cars = carService.listAllCars();
        for (Car car : cars) {
            tableModel.addRow(new Object[]{car.getId(), car.getName(), car.getBrand(), car.getYear(), car.getPrice()});
        }
        clearSearchInputField();
    }

    private void searchCarByYear() {
        LOG.info("Searching by year");

        if (searchField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Search field cannot be empty.");
            return;
        }

        clearTable();
        int year = Integer.parseInt(searchField.getText());
        List<Car> cars = carService.findByYear(year);
        for (Car car : cars) {
            tableModel.addRow(new Object[]{car.getId(), car.getName(), car.getBrand(), car.getYear(), car.getPrice()});
        }
    }

    private void searchCarByBrand() {
        LOG.info("Searching by brand");

        if (searchField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Search field cannot be empty.");
            return;
        }

        clearTable();
        String brand = searchField.getText();
        List<Car> cars = carService.findByBrand(brand);
        for (Car car : cars) {
            tableModel.addRow(new Object[]{car.getId(), car.getName(), car.getBrand(), car.getYear(), car.getPrice()});
        }
    }

    private void updateCar() {
        LOG.info("Updating car");
        int selectedRow = carTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a car(row).");
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        Car originalCar;
        try {
            originalCar = carService.findCarById(id);
        } catch (ServiceException e) {
            LOG.error("Could not find car by id", e);
            JOptionPane.showMessageDialog(frame, "Could not find car by id");
            return;
        }

        if (!updateCarFields(originalCar)) {
            return;
        }

        try {
            carService.modifyCar(originalCar);
        } catch (ServiceException e) {
            LOG.error("Could not modify car", e);
            JOptionPane.showMessageDialog(frame, "Could not modify car");
            return;
        }

        clearInputFields();
        listAllCars();
    }

    private boolean updateCarFields(Car originalCar) {
        boolean isUpdated = false;

        if (!nameField.getText().isEmpty()) {
            originalCar.setName(nameField.getText());
            isUpdated = true;
        }

        if (!brandField.getText().isEmpty()) {
            originalCar.setBrand(brandField.getText());
            isUpdated = true;
        }

        if (!yearField.getText().isEmpty()) {
            try {
                originalCar.setYear(Integer.parseInt(yearField.getText()));
                isUpdated = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid year format");
                return false;
            }
        }

        if (!priceField.getText().isEmpty()) {
            try {
                originalCar.setPrice(Double.parseDouble(priceField.getText()));
                isUpdated = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid price format");
                return false;
            }
        }

        return isUpdated;
    }

    private void deleteCar() throws RepoNotFoundException {
        LOG.info("Deleting car");

        int selectedRow = carTable.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            try {
                carService.removeCar(id);
            } catch (ServiceException e) {
                LOG.error("Could not delete car", e);
                JOptionPane.showMessageDialog(frame, "Could not delete car");
            }
            listAllCars();
        }
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a car(row).");
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        brandField.setText("");
        yearField.setText("");
        priceField.setText("");
    }

    private void clearSearchInputField() {
        searchField.setText("");
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application().frame.setVisible(true);
            }
        });
    }
}