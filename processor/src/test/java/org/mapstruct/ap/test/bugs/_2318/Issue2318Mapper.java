/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2318;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = Issue2318Mapper.Config.class)
public interface Issue2318Mapper {

    Issue2318Mapper INSTANCE = Mappers.getMapper( Issue2318Mapper.class );

    @MapperConfig
    interface Config {

        @Mapping(target = "parentValue1", source = "holder")
        TargetParent mapParent(SourceParent parent);

        @InheritConfiguration(name = "mapParent")
        @Mapping(target = "childValue", source = "value")
        @Mapping(target = "parentValue2", source = "holder.parentValue2")
        TargetChild mapChild(SourceChild child);
    }

    @InheritConfiguration(name = "mapChild")
    TargetChild mapChild(SourceChild child);

    default String parentValue1(SourceParent.Holder holder) {
        return holder.getParentValue1();
    }

    class SourceParent {
        private Holder holder;

        public Holder getHolder() {
            return holder;
        }

        public void setHolder(Holder holder) {
            this.holder = holder;
        }

        public static class Holder {
            private String parentValue1;
            private Integer parentValue2;

            public String getParentValue1() {
                return parentValue1;
            }

            public void setParentValue1(String parentValue1) {
                this.parentValue1 = parentValue1;
            }

            public Integer getParentValue2() {
                return parentValue2;
            }

            public void setParentValue2(Integer parentValue2) {
                this.parentValue2 = parentValue2;
            }
        }
    }

    class SourceChild extends SourceParent {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class TargetParent {
        private String parentValue1;
        private Integer parentValue2;

        public String getParentValue1() {
            return parentValue1;
        }

        public void setParentValue1(String parentValue1) {
            this.parentValue1 = parentValue1;
        }

        public Integer getParentValue2() {
            return parentValue2;
        }

        public void setParentValue2(Integer parentValue2) {
            this.parentValue2 = parentValue2;
        }
    }

    class TargetChild extends TargetParent {
        private String childValue;

        public String getChildValue() {
            return childValue;
        }

        public void setChildValue(String childValue) {
            this.childValue = childValue;
        }
    }
}
