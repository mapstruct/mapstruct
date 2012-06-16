/**
 *  Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)
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
package de.moapa.maple.ap.test;

import java.io.File;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import de.moapa.maple.ap.test.model.Car;
import de.moapa.maple.ap.test.model.CarDto;
import de.moapa.maple.ap.test.model.IntToStringConverter;
import de.moapa.maple.ap.test.model.NativeCarMapper;
import de.moapa.maple.ap.test.model.Person;
import de.moapa.maple.ap.test.model.PersonDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class NativeCarMapperTest extends MapperTestBase {

	private DiagnosticCollector<JavaFileObject> diagnostics;

	public NativeCarMapperTest() {
		super( "maple.jar" );
	}

	@BeforeMethod
	public void generateMapperImplementation() {

		diagnostics = new DiagnosticCollector<JavaFileObject>();
		File[] sourceFiles = getSourceFiles(
				Car.class,
				CarDto.class,
				Person.class,
				PersonDto.class,
				NativeCarMapper.class,
				IntToStringConverter.class
		);

		boolean compilationSuccessful = compile( diagnostics, sourceFiles );

		assertThat( compilationSuccessful ).describedAs( "Compilation failed: " + diagnostics.getDiagnostics() )
				.isTrue();
	}

	@Test
	public void shouldProvideMapperInstance() throws Exception {

		assertThat( NativeCarMapper.INSTANCE ).isNotNull();
	}

	@Test
	public void shouldMapAttributeByName() {

		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ) );

		//when
		CarDto carDto = NativeCarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getMake() ).isEqualTo( car.getMake() );
	}

	@Test(enabled = false)
	public void shouldMapReferenceAttribute() {

		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ) );

		//when
		CarDto carDto = NativeCarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getDriver() ).isNotNull();
		assertThat( carDto.getDriver().getName() ).isEqualTo( "Bob" );
	}

	@Test(enabled = false)
	public void shouldReverseMapReferenceAttribute() {

		//given
		CarDto carDto = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ) );

		//when
		Car car = NativeCarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getDriver() ).isNotNull();
		assertThat( car.getDriver().getName() ).isEqualTo( "Bob" );
	}

	@Test
	public void shouldMapAttributeWithCustomMapping() {

		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ) );

		//when
		CarDto carDto = NativeCarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
	}

	@Test(enabled = false)
	public void shouldConsiderCustomMappingForReverseMapping() {

		//given
		CarDto carDto = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ) );

		//when
		Car car = NativeCarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getNumberOfSeats() ).isEqualTo( carDto.getSeatCount() );
	}

	@Test
	public void shouldApplyConverter() {

		//given
		Car car = new Car( "Morris", 2, 1980, new Person( "Bob" ) );

		//when
		CarDto carDto = NativeCarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getManufacturingYear() ).isEqualTo( "1980" );
	}

	@Test(enabled = false)
	public void shouldApplyConverterForReverseMapping() {

		//given
		CarDto carDto = new CarDto( "Morris", 2, "1980", new PersonDto( "Bob" ) );

		//when
		Car car = NativeCarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getYearOfManufacture() ).isEqualTo( 1980 );
	}
}
