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
package org.mapstruct.ap.test.accessibility;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isProtected;
import static java.lang.reflect.Modifier.isPublic;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for different accessibility modifiers
 *
 * @author Andreas Gudian
 */
@WithClasses({ Source.class, Target.class, DefaultSourceTargetMapperAbstr.class, DefaultSourceTargetMapperIfc.class })
@RunWith( AnnotationProcessorTestRunner.class )
public class AccessibilityTest {

    @Test
    @IssueKey("103")
    public void testGeneratedModifiersFromAbstractClassAreCorrect() throws Exception {
        Class<?> defaultFromAbstract = loadForMapper( DefaultSourceTargetMapperAbstr.class );

        assertTrue( isDefault( defaultFromAbstract.getModifiers() ) );

        assertTrue( isPublic( modifiersFor( defaultFromAbstract, "publicSourceToTarget" ) ) );
        assertTrue( isProtected( modifiersFor( defaultFromAbstract, "protectedSourceToTarget" ) ) );
        assertTrue( isDefault( modifiersFor( defaultFromAbstract, "defaultSourceToTarget" ) ) );
    }

    @Test
    @IssueKey("103")
    public void testGeneratedModifiersFromInterfaceAreCorrect() throws Exception {
        Class<?> defaultFromIfc = loadForMapper( DefaultSourceTargetMapperIfc.class );

        assertTrue( isDefault( defaultFromIfc.getModifiers() ) );

        assertTrue( isPublic( modifiersFor( defaultFromIfc, "implicitlyPublicSoureToTarget" ) ) );
    }

    private static Class<?> loadForMapper(Class<?> mapper) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass( mapper.getName() + "Impl" );
    }

    private int modifiersFor(Class<?> clazz, String method) throws Exception {
        return clazz.getDeclaredMethod( method, Source.class ).getModifiers();
    }

    private static boolean isDefault(int modifiers) {
        return !isPublic( modifiers ) && !isProtected( modifiers ) && !isPrivate( modifiers );
    }
}
