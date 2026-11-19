/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-19T20:17:26+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.13 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyNullableValueMapMapperImpl implements JSpecifyNullableValueMapMapper {

    @Override
    public Map<String, NullMarkedTargetBean> mapAll(Map<String, NullMarkedSourceBean> sources) {

        Map<String, NullMarkedTargetBean> map = LinkedHashMap.newLinkedHashMap( sources.size() );

        for ( java.util.Map.Entry<String, NullMarkedSourceBean> entry : sources.entrySet() ) {
            String key = entry.getKey();
            if ( entry.getValue() != null ) {
                NullMarkedTargetBean value = map( entry.getValue() );
                map.put( key, value );
            }
            else {
                map.put( key, null );
            }
        }

        return map;
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
