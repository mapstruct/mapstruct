/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a compilation task for a number of sources with given processor options.
 */
public class CompilationRequest {
    private final Set<Class<?>> sourceClasses;
    private final Map<Class<?>, Class<?>> services;
    private final List<String> processorOptions;

    CompilationRequest(Set<Class<?>> sourceClasses, Map<Class<?>, Class<?>> services, List<String> processorOptions) {
        this.sourceClasses = sourceClasses;
        this.services = services;
        this.processorOptions = processorOptions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( processorOptions == null ) ? 0 : processorOptions.hashCode() );
        result = prime * result + ( ( services == null ) ? 0 : services.hashCode() );
        result = prime * result + ( ( sourceClasses == null ) ? 0 : sourceClasses.hashCode() );
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

        return processorOptions.equals( other.processorOptions )
            && services.equals( other.services )
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
}
