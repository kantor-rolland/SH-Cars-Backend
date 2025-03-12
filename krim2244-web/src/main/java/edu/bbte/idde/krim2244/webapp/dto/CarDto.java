package edu.bbte.idde.krim2244.webapp.dto;

public class CarDto {
    private final Integer id;
    private final String name;
    private final String brand;
    private final Integer year;
    private final Double price;
    private final String uploadDate;

    public CarDto(Integer id, String name, String brand, Integer year, Double price, String uploadDate) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.uploadDate = uploadDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getYear() {
        return year;
    }

    public Double getPrice() {
        return price;
    }

    public String getUploadDate() {
        return uploadDate;
    }
}
