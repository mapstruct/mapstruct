/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-10T10:00:00+0530",
    comments = "version: , compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
public class JSpecifyDisabledReusedMethodMapperImpl implements JSpecifyDisabledReusedMethodMapper {

    private final JSpecifySimpleReusedMethodUses jSpecifySimpleReusedMethodUses = new JSpecifySimpleReusedMethodUses();

    @Override
    public JSpecifySimpleReusedMethodTarget map(JSpecifySimpleReusedMethodSource source) {
        if ( source == null ) {
            return null;
        }

        JSpecifySimpleReusedMethodTarget jSpecifySimpleReusedMethodTarget = new JSpecifySimpleReusedMethodTarget();

        jSpecifySimpleReusedMethodTarget.setValue( jSpecifySimpleReusedMethodUses.map( source.getValue() ) );

        return jSpecifySimpleReusedMethodTarget;
    }
}
