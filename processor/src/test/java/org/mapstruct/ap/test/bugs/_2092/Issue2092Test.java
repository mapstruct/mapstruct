/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2092;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("2092")
@WithClasses({
    Issue2092Mapper.class,
    BeanA.class, BeanB.class,
    TargetBeanA.class, TargetBeanB.class
})
//@Ignore
public class Issue2092Test {
    @Test
    public void shouldIgnoreUnmappedSourcePropertyForBeanA() {
        BeanA beanA = new BeanA();
        beanA.setA1( 1 );
        beanA.setA2( 2 );

        TargetBeanA targetBeanA = Issue2092Mapper.INSTANCE.mapA( beanA );

        assertThat( targetBeanA ).isNotNull();
        assertThat( targetBeanA.getAa1() ).isEqualTo( 1 );
    }

    @Test
    public void shouldIgnoreUnmappedSourcePropertyForBeanB() {
        BeanB beanB = new BeanB();
        beanB.setA1( 1 );
        beanB.setA2( 2 );
        beanB.setB1( 3 );
        beanB.setB2( 4 );

        TargetBeanB targetBeanB = Issue2092Mapper.INSTANCE.mapB( beanB );

        assertThat( targetBeanB ).isNotNull();
        assertThat( targetBeanB.getAa1() ).isEqualTo( 1 );
        assertThat( targetBeanB.getBb1() ).isEqualTo( 3 );
    }
}
