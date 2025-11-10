/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.StringValue;
import org.junit.Test;
import org.mapstruct.itest.protobuf.EverythingMapper;

/**
 * Test for generation of Protobuf Builder Mapper implementations
 *
 * @author Christian Bandowski
 * @author Freeman
 */
public class ProtobufMapperTest {

    @Test
    public void testSimpleImmutableBuilderHappyPath() {
        PersonDto personDto = PersonMapper.INSTANCE.toDto( PersonProtos.Person.newBuilder()
            .setAge( 33 )
            .setName( "Bob" )
            .setAddress( PersonProtos.Person.Address.newBuilder()
                .setAddressLine( "Wild Drive" )
                .build() )
            .build() );

        assertThat( personDto.getAge() ).isEqualTo( 33 );
        assertThat( personDto.getName() ).isEqualTo( "Bob" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }

    @Test
    public void testLombokToImmutable() {
        PersonProtos.Person person = PersonMapper.INSTANCE.fromDto( new PersonDto( "Bob", 33, new AddressDto( "Wild Drive" ) ) );

        assertThat( person.getAge() ).isEqualTo( 33 );
        assertThat( person.getName() ).isEqualTo( "Bob" );
        assertThat( person.getAddress() ).isNotNull();
        assertThat( person.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }

    @Test
    public void testProtobufConvert() {
        Everything original = new Everything();

        // Primitive types
        original.setInt32( 42 );
        original.setInt64( 123456L );
        original.setFloat_( 3.14f );
        original.setDouble_( 2.718 );
        original.setBool( true );
        original.setString( "test string" );
        original.setBytes( ByteString.copyFromUtf8( "test bytes" ) );

        // Wrapper types
        original.setInt32Value( 100 );
        original.setInt64Value( 99999L );
        original.setFloatValue( FloatValue.of( 1.5f ) );
        original.setDoubleValue( 9.99 );
        original.setBoolValue( false );
        original.setStringValue( StringValue.of( "wrapped string" ) );
        original.setBytesValue( ByteString.copyFromUtf8( "wrapped bytes" ) );

        // Repeated fields
        original.setRepeatedInt32( new Integer[] { 1, 2, 3, 4, 5 } );
        Set<Long> int64Set = new HashSet<>();
        int64Set.add( 10L );
        int64Set.add( 20L );
        int64Set.add( 30L );
        original.setRepeatedInt64( int64Set );
        original.setRepeatedFloat( Arrays.asList( 1.1f, 2.2f, 3.3f ) );
        original.setRepeatedDouble( Arrays.asList(
            DoubleValue.of( 4.4 ),
            DoubleValue.of( 5.5 ),
            DoubleValue.of( 6.6 )
        ) );
        original.setRepeatedBool( Arrays.asList( BoolValue.of( true ), BoolValue.of( false ), BoolValue.of( true ) ) );
        original.setRepeatedString( Arrays.asList( "first", "second", "third" ) );
        original.setRepeatedBytes( Arrays.asList(
            BytesValue.of( ByteString.copyFromUtf8( "bytes1" ) ),
            BytesValue.of( ByteString.copyFromUtf8( "bytes2" ) )
        ) );

        // Map fields
        Map<Integer, ByteString> mapInt32String = new HashMap<>();
        mapInt32String.put( 1, ByteString.copyFromUtf8( "one" ) );
        mapInt32String.put( 2, ByteString.copyFromUtf8( "two" ) );
        original.setMapInt32String( mapInt32String );

        Map<Long, String> mapInt64String = new HashMap<>();
        mapInt64String.put( 100L, "hundred" );
        mapInt64String.put( 200L, "two hundred" );
        original.setMapInt64String( mapInt64String );

        Map<BoolValue, String> mapBoolString = new HashMap<>();
        mapBoolString.put( BoolValue.of( true ), "true value" );
        mapBoolString.put( BoolValue.of( false ), "false value" );
        original.setMapBoolString( mapBoolString );

        Map<String, String> mapStringString = new HashMap<>();
        mapStringString.put( "key1", "value1" );
        mapStringString.put( "key2", "value2" );
        original.setMapStringString( mapStringString );

        Map<String, BytesValue> mapStringBytes = new HashMap<>();
        mapStringBytes.put( "bytes_key", BytesValue.of( ByteString.copyFromUtf8( "bytes_value" ) ) );
        original.setMapStringBytes( mapStringBytes );

        // Message fields
        Everything.Message message = new Everything.Message();
        message.setId( 999L );
        message.setName( "test message" );
        original.setMessage( message );

        Everything.Message msg1 = new Everything.Message();
        msg1.setId( 1L );
        msg1.setName( "message 1" );
        Everything.Message msg2 = new Everything.Message();
        msg2.setId( 2L );
        msg2.setName( "message 2" );
        original.setRepeatedMessage( Arrays.asList( msg1, msg2 ) );

        Map<StringValue, Everything.Message> mapStringMessage = new HashMap<>();
        Everything.Message msg3 = new Everything.Message();
        msg3.setId( 3L );
        msg3.setName( "message 3" );
        mapStringMessage.put( StringValue.of( "msg_key" ), msg3 );
        original.setMapStringMessage( mapStringMessage );

        // Enum fields
        original.setEnum_( 1 );
        original.setOptionalEnum( 2 );
        original.setRepeatedEnum( Arrays.asList( "ENUM_VALUE_1", "ENUM_VALUE_2" ) );

        Map<String, Integer> mapStringEnum = new HashMap<>();
        mapStringEnum.put( "enum1", 1 );
        mapStringEnum.put( "enum2", 2 );
        original.setMapStringEnum( mapStringEnum );

        // Well-known types
        original.setTimestamp( Instant.ofEpochSecond( 1234567890, 123456789 ) );
        original.setDuration( Duration.ofSeconds( 3600, 500000000 ) );

        // Google type package
        original.setTimeOfDay( LocalTime.of( 14, 30, 45, 123456789 ) );
        original.setDate( "2023-12-25" );
        original.setDayOfWeek( DayOfWeek.MONDAY );
        original.setMonth( Month.JANUARY );

        // Oneof
        original.setOneofInt32( 12345 );

        // Convert
        EverythingProto2 proto2Message = EverythingMapper.INSTANCE.javaBeanToProto2( original );
        Everything proto2Result = EverythingMapper.INSTANCE.proto2ToJavaBean( proto2Message );

        EverythingProto3 proto3Message = EverythingMapper.INSTANCE.javaBeanToProto3( original );
        Everything proto3Result = EverythingMapper.INSTANCE.proto3ToJavaBean( proto3Message );

        EverythingEdition2023 edition2023Message = EverythingMapper.INSTANCE.javaBeanToEdition2023( original );
        Everything edition2023Result = EverythingMapper.INSTANCE.edition2023ToJavaBean( edition2023Message );

        // Use AssertJ recursive comparison to verify equality
        assertThat( proto3Result ).usingRecursiveComparison().isEqualTo( original );
        assertThat( proto2Result ).usingRecursiveComparison().isEqualTo( original );
        assertThat( edition2023Result ).usingRecursiveComparison().isEqualTo( original );
    }
}
