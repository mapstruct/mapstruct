/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.conversion

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for type conversion with KSP processor.
 */
class TypeConversionTest {

    @Test
    fun shouldConvertIntToString() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Int)
        data class Target(val value: String)

        @Mapper
        interface ConversionMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ConversionMapperImpl()
            val source = Source(42)
            val target = mapper.map(source)

            assert(target.value == "42") {
                "Expected value to be '42' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldConvertStringToInt() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: String)
        data class Target(val value: Int)

        @Mapper
        interface StringToIntMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = StringToIntMapperImpl()
            val source = Source("123")
            val target = mapper.map(source)

            assert(target.value == 123) {
                "Expected value to be 123 but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertIntToLong() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Int)
        data class Target(val value: Long)

        @Mapper
        interface IntToLongMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = IntToLongMapperImpl()
            val source = Source(42)
            val target = mapper.map(source)

            assert(target.value == 42L) {
                "Expected value to be 42L but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertLongToDouble() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Long)
        data class Target(val value: Double)

        @Mapper
        interface LongToDoubleMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = LongToDoubleMapperImpl()
            val source = Source(100L)
            val target = mapper.map(source)

            assert(target.value == 100.0) {
                "Expected value to be 100.0 but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertBooleanToString() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val flag: Boolean)
        data class Target(val flag: String)

        @Mapper
        interface BoolToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = BoolToStringMapperImpl()

            val trueSource = Source(true)
            val trueTarget = mapper.map(trueSource)
            assert(trueTarget.flag == "true") {
                "Expected flag 'true' but was '${'$'}{trueTarget.flag}'"
            }

            val falseSource = Source(false)
            val falseTarget = mapper.map(falseSource)
            assert(falseTarget.flag == "false") {
                "Expected flag 'false' but was '${'$'}{falseTarget.flag}'"
            }
        }
    """)

    @Test
    fun shouldConvertStringToBoolean() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val flag: String)
        data class Target(val flag: Boolean)

        @Mapper
        interface StringToBoolMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = StringToBoolMapperImpl()
            val source = Source("true")
            val target = mapper.map(source)

            assert(target.flag == true) {
                "Expected flag true but was ${'$'}{target.flag}"
            }
        }
    """)

    @Test
    fun shouldConvertBigDecimalToString() = pluginTest("""
        import org.mapstruct.Mapper
        import java.math.BigDecimal

        data class Source(val amount: BigDecimal)
        data class Target(val amount: String)

        @Mapper
        interface BigDecimalToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = BigDecimalToStringMapperImpl()
            val source = Source(BigDecimal("123.45"))
            val target = mapper.map(source)

            assert(target.amount == "123.45") {
                "Expected amount '123.45' but was '${'$'}{target.amount}'"
            }
        }
    """)

    @Test
    fun shouldConvertStringToBigDecimal() = pluginTest("""
        import org.mapstruct.Mapper
        import java.math.BigDecimal

        data class Source(val amount: String)
        data class Target(val amount: BigDecimal)

        @Mapper
        interface StringToBigDecimalMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = StringToBigDecimalMapperImpl()
            val source = Source("999.99")
            val target = mapper.map(source)

            assert(target.amount == BigDecimal("999.99")) {
                "Expected amount 999.99 but was ${'$'}{target.amount}"
            }
        }
    """)

    @Test
    fun shouldConvertBigIntegerToString() = pluginTest("""
        import org.mapstruct.Mapper
        import java.math.BigInteger

        data class Source(val number: BigInteger)
        data class Target(val number: String)

        @Mapper
        interface BigIntToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = BigIntToStringMapperImpl()
            val source = Source(BigInteger("12345678901234567890"))
            val target = mapper.map(source)

            assert(target.number == "12345678901234567890") {
                "Expected number '12345678901234567890' but was '${'$'}{target.number}'"
            }
        }
    """)

    @Test
    fun shouldConvertCharToString() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val letter: Char)
        data class Target(val letter: String)

        @Mapper
        interface CharToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = CharToStringMapperImpl()
            val source = Source('X')
            val target = mapper.map(source)

            assert(target.letter == "X") {
                "Expected letter 'X' but was '${'$'}{target.letter}'"
            }
        }
    """)

    @Test
    fun shouldConvertFloatToDouble() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Float)
        data class Target(val value: Double)

        @Mapper
        interface FloatToDoubleMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = FloatToDoubleMapperImpl()
            val source = Source(3.14f)
            val target = mapper.map(source)

            assert(target.value > 3.13 && target.value < 3.15) {
                "Expected value ~3.14 but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertShortToInt() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Short)
        data class Target(val value: Int)

        @Mapper
        interface ShortToIntMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ShortToIntMapperImpl()
            val source = Source(100.toShort())
            val target = mapper.map(source)

            assert(target.value == 100) {
                "Expected value 100 but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertByteToInt() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Byte)
        data class Target(val value: Int)

        @Mapper
        interface ByteToIntMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = ByteToIntMapperImpl()
            val source = Source(42.toByte())
            val target = mapper.map(source)

            assert(target.value == 42) {
                "Expected value 42 but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertDoubleToString() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Double)
        data class Target(val value: String)

        @Mapper
        interface DoubleToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = DoubleToStringMapperImpl()
            val source = Source(123.456)
            val target = mapper.map(source)

            assert(target.value == "123.456") {
                "Expected value '123.456' but was '${'$'}{target.value}'"
            }
        }
    """)

    @Test
    fun shouldConvertStringToDouble() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: String)
        data class Target(val value: Double)

        @Mapper
        interface StringToDoubleMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = StringToDoubleMapperImpl()
            val source = Source("99.99")
            val target = mapper.map(source)

            assert(target.value == 99.99) {
                "Expected value 99.99 but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertStringToLong() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: String)
        data class Target(val value: Long)

        @Mapper
        interface StringToLongMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = StringToLongMapperImpl()
            val source = Source("9876543210")
            val target = mapper.map(source)

            assert(target.value == 9876543210L) {
                "Expected value 9876543210L but was ${'$'}{target.value}"
            }
        }
    """)

    @Test
    fun shouldConvertLongToString() = pluginTest("""
        import org.mapstruct.Mapper

        data class Source(val value: Long)
        data class Target(val value: String)

        @Mapper
        interface LongToStringMapper {
            fun map(source: Source): Target
        }

        fun test() {
            val mapper = LongToStringMapperImpl()
            val source = Source(1234567890L)
            val target = mapper.map(source)

            assert(target.value == "1234567890") {
                "Expected value '1234567890' but was '${'$'}{target.value}'"
            }
        }
    """)
}
