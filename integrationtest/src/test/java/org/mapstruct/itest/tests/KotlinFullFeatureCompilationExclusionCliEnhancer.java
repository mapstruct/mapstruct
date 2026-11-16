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
 * @author Filip Hrisafov
 */
public final class KotlinFullFeatureCompilationExclusionCliEnhancer implements ProcessorTest.CommandLineEnhancer {
    @Override
    public Collection<String> getAdditionalCommandLineArguments(ProcessorTest.ProcessorType processorType,
                                                                JRE currentJreVersion) {
        List<String> additionalExcludes = new ArrayList<>();


        switch ( currentJreVersion ) {
            case JAVA_8:
                addJdkExclude( additionalExcludes, "jdk17" );
                addJdkExclude( additionalExcludes, "jdk21" );
                break;
            case JAVA_11:
                addJdkExclude( additionalExcludes, "jdk17" );
                addJdkExclude( additionalExcludes, "jdk21" );
                break;
            case JAVA_17:
                addJdkExclude( additionalExcludes, "jdk21" );
                break;
            default:
        }

        Collection<String> result = new ArrayList<>( additionalExcludes.size() );
        for ( int i = 0; i < additionalExcludes.size(); i++ ) {
            result.add( "-DadditionalExclude" + i + "=" + additionalExcludes.get( i ) );
        }

        return result;
    }

    private static void addJdkExclude(Collection<String> additionalExcludes, String jdk) {
        additionalExcludes.add( "org/mapstruct/ap/test/**/kotlin/**/" + jdk + "/**/*.java" );
        additionalExcludes.add( "org/mapstruct/ap/test/**/kotlin/**/" + jdk + "/**/*.kt" );
    }
}
