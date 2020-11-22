/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.string;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-24T17:41:29+0300",
    comments = "version: , compiler: javac, environment: Java 1.8.0_265 (AdoptOpenJDK)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setB( String.valueOf( source.getB() ) );
        if ( source.getBb() != null ) {
            target.setBb( String.valueOf( source.getBb() ) );
        }
        target.setS( String.valueOf( source.getS() ) );
        if ( source.getSs() != null ) {
            target.setSs( String.valueOf( source.getSs() ) );
        }
        target.setI( String.valueOf( source.getI() ) );
        if ( source.getIi() != null ) {
            target.setIi( String.valueOf( source.getIi() ) );
        }
        target.setL( String.valueOf( source.getL() ) );
        if ( source.getLl() != null ) {
            target.setLl( String.valueOf( source.getLl() ) );
        }
        target.setF( String.valueOf( source.getF() ) );
        if ( source.getFf() != null ) {
            target.setFf( String.valueOf( source.getFf() ) );
        }
        target.setD( String.valueOf( source.getD() ) );
        if ( source.getDd() != null ) {
            target.setDd( String.valueOf( source.getDd() ) );
        }
        target.setBool( String.valueOf( source.getBool() ) );
        if ( source.getBoolBool() != null ) {
            target.setBoolBool( String.valueOf( source.getBoolBool() ) );
        }
        target.setC( String.valueOf( source.getC() ) );
        if ( source.getCc() != null ) {
            target.setCc( source.getCc().toString() );
        }
        if ( source.getSb() != null ) {
            target.setSb( source.getSb().toString() );
        }

        return target;
    }

    @Override
    public Source targetToSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getB() != null ) {
            source.setB( Byte.parseByte( target.getB() ) );
        }
        if ( target.getBb() != null ) {
            source.setBb( Byte.parseByte( target.getBb() ) );
        }
        if ( target.getS() != null ) {
            source.setS( Short.parseShort( target.getS() ) );
        }
        if ( target.getSs() != null ) {
            source.setSs( Short.parseShort( target.getSs() ) );
        }
        if ( target.getI() != null ) {
            source.setI( Integer.parseInt( target.getI() ) );
        }
        if ( target.getIi() != null ) {
            source.setIi( Integer.parseInt( target.getIi() ) );
        }
        if ( target.getL() != null ) {
            source.setL( Long.parseLong( target.getL() ) );
        }
        if ( target.getLl() != null ) {
            source.setLl( Long.parseLong( target.getLl() ) );
        }
        if ( target.getF() != null ) {
            source.setF( Float.parseFloat( target.getF() ) );
        }
        if ( target.getFf() != null ) {
            source.setFf( Float.parseFloat( target.getFf() ) );
        }
        if ( target.getD() != null ) {
            source.setD( Double.parseDouble( target.getD() ) );
        }
        if ( target.getDd() != null ) {
            source.setDd( Double.parseDouble( target.getDd() ) );
        }
        if ( target.getBool() != null ) {
            source.setBool( Boolean.parseBoolean( target.getBool() ) );
        }
        if ( target.getBoolBool() != null ) {
            source.setBoolBool( Boolean.parseBoolean( target.getBoolBool() ) );
        }
        if ( target.getC() != null ) {
            source.setC( target.getC().charAt( 0 ) );
        }
        if ( target.getCc() != null ) {
            source.setCc( target.getCc().charAt( 0 ) );
        }
        source.setObject( target.getObject() );
        if ( target.getSb() != null ) {
            source.setSb( new StringBuilder( target.getSb() ) );
        }

        return source;
    }
}
