/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.string;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:55:53+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setB( String.valueOf( source.getB() ) );
        Byte bb = source.getBb();
        if ( bb != null ) {
            target.setBb( String.valueOf( bb ) );
        }
        target.setS( String.valueOf( source.getS() ) );
        Short ss = source.getSs();
        if ( ss != null ) {
            target.setSs( String.valueOf( ss ) );
        }
        target.setI( String.valueOf( source.getI() ) );
        Integer ii = source.getIi();
        if ( ii != null ) {
            target.setIi( String.valueOf( ii ) );
        }
        target.setL( String.valueOf( source.getL() ) );
        Long ll = source.getLl();
        if ( ll != null ) {
            target.setLl( String.valueOf( ll ) );
        }
        target.setF( String.valueOf( source.getF() ) );
        Float ff = source.getFf();
        if ( ff != null ) {
            target.setFf( String.valueOf( ff ) );
        }
        target.setD( String.valueOf( source.getD() ) );
        Double dd = source.getDd();
        if ( dd != null ) {
            target.setDd( String.valueOf( dd ) );
        }
        target.setBool( String.valueOf( source.getBool() ) );
        Boolean boolBool = source.getBoolBool();
        if ( boolBool != null ) {
            target.setBoolBool( String.valueOf( boolBool ) );
        }
        target.setC( String.valueOf( source.getC() ) );
        Character cc = source.getCc();
        if ( cc != null ) {
            target.setCc( cc.toString() );
        }
        StringBuilder sb = source.getSb();
        if ( sb != null ) {
            target.setSb( sb.toString() );
        }

        return target;
    }

    @Override
    public Source targetToSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String b = target.getB();
        if ( b != null ) {
            source.setB( Byte.parseByte( b ) );
        }
        String bb = target.getBb();
        if ( bb != null ) {
            source.setBb( Byte.parseByte( bb ) );
        }
        String s = target.getS();
        if ( s != null ) {
            source.setS( Short.parseShort( s ) );
        }
        String ss = target.getSs();
        if ( ss != null ) {
            source.setSs( Short.parseShort( ss ) );
        }
        String i = target.getI();
        if ( i != null ) {
            source.setI( Integer.parseInt( i ) );
        }
        String ii = target.getIi();
        if ( ii != null ) {
            source.setIi( Integer.parseInt( ii ) );
        }
        String l = target.getL();
        if ( l != null ) {
            source.setL( Long.parseLong( l ) );
        }
        String ll = target.getLl();
        if ( ll != null ) {
            source.setLl( Long.parseLong( ll ) );
        }
        String f = target.getF();
        if ( f != null ) {
            source.setF( Float.parseFloat( f ) );
        }
        String ff = target.getFf();
        if ( ff != null ) {
            source.setFf( Float.parseFloat( ff ) );
        }
        String d = target.getD();
        if ( d != null ) {
            source.setD( Double.parseDouble( d ) );
        }
        String dd = target.getDd();
        if ( dd != null ) {
            source.setDd( Double.parseDouble( dd ) );
        }
        String bool = target.getBool();
        if ( bool != null ) {
            source.setBool( Boolean.parseBoolean( bool ) );
        }
        String boolBool = target.getBoolBool();
        if ( boolBool != null ) {
            source.setBoolBool( Boolean.parseBoolean( boolBool ) );
        }
        String c = target.getC();
        if ( c != null ) {
            source.setC( c.charAt( 0 ) );
        }
        String cc = target.getCc();
        if ( cc != null ) {
            source.setCc( cc.charAt( 0 ) );
        }
        source.setObject( target.getObject() );
        String sb = target.getSb();
        if ( sb != null ) {
            source.setSb( new StringBuilder( sb ) );
        }

        return source;
    }
}
