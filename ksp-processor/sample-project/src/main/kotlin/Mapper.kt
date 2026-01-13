package org.example

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


class Car(val numberOfSeats: Int, val make: String)

class CarDto(val numberOfSeats: Int, val make: String) {
    override fun toString(): String = "CarDto(numberOfSeats=$numberOfSeats, make='$make')"
}

@Mapper
interface CarMapper {
    fun carToCarDto(car: Car): CarDto

    companion object {
        val INSTANCE: CarMapper = Mappers.getMapper(CarMapper::class.java)
    }
}