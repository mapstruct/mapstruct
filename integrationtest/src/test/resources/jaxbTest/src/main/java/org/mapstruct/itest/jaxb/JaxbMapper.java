/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.itest.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;

import org.mapstruct.itest.jaxb.xsd.test1.ObjectFactory;

/**
 * This class can be removed as soon as MapStruct is capable of generating List mappings.
 *
 * @author Sjaak Derksen
 */
public class JaxbMapper {

    private final ObjectFactory of = new ObjectFactory();

    /**
     * This method is needed, because currently MapStruct is not capable of selecting
     * the proper factory method for Lists
     *
     * @param orderDetailsDescriptions
     *
     * @return
     */
    List<JAXBElement<String>> toJaxbList(List<String> orderDetailsDescriptions) {

        List<JAXBElement<String>> result = new ArrayList<JAXBElement<String>>();
        for ( String orderDetailDescription : orderDetailsDescriptions ) {
            result.add( of.createOrderDetailsTypeDescription( orderDetailDescription ) );
        }
        return result;
    }

}
