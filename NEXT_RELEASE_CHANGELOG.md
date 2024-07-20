### Features

### Enhancements

### Bugs

* Breaking change: Presence check method used only once when multiple source parameters are provided (#3601)

### Documentation

### Build

## Breaking changes

### Presence checks for source parameters

In 1.6, support for presence checks on source parameters has been added.
This means that even if you want to map a source parameter directly to some target property the new `@SourceParameterCondition` or `@Condition(appliesTo = ConditionStrategy.SOURCE_PARAMETERS)` should be used.

e.g.

If we had the following in 1.5:
```java
@Mapper
public interface OrderMapper {

    @Mapping(source = "dto", target = "customer", conditionQualifiedByName = "mapCustomerFromOrder")
    Order map(OrderDTO dto);

    @Condition
    @Named("mapCustomerFromOrder")
    default boolean mapCustomerFromOrder(OrderDTO dto) {
        return dto != null && dto.getCustomerName() != null;
    }

}
```

Them MapStruct would generate

```java
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order map(OrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        if ( mapCustomerFromOrder( dto ) ) {
            order.setCustomer( orderDtoToCustomer( orderDTO ) );
        }

        return order;
    }
}
```

In order for the same to be generated in 1.6, the mapper needs to look like this:

```java
@Mapper
public interface OrderMapper {

    @Mapping(source = "dto", target = "customer", conditionQualifiedByName = "mapCustomerFromOrder")
    Order map(OrderDTO dto);

    @SourceParameterCondition
    @Named("mapCustomerFromOrder")
    default boolean mapCustomerFromOrder(OrderDTO dto) {
        return dto != null && dto.getCustomerName() != null;
    }

}
```

