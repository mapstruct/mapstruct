/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1698;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Erroneous1698Mapper {

    Erroneous1698Mapper INSTANCE = Mappers.getMapper( Erroneous1698Mapper.class );

    Target map(Source source);

    @Mapping(target = "value", source = "source")
    Cat mapToCat(String source);

    @Mapping(target = "value", source = "source")
    Dog mapToDog(String source);

    class Source {

        private String cat;
        private String dog;
        private String rabbit;

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public String getDog() {
            return dog;
        }

        public void setDog(String dog) {
            this.dog = dog;
        }

        public String getRabbit() {
            return rabbit;
        }

        public void setRabbit(String rabbit) {
            this.rabbit = rabbit;
        }
    }

    class Target {

        private Cat cat;
        private Dog dog;
        private Rabbit rabbit;

        public Cat getCat() {
            return cat;
        }

        public void setCat(Cat cat) {
            this.cat = cat;
        }

        public Dog getDog() {
            return dog;
        }

        public void setDog(Dog dog) {
            this.dog = dog;
        }

        public Rabbit getRabbit() {
            return rabbit;
        }

        public void setRabbit(Rabbit rabbit) {
            this.rabbit = rabbit;
        }
    }

    class Animal {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Cat extends Animal {

    }

    class Dog extends Animal {

    }

    class Rabbit extends Animal {

    }
}
