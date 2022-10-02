/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3040;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

/**
 * @author orange add
 */
@Mapper
public interface Issue3040Mapper {

    @BeanMapping( mappingControl =  DeepClone.class )
    FruitDto map( Fruit fruit );

    class Fruit {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class FruitDto {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
