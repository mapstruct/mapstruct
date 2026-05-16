/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper for holding Protobuf FQN.
 *
 * @author Freeman
 */
public class ProtobufConstants {

    public static final String MESSAGE_LITE_OR_BUILDER_FQN = "com.google.protobuf.MessageLiteOrBuilder";
    public static final String PROTOCOL_MESSAGE_ENUM_FQN = "com.google.protobuf.ProtocolMessageEnum";

    public static final String BYTE_STRING_FQN = "com.google.protobuf.ByteString";

    // Well known types
    public static final String TIMESTAMP_FQN = "com.google.protobuf.Timestamp";
    public static final String DURATION_FQN = "com.google.protobuf.Duration";

    // Wrapper types
    public static final String DOUBLE_VALUE_FQN = "com.google.protobuf.DoubleValue";
    public static final String FLOAT_VALUE_FQN = "com.google.protobuf.FloatValue";
    public static final String INT64_VALUE_FQN = "com.google.protobuf.Int64Value";
    public static final String UINT64_VALUE_FQN = "com.google.protobuf.UInt64Value";
    public static final String INT32_VALUE_FQN = "com.google.protobuf.Int32Value";
    public static final String UINT32_VALUE_FQN = "com.google.protobuf.UInt32Value";
    public static final String BOOL_VALUE_FQN = "com.google.protobuf.BoolValue";
    public static final String STRING_VALUE_FQN = "com.google.protobuf.StringValue";
    public static final String BYTES_VALUE_FQN = "com.google.protobuf.BytesValue";

    // proto-google-common-protos google/type package
    public static final String DATE_FQN = "com.google.type.Date";
    public static final String DAY_OF_WEEK_FQN = "com.google.type.DayOfWeek";
    public static final String MONTH_FQN = "com.google.type.Month";
    public static final String TIME_OF_DAY_FQN = "com.google.type.TimeOfDay";

    private ProtobufConstants() {
    }
}
