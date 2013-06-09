/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

/**
 * Possible issue reporting policies. Duplicates the enum of the same name from
 * the core module as this can't be referenced here.
 *
 * @author Gunnar Morling
 */
public enum ReportingPolicy {

    IGNORE( null, false, false ), WARN( Kind.WARNING, true, false ), ERROR( Kind.ERROR, true, true );

    private final Diagnostic.Kind diagnosticKind;
    private final boolean requiresReport;
    private final boolean failsBuild;

    private ReportingPolicy(Diagnostic.Kind diagnosticKind, boolean requiresReport, boolean failsBuild) {
        this.requiresReport = requiresReport;
        this.diagnosticKind = diagnosticKind;
        this.failsBuild = failsBuild;
    }

    public Diagnostic.Kind getDiagnosticKind() {
        return diagnosticKind;
    }

    public boolean requiresReport() {
        return requiresReport;
    }

    public boolean failsBuild() {
        return failsBuild;
    }
}
