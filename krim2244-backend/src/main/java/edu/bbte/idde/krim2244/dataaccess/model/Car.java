package edu.bbte.idde.krim2244.dataaccess.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Car extends BaseEntity {
    private String name;
    private String brand;
    private int year;
    private Double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate uploadDate;

    // konstruktorok...
    public Car() {
        super();
    }

    public Car(String name, String brand, int year, Double price) {
        super();
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.uploadDate = LocalDate.now();
    }

    public Car(String name, String brand, int year, Double price, LocalDate uploadDate) {
        super();
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.uploadDate = uploadDate;
    }

    public Car(Long id, String name, LocalDate uploadDate, Double price, int year, String brand) {
        super(id);
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.uploadDate = uploadDate;
    }

    // getterek setterek...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    // toString fölülírása...
    @Override
    public String toString() {
        return "Cars{"
                + "name='" + name + '\''
                + ", brand='" + brand + '\''
                + ", year=" + year
                + ", price=" + price
                + ", uploadDate=" + uploadDate
                + ", id=" + id
                + '}';
    }
}
