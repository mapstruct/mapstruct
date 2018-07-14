/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
