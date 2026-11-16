/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1130;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * Test mapper similar to the one provided in the issue.
 *
 * @author Andreas Gudian
 */
@Mapper
public abstract class Issue1130Mapper {
    static class AEntity {
        private BEntity b;

        public BEntity getB() {
            return b;
        }

        public void setB(BEntity b) {
            this.b = b;
        }
    }

    static class BEntity {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    static class ADto {
        private BDto b;

        public BDto getB() {
            return b;
        }

        public void setB(BDto b) {
            this.b = b;
        }
    }

    class BDto {
        private final String passedViaConstructor;
        private String id;

        BDto(String passedViaConstructor) {
            this.passedViaConstructor = passedViaConstructor;
        }

        String getPassedViaConstructor() {
            return passedViaConstructor;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    abstract void mergeA(AEntity source, @MappingTarget ADto target);

    abstract void mergeB(BEntity source, @MappingTarget BDto target);

    @ObjectFactory
    protected BDto createB(@TargetType Class<BDto> clazz) {
        return new BDto( "created by factory" );
    }
}
