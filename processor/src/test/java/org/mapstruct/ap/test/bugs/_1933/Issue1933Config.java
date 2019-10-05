/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1933;

import org.mapstruct.BeanMapping;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;

/**
 * @author Sjaak Derksen
 */
@MapperConfig(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface Issue1933Config {

    @BeanMapping(ignoreByDefault = true)
    Entity updateEntity(Dto dto);

    class Entity {
        //CHECKSTYLE:OFF
        public String id;
        public int updateCount;
        //CHECKSTYLE:ON
    }

    class Dto {
        //CHECKSTYLE:OFF
        public String id;
        public int updateCount;
        //CHECKSTYLE:ON
    }

}
