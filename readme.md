# What is MapStruct?

MapStruct is a Java [annotation processor](http://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html) for the generation of type-safe bean mapping classes.

All you have to do is to define a mapper interfaces, annotate it with the `@Mapper` annotation and add the required mapping methods. During compilation, MapStruct will generate an implementation for the mapper interface. This implementation uses plain Java method invocations, i.e. no reflection or similar.

# Hello World

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

# Advanced mappings

## Reverse mappings

Often bi-directional mappings are required, e.g. from entity to DTO and from DTO to entity. For this purpose, simply declare a method with the required parameter and return type on the mapping interface which also declares the forward mapping method:

```java
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

	@Mapping(source = "numberOfSeats", target = "seatCount")
	CarDto carToCarDto(Car car);
    
    Car carDtoToCar(CarDto carDto); (1)
}
```

1. The `carDtoToCar()` method is the reverse mapping method for `carToCarDto()`. Note that the attribute mappings only have to be specified at one of the two methods and will be applied to the corresponding reverse mapping method as well.

## Mapping referenced objects and collections

Typically an object has not only primitive attributes but also references other objects. E.g. the `Car` class could contain a reference to a `Person` object (representing the car's driver) which should be mapped to a `PersonDto` object referenced by the `CarDto` class.

In this case just define a mapping method for the referenced object types as well:

```java
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

	CarDto carToCarDto(Car car);
    
    PersonDto personToPersonDto(Person person);
    
    //reverse mapping methods
}
```

The generated code for the `carToCarDto()` method will invoke the `personToPersonDto()` method for mapping the `driver` attribute.

The same works for collections. E.g. the `Car` class could reference the list of all passengers:

```java
public class Car {

    private List<Person> passengers;
	
	//...
}
```

To map this attribute, define a method accepting a list of persons and returning a list of person DTOs:

```java
@Mapper
public interface CarMapper {

    //other members
    
    List<PersonDto> personsToPersonDtos(List<Person> prsons);
}
```

This method will be invoked by the generated implementation when mapping the `passengers` attribute.

NOTE: Collection mapping methods may be generated without declaration in the future (see issues [#3](https://github.com/gunnarmorling/mapstruct/issues/3) and [#4](https://github.com/gunnarmorling/mapstruct/issues/4)).

## Type mappings

Not always a mapped attribute has the same type in the source and target objects. MapStruct generates appropriate conversion code where possible (e.g. to map an `int` attribute to a String and vice versa by calling `toString()` and `parseInt()`, respectively.

Where this is not automatically possible, you can implement mapping methods yourself and make these known to MapStruct. E.g. the `Car` class might contain an attribute `manufacturingDate` while the corresponding DTO attribute is of type String.

In order to map this attribute, you could implement a mapper class like this:

```java
public class DateMapper {

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd" ).format( date ) : null;
    }

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat( "yyyy-MM-dd" ).parse( date ) : null;
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }
}
```

In the `@Mapper` annotation at the `CarMapper` interface reference this mapper class:

```java
@Mapper(uses=DateMapper.class)
public class CarMapperMapper {

    CarDto carToCarDto(Car car);
    
    //other mapping methods
}
```

When generating code for the implementation of the `carToCarDto()` method, MapStruct will look for a method which maps a `Date` object into a String, find it on the `DateMapper` class and generate an invocation of `asString()` for mapping the `manufacturingDate` attribute.

# Using MapStruct

MapStruct is a Java annotation processor based on [JSR 269](jcp.org/en/jsr/detail?id=269) and as such can be used within command line builds (javac, Ant, Maven etc.) as well as from within your IDE.

For Maven based projects add the following to your POM file in order to use MapStruct:

```xml
...
<properties>
    <org.mapstruct.version>[current MapStruct version]</org.mapstruct.version>
    
</properties>
...
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
</dependencies>
...
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
