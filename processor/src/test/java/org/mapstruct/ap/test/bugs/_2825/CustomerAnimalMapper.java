package org.mapstruct.ap.test.bugs._2825;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerAnimalMapper {

    CustomerAnimalMapper INSTANCE = Mappers.getMapper(CustomerAnimalMapper.class);

    @SubclassMapping(target = TargetAnimal.class, source = Dog.class)
    @SubclassMapping(target = TargetAnimal.class, source = Cat.class)
    TargetAnimal map(Animal source);


    TargetAnimal map(Dog dog);

    TargetAnimal map(Cat cat);
}
