// ABOUTME: Adapts KSP's CodeGenerator to implement javax.annotation.processing.Filer interface.
// ABOUTME: Bridges between KSP file generation API and Java annotation processing Filer API.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import javax.annotation.processing.Filer
import javax.lang.model.element.Element
import javax.tools.FileObject
import javax.tools.JavaFileManager
import javax.tools.JavaFileObject
import java.io.IOException

/**
 * Adapts KSP's [CodeGenerator] to implement [Filer] interface.
 * Routes file creation requests from Java annotation processing API to KSP code generation.
 */
class KspFilerAdapter(
    private val codeGenerator: CodeGenerator
) : Filer {

    override fun createSourceFile(
        name: CharSequence,
        vararg originatingElements: Element
    ): JavaFileObject {
        // Extract package and class name
        val qualifiedName = name.toString()
        val lastDot = qualifiedName.lastIndexOf('.')
        val packageName = if (lastDot > 0) qualifiedName.substring(0, lastDot) else ""
        val className = if (lastDot > 0) qualifiedName.substring(lastDot + 1) else qualifiedName

        // Create KSP dependencies from originating elements
        val dependencies = Dependencies(
            aggregating = false,
            // TODO: Convert originatingElements to KSFile instances
            *emptyArray()
        )

        // Create output stream via KSP CodeGenerator
        val outputStream = codeGenerator.createNewFile(
            dependencies = dependencies,
            packageName = packageName,
            fileName = className,
            extensionName = "java"
        )

        return KspJavaFileObject(
            qualifiedName = qualifiedName,
            outputStream = outputStream
        )
    }

    override fun createClassFile(
        name: CharSequence,
        vararg originatingElements: Element
    ): JavaFileObject {
        throw UnsupportedOperationException("createClassFile not supported in KSP")
    }

    override fun createResource(
        location: JavaFileManager.Location,
        pkg: CharSequence,
        relativeName: CharSequence,
        vararg originatingElements: Element
    ): FileObject {
        throw UnsupportedOperationException("createResource not fully supported in KSP adapter")
    }

    override fun getResource(
        location: JavaFileManager.Location,
        pkg: CharSequence,
        relativeName: CharSequence
    ): FileObject {
        throw UnsupportedOperationException("getResource not supported in KSP")
    }
}
