/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3591;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3591")
class Issue3591Test {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        BeanDto.class,
        Bean.class,
        BeanMapper.class
    })
    void mapNestedBeansWithMappingAnnotation() {
        Bean bean = new Bean( "parent" );
        Bean child = new Bean( "child" );
        bean.setBeans( Collections.singletonList( child ) );

        BeanDto beanDto = BeanMapper.INSTANCE.map( bean, new BeanDto() );

        assertThat( beanDto ).isNotNull();
        assertThat( beanDto.getValue() ).isEqualTo( "parent" );
        assertThat( beanDto.getBeans() )
            .extracting( BeanDto::getValue )
            .containsExactly( "child" );
    }

    @ProcessorTest
    @WithClasses({
        ContainerBean.class,
        ContainerBeanDto.class,
        ContainerBeanMapper.class,
    })
    void shouldMapNestedMapAndStream() {
        generatedSource.addComparisonToFixtureFor( ContainerBeanMapper.class );

        ContainerBean containerBean = new ContainerBean( "parent" );
        Map<String, ContainerBean> beanMap = new HashMap<>();
        beanMap.put( "child", new ContainerBean( "mapChild" ) );
        containerBean.setBeanMap( beanMap );

        Stream<ContainerBean> streamChild = Stream.of( new ContainerBean( "streamChild" ) );
        containerBean.setBeanStream( streamChild );

        ContainerBeanDto dto = ContainerBeanMapper.INSTANCE.mapWithMapMapping( containerBean, new ContainerBeanDto() );

        assertThat( dto ).isNotNull();

        assertThat( dto.getBeanMap() )
            .extractingByKey( "child" )
            .extracting( ContainerBeanDto::getValue )
            .isEqualTo( "mapChild" );

        assertThat( dto.getBeanStream() )
            .singleElement()
            .extracting( ContainerBeanDto::getValue )
            .isEqualTo( "streamChild" );
    }

}
