/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses({
    ByteSource.class,
    ByteTarget.class,
    ByteWrapperSource.class,
    ByteWrapperTarget.class,
    ShortSource.class,
    ShortTarget.class,
    ShortWrapperSource.class,
    ShortWrapperTarget.class,
    IntSource.class,
    IntTarget.class,
    IntWrapperSource.class,
    IntWrapperTarget.class,
    LongSource.class,
    LongTarget.class,
    LongWrapperSource.class,
    LongWrapperTarget.class,
    FloatSource.class,
    FloatTarget.class,
    FloatWrapperSource.class,
    FloatWrapperTarget.class,
    DoubleSource.class,
    DoubleTarget.class,
    DoubleWrapperSource.class,
    DoubleWrapperTarget.class,
    SourceTargetMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NumberConversionTest {

    @Test
    public void shouldApplyByteConversions() {
        ByteSource source = new ByteSource();
        source.setB( (byte) 1 );
        source.setBb( (byte) 2 );
        source.setS( (byte) 3 );
        source.setSs( (byte) 4 );
        source.setI( (byte) 5 );
        source.setIi( (byte) 6 );
        source.setL( (byte) 7 );
        source.setLl( (byte) 8 );
        source.setF( (byte) 9 );
        source.setFf( (byte) 10 );
        source.setD( (byte) 11 );
        source.setDd( (byte) 12 );

        ByteTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyByteWrapperConversions() {
        ByteWrapperSource source = new ByteWrapperSource();
        source.setB( (byte) 1 );
        source.setBb( (byte) 2 );
        source.setS( (byte) 3 );
        source.setSs( (byte) 4 );
        source.setI( (byte) 5 );
        source.setIi( (byte) 6 );
        source.setL( (byte) 7 );
        source.setLl( (byte) 8 );
        source.setF( (byte) 9 );
        source.setFf( (byte) 10 );
        source.setD( (byte) 11 );
        source.setDd( (byte) 12 );

        ByteWrapperTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyShortConversions() {
        ShortSource source = new ShortSource();
        source.setB( (short) 1 );
        source.setBb( (short) 2 );
        source.setS( (short) 3 );
        source.setSs( (short) 4 );
        source.setI( (short) 5 );
        source.setIi( (short) 6 );
        source.setL( (short) 7 );
        source.setLl( (short) 8 );
        source.setF( (short) 9 );
        source.setFf( (short) 10 );
        source.setD( (short) 11 );
        source.setDd( (short) 12 );

        ShortTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyShortWrapperConversions() {
        ShortWrapperSource source = new ShortWrapperSource();
        source.setB( (short) 1 );
        source.setBb( (short) 2 );
        source.setS( (short) 3 );
        source.setSs( (short) 4 );
        source.setI( (short) 5 );
        source.setIi( (short) 6 );
        source.setL( (short) 7 );
        source.setLl( (short) 8 );
        source.setF( (short) 9 );
        source.setFf( (short) 10 );
        source.setD( (short) 11 );
        source.setDd( (short) 12 );

        ShortWrapperTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyIntConversions() {
        IntSource source = new IntSource();
        source.setB( 1 );
        source.setBb( 2 );
        source.setS( 3 );
        source.setSs( 4 );
        source.setI( 5 );
        source.setIi( 6 );
        source.setL( 7 );
        source.setLl( 8 );
        source.setF( 9 );
        source.setFf( 10 );
        source.setD( 11 );
        source.setDd( 12 );

        IntTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyIntWrapperConversions() {
        IntWrapperSource source = new IntWrapperSource();
        source.setB( 1 );
        source.setBb( 2 );
        source.setS( 3 );
        source.setSs( 4 );
        source.setI( 5 );
        source.setIi( 6 );
        source.setL( 7 );
        source.setLl( 8 );
        source.setF( 9 );
        source.setFf( 10 );
        source.setD( 11 );
        source.setDd( 12 );

        IntWrapperTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyLongConversions() {
        LongSource source = new LongSource();
        source.setB( 1 );
        source.setBb( 2 );
        source.setS( 3 );
        source.setSs( 4 );
        source.setI( 5 );
        source.setIi( 6 );
        source.setL( 7 );
        source.setLl( 8 );
        source.setF( 9 );
        source.setFf( 10 );
        source.setD( 11 );
        source.setDd( 12 );

        LongTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyLongWrapperConversions() {
        LongWrapperSource source = new LongWrapperSource();
        source.setB( (long) 1 );
        source.setBb( (long) 2 );
        source.setS( (long) 3 );
        source.setSs( (long) 4 );
        source.setI( (long) 5 );
        source.setIi( (long) 6 );
        source.setL( (long) 7 );
        source.setLl( (long) 8 );
        source.setF( (long) 9 );
        source.setFf( (long) 10 );
        source.setD( (long) 11 );
        source.setDd( (long) 12 );

        LongWrapperTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyFloatConversions() {
        FloatSource source = new FloatSource();
        source.setB( 1 );
        source.setBb( 2 );
        source.setS( 3 );
        source.setSs( 4 );
        source.setI( 5 );
        source.setIi( 6 );
        source.setL( 7 );
        source.setLl( 8 );
        source.setF( 9 );
        source.setFf( 10 );
        source.setD( 11 );
        source.setDd( 12 );

        FloatTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyFloatWrapperConversions() {
        FloatWrapperSource source = new FloatWrapperSource();
        source.setB( 1f );
        source.setBb( 2f );
        source.setS( 3f );
        source.setSs( 4f );
        source.setI( 5f );
        source.setIi( 6f );
        source.setL( 7f );
        source.setLl( 8f );
        source.setF( 9f );
        source.setFf( 10f );
        source.setD( 11f );
        source.setDd( 12f );

        FloatWrapperTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyDoubleConversions() {
        DoubleSource source = new DoubleSource();
        source.setB( 1 );
        source.setBb( 2 );
        source.setS( 3 );
        source.setSs( 4 );
        source.setI( 5 );
        source.setIi( 6 );
        source.setL( 7 );
        source.setLl( 8 );
        source.setF( 9 );
        source.setFf( 10 );
        source.setD( 11 );
        source.setDd( 12 );

        DoubleTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    public void shouldApplyDoubleWrapperConversions() {
        DoubleWrapperSource source = new DoubleWrapperSource();
        source.setB( 1d );
        source.setBb( 2d );
        source.setS( 3d );
        source.setSs( 4d );
        source.setI( 5d );
        source.setIi( 6d );
        source.setL( 7d );
        source.setLl( 8d );
        source.setF( 9d );
        source.setFf( 10d );
        source.setD( 11d );
        source.setDd( 12d );

        DoubleWrapperTarget target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @Test
    @IssueKey( "229" )
    public void wrapperToPrimitveIsNullSafe() {
        assertThat( SourceTargetMapper.INSTANCE.sourceToTarget( new ByteWrapperSource() ) ).isNotNull();
        assertThat( SourceTargetMapper.INSTANCE.sourceToTarget( new DoubleWrapperSource() ) ).isNotNull();
        assertThat( SourceTargetMapper.INSTANCE.sourceToTarget( new ShortWrapperSource() ) ).isNotNull();
        assertThat( SourceTargetMapper.INSTANCE.sourceToTarget( new IntWrapperSource() ) ).isNotNull();
        assertThat( SourceTargetMapper.INSTANCE.sourceToTarget( new FloatWrapperSource() ) ).isNotNull();
        assertThat( SourceTargetMapper.INSTANCE.sourceToTarget( new LongWrapperSource() ) ).isNotNull();
    }
}
