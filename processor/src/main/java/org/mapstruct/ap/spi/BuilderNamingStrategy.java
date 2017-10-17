/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.spi;

/**
 * A service provider interface for the mapping between builder method names and properties.  It's common
 * to use a different accessor naming convention for builders than for POJOs.  Example:
 *
 * <code>
 *     ContactBuilder contactBuilder = Contact.builder();
 *     contactBuilder.firstName("Frank");
 *     contactBuilder.lastName("Barnes");
 *
 *     Contact contact = contactBuilder.build();
 *     assert "Frank".equals(contact.getFirstName());
 * </code>
 *
 * @author Eric Martineau
 * @author Gunnar Morling
 */
public interface BuilderNamingStrategy extends AccessorNamingStrategy {
}
