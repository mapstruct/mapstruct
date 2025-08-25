/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andreas Gudian
 *
 */
public class GenericsTest {

    @ProcessorTest
    @IssueKey("574")
    @WithClasses({
        AbstractTo.class,
        AbstractIdHoldingTo.class,
        Source.class,
        SourceTargetMapper.class,
        TargetTo.class
    })
    public void mapsIdCorrectly() {
        TargetTo target = new TargetTo();
        target.setId( 41L );
        assertThat( SourceTargetMapper.INSTANCE.toSource( target ).getId() ).isEqualTo( 41L );
    }

    @ProcessorTest
    @IssueKey( "2690" )
    @WithClasses({
        TwinGenericTarget.class,
        TwinGenericSource.class,
        TwinGenericMapper.class
    })
    public void mapsIdenticalGenerics() {
        TwinGenericSource<String, Integer> source = new TwinGenericSource<>();
        source.setObject1( "StringObject" );
        source.setObject2( 123 );

        TwinGenericTarget<String, Integer> target = TwinGenericMapper.INSTANCE.map( source );

        assertThat( target.getObject1() ).isEqualTo( "StringObject" );
        assertThat( target.getObject2() ).isEqualTo( 123 );
    }

    @ProcessorTest
    @IssueKey("2954")
    @WithClasses({
        GenericTargetContainer.class,
        GenericSourceContainer.class,
        GenericContainerMapper.class,
    })
    public void mapsSameTypeCorrectly() {
        String containedObject = "Test";
        GenericSourceContainer<String> container = new GenericSourceContainer<>( containedObject, "otherValue" );

        GenericTargetContainer<String> resultString = GenericContainerMapper.INSTANCE.map( container );
        assertThat( resultString.getContained() ).isSameAs( containedObject );
        assertThat( resultString.getOtherValue() ).isEqualTo( container.getOtherValue() );

        Integer replacement = 1234;
        GenericTargetContainer<Integer> resultContext =
            GenericContainerMapper.INSTANCE.mapWithContext( container, replacement );
        assertThat( resultContext.getContained() ).isSameAs( replacement );
        assertThat( resultContext.getOtherValue() ).isEqualTo( container.getOtherValue() );

        GenericTargetContainer<Integer> resultSecondParameter =
            GenericContainerMapper.INSTANCE.mapWithSecondParameter( container, replacement );
        assertThat( resultSecondParameter.getContained() ).isSameAs( replacement );
        assertThat( resultSecondParameter.getOtherValue() ).isEqualTo( container.getOtherValue() );
    }

    @ProcessorTest
    @IssueKey("2954")
    @WithClasses({
        GenericTargetContainer.class,
        GenericSourceContainer.class,
        ErroneousGenericContainerMapperMismatch.class,
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
        @Diagnostic(type = ErroneousGenericContainerMapperMismatch.class,
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    line = 20,
                    message = "Can't map property \"S contained\" to \"T contained\". "
                        + "Both generic types should be the same." )
        }
    )
    public void invalidGenericMappingsMismatch() {
    }

    @ProcessorTest
    @IssueKey("2954")
    @WithClasses({
        GenericTargetContainer.class,
        GenericSourceContainer.class,
        ErroneousGenericContainerMapperParameterMismatch.class,
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
        @Diagnostic(type = ErroneousGenericContainerMapperParameterMismatch.class,
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    line = 21,
                    message = "Can't map parameter \"T replacement\" to \"S contained\". "
                        + "Both generic types should be the same." )
        }
    )
    public void invalidGenericMappings() {
    }

    @ProcessorTest
    @IssueKey( "2690" )
    @WithClasses({
        TwinGenericTarget.class,
        TwinGenericSource.class,
        ErroneousTwinGenericMapper.class,
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousTwinGenericMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 18,
                            message = "Can't map property \"B object1\" to \"A object1\". "
                                            + "Both generic types should be the same." ),
            @Diagnostic(type = ErroneousTwinGenericMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 18,
                        message = "Can't map property \"A object2\" to \"B object2\". "
                                        + "Both generic types should be the same." )
        }
    )
    public void invalidTwinGenericMappings() {
    }
}
