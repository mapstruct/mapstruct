/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
import org.jspecify.annotations.NullMarked;

@NullMarked
module test.mapstruct {
    requires static org.jspecify;
    requires org.mapstruct;

    exports org.mapstruct.itest.modules.nullmarked;
    exports org.mapstruct.itest.modules.nullunmarked;
}
