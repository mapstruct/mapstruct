/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
