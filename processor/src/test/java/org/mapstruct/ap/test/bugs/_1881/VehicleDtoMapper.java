/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1881;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface VehicleDtoMapper {

  VehicleDtoMapper INSTANCE = Mappers.getMapper( VehicleDtoMapper.class );

  @Mapping(source = "name", target = "name")
  @Mapping(source = "size", target = "vehicleProperties.size")
  @Mapping(source = "type", target = "vehicleProperties.type")
  VehicleDto map(Vehicle vehicle);

  class VehicleDto {
    private String name;
    private VehiclePropertiesDto vehicleProperties;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public VehiclePropertiesDto getVehicleProperties() {
      return vehicleProperties;
    }

    public void setVehicleProperties(VehiclePropertiesDto vehicleProperties) {
      this.vehicleProperties = vehicleProperties;
    }
  }

  class Vehicle {
    private final String name;
    private final int size;
    private final String type;

    public Vehicle(String name, int size, String type) {
      this.name = name;
      this.size = size;
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public int getSize() {
      return size;
    }

    public String getType() {
      return type;
    }
  }

  class VehiclePropertiesDto {
    private int size;
    private String type;

    public int getSize() {
      return size;
    }

    public void setSize(int size) {
      this.size = size;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
