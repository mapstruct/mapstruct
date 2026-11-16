/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test field injection for component model spring.
 *
 * @author Filip Hrisafov
 * @author Jose Carlos Campanero Ortiz
 */
@WithClasses({
        CustomerSpringComponentQualifiedMapper.class,
        CustomerSpringControllerQualifiedMapper.class,
        CustomerSpringServiceQualifiedMapper.class,
        CustomerSpringRepositoryQualifiedMapper.class,
        CustomStereotype.class,
        CustomerSpringCustomStereotypeQualifiedMapper.class
})
@IssueKey( "1427" )
@WithSpring
public class SpringAnnotateWithMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveComponentAnnotatedQualifiedMapper() {

        // then
        generatedSource.forMapper( CustomerSpringComponentQualifiedMapper.class )
                .content()
                .contains( "@Component(value = \"AnnotateWithComponent\")" )
                .doesNotContain( "@Component" + System.lineSeparator() );

    }

    @ProcessorTest
    public void shouldHaveControllerAnnotatedQualifiedMapper() {

        // then
        generatedSource.forMapper( CustomerSpringControllerQualifiedMapper.class )
                .content()
                .contains( "@Controller(value = \"AnnotateWithController\")" )
                .doesNotContain( "@Component" );

    }

    @ProcessorTest
    public void shouldHaveServiceAnnotatedQualifiedMapper() {

        // then
        generatedSource.forMapper( CustomerSpringServiceQualifiedMapper.class )
                .content()
                .contains( "@Service(value = \"AnnotateWithService\")" )
                .doesNotContain( "@Component" );

    }

    @ProcessorTest
    public void shouldHaveRepositoryAnnotatedQualifiedMapper() {

        // then
        generatedSource.forMapper( CustomerSpringRepositoryQualifiedMapper.class )
                .content()
                .contains( "@Repository(value = \"AnnotateWithRepository\")" )
                .doesNotContain( "@Component" );

    }

    @ProcessorTest
    public void shouldHaveCustomStereotypeAnnotatedQualifiedMapper() {

        // then
        generatedSource.forMapper( CustomerSpringCustomStereotypeQualifiedMapper.class )
                .content()
                .contains( "@CustomStereotype(value = \"AnnotateWithCustomStereotype\")" )
                .doesNotContain( "@Component" );

    }

}
