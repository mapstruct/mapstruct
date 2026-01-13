/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
@file:OptIn(ExperimentalCompilerApi::class)

package org.mapstruct.ksp.test

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import com.tschuchort.compiletesting.useKsp2
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.mapstruct.ksp.MapStructSymbolProcessorProvider

/**
 * DSL for testing KSP processor with embedded assertions.
 * 
 * Example:
 * ```kotlin
 * assertThis(
 *     CompilerTest(
 *         code = {
 *             """
 *             package test
 *             
 *             import org.mapstruct.Mapper
 *             
 *             data class Source(val name: String)
 *             data class Target(val name: String)
 *             
 *             @Mapper
 *             interface MyMapper {
 *                 fun map(source: Source): Target
 *             }
 *             
 *             // Assertions
 *             fun testMapping() {
 *                 val mapper = MyMapperImpl()
 *                 val source = Source("John")
 *                 val target = mapper.map(source)
 *                 assert(target.name == "John") { "Expected name to be John" }
 *             }
 *             """
 *         },
 *         assert = {
 *             compiles
 *             runFunction("test.testMapping")
 *         }
 *     )
 * )
 * ```
 */
class CompilerTest(
    val code: CodeContext.() -> String,
    val assert: AssertContext.() -> Unit
)

/**
 * Context for defining source code.
 */
class CodeContext {
    internal val sources = mutableListOf<Source>()
    
    fun sources(vararg sources: Source) {
        this.sources.addAll(sources)
    }
    
    data class Source(
        val filename: String,
        val text: String
    )
}

/**
 * Context for assertions on compilation results.
 */
class AssertContext internal constructor(
    private val result: JvmCompilationResult,
    private val originalSources: List<CodeContext.Source>
) {
    /**
     * Assert that compilation succeeded.
     */
    val compiles: Unit
        @OptIn(ExperimentalCompilerApi::class)
        get() {
            if (result.exitCode != KotlinCompilation.ExitCode.OK) {
                throw AssertionError("Expected compilation to succeed but it failed:\n${result.messages}")
            }
        }
    
    /**
     * Assert that compilation failed.
     */
    val fails: Unit
        @OptIn(ExperimentalCompilerApi::class)
        get() {
            if (result.exitCode == KotlinCompilation.ExitCode.OK) {
                throw AssertionError("Expected compilation to fail but it succeeded")
            }
        }
    
    /**
     * Assert that compilation failed with a specific message.
     */
    fun failsWith(predicate: (String) -> Boolean) {
        if (!predicate(result.messages)) {
            throw AssertionError("Expected compilation to fail with specific message but got:\n${result.messages}")
        }
    }
    
    /**
     * Run a function by its fully qualified name.
     */
    fun runFunction(functionFqn: String) {
        val (packageName, functionName) = parseFunctionFqn(functionFqn)
        
        // Kotlin compiles top-level functions into a class named after the file
        // Try multiple possible class names
        val possibleClassNames = if (packageName.isEmpty()) {
            listOf("TestSourceKt", "TestSource0Kt")
        } else {
            listOf("$packageName.TestSourceKt", "$packageName.TestSource0Kt")
        }
        
        var lastException: Exception? = null
        for (className in possibleClassNames) {
            try {
                val klass = result.classLoader.loadClass(className)
                val method = klass.getDeclaredMethod(functionName)
                method.invoke(null)
                return // Success
            } catch (e: ClassNotFoundException) {
                lastException = e
                continue
            } catch (e: NoSuchMethodException) {
                lastException = e
                continue
            }
        }
        
        throw AssertionError("Failed to run function $functionFqn. Tried classes: $possibleClassNames", lastException)
    }
    
    /**
     * Evaluate an expression and check its value.
     */
    fun String.evalsTo(expected: Any?) {
        val actual = evalExpression(this)
        if (actual != expected) {
            throw AssertionError("Expected expression '$this' to evaluate to '$expected' but got '$actual'")
        }
    }
    
    /**
     * Combine multiple assertions.
     */
    fun allOf(vararg assertions: AssertContext.() -> Unit) {
        assertions.forEach { it() }
    }
    
    @OptIn(ExperimentalCompilerApi::class)
    private fun evalExpression(expression: String): Any? {
        // Extract package name from original sources
        val packageName = extractPackageNameFromSources()
        
        // Create a wrapper function that evaluates the expression
        val wrapperSource = SourceFile.kotlin(
            "ExpressionEvaluator.kt",
            """
            ${if (packageName.isNotEmpty()) "package $packageName" else ""}
            
            fun evaluateExpression(): Any? = $expression
            """.trimIndent()
        )
        
        // Convert original sources to SourceFile objects
        val sourceFiles = originalSources.map { source ->
            SourceFile.kotlin(source.filename, source.text)
        }
        
        // Compile the wrapper with original sources
        val evalCompilation = KotlinCompilation().apply {
            useKsp2()
            sources = sourceFiles + wrapperSource
            symbolProcessorProviders = mutableListOf(MapStructSymbolProcessorProvider())
            inheritClassPath = true
            messageOutputStream = System.out
        }
        
        val evalResult = evalCompilation.compile()
        if (evalResult.exitCode != KotlinCompilation.ExitCode.OK) {
            throw AssertionError("Failed to compile expression: $expression\n${evalResult.messages}")
        }
        
        // Load and invoke the wrapper function
        val className = if (packageName.isEmpty()) {
            "ExpressionEvaluatorKt"
        } else {
            "$packageName.ExpressionEvaluatorKt"
        }
        val wrapperClass = evalResult.classLoader.loadClass(className)
        val evalMethod = wrapperClass.getDeclaredMethod("evaluateExpression")
        
        return evalMethod.invoke(null)
    }
    
    private fun extractPackageNameFromSources(): String {
        // Extract from first source file
        val firstSource = originalSources.firstOrNull() ?: return ""
        val packageMatch = Regex("package\\s+([\\w.]+)").find(firstSource.text)
        return packageMatch?.groupValues?.get(1) ?: ""
    }
    
    private fun parseFunctionFqn(fqn: String): Pair<String, String> {
        val lastDot = fqn.lastIndexOf('.')
        return if (lastDot == -1) {
            "" to fqn
        } else {
            fqn.substring(0, lastDot) to fqn.substring(lastDot + 1)
        }
    }
}

