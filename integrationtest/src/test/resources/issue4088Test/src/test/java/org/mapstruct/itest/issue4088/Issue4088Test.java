/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.issue4088;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class Issue4088Test {

    @Test
    void updateFromRequestShouldCompileAndMapNestedRecordSource() {
        SwTermin target = new SwTermin();

        SwTerminMapper.INSTANCE.updateFromRequest(
            new UpdateSwTerminRequest( new UpdateSwTerminRequest.Termin( new LocalDateTime( 2026, 7, 9 ) ) ),
            target
        );

        assertThat( target.getDatum() ).isNotNull();
        assertThat( target.getDatum().getYear() ).isEqualTo( 2026 );
        assertThat( target.getDatum().getMonth() ).isEqualTo( 7 );
        assertThat( target.getDatum().getDay() ).isEqualTo( 9 );
    }
}

@Mapper
abstract class SwTerminMapper {

    static final SwTerminMapper INSTANCE = Mappers.getMapper( SwTerminMapper.class );

    @Mapping(target = "datum", source = "termin.termin")
    abstract SwTermin createFromRequest(UpdateSwTerminRequest request);

    @InheritConfiguration
    abstract void updateFromRequest(UpdateSwTerminRequest request, @MappingTarget SwTermin termin);
}

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
class SwTermin {

    private @Nullable LocalDate datum;
}

record UpdateSwTerminRequest(Termin termin) {

    record Termin(LocalDateTime termin) {
    }
}

class LocalDate {

    private int day;
    private int month;
    private int year;

    LocalDate() {
    }

    LocalDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

class LocalDateTime {

    private int day;
    private int month;
    private int year;

    LocalDateTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
