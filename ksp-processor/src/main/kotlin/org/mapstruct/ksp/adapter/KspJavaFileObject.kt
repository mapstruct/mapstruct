// ABOUTME: Implements JavaFileObject for KSP-generated source files.
// ABOUTME: Wraps KSP's output stream in JavaFileObject interface for compatibility with existing code generation.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.adapter

import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer
import java.net.URI
import javax.lang.model.element.Modifier
import javax.lang.model.element.NestingKind
import javax.tools.JavaFileObject

/**
 * Implementation of [JavaFileObject] that wraps KSP's output stream.
 * Provides minimal implementation needed for MapStruct code generation.
 */
class KspJavaFileObject(
    private val qualifiedName: String,
    private val outputStream: OutputStream
) : JavaFileObject {

    override fun toUri(): URI {
        return URI.create("ksp:///$qualifiedName")
    }

    override fun getName(): String {
        return qualifiedName
    }

    override fun openInputStream(): InputStream {
        throw UnsupportedOperationException("Reading not supported")
    }

    override fun openOutputStream(): OutputStream {
        return outputStream
    }

    override fun openReader(ignoreEncodingErrors: Boolean): Reader {
        throw UnsupportedOperationException("Reading not supported")
    }

    override fun openWriter(): Writer {
        return outputStream.writer(Charsets.UTF_8)
    }

    override fun getLastModified(): Long {
        return System.currentTimeMillis()
    }

    override fun delete(): Boolean {
        return false
    }

    override fun getKind(): JavaFileObject.Kind {
        return JavaFileObject.Kind.SOURCE
    }

    override fun isNameCompatible(simpleName: String, kind: JavaFileObject.Kind): Boolean {
        val baseName = qualifiedName.substringAfterLast('.')
        return baseName == simpleName && this.kind == kind
    }

    override fun getNestingKind(): NestingKind? {
        return null
    }

    override fun getAccessLevel(): Modifier? {
        return null
    }

    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence {
        throw UnsupportedOperationException("Reading not supported")
    }
}
