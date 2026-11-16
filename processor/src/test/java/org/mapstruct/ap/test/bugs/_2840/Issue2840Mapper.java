/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2840;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2840Mapper {

    Issue2840Mapper INSTANCE =
        Mappers.getMapper( Issue2840Mapper.class );

    Issue2840Mapper.Target map(Short shortValue, Integer intValue);

    default int toInt(Number number) {
        return number.intValue() + 5;
    }

    default short toShort(Number number) {
        return (short) (number.shortValue() + 10);
    }

    class Target {

        private int intValue;
        private short shortValue;

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public short getShortValue() {
            return shortValue;
        }

        public void setShortValue(short shortValue) {
            this.shortValue = shortValue;
        }
    }
}
