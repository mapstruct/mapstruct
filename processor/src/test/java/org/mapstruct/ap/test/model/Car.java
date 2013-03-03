/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.model;

import java.util.ArrayList;

public class Car {

	private String make;
	private int numberOfSeats;
	private int yearOfManufacture;
	private Person driver;
	private ArrayList<Person> passengers;
	private int price;
	private Category category;

	public Car() {
	}

	public Car(String make, int numberOfSeats, int yearOfManufacture, Person driver, ArrayList<Person> passengers) {
		this.make = make;
		this.numberOfSeats = numberOfSeats;
		this.yearOfManufacture = yearOfManufacture;
		this.driver = driver;
		this.passengers = passengers;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public int getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public Person getDriver() {
		return driver;
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public ArrayList<Person> getPassengers() {
		return passengers;
	}

	public void setPassengers(ArrayList<Person> passengers) {
		this.passengers = passengers;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
