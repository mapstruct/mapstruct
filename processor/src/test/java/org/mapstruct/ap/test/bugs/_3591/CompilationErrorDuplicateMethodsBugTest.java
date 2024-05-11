package org.mapstruct.ap.test.bugs._3591;

import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3591")
public class CompilationErrorDuplicateMethodsBugTest {

    @ProcessorTest
    @WithClasses({
        BeanDto.class,
        Bean.class,
        BeanMapper.class
    })
    void mapNestedBeansWithMappingAnnotation() {
        Bean bean = testBean();

        BeanDto beanDto = BeanMapper.INSTANCE.map( bean, new BeanDto() );

        assertThat( beanDto.getValue() ).isEqualTo( "parent" );
        assertThat( beanDto.getBeans() ).isNotEmpty();
        assertThat( beanDto.getBeans().get( 0 ).getValue() ).isEqualTo( "child" );
    }

    @ProcessorTest
    @WithClasses({
        BeanDto.class,
        SpecialBeanDto.class,
        Bean.class,
        BeanMapperWithSelectionParameters.class
    })
    void shouldSelectMethodByQualifier() {
        Bean bean = testBean();
        BeanDto beanDto = new BeanDto();

        BeanMapperWithSelectionParameters.INSTANCE.mapWithQualifier( bean, beanDto );

        assertThat( beanDto.getBeans() ).isNotEmpty();
        assertThat( beanDto.getBeans().get( 0 ).getValue() ).isEqualTo( "withQualifier" );
    }

    @ProcessorTest
    @WithClasses({
        BeanDto.class,
        SpecialBeanDto.class,
        Bean.class,
        BeanMapperWithSelectionParameters.class
    })
    void shouldSelectMethodByName() {
        Bean bean = testBean();
        BeanDto beanDto = new BeanDto();

        BeanMapperWithSelectionParameters.INSTANCE.mapWithQualifiedByName( bean, beanDto );

        assertThat( beanDto.getBeans() ).isNotEmpty();
        assertThat( beanDto.getBeans().get( 0 ).getValue() ).isEqualTo( "QualifiedByName" );
    }

    @ProcessorTest
    @WithClasses({
        BeanDto.class,
        SpecialBeanDto.class,
        Bean.class,
        BeanMapperWithSelectionParameters.class
    })
    void shouldMapChildrenBeansToSpecialBeans() {
        Bean bean = testBean();
        BeanDto beanDto = new BeanDto();

        BeanMapperWithSelectionParameters.INSTANCE.mapToResultType( bean, beanDto );

        assertThat( beanDto.getBeans() ).isNotEmpty();
        assertThat( beanDto.getBeans().get( 0 ).getValue() ).isEqualTo( "child" );
        assertThat( beanDto.getBeans().get( 0 ) ).isInstanceOf( SpecialBeanDto.class );
    }

    @ProcessorTest
    @WithClasses({
        BeanDto.class,
        SpecialBeanDto.class,
        Bean.class,
        BeanMapperWithSelectionParameters.class
    })
    void shouldMapWithoutAnySelection() {
        Bean bean = testBean();
        BeanDto beanDto = new BeanDto();

        BeanMapperWithSelectionParameters.INSTANCE.map( bean, beanDto );

        assertThat( beanDto.getBeans() ).isNotEmpty();
        assertThat( beanDto.getBeans().get( 0 ).getValue() ).isEqualTo( "child" );
        assertThat( beanDto.getBeans().get( 0 ) ).isInstanceOf( BeanDto.class );
    }

    Bean testBean() {
        Bean bean = new Bean( "parent" );
        Bean child = new Bean( "child" );
        bean.setBeans( List.of( child ) );
        return bean;
    }

}
