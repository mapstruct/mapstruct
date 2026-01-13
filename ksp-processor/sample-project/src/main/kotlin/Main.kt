package org.example

fun main() {
    val car = Car(4, "Toyota")
    val dto = CarMapper.INSTANCE.carToCarDto(car)
    println("Mapped: $dto")
}