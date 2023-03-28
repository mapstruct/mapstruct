/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.javadoc;

import org.assertj.core.api.AbstractCharSequenceAssert;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.annotatewith.AnnotateWithEnum;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Jose Carlos Campanero Ortiz
 */
@IssueKey("2987")
@WithClasses(AnnotateWithEnum.class)
public class JavadocTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( { JavadocAnnotatedWithValueMapper.class } )
    public void javadocAnnotatedWithValueMapper() {
        AbstractCharSequenceAssert<?, String> content = generatedSource
                .forMapper( JavadocAnnotatedWithValueMapper.class )
                .content();
        content
                       .contains( "This is the description" );
    }

    @ProcessorTest
    @WithClasses( { JavadocAnnotatedWithAttributesMapper.class } )
    public void javadocAnnotatedWithAttributesMapper() {
        AbstractCharSequenceAssert<?, String> content = generatedSource
                .forMapper( JavadocAnnotatedWithAttributesMapper.class )
                .content();
        content
                .contains( "This is the description" )
                .contains( "@author author1" )
                .contains( "@author author2" )
                .contains( "@deprecated Use {@link OtherMapper} instead" )
                .contains( "@since 0.1" );
    }
}
