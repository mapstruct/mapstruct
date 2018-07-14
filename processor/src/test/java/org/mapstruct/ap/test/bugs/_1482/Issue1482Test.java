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
package org.mapstruct.ap.test.bugs._1482;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Source.class,
    Source2.class,
    Target.class,
    SourceEnum.class,
    SourceTargetMapper.class,
    TargetSourceMapper.class,
    BigDecimalWrapper.class,
    ValueWrapper.class
})
@IssueKey(value = "1482")
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue1482Test {

    @Test
    public void testForward() {

        Source source = new Source();
        source.setTest( SourceEnum.VAL1 );
        source.setWrapper( new BigDecimalWrapper( new BigDecimal( 5 ) ) );

        Target target = SourceTargetMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getTest() ).isEqualTo( "value1" );
        assertThat( target.getBigDecimal() ).isEqualTo( new BigDecimal( 5 ) );

    }

    @Test
    public void testReverse() {

        Target target = new Target();
        target.setBigDecimal( new BigDecimal( 5 ) );
        target.setTest( "VAL1" );

        Source2 source2 = TargetSourceMapper.INSTANCE.map( target );

        assertThat( source2 ).isNotNull();
        assertThat( source2.getTest() ).isEqualTo( SourceEnum.VAL1 );
        assertThat( source2.getWrapper().getValue() ).isEqualTo( new BigDecimal( 5 ) );

    }

}
