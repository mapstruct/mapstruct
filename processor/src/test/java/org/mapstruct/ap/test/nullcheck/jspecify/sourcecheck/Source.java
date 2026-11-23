/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.sourcecheck;

import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class Source {

    private @Nullable Child child;
    private @Nullable String name;
    private @Nullable String nick;
    private @Nullable List<Integer> numbers;

    public @Nullable Child getChild() {
        return child;
    }

    public void setChild(@Nullable Child child) {
        this.child = child;
    }

    public @Nullable String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getNick() {
        return nick;
    }

    public void setNick(@Nullable String nick) {
        this.nick = nick;
    }

    public boolean hasNick() {
        return nick != null && !nick.isEmpty();
    }

    public @Nullable List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(@Nullable List<Integer> numbers) {
        this.numbers = numbers;
    }

    @NullMarked
    public static class Child {

        private String value = "";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
