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
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.Computer;
import org.mapstruct.ap.test.nestedbeans.unmappable.ComputerDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Dictionary;
import org.mapstruct.ap.test.nestedbeans.unmappable.DictionaryDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.ForeignWord;
import org.mapstruct.ap.test.nestedbeans.unmappable.ForeignWordDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Cat;
import org.mapstruct.ap.test.nestedbeans.unmappable.CatDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Info;
import org.mapstruct.ap.test.nestedbeans.unmappable.InfoDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableEnumMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableWarnCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableWarnDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableWarnDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableWarnDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableWarnDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableWarnValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.UserDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.User;
import org.mapstruct.ap.test.nestedbeans.unmappable.WheelDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Wheel;
import org.mapstruct.ap.test.nestedbeans.unmappable.Car;
import org.mapstruct.ap.test.nestedbeans.unmappable.CarDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.ExternalRoofType;
import org.mapstruct.ap.test.nestedbeans.unmappable.House;
import org.mapstruct.ap.test.nestedbeans.unmappable.HouseDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Color;
import org.mapstruct.ap.test.nestedbeans.unmappable.ColorDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Roof;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofType;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.Word;
import org.mapstruct.ap.test.nestedbeans.unmappable.WordDto;
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
    Cat.class, CatDto.class, Info.class, InfoDto.class,
    BaseCollectionElementPropertyMapper.class,
    BaseDeepListMapper.class,
    BaseDeepMapKeyMapper.class,
    BaseDeepMapValueMapper.class,
    BaseDeepNestingMapper.class,
    BaseValuePropertyMapper.class
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
            @Diagnostic(type = BaseDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unmapped target property: \"rgb\"\\. Mapping from " + PROPERTY +
                    " \".*Color house\\.roof\\.color\" to \".*ColorDto house\\.roof\\.color\"\\.")
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
            @Diagnostic(type = BaseDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unmapped target property: \"left\"\\. Mapping from " + COLLECTION_ELEMENT +
                    " \".*Wheel car\\.wheels\" to \".*WheelDto car\\.wheels\"\\.")
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
            @Diagnostic(type = BaseDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unmapped target property: \"pronunciation\"\\. Mapping from " + MAP_KEY +
                    " \".*Word dictionary\\.wordMap\\{:key\\}\" to \".*WordDto dictionary\\.wordMap\\{:key\\}\"\\.")
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
            @Diagnostic(type = BaseDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unmapped target property: \"pronunciation\"\\. Mapping from " + MAP_VALUE +
                    " \".*ForeignWord dictionary\\.wordMap\\{:value\\}\" " +
                    "to \".*ForeignWordDto dictionary\\.wordMap\\{:value\\}\"\\.")
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
            @Diagnostic(type = BaseCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unmapped target property: \"color\"\\. Mapping from " + PROPERTY +
                    " \".*Info computers\\[\\].info\" to \".*InfoDto computers\\[\\].info\"\\.")
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
            @Diagnostic(type = BaseValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                messageRegExp = "Unmapped target property: \"color\"\\. Mapping from " + PROPERTY +
                    " \".*Info catNameMap\\{:value\\}.info\" to \".*InfoDto catNameMap\\{:value\\}.info\"\\.")
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
                line = 38,
                messageRegExp = "The following constants from the property \".*RoofType house\\.roof\\.type\" enum " +
                    "have no corresponding constant in the \".*ExternalRoofType house\\.roof\\.type\" enum and must " +
                    "be be mapped via adding additional mappings: NORMAL\\."
            )
        }
    )
    public void testMapEnumProperty() {
    }

    @Test
    @WithClasses({
        UnmappableWarnDeepNestingMapper.class,
        UnmappableWarnDeepListMapper.class,
        UnmappableWarnDeepMapKeyMapper.class,
        UnmappableWarnDeepMapValueMapper.class,
        UnmappableWarnCollectionElementPropertyMapper.class,
        UnmappableWarnValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = BaseDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target property: \"rgb\"\\. Mapping from " + PROPERTY +
                    " \".*Color house\\.roof\\.color\" to \".*ColorDto house\\.roof\\.color\"\\."),
            @Diagnostic(type = BaseDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target property: \"left\"\\. Mapping from " + COLLECTION_ELEMENT +
                    " \".*Wheel car\\.wheels\" to \".*WheelDto car\\.wheels\"\\."),
            @Diagnostic(type = BaseDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target property: \"pronunciation\"\\. Mapping from " + MAP_KEY +
                    " \".*Word dictionary\\.wordMap\\{:key\\}\" to \".*WordDto dictionary\\.wordMap\\{:key\\}\"\\."),
            @Diagnostic(type = BaseDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target property: \"pronunciation\"\\. Mapping from " + MAP_VALUE +
                    " \".*ForeignWord dictionary\\.wordMap\\{:value\\}\" " +
                    "to \".*ForeignWordDto dictionary\\.wordMap\\{:value\\}\"\\."),
            @Diagnostic(type = BaseCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target property: \"color\"\\. Mapping from " + PROPERTY +
                    " \".*Info computers\\[\\].info\" to \".*InfoDto computers\\[\\].info\"\\."),
            @Diagnostic(type = BaseValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                messageRegExp = "Unmapped target property: \"color\"\\. Mapping from " + PROPERTY +
                    " \".*Info catNameMap\\{:value\\}.info\" to \".*InfoDto catNameMap\\{:value\\}.info\"\\.")
        }
    )
    public void testWarnUnmappedTargetProperties() {
    }

    @Test
    @WithClasses({
        UnmappableIgnoreDeepNestingMapper.class,
        UnmappableIgnoreDeepListMapper.class,
        UnmappableIgnoreDeepMapKeyMapper.class,
        UnmappableIgnoreDeepMapValueMapper.class,
        UnmappableIgnoreCollectionElementPropertyMapper.class,
        UnmappableIgnoreValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED
    )
    public void testIgnoreUnmappedTargetProperties() {
    }
}
