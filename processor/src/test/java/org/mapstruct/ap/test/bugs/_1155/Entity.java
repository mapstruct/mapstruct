/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1155;

/**
 * @author Filip Hrisafov
 */
class Entity {

    static class Client {

        //CHECKSTYLE:OFF
        public long id;
        //CHECKSTYLE:ON
    }

    static class Dto {

        //CHECKSTYLE:OFF
        public long clientId;
        //CHECKSTYLE:ON
    }

    //CHECKSTYLE:OFF
    public Client client;
    //CHECKSTYLE:ON
}
