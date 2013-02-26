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
package org.mapstruct.ap.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.mapstruct.ap.test.model.Car;
import org.mapstruct.ap.test.model.CarDto;
import org.mapstruct.ap.test.model.CarMapper;
import org.mapstruct.ap.test.model.IntToStringConverter;
import org.mapstruct.ap.test.model.Person;
import org.mapstruct.ap.test.model.PersonDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CarMapperTest extends MapperTestBase {

	private DiagnosticCollector<JavaFileObject> diagnostics;

	public CarMapperTest() {
		super( "mapstruct.jar" );
	}

	@BeforeMethod
	public void generateMapperImplementation() {
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		File[] sourceFiles = getSourceFiles(
				Car.class,
				CarDto.class,
				Person.class,
				PersonDto.class,
				CarMapper.class,
				IntToStringConverter.class
		);

		boolean compilationSuccessful = compile( diagnostics, sourceFiles );

		assertThat( compilationSuccessful ).describedAs( "Compilation failed: " + diagnostics.getDiagnostics() )
				.isTrue();
	}

	@Test
	public void shouldProvideMapperInstance() throws Exception {
		assertThat( CarMapper.INSTANCE ).isNotNull();
	}

	@Test
	public void shouldMapAttributeByName() {
		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ), new ArrayList<Person>() );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getMake() ).isEqualTo( car.getMake() );
	}

	@Test
	public void shouldMapReferenceAttribute() {
		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ), new ArrayList<Person>() );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getDriver() ).isNotNull();
		assertThat( carDto.getDriver().getName() ).isEqualTo( "Bob" );
	}

	@Test
	public void shouldReverseMapReferenceAttribute() {
		//given
		CarDto carDto = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ), new ArrayList<PersonDto>() );

		//when
		Car car = CarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getDriver() ).isNotNull();
		assertThat( car.getDriver().getName() ).isEqualTo( "Bob" );
	}

	@Test
	public void shouldMapAttributeWithCustomMapping() {
		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ), new ArrayList<Person>() );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
	}

	@Test
	public void shouldConsiderCustomMappingForReverseMapping() {
		//given
		CarDto carDto = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ), new ArrayList<PersonDto>() );

		//when
		Car car = CarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getNumberOfSeats() ).isEqualTo( carDto.getSeatCount() );
	}

	@Test
	public void shouldApplyConverter() {
		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ), new ArrayList<Person>() );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getManufacturingYear() ).isEqualTo( "1980" );
	}

	@Test
	public void shouldApplyConverterForReverseMapping() {
		//given
		CarDto carDto = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ), new ArrayList<PersonDto>() );

		//when
		Car car = CarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getYearOfManufacture() ).isEqualTo( 1980 );
	}

	@Test
	public void shouldMapIterable() {
		//given
		Car car1 = new Car( "Morris", 2, 1980, new Person( "Bob" ), new ArrayList<Person>() );
		Car car2 = new Car( "Railton", 4, 1934, new Person( "Bill" ), new ArrayList<Person>() );

		//when
		List<CarDto> dtos = CarMapper.INSTANCE.carsToCarDtos( new ArrayList<Car>( Arrays.asList( car1, car2 ) ) );

		//then
		assertThat( dtos ).isNotNull();
		assertThat( dtos ).hasSize( 2 );

		assertThat( dtos.get( 0 ).getMake() ).isEqualTo( "Morris" );
		assertThat( dtos.get( 0 ).getSeatCount() ).isEqualTo( 2 );
		assertThat( dtos.get( 0 ).getManufacturingYear() ).isEqualTo( "1980" );
		assertThat( dtos.get( 0 ).getDriver().getName() ).isEqualTo( "Bob" );

		assertThat( dtos.get( 1 ).getMake() ).isEqualTo( "Railton" );
		assertThat( dtos.get( 1 ).getSeatCount() ).isEqualTo( 4 );
		assertThat( dtos.get( 1 ).getManufacturingYear() ).isEqualTo( "1934" );
		assertThat( dtos.get( 1 ).getDriver().getName() ).isEqualTo( "Bill" );
	}

	@Test
	public void shouldReverseMapIterable() {
		//given
		CarDto car1 = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ), new ArrayList<PersonDto>() );
		CarDto car2 = new CarDto( "Railton", 4, "1934", new PersonDto( "Bill" ), new ArrayList<PersonDto>() );

		//when
		List<Car> cars = CarMapper.INSTANCE.carDtosToCars( new ArrayList<CarDto>( Arrays.asList( car1, car2 ) ) );

		//then
		assertThat( cars ).isNotNull();
		assertThat( cars ).hasSize( 2 );

		assertThat( cars.get( 0 ).getMake() ).isEqualTo( "Morris" );
		assertThat( cars.get( 0 ).getNumberOfSeats() ).isEqualTo( 2 );
		assertThat( cars.get( 0 ).getYearOfManufacture() ).isEqualTo( 1980 );
		assertThat( cars.get( 0 ).getDriver().getName() ).isEqualTo( "Bob" );

		assertThat( cars.get( 1 ).getMake() ).isEqualTo( "Railton" );
		assertThat( cars.get( 1 ).getNumberOfSeats() ).isEqualTo( 4 );
		assertThat( cars.get( 1 ).getYearOfManufacture() ).isEqualTo( 1934 );
		assertThat( cars.get( 1 ).getDriver().getName() ).isEqualTo( "Bill" );
	}

	@Test
	public void shouldMapIterableAttribute() {
		//given
		Car car = new Car(
				"Morris",
				2,
				1980,
				new Person( "Bob" ),
				new ArrayList<Person>( Arrays.asList( new Person( "Alice" ), new Person( "Bill" ) ) )
		);

		//when
		CarDto dto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( dto ).isNotNull();

		assertThat( dto.getPassengers() ).hasSize( 2 );
		assertThat( dto.getPassengers().get( 0 ).getName() ).isEqualTo( "Alice" );
		assertThat( dto.getPassengers().get( 1 ).getName() ).isEqualTo( "Bill" );
	}

	@Test
	public void shouldReverseMapIterableAttribute() {
		//given
		CarDto carDto = new CarDto(
				"Morris",
				2,
				"1980",
				new PersonDto( "Bob" ),
				new ArrayList<PersonDto>( Arrays.asList( new PersonDto( "Alice" ), new PersonDto( "Bill" ) ) )
		);

		//when
		Car car = CarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();

		assertThat( car.getPassengers() ).hasSize( 2 );
		assertThat( car.getPassengers().get( 0 ).getName() ).isEqualTo( "Alice" );
		assertThat( car.getPassengers().get( 1 ).getName() ).isEqualTo( "Bill" );
	}
}
