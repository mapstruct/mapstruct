/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
