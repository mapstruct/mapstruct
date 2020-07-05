package org.mapstruct.ap.test.bugs._2101;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Issue2102IgnoreAllButMapper {

    @BeanMapping( ignoreByDefault = true )
    @Mapping(target = "value1") // but do map value1
    Target map1(Source source);

    @InheritConfiguration
    @Mapping(target = "value1", source = "value2" )
    Target map2(Source source);

    //CHECKSTYLE:OFF
    class Source {
        public String value1;
        public String value2;
    }

    class Target {
        public String value1;
    }
    //CHECKSTYLE:ON

}
