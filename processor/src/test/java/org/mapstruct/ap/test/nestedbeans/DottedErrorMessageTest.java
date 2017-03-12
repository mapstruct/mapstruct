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
package org.mapstruct.ap.test.nestedbeans;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedbeans.erroneous.Computer;
import org.mapstruct.ap.test.nestedbeans.erroneous.ComputerDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.Dictionary;
import org.mapstruct.ap.test.nestedbeans.erroneous.DictionaryDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.ForeignWord;
import org.mapstruct.ap.test.nestedbeans.erroneous.ForeignWordDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.Cat;
import org.mapstruct.ap.test.nestedbeans.erroneous.CatDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.Info;
import org.mapstruct.ap.test.nestedbeans.erroneous.InfoDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.RoofTypeMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableEnumMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.UserDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.User;
import org.mapstruct.ap.test.nestedbeans.erroneous.WheelDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.Wheel;
import org.mapstruct.ap.test.nestedbeans.erroneous.Car;
import org.mapstruct.ap.test.nestedbeans.erroneous.CarDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.ExternalRoofType;
import org.mapstruct.ap.test.nestedbeans.erroneous.House;
import org.mapstruct.ap.test.nestedbeans.erroneous.HouseDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.Color;
import org.mapstruct.ap.test.nestedbeans.erroneous.ColorDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.Roof;
import org.mapstruct.ap.test.nestedbeans.erroneous.RoofType;
import org.mapstruct.ap.test.nestedbeans.erroneous.RoofDto;
import org.mapstruct.ap.test.nestedbeans.erroneous.UnmappableDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.erroneous.Word;
import org.mapstruct.ap.test.nestedbeans.erroneous.WordDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({
    Car.class, CarDto.class, Color.class, ColorDto.class,
    House.class, HouseDto.class, Roof.class, RoofDto.class,
    RoofType.class, ExternalRoofType.class, RoofTypeMapper.class,
    User.class, UserDto.class, Wheel.class, WheelDto.class,
    Dictionary.class, DictionaryDto.class, Word.class, WordDto.class,
    ForeignWord.class, ForeignWordDto.class,
    Computer.class, ComputerDto.class,
    Cat.class, CatDto.class, Info.class, InfoDto.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class DottedErrorMessageTest {

    private static final String PROPERTY = "property";
    private static final String COLLECTION_ELEMENT = "Collection element";
    private static final String MAP_KEY = "Map key";
    private static final String MAP_VALUE = "Map value";

    @Test
    @WithClasses({
        UnmappableDeepNestingMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map " + PROPERTY +
                    " \".*Color house\\.roof\\.color\" to \".*house\\.roof\\.color\"\\. " +
                    "Consider to declare/implement a mapping method: \".*ColorDto map\\(.*Color value\\)\"\\.")
        }
    )
    public void testDeepNestedBeans() {
    }

    @Test
    @WithClasses({
        UnmappableDeepListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map " + COLLECTION_ELEMENT +
                    " \".*Wheel car\\.wheels\" to \".*car\\.wheels\"\\. " +
                    "Consider to declare/implement a mapping method: \".*WheelDto map\\(.*Wheel value\\)\"\\.")
        }
    )
    public void testIterables() {
    }

    @Test
    @WithClasses({
        UnmappableDeepMapKeyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map " + MAP_KEY +
                    " \".*Word dictionary\\.wordMap\\{:key\\}\" to \".*dictionary\\.wordMap\\{:key\\}\"\\. " +
                    "Consider to declare/implement a mapping method: \".*WordDto map\\(.*Word value\\)\"\\.")
        }
    )
    public void testMapKeys() {
    }

    @Test
    @WithClasses({
        UnmappableDeepMapValueMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map " + MAP_VALUE +
                    " \".*ForeignWord dictionary\\.wordMap\\{:value\\}\" " +
                    "to \".*dictionary\\.wordMap\\{:value\\}\"\\. " +
                    "Consider to declare/implement a mapping method: " +
                    "\".*ForeignWordDto map\\(.*ForeignWord value\\)\"\\.")
        }
    )
    public void testMapValues() {
    }

    @Test
    @WithClasses({
        UnmappableCollectionElementPropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map " + PROPERTY +
                    " \".*Info computers\\[\\].info\" to \".*computers\\[\\].info\"\\. " +
                    "Consider to declare/implement a mapping method: \".*InfoDto map\\(.*Info value\\)\"\\.")
        }
    )
    public void testCollectionElementProperty() {
    }

    @Test
    @WithClasses({
        UnmappableValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map " + PROPERTY +
                    " \".*Info catNameMap\\{:value\\}.info\" to \".*catNameMap\\{:value\\}.info\"\\. " +
                    "Consider to declare/implement a mapping method: \".*InfoDto map\\(.*Info value\\)\"\\.")
        }
    )
    public void testMapValueProperty() {
    }

    @Test
    @WithClasses({
        UnmappableEnumMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableEnumMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "The following constants from the property \".*RoofType house\\.roof\\.type\" enum " +
                    "have no corresponding constant in the \".*ExternalRoofType house\\.roof\\.type\" enum and must " +
                    "be be mapped via adding additional mappings: NORMAL\\."
            )
        }
    )
    public void testMapEnumProperty() {
    }
}
