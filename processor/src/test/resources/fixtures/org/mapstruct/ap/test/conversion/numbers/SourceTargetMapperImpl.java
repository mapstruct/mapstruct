/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.numbers;

import java.math.BigDecimal;
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
    date = "2024-09-14T11:36:20+0300",
    comments = "version: , compiler: javac, environment: Java 17.0.10 (Private Build)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setI( new DecimalFormat( "##.00" ).format( source.getI() ) );
        if ( source.getIi() != null ) {
            target.setIi( new DecimalFormat( "##.00" ).format( source.getIi() ) );
        }
        target.setD( new DecimalFormat( "##.00" ).format( source.getD() ) );
        if ( source.getDd() != null ) {
            target.setDd( new DecimalFormat( "##.00" ).format( source.getDd() ) );
        }
        target.setF( new DecimalFormat( "##.00" ).format( source.getF() ) );
        if ( source.getFf() != null ) {
            target.setFf( new DecimalFormat( "##.00" ).format( source.getFf() ) );
        }
        target.setL( new DecimalFormat( "##.00" ).format( source.getL() ) );
        if ( source.getLl() != null ) {
            target.setLl( new DecimalFormat( "##.00" ).format( source.getLl() ) );
        }
        target.setB( new DecimalFormat( "##.00" ).format( source.getB() ) );
        if ( source.getBb() != null ) {
            target.setBb( new DecimalFormat( "##.00" ).format( source.getBb() ) );
        }
        target.setComplex1( new DecimalFormat( "##0.##E0" ).format( source.getComplex1() ) );
        target.setComplex2( new DecimalFormat( "$#.00" ).format( source.getComplex2() ) );
        if ( source.getBigDecimal1() != null ) {
            target.setBigDecimal1( createDecimalFormat( "#0.#E0" ).format( source.getBigDecimal1() ) );
        }
        if ( source.getBigInteger1() != null ) {
            target.setBigInteger1( createDecimalFormat( "0.#############E0" ).format( source.getBigInteger1() ) );
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
        if ( source.getIi() != null ) {
            target.setIi( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getIi() ) );
        }
        target.setD( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getD() ) );
        if ( source.getDd() != null ) {
            target.setDd( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getDd() ) );
        }
        target.setF( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getF() ) );
        if ( source.getFf() != null ) {
            target.setFf( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getFf() ) );
        }
        target.setL( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getL() ) );
        if ( source.getLl() != null ) {
            target.setLl( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getLl() ) );
        }
        target.setB( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getB() ) );
        if ( source.getBb() != null ) {
            target.setBb( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getBb() ) );
        }
        target.setComplex1( new DecimalFormat( "##0.##E0", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getComplex1() ) );
        target.setComplex2( new DecimalFormat( "$#.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).format( source.getComplex2() ) );
        if ( source.getBigDecimal1() != null ) {
            target.setBigDecimal1( createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "ru" ) ).format( source.getBigDecimal1() ) );
        }
        if ( source.getBigInteger1() != null ) {
            target.setBigInteger1( createDecimalFormatWithLocale( "0.#############E0", Locale.forLanguageTag( "ru" ) ).format( source.getBigInteger1() ) );
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
            if ( target.getI() != null ) {
                source.setI( new DecimalFormat( "##.00" ).parse( target.getI() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getIi() != null ) {
                source.setIi( new DecimalFormat( "##.00" ).parse( target.getIi() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getD() != null ) {
                source.setD( new DecimalFormat( "##.00" ).parse( target.getD() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getDd() != null ) {
                source.setDd( new DecimalFormat( "##.00" ).parse( target.getDd() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getF() != null ) {
                source.setF( new DecimalFormat( "##.00" ).parse( target.getF() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getFf() != null ) {
                source.setFf( new DecimalFormat( "##.00" ).parse( target.getFf() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getL() != null ) {
                source.setL( new DecimalFormat( "##.00" ).parse( target.getL() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getLl() != null ) {
                source.setLl( new DecimalFormat( "##.00" ).parse( target.getLl() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getB() != null ) {
                source.setB( new DecimalFormat( "##.00" ).parse( target.getB() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBb() != null ) {
                source.setBb( new DecimalFormat( "##.00" ).parse( target.getBb() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex1() != null ) {
                source.setComplex1( new DecimalFormat( "##0.##E0" ).parse( target.getComplex1() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex2() != null ) {
                source.setComplex2( new DecimalFormat( "$#.00" ).parse( target.getComplex2() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigDecimal1() != null ) {
                source.setBigDecimal1( (BigDecimal) createDecimalFormat( "#0.#E0" ).parse( target.getBigDecimal1() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigInteger1() != null ) {
                source.setBigInteger1( ( (BigDecimal) createDecimalFormat( "0.#############E0" ).parse( target.getBigInteger1() ) ).toBigInteger() );
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
            if ( target.getI() != null ) {
                source.setI( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getI() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getIi() != null ) {
                source.setIi( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getIi() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getD() != null ) {
                source.setD( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getD() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getDd() != null ) {
                source.setDd( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getDd() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getF() != null ) {
                source.setF( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getF() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getFf() != null ) {
                source.setFf( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getFf() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getL() != null ) {
                source.setL( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getL() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getLl() != null ) {
                source.setLl( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getLl() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getB() != null ) {
                source.setB( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getB() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBb() != null ) {
                source.setBb( new DecimalFormat( "##.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getBb() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex1() != null ) {
                source.setComplex1( new DecimalFormat( "##0.##E0", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getComplex1() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex2() != null ) {
                source.setComplex2( new DecimalFormat( "$#.00", DecimalFormatSymbols.getInstance( Locale.forLanguageTag( "ru " ) ) ).parse( target.getComplex2() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigDecimal1() != null ) {
                source.setBigDecimal1( (BigDecimal) createDecimalFormatWithLocale( "#0.#E0", Locale.forLanguageTag( "ru" ) ).parse( target.getBigDecimal1() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigInteger1() != null ) {
                source.setBigInteger1( ( (BigDecimal) createDecimalFormatWithLocale( "0.#############E0", Locale.forLanguageTag( "ru" ) ).parse( target.getBigInteger1() ) ).toBigInteger() );
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

        List<String> list = new ArrayList<String>( source.size() );
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

        List<Float> list = new ArrayList<Float>( source.size() );
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

        List<String> list = new ArrayList<String>( source.size() );
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

        List<BigDecimal> list = new ArrayList<BigDecimal>( source.size() );
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

        Map<String, String> map = new LinkedHashMap<String, String>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

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

        Map<String, String> map = new LinkedHashMap<String, String>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

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

        Map<Float, Float> map = new LinkedHashMap<Float, Float>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

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

        Map<BigDecimal, BigDecimal> map = new LinkedHashMap<BigDecimal, BigDecimal>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );

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

    private DecimalFormat createDecimalFormatWithLocale( String numberFormat, Locale locale ) {

        DecimalFormat df = new DecimalFormat( numberFormat, DecimalFormatSymbols.getInstance( locale ) );
        df.setParseBigDecimal( true );
        return df;
    }

    private DecimalFormat createDecimalFormat( String numberFormat ) {

        DecimalFormat df = new DecimalFormat( numberFormat );
        df.setParseBigDecimal( true );
        return df;
    }
}
