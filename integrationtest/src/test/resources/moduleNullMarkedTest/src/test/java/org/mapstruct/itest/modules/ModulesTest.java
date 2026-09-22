/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules;

import org.junit.jupiter.api.Test;
import org.mapstruct.itest.modules.nullmarked.NullMarkedMapper;
import org.mapstruct.itest.modules.nullmarked.NullMarkedSourceBean;
import org.mapstruct.itest.modules.nullmarked.NullMarkedTargetBean;
import org.mapstruct.itest.modules.nullunmarked.NullUnmarkedMapper;
import org.mapstruct.itest.modules.nullunmarked.NullUnmarkedSourceBean;
import org.mapstruct.itest.modules.nullunmarked.NullUnmarkedTargetBean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests that verify JSpecify {@code @NullMarked} on a JPMS module is
 * honored by the MapStruct processor, and that a package-level {@code @NullUnmarked}
 * correctly reverses the module-level {@code @NullMarked} scope.
 */
class ModulesTest {

    @Test
    void nullMarkedModuleSuppressesNullCheck() {
        // The module is @NullMarked, so unannotated types are effectively @NonNull.
        // With NullValueCheckStrategy.ALWAYS, a source getter that is effectively
        // @NonNull must NOT have a null check generated -> the target setter is
        // called even when the source value is null.
        NullMarkedSourceBean source = new NullMarkedSourceBean();
        assertThat( source.getValue() ).isNull();

        NullMarkedTargetBean target = NullMarkedMapper.INSTANCE.map( source );

        assertThat( target.isValueSet() ).isTrue();
        assertThat( target.getValue() ).isNull();
    }

    @Test
    void nullUnmarkedPackageReversesModuleNullMarked() {
        // The package is @NullUnmarked inside a @NullMarked module. The
        // package-level scope reversal makes unannotated types have UNKNOWN
        // nullability, so NullValueCheckStrategy.ALWAYS generates a null check
        // and the target setter is NOT called when the source value is null.
        NullUnmarkedSourceBean source = new NullUnmarkedSourceBean();
        assertThat( source.getValue() ).isNull();

        NullUnmarkedTargetBean target = NullUnmarkedMapper.INSTANCE.map( source );

        assertThat( target.isValueSet() ).isFalse();
        assertThat( target.getValue() ).isNull();
    }
}
