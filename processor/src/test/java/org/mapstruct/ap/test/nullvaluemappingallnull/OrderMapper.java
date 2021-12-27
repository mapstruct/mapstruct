package org.mapstruct.ap.test.nullvaluemappingallnull;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.nullvaluemapping.CarMapper;
import org.mapstruct.ap.test.nullvaluemappingallnull.vo.Order;
import org.mapstruct.ap.test.nullvaluemappingallnull.vo.OrderDTO;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    // check that more than one null mapping collapses to null
    @Mapping(source="addressLine1", target="address.line1")
    @Mapping(source="addressLine2", target="address.line2")
    @Mapping(source="addressLine3", target="address.line3")
    // check that multiple nested mappings collapse to null
    @Mapping(source="leafOid", target="nestedMiddle.leaf.oid")
    // check that mappings with default values do NOT get collapsed to null (Note: Future configuration option to ignore default values, constants, expressions, etc)
    @Mapping(source="defaultLeafName", target="defaultLeaf.name")
    @Mapping(source="defaultLeafClassification", target="defaultLeaf.classification", defaultValue = "unclassified")
    // enums
    @Mapping(source="enumLeafStatus", target="enumLeaf.status")
    Order convertFromOrderDTO(OrderDTO orderDto);

    @InheritInverseConfiguration
    OrderDTO convertFromOrder(Order order);

    // check that more than one null mapping collapses to null
    @Mapping(source="addressLine1", target="address.line1")
    @Mapping(source="addressLine2", target="address.line2")
    @Mapping(source="addressLine3", target="address.line3")
    // check that multiple nested mappings collapse to null
    @Mapping(source="leafOid", target="nestedMiddle.leaf.oid")
    // check that mappings with default values do NOT get collapsed to null (Note: Future configuration option to ignore default values, constants, expressions, etc)
    @Mapping(source="defaultLeafName", target="defaultLeaf.name")
    @Mapping(source="defaultLeafClassification", target="defaultLeaf.classification", defaultValue = "unclassified")
    // enums
    @Mapping(source="enumLeafStatus", target="enumLeaf.status")
    Order convertFromOrderDTO(Map<String,String> orderDto);

}
