/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.condition.JRE;
import org.mapstruct.itest.testutil.extension.ProcessorTest;

/**
 * Adds explicit exclusions of test mappers that are known or expected to not work with specific compilers.
 *
 * @author Andreas Gudian
 */
public final class FullFeatureCompilationExclusionCliEnhancer implements ProcessorTest.CommandLineEnhancer {
    @Override
    public Collection<String> getAdditionalCommandLineArguments(ProcessorTest.ProcessorType processorType,
        JRE currentJreVersion) {
        List<String> additionalExcludes = new ArrayList<>();

        // SPI not working correctly here.. (not picked up)
        additionalExcludes.add( "org/mapstruct/ap/test/bugs/_1596/*.java" );
        additionalExcludes.add( "org/mapstruct/ap/test/bugs/_1801/*.java" );
        additionalExcludes.add( "org/mapstruct/ap/test/bugs/_3089/*.java" );

        switch ( currentJreVersion ) {
            case JAVA_8:
                additionalExcludes.add( "org/mapstruct/ap/test/**/spring/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/injectionstrategy/cdi/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/injectionstrategy/jakarta_cdi/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/annotatewith/deprecated/jdk11/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk21/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk17/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk17/**/*.java" );
                if ( processorType == ProcessorTest.ProcessorType.ECLIPSE_JDT ) {
                    additionalExcludes.add(
                        "org/mapstruct/ap/test/selection/methodgenerics/wildcards/LifecycleIntersectionMapper.java" );
                }
                // JDK 8 javac does not support @NullMarked applied to a method or constructor, so these
                // mappers (which use a method-level @NullMarked) cannot be compiled on Java 8.
                // see https://github.com/jspecify/jspecify/wiki/version-compatibility#issues-with-java-8
                additionalExcludes.add(
                        "org/mapstruct/ap/test/nullcheck/jspecify/JSpecifyInnerClassMapper.java" );
                additionalExcludes.add(
                        "org/mapstruct/ap/test/nullcheck/jspecify/DeepNestedJSpecifyMapper.java" );
                additionalExcludes.add(
                        "org/mapstruct/ap/test/nullcheck/jspecify/FqNameCollisionJSpecifyMapper.java" );
                additionalExcludes.add(
                        "org/mapstruct/ap/test/nullcheck/jspecify/annotation/*.java" );
                // JVMs pre 22 have problems reading type in use annotations
                // Here Java 8 does not see the nested class annotation
                // https://github.com/jspecify/jspecify/wiki/version-compatibility#issues-with-javac-before-version-22
                additionalExcludes.add( "org/mapstruct/ap/test/bugs/_4086/*" );
                break;
            case JAVA_11:
                additionalExcludes.add( "org/mapstruct/ap/test/**/spring/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk17/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk17/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk21/*.java" );
                break;
            case JAVA_17:
                additionalExcludes.add( "org/mapstruct/ap/test/**/jdk21/*.java" );
                break;
            default:
        }

        Collection<String> result = new ArrayList<>(additionalExcludes.size());
        for ( int i = 0; i < additionalExcludes.size(); i++ ) {
            result.add( "-DadditionalExclude" + i + "=" + additionalExcludes.get( i ) );
        }

        return result;
    }
}
