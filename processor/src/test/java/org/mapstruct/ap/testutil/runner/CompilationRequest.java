/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a compilation task for a number of sources with given processor options.
 */
public class CompilationRequest {
    private final Compiler compiler;
    private final Set<Class<?>> sourceClasses;
    private final Map<Class<?>, Class<?>> services;
    private final List<String> processorOptions;
    private final Collection<String> testDependencies;

    CompilationRequest(Compiler compiler, Set<Class<?>> sourceClasses, Map<Class<?>, Class<?>> services,
                       List<String> processorOptions, Collection<String> testDependencies) {
        this.compiler = compiler;
        this.sourceClasses = sourceClasses;
        this.services = services;
        this.processorOptions = processorOptions;
        this.testDependencies = testDependencies;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( compiler == null ) ? 0 : compiler.hashCode() );
        result = prime * result + ( ( processorOptions == null ) ? 0 : processorOptions.hashCode() );
        result = prime * result + ( ( services == null ) ? 0 : services.hashCode() );
        result = prime * result + ( ( sourceClasses == null ) ? 0 : sourceClasses.hashCode() );
        result = prime * result + ( ( testDependencies == null ) ? 0 : testDependencies.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        CompilationRequest other = (CompilationRequest) obj;

        return compiler.equals( other.compiler )
            && processorOptions.equals( other.processorOptions )
            && services.equals( other.services )
            && testDependencies.equals( other.testDependencies )
            && sourceClasses.equals( other.sourceClasses );
    }

    public Set<Class<?>> getSourceClasses() {
        return sourceClasses;
    }

    public List<String> getProcessorOptions() {
        return processorOptions;
    }

    public Map<Class<?>, Class<?>> getServices() {
        return services;
    }

    public Collection<String> getTestDependencies() {
        return testDependencies;
    }

    public Compiler getCompiler() {
        return compiler;
    }
}
