package org.mapstruct.ap.test.bugs._2322;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2322Mapper {

    Issue2322Mapper INSTANCE = Mappers.getMapper( Issue2322Mapper.class );

    @Mapping( target = "b", source = "a" )
    @Mapping( target = "b.field1B", source = "a.field1A" )
    Wrap map(A a);

    class A {
        //CHECKSTYLE:OFF
        public int field1A;
        public int field2;
        public int field3;
        //CHECKSTYLE:ON
    }

    class B {
        //CHECKSTYLE:OFF
        public int field1B;
        public int field2;
        public int field3;
        //CHECKSTYLE:ON
    }

    class Wrap {
        //CHECKSTYLE:OFF
        public B b;
        //CHECKSTYLE:ON
    }
}
