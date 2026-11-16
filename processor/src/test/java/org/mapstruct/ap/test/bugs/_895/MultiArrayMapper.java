/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._895;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface MultiArrayMapper {
    WithListOfByteArray convert(WithArrayOfByteArray b);

    WithArrayOfByteArray convert(WithListOfByteArray b);

    class WithArrayOfByteArray {
        private byte[][] bytes;

        public byte[][] getBytes() {
            return bytes;
        }

        public void setBytes(byte[][] bytes) {
            this.bytes = bytes;
        }
    }

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    int[][] copyMultiArray(int[][] multiArray);

    WithListOfGenericArray<String> convertGeneric(WithArrayOfGenericArray<String> b);

    WithArrayOfGenericArray<String> convertGeneric(WithListOfGenericArray<String> b);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    WithArrayOfGenericArray<String>[][] copyGenericMultiArray(WithArrayOfGenericArray<String>[][] multiArray);

    class WithListOfByteArray {
        private List<byte[]> bytes;

        public List<byte[]> getBytes() {
            return bytes;
        }

        public void setBytes(List<byte[]> bytes) {
            this.bytes = bytes;
        }
    }

    class WithArrayOfGenericArray<T> {
        private T [][] data;

        public T[][] getData() {
            return data;
        }

        public void setData(T[][] data) {
            this.data = data;
        }
    }

    class WithListOfGenericArray<T> {
        private List<T[]> data;

        public List<T[]> getData() {
            return data;
        }

        public void setData(List<T[]> data) {
            this.data = data;
        }
    }
}
