/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2133;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2133Mapper {

    Issue2133Mapper INSTANCE = Mappers.getMapper( Issue2133Mapper.class );

    @BeanMapping(resultType = Target.class)
    AbstractTarget map(Source source);

    class Source {

        private EmbeddedDto embedded;

        public EmbeddedDto getEmbedded() {
            return embedded;
        }

        public void setEmbedded(EmbeddedDto embedded) {
            this.embedded = embedded;
        }
    }

    class Target extends AbstractTarget {
    }

    abstract class AbstractTarget {

        private EmbeddedEntity embedded;

        public EmbeddedEntity getEmbedded() {
            return embedded;
        }

        public void setEmbedded(EmbeddedEntity embedded) {
            this.embedded = embedded;
        }
    }

    class EmbeddedDto {

        private String s1;

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }
    }

    class EmbeddedEntity {

        private String s1;

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

    }
}
