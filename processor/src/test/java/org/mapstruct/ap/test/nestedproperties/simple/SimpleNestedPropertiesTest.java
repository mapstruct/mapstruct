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
package org.mapstruct.ap.test.nestedproperties.simple;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedproperties.simple._target.TargetObject;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceProps;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceRoot;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sebastian Hasait
 */
@WithClasses( { SourceRoot.class, SourceProps.class, TargetObject.class } )
@IssueKey( "407" )
@RunWith( AnnotationProcessorTestRunner.class )
public class SimpleNestedPropertiesTest {

    @Test
    @WithClasses( { SimpleMapper.class } )
    public void testNull() {
        TargetObject targetObject = SimpleMapper.MAPPER.toTargetObject( null );

        assertNull( targetObject );
    }

    @Test
    @WithClasses( { SimpleMapper.class } )
    public void testViaNull() {
        SourceRoot sourceRoot = new SourceRoot();
        // sourceRoot.getProps() is null

        TargetObject targetObject = SimpleMapper.MAPPER.toTargetObject( sourceRoot );

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

    @Test
    @WithClasses( { SimpleMapper.class } )
    public void testFilled() {
        SourceRoot sourceRoot = new SourceRoot();
        SourceProps sourceProps = new SourceProps();
        sourceRoot.setProps( sourceProps );
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
