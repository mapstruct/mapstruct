# What's MapStruct?

MapStruct is a Java [annotation processor](http://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html) for the generation of type-safe bean mapping classes.

All you have to do is to define a mapper interfaces, annotate it with the `@Mapper` annotation and add the required mapping methods. During compilation, MapStruct will generate an implementation for the mapper interface. This implementation uses plain Java method invocations, i.e. no reflection or similar.

## Hello World

The following shows a simple example for using MapStruct. First, let's define an object (e.g. a JPA entity) and an accompanying data transfer object (DTO):

```java
public class Car {

	private String make;
	private int numberOfSeats;
	
	//constructor, getters, setters etc.
}

public class CarDto {

	private String make;
	private int seatCount;

	//constructor, getters, setters etc.
}
```

Both types are rather similar, only the seat count attributes have different names. A mapper interface could thus look like this:

```java
@Mapper (1)
public interface CarMapper {

	CarMapper INSTANCE = Mappers.getMapper( CarMapper.class ); (3)

	@Mapping(source = "numberOfSeats", target = "seatCount")
	CarDto carToCarDto(Car car); (2)
}
```

The interface is straight-forward: 

1. Annotating it with `@Mapper` let's the MapStruct processor kick in during compilation
1. The actual mapping method expects the source object as parameter and returns the target object. Its name can be freely chosen. Of course there can be multiple mapping methods in one interface. For attributes with different names in source and target object, the `@Mapping` annotation can be used to configure the names.
1. An instance of the interface implementation can be retrieved from the `Mappers` class. By convention, the interface declares a member `INSTANCE`, providing access to the mapper implementation for clients

Based on the mapper interface, clients can perform object mappings in a very easy and type-safe manner:

```java
@Test
public void shouldMapCarToDto() {

	//given
	Car car = new Car( "Morris", 2 );

	//when
	CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

	//then
	assertThat( carDto ).isNotNull();
	assertThat( carDto.getMake() ).isEqualTo( car.getMake() );
	assertThat( carDto.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
}
```

Sometimes not only the names of two corresponding attributes differ, but also their types. This can be addressed by defining a custom type converter:

	public class IntToStringConverter implements Converter<Integer, String> {

		@Override
		public String from(Integer source) {
			return source != null ? source.toString() : null;
		}

		@Override
		public Integer to(String target) {
			return target != null ? Integer.valueOf( target ) : null;
		}
	}
	
To make use of a converter, specify its type within the `@Mapping` annotation:

	@Mapper
	public interface CarMapper {

		CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

		@Mappings({
			@Mapping(source = "numberOfSeats", target = "seatCount"),
			@Mapping(source = "yearOfManufacture", target = "manufacturingYear", converter = IntToStringConverter.class)
		})
		CarDto carToCarDto(Car car);

		Car carDtoToCar(CarDto carDto);
	}
	
# Using MapStruct

MapStruct is a Java annotation processor based on [JSR 269](jcp.org/en/jsr/detail?id=269) and as such can be used within command line builds (javac, Ant, Maven etc.) as well as from within your IDE.

Detailed instructions on the usage will be added soon, in between the [set up](http://docs.jboss.org/hibernate/stable/jpamodelgen/reference/en-US/html/chapter-usage.html) of the Hibernate JPA meta model generator can be used as general guideline for setting up an annotation processor.

In order to use MapStruct, you have to check out its sources as it is currently not available in any public Maven repository.

# What's next

MapStruct is just in its very beginnings. There are several ideas for further features, including but not limited to:

* Allow to generate mappers for several existing mapping frameworks (currently only Dozer is supported).
* Generate "native" mappers, that is without any reflection, but by direcly invoking getters and setters within the generated mapper. This should deliver very efficient mapper implementations
* Provide a way to access the underlying mapper in order to make use of advanced features not provided by the MapStruct API (similar to the `unwrap()` method of JPA etc.)
* Provide a way to add custom mapping code in a very simple way

Example:

	@Mapper(extension=CarMapperCustomization.class)
	public interface CarMapper {

		CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );
	
		//automatically generated mapping methods
		CarDto carToCarDto(Car car);

		Car carDtoToCar(CarDto carDto);

		//very complex mapping which requires some hand-coding
		Vehicle carToVehicle(Car car);
	}

	public abstract class CarMapperCustomization implements CarMapper {
	
		@Override
		public Vehicle carToVehicle(Car car) {
			//implement custom mapping logic
		}
	}

* Remove runtime dependencies to MapStruct by using the JDK service loader to retrieve mapper implementations.
