/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3849;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3849")
@WithClasses({
    Parent.class,
    ParentDto.class,
    Child.class,
    ChildDto.class
})
public class Issue3849Test {

    @ProcessorOption(name = "mapstruct.disableLifecycleOverloadDeduplicateSelector", value = "true")
    @ProcessorTest()
    @WithClasses(DeduplicateForCompileArgsMapper.class)
    void lifecycleMappingOverloadSelectorDisableCompileArgs() {
        Child child = new Child();
        Parent parent = new Parent();

        DeduplicateForCompileArgsMapper.MappingContext mappingContext =
            new DeduplicateForCompileArgsMapper.MappingContext();
        ParentDto parentDto = DeduplicateForCompileArgsMapper.INSTANCE.mapParent( parent, mappingContext );
        assertThat( DeduplicateForCompileArgsMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingParentTargetInOtherClass",
                "beforeMappingParentTarget",
                "afterMappingParentTargetInOtherClass",
                "afterMappingParentTarget"
            );

        DeduplicateForCompileArgsMapper.INVOKED_METHODS.clear();

        ParentDto childDto = DeduplicateForCompileArgsMapper.INSTANCE.mapChild( child, mappingContext );

        assertThat( DeduplicateForCompileArgsMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingChildTargetInOtherClass",
                "beforeMappingChildTargetInOtherClass",
                "beforeMappingChildTarget",
                "beforeMappingChildTarget",
                "afterMappingChildTargetInOtherClass",
                "afterMappingChildTargetInOtherClass",
                "afterMappingChildTarget",
                "afterMappingChildTarget"
            );

        DeduplicateForCompileArgsMapper.INVOKED_METHODS.clear();
    }

    @ProcessorTest()
    @WithClasses( DeduplicateByTargetMapper.class )
    void lifecycleMappingOverloadByTarget() {
        Child child = new Child();
        Parent parent = new Parent();

        DeduplicateByTargetMapper.MappingContext mappingContext = new DeduplicateByTargetMapper.MappingContext();
        ParentDto parentDto = DeduplicateByTargetMapper.INSTANCE.mapParent( parent, mappingContext );
        assertThat( DeduplicateByTargetMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingParentTargetInOtherClass",
                "beforeMappingParentTarget",
                "afterMappingParentTargetInOtherClass",
                "afterMappingParentTarget"
            );

        DeduplicateByTargetMapper.INVOKED_METHODS.clear();

        ParentDto childDto = DeduplicateByTargetMapper.INSTANCE.mapChild( child, mappingContext );

        assertThat( DeduplicateByTargetMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingChildTargetInOtherClass",
                "beforeMappingChildTarget",
                "afterMappingChildTargetInOtherClass",
                "afterMappingChildTarget"
            );

        DeduplicateByTargetMapper.INVOKED_METHODS.clear();
    }

    @ProcessorTest
    @WithClasses( DeduplicateBySourceMapper.class )
    void lifecycleMappingOverloadBySource() {
        Child child = new Child();
        Parent parent = new Parent();

        DeduplicateBySourceMapper.MappingContext mappingContext = new DeduplicateBySourceMapper.MappingContext();
        ParentDto parentDto = DeduplicateBySourceMapper.INSTANCE.mapParent( parent, mappingContext );
        assertThat( DeduplicateBySourceMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingParentSourceInOtherClass",
                "beforeMappingParentSource",
                "afterMappingParentSourceInOtherClass",
                "afterMappingParentSource"
            );

        DeduplicateBySourceMapper.INVOKED_METHODS.clear();

        ParentDto childDto = DeduplicateBySourceMapper.INSTANCE.mapChild( child, mappingContext );

        assertThat( DeduplicateBySourceMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingChildSourceInOtherClass",
                "beforeMappingChildSource",
                "afterMappingChildSourceInOtherClass",
                "afterMappingChildSource"
            );

        DeduplicateBySourceMapper.INVOKED_METHODS.clear();
    }

    @ProcessorTest
    @WithClasses(DeduplicateGenericMapper.class)
    void lifecycleMappingOverloadForGeneric() {
        Child child = new Child();
        Parent parent = new Parent();

        ParentDto parentDto = DeduplicateGenericMapper.INSTANCE.mapParent( parent );
        assertThat( DeduplicateGenericMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingParentGeneric",
                "afterMappingParent"
            );

        DeduplicateGenericMapper.INVOKED_METHODS.clear();

        ParentDto childDto = DeduplicateGenericMapper.INSTANCE.mapChild( child );

        assertThat( DeduplicateGenericMapper.INVOKED_METHODS )
            .containsExactly(
                "beforeMappingChild",
                "afterMappingChild"
            );

        DeduplicateGenericMapper.INVOKED_METHODS.clear();
    }
}

