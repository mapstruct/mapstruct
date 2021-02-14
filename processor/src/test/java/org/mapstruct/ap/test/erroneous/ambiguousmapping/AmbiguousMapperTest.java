/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousmapping;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

@IssueKey("2156")
public class AmbiguousMapperTest {

    @ProcessorTest
    @WithClasses( ErroneousWithAmbiguousMethodsMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousWithAmbiguousMethodsMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message =
    "Ambiguous mapping methods found for mapping property " +
    "\"ErroneousWithAmbiguousMethodsMapper.LeafDTO branch.leaf\" to ErroneousWithAmbiguousMethodsMapper.LeafEntity: " +
    "ErroneousWithAmbiguousMethodsMapper.LeafEntity map1(ErroneousWithAmbiguousMethodsMapper.LeafDTO dto), " +
    "ErroneousWithAmbiguousMethodsMapper.LeafEntity map2(ErroneousWithAmbiguousMethodsMapper.LeafDTO dto). " +
    "See https://mapstruct.org/faq/#ambiguous for more info."            )
        }
    )
    public void testErrorMessageForAmbiguous() {
    }

    @ProcessorTest
    @WithClasses( ErroneousWithMoreThanFiveAmbiguousMethodsMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousWithMoreThanFiveAmbiguousMethodsMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message =
    // CHECKSTYLE:OFF
    "Ambiguous mapping methods found for mapping property \"ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO branch.leaf\" to " +
    "ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity: " +
    "ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map1(ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
    "ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map2(ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
    "ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map3(ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
    "ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map4(ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
    "ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map5(ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto)" +
    "... and 1 more. See https://mapstruct.org/faq/#ambiguous for more info."
    // CHECKSTYLE:ON
            )
        }
    )
    public void testErrorMessageForManyAmbiguous() {
    }

    @ProcessorTest
    @ProcessorOption(name = "mapstruct.verbose", value = "true")
    @WithClasses( ErroneousWithMoreThanFiveAmbiguousMethodsMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousWithMoreThanFiveAmbiguousMethodsMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message =
                    // CHECKSTYLE:OFF
                 "Ambiguous mapping methods found for mapping property \"org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO branch.leaf\" " +
                     "to org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity: " +
                     "org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map1(org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
                     "org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map2(org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
                     "org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map3(org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
                     "org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map4(org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
                     "org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map5(org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto), " +
                     "org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafEntity map6(org.mapstruct.ap.test.erroneous.ambiguousmapping.ErroneousWithMoreThanFiveAmbiguousMethodsMapper.LeafDTO dto). " +
                     "See https://mapstruct.org/faq/#ambiguous for more info."
                // CHECKSTYLE:ON
            )
        }
    )
    public void testErrorMessageForManyAmbiguousVerbose() {
    }

}
