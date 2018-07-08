/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.aptintegration;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1384")
@WithClasses({
    AccountDto.class,
    AccountEntity.class,
    AccountMapper.class
})
/**
 * @author Sjaak Derksen
 */
public class AptIntegrationTest {

    @Test
    public void testWithoutAstModifyingAnnotationProcessorSpi() {
        testSucces();
    }

    @Test
    @WithServiceImplementation(AlwaysComplete.class)
    public void testAlwaysCompleteSpi() {
        testSucces();
    }

    @Test
    @WithServiceImplementation(AlwaysIncomplete.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {}
    )
    public void testAlwaysIncompleteSpi() {
    }

    @Test
    @WithServiceImplementation(AlwaysCompleteDeprecated.class)
    public void testAlwaysCompleteDeprecatedSpi() {
        testSucces();
    }

    @Test
    @WithServiceImplementation(AlwaysIncompleteDeprecated.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {}
    )
    public void testAlwaysIncompleteDeprecatedSpi() {
    }

    private void testSucces() {
        AccountDto dto = new AccountDto();
        dto.setAmount( new BigDecimal( "15.50" ) );

        AccountEntity entity = AccountMapper.INSTANCE.map( dto );

        assertThat( entity ).isNotNull();
        assertThat( entity.getAmount() ).isNotNull();
        assertThat( entity.getAmount() ).isEqualByComparingTo( new BigDecimal( "15.50" ) );
    }
}
