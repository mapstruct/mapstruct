/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Immutable implementation of {@link ASource}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableASource.builder()}.
 */
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableASource implements ASource {
  private final ImmutableBSource b;

  private ImmutableASource(ImmutableBSource b) {
    this.b = b;
  }

  /**
   * @return The value of the {@code b} attribute
   */
  @Override
  public ImmutableBSource getB() {
    return b;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ASource#getB() b} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for b
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableASource withB(ImmutableBSource value) {
    if (this.b == value) return this;
    ImmutableBSource newValue = Objects.requireNonNull(value, "b");
    return new ImmutableASource(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableASource} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableASource
        && equalTo(0, (ImmutableASource) another);
  }

  private boolean equalTo(int synthetic, ImmutableASource another) {
    return b.equals(another.b);
  }

  /**
   * Computes a hash code from attributes: {@code b}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + b.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ASource} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "ASource{"
        + "b=" + b
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link ASource} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ASource instance
   */
  public static ImmutableASource copyOf(ASource instance) {
    if (instance instanceof ImmutableASource) {
      return (ImmutableASource) instance;
    }
    return ImmutableASource.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableASource ImmutableASource}.
   * <pre>
   * ImmutableASource.builder()
   *    .b(com.bol.generics.ImmutableBSource) // required {@link ASource#getB() b}
   *    .build();
   * </pre>
   * @return A new ImmutableASource builder
   */
  public static ImmutableASource.Builder builder() {
    return new ImmutableASource.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableASource ImmutableASource}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_B = 0x1L;
    private long initBits = 0x1L;

    private ImmutableBSource b;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ASource} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ASource instance) {
      Objects.requireNonNull(instance, "instance");
      b(instance.getB());
      return this;
    }

    /**
     * Initializes the value for the {@link ASource#getB() b} attribute.
     * @param b The value for b
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder b(ImmutableBSource b) {
      this.b = Objects.requireNonNull(b, "b");
      initBits &= ~INIT_BIT_B;
      return this;
    }

    /**
     * Builds a new {@link ImmutableASource ImmutableASource}.
     * @return An immutable instance of ASource
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableASource build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableASource(b);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_B) != 0) attributes.add("b");
      return "Cannot build ASource, some of required attributes are not set " + attributes;
    }
  }
}
