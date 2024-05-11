package org.mapstruct.ap.test.bugs._3591;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanMapperWithSelectionParameters {

    BeanMapperWithSelectionParameters INSTANCE = Mappers.getMapper( BeanMapperWithSelectionParameters.class );

    @Mapping(target = "beans", source = "beans", qualifiedBy = WithQualifier.class)
    void mapWithQualifier(Bean bean, @MappingTarget BeanDto beanDto);

    @Mapping(target = "beans", source = "beans", qualifiedByName = "ByName")
    void mapWithQualifiedByName(Bean bean, @MappingTarget BeanDto beanDto);

    @Mapping(target = "beans", source = "beans", resultType = SpecialBeanDto.class)
    void mapToResultType(Bean bean, @MappingTarget BeanDto beanDto);

    @Mapping(target = "beans", source = "beans")
    BeanDto map(Bean bean, @MappingTarget BeanDto beanDto);

    SpecialBeanDto toSpecialBeanDto(Bean bean);

    @Named("ByName")
    default BeanDto byName(Bean bean) {
        BeanDto beanDto = new BeanDto();
        beanDto.setValue( "QualifiedByName" );

        return beanDto;
    }

    @WithQualifier
    @Mapping(target = "value", expression = "java(\"withQualifier\")")
    BeanDto withQualifier(Bean bean);

    @Qualifier
    @interface WithQualifier {
    }

}
