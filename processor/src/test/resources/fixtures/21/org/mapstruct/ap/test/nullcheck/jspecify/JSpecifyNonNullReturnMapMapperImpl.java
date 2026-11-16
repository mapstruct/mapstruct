/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T00:03:32+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNonNullReturnMapMapperImpl implements JSpecifyNonNullReturnMapMapper {

    @Override
    public Map<String, NullMarkedTargetBean> mapAll(Map<String, NullMarkedSourceBean> sources) {
        if ( sources == null ) {
            return new LinkedHashMap<>();
        }

        Map<String, NullMarkedTargetBean> map = LinkedHashMap.newLinkedHashMap( sources.size() );

        for ( java.util.Map.Entry<String, NullMarkedSourceBean> entry : sources.entrySet() ) {
            String key = entry.getKey();
            NullMarkedTargetBean value = map( entry.getValue() );
            map.put( key, value );
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
