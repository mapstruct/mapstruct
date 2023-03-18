/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.qualifier;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Composer.class,
    ComposerDto.class,
    Rossini.class,
    RossiniDto.class,
    Vivaldi.class,
    VivaldiDto.class,
    VivaldiDto.class,
    VivaldiDto.class,
    Light.class,
    Unused.class,
    NonExistent.class,
    RossiniMapper.class,
    VivaldiMapper.class
})
@IssueKey("3119")
public class SubclassQualifierMapperTest {
    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses(SubclassQualifiedByMapper.class)
    void subclassQualifiedBy() {
        Rossini rossini = buildRossini();

        Vivaldi vivaldi = buildVivaldi();

        RossiniDto rossiniDto = (RossiniDto) SubclassQualifiedByMapper.INSTANCE.toDto( rossini );
        RossiniDto rossiniDtoLight = (RossiniDto) SubclassQualifiedByMapper.INSTANCE.toDtoLight( rossini );

        VivaldiDto vivaldiDto = (VivaldiDto) SubclassQualifiedByMapper.INSTANCE.toDto( vivaldi );
        VivaldiDto vivaldiDtoLight = (VivaldiDto) SubclassQualifiedByMapper.INSTANCE.toDtoLight( vivaldi );

        assertThat( rossiniDto.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniDto.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );
        assertThat( rossiniDtoLight.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniDtoLight.getCrescendo() ).isNull();

        assertThat( vivaldiDto.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiDto.getSeasons() ).containsExactly( "spring", "winter" );
        assertThat( vivaldiDtoLight.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiDtoLight.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByMapper.class)
    void subclassQualifiedByOnlyOne() {
        Rossini rossini = buildRossini();

        Vivaldi vivaldi = buildVivaldi();

        RossiniDto rossiniDto = (RossiniDto) SubclassQualifiedByMapper.INSTANCE.toDtoLightJustVivaldi( rossini );

        VivaldiDto vivaldiDto = (VivaldiDto) SubclassQualifiedByMapper.INSTANCE.toDtoLightJustVivaldi( vivaldi );

        assertThat( rossiniDto.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniDto.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );

        assertThat( vivaldiDto.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiDto.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByNameMapper.class)
    void subclassQualifiedByName() {
        Rossini rossini = buildRossini();

        Vivaldi vivaldi = buildVivaldi();

        RossiniDto rossiniDto = (RossiniDto) SubclassQualifiedByNameMapper.INSTANCE.toDto( rossini );
        RossiniDto rossiniDtoLight = (RossiniDto) SubclassQualifiedByNameMapper.INSTANCE.toDtoLight( rossini );

        VivaldiDto vivaldiDto = (VivaldiDto) SubclassQualifiedByNameMapper.INSTANCE.toDto( vivaldi );
        VivaldiDto vivaldiDtoLight = (VivaldiDto) SubclassQualifiedByNameMapper.INSTANCE.toDtoLight( vivaldi );

        assertThat( rossiniDto.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniDto.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );
        assertThat( rossiniDtoLight.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniDtoLight.getCrescendo() ).isNull();

        assertThat( vivaldiDto.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiDto.getSeasons() ).containsExactly( "spring", "winter" );
        assertThat( vivaldiDtoLight.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiDtoLight.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByNameMapper.class)
    void subclassQualifiedByNameOnlyOne() {
        Rossini rossini = buildRossini();

        Vivaldi vivaldi = buildVivaldi();

        RossiniDto rossiniDto = (RossiniDto) SubclassQualifiedByNameMapper.INSTANCE.toDtoLightJustVivaldi( rossini );

        VivaldiDto vivaldiDto = (VivaldiDto) SubclassQualifiedByNameMapper.INSTANCE.toDtoLightJustVivaldi( vivaldi );

        assertThat( rossiniDto.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniDto.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );

        assertThat( vivaldiDto.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiDto.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByMapper.class)
    void subclassQualifiedByInverse() {
        RossiniDto rossiniDto = buildRossiniDto();

        VivaldiDto vivaldiDto = buildVivaldiDto();

        Rossini rossini = (Rossini) SubclassQualifiedByMapper.INSTANCE.fromDto( rossiniDto );
        Rossini rossiniLight = (Rossini) SubclassQualifiedByMapper.INSTANCE.fromDtoLight( rossiniDto );

        Vivaldi vivaldi = (Vivaldi) SubclassQualifiedByMapper.INSTANCE.fromDto( vivaldiDto );
        Vivaldi vivaldiLight = (Vivaldi) SubclassQualifiedByMapper.INSTANCE.fromDtoLight( vivaldiDto );

        assertThat( rossini.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossini.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );
        assertThat( rossiniLight.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniLight.getCrescendo() ).isNull();

        assertThat( vivaldi.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldi.getSeasons() ).containsExactly( "spring", "winter" );
        assertThat( vivaldiLight.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiLight.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByMapper.class)
    void subclassQualifiedByOnlyOneInverse() {
        RossiniDto rossiniDto = buildRossiniDto();

        VivaldiDto vivaldiDto = buildVivaldiDto();

        Rossini rossini = (Rossini) SubclassQualifiedByMapper.INSTANCE.fromDtoLightJustVivaldi( rossiniDto );

        Vivaldi vivaldi = (Vivaldi) SubclassQualifiedByMapper.INSTANCE.fromDtoLightJustVivaldi( vivaldiDto );

        assertThat( rossini.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossini.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );

        assertThat( vivaldi.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldi.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByNameMapper.class)
    void subclassQualifiedByNameInverse() {
        RossiniDto rossiniDto = buildRossiniDto();

        VivaldiDto vivaldiDto = buildVivaldiDto();

        Rossini rossini = (Rossini) SubclassQualifiedByNameMapper.INSTANCE.fromDto( rossiniDto );
        Rossini rossiniLight = (Rossini) SubclassQualifiedByNameMapper.INSTANCE.fromDtoLight( rossiniDto );

        Vivaldi vivaldi = (Vivaldi) SubclassQualifiedByNameMapper.INSTANCE.fromDto( vivaldiDto );
        Vivaldi vivaldiLight = (Vivaldi) SubclassQualifiedByNameMapper.INSTANCE.fromDtoLight( vivaldiDto );

        assertThat( rossini.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossini.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );
        assertThat( rossiniLight.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossiniLight.getCrescendo() ).isNull();

        assertThat( vivaldi.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldi.getSeasons() ).containsExactly( "spring", "winter" );
        assertThat( vivaldiLight.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldiLight.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByNameMapper.class)
    void subclassQualifiedByNameOnlyOneInverse() {
        RossiniDto rossiniDto = buildRossiniDto();

        VivaldiDto vivaldiDto = buildVivaldiDto();

        Rossini rossini = (Rossini) SubclassQualifiedByNameMapper.INSTANCE.fromDtoLightJustVivaldi( rossiniDto );

        Vivaldi vivaldi = (Vivaldi) SubclassQualifiedByNameMapper.INSTANCE.fromDtoLightJustVivaldi( vivaldiDto );

        assertThat( rossini.getName() ).isEqualTo( "gioacchino" );
        assertThat( rossini.getCrescendo() ).containsExactly( "andante", "allegro", "vivace" );

        assertThat( vivaldi.getName() ).isEqualTo( "antonio" );
        assertThat( vivaldi.getSeasons() ).isNull();
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByMapper.class)
    void subclassQualifiedByDoesNotContainUnused() {
        generatedSource.forMapper( SubclassQualifiedByMapper.class )
            .content()
            .doesNotContain( "DtoUnused" );
    }

    @ProcessorTest
    @WithClasses(SubclassQualifiedByNameMapper.class)
    void subclassQualifiedByNameDoesNotContainUnused() {
        generatedSource.forMapper( SubclassQualifiedByNameMapper.class )
            .content()
            .doesNotContain( "DtoUnused" );
    }

    @ProcessorTest
    @WithClasses(ErroneousSubclassQualifiedByMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            type = ErroneousSubclassQualifiedByMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 13,
            message = "Qualifier error. No method found annotated with: [ @NonExistent ]. " +
                "See https://mapstruct.org/faq/#qualifier for more info."
        )
    )
    void subclassQualifiedByErroneous() {
    }

    @ProcessorTest
    @WithClasses(ErroneousSubclassQualifiedByNameMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            type = ErroneousSubclassQualifiedByNameMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 13,
            alternativeLine = 15,
            message = "Qualifier error. No method found annotated with @Named#value: [ non-existent ]. " +
                "See https://mapstruct.org/faq/#qualifier for more info."
        )
    )
    void subclassQualifiedByNameErroneous() {
    }

    private Rossini buildRossini() {
        Rossini rossini = new Rossini();
        rossini.setName( "gioacchino" );
        List<String> crescendo = new ArrayList<>();
        crescendo.add( "andante" );
        crescendo.add( "allegro" );
        crescendo.add( "vivace" );
        rossini.setCrescendo( crescendo );
        return rossini;
    }

    private Vivaldi buildVivaldi() {
        Vivaldi vivaldi = new Vivaldi();
        vivaldi.setName( "antonio" );
        List<String> season = new ArrayList<>();
        season.add( "spring" );
        season.add( "winter" );
        vivaldi.setSeasons( season );
        return vivaldi;
    }

    private RossiniDto buildRossiniDto() {
        RossiniDto rossiniDto = new RossiniDto();
        rossiniDto.setName( "gioacchino" );
        List<String> crescendo = new ArrayList<>();
        crescendo.add( "andante" );
        crescendo.add( "allegro" );
        crescendo.add( "vivace" );
        rossiniDto.setCrescendo( crescendo );
        return rossiniDto;
    }

    private VivaldiDto buildVivaldiDto() {
        VivaldiDto vivaldiDto = new VivaldiDto();
        vivaldiDto.setName( "antonio" );
        List<String> season = new ArrayList<>();
        season.add( "spring" );
        season.add( "winter" );
        vivaldiDto.setSeasons( season );
        return vivaldiDto;
    }
}
