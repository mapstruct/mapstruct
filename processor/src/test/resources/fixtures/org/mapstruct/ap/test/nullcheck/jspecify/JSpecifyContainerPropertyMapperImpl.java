/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-19T23:41:38+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class JSpecifyContainerPropertyMapperImpl implements JSpecifyContainerPropertyMapper {

    @Override
    public JSpecifyContainerTargetBean map(JSpecifyContainerSourceBean source) {
        if ( source == null ) {
            return null;
        }

        JSpecifyContainerTargetBean jSpecifyContainerTargetBean = new JSpecifyContainerTargetBean();

        List<String> list = source.getValues();
        if ( list != null ) {
            jSpecifyContainerTargetBean.setValues( new ArrayList<>( list ) );
        }
        Map<String, String> map = source.getEntries();
        if ( map != null ) {
            jSpecifyContainerTargetBean.setEntries( new LinkedHashMap<>( map ) );
        }
        if ( source.getItems() != null ) {
            jSpecifyContainerTargetBean.setItems( source.getItems() );
        }

        return jSpecifyContainerTargetBean;
    }
}
