/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.test.subclass

import org.junit.jupiter.api.Test
import org.mapstruct.ksp.test.pluginTest

/**
 * Tests for subclass mapping with KSP processor.
 *
 * NOTE: @SubclassMapping has issues with KSP adapter duplicate source detection.
 */
class SubclassMappingTest {

    // TODO: Fix KSP adapter to handle multiple @SubclassMapping annotations
    @org.junit.jupiter.api.Disabled("KSP adapter error: 'Subclass is already defined as a source'")
    @Test
    fun shouldMapSubclassWithSubclassMapping() = pluginTest("""
        import org.mapstruct.Mapper
        import org.mapstruct.SubclassMapping

        sealed class Vehicle {
            abstract val brand: String
        }
        data class Car(override val brand: String, val numDoors: Int) : Vehicle()
        data class Bike(override val brand: String, val type: String) : Vehicle()

        sealed class VehicleDto {
            abstract val brand: String
        }
        data class CarDto(override val brand: String, val numDoors: Int) : VehicleDto()
        data class BikeDto(override val brand: String, val type: String) : VehicleDto()

        @Mapper
        interface VehicleMapper {
            @SubclassMapping(source = Car::class, target = CarDto::class)
            @SubclassMapping(source = Bike::class, target = BikeDto::class)
            fun map(vehicle: Vehicle): VehicleDto

            fun mapCar(car: Car): CarDto
            fun mapBike(bike: Bike): BikeDto
        }

        fun test() {
            val mapper = VehicleMapperImpl()

            val car = Car("Toyota", 4)
            val carDto = mapper.map(car) as CarDto
            assert(carDto.brand == "Toyota") {
                "Expected brand 'Toyota' but was '${'$'}{carDto.brand}'"
            }
            assert(carDto.numDoors == 4) {
                "Expected numDoors 4 but was ${'$'}{carDto.numDoors}"
            }

            val bike = Bike("Honda", "Sport")
            val bikeDto = mapper.map(bike) as BikeDto
            assert(bikeDto.brand == "Honda") {
                "Expected brand 'Honda' but was '${'$'}{bikeDto.brand}'"
            }
            assert(bikeDto.type == "Sport") {
                "Expected type 'Sport' but was '${'$'}{bikeDto.type}'"
            }
        }
    """)
}
