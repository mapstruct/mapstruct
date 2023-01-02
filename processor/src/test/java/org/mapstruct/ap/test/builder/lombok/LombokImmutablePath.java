/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lombok;

import com.google.common.collect.ImmutableList;
import java.beans.ConstructorProperties;
import java.util.List;

@SuppressWarnings("RedundantIfStatement")
public final class LombokImmutablePath {
    private final List<String> edges;

    @ConstructorProperties({"edges"})
    LombokImmutablePath(List<String> edges) {
        this.edges = edges;
    }

    public static LombokImmutablePathBuilder builder() {
        return new LombokImmutablePathBuilder();
    }

    public List<String> getEdges() {
        return this.edges;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        else if (!(o instanceof LombokImmutablePath)) {
            return false;
        }
        else {
            LombokImmutablePath other = (LombokImmutablePath) o;
            Object thisEdges = this.getEdges();
            Object otherEdges = other.getEdges();
            if (thisEdges == null) {
                if (otherEdges != null) {
                    return false;
                }
            }
            else if (!thisEdges.equals( otherEdges )) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        int result = 1;
        Object edges = this.getEdges();
        result = result * 59 + (edges == null ? 43 : edges.hashCode());
        return result;
    }

    public String toString() {
        return "Path(edges=" + this.getEdges() + ")";
    }

    @SuppressWarnings("unused")
    public static class LombokImmutablePathBuilder {
        private ImmutableList.Builder<String> edges;

        LombokImmutablePathBuilder() {
        }

        public LombokImmutablePathBuilder edge(String edge) {
            if (this.edges == null) {
                this.edges = ImmutableList.builder();
            }

            this.edges.add( edge.toUpperCase() ); // modified manually to add UPPERCASE characters
            return this;
        }

        public LombokImmutablePathBuilder edges(Iterable<? extends String> edges) {
            if (edges == null) {
                throw new NullPointerException("edges cannot be null");
            }
            else {
                if (this.edges == null) {
                    this.edges = ImmutableList.builder();
                }

                this.edges.addAll( edges );
                return this;
            }
        }

        public LombokImmutablePathBuilder clearEdges() {
            this.edges = null;
            return this;
        }

        public LombokImmutablePath build() {
            List<String> edges = this.edges == null ? ImmutableList.of() : this.edges.build();
            return new LombokImmutablePath(edges);
        }

        public String toString() {
            return "Path.PathBuilder(edges=" + this.edges + ")";
        }
    }
}
