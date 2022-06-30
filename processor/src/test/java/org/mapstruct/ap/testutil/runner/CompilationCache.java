/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
