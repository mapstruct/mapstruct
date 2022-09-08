/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3015;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.ap.test.annotatewith.CustomMethodOnlyAnnotation;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author orange add
 */
@WithClasses( {Issue3015Mapper.class, CustomMethodOnlyAnnotation.class } )
public class Issue3015Test {

    @ProcessorTest
    public void noNeedPassAnnotationToForgeMethod() {
        Issue3015Mapper mapper = Mappers.getMapper( Issue3015Mapper.class );
        Method[] declaredMethods = mapper.getClass().getDeclaredMethods();
        List<Method> annotationMethods = Arrays.stream( declaredMethods )
            .filter( method -> method.getAnnotation( CustomMethodOnlyAnnotation.class ) != null )
            .collect( Collectors.toList() );
        assertThat( annotationMethods.size() ).isEqualTo( 1 );
    }
}
