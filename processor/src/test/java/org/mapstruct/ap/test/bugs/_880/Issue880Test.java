/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._880;

import javax.tools.Diagnostic.Kind;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOptions;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.stereotype.Component;

/**
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    UsesConfigFromAnnotationMapper.class,
    DefaultsToProcessorOptionsMapper.class,
    Poodle.class,
    Config.class })
@ProcessorOptions({
    @ProcessorOption(name = "mapstruct.defaultComponentModel", value = "spring"),
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "ignore") })
public class Issue880Test {
    @Rule
    public GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = @Diagnostic(kind = Kind.WARNING,
            type = UsesConfigFromAnnotationMapper.class, line = 16,
            messageRegExp = "Unmapped target property: \"core\"\\."))
    public void compilationSucceedsAndAppliesCorrectComponentModel() {
        generatedSource.forMapper( UsesConfigFromAnnotationMapper.class ).containsNoImportFor( Component.class );

        generatedSource.forMapper( DefaultsToProcessorOptionsMapper.class ).containsImportFor( Component.class );
    }
}
