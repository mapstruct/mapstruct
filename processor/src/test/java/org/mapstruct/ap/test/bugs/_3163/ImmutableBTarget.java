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
 * Immutable implementation of {@link BTarget}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableBTarget.builder()}.
 */
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableBTarget implements BTarget {
  private final String s;

  private ImmutableBTarget(String s) {
    this.s = s;
  }

  /**
   * @return The value of the {@code s} attribute
   */
  @Override
  public String getS() {
    return s;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link BTarget#getS() s} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for s
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableBTarget withS(String value) {
    String newValue = Objects.requireNonNull(value, "s");
    if (this.s.equals(newValue)) return this;
    return new ImmutableBTarget(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableBTarget} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableBTarget
        && equalTo(0, (ImmutableBTarget) another);
  }

  private boolean equalTo(int synthetic, ImmutableBTarget another) {
    return s.equals(another.s);
  }

  /**
   * Computes a hash code from attributes: {@code s}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + s.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code BTarget} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "BTarget{"
        + "s=" + s
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link BTarget} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable BTarget instance
   */
  public static ImmutableBTarget copyOf(BTarget instance) {
    if (instance instanceof ImmutableBTarget) {
      return (ImmutableBTarget) instance;
    }
    return ImmutableBTarget.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableBTarget ImmutableBTarget}.
   * <pre>
   * ImmutableBTarget.builder()
   *    .s(String) // required {@link BTarget#getS() s}
   *    .build();
   * </pre>
   * @return A new ImmutableBTarget builder
   */
  public static ImmutableBTarget.Builder builder() {
    return new ImmutableBTarget.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableBTarget ImmutableBTarget}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_S = 0x1L;
    private long initBits = 0x1L;

    private String s;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code BTarget} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(BTarget instance) {
      Objects.requireNonNull(instance, "instance");
      s(instance.getS());
      return this;
    }

    /**
     * Initializes the value for the {@link BTarget#getS() s} attribute.
     * @param s The value for s
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder s(String s) {
      this.s = Objects.requireNonNull(s, "s");
      initBits &= ~INIT_BIT_S;
      return this;
    }

    /**
     * Builds a new {@link ImmutableBTarget ImmutableBTarget}.
     * @return An immutable instance of BTarget
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableBTarget build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableBTarget(s);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_S) != 0) attributes.add("s");
      return "Cannot build BTarget, some of required attributes are not set " + attributes;
    }
  }
}
