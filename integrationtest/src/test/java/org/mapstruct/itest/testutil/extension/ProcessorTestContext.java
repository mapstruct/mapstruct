/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.extension;

/**
 * @author Filip Hrisafov
 */
public class ProcessorTestContext {

    private final String baseDir;
    private final ProcessorTest.ProcessorType processor;
    private final Class<? extends ProcessorTest.CommandLineEnhancer> cliEnhancerClass;
    private final boolean forkJvm;

    public ProcessorTestContext(String baseDir,
        ProcessorTest.ProcessorType processor,
        Class<? extends ProcessorTest.CommandLineEnhancer> cliEnhancerClass,
        boolean forkJvm) {
        this.baseDir = baseDir;
        this.processor = processor;
        this.cliEnhancerClass = cliEnhancerClass;
        this.forkJvm = forkJvm;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public ProcessorTest.ProcessorType getProcessor() {
        return processor;
    }

    public Class<? extends ProcessorTest.CommandLineEnhancer> getCliEnhancerClass() {
        return cliEnhancerClass;
    }

    public boolean isForkJvm() {
        return forkJvm;
    }
}
