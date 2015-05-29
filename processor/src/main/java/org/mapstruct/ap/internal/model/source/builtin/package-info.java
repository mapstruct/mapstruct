/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
/**
 * <p>
 * Contains "built-in methods" which may be added as private methods to a generated mapper. Built-in methods are an
 * alternative to primitive conversions in cases where those don't suffice, e.g. if several lines of code are required
 * for a conversion or an exception needs to be handled. Each built-in method has a corresponding template which
 * contains the source code of the method.
 * </p>
 */
package org.mapstruct.ap.internal.model.source.builtin;
