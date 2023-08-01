/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable implementation of {@link ATarget}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableATarget.builder()}.
 */
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableATarget implements ATarget {
  private final ImmutableBTarget b;

  private ImmutableATarget(ImmutableBTarget b) {
    this.b = b;
  }

  /**
   * @return The value of the {@code b} attribute
   */
  @Override
  public Optional<ImmutableBTarget> getB() {
    return Optional.ofNullable(b);
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link ATarget#getB() b} attribute.
   * @param value The value for b
   * @return A modified copy of {@code this} object
   */
  public final ImmutableATarget withB(ImmutableBTarget value) {
    ImmutableBTarget newValue = Objects.requireNonNull(value, "b");
    if (this.b == newValue) return this;
    return new ImmutableATarget(newValue);
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link ATarget#getB() b} attribute.
   * A shallow reference equality check is used on unboxed optional value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for b
   * @return A modified copy of {@code this} object
   */
  @SuppressWarnings("unchecked") // safe covariant cast
  public final ImmutableATarget withB(Optional<? extends ImmutableBTarget> optional) {
    ImmutableBTarget value = optional.orElse(null);
    if (this.b == value) return this;
    return new ImmutableATarget(value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableATarget} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableATarget
        && equalTo(0, (ImmutableATarget) another);
  }

  private boolean equalTo(int synthetic, ImmutableATarget another) {
    return Objects.equals(b, another.b);
  }

  /**
   * Computes a hash code from attributes: {@code b}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(b);
    return h;
  }

  /**
   * Prints the immutable value {@code ATarget} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("ATarget{");
    if (b != null) {
      builder.append("b=").append(b);
    }
    return builder.append("}").toString();
  }

  /**
   * Creates an immutable copy of a {@link ATarget} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ATarget instance
   */
  public static ImmutableATarget copyOf(ATarget instance) {
    if (instance instanceof ImmutableATarget) {
      return (ImmutableATarget) instance;
    }
    return ImmutableATarget.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableATarget ImmutableATarget}.
   * <pre>
   * ImmutableATarget.builder()
   *    .b(ImmutableBTarget) // optional {@link ATarget#getB() b}
   *    .build();
   * </pre>
   * @return A new ImmutableATarget builder
   */
  public static ImmutableATarget.Builder builder() {
    return new ImmutableATarget.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableATarget ImmutableATarget}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private ImmutableBTarget b;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ATarget} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ATarget instance) {
      Objects.requireNonNull(instance, "instance");
      Optional<ImmutableBTarget> bOptional = instance.getB();
      if (bOptional.isPresent()) {
        b(bOptional);
      }
      return this;
    }

    /**
     * Initializes the optional value {@link ATarget#getB() b} to b.
     * @param b The value for b
     * @return {@code this} builder for chained invocation
     */
    public final Builder b(ImmutableBTarget b) {
      this.b = Objects.requireNonNull(b, "b");
      return this;
    }

    /**
     * Initializes the optional value {@link ATarget#getB() b} to b.
     * @param b The value for b
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder b(Optional<? extends ImmutableBTarget> b) {
      this.b = b.orElse(null);
      return this;
    }

    /**
     * Builds a new {@link ImmutableATarget ImmutableATarget}.
     * @return An immutable instance of ATarget
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableATarget build() {
      return new ImmutableATarget(b);
    }
  }
}
