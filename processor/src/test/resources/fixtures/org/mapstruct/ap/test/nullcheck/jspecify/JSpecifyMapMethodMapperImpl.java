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
    date = "2026-05-14T23:30:08+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyMapMethodMapperImpl implements JSpecifyMapMethodMapper {

    @Override
    public Map<String, NullMarkedTargetBean> mapAll(Map<String, NullMarkedSourceBean> sources) {

        Map<String, NullMarkedTargetBean> map = new LinkedHashMap<>( Math.max( (int) ( sources.size() / .75f ) + 1, 16 ) );

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
