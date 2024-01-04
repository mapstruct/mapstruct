/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.compilation.model;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.codehaus.plexus.compiler.CompilerMessage;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;

/**
 * Represents a diagnostic occurred during a compilation.
 *
 * @author Gunnar Morling
 */
public class DiagnosticDescriptor {

    private final String sourceFileName;
    private final Kind kind;
    private final Long line;
    private final Long alternativeLine;
    private final String message;
    private final String messageRegex;

    private DiagnosticDescriptor(String sourceFileName, Kind kind, Long line, String message, String messageRegex) {
        this( sourceFileName, kind, line, null, message, messageRegex );
    }

    private DiagnosticDescriptor(String sourceFileName, Kind kind, Long line, Long alternativeLine, String message,
        String messageRegex) {
        this.sourceFileName = sourceFileName;
        this.kind = kind;
        this.line = line;
        this.alternativeLine = alternativeLine;
        this.message = message;
        this.messageRegex = messageRegex;
    }

    public static DiagnosticDescriptor forDiagnostic(Diagnostic diagnostic) {
        String sourceFileName = diagnostic.type() != void.class
            ? diagnostic.type().getName().replace( ".", File.separator ) + ".java"
            : null;
        return new DiagnosticDescriptor(
            sourceFileName,
            diagnostic.kind(),
            diagnostic.line() != -1 ? diagnostic.line() : null,
            diagnostic.alternativeLine() != -1 ? diagnostic.alternativeLine() : null,
            diagnostic.message(),
            diagnostic.messageRegExp()
        );
    }

    public static DiagnosticDescriptor forDiagnostic(String sourceDir,
                                                     javax.tools.Diagnostic<? extends JavaFileObject> diagnostic) {
        return new DiagnosticDescriptor(
            getSourceName( sourceDir, diagnostic ),
            diagnostic.getKind(),
            diagnostic.getLineNumber(),
            diagnostic.getMessage( null ),
            null
        );
    }

    public static DiagnosticDescriptor forCompilerMessage(String sourceDir, CompilerMessage compilerMessage) {
        String[] lines = compilerMessage.getMessage().split( System.lineSeparator() );
        String message = lines[3];

        return new DiagnosticDescriptor(
            removeSourceDirPrefix( sourceDir, compilerMessage.getFile() ),
            toJavaxKind( compilerMessage.getKind() ),
            Long.valueOf( compilerMessage.getStartLine() ),
            message,
            null
            );
    }

    private static Kind toJavaxKind(CompilerMessage.Kind kind) {
        switch ( kind ) {
            case ERROR:
                return Kind.ERROR;
            case MANDATORY_WARNING:
                return Kind.MANDATORY_WARNING;
            case NOTE:
                return Kind.NOTE;
            case OTHER:
                return Kind.OTHER;
            case WARNING:
                return Kind.WARNING;
            default:
                return null;
        }
    }

    private static String getSourceName(String sourceDir, javax.tools.Diagnostic<? extends JavaFileObject> diagnostic) {
        if ( diagnostic.getSource() == null ) {
            return null;
        }

        URI uri = getUri( diagnostic );

        try {
            String sourceName = new File( uri ).getCanonicalPath();
            return removeSourceDirPrefix( sourceDir, sourceName );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    private static String removeSourceDirPrefix(String sourceDir, String sourceName) {
        return sourceName.length() > sourceDir.length() ?
            sourceName.substring( sourceDir.length() + 1 ) :
            sourceName;
    }

    private static URI getUri(javax.tools.Diagnostic<? extends JavaFileObject> diagnostic) {
        URI uri = diagnostic.getSource().toUri();

        if ( uri.isAbsolute() ) {
            return uri;
        }

        //Make the URI absolute in case it isn't (the case with JDK 6)
        try {
            return new URI( "file:" + uri );
        }
        catch ( URISyntaxException e ) {
            throw new RuntimeException( e );
        }
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public Kind getKind() {
        return kind;
    }

    public Long getLine() {
        return line;
    }

    public Long getAlternativeLine() {
        return alternativeLine;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageRegex() {
        return messageRegex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( kind == null ) ? 0 : kind.hashCode() );
        result = prime * result + (int) ( line ^ ( line >>> 32 ) );
        result = prime * result + ( ( message == null ) ? 0 : message.hashCode() );
        result = prime * result
            + ( ( sourceFileName == null ) ? 0 : sourceFileName.hashCode() );
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
        DiagnosticDescriptor other = (DiagnosticDescriptor) obj;
        if ( kind != other.kind ) {
            return false;
        }
        if ( !Objects.equals( line, other.line ) ) {
            return false;
        }
        if ( !Objects.equals( message, other.message ) ) {
            return false;
        }
        if ( !Objects.equals( sourceFileName, other.sourceFileName ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String fileName = sourceFileName != null ?
            sourceFileName.substring( this.sourceFileName.lastIndexOf( File.separatorChar ) + 1 ) : null;
        return "DiagnosticDescriptor: " + kind + " " + fileName + ":" + line + " " + message;
    }
}
