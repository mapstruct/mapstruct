/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.classaccessibility;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isProtected;
import static java.lang.reflect.Modifier.isPublic;
import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Source.class,
    Target.class,
    PublicAbstractionMapper.class,
    PackageAbstractionMapper.class,
    ForcedPublicMapper.class,
    ForcedDefaultMapper.class
})
public class ClassAccessibilityTest {

    @ProcessorTest
    public void shouldCreateModifierAccordingToAnnotation() throws Exception {
        Class<?> publicLike = loadForMapper( PublicAbstractionMapper.class );
        assertThat( isPublic( publicLike.getModifiers() ) ).isTrue();

        Class<?> packageLike = loadForMapper( PackageAbstractionMapper.class );
        assertThat( isDefault( packageLike.getModifiers() ) ).isTrue();

        Class<?> forcedPublic = loadForMapper( ForcedPublicMapper.class );
        assertThat( isPublic( forcedPublic.getModifiers() ) ).isTrue();

        Class<?> forcedDefault = loadForMapper( ForcedDefaultMapper.class );
        assertThat( isDefault( forcedDefault.getModifiers() ) ).isTrue();
    }

    private static Class<?> loadForMapper(Class<?> mapper) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass( mapper.getName() + "Impl" );
    }

    private static boolean isDefault(int modifiers) {
        return !isPublic( modifiers ) && !isProtected( modifiers ) && !isPrivate( modifiers );
    }
}