/**
 * Simplified DSL for testing KSP processor with sensible defaults.
 *
 * Example:
 * ```kotlin
 * @Test
 * fun myTest() = pluginTest("""
 *     import org.mapstruct.Mapper
 *
 *     data class Source(val name: String)
 *     data class Target(val name: String)
 *
 *     @Mapper
 *     interface MyMapper {
 *         fun map(source: Source): Target
 *     }
 *
 *     fun test() {
 *         val mapper = MyMapperImpl()
 *         val source = Source("John")
 *         val target = mapper.map(source)
 *         assert(target.name == "John")
 *     }
 * """)
 * ```
 */
@OptIn(ExperimentalCompilerApi::class)
fun pluginTest(
    @Language("kotlin") code: String,
    assert: AssertContext.() -> Unit = {
        compiles
        runFunction("test")
    }
) {
    assertThis(
        CompilerTest(
            code = { code },
            assert = assert
        )
    )
}

/**
 * Execute a compiler test.
 */
@OptIn(ExperimentalCompilerApi::class)
fun assertThis(test: CompilerTest) {
    // Gather sources
    val codeContext = CodeContext()
    val sourceCode = test.code(codeContext)

    // If no explicit sources were added, use the returned string as the source
    if (codeContext.sources.isEmpty()) {
        codeContext.sources.add(
            CodeContext.Source(
                filename = "TestSource.kt",
                text = sourceCode.trimIndent()
            )
        )
    }

    // Convert to SourceFile objects
    val sourceFiles = codeContext.sources.map { source ->
        SourceFile.kotlin(source.filename, source.text)
    }

    // Compile
    val compilation = KotlinCompilation().apply {
        useKsp2()
        sources = sourceFiles
        symbolProcessorProviders = mutableListOf(MapStructSymbolProcessorProvider())
        inheritClassPath = true
        messageOutputStream = System.out
    }

    val result = compilation.compile()

    // Create assertion context with access to original sources
    val assertContext = AssertContext(result, codeContext.sources)

    // Run assertions
    test.assert(assertContext)
}
