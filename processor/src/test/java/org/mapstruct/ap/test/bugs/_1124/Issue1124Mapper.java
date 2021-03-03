/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1124;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface Issue1124Mapper {
    class Entity {
        private Long id;
        private Entity entity;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }
    }

    class DTO {
        private Long id;
        private DTO entity;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public DTO getEntity() {
            return entity;
        }

        public void setEntity(DTO entity) {
            this.entity = entity;
        }
    }

    class MappingContext {
    }

    @Mapping(target = "id", source = "entity.id")
    DTO map(Entity entity, @Context MappingContext context);
}
