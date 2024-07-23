/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3591;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-25T14:23:23+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
public class ContainerBeanMapperImpl implements ContainerBeanMapper {

    @Override
    public ContainerBeanDto mapWithMapMapping(ContainerBean containerBean, ContainerBeanDto containerBeanDto) {
        if ( containerBean == null ) {
            return containerBeanDto;
        }

        if ( containerBeanDto.getBeanMap() != null ) {
            Map<String, ContainerBeanDto> map = stringContainerBeanMapToStringContainerBeanDtoMap( containerBean.getBeanMap() );
            if ( map != null ) {
                containerBeanDto.getBeanMap().clear();
                containerBeanDto.getBeanMap().putAll( map );
            }
            else {
                containerBeanDto.setBeanMap( null );
            }
        }
        else {
            Map<String, ContainerBeanDto> map = stringContainerBeanMapToStringContainerBeanDtoMap( containerBean.getBeanMap() );
            if ( map != null ) {
                containerBeanDto.setBeanMap( map );
            }
        }
        containerBeanDto.setBeanStream( containerBeanStreamToContainerBeanDtoStream( containerBean.getBeanStream() ) );
        containerBeanDto.setValue( containerBean.getValue() );

        return containerBeanDto;
    }

    protected Stream<ContainerBeanDto> containerBeanStreamToContainerBeanDtoStream(Stream<ContainerBean> stream) {
        if ( stream == null ) {
            return null;
        }

        return stream.map( containerBean -> containerBeanToContainerBeanDto( containerBean ) );
    }

    protected ContainerBeanDto containerBeanToContainerBeanDto(ContainerBean containerBean) {
        if ( containerBean == null ) {
            return null;
        }

        ContainerBeanDto containerBeanDto = new ContainerBeanDto();

        containerBeanDto.setBeanMap( stringContainerBeanMapToStringContainerBeanDtoMap( containerBean.getBeanMap() ) );
        containerBeanDto.setBeanStream( containerBeanStreamToContainerBeanDtoStream( containerBean.getBeanStream() ) );
        containerBeanDto.setValue( containerBean.getValue() );

        return containerBeanDto;
    }

    protected Map<String, ContainerBeanDto> stringContainerBeanMapToStringContainerBeanDtoMap(Map<String, ContainerBean> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, ContainerBeanDto> map1 = LinkedHashMap.newLinkedHashMap( map.size() );

        for ( java.util.Map.Entry<String, ContainerBean> entry : map.entrySet() ) {
            String key = entry.getKey();
            ContainerBeanDto value = containerBeanToContainerBeanDto( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
