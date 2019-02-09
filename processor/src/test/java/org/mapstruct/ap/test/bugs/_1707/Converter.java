/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1707;

import java.util.Set;
import java.util.stream.Stream;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class Converter {

    public abstract Set<Target> convert(Stream<Source> source);

    @Mapping( target = "custom", ignore = true )
    public abstract Target convert(Source source);

    @AfterMapping
    public void addCustomValue(@MappingTarget Set<Target> targetList) {
        targetList.forEach( t -> t.custom = true );
    }

    public static final class Source {
        private String text;
        private int number;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public static class Target {
        private String text;
        private int number;
        private boolean custom;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public boolean isCustom() {
            return custom;
        }

        public void setCustom(boolean custom) {
            this.custom = custom;
        }
    }

}
