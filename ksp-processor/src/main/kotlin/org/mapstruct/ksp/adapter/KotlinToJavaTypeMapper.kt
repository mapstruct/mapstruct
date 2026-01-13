// ABOUTME: Maps Kotlin built-in type qualified names to their Java equivalents
// ABOUTME: Prevents generation of invalid imports like "kotlin.String" in Java code
package org.mapstruct.ksp.adapter

/**
 * Maps Kotlin built-in type qualified names to their Java equivalents.
 * This is necessary because KSP returns Kotlin type names (e.g., kotlin.String)
 * but the generated Java code needs Java type names (e.g., java.lang.String).
 */
internal object KotlinToJavaTypeMapper {
    
    private val kotlinToJavaMap = mapOf(
        "kotlin.String" to "java.lang.String",
        "kotlin.Int" to "java.lang.Integer",
        "kotlin.Long" to "java.lang.Long",
        "kotlin.Short" to "java.lang.Short",
        "kotlin.Byte" to "java.lang.Byte",
        "kotlin.Float" to "java.lang.Float",
        "kotlin.Double" to "java.lang.Double",
        "kotlin.Boolean" to "java.lang.Boolean",
        "kotlin.Char" to "java.lang.Character",
        "kotlin.Array" to "java.lang.Object[]",
        "kotlin.IntArray" to "int[]",
        "kotlin.LongArray" to "long[]",
        "kotlin.ShortArray" to "short[]",
        "kotlin.ByteArray" to "byte[]",
        "kotlin.FloatArray" to "float[]",
        "kotlin.DoubleArray" to "double[]",
        "kotlin.BooleanArray" to "boolean[]",
        "kotlin.CharArray" to "char[]",
        "kotlin.collections.List" to "java.util.List",
        "kotlin.collections.Set" to "java.util.Set",
        "kotlin.collections.Map" to "java.util.Map",
        "kotlin.collections.Collection" to "java.util.Collection",
        "kotlin.collections.Iterable" to "java.lang.Iterable",
        "kotlin.collections.Iterator" to "java.util.Iterator",
        "kotlin.collections.MutableList" to "java.util.List",
        "kotlin.collections.MutableSet" to "java.util.Set",
        "kotlin.collections.MutableMap" to "java.util.Map",
        "kotlin.collections.MutableCollection" to "java.util.Collection",
        "kotlin.collections.MutableIterable" to "java.lang.Iterable",
        "kotlin.collections.MutableIterator" to "java.util.Iterator",
        "kotlin.Unit" to "java.lang.Void",
        "kotlin.Nothing" to "java.lang.Void",
        "kotlin.Any" to "java.lang.Object",
        "kotlin.Throwable" to "java.lang.Throwable",
        "kotlin.Exception" to "java.lang.Exception",
        "kotlin.RuntimeException" to "java.lang.RuntimeException",
        "kotlin.Error" to "java.lang.Error",
        "kotlin.Comparable" to "java.lang.Comparable",
        "kotlin.Enum" to "java.lang.Enum",
        "kotlin.Annotation" to "java.lang.annotation.Annotation"
    )
    
    /**
     * Maps a Kotlin qualified type name to its Java equivalent.
     * Returns the original name if no mapping is found.
     */
    fun mapToJavaType(kotlinQualifiedName: String): String {
        return kotlinToJavaMap[kotlinQualifiedName] ?: kotlinQualifiedName
    }
}
