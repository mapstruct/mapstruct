/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.sealed

sealed class VehicleDto {
    var name: String? = null
    var maker: String? = null

    class BikeDto : VehicleDto() {
        var numberOfGears: Int = 0
    }

    class CarDto : VehicleDto() {
        var manual: Boolean = false
    }

}

sealed class MotorDto : VehicleDto() {
    var cc: Int = 0

    class DavidsonDto : MotorDto() {
        var numberOfExhausts: Int = 0
    }

    class HarleyDto : MotorDto() {
        var engineDb: Int = 0
    }
}

class VehicleCollectionDto {
    var vehicles: MutableList<VehicleDto> = mutableListOf()
}
