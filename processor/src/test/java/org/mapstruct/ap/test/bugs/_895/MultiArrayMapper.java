/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._895;

import java.util.List;

import org.mapstruct.Mapper;

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

    class WithListOfByteArray {
        private List<byte[]> bytes;

        public List<byte[]> getBytes() {
            return bytes;
        }

        public void setBytes(List<byte[]> bytes) {
            this.bytes = bytes;
        }
    }
}
