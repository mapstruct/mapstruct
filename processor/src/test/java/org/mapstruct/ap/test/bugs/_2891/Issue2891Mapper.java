/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2891;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

/**
 * @author Sergei Portnov
 */
@Mapper
public interface Issue2891Mapper {

    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @SubclassMapping(source = Source1.class, target = Target1.class)
    @SubclassMapping(source = Source2.class, target = Target2.class)
    AbstractTarget map(AbstractSource source);

    abstract class AbstractTarget {

        private final String name;

        protected AbstractTarget(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class Target1 extends AbstractTarget {

        protected Target1(String name) {
            super( name );
        }
    }

    class Target2 extends AbstractTarget {

        protected Target2(String name) {
            super( name );
        }
    }

    abstract class AbstractSource {

        private final String name;

        protected AbstractSource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class Source1 extends AbstractSource {
        protected Source1(String name) {
            super( name );
        }
    }

    class Source2 extends AbstractSource {

        protected Source2(String name) {
            super( name );
        }
    }
}
