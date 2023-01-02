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
public final class LombokImmutablePath2 {
    private final List<String> edges;

    @ConstructorProperties({"edges"})
    LombokImmutablePath2(List<String> edges) {
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
        else if (!(o instanceof LombokImmutablePath2)) {
            return false;
        }
        else {
            LombokImmutablePath2 other = (LombokImmutablePath2) o;
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

        public LombokImmutablePathBuilder addEdge(String edge) { // follows mapstruct convention of an adder method
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

        public LombokImmutablePath2 build() {
            List<String> edges = this.edges == null ? ImmutableList.of() : this.edges.build();
            return new LombokImmutablePath2(edges);
        }

        public String toString() {
            return "Path.PathBuilder(edges=" + this.edges + ")";
        }
    }
}
