/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.testutil.ProcessorTestConfiguration;

public class MapStructProcessorTestConfiguration implements ProcessorTestConfiguration {

    @Override
    public Class<?>[] getAnnotationProcessorClasses() {
        return new Class<?>[] {
            MappingProcessor.class
        };
    }

    @Override
    public String[] getAnnotationProcessorPackagesOrClasses() {
        return new String[] {
            "org.mapstruct.ap.internal.",
            "org.mapstruct.ap.spi.",
            "org.mapstruct.ap.MappingProcessor",
        };
    }

    @Override
    public String[] getAnnotationProcessorAndTestRootPackagesOrClasses() {
        return new String[] { "org.mapstruct." };
    }

    @Override
    public String getGenerateFileName(Class<?> sourceClass) {
        return sourceClass.getName().replace( '.', '/' ).concat( "Impl.java" );
    }

    @Override
    public Collection<String> getTestCompilationClasspath() {
        return Arrays.asList(
            // MapStruct annotations in multi-module reactor build or IDE
            "core" + File.separator + "target",
            // MapStruct annotations in single module build
            "org" + File.separator + "mapstruct" + File.separator + "mapstruct" + File.separator,
                        "guava" );
    }

    @Override
    public Collection<String> getProcessorClasspath() {
        return Arrays.asList(
            "processor" + File.separator + "target",  // the processor itself,
            "freemarker",
                        "gem-api" );
    }

    @Override
    public InputStream getCheckStyleConfiguration() {
        return getClass().getClassLoader().getResourceAsStream( "checkstyle-for-generated-sources.xml" );
    }
}
