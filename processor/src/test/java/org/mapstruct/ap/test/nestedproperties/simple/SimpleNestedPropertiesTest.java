/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedproperties.simple;

import org.mapstruct.ap.test.nestedproperties.simple._target.TargetObject;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceProps;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceRoot;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sebastian Hasait
 */
@WithClasses({ SourceRoot.class, SourceProps.class, TargetObject.class })
@IssueKey("407")
public class SimpleNestedPropertiesTest {

    @ProcessorTest
    @WithClasses({ SimpleMapper.class })
    public void testNull() {
        TargetObject targetObject = SimpleMapper.MAPPER.toTargetObject( null );

        assertThat( targetObject ).isNotNull();
    }

    @ProcessorTest
    @WithClasses({ SimpleMapper.class })
    public void testViaNull() {
        SourceRoot sourceRoot = new SourceRoot();
        // sourceRoot.getProps() is null

        TargetObject targetObject = SimpleMapper.MAPPER.toTargetObject( sourceRoot );

        assertEquals( 0L, targetObject.getPublicLongValue() );
        assertEquals( 0L, targetObject.getLongValue() );
        assertEquals( 0, targetObject.getIntValue() );
        assertEquals( 0.0, targetObject.getDoubleValue(), 0.01 );
        assertEquals( 0.0f, targetObject.getFloatValue(), 0.01f );
        assertEquals( 0, targetObject.getShortValue() );
        assertEquals( 0, targetObject.getCharValue() );
        assertEquals( 0, targetObject.getByteValue() );
        assertFalse( targetObject.isBooleanValue() );
        assertNull( targetObject.getByteArray() );
        assertNull( targetObject.getStringValue() );
    }

    @ProcessorTest
    @WithClasses({ SimpleMapper.class })
    public void testFilled() {
        SourceRoot sourceRoot = new SourceRoot();
        SourceProps sourceProps = new SourceProps();
        sourceRoot.setProps( sourceProps );
        sourceProps.publicLongValue = Long.MAX_VALUE;
        sourceProps.setLongValue( Long.MAX_VALUE );
        sourceProps.setIntValue( Integer.MAX_VALUE );
        sourceProps.setDoubleValue( Double.MAX_VALUE );
        sourceProps.setFloatValue( Float.MAX_VALUE );
        sourceProps.setShortValue( Short.MAX_VALUE );
        sourceProps.setCharValue( Character.MAX_VALUE );
        sourceProps.setByteValue( Byte.MAX_VALUE );
        sourceProps.setBooleanValue( true );
        String stringValue = "lorem ipsum";
        sourceProps.setByteArray( stringValue.getBytes() );
        sourceProps.setStringValue( stringValue );

        TargetObject targetObject = SimpleMapper.MAPPER.toTargetObject( sourceRoot );

        assertEquals( Long.MAX_VALUE, targetObject.getPublicLongValue() );
        assertEquals( Long.MAX_VALUE, targetObject.getLongValue() );
        assertEquals( Integer.MAX_VALUE, targetObject.getIntValue() );
        assertEquals( Double.MAX_VALUE, targetObject.getDoubleValue(), 0.01 );
        assertEquals( Float.MAX_VALUE, targetObject.getFloatValue(), 0.01f );
        assertEquals( Short.MAX_VALUE, targetObject.getShortValue() );
        assertEquals( Character.MAX_VALUE, targetObject.getCharValue() );
        assertEquals( Byte.MAX_VALUE, targetObject.getByteValue() );
        assertTrue( targetObject.isBooleanValue() );
        assertArrayEquals( stringValue.getBytes(), targetObject.getByteArray() );
        assertEquals( stringValue, targetObject.getStringValue() );
    }

}
