/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4037;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    uses = Issue4037Mapper.PrechecksSupport.class
)
public interface Issue4037Mapper {

    Issue4037Mapper INSTANCE = Mappers.getMapper( Issue4037Mapper.class );

    @Mapping(target = "browserId", source = "requestBean.deviceInfo.browserId")
    @Mapping(target = "osId", source = "requestBean.deviceInfo.osId")
    @Mapping(target = "deviceName", source = "requestBean.deviceInfo.deviceName")
    AudienceProfileRequest map(RequestBean requestBean, String slotId);

    class RequestBean {
        private DeviceInfo deviceInfo;

        public DeviceInfo getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
        }
    }

    class DeviceInfo {
        private Integer browserId;
        private Integer osId;
        private String deviceName;

        public Integer getBrowserId() {
            return browserId;
        }

        public void setBrowserId(Integer browserId) {
            this.browserId = browserId;
        }

        public Integer getOsId() {
            return osId;
        }

        public void setOsId(Integer osId) {
            this.osId = osId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }

    class AudienceProfileRequest {
        private Integer browserId;
        private Integer osId;
        private String deviceName;

        public Integer getBrowserId() {
            return browserId;
        }

        public void setBrowserId(Integer browserId) {
            this.browserId = browserId;
        }

        public Integer getOsId() {
            return osId;
        }

        public void setOsId(Integer osId) {
            this.osId = osId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }

    class PrechecksSupport {

        @Condition
        public boolean isNotBlank(String value) {
            return value != null && !value.trim().isEmpty();
        }
    }
}
