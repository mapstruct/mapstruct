/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targetpropertyname;

/**
 * @author Nikola Ivačič <nikola.ivacic@dropchop.org> on 20. 06. 22.
 */
public class AddressDto implements DomainModel {
  private String street;

  public AddressDto() {
  }

  public AddressDto(String street) {
    this.street = street;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }
}
