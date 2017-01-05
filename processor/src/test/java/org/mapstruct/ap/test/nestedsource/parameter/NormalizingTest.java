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
package org.mapstruct.ap.test.nestedsource.parameter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 */
@WithClasses({ FontDto.class, LetterDto.class, LetterEntity.class, LetterMapper.class })
@IssueKey("836")
@RunWith(AnnotationProcessorTestRunner.class)
public class NormalizingTest {

    @Test
    public void shouldGenerateImplementationForPropertyNamesOnly() {

        FontDto fontIn = new FontDto();
        fontIn.setSize( 10 );
        fontIn.setType( "Sans Serif" );
        LetterDto letterIn = new LetterDto();
        letterIn.setFont( fontIn );
        letterIn.setHeading( "dear sir," );
        letterIn.setBody( "Concerning your writing... " );
        letterIn.setSignature( "B. Obama" );

        LetterEntity letterEntity = LetterMapper.INSTANCE.normalize( letterIn );

        LetterDto letterOut = LetterMapper.INSTANCE.deNormalizeLetter( letterEntity );

        assertThat( letterOut ).isNotNull();
        assertThat( letterOut.getHeading() ).isEqualTo( "dear sir," );
        assertThat( letterOut.getBody() ).isEqualTo( "Concerning your writing... " );
        assertThat( letterOut.getSignature() ).isEqualTo( "B. Obama" );
        assertThat( letterOut.getFont() ).isNotNull();
        assertThat( letterOut.getFont().getSize() ).isEqualTo( 10 );
        assertThat( letterOut.getFont().getType() ).isEqualTo( "Sans Serif"  );
    }

}
