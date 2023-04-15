/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3015;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.annotatewith.CustomMethodOnlyAnnotation;

/**
 * @author orange add
 */
@Mapper
public interface Issue3015Mapper {

    @AnnotateWith( CustomMethodOnlyAnnotation.class )
    Target map(Source source);

    class Source {

        private NestedSource nested;
        private List<String> list;
        private Stream<String> stream;
        private AnnotateSourceEnum annotateWithEnum;
        private Map<String, Integer> map;

        public NestedSource getNested() {
            return nested;
        }

        public void setNested(NestedSource nested) {
            this.nested = nested;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public Stream<String> getStream() {
            return stream;
        }

        public void setStream(Stream<String> stream) {
            this.stream = stream;
        }

        public AnnotateSourceEnum getAnnotateWithEnum() {
            return annotateWithEnum;
        }

        public void setAnnotateWithEnum(AnnotateSourceEnum annotateWithEnum) {
            this.annotateWithEnum = annotateWithEnum;
        }

        public Map<String, Integer> getMap() {
            return map;
        }

        public void setMap(Map<String, Integer> map) {
            this.map = map;
        }
    }

    class Target {
        private NestedTarget nested;
        private List<Integer> list;
        private Stream<Integer> stream;
        private AnnotateTargetEnum annotateWithEnum;
        private Map<String, String> map;

        public NestedTarget getNested() {
            return nested;
        }

        public void setNested(NestedTarget nested) {
            this.nested = nested;
        }

        public List<Integer> getList() {
            return list;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }

        public Stream<Integer> getStream() {
            return stream;
        }

        public void setStream(Stream<Integer> stream) {
            this.stream = stream;
        }

        public AnnotateTargetEnum getAnnotateWithEnum() {
            return annotateWithEnum;
        }

        public void setAnnotateWithEnum(AnnotateTargetEnum annotateWithEnum) {
            this.annotateWithEnum = annotateWithEnum;
        }

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }
    }

    enum AnnotateSourceEnum {
        EXISTING;
    }

    enum AnnotateTargetEnum {
        EXISTING;
    }

    class NestedSource {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class NestedTarget {
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
