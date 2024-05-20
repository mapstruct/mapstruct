package org.mapstruct.ap.test.bugs._3591;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ContainerBean.class,
        ContainerBeanDto.class,
        ContainerBeanMapper.class,
    })
    void shouldMapNestedMapAndStream() {
        ContainerBean containerBean = testContainerBean();

        ContainerBeanDto dto = ContainerBeanMapper.INSTANCE.mapWithMapMapping( containerBean, new ContainerBeanDto() );

        assertThat( dto.getBeanMap() ).isNotEmpty();
        assertThat( dto.getBeanMap().get( "child" ).getValue() ).isEqualTo( "mapChild" );

        List<ContainerBeanDto> fromStream = dto.getBeanStream().collect( Collectors.toList() );
        assertThat( fromStream ).isNotEmpty();
        assertThat( fromStream.get( 0 ).getValue() ).isEqualTo( "streamChild" );
    }

    static ContainerBean testContainerBean() {
        ContainerBean containerBean = new ContainerBean( "parent" );

        Map<String, ContainerBean> beanMap = new HashMap<>();
        beanMap.put( "child", new ContainerBean( "mapChild" ) );
        containerBean.setBeanMap( beanMap );

        Stream<ContainerBean> streamChild = Stream.of( new ContainerBean( "streamChild" ) );
        containerBean.setBeanStream( streamChild );

        return containerBean;
    }

    static Bean testBean() {
        Bean bean = new Bean( "parent" );
        Bean child = new Bean( "child" );
        List<Bean> childList = new ArrayList<>();
        childList.add( child );
        bean.setBeans( childList );

        return bean;
    }

}
