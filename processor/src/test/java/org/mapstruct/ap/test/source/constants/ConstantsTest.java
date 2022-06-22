/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses( {
    ConstantsMapper.class,
    ConstantsTarget.class
} )
public class ConstantsTest {

    @RegisterExtension
    final GeneratedSource generatedSrc =
        new GeneratedSource().addComparisonToFixtureFor( ConstantsMapper.class );

    @ProcessorTest
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

    @ProcessorTest
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
                line = 25,
                message =
                    "Can't map \"zz\" to \"boolean booleanValue\". Reason: only 'true' or 'false' are supported."),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                message = "Can't map \"'ba'\" to \"char charValue\". Reason: invalid character literal."),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 27,
                message =
                    "Can't map \"200\" to \"byte byteValue\". Reason: Value out of range. Value:\"200\" Radix:10."),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 28,
                message = "Can't map \"0xFFFF_FFFF_FFFF\" to \"int intValue\". Reason: integer number too large."),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                message = "Can't map \"1\" to \"long longValue\". Reason: L/l mandatory for long types."),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 30,
                message = "Can't map \"1.40e-_45f\" to \"float floatValue\". Reason: improperly placed underscores."),
            @Diagnostic(type = ErroneousConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 31,
                message = "Can't map \"1e-137000\" to \"double doubleValue\". Reason: floating point number too small.")
        }
    )
    public void miscellaneousDetailMessages() {
    }

}
