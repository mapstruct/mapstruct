/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nested;

import java.util.Optional;

/**
 * @author Filip Hrisafov
 */
public class Artist {
    private final String name;
    private final Label label;

    public Artist(String name, Label label) {
        this.name = name;
        this.label = label;
    }

    public boolean hasName() {
        return name != null;
    }

    public String getName() {
        return name;
    }

    public boolean hasLabel() {
        return label != null;
    }

    public Optional<Label> getLabel() {
        return Optional.ofNullable( label );
    }

    public static class Label {
        private final String name;
        private final Studio studio;

        public Label(String name, Studio studio) {
            this.name = name;
            this.studio = studio;
        }

        public String getName() {
            return name;
        }

        public boolean hasStudio() {
            return studio != null;
        }

        public Optional<Studio> getStudio() {
            return Optional.ofNullable( studio );
        }
    }

    public static class Studio {
        private final String city;
        private final String name;

        public Studio(String city, String name) {
            this.city = city;
            this.name = name;
        }

        public boolean hasCity() {
            return city != null;
        }

        public Optional<String> getCity() {
            return Optional.ofNullable( city );
        }

        public String getName() {
            return name;
        }
    }
}
