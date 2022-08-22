/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

import org.junit.jupiter.api.extension.RegisterExtension;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses({
    AbstractParentSource.class,
    AbstractParentTarget.class,
    ImplementedParentSource.class,
    ImplementedParentTarget.class,
    InterfaceParentSource.class,
    InterfaceParentTarget.class,
    SubSource.class,
    SubSourceOther.class,
    SubTarget.class,
    SubTargetOther.class,
})
public class SubclassFixtureTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( {
        SubclassInterfaceMapper.class,
    } )
    void subclassInterfaceParentFixture() {
        generatedSource.addComparisonToFixtureFor( SubclassInterfaceMapper.class );
    }

    @ProcessorTest
    @WithClasses( {
        SubSourceOverride.class,
        SubSourceSeparate.class,
        SubTargetSeparate.class,
        SubclassAbstractMapper.class,
    } )
    void subclassAbstractParentFixture() {
        generatedSource.addComparisonToFixtureFor( SubclassAbstractMapper.class );
    }

    @ProcessorTest
    @WithClasses( {
        SubclassImplementedMapper.class,
    } )
    void subclassImplementationParentFixture() {
        generatedSource.addComparisonToFixtureFor( SubclassImplementedMapper.class );
    }
}
