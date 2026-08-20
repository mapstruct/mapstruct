/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T22:10:29+0530",
    comments = "version: , compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
public class JSpecifyReusedMethodMapperImpl implements JSpecifyReusedMethodMapper {

    private final JSpecifyReusedMethodUses jSpecifyReusedMethodUses = new JSpecifyReusedMethodUses();

    @Override
    public Target map(Source source) {

        Target target = new Target();

        if ( source.getGuarded() != null ) {
            target.setGuarded( jSpecifyReusedMethodUses.mapNonNull( source.getGuarded() ) );
        }
        target.setDirect( jSpecifyReusedMethodUses.mapNullableInput( source.getDirect() ) );
        target.setUnmarked( jSpecifyReusedMethodUses.mapUnmarked( source.getUnmarked() ) );
        target.setValues( jSpecifyReusedMethodUses.mapList( source.getValues() ) );
        target.setEntries( jSpecifyReusedMethodUses.mapMap( source.getEntries() ) );
        if ( source.getChain() != null ) {
            target.setChain( jSpecifyReusedMethodUses.mapChainEnd( jSpecifyReusedMethodUses.mapChainStart( source.getChain() ) ) );
        }

        return target;
    }
}
