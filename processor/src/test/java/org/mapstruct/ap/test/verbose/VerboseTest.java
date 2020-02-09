/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedNote;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.Compiler;

@IssueKey("37")
public class VerboseTest {

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses({ CreateBeanMapping.class, CreateBeanMappingConfig.class })
    @ExpectedNote("^MapStruct: Using accessor naming strategy:.*DefaultAccessorNamingStrategy.*$")
    @ExpectedNote("^MapStruct: Using builder provider:.*DefaultBuilderProvider.*$")
    @ExpectedNote("^ MapStruct: processing:.*.CreateBeanMapping.*$")
    @ExpectedNote("^ MapStruct: applying mapper configuration:.*CreateBeanMappingConfig.*$")
    public void testGeneralMessages() {
    }

    @ProcessorTest(Compiler.JDK)
    @WithServiceImplementation(provides = BuilderProvider.class, value = ImmutablesBuilderProvider.class)
    @WithServiceImplementation(provides = AccessorNamingStrategy.class, value = ImmutablesAccessorNamingStrategy.class)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses({ CreateBeanMapping.class, CreateBeanMappingConfig.class })
    @ExpectedNote("^MapStruct: Using accessor naming strategy:.*ImmutablesAccessorNamingStrategy.*$")
    @ExpectedNote("^MapStruct: Using builder provider:.*ImmutablesBuilderProvider.*$")
    @ExpectedNote("^ MapStruct: processing:.*.CreateBeanMapping.*$")
    @ExpectedNote("^ MapStruct: applying mapper configuration:.*CreateBeanMappingConfig.*$")
    public void testGeneralWithOtherSPI() {
    }

    @ProcessorTest(Compiler.JDK)
    @WithServiceImplementation(provides = AstModifyingAnnotationProcessor.class,
        value = AstModifyingAnnotationProcessorSaysNo.class)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses({ CreateBeanMapping.class, CreateBeanMappingConfig.class })
    @ExpectedNote("^MapStruct: referred types not available \\(yet\\), deferring mapper:.*CreateBeanMapping.*$")
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(
            type = CreateBeanMapping.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 12,
            messageRegExp = ".*No implementation was created for CreateBeanMapping due to having a problem in the " +
                "erroneous element .*"
        )
    })
    public void testDeferred() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses({ CreateBeanMapping.class, CreateBeanMappingConfig.class })
    @ExpectedNote("^- MapStruct: creating bean mapping method implementation for.*$")
    @ExpectedNote("^-- MapStruct: creating property mapping.*$")
    public void testCreateBeanMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(SelectBeanMapping.class)
    @ExpectedNote("^- MapStruct: creating bean mapping method implementation for.*$")
    @ExpectedNote("^-- MapStruct: selecting property mapping.*$")
    public void testSelectBeanMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(ValueMapping.class)
    @ExpectedNote("^- MapStruct: creating value mapping method implementation for.*$")
    public void testValueMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(CreateIterableMapping.class)
    @ExpectedNote("^- MapStruct: creating iterable mapping method implementation for.*$")
    @ExpectedNote("^-- MapStruct: creating element mapping.*$")
    public void testVerboseCreateIterableMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(SelectIterableMapping.class)
    @ExpectedNote("^- MapStruct: creating iterable mapping method implementation for.*$")
    @ExpectedNote("^-- MapStruct: selecting element mapping.*$")
    public void testVerboseSelectingIterableMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(SelectStreamMapping.class)
    @ExpectedNote("^- MapStruct: creating stream mapping method implementation for.*$")
    public void testVerboseSelectingStreamMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(CreateMapMapping.class)
    @ExpectedNote("^- MapStruct: creating map mapping method implementation for.*$")
    @ExpectedNote("^-- MapStruct: creating key mapping.*$")
    @ExpectedNote("^-- MapStruct: creating value mapping.*$")
    public void testVerboseCreateMapMapping() {
    }

    @ProcessorTest(Compiler.JDK)
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses(SelectMapMapping.class)
    @ExpectedNote("^- MapStruct: creating map mapping method implementation for.*$")
    @ExpectedNote("^-- MapStruct: selecting key mapping.*$")
    @ExpectedNote("^-- MapStruct: selecting value mapping.*$")
    public void testVerboseSelectingMapMapping() {
    }
}
