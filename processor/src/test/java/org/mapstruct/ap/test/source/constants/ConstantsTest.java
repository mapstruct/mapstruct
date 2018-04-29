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
package org.mapstruct.ap.test.source.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 *
 * @author Sjaak Derksen
 */
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( {
    ConstantsMapper.class,
    ConstantsTarget.class
} )
public class ConstantsTest {

    @Rule
    public final GeneratedSource generatedSrc =
        new GeneratedSource().addComparisonToFixtureFor( ConstantsMapper.class );

    @Test
    public void testNumericConstants() {

        ConstantsTarget target = ConstantsMapper.INSTANCE.mapFromConstants( "dummy" );

        assertThat( target ).isNotNull();
        assertThat( target.isBooleanValue() ).isEqualTo( true );
        assertThat( target.getBooleanBoxed() ).isEqualTo( false );
        assertThat( target.getCharValue() ).isEqualTo( 'b' );
        assertThat( target.getCharBoxed() ).isEqualTo( 'a' );
        assertThat( target.getByteValue() ).isEqualTo( (byte) 20 );
        assertThat( target.getByteBoxed() ).isEqualTo( (byte) -128 );
        assertThat( target.getShortValue() ).isEqualTo( (short) 1996 );
        assertThat( target.getShortBoxed() ).isEqualTo( (short) -1996 );
        assertThat( target.getIntValue() ).isEqualTo( -03777777 );
        assertThat( target.getIntBoxed() ).isEqualTo( 15 );
        assertThat( target.getLongValue() ).isEqualTo( 0x7fffffffffffffffL );
        assertThat( target.getLongBoxed() ).isEqualTo( 0xCAFEBABEL );
        assertThat( target.getFloatValue() ).isEqualTo( 1.40e-45f );
        assertThat( target.getFloatBoxed() ).isEqualTo( 3.4028235e38f );
        assertThat( target.getDoubleValue() ).isEqualTo( 1e137 );
        assertThat( target.getDoubleBoxed() ).isEqualTo( 0x0.001P-1062d );
        assertThat( target.getDoubleBoxedZero() ).isEqualTo( 0.0 );
    }

    @Test
    @IssueKey("1458")
    @WithClasses({
        ConstantsTarget.class,
        ErroneousConstantMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 39,
                messageRegExp = "^.*only 'true' or 'false' are supported\\.$"),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 40,
                messageRegExp = "^.*invalid character literal\\.$"),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 41,
                messageRegExp = "^.*Value out of range. Value:\"200\" Radix:10\\.$"),
           @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 42,
                messageRegExp = "^.*integer number too large.$"),
           @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 43,
                messageRegExp = "^.*L/l mandatory for long types.$"),
           @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 44,
                messageRegExp = "^.*improperly placed underscores.$"),
          @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 45,
                messageRegExp = "^.*floating point number too small.$")
        }
    )
    public void miscellaneousDetailMessages() {
    }

}
