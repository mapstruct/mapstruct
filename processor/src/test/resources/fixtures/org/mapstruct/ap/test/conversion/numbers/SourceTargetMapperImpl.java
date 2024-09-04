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
    date = "2024-09-04T13:31:24+0300",
    comments = "version: , compiler: javac, environment: Java 17.0.10 (Private Build)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target sourceToTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setI( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getI() ) );
        if ( source.getIi() != null ) {
            target.setIi( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getIi() ) );
        }
        target.setD( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getD() ) );
        if ( source.getDd() != null ) {
            target.setDd( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getDd() ) );
        }
        target.setF( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getF() ) );
        if ( source.getFf() != null ) {
            target.setFf( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getFf() ) );
        }
        target.setL( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getL() ) );
        if ( source.getLl() != null ) {
            target.setLl( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getLl() ) );
        }
        target.setB( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getB() ) );
        if ( source.getBb() != null ) {
            target.setBb( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getBb() ) );
        }
        target.setComplex1( new DecimalFormat( "##0.##E0", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getComplex1() ) );
        target.setComplex2( new DecimalFormat( "$#.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( source.getComplex2() ) );
        if ( source.getBigDecimal1() != null ) {
            target.setBigDecimal1( createDecimalFormat( "#0.#E0", java.util.Locale.getDefault() ).format( source.getBigDecimal1() ) );
        }
        if ( source.getBigInteger1() != null ) {
            target.setBigInteger1( createDecimalFormat( "0.#############E0", Locale.getDefault() ).format( source.getBigInteger1() ) );
        }

        return target;
    }

    @Override
    public Target sourceToTargetWithCustomLocale(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setI( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getI() ) );
        if ( source.getIi() != null ) {
            target.setIi( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getIi() ) );
        }
        target.setD( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getD() ) );
        if ( source.getDd() != null ) {
            target.setDd( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getDd() ) );
        }
        target.setF( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getF() ) );
        if ( source.getFf() != null ) {
            target.setFf( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getFf() ) );
        }
        target.setL( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getL() ) );
        if ( source.getLl() != null ) {
            target.setLl( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getLl() ) );
        }
        target.setB( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getB() ) );
        if ( source.getBb() != null ) {
            target.setBb( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getBb() ) );
        }
        target.setComplex1( new DecimalFormat( "##0.##E0", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getComplex1() ) );
        target.setComplex2( new DecimalFormat( "$#.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).format( source.getComplex2() ) );
        if ( source.getBigDecimal1() != null ) {
            target.setBigDecimal1( createDecimalFormat( "#0.#E0", java.util.Locale.forLanguageTag( "ru" ) ).format( source.getBigDecimal1() ) );
        }
        if ( source.getBigInteger1() != null ) {
            target.setBigInteger1( createDecimalFormat( "0.#############E0", Locale.forLanguageTag( "ru" ) ).format( source.getBigInteger1() ) );
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
                source.setI( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getI() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getIi() != null ) {
                source.setIi( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getIi() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getD() != null ) {
                source.setD( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getD() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getDd() != null ) {
                source.setDd( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getDd() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getF() != null ) {
                source.setF( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getF() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getFf() != null ) {
                source.setFf( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getFf() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getL() != null ) {
                source.setL( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getL() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getLl() != null ) {
                source.setLl( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getLl() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getB() != null ) {
                source.setB( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getB() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBb() != null ) {
                source.setBb( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getBb() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex1() != null ) {
                source.setComplex1( new DecimalFormat( "##0.##E0", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getComplex1() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex2() != null ) {
                source.setComplex2( new DecimalFormat( "$#.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( target.getComplex2() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigDecimal1() != null ) {
                source.setBigDecimal1( (BigDecimal) createDecimalFormat( "#0.#E0", java.util.Locale.getDefault() ).parse( target.getBigDecimal1() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigInteger1() != null ) {
                source.setBigInteger1( ( (BigDecimal) createDecimalFormat( "0.#############E0", Locale.getDefault() ).parse( target.getBigInteger1() ) ).toBigInteger() );
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
                source.setI( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getI() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getIi() != null ) {
                source.setIi( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getIi() ).intValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getD() != null ) {
                source.setD( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getD() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getDd() != null ) {
                source.setDd( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getDd() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getF() != null ) {
                source.setF( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getF() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getFf() != null ) {
                source.setFf( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getFf() ).floatValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getL() != null ) {
                source.setL( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getL() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getLl() != null ) {
                source.setLl( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getLl() ).longValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getB() != null ) {
                source.setB( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getB() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBb() != null ) {
                source.setBb( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getBb() ).byteValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex1() != null ) {
                source.setComplex1( new DecimalFormat( "##0.##E0", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getComplex1() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getComplex2() != null ) {
                source.setComplex2( new DecimalFormat( "$#.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.forLanguageTag( "ru " ) ) ).parse( target.getComplex2() ).doubleValue() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigDecimal1() != null ) {
                source.setBigDecimal1( (BigDecimal) createDecimalFormat( "#0.#E0", java.util.Locale.forLanguageTag( "ru" ) ).parse( target.getBigDecimal1() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( target.getBigInteger1() != null ) {
                source.setBigInteger1( ( (BigDecimal) createDecimalFormat( "0.#############E0", Locale.forLanguageTag( "ru" ) ).parse( target.getBigInteger1() ) ).toBigInteger() );
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
            list.add( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( float1 ) );
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
                list.add( new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( string ).floatValue() );
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
            list.add( createDecimalFormat( "#0.#E0", java.util.Locale.forLanguageTag( "fr" ) ).format( bigDecimal ) );
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
                list.add( (BigDecimal) createDecimalFormat( "#0.#E0", java.util.Locale.forLanguageTag( "fr" ) ).parse( string ) );
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
            String key = new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( entry.getKey() );
            String value = new DecimalFormat( "##", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).format( entry.getValue() );
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
            String key = createDecimalFormat( "#0.#E0", java.util.Locale.forLanguageTag( "fr" ) ).format( entry.getKey() );
            String value = createDecimalFormat( "0.#############E0", java.util.Locale.forLanguageTag( "fr" ) ).format( entry.getValue() );
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
                key = new DecimalFormat( "##.00", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( entry.getKey() ).floatValue();
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            Float value;
            try {
                value = new DecimalFormat( "##", java.text.DecimalFormatSymbols.getInstance( java.util.Locale.getDefault() ) ).parse( entry.getValue() ).floatValue();
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
                key = (BigDecimal) createDecimalFormat( "#0.#E0", java.util.Locale.forLanguageTag( "fr" ) ).parse( entry.getKey() );
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            BigDecimal value;
            try {
                value = (BigDecimal) createDecimalFormat( "0.#############E0", java.util.Locale.forLanguageTag( "fr" ) ).parse( entry.getValue() );
            }
            catch ( ParseException e ) {
                throw new RuntimeException( e );
            }
            map.put( key, value );
        }

        return map;
    }

    private DecimalFormat createDecimalFormat( String numberFormat, Locale locale ) {

        DecimalFormat df = new DecimalFormat( numberFormat, DecimalFormatSymbols.getInstance( locale ) );
        df.setParseBigDecimal( true );
        return df;
    }
}
