/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.bugs._1453;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1453")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Auction.class,
    AuctionDto.class,
    Issue1453Mapper.class,
    Payment.class,
    PaymentDto.class
})
public class Issue1453Test {

    @Rule
    public GeneratedSource source = new GeneratedSource().addComparisonToFixtureFor( Issue1453Mapper.class );

    @Test
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
