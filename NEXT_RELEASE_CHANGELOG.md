### Features

* Support for Java 21 Sequenced Collections (#3240)


### Enhancements

* Add support for locale parameter for numberFormat and dateFormat (#3628)
* Behaviour change: Warning when the target has no target properties (#1140)



### Bugs

* Improve error message when mapping non-iterable to array (#3786)

### Documentation

### Build

### Behaviour Change

#### Warning when the target has no target properties

With this change, if the target bean does not have any target properties, a warning will be shown.
This is like this to avoid potential mistakes by users, where they might think that the target bean has properties, but it does not.
