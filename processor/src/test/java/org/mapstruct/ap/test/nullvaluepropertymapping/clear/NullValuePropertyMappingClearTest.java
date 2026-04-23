/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping.clear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Bean.class,
    BeanDTO.class,
    NestedBean.class,
})
class NullValuePropertyMappingClearTest {

    @ProcessorTest
    @WithClasses(BeanMapper.class)
    void generatedMapperMethodsShouldCallClear() {
        BeanDTO target = new BeanDTO();
        target.setId( "target" );
        List<String> targetList = new ArrayList<>();
        targetList.add( "a" );
        targetList.add( "b" );
        target.setList( targetList );
        Map<String, String> targetMap = new HashMap<>();
        targetMap.put( "a", "aValue" );
        target.setMap( targetMap );
        Bean source = new Bean();

        BeanMapper.INSTANCE.map( source, target );
        assertThat( target.getId() ).isNull();
        assertThat( target.getList() )
            .isSameAs( targetList )
            .isEmpty();
        assertThat( target.getMap() )
            .isSameAs( targetMap )
            .isEmpty();

        NestedBean nestedBean = new NestedBean();
        nestedBean.setBean( source );
        targetList.add( "a" );
        targetList.add( "b" );
        targetMap.put( "a", "aValue" );
        target.setId( "target" );
        BeanMapper.INSTANCE.map( nestedBean, target );
        assertThat( target.getId() ).isNull();
        assertThat( target.getList() )
            .isSameAs( targetList )
            .isEmpty();
        assertThat( target.getMap() )
            .isSameAs( targetMap )
            .isEmpty();

        targetList.add( "a" );
        targetList.add( "b" );
        targetMap.put( "a", "aValue" );
        target.setId( "target" );
        BeanMapper.INSTANCE.mapWithBeanMapping( source, target );
        assertThat( target.getId() ).isNull();
        assertThat( target.getList() )
            .isSameAs( targetList )
            .isEmpty();
        assertThat( target.getMap() )
            .isSameAs( targetMap )
            .isEmpty();
    }

    @ProcessorTest
    @WithClasses(BeanMapperWithStrategyOnMapper.class)
    void generatedMapperWithMappingDefinedInConfigMethodsShouldCallClear() {
        BeanDTO target = new BeanDTO();
        target.setId( "target" );
        List<String> targetList = new ArrayList<>();
        targetList.add( "a" );
        targetList.add( "b" );
        target.setList( targetList );
        Map<String, String> targetMap = new HashMap<>();
        targetMap.put( "a", "aValue" );
        target.setMap( targetMap );
        Bean source = new Bean();

        BeanMapperWithStrategyOnMapper.INSTANCE.map( source, target );
        assertThat( target.getId() ).isNull();
        assertThat( target.getList() )
            .isSameAs( targetList )
            .isEmpty();
        assertThat( target.getMap() )
            .isSameAs( targetMap )
            .isEmpty();

        NestedBean nestedBean = new NestedBean();
        nestedBean.setBean( source );
        targetList.add( "a" );
        targetList.add( "b" );
        targetMap.put( "a", "aValue" );
        target.setId( "target" );
        BeanMapperWithStrategyOnMapper.INSTANCE.map( nestedBean, target );
        assertThat( target.getId() ).isNull();
        assertThat( target.getList() )
            .isSameAs( targetList )
            .isEmpty();
        assertThat( target.getMap() )
            .isSameAs( targetMap )
            .isEmpty();
    }

}
