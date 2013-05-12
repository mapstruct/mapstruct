/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.testutil.compilation.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Represents the outcome of a compilation.
 *
 * @author Gunnar Morling
 */
public class CompilationOutcomeDescriptor {

    private CompilationResult compilationResult;
    private Set<DiagnosticDescriptor> diagnostics;

    private CompilationOutcomeDescriptor(CompilationResult compilationResult,
                                         Set<DiagnosticDescriptor> diagnostics) {
        this.compilationResult = compilationResult;
        this.diagnostics = diagnostics;
    }

    public static CompilationOutcomeDescriptor forExpectedCompilationResult(ExpectedCompilationOutcome expectedCompilationResult) {
        if ( expectedCompilationResult == null ) {
            return new CompilationOutcomeDescriptor(
                CompilationResult.SUCCEEDED,
                Collections.<DiagnosticDescriptor>emptySet()
            );
        }
        else {
            Set<DiagnosticDescriptor> diagnosticDescriptors = new HashSet<DiagnosticDescriptor>();
            for ( Diagnostic diagnostic : expectedCompilationResult.diagnostics() ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forDiagnostic( diagnostic ) );
            }

            return new CompilationOutcomeDescriptor( expectedCompilationResult.value(), diagnosticDescriptors );
        }
    }

    public static CompilationOutcomeDescriptor forResult(String sourceDir, boolean compilationSuccessful, List<javax.tools.Diagnostic<? extends JavaFileObject>> diagnostics) {
        CompilationResult compilationResult = compilationSuccessful ? CompilationResult.SUCCEEDED : CompilationResult.FAILED;

        Set<DiagnosticDescriptor> diagnosticDescriptors = new HashSet<DiagnosticDescriptor>();
        for ( javax.tools.Diagnostic<? extends JavaFileObject> diagnostic : diagnostics ) {
            //ignore notes created by the compiler
            if ( diagnostic.getKind() != Kind.NOTE ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forDiagnostic( sourceDir, diagnostic ) );
            }
        }

        return new CompilationOutcomeDescriptor( compilationResult, diagnosticDescriptors );
    }

    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    public Set<DiagnosticDescriptor> getDiagnostics() {
        return diagnostics;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
            * result
            + ( ( compilationResult == null ) ? 0 : compilationResult
            .hashCode() );
        result = prime * result
            + ( ( diagnostics == null ) ? 0 : diagnostics.hashCode() );
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
        CompilationOutcomeDescriptor other = (CompilationOutcomeDescriptor) obj;
        if ( compilationResult != other.compilationResult ) {
            return false;
        }
        if ( diagnostics == null ) {
            if ( other.diagnostics != null ) {
                return false;
            }
        }
        else if ( !diagnostics.equals( other.diagnostics ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompilationResultDescriptor [compilationResult="
            + compilationResult + ", diagnostics=" + diagnostics + "]";
    }
}
