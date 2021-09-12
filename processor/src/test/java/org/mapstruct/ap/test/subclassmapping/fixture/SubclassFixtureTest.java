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

@WithClasses( { AbstractParentSource.class, AbstractParentTarget.class,
    InterfaceParentSource.class, InterfaceParentTarget.class,
    ImplementedParentSource.class, ImplementedParentTarget.class,
    SubSource.class, SubSourceOther.class, SubTarget.class, SubTargetOther.class,
    SubclassAbstractMapper.class, SubclassImplementedMapper.class, SubclassInterfaceMapper.class } )
public class SubclassFixtureTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void subclassInterfaceParentFixtureTest() {
        generatedSource.addComparisonToFixtureFor( SubclassInterfaceMapper.class );
    }

    @ProcessorTest
    void subclassAbstractParentFixtureTest() {
        generatedSource.addComparisonToFixtureFor( SubclassAbstractMapper.class );
    }

    @ProcessorTest
    void subclassImplementationParentFixtureTest() {
        generatedSource.addComparisonToFixtureFor( SubclassImplementedMapper.class );
    }
}
