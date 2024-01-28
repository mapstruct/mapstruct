/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the javadoc information that should be generated for a {@link Mapper}.
 *
 * @author Jose Carlos Campanero Ortiz
 */
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

        public Builder authors(List<String> authors) {
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
        this.authors = authors != null ? Collections.unmodifiableList( authors ) : Collections.emptyList();
        this.deprecated = deprecated;
        this.since = since;
    }

    public String getValue() {
        return value;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public String getSince() {
        return since;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

}
