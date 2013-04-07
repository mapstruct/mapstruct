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

For Maven based projects add the following to your POM file in order to use MapStruct:

```xml
...
<properties>
    <org.mapstruct.version>[current MapStruct version]</org.mapstruct.version>
    
</properties>

<!-- ... -->

<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
</dependencies>

<!-- ... -->

<build>
    <plugins>
        <plugin>
            <groupId>org.bsc.maven</groupId>
            <artifactId>maven-processor-plugin</artifactId>
            <version>2.0.2</version>
            <configuration>
                <defaultOutputDirectory>${project.build.directory}/generated-sources</defaultOutputDirectory>
                <processors>
                    <processor>org.mapstruct.ap.MappingProcessor</processor>
                </processors>
            </configuration>
            <executions>
                <execution>
                    <id>process</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>process</goal>
                    </goals>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>org.mapstruct</groupId>
                    <artifactId>mapstruct-processor</artifactId>
                    <version>${org.mapstruct.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

NOTE: In order to use MapStruct, you currently have to check out its sources and build it yourself as it is not yet available in any public Maven repository.

# What's next

MapStruct is just in its very beginnings. Some ideas for further features:

* Support EL expressions to create derived attributes
* Support mapping of collections of objects (lists, arrays, maps etc.)
* Remove runtime dependencies to MapStruct by e.g. using the JDK service loader or dependency injection via CDI to retrieve mapper implementations
* etc.

Check out the [issue list](https://github.com/gunnarmorling/mapstruct/issues?state=open) for more details.
