/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.google.protobuf;

/**
 * Mock interface to simulate protobuf's MessageOrBuilder for testing.
 * This allows testing of ProtobufAccessorNamingStrategy without requiring
 * the actual protobuf dependency.
 */
public interface MessageOrBuilder {
}
