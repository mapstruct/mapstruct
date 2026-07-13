/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping.containertype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@code nullValueIterablePropertyMappingStrategy} and {@code nullValueMapPropertyMappingStrategy}, which
 * let Iterable- and Map-typed properties use a different null-handling strategy than the general
 * {@code nullValuePropertyMappingStrategy} applied to the rest of the bean.
 */
@IssueKey("3387")
@WithClasses({
    Address.class,
    AddressDTO.class,
    Bean.class,
    BeanDTO.class
})
class NullValueContainerPropertyMappingTest {

    @ProcessorTest
    @WithClasses(BeanMapperOnMapper.class)
    void containerStrategyOnMapperOverridesGeneralStrategy() {
        assertContainerStrategyAppliedButNotToPlainBean( BeanMapperOnMapper.INSTANCE::update );
    }

    @ProcessorTest
    @WithClasses(BeanMapperPrecedence.class)
    void containerStrategyOnMappingOverridesMapperLevelContainerStrategy() {
        Bean source = new Bean();

        BeanDTO target = new BeanDTO();
        target.setId( "original id" );
        List<String> originalTags = List.of( "a", "b" );
        target.setTags( originalTags );
        Map<String, String> originalMeta = new HashMap<>();
        originalMeta.put( "k", "v" );
        target.setMeta( originalMeta );
        Address address = new Address();
        address.setCity( "Almaty" );
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity( "Almaty" );
        target.setAddress( addressDTO );

        BeanMapperPrecedence.INSTANCE.update( source, target );

        // tags has its own nullValueIterablePropertyMappingStrategy = IGNORE on @Mapping,
        // which takes precedence over the mapper-level SET_TO_DEFAULT -> left untouched
        assertThat( target.getTags() ).isSameAs( originalTags );

        // meta has no @Mapping-level override -> falls back to the mapper-level
        // nullValueMapPropertyMappingStrategy = SET_TO_DEFAULT
        assertThat( target.getMeta() ).isNotNull().isEmpty();

        // id has no container-specific strategy applicable (it's not a container) and no strategy
        // configured at all -> default SET_TO_NULL
        assertThat( target.getId() ).isNull();
    }

    private void assertContainerStrategyAppliedButNotToPlainBean(BiConsumer<Bean, BeanDTO> mapper) {
        Bean source = new Bean();

        BeanDTO target = new BeanDTO();
        target.setId( "original id" );
        target.setTags( List.of( "a", "b" ) );
        Map<String, String> meta = new HashMap<>();
        meta.put( "k", "v" );
        target.setMeta( meta );
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity( "Almaty" );
        target.setAddress( addressDTO );

        mapper.accept( source, target );

        // general strategy (SET_TO_NULL) applies to plain properties
        assertThat( target.getId() ).isNull();
        assertThat( target.getAddress() ).isNull();

        // container-specific strategy (SET_TO_DEFAULT) applies to Iterable/Map properties
        assertThat( target.getTags() ).isNotNull().isEmpty();
        assertThat( target.getMeta() ).isNotNull().isEmpty();
    }
}
