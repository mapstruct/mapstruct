/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Javadoc extends ModelElement {

    public static class Builder {

        private String value;
        private List<String> authors;
        private String deprecated;
        private String since;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder authors(List authors) {
            this.authors = authors;
            return this;
        }

        public Builder deprecated(String deprecated) {
            this.deprecated = deprecated;
            return this;
        }

        public Builder since(String since) {
            this.since = since;
            return this;
        }

        public Javadoc build() {
            return new Javadoc(
                    value,
                    authors,
                    deprecated,
                    since
            );
        }
    }

    private final String value;
    private final List<String> authors;
    private final String deprecated;
    private final String since;

    private Javadoc(String value, List<String> authors, String deprecated, String since) {
        this.value = value;
        this.authors = authors;
        this.deprecated = deprecated;
        this.since = since;
    }

    public String getValue() {
        return value;
    }

    public boolean hasAuthors() {
        return authors != null && !authors.isEmpty();
    }

    public List<String> getAuthors() {
        return Collections.unmodifiableList(
                Optional
                        .ofNullable( authors )
                        .orElse( Collections.emptyList() )
        );
    }

    public boolean hasDeprecated() {
        return !Strings.isEmpty( deprecated );
    }

    public String getDeprecated() {
        return deprecated;
    }

    public boolean hasSince() {
        return !Strings.isEmpty( since );
    }

    public String getSince() {
        return since;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

}
