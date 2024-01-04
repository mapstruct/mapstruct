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
                additionalExcludes.add( "org/mapstruct/ap/test/injectionstrategy/cdi/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/injectionstrategy/jakarta_cdi/**/*.java" );
                additionalExcludes.add( "org/mapstruct/ap/test/annotatewith/deprecated/jdk11/*.java" );
                if ( processorType == ProcessorTest.ProcessorType.ECLIPSE_JDT ) {
                    additionalExcludes.add(
                        "org/mapstruct/ap/test/selection/methodgenerics/wildcards/LifecycleIntersectionMapper.java" );
                }
                break;
            case JAVA_9:
                // TODO find out why this fails:
                additionalExcludes.add( "org/mapstruct/ap/test/collection/wildcard/BeanMapper.java" );
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
