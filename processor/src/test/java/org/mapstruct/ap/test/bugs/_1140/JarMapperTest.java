package org.mapstruct.ap.test.bugs._1140;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;


class JarMapperTest {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = JarToJarMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                message = "Target \"EmptyJar\" has no target properties, targeted by this mapping method: \"EmptyJar mapToEmptyJar(FilledJar filledJar)\"")
        })
    @WithClasses({ FilledJar.class, EmptyJar.class, JarToJarMapper.class })
    void targetHasNoProperties() {
        FilledJar filledJar = new FilledJar();
        filledJar.setLabel( "Lemoncurd" );
        filledJar.setWeight( 1.00 );
        filledJar.setContent( new Object() );

        EmptyJar emptyJar = JarToJarMapper.INSTANCE.mapToEmptyJar( filledJar );

        assertThat( emptyJar ).hasAllNullFieldsOrProperties();
    }


    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = JarToAirplaneMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                message = "Target \"AirplaneWithNoAccessors\" has no target properties, targeted by this mapping method: \"AirplaneWithNoAccessors mapToAirplaneWithNoAccessors(FilledJar jar)\"")
        })
    @WithClasses({ FilledJar.class, AirplaneWithNoAccessors.class, JarToAirplaneMapper.class })
    void targetHasNoAccessibleProperties() {

        AirplaneWithNoAccessors airplane = JarToAirplaneMapper.INSTANCE.mapToAirplaneWithNoAccessors( new FilledJar() );

        assertThat( airplane )
            .hasFieldOrPropertyWithValue( "flightNumber", 0 )
            .hasFieldOrPropertyWithValue( "airplaneName", null );
    }
}