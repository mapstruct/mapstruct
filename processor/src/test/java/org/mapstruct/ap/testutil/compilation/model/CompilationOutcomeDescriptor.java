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
package org.mapstruct.ap.testutil.compilation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.codehaus.plexus.compiler.CompilerMessage;
import org.codehaus.plexus.compiler.CompilerResult;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Represents the outcome of a compilation.
 *
 * @author Gunnar Morling
 */
public class CompilationOutcomeDescriptor {

    private CompilationResult compilationResult;
    private List<DiagnosticDescriptor> diagnostics;

    private CompilationOutcomeDescriptor(CompilationResult compilationResult,
                                         List<DiagnosticDescriptor> diagnostics) {
        this.compilationResult = compilationResult;
        this.diagnostics = diagnostics;
    }

    public static CompilationOutcomeDescriptor forExpectedCompilationResult(
        ExpectedCompilationOutcome expectedCompilationResult) {
        if ( expectedCompilationResult == null ) {
            return new CompilationOutcomeDescriptor(
                CompilationResult.SUCCEEDED,
                Collections.<DiagnosticDescriptor>emptyList()
            );
        }
        else {
            List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<DiagnosticDescriptor>();
            for ( org.mapstruct.ap.testutil.compilation.annotation.Diagnostic diagnostic :
                expectedCompilationResult.diagnostics() ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forDiagnostic( diagnostic ) );
            }

            return new CompilationOutcomeDescriptor( expectedCompilationResult.value(), diagnosticDescriptors );
        }
    }

    public static CompilationOutcomeDescriptor forResult(String sourceDir, boolean compilationSuccessful,
                                                         List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        CompilationResult compilationResult =
            compilationSuccessful ? CompilationResult.SUCCEEDED : CompilationResult.FAILED;

        List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<DiagnosticDescriptor>();
        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics ) {
            //ignore notes created by the compiler
            if ( diagnostic.getKind() != Kind.NOTE ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forDiagnostic( sourceDir, diagnostic ) );
            }
        }

        return new CompilationOutcomeDescriptor( compilationResult, diagnosticDescriptors );
    }

    public static CompilationOutcomeDescriptor forResult(String sourceDir, CompilerResult compilerResult) {
        CompilationResult compilationResult =
            compilerResult.isSuccess() ? CompilationResult.SUCCEEDED : CompilationResult.FAILED;

        List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<DiagnosticDescriptor>();

        for ( CompilerMessage message : compilerResult.getCompilerMessages() ) {
            if ( message.getKind() != CompilerMessage.Kind.NOTE ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forCompilerMessage( sourceDir, message ) );
            }
        }

        return new CompilationOutcomeDescriptor( compilationResult, diagnosticDescriptors );
    }

    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    public List<DiagnosticDescriptor> getDiagnostics() {
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
