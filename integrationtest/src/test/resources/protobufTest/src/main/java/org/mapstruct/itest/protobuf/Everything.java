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
import com.google.protobuf.StringValue;

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
    private int oneofInt32;
    private String oneofString;
    private Integer oneofEnum;
    private Message oneofMessage;

    public Integer getInt32() {
        return this.int32;
    }

    public long getInt64() {
        return this.int64;
    }

    public Float getFloat_() {
        return this.float_;
    }

    public Double getDouble_() {
        return this.double_;
    }

    public Boolean getBool() {
        return this.bool;
    }

    public String getString() {
        return this.string;
    }

    public ByteString getBytes() {
        return this.bytes;
    }

    public Integer getInt32Value() {
        return this.int32Value;
    }

    public long getInt64Value() {
        return this.int64Value;
    }

    public FloatValue getFloatValue() {
        return this.floatValue;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public boolean isBoolValue() {
        return this.boolValue;
    }

    public StringValue getStringValue() {
        return this.stringValue;
    }

    public ByteString getBytesValue() {
        return this.bytesValue;
    }

    public Integer[] getRepeatedInt32() {
        return this.repeatedInt32;
    }

    public Set<Long> getRepeatedInt64() {
        return this.repeatedInt64;
    }

    public List<Float> getRepeatedFloat() {
        return this.repeatedFloat;
    }

    public List<DoubleValue> getRepeatedDouble() {
        return this.repeatedDouble;
    }

    public List<BoolValue> getRepeatedBool() {
        return this.repeatedBool;
    }

    public List<String> getRepeatedString() {
        return this.repeatedString;
    }

    public List<BytesValue> getRepeatedBytes() {
        return this.repeatedBytes;
    }

    public Map<Integer, ByteString> getMapInt32String() {
        return this.mapInt32String;
    }

    public Map<Long, String> getMapInt64String() {
        return this.mapInt64String;
    }

    public Map<BoolValue, String> getMapBoolString() {
        return this.mapBoolString;
    }

    public Map<String, String> getMapStringString() {
        return this.mapStringString;
    }

    public Map<String, BytesValue> getMapStringBytes() {
        return this.mapStringBytes;
    }

    public Message getMessage() {
        return this.message;
    }

    public List<Message> getRepeatedMessage() {
        return this.repeatedMessage;
    }

    public Map<StringValue, Message> getMapStringMessage() {
        return this.mapStringMessage;
    }

    public Integer getEnum_() {
        return this.enum_;
    }

    public int getOptionalEnum() {
        return this.optionalEnum;
    }

    public List<String> getRepeatedEnum() {
        return this.repeatedEnum;
    }

    public Map<String, Integer> getMapStringEnum() {
        return this.mapStringEnum;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public LocalTime getTimeOfDay() {
        return this.timeOfDay;
    }

    public String getDate() {
        return this.date;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public Month getMonth() {
        return this.month;
    }

    public void setInt32(Integer int32) {
        this.int32 = int32;
    }

    public void setInt64(long int64) {
        this.int64 = int64;
    }

    public void setFloat_(Float float_) {
        this.float_ = float_;
    }

    public void setDouble_(Double double_) {
        this.double_ = double_;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setBytes(ByteString bytes) {
        this.bytes = bytes;
    }

    public void setInt32Value(Integer int32Value) {
        this.int32Value = int32Value;
    }

    public void setInt64Value(long int64Value) {
        this.int64Value = int64Value;
    }

    public void setFloatValue(FloatValue floatValue) {
        this.floatValue = floatValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public void setStringValue(StringValue stringValue) {
        this.stringValue = stringValue;
    }

    public void setBytesValue(ByteString bytesValue) {
        this.bytesValue = bytesValue;
    }

    public void setRepeatedInt32(Integer[] repeatedInt32) {
        this.repeatedInt32 = repeatedInt32;
    }

    public void setRepeatedInt64(Set<Long> repeatedInt64) {
        this.repeatedInt64 = repeatedInt64;
    }

    public void setRepeatedFloat(List<Float> repeatedFloat) {
        this.repeatedFloat = repeatedFloat;
    }

    public void setRepeatedDouble(List<DoubleValue> repeatedDouble) {
        this.repeatedDouble = repeatedDouble;
    }

    public void setRepeatedBool(List<BoolValue> repeatedBool) {
        this.repeatedBool = repeatedBool;
    }

    public void setRepeatedString(List<String> repeatedString) {
        this.repeatedString = repeatedString;
    }

    public void setRepeatedBytes(List<BytesValue> repeatedBytes) {
        this.repeatedBytes = repeatedBytes;
    }

    public void setMapInt32String(Map<Integer, ByteString> mapInt32String) {
        this.mapInt32String = mapInt32String;
    }

    public void setMapInt64String(Map<Long, String> mapInt64String) {
        this.mapInt64String = mapInt64String;
    }

    public void setMapBoolString(Map<BoolValue, String> mapBoolString) {
        this.mapBoolString = mapBoolString;
    }

    public void setMapStringString(Map<String, String> mapStringString) {
        this.mapStringString = mapStringString;
    }

    public void setMapStringBytes(Map<String, BytesValue> mapStringBytes) {
        this.mapStringBytes = mapStringBytes;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setRepeatedMessage(List<Message> repeatedMessage) {
        this.repeatedMessage = repeatedMessage;
    }

    public void setMapStringMessage(Map<StringValue, Message> mapStringMessage) {
        this.mapStringMessage = mapStringMessage;
    }

    public void setEnum_(Integer enum_) {
        this.enum_ = enum_;
    }

    public void setOptionalEnum(int optionalEnum) {
        this.optionalEnum = optionalEnum;
    }

    public void setRepeatedEnum(List<String> repeatedEnum) {
        this.repeatedEnum = repeatedEnum;
    }

    public void setMapStringEnum(Map<String, Integer> mapStringEnum) {
        this.mapStringEnum = mapStringEnum;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTimeOfDay(LocalTime timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getOneofInt32() {
        return oneofInt32;
    }

    public void setOneofInt32(int oneofInt32) {
        this.oneofInt32 = oneofInt32;
    }

    public String getOneofString() {
        return oneofString;
    }

    public void setOneofString(String oneofString) {
        this.oneofString = oneofString;
    }

    public Integer getOneofEnum() {
        return oneofEnum;
    }

    public void setOneofEnum(Integer oneofEnum) {
        this.oneofEnum = oneofEnum;
    }

    public Message getOneofMessage() {
        return oneofMessage;
    }

    public void setOneofMessage(Message oneofMessage) {
        this.oneofMessage = oneofMessage;
    }

    public static class Message {
        private long id;
        private String name;

        public long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
