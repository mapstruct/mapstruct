/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2436;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for applying defaultValue / defaultExpression when a source parameter is null.
 */
@IssueKey("2436")
@WithClasses({
    Source1.class,
    Source2.class,
    Target.class,
    ConstructorTarget.class
})
class Issue2436Test {

    @ProcessorTest
    @WithClasses(Issue2436Mapper.class)
    void multiSourceWithFirstSourceNull() {
        Source2 source2 = new Source2();
        source2.setProperty2( "value2" );

        Target target = Issue2436Mapper.INSTANCE.map( null, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "default1" );
        assertThat( target.getProperty2() ).isEqualTo( "value2" );
        assertThat( target.getProperty3() ).isNull();
    }

    @ProcessorTest
    @WithClasses(Issue2436Mapper.class)
    void multiSourceWithSecondSourceNull() {
        Source1 source1 = new Source1();
        source1.setProperty1( "value1" );

        Target target = Issue2436Mapper.INSTANCE.map( source1, null );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "value1" );
        assertThat( target.getProperty2() ).isEqualTo( "default2" );
        assertThat( target.getProperty3() ).isEqualTo( "value1" );
    }

    @ProcessorTest
    @WithClasses(Issue2436Mapper.class)
    void multiSourceWithPropertyNullKeepsPropertyLevelDefault() {
        Source1 source1 = new Source1();
        Source2 source2 = new Source2();

        Target target = Issue2436Mapper.INSTANCE.map( source1, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "default1" );
        assertThat( target.getProperty2() ).isEqualTo( "default2" );
        assertThat( target.getProperty3() ).isNull();
    }

    @ProcessorTest
    @WithClasses(Issue2436Mapper.class)
    void multiSourceWithValuesPresent() {
        Source1 source1 = new Source1();
        source1.setProperty1( "value1" );
        Source2 source2 = new Source2();
        source2.setProperty2( "value2" );

        Target target = Issue2436Mapper.INSTANCE.map( source1, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "value1" );
        assertThat( target.getProperty2() ).isEqualTo( "value2" );
        assertThat( target.getProperty3() ).isEqualTo( "value1" );
    }

    @ProcessorTest
    @WithClasses(Issue2436ConstructorMapper.class)
    void constructorMultiSourceWithFirstSourceNull() {
        Source2 source2 = new Source2();
        source2.setProperty2( "value2" );

        ConstructorTarget target = Issue2436ConstructorMapper.INSTANCE.map( null, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "default1" );
        assertThat( target.getProperty2() ).isEqualTo( "value2" );
    }

    @ProcessorTest
    @WithClasses(Issue2436ReturnDefaultMapper.class)
    void singleSourceReturnDefaultWithNullSource() {
        Target target = Issue2436ReturnDefaultMapper.INSTANCE.map( null );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "default1" );
        assertThat( target.getProperty2() ).isNull();
    }

    @ProcessorTest
    @WithClasses(Issue2436ConditionMapper.class)
    void conditionFalseDoesNotApplyDefault() {
        Source1 source1 = new Source1();
        // property1 is null, so @SourceParameterCondition returns false
        Source2 source2 = new Source2();
        source2.setProperty2( "value2" );

        Target target = Issue2436ConditionMapper.INSTANCE.map( source1, source2 );

        assertThat( target ).isNotNull();
        // condition false → source1 mappings are skipped entirely (no default applied)
        assertThat( target.getProperty1() ).isNull();
        assertThat( target.getProperty2() ).isEqualTo( "value2" );
    }

    @ProcessorTest
    @WithClasses(Issue2436ConditionMapper.class)
    void conditionTrueAppliesSourceValue() {
        Source1 source1 = new Source1();
        source1.setProperty1( "value1" );
        // property1 is non-null, so @SourceParameterCondition returns true
        Source2 source2 = new Source2();
        source2.setProperty2( "value2" );

        Target target = Issue2436ConditionMapper.INSTANCE.map( source1, source2 );

        assertThat( target ).isNotNull();
        // condition true → source1 mappings proceed normally, actual value wins over default
        assertThat( target.getProperty1() ).isEqualTo( "value1" );
        assertThat( target.getProperty2() ).isEqualTo( "value2" );
    }

    @ProcessorTest
    @WithClasses(Issue2436ConstructorMapper.class)
    void constructorMultiSourceWithSecondSourceNull() {
        Source1 source1 = new Source1();
        source1.setProperty1( "value1" );

        ConstructorTarget target = Issue2436ConstructorMapper.INSTANCE.map( source1, null );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "value1" );
        assertThat( target.getProperty2() ).isEqualTo( "default2" );
    }

    @ProcessorTest
    @WithClasses(Issue2436ConstructorReturnDefaultMapper.class)
    void singleSourceConstructorReturnDefaultWithNullSource() {
        ConstructorTarget target = Issue2436ConstructorReturnDefaultMapper.INSTANCE.map( null );

        assertThat( target ).isNotNull();
        assertThat( target.getProperty1() ).isEqualTo( "default1" );
        assertThat( target.getProperty2() ).isEqualTo( "default2" );
    }

    @ProcessorTest
    @WithClasses(Issue2436Mapper.class)
    void allSourcesNullReturnsNull() {
        Target target = Issue2436Mapper.INSTANCE.map( null, null );

        assertThat( target ).isNull();
    }
}
