/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
