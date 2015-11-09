/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;

/**
 * Remembers the last {@link CompilationRequest} together with the outcome of that request. Consecutive tests that
 * request the same compilation can reuse the results from this holder.
 *
 * @author Andreas Gudian
 */
class CompilationCache {
    private String lastSourceOutputDir;
    private CompilationRequest lastRequest;
    private CompilationOutcomeDescriptor lastResult;

    public String getLastSourceOutputDir() {
        return lastSourceOutputDir;
    }

    public void setLastSourceOutputDir(String lastSourceOutputDir) {
        this.lastSourceOutputDir = lastSourceOutputDir;
    }

    public CompilationRequest getLastRequest() {
        return lastRequest;
    }

    public void update(CompilationRequest lastRequest, CompilationOutcomeDescriptor lastResult) {
        this.lastRequest = lastRequest;
        this.lastResult = lastResult;
    }

    public CompilationOutcomeDescriptor getLastResult() {
        return lastResult;
    }
}
