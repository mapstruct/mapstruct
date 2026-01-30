/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.protobuf;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import lombok.Data;

@Data
public class Everything {
    // Primitive types
    private Integer int32;
    private long int64;
    private Float float_;
    private Double double_;
    private Boolean bool;
    private String string;
    private ByteString bytes;

    // Wrapper types
    private Integer int32Value;
    private long int64Value;
    private FloatValue floatValue;
    private double doubleValue;
    private boolean boolValue;
    private StringValue stringValue;
    private ByteString bytesValue;

    // repeated
    private Integer[] repeatedInt32;
    private Set<Long> repeatedInt64;
    private List<Float> repeatedFloat;
    private List<DoubleValue> repeatedDouble;
    private List<BoolValue> repeatedBool;
    private List<String> repeatedString;
    private List<BytesValue> repeatedBytes;

    // map
    private Map<Integer, ByteString> mapInt32String;
    private Map<Long, String> mapInt64String;
    private Map<BoolValue, String> mapBoolString;
    private Map<String, String> mapStringString;
    private Map<String, BytesValue> mapStringBytes;

    // message
    private Message message;
    private List<Message> repeatedMessage;
    private Map<StringValue, Message> mapStringMessage;

    // enum
    private Integer enum_;
    private int optionalEnum;
    private List<String> repeatedEnum;
    private Map<String, Integer> mapStringEnum;

    // wellknown
    private Instant timestamp;
    private Duration duration;

    // google/type package
    private LocalTime timeOfDay;
    private String date;
    private DayOfWeek dayOfWeek;
    private Month month;

    // oneof
    private Int32Value oneofInt32;
    private StringValue oneofString;
    private Integer oneofEnum;
    private Message oneofMessage;

    //  deprecated field
    @Deprecated
    private Integer deprecatedInt32;
    @Deprecated
    private String deprecatedString;
    @Deprecated
    private List<String> deprecatedRepeatedString;
    @Deprecated
    private Map<String, Integer> deprecatedMapStringInt32;
    @Deprecated
    private Integer deprecatedEnum;

    //  special naming
    private String strBytes;
    private Integer enValue;
    private List<String> reStringList;
    private List<Integer> reEnumValueList;
    private Map<String, String> maStringStringMap;
    private Map<String, String> maStringEnumMap;
    private Message msgBuilder;
    private Message msgOrBuilder;

    @Data
    public static class Message {
        private long id;
        private String name;
    }
}
