/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.output.NullOutputStream;
import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.annotation.DisableCheckstyle;

import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;

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
    public void runAdditionalChecks(Class<?> testClass, String classOutputDir, String sourceOutputDir)
                    throws Exception {
        if ( !findAnnotation( testClass, DisableCheckstyle.class ).isPresent() ) {
            assertCheckstyleRules( classOutputDir, sourceOutputDir );
        }
    }

    private void assertCheckstyleRules(String classOutputDir, String sourceOutputDir) throws Exception {
        InputStream checkStyle =
                        getClass().getClassLoader().getResourceAsStream( "checkstyle-for-generated-sources.xml" );
        if ( sourceOutputDir != null && checkStyle != null ) {
            Properties properties = new Properties();
            properties.put( "checkstyle.cache.file", classOutputDir + "/checkstyle.cache" );

            final Checker checker = new Checker();
            checker.setModuleClassLoader( Checker.class.getClassLoader() );
            checker
            .configure(
                ConfigurationLoader
                .loadConfiguration(
                    new InputSource( checkStyle ),
                    new PropertiesExpander( properties ),
                    ConfigurationLoader.IgnoredModulesOptions.OMIT ) );

            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            checker
            .addListener(
                new DefaultLogger(
                    NullOutputStream.NULL_OUTPUT_STREAM,
                    AutomaticBean.OutputStreamOptions.CLOSE,
                    errorStream,
                    AutomaticBean.OutputStreamOptions.CLOSE ) );

            int errors = checker.process( findGeneratedFiles( new File( sourceOutputDir ) ) );
            assertThat( errors )
            .withFailMessage(
                () -> {
                    try {
                        return "Expected checkstyle compliant output, but got errors:\n"
                                        + errorStream.toString( "UTF-8" );
                    }
                    catch ( UnsupportedEncodingException e ) {
                        return "Expected checkstyle compliant output, but got errors:\n"
                                        + errorStream.toString();
                    }
                } )
            .isZero();
        }
    }

    private static List<File> findGeneratedFiles(File file) {
        final List<File> files = new LinkedList<>();

        if ( file.canRead() ) {
            if ( file.isDirectory() ) {
                for ( File element : file.listFiles() ) {
                    files.addAll( findGeneratedFiles( element ) );
                }
            }
            else if ( file.isFile() ) {
                files.add( file );
            }
        }
        return files;
    }
}
