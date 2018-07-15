/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.complex._target;

import java.util.List;

public class CarDto {

    private String make;
    private int seatCount;
    private String manufacturingYear;
    private PersonDto driver;
    private List<PersonDto> passengers;
    private Long price;
    private String category;

    public CarDto() {
    }

    public CarDto(String make, int seatCount, String manufacturingYear, PersonDto driver, List<PersonDto> passengers) {
        this.make = make;
        this.seatCount = seatCount;
        this.manufacturingYear = manufacturingYear;
        this.driver = driver;
        this.passengers = passengers;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(String manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public PersonDto getDriver() {
        return driver;
    }

    public void setDriver(PersonDto driver) {
        this.driver = driver;
    }

    public List<PersonDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PersonDto> passengers) {
        this.passengers = passengers;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
