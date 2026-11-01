/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.BuildInSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JakartaJaxbElementListProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JakartaJaxbElementProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JaxbElementListProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JaxbElementProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.XMLGregorianCalendarBuiltinSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.BigDecimalProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.BuildInTarget;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeType;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeTypeProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringListProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.XMLGregorianCalendarBuiltinTarget;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.WithJakartaJaxb;
import org.mapstruct.ap.testutil.WithJavaxJaxb;
import org.mapstruct.ap.testutil.WithPackageInfo;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithJSpecify
public class JspecifyBuildInTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( {
            JaxbMapper.class,
            JaxbElementProperty.class,
            StringProperty.class,
            BigDecimalProperty.class,
            SomeTypeProperty.class,
            SomeType.class
    } )
    @WithJavaxJaxb
    @WithPackageInfo( BuildInSource.class )
    public void testJspecifyJAXBElementBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( JaxbMapper.class, "TargetNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JaxbMapper.class,
            JaxbElementProperty.class,
            StringProperty.class,
            BigDecimalProperty.class,
            SomeTypeProperty.class,
            SomeType.class
    } )
    @WithJavaxJaxb
    @WithPackageInfo( { BuildInTarget.class, BuildInSource.class } )
    public void testJspecifyJAXBElementBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JaxbMapper.class, "TargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JaxbMapper.class,
            JaxbElementProperty.class,
            StringProperty.class,
            BigDecimalProperty.class,
            SomeTypeProperty.class,
            SomeType.class
    } )
    @WithJavaxJaxb
    @WithPackageInfo(BuildInTarget.class)
    public void testJspecifyJAXBElementBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JaxbMapper.class, "TargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JakartaJaxbMapper.class,
            JakartaJaxbElementProperty.class,
            StringProperty.class,
            BigDecimalProperty.class,
            SomeTypeProperty.class,
            SomeType.class
    } )
    @WithJakartaJaxb
    @WithPackageInfo( { BuildInTarget.class, BuildInSource.class } )
    public void testJspecifyJakartaElementBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JakartaJaxbMapper.class, "TargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JakartaJaxbMapper.class,
            JakartaJaxbElementProperty.class,
            StringProperty.class,
            BigDecimalProperty.class,
            SomeTypeProperty.class,
            SomeType.class
    } )
    @WithJakartaJaxb
    @WithPackageInfo(BuildInTarget.class)
    public void testJspecifyJakartaElementBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JakartaJaxbMapper.class, "TargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JakartaJaxbMapper.class,
            JakartaJaxbElementProperty.class,
            StringProperty.class,
            BigDecimalProperty.class,
            SomeTypeProperty.class,
            SomeType.class
    } )
    @WithJakartaJaxb
    @WithPackageInfo( BuildInSource.class )
    public void testJspecifyJakartaElementBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( JakartaJaxbMapper.class, "TargetNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JaxbListMapper.class,
            JaxbElementListProperty.class,
            StringListProperty.class
    } )
    @WithJavaxJaxb
    @WithPackageInfo( BuildInSource.class )
    public void testJspecifyJAXBElementListBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( JaxbListMapper.class, "SourceNoneNullableTargetNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JaxbListMapper.class,
            JaxbElementListProperty.class,
            StringListProperty.class
    } )
    @WithJavaxJaxb
    @WithPackageInfo( { BuildInTarget.class, BuildInSource.class } )
    public void testJspecifyJAXBElementListBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JaxbListMapper.class, "SourceNoneNullableTargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JaxbListMapper.class,
            JaxbElementListProperty.class,
            StringListProperty.class
    } )
    @WithJavaxJaxb
    @WithPackageInfo(BuildInTarget.class)
    public void testJspecifyJAXBElementListBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JaxbListMapper.class, "SourceNullableTargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JakartaJaxbListMapper.class,
            JakartaJaxbElementListProperty.class,
            StringListProperty.class
    } )
    @WithJakartaJaxb
    @WithPackageInfo( BuildInSource.class )
    public void testJspecifyJakartaJAXBElementLisBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( JakartaJaxbListMapper.class, "SourceNoneNullableTargetNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JakartaJaxbListMapper.class,
            JakartaJaxbElementListProperty.class,
            StringListProperty.class
    } )
    @WithJakartaJaxb
    @WithPackageInfo( { BuildInTarget.class, BuildInSource.class } )
    public void testJspecifyJakartaJAXBElementLisBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JakartaJaxbListMapper.class,
                "SourceNoneNullableTargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {
            JakartaJaxbListMapper.class,
            JakartaJaxbElementListProperty.class,
            StringListProperty.class
    } )
    @WithJakartaJaxb
    @WithPackageInfo(BuildInTarget.class)
    public void testJspecifyJakartaJAXBElementLisBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JakartaJaxbListMapper.class, "SourceNullableTargetNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {BuildInTarget.class, BuildInSource.class, BuildInMapper.class } )
    @WithPackageInfo( BuildInSource.class )
    public void testJspecifyBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( BuildInMapper.class, "SourceNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {BuildInTarget.class, BuildInSource.class, BuildInMapper.class } )
    @WithPackageInfo( { BuildInTarget.class, BuildInSource.class } )
    public void testJspecifyBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( BuildInMapper.class, "SourceNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {BuildInTarget.class, BuildInSource.class, BuildInMapper.class } )
    @WithPackageInfo( BuildInTarget.class )
    public void testJspecifyBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( BuildInMapper.class, "SourceNullable" );
    }

    @ProcessorTest
    @WithClasses( {XMLGregorianCalendarBuiltinTarget.class, XMLGregorianCalendarBuiltinSource.class,
            XMLGregorianCalendarBuiltinMapper.class } )
    @WithPackageInfo( { XMLGregorianCalendarBuiltinTarget.class, XMLGregorianCalendarBuiltinSource.class } )
    public void testJspecifyXMLGregorianCalendarBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( XMLGregorianCalendarBuiltinMapper.class, "SourceNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {XMLGregorianCalendarBuiltinTarget.class, XMLGregorianCalendarBuiltinSource.class,
            XMLGregorianCalendarBuiltinMapper.class } )
    @WithPackageInfo( XMLGregorianCalendarBuiltinSource.class )
    public void testJspecifyXMLGregorianCalendarBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( XMLGregorianCalendarBuiltinMapper.class, "SourceNoneNullable" );
    }

    @ProcessorTest
    @WithClasses( {XMLGregorianCalendarBuiltinTarget.class, XMLGregorianCalendarBuiltinSource.class,
            XMLGregorianCalendarBuiltinMapper.class } )
    @WithPackageInfo( XMLGregorianCalendarBuiltinTarget.class )
    public void testJspecifyXMLGregorianCalendarBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( XMLGregorianCalendarBuiltinMapper.class, "SourceNullable" );
    }

}
