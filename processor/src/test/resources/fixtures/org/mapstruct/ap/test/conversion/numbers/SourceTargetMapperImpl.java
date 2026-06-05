/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T15:54:36+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 21.0.2 (Oracle Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setI( new DecimalFormat( "##.00" ).format( source.getI() ) );
        Integer ii = source.getIi();
        if ( ii != null ) {
            target.setIi( new DecimalFormat( "##.00" ).format( ii ) );
        }
        target.setD( new DecimalFormat( "##.00" ).format( source.getD() ) );
        Double dd = source.getDd();
        if ( dd != null ) {
            target.setDd( new DecimalFormat( "##.00" ).format( dd ) );
        }
        target.setF( new DecimalFormat( "##.00" ).format( source.getF() ) );
        Float ff = source.getFf();
        if ( ff != null ) {
            target.setFf( new DecimalFormat( "##.00" ).format( ff ) );
        }
        target.setL( new DecimalFormat( "##.00" ).format( source.getL() ) );
        Long ll = source.getLl();
        if ( ll != null ) {
            target.setLl( new DecimalFormat( "##.00" ).format( ll ) );
        }
        target.setB( new DecimalFormat( "##.00" ).format( source.getB() ) );
        Byte bb = source.getBb();
        if ( bb != null ) {
            target.setBb( new DecimalFormat( "##.00" ).format( bb ) );
        }
        target.setComplex1( new DecimalFormat( "##0.##E0" ).format( source.getComplex1() ) );
        target.setComplex2( new DecimalFormat( "$#.00" ).format( source.getComplex2() ) );
        BigDecimal bigDecimal1 = source.getBigDecimal1();
        if ( bigDecimal1 != null ) {
            target.setBigDecimal1( createDecimalFormat( "#0.#E0" ).format( bigDecimal1 ) );
        }
        BigInteger bigInteger1 = source.getBigInteger1();
        if ( bigInteger1 != null ) {
            target.setBigInteger1( createDecimalFormat( "0.#############E0" ).format( bigInteger1 ) );
        }

        return target;
    }

    @Override
    public Target sourceToTargetWithCustomLocale(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setI( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getI() ) );
        Integer ii = source.getIi();
        if ( ii != null ) {
            target.setIi( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( ii ) );
        }
        target.setD( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getD() ) );
        Double dd = source.getDd();
        if ( dd != null ) {
            target.setDd( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( dd ) );
        }
        target.setF( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getF() ) );
        Float ff = source.getFf();
        if ( ff != null ) {
            target.setFf( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( ff ) );
        }
        target.setL( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getL() ) );
        Long ll = source.getLl();
        if ( ll != null ) {
            target.setLl( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( ll ) );
        }
        target.setB( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getB() ) );
        Byte bb = source.getBb();
        if ( bb != null ) {
            target.setBb( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( bb ) );
        }
        target.setComplex1( new DecimalFormat( "##0.##E0", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getComplex1() ) );
        target.setComplex2( new DecimalFormat( "$#.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getComplex2() ) );
        BigDecimal bigDecimal1 = source.getBigDecimal1();
        if ( bigDecimal1 != null ) {
            target.setBigDecimal1( createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "ru" ) ).format( bigDecimal1 ) );
        }
        BigInteger bigInteger1 = source.getBigInteger1();
        if ( bigInteger1 != null ) {
            target.setBigInteger1( createDecimalFormatWithLocale( "0.#############E0", Locale.forLanguageTag( "ru" ) ).format( bigInteger1 ) );
        }

        return target;
    }

    @Override
    public Source targetToSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        try {
            String i = target.getI();
            if ( i != null ) {
                source.setI( new DecimalFormat( "##.00" ).parse( i ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String ii = target.getIi();
            if ( ii != null ) {
                source.setIi( new DecimalFormat( "##.00" ).parse( ii ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String d = target.getD();
            if ( d != null ) {
                source.setD( new DecimalFormat( "##.00" ).parse( d ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String dd = target.getDd();
            if ( dd != null ) {
                source.setDd( new DecimalFormat( "##.00" ).parse( dd ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String f = target.getF();
            if ( f != null ) {
                source.setF( new DecimalFormat( "##.00" ).parse( f ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String ff = target.getFf();
            if ( ff != null ) {
                source.setFf( new DecimalFormat( "##.00" ).parse( ff ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String l = target.getL();
            if ( l != null ) {
                source.setL( new DecimalFormat( "##.00" ).parse( l ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String ll = target.getLl();
            if ( ll != null ) {
                source.setLl( new DecimalFormat( "##.00" ).parse( ll ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String b = target.getB();
            if ( b != null ) {
                source.setB( new DecimalFormat( "##.00" ).parse( b ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String bb = target.getBb();
            if ( bb != null ) {
                source.setBb( new DecimalFormat( "##.00" ).parse( bb ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String complex1 = target.getComplex1();
            if ( complex1 != null ) {
                source.setComplex1( new DecimalFormat( "##0.##E0" ).parse( complex1 ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String complex2 = target.getComplex2();
            if ( complex2 != null ) {
                source.setComplex2( new DecimalFormat( "$#.00" ).parse( complex2 ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String bigDecimal1 = target.getBigDecimal1();
            if ( bigDecimal1 != null ) {
                source.setBigDecimal1( (BigDecimal) createDecimalFormat( "#0.#E0" ).parse( bigDecimal1 ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String bigInteger1 = target.getBigInteger1();
            if ( bigInteger1 != null ) {
                source.setBigInteger1( ( (BigDecimal) createDecimalFormat( "0.#############E0" ).parse( bigInteger1 ) ).toBigInteger() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return source;
    }

    @Override
    public Source targetToSourceWithCustomLocale(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        try {
            String i = target.getI();
            if ( i != null ) {
                source.setI( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( i ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String ii = target.getIi();
            if ( ii != null ) {
                source.setIi( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( ii ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String d = target.getD();
            if ( d != null ) {
                source.setD( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( d ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String dd = target.getDd();
            if ( dd != null ) {
                source.setDd( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( dd ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String f = target.getF();
            if ( f != null ) {
                source.setF( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( f ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String ff = target.getFf();
            if ( ff != null ) {
                source.setFf( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( ff ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String l = target.getL();
            if ( l != null ) {
                source.setL( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( l ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String ll = target.getLl();
            if ( ll != null ) {
                source.setLl( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( ll ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String b = target.getB();
            if ( b != null ) {
                source.setB( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( b ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String bb = target.getBb();
            if ( bb != null ) {
                source.setBb( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( bb ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String complex1 = target.getComplex1();
            if ( complex1 != null ) {
                source.setComplex1( new DecimalFormat( "##0.##E0", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( complex1 ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String complex2 = target.getComplex2();
            if ( complex2 != null ) {
                source.setComplex2( new DecimalFormat( "$#.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( complex2 ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String bigDecimal1 = target.getBigDecimal1();
            if ( bigDecimal1 != null ) {
                source.setBigDecimal1( (BigDecimal) createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "ru" ) ).parse( bigDecimal1 ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            String bigInteger1 = target.getBigInteger1();
            if ( bigInteger1 != null ) {
                source.setBigInteger1( ( (BigDecimal) createDecimalFormatWithLocale( "0.#############E0", Locale.forLanguageTag( "ru" ) ).parse( bigInteger1 ) ).toBigInteger() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return source;
    }

    @Override
    public List<String> sourceToTarget(List<Float> source) {
        if ( source == null ) {
            return null;
        }

        List<String> list = new ArrayList<>( source.size() );
        for ( Float float1 : source ) {
            list.add( new DecimalFormat( "##.00" ).format( float1 ) );
        }

        return list;
    }

    @Override
    public List<Float> targetToSource(List<String> source) {
        if ( source == null ) {
            return null;
        }

        List<Float> list = new ArrayList<>( source.size() );
        for ( String string : source ) {
            try {
                list.add( new DecimalFormat( "##.00" ).parse( string ).floatValue() );
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
        }

        return list;
    }

    @Override
    public List<String> sourceToTargetWithCustomLocale(List<BigDecimal> source) {
        if ( source == null ) {
            return null;
        }

        List<String> list = new ArrayList<>( source.size() );
        for ( BigDecimal bigDecimal : source ) {
            list.add( createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "fr" ) ).format( bigDecimal ) );
        }

        return list;
    }

    @Override
    public List<BigDecimal> targetToSourceWithCustomLocale(List<String> source) {
        if ( source == null ) {
            return null;
        }

        List<BigDecimal> list = new ArrayList<>( source.size() );
        for ( String string : source ) {
            try {
                list.add( (BigDecimal) createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "fr" ) ).parse( string ) );
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
        }

        return list;
    }

    @Override
    public Map<String, String> sourceToTarget(Map<Float, Float> source) {
        if ( source == null ) {
            return null;
        }

        Map<String, String> map = new LinkedHashMap<>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Float, Float> entry : source.entrySet() ) {
            String key = new DecimalFormat( "##.00" ).format( entry.getKey() );
            String value = new DecimalFormat( "##" ).format( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<String, String> sourceToTargetWithCustomLocale(Map<BigDecimal, BigDecimal> source) {
        if ( source == null ) {
            return null;
        }

        Map<String, String> map = new LinkedHashMap<>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<BigDecimal, BigDecimal> entry : source.entrySet() ) {
            String key = createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "fr" ) ).format( entry.getKey() );
            String value = createDecimalFormatWithLocale( "0.#############E0", Locale.forLanguageTag( "fr" ) ).format( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<Float, Float> targetToSource(Map<String, String> source) {
        if ( source == null ) {
            return null;
        }

        Map<Float, Float> map = new LinkedHashMap<>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, String> entry : source.entrySet() ) {
            Float key;
            try {
                key = new DecimalFormat( "##.00" ).parse( entry.getKey() ).floatValue();
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            Float value;
            try {
                value = new DecimalFormat( "##" ).parse( entry.getValue() ).floatValue();
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<BigDecimal, BigDecimal> targetToSourceWithCustomLocale(Map<String, String> source) {
        if ( source == null ) {
            return null;
        }

        Map<BigDecimal, BigDecimal> map = new LinkedHashMap<>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, String> entry : source.entrySet() ) {
            BigDecimal key;
            try {
                key = (BigDecimal) createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "fr" ) ).parse( entry.getKey() );
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            BigDecimal value;
            try {
                value = (BigDecimal) createDecimalFormatWithLocale( "0.#############E0", Locale.forLanguageTag( "fr" ) ).parse( entry.getValue() );
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            map.put( key, value );
        }

        return map;
    }

    private DecimalFormat createDecimalFormat( String numberFormat ) {

        DecimalFormat df = new DecimalFormat( numberFormat );
        df.setParseBigDecimal( true );
        return df;
    }

    private DecimalFormat createDecimalFormatWithLocale( String numberFormat, Locale locale ) {

        DecimalFormat df = new DecimalFormat( numberFormat, DecimalFormatSymbols.getInstance( locale ) );
        df.setParseBigDecimal( true );
        return df;
    }
}
