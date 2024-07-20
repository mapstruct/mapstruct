### Enhancements

* Breaking change: (#3574) - 
This reverts #2560, because we've decided that `@BeanMapping(ignoreByDefault = true)` should only be applied to target properties and not to source properties. 
Source properties are ignored anyway, the `BeanMapping#unmappedSourcePolicy` should be used to control what should happen with unmapped source policy

### Bugs

* Breaking change: Presence check method used only once when multiple source parameters are provided (#3601)
* Fix `@SubclassMapping` not working with `@BeanMapping#ignoreUnmappedSourceProperties` (#3609)
* Fix duplicate method generation with recursive auto mapping (#3591)

### Documentation

* Fix documentation of `unmappedSourcePolicy` default value (#3635)
* Fix documentation link of before and after mapping when using builders (#3639)
* Fix typo in experimental note (#3634)
* Add example classes for the passing target type documentation (#3504)

### Build

* Enforce whitespaces around the for colon with CheckStyle (#3642)

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

Then MapStruct would generate

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

