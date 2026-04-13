/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage.PackageNullMarkedMapper;
import org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage.PackageNullMarkedSourceBean;
import org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage.PackageNullMarkedTargetBean;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.WithPackageInfo;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for JSpecify {@code @NullMarked} / {@code @NullUnmarked} support.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithJSpecify
public class JSpecifyNullMarkedTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({ NullMarkedSourceBean.class, NullMarkedTargetBean.class, NullMarkedMapper.class })
    public void nullMarkedSourceAndTarget() {
        generatedSource.addComparisonToFixtureFor( NullMarkedMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        NullMarkedTargetBean target = NullMarkedMapper.INSTANCE.map( source );

        // nonNullByDefault: source effectively @NonNull -> skip null check -> setter called
        assertThat( target.isNonNullByDefaultSet() ).isTrue();
        assertThat( target.getNonNullByDefault() ).isEqualTo( "value" );
    }

    @ProcessorTest
    @WithClasses({ NullMarkedSourceBean.class, NullMarkedTargetBean.class, NullMarkedMapper.class })
    public void nullMarkedNullableSourceToNonNullTarget() {
        // @Nullable getter -> target effectively @NonNull -> null check added
        NullMarkedSourceBean source = new NullMarkedSourceBean();
        // explicitlyNullable is null

        NullMarkedTargetBean target = NullMarkedMapper.INSTANCE.map( source );

        // Target's setter for explicitlyNullable is @Nullable -> defers to strategy
        // Default NVCS = ON_IMPLICIT_CONVERSION, direct String -> String -> no null check
        assertThat( target.isExplicitlyNullableSet() ).isTrue();
    }

    @ProcessorTest
    @WithClasses({ NullMarkedSourceBean.class, TargetBean.class, NullMarkedSourceToPlainTargetMapper.class })
    public void nullMarkedSourceToPlainTarget() {
        generatedSource.addComparisonToFixtureFor( NullMarkedSourceToPlainTargetMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        TargetBean target = NullMarkedSourceToPlainTargetMapper.INSTANCE.map( source );

        // Source effectively @NonNull -> skip null check -> setter always called
        assertThat( target.isUnannotatedTargetSet() ).isTrue();
    }

    @ProcessorTest
    @WithClasses({ NullMarkedSourceBean.class, NullMarkedTargetBean.class, NullMarkedMapperWithParam.class })
    public void nullMarkedMapperSkipsMethodLevelNullGuard() {
        generatedSource.addComparisonToFixtureFor( NullMarkedMapperWithParam.class );
    }

    @ProcessorTest
    @WithClasses({
        PackageNullMarkedSourceBean.class,
        PackageNullMarkedTargetBean.class,
        PackageNullMarkedMapper.class
    })
    @WithPackageInfo(PackageNullMarkedSourceBean.class)
    public void packageLevelNullMarked() {
        generatedSource.addComparisonToFixtureFor( PackageNullMarkedMapper.class );

        // @NullMarked package: unannotated types are effectively @NonNull.
        // Mapper uses NullValueCheckStrategy.ALWAYS but JSpecify wins -> no null checks.
        PackageNullMarkedSourceBean source = new PackageNullMarkedSourceBean();
        // value is null

        PackageNullMarkedTargetBean target = PackageNullMarkedMapper.INSTANCE.map( source );

        // Unannotated getter is effectively @NonNull -> no null check despite ALWAYS strategy
        // -> setter is called even when source value is null
        assertThat( target.isValueSet() ).isTrue();
    }

    @ProcessorTest
    @WithClasses({
        SourceBean.class,
        TargetBean.class,
        JSpecifyNullUnmarkedMethodMapper.class
    })
    public void nullUnmarkedOnMethodReversesEnclosingNullMarkedScope() {
        // The mapper interface is @NullMarked but the method is @NullUnmarked. The source
        // parameter is unannotated: with method-level scope, its nullability must be UNKNOWN
        // (not promoted), so the method-level null guard is generated. Passing null must
        // return null rather than NPE.
        TargetBean target = JSpecifyNullUnmarkedMethodMapper.INSTANCE.map( null );

        assertThat( target ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        NullUnmarkedSourceBean.class,
        TargetBean.class,
        JSpecifyNullUnmarkedMapper.class
    })
    public void nullUnmarkedReversesEnclosingNullMarkedScope() {
        // The source class is @NullUnmarked inside a @NullMarked outer class.
        // Unannotated getter must be UNKNOWN, not promoted to @NonNull.
        // With NVCS=ALWAYS, a null check is generated and the setter is NOT called when source is null.
        NullUnmarkedSourceBean.Inner source = new NullUnmarkedSourceBean.Inner();
        // value is null

        TargetBean target = JSpecifyNullUnmarkedMapper.INSTANCE.map( source );

        assertThat( target.isUnannotatedTargetSet() ).isFalse();
    }

    @ProcessorTest
    @WithClasses({
        SourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNullMarkedTargetScopeMapper.class
    })
    public void nullMarkedTargetPromotesUnannotatedSetterParamToNonNull() {
        // Target bean is @NullMarked; setNonNullByDefault has an unannotated String parameter
        // which must be treated as @NonNull by JSpecify semantics. Source getter is @Nullable,
        // so a null check must be generated and the setter must NOT be called when source is null.
        SourceBean source = new SourceBean();
        // nullableValue is null

        NullMarkedTargetBean target = JSpecifyNullMarkedTargetScopeMapper.INSTANCE.map( source );

        assertThat( target.isNonNullByDefaultSet() ).isFalse();
    }

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        TargetBean.class,
        JSpecifyNullableOverridesScopeMapper.class
    })
    public void explicitNullableOverridesNullMarkedScope() {
        // Source getter is explicitly @Nullable inside a @NullMarked class — the explicit
        // annotation must keep the source NULLABLE instead of being promoted to @NonNull
        // by the scope. Target setter is explicitly @NonNull, so a null check is required.
        NullMarkedSourceBean source = new NullMarkedSourceBean();
        // explicitlyNullable is null

        TargetBean target = JSpecifyNullableOverridesScopeMapper.INSTANCE.map( source );

        // Null check generated -> setter must NOT be called when source value is null.
        assertThat( target.isNonNullTargetFromNullableSet() ).isFalse();
    }

    @ProcessorTest
    @WithClasses({
        PackageNullMarkedSourceBean.class,
        PackageNullMarkedTargetBean.class,
        PackageNullMarkedMapper.class
    })
    public void packageLevelNullMarkedWithoutPackageInfo() {
        // Same classes and mapper as packageLevelNullMarked but WITHOUT @WithPackageInfo.
        // Without the package-info.java being compiled, @NullMarked is not visible —
        // unannotated types have unknown nullability and the ALWAYS strategy produces null checks.
        generatedSource.addComparisonToFixtureFor( PackageNullMarkedMapper.class, "withoutpackageinfo" );
    }

}
