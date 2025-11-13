// ABOUTME: Adapter that bridges KSP's CodeGenerator to javax.annotation.processing.Filer interface.
// ABOUTME: Enables MapStruct's existing file writing infrastructure to work with KSP code generation.
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer
import java.net.URI
import javax.annotation.processing.Filer
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.NestingKind
import javax.tools.FileObject
import javax.tools.JavaFileManager
import javax.tools.JavaFileObject

class KspFiler(private val codeGenerator: CodeGenerator) : Filer {

    override fun createSourceFile(
        name: CharSequence?,
        vararg originatingElements: Element?
    ): JavaFileObject {
        requireNotNull(name) { "Source file name cannot be null" }

        // Extract package name and simple name from fully qualified name
        val qualifiedName = name.toString()
        val lastDot = qualifiedName.lastIndexOf('.')
        val packageName = if (lastDot > 0) qualifiedName.substring(0, lastDot) else ""
        val simpleName = if (lastDot > 0) qualifiedName.substring(lastDot + 1) else qualifiedName

        // Collect originating KSFile sources
        val originatingFiles = originatingElements
            .filterNotNull()
            .mapNotNull { element ->
                when (element) {
                    is KspClassTypeElement -> element.declaration.containingFile
                    else -> null
                }
            }
            .toList()

        val dependencies = if (originatingFiles.isNotEmpty()) {
            Dependencies(aggregating = false, *originatingFiles.toTypedArray())
        } else {
            Dependencies.ALL_FILES
        }

        // Create output stream via KSP CodeGenerator
        val outputStream = codeGenerator.createNewFile(
            dependencies = dependencies,
            packageName = packageName,
            fileName = simpleName,
            extensionName = "java"
        )

        return KspJavaFileObject(qualifiedName, outputStream)
    }

    override fun createClassFile(
        name: CharSequence?,
        vararg originatingElements: Element?
    ): JavaFileObject {
        throw UnsupportedOperationException("createClassFile is not supported in KSP")
    }

    override fun createResource(
        location: JavaFileManager.Location?,
        moduleAndPkg: CharSequence?,
        relativeName: CharSequence?,
        vararg originatingElements: Element?
    ): FileObject {
        requireNotNull(relativeName) { "Resource name cannot be null" }

        // Extract originating KSFile sources
        val originatingFiles = originatingElements
            .filterNotNull()
            .mapNotNull { element ->
                when (element) {
                    is KspClassTypeElement -> element.declaration.containingFile
                    else -> null
                }
            }
            .toList()

        val dependencies = if (originatingFiles.isNotEmpty()) {
            Dependencies(aggregating = false, *originatingFiles.toTypedArray())
        } else {
            Dependencies.ALL_FILES
        }

        // For resources, use the relative name as-is
        val resourcePath = relativeName.toString()

        // Parse package and file name from path (e.g., "META-INF/services/ServiceName")
        val lastSlash = resourcePath.lastIndexOf('/')
        val packageName = if (lastSlash > 0) resourcePath.substring(0, lastSlash).replace('/', '.') else ""
        val fileName = if (lastSlash >= 0) resourcePath.substring(lastSlash + 1) else resourcePath

        val outputStream = codeGenerator.createNewFile(
            dependencies = dependencies,
            packageName = packageName,
            fileName = fileName,
            extensionName = ""
        )

        return KspFileObject(resourcePath, outputStream)
    }

    override fun getResource(
        location: JavaFileManager.Location?,
        moduleAndPkg: CharSequence?,
        relativeName: CharSequence?
    ): FileObject {
        throw UnsupportedOperationException("getResource is not supported in KSP")
    }
}

/**
 * Adapter that wraps a KSP OutputStream as a JavaFileObject.
 */
private class KspJavaFileObject(
    private val qualifiedName: String,
    private val outputStream: OutputStream
) : JavaFileObject {

    override fun getKind(): JavaFileObject.Kind = JavaFileObject.Kind.SOURCE

    override fun isNameCompatible(simpleName: String, kind: JavaFileObject.Kind): Boolean {
        val name = qualifiedName.substringAfterLast('.')
        return name == simpleName && kind == JavaFileObject.Kind.SOURCE
    }

    override fun getNestingKind(): NestingKind? = null

    override fun getAccessLevel(): Modifier? = null

    override fun toUri(): URI = URI.create("ksp:///$qualifiedName")

    override fun getName(): String = qualifiedName

    override fun openInputStream(): InputStream {
        throw UnsupportedOperationException("Reading from JavaFileObject is not supported")
    }

    override fun openOutputStream(): OutputStream = outputStream

    override fun openReader(ignoreEncodingErrors: Boolean): Reader {
        throw UnsupportedOperationException("Reading from JavaFileObject is not supported")
    }

    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence {
        throw UnsupportedOperationException("Reading from JavaFileObject is not supported")
    }

    override fun openWriter(): Writer = outputStream.writer(Charsets.UTF_8)

    override fun getLastModified(): Long = 0L

    override fun delete(): Boolean = false
}

/**
 * Adapter that wraps a KSP OutputStream as a FileObject (for resources).
 */
private class KspFileObject(
    private val name: String,
    private val outputStream: OutputStream
) : FileObject {

    override fun toUri(): URI = URI.create("ksp:///$name")

    override fun getName(): String = name

    override fun openInputStream(): InputStream {
        throw UnsupportedOperationException("Reading from FileObject is not supported")
    }

    override fun openOutputStream(): OutputStream = outputStream

    override fun openReader(ignoreEncodingErrors: Boolean): Reader {
        throw UnsupportedOperationException("Reading from FileObject is not supported")
    }

    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence {
        throw UnsupportedOperationException("Reading from FileObject is not supported")
    }

    override fun openWriter(): Writer = outputStream.writer(Charsets.UTF_8)

    override fun getLastModified(): Long = 0L

    override fun delete(): Boolean = false
}
