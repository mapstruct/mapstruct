/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private String propertyThatShouldBeMapped;
    private String stringConstant;
    private String emptyStringConstant;
    private int integerConstant;
    private Long longWrapperConstant;
    private Date dateConstant;
    private List<String> nameConstants = new ArrayList<>();
    private CountryEnum country;

    public String getPropertyThatShouldBeMapped() {
        return propertyThatShouldBeMapped;
    }

    public void setPropertyThatShouldBeMapped(String propertyThatShouldBeMapped) {
        this.propertyThatShouldBeMapped = propertyThatShouldBeMapped;
    }

    public String getStringConstant() {
        return stringConstant;
    }

    public void setStringConstant(String stringConstant) {
        this.stringConstant = stringConstant;
    }

    public int getIntegerConstant() {
        return integerConstant;
    }

    public void setIntegerConstant(int integerConstant) {
        this.integerConstant = integerConstant;
    }

    public Long getLongWrapperConstant() {
        return longWrapperConstant;
    }

    public void setLongWrapperConstant(Long longWrapperConstant) {
        this.longWrapperConstant = longWrapperConstant;
    }

    public Date getDateConstant() {
        return dateConstant;
    }

    public void setDateConstant(Date dateConstant) {
        this.dateConstant = dateConstant;
    }

    public List<String> getNameConstants() {
        return nameConstants;
    }

    public String getEmptyStringConstant() {
        return emptyStringConstant;
    }

    public void setEmptyStringConstant(String emptyStringConstant) {
        this.emptyStringConstant = emptyStringConstant;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

}
