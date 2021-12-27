package org.mapstruct.ap.test.nullvaluemappingallnull;

import org.mapstruct.ap.test.nullvaluemappingallnull.vo.*;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for the strategies for mapping {@code null} values, given via {@code NullValueMapping} etc.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "1166" )
@WithClasses({
    Address.class,
    DefaultLeaf.class,
    EnumLeaf.class,
    NestedCollection.class,
    NestedLeaf.class,
    NestedMiddle.class,
    Order.class,
    OrderDTO.class,
    OrderItem.class,
    OrderItemDTO.class,
    OrderMapper.class,
    CentralConfig1166.class
})
public class NullValueMappingTest {

    @ProcessorTest
    public void shouldProvideMapperInstance() {
        org.assertj.core.api.Assertions.assertThat( OrderMapper.INSTANCE ).isNotNull();
    }

    @ProcessorTest
    public void convertDtoToOrder() {
        //given
        OrderDTO orderDto = new OrderDTO();

        //when
        Order order = OrderMapper.INSTANCE.convertFromOrderDTO(orderDto);

        //then

        org.assertj.core.api.Assertions.assertThat( order.getAddress() ).isNull();
        org.assertj.core.api.Assertions.assertThat( order.getDefaultLeaf() ).isNotNull();
        org.assertj.core.api.Assertions.assertThat( order.getNestedMiddle() ).isNull();
    }

    @ProcessorTest
    public void convertMapToOrder() {
        //given
        Map<String,String> orderDto = new HashMap<>();

        //when
        Order order = OrderMapper.INSTANCE.convertFromOrderDTO(orderDto);

        //then

        org.assertj.core.api.Assertions.assertThat( order.getAddress() ).isNull();
        org.assertj.core.api.Assertions.assertThat( order.getDefaultLeaf() ).isNotNull();
        org.assertj.core.api.Assertions.assertThat( order.getNestedMiddle() ).isNull();
    }

    @ProcessorTest
    public void convertOrderToDto() {
        //given
        OrderDTO orderDto = new OrderDTO();

        //when
        Order order = OrderMapper.INSTANCE.convertFromOrderDTO(orderDto);

        //then

        org.assertj.core.api.Assertions.assertThat( order.getAddress() ).isNull();
        org.assertj.core.api.Assertions.assertThat( order.getDefaultLeaf() ).isNotNull();
        org.assertj.core.api.Assertions.assertThat( order.getNestedMiddle() ).isNull();
    }
}
