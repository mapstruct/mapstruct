/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2983;

import org.mapstruct.ap.test.annotatewith.CustomAnnotation;
import org.mapstruct.ap.test.annotatewith.CustomMethodOnlyAnnotation;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author orange add
 */

@WithClasses({Issue2983Mapper.class, Source.class, Target.class, OrderType.class, ExternalOrderType.class,
        CustomAnnotation.class, CustomMethodOnlyAnnotation.class})
public class Issue2983Test {

    @ProcessorTest
    void shouldContainAnnotationWithNormalTypeMappingMethod() throws NoSuchMethodException {
        Class<? extends Issue2983Mapper> instanceClass = Issue2983Mapper.INSTANCE.getClass();
        Method map = instanceClass.getMethod( "map", Source.class );
        Method toStringList = instanceClass.getMethod( "toStringList", List.class );
        Method toStringStream = instanceClass.getMethod( "toStringStream", Stream.class );
        Method longDateMapToStringStringMap = instanceClass.getMethod( "longDateMapToStringStringMap", Map.class );
        assertThat( map.getAnnotation( Deprecated.class ) ).isNotNull();
        assertThat( toStringList.getAnnotation( Deprecated.class ) ).isNotNull();
        assertThat( toStringStream.getAnnotation( Deprecated.class ) ).isNotNull();
        assertThat( longDateMapToStringStringMap.getAnnotation( Deprecated.class ) ).isNotNull();
        assertThat( longDateMapToStringStringMap.getAnnotation( CustomAnnotation.class ) ).isNotNull();
    }

    @ProcessorTest
    void shouldContainAnnotationWithValueMappingMethod() throws NoSuchMethodException {
        Class<? extends Issue2983Mapper> instanceClass = Issue2983Mapper.INSTANCE.getClass();
        Method orderTypeToExternalOrderType = instanceClass
                .getMethod( "orderTypeToExternalOrderType", OrderType.class );
        assertThat( orderTypeToExternalOrderType.getAnnotation( Deprecated.class ) ).isNotNull();
        assertThat( orderTypeToExternalOrderType.getAnnotation( CustomMethodOnlyAnnotation.class ) ).isNotNull();

    }
}
