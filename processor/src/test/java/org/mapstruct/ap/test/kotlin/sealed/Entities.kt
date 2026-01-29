/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.sealed

sealed class Vehicle {
    var name: String? = null
    var vehicleManufacturingCompany: String? = null

    class Bike : Vehicle() {
        var numberOfGears: Int = 0
    }

    class Car : Vehicle() {
        var manual: Boolean = false
    }

}

sealed class Motor : Vehicle() {
    var cc: Int = 0

    class Davidson : Motor() {
        var numberOfExhausts: Int = 0
    }

    class Harley : Motor() {
        var engineDb: Int = 0
    }
}

class VehicleCollection {
    var vehicles: MutableList<Vehicle> = mutableListOf()
}
