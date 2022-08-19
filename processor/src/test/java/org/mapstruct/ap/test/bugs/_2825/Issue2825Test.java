package org.mapstruct.ap.test.bugs._2825;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import java.lang.reflect.Method;

@IssueKey("2825")
public class Issue2825Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED, diagnostics = {
            @Diagnostic(type = AnimalMapper.class,kind = javax.tools.Diagnostic.Kind.WARNING),
    })
    @WithClasses({Animal.class, Dog.class, Cat.class, TargetAnimal.class, AnimalMapper.class})
    public void generalMethodShouldSuccessWork() {
        Animal animal = new Dog();
        animal.setName("dog");
        AnimalMapper instance = AnimalMapper.INSTANCE;
        TargetAnimal targetAnimal = instance.map(animal);
        assertThat(targetAnimal.getName()).isNotBlank();
    }


    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED, diagnostics = {
            @Diagnostic(type = CustomerAnimalMapper.class,kind = javax.tools.Diagnostic.Kind.WARNING)

    })
    @WithClasses({Animal.class, Dog.class, Cat.class, TargetAnimal.class,CustomerAnimalMapper.class})
    public void customerMethodShouldSuccessWork() {
        Animal animal = new Dog();
        animal.setName("dog");
        CustomerAnimalMapper instance = CustomerAnimalMapper.INSTANCE;
        TargetAnimal targetAnimal = instance.map(animal);
        assertThat(targetAnimal.getName()).isNotBlank();
    }

    @ProcessorTest
    @WithClasses({Apple.class,AppleDto.class,Fruit.class,FruitDto.class,FruitMapper.class,Orange.class,OrangeDto.class})
    public void generalTargetImpl(){
        Orange orange = new Orange();
        orange.setName("Orange");
        FruitMapper instance = FruitMapper.INSTANCE;
        FruitDto fruitDto = instance.map(orange);
        assertThat(fruitDto.getName()).isNotBlank();
    }

}
