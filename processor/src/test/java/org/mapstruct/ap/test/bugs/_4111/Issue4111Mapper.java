/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4111;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue4111Mapper {

    Issue4111Mapper INSTANCE = Mappers.getMapper( Issue4111Mapper.class );

    Target toTarget(Source source);

    class Source {
        private Optional<String> name = Optional.empty();
        private Optional<Map<String, String>> attributes = Optional.empty();
        private Optional<List<String>> tags = Optional.empty();

        public Optional<String> getName() {
            return name;
        }

        public void setName(Optional<String> name) {
            this.name = name;
        }

        public Optional<Map<String, String>> getAttributes() {
            return attributes;
        }

        public void setAttributes(Optional<Map<String, String>> attributes) {
            this.attributes = attributes;
        }

        public Optional<List<String>> getTags() {
            return tags;
        }

        public void setTags(Optional<List<String>> tags) {
            this.tags = tags;
        }
    }

    class Target {
        private String name;
        private Map<String, String> attributes;
        private List<String> tags;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
