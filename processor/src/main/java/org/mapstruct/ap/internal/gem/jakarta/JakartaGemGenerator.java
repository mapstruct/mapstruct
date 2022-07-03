/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem.jakarta;

import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlElementRef;
import org.mapstruct.tools.gem.GemDefinition;

/**
 * This class is a temporary solution to an issue in the Gem Tools library.
 *
 * <p>
 * This class can be merged with {@link org.mapstruct.ap.internal.gem.GemGenerator}
 * after the mentioned issue is resolved.
 * </p>
 *
 * @see <a href="https://github.com/mapstruct/tools-gem/issues/10">Gem Tools issue #10</a>
 * @author Iaroslav Bogdanchikov
 */
@GemDefinition(XmlElementDecl.class)
@GemDefinition(XmlElementRef.class)
class JakartaGemGenerator {
}
