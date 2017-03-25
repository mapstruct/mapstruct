/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.bugs._1148;

/**
 * @author Filip Hrisafov
 */
class Entity {

    static class NestedClient {
        //CHECKSTYLE:OFF
        public long id;
        //CHECKSTYLE:ON
    }

    static class Client {

        //CHECKSTYLE:OFF
        public NestedClient nestedClient;
        //CHECKSTYLE:ON
    }

    static class Dto {

        //CHECKSTYLE:OFF
        public long recipientId;
        public long senderId;
        public NestedDto nestedDto;
        public NestedDto nestedDto2;
        public ClientDto sameLevel;
        public ClientDto sameLevel2;
        public ClientDto level;
        public ClientDto level2;
        //CHECKSTYLE:ON
    }

    static class ClientDto {
        //CHECKSTYLE:OFF
        public NestedDto client;

        //CHECKSTYLE:ON
        ClientDto(NestedDto client) {
            this.client = client;
        }
    }

    static class NestedDto {
        //CHECKSTYLE:OFF
        public long id;
        //CHECKSTYLE:ON

        NestedDto(long id) {
            this.id = id;
        }
    }

    //CHECKSTYLE:OFF
    public Client client;
    public Client client2;
    public NestedClient nested;
    public NestedClient nested2;
    private Client recipient;
    private Client sender;
    private long id;
    private long id2;
    //CHECKSTYLE:OFF

    public Client getRecipient() {
        return recipient;
    }

    public void setRecipient(Client recipient) {
        this.recipient = recipient;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }
}
