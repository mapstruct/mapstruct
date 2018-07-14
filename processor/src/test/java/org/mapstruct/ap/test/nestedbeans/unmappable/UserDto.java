/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

import java.util.List;
import java.util.Map;

public class UserDto {

    private String name;
    private CarDto car;
    private HouseDto house;
    private DictionaryDto dictionary;
    private List<ComputerDto> computers;
    private Map<String, CatDto> catNameMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public HouseDto getHouse() {
        return house;
    }

    public void setHouse(HouseDto house) {
        this.house = house;
    }

    public DictionaryDto getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryDto dictionary) {
        this.dictionary = dictionary;
    }

    public List<ComputerDto> getComputers() {
        return computers;
    }

    public void setComputers(List<ComputerDto> computers) {
        this.computers = computers;
    }

    public Map<String, CatDto> getCatNameMap() {
        return catNameMap;
    }

    public void setCatNameMap(
        Map<String, CatDto> catNameMap) {
        this.catNameMap = catNameMap;
    }
}
