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

import java.io.File;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;

/**
 * Represents a diagnostic ocurred during a compilation.
 *
 * @author Gunnar Morling
 */
public class DiagnosticDescriptor {

    private final String sourceFileName;
    private final Kind kind;
    private final long line;
    private final String message;

    private DiagnosticDescriptor(String sourceFileName, Kind kind, long line, String message) {
        this.sourceFileName = sourceFileName;
        this.kind = kind;
        this.line = line;
        this.message = message;
    }

    public static DiagnosticDescriptor forDiagnostic(Diagnostic diagnostic) {
        String soureFileName = diagnostic.type().getName().replaceAll( "\\.", File.separator ) + ".java";
        return new DiagnosticDescriptor( soureFileName, diagnostic.kind(), diagnostic.line(), "" );
    }

    public static DiagnosticDescriptor forDiagnostic(String sourceDir, javax.tools.Diagnostic<? extends JavaFileObject> diagnostic) {
        return new DiagnosticDescriptor(
            diagnostic.getSource().getName().substring( sourceDir.length() + 1 ),
            diagnostic.getKind(),
            diagnostic.getLineNumber(),
            ""
        );
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public Kind getKind() {
        return kind;
    }

    public long getLine() {
        return line;
    }

    public String getMessage() {
        return message;
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
        if ( line != other.line ) {
            return false;
        }
        if ( message == null ) {
            if ( other.message != null ) {
                return false;
            }
        }
        else if ( !message.equals( other.message ) ) {
            return false;
        }
        if ( sourceFileName == null ) {
            if ( other.sourceFileName != null ) {
                return false;
            }
        }
        else if ( !sourceFileName.equals( other.sourceFileName ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiagnosticDescriptor [sourceFileName=" + sourceFileName
            + ", kind=" + kind + ", line=" + line + ", message=" + message
            + "]";
    }
}
