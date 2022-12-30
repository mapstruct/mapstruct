/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3126;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

/**
 * @author Ben Zegveld
 */
abstract class AddressDO {
    private String id;
    private Auditable auditable;

    public Auditable getAuditable() {
        return auditable;
    }

    public String getId() {
        return id;
    }

    public void setAuditable(Auditable auditable) {
        this.auditable = auditable;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class Auditable {
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

class HomeAddressDO extends AddressDO {
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

class OfficeAddressDO extends AddressDO {
    private String building;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}

interface AddressResponseDto {
}

class HomeAddressResponseDto implements AddressResponseDto {
    private String id;
    private String unit;
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public String getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

class OfficeAddressResponseDto implements AddressResponseDto {
    private String id;
    private String building;
    private String createdBy;

    public String getBuilding() {
        return building;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getId() {
        return id;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(String id) {
        this.id = id;
    }
}

@Mapper( subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION )
public interface AddressMapper {
    @SubclassMapping( source = HomeAddressDO.class, target = HomeAddressResponseDto.class )
    @SubclassMapping( source = OfficeAddressDO.class, target = OfficeAddressResponseDto.class )
    @Mapping( source = "auditable", target = "." )
    AddressResponseDto toDto(AddressDO addressDo);
}
