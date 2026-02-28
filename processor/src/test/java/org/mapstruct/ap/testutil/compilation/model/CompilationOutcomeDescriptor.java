/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.compilation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.codehaus.plexus.compiler.CompilerMessage;
import org.codehaus.plexus.compiler.CompilerResult;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.messages.MessageCollectorImpl;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedNote;

/**
 * Represents the outcome of a compilation.
 *
 * @author Gunnar Morling
 */
public class CompilationOutcomeDescriptor {

    private CompilationResult compilationResult;
    private List<DiagnosticDescriptor> diagnostics;
    private List<String> notes;

    private CompilationOutcomeDescriptor(CompilationResult compilationResult,
                                         List<DiagnosticDescriptor> diagnostics,
                                         List<String> notes) {
        this.compilationResult = compilationResult;
        this.diagnostics = diagnostics;
        this.notes = notes;
    }

    public static CompilationOutcomeDescriptor forExpectedCompilationResult(
        ExpectedCompilationOutcome expectedCompilationResult, ExpectedNote.ExpectedNotes expectedNotes,
        ExpectedNote expectedNote) {
        List<String> notes = new ArrayList<>();
        if ( expectedNotes != null ) {
            notes.addAll( Stream.of( expectedNotes.value() )
                .map( ExpectedNote::value )
                .collect( Collectors.toList() ) );
        }
        if ( expectedNote != null ) {
            notes.add( expectedNote.value() );
        }
        if ( expectedCompilationResult == null ) {
            return new CompilationOutcomeDescriptor(
                CompilationResult.SUCCEEDED,
                Collections.emptyList(),
                notes
            );
        }
        else {
            List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<>();
            for ( org.mapstruct.ap.testutil.compilation.annotation.Diagnostic diagnostic :
                expectedCompilationResult.diagnostics() ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forDiagnostic( diagnostic ) );
            }
            return new CompilationOutcomeDescriptor( expectedCompilationResult.value(), diagnosticDescriptors, notes );
        }
    }

    public static CompilationOutcomeDescriptor forResult(String sourceDir, boolean compilationSuccessful,
                                                         List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        CompilationResult compilationResult =
            compilationSuccessful ? CompilationResult.SUCCEEDED : CompilationResult.FAILED;
        List<String> notes = new ArrayList<>();
        List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<>();
        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics ) {
            //ignore notes created by the compiler
            if ( diagnostic.getKind() != Kind.NOTE ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forDiagnostic( sourceDir, diagnostic ) );
            }
            else {
                notes.add( diagnostic.getMessage( null ) );
            }
        }

        return new CompilationOutcomeDescriptor( compilationResult, diagnosticDescriptors, notes );
    }

    public static CompilationOutcomeDescriptor forResult(String sourceDir, CompilerResult compilerResult) {
        CompilationResult compilationResult =
            compilerResult.isSuccess() ? CompilationResult.SUCCEEDED : CompilationResult.FAILED;
        List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<>();

        for ( CompilerMessage message : compilerResult.getCompilerMessages() ) {
            if ( message.getKind() != CompilerMessage.Kind.NOTE ) {
                diagnosticDescriptors.add( DiagnosticDescriptor.forCompilerMessage( sourceDir, message ) );
            }
            // the eclipse compiler does not support NOTE (it is never actually set).
        }

        return new CompilationOutcomeDescriptor( compilationResult, diagnosticDescriptors, Collections.emptyList() );
    }

    public static CompilationOutcomeDescriptor forResult(String sourceDir, ExitCode exitCode,
                                                         List<MessageCollectorImpl.Message> messages) {
        List<String> notes = new ArrayList<>();
        List<DiagnosticDescriptor> diagnosticDescriptors = new ArrayList<>( messages.size() );
        for ( MessageCollectorImpl.Message message : messages ) {
            //ignore notes created by the compiler
            DiagnosticDescriptor descriptor = DiagnosticDescriptor.forCompilerMessage( sourceDir, message );
            if ( descriptor.getKind() != Kind.NOTE ) {
                diagnosticDescriptors.add( descriptor );
            }
            else {
                notes.add( descriptor.getMessage() );
            }
        }
        CompilationResult compilationResult =
            exitCode == ExitCode.OK ? CompilationResult.SUCCEEDED : CompilationResult.FAILED;
        return new CompilationOutcomeDescriptor( compilationResult, diagnosticDescriptors, notes );
    }

    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    public List<DiagnosticDescriptor> getDiagnostics() {
        return diagnostics;
    }

    public List<String> getNotes() {
        return notes;
    }

    public CompilationOutcomeDescriptor merge(CompilationOutcomeDescriptor other) {
        CompilationResult compilationResult =
            this.compilationResult == CompilationResult.SUCCEEDED ? other.compilationResult : this.compilationResult;
        List<DiagnosticDescriptor> diagnostics = new ArrayList<>( this.diagnostics );
        diagnostics.addAll( other.diagnostics );
        List<String> notes = new ArrayList<>( this.notes );
        notes.addAll( other.notes );
        return new CompilationOutcomeDescriptor( compilationResult, diagnostics, notes );
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
        if ( !Objects.equals( diagnostics, other.diagnostics ) ) {
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
