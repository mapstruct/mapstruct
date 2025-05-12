### Features

* Support for Java 21 Sequenced Collections (#3240)


### Enhancements

* Add support for locale parameter for numberFormat and dateFormat (#3628)
* Detect Builder without a factory method (#3729) - With this if there is an inner class that ends with `Builder` and has a constructor with parameters, 
it will be treated as a potential builder. 
Builders through static methods on the type have a precedence.
* Behaviour change: Warning when the target has no target properties (#1140)



### Bugs

* Improve error message when mapping non-iterable to array (#3786)

### Documentation

### Build

### Behaviour Change

#### Warning when the target has no target properties

With this change, if the target bean does not have any target properties, a warning will be shown.
This is like this to avoid potential mistakes by users, where they might think that the target bean has properties, but it does not.
