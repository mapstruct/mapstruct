/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1801.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable implementation of {@link Item}.
 * <p>
 * Superclass should expose a static subclass of the Builder to create immutable instance
 * {@code public static Builder extends ImmutableItem.Builder}.
 *
 * @author Zhizhi Deng
 */
@SuppressWarnings({"all"})
public final class ImmutableItem extends Item {
  private final String id;

  private ImmutableItem(String id) {
    this.id = id;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Item#getId() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableItem withId(String value) {
    if (this.id == value) return this;
    return new ImmutableItem(value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableItem} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableItem
        && equalTo((ImmutableItem) another);
  }

  private boolean equalTo(ImmutableItem another) {
    return id.equals(another.id);
  }

  /**
   * Computes a hash code from attributes: {@code id}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Item} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "Item{"
        + "id=" + id
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link Item} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Item instance
   */
  public static ImmutableItem copyOf(Item instance) {
    if (instance instanceof ImmutableItem) {
      return (ImmutableItem) instance;
    }
    return new Builder()
        .from(instance)
        .build();
  }

  /**
   * Builds instances of type {@link ImmutableItem ImmutableItem}.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private long initBits = 0x1L;

    private String id;

    Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Item} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Item instance) {
      id(instance.getId());
      return this;
    }

    /**
     * Initializes the value for the {@link Item#getId() id} attribute.
     * @param id The value for id
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(String id) {
      this.id = id;
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Builds a new {@link ImmutableItem ImmutableItem}.
     * @return An immutable instance of Item
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableItem build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableItem(id);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build Item, some of required attributes are not set " + attributes;
    }
  }
}
