# What's Maple?

Maple is a Java [annotation processor](http://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html) for the generation of type-safe bean mapping classes.

All you have to do is to define one more more mapper interfaces, annotate them with the `@Mapper` annotation and add the required mapping methods. During compilation, Maple will generate an implementation for each mapper interface, based on your preferred mapping framework (currently [Dozer](http://dozer.sourceforge.net/) is supported, more to come).

The following shows an example. First, an object (e.g. a JPA entity) and an accompanying data transfer object (DTO):

	public class Car {

		private String make;
		private int numberOfSeats;
		private int yearOfManufacture;
		
		//constructor, getters, setters etc.
	}
	
	public class CarDto {

		private String make;
		private int seatCount;
		private int yearOfManufacture;

		//constructor, getters, setters etc.
	}
	
Both types are rather similar, only the seat count attributes have different names. The mapper interface thus looks like this:

	@Mapper
	public interface CarMapper {

		CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

		@Mapping(source = "numberOfSeats", target = "seatCount"),
		CarDto carToCarDto(Car car);

		Car carDtoToCar(CarDto carDto);
	}
	
The interface is straight-forward: 

* Annotating it with `@Mapper` let's the Maple processor kick in during compilation
* The `INSTANCE` member provides access to the mapper implementation for clients (a service loader based alternative coming soon)
* For each mapping direction (entity to DTO and vice versa) there is a conversion method. For those attributes which have differing names and thus can't be mapped automatically, a mapping is configured using the `@Mapping` annotation on one of the methods.

Based on the mapper interface, clients can perform object mappings in a very easy and type-safe manner:

	@Test
	public void shouldMapCarToDto() {

		//given
		Car car = new Car( "Morris", 2, 1980 );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getMake() ).isEqualTo( car.getMake() );
		assertThat( carDto.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
		assertThat( carDto.getyearOfManufacture() ).isEqualTo( car.getyearOfManufacture() );
	}
		
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
	
# Using Maple

Maple is a Java annotation processor based on [JSR 269](jcp.org/en/jsr/detail?id=269) and as such can be used within command line builds (javac, Ant, Maven etc.) as well as from within your IDE.

Detailed instructions on the usage will be added soon, in between the [set up](http://docs.jboss.org/hibernate/stable/jpamodelgen/reference/en-US/html/chapter-usage.html) of the Hibernate JPA meta model generator can be used as general guideline for setting up an annotation processor.

In order to use Maple, you have to check out its sources as it is currently not available in any public Maven repository.

# What's next

Maple is just in its very beginnings. There are several ideas for further features, including but not limited to:

* Allow to generate mappers for several existing mapping frameworks (currently only Dozer is supported).
* Generate "native" mappers, that is without any reflection, but by direcly invoking getters and setters within the generated mapper. This should deliver very efficient mapper implementations
* Provide a way to access the underlying mapper in order to make use of advanced features not provided by the Maple API (similar to the `unwrap()` method of JPA etc.)
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

* Remove runtime dependencies to Maple by using the JDK service loader to retrieve mapper implementations.