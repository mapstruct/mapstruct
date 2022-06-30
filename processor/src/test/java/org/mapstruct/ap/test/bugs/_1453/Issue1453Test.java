/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1453;

import java.util.Arrays;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1453")
@WithClasses({
    Auction.class,
    AuctionDto.class,
    Issue1453Mapper.class,
    Payment.class,
    PaymentDto.class
})
public class Issue1453Test {

    @RegisterExtension
    final GeneratedSource source = new GeneratedSource().addComparisonToFixtureFor( Issue1453Mapper.class );

    @ProcessorTest
    public void shouldGenerateCorrectCode() {

        AuctionDto target = Issue1453Mapper.INSTANCE.map( new Auction(
            Arrays.asList( new Payment( 100L ), new Payment( 500L ) ),
            Arrays.asList( new Payment( 200L ), new Payment( 600L ) )
        ) );

        PaymentDto first = new PaymentDto();
        first.setPrice( 100L );
        PaymentDto second = new PaymentDto();
        second.setPrice( 500L );

        assertThat( target.takePayments() )
            .usingFieldByFieldElementComparator()
            .containsExactly(
                first,
                second
            );

        PaymentDto firstOther = new PaymentDto();
        firstOther.setPrice( 200L );
        PaymentDto secondOther = new PaymentDto();
        secondOther.setPrice( 600L );

        assertThat( target.takeOtherPayments() )
            .usingFieldByFieldElementComparator()
            .containsExactly(
                firstOther,
                secondOther
            );
    }
}
