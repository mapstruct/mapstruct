/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedinterface;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class SealedInterfaceTest {

    private final Issue3836Mapper mapper = Issue3836Mapper.INSTANCE;

    @Test
    public void shouldGeneratePatternSwitchInPolymorphicMapMethod() throws IOException {
        Path generatedFile = Path.of("target/generated-sources/annotations/org/mapstruct/itest/sealedinterface/Issue3836MapperImpl.java");
        String content = Files.readString(generatedFile);
        assertThat(content.contains("switch (from) {")).isTrue();
        assertThat(content.contains("case FromA fromA ->")).isTrue();
        assertThat(content.contains("return map( (FromA) from );")).isTrue();
        assertThat(content.contains("case FromB fromB ->")).isTrue();
        assertThat(content.contains("return map( (FromB) from );")).isTrue();
        assertThat(content.contains("if (from instanceof")).isFalse();
    }

    @Test
    public void shouldMapFromAToTo() {
        FromA fromA = new FromA(100);

        To result = mapper.map(fromA);

        assertThat(result).isNotNull();
        assertThat(result.to()).isEqualTo(100);
    }

    @Test
    public void shouldMapFromBToTo() {
        FromB fromB = new FromB(200);

        To result = mapper.map(fromB);

        assertThat(result).isNotNull();
        assertThat(result.to()).isEqualTo(200);
    }

    @Test
    public void shouldMapFromInterfaceWithFromAInstance() {
        From from = new FromA(300);

        To result = mapper.map(from);

        assertThat(result).isNotNull();
        assertThat(result.to()).isEqualTo(300);
    }

    @Test
    public void shouldMapFromInterfaceWithFromBInstance() {
        From from = new FromB(400);

        To result = mapper.map(from);

        assertThat(result).isNotNull();
        assertThat(result.to()).isEqualTo(400);
    }

    @Test
    public void shouldReturnNullWhenInputIsNull() {
        assertThat(mapper.map((FromA) null)).isNull();
        assertThat(mapper.map((FromB) null)).isNull();
        assertThat(mapper.map((From) null)).isNull();
    }
}