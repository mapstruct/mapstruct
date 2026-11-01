/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtomethod;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T22:13:51+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class MethodToMethodMapperImpl implements MethodToMethodMapper {

    @Override
    public MethodToMethodTargetBean map(MethodToMethodSourceBean source) {
        if ( source == null ) {
            return null;
        }

        MethodToMethodTargetBean methodToMethodTargetBean = new MethodToMethodTargetBean();

        methodToMethodTargetBean.setValue( wrapToMyTargetValue( source.getName() ) );

        return methodToMethodTargetBean;
    }

    private MyTargetValue wrapToMyTargetValue(String s) {
        MyWrapper string = wrap( s );
        if (string == null) {
            return null;
        }
        return unwrap( string );
    }
}
