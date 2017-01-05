/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
