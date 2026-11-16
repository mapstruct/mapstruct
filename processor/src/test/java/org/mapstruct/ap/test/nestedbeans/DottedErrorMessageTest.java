/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import org.mapstruct.ap.test.nestedbeans.unmappable.BaseCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.Car;
import org.mapstruct.ap.test.nestedbeans.unmappable.CarDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Cat;
import org.mapstruct.ap.test.nestedbeans.unmappable.CatDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Color;
import org.mapstruct.ap.test.nestedbeans.unmappable.ColorDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Computer;
import org.mapstruct.ap.test.nestedbeans.unmappable.ComputerDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Dictionary;
import org.mapstruct.ap.test.nestedbeans.unmappable.DictionaryDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.ExternalRoofType;
import org.mapstruct.ap.test.nestedbeans.unmappable.ForeignWord;
import org.mapstruct.ap.test.nestedbeans.unmappable.ForeignWordDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.House;
import org.mapstruct.ap.test.nestedbeans.unmappable.HouseDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Info;
import org.mapstruct.ap.test.nestedbeans.unmappable.InfoDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Roof;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofType;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.User;
import org.mapstruct.ap.test.nestedbeans.unmappable.UserDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Wheel;
import org.mapstruct.ap.test.nestedbeans.unmappable.WheelDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.Word;
import org.mapstruct.ap.test.nestedbeans.unmappable.WordDto;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceEnumMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableSourceValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableTargetCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableTargetDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableTargetDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableTargetDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableTargetDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.erroneous.UnmappableTargetValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.ignore.UnmappableIgnoreValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableSourceWarnCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableSourceWarnDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableSourceWarnDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableSourceWarnDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableSourceWarnDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableSourceWarnValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableTargetWarnCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableTargetWarnDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableTargetWarnDeepMapKeyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableTargetWarnDeepMapValueMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableTargetWarnDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.warn.UnmappableTargetWarnValuePropertyMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
public class DottedErrorMessageTest {

    private static final String PROPERTY = "property";
    private static final String COLLECTION_ELEMENT = "Collection element";
    private static final String MAP_KEY = "Map key";
    private static final String MAP_VALUE = "Map value";

    @ProcessorTest
    @WithClasses({
        UnmappableTargetDeepNestingMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 14,
                message = "Unmapped target property: \"rgb\". Mapping from " + PROPERTY +
                    " \"Color house.roof.color\" to \"ColorDto house.roof.color\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepNestingMapper'.")
        }
    )
    public void testTargetDeepNestedBeans() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableTargetDeepListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 14,
                message = "Unmapped target property: \"left\". Mapping from " + COLLECTION_ELEMENT +
                    " \"Wheel car.wheels\" to \"WheelDto car.wheels\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepListMapper'.")
        }
    )
    public void testTargetIterables() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableTargetDeepMapKeyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 14,
                message = "Unmapped target property: \"pronunciation\". Mapping from " + MAP_KEY +
                    " \"Word dictionary.wordMap{:key}\" to \"WordDto dictionary.wordMap{:key}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapKeyMapper'.")
        }
    )
    public void testTargetMapKeys() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableTargetDeepMapValueMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 14,
                message = "Unmapped target property: \"pronunciation\". Mapping from " + MAP_VALUE +
                    " \"ForeignWord dictionary.wordMap{:value}\" to \"ForeignWordDto dictionary.wordMap{:value}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapValueMapper'.")
        }
    )
    public void testTargetMapValues() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableTargetCollectionElementPropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 14,
                message = "Unmapped target property: \"color\". Mapping from " + PROPERTY +
                    " \"Info computers[].info\" to \"InfoDto computers[].info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseCollectionElementPropertyMapper'.")
        }
    )
    public void testTargetCollectionElementProperty() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableTargetValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 14,
                message = "Unmapped target property: \"color\". Mapping from " + PROPERTY +
                    " \"Info catNameMap{:value}.info\" to \"InfoDto catNameMap{:value}.info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseValuePropertyMapper'.")
        }
    )
    public void testTargetMapValueProperty() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceDeepNestingMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"cmyk\". Mapping from " + PROPERTY +
                    " \"Color house.roof.color\" to \"ColorDto house.roof.color\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepNestingMapper'.")
        }
    )
    public void testSourceDeepNestedBeans() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceDeepListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"right\". Mapping from " + COLLECTION_ELEMENT +
                    " \"Wheel car.wheels\" to \"WheelDto car.wheels\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepListMapper'.")
        }
    )
    public void testSourceIterables() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceDeepMapKeyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"meaning\". Mapping from " + MAP_KEY +
                    " \"Word dictionary.wordMap{:key}\" to \"WordDto dictionary.wordMap{:key}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapKeyMapper'.")
        }
    )
    public void testSourceMapKeys() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceDeepMapValueMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"meaning\". Mapping from " + MAP_VALUE +
                    " \"ForeignWord dictionary.wordMap{:value}\" to \"ForeignWordDto dictionary.wordMap{:value}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapValueMapper'.")
        }
    )
    public void testSourceMapValues() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceCollectionElementPropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"size\". Mapping from " + PROPERTY +
                    " \"Info computers[].info\" to \"InfoDto computers[].info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseCollectionElementPropertyMapper'.")
        }
    )
    public void testSourceCollectionElementProperty() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"size\". Mapping from " + PROPERTY +
                    " \"Info catNameMap{:value}.info\" to \"InfoDto catNameMap{:value}.info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseValuePropertyMapper'.")
        }
    )
    public void testSourceMapValueProperty() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableSourceEnumMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceEnumMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                message =
                    "The following constants from the property \"RoofType house.roof.type\" enum " +
                        "have no corresponding constant in the \"ExternalRoofType house.roof.type\" enum and must " +
                        "be be mapped via adding additional mappings: NORMAL."
            )
        }
    )
    public void testSourceMapEnumProperty() {
    }

    @ProcessorTest
    @WithClasses({
        UnmappableTargetWarnDeepNestingMapper.class,
        UnmappableTargetWarnDeepListMapper.class,
        UnmappableTargetWarnDeepMapKeyMapper.class,
        UnmappableTargetWarnDeepMapValueMapper.class,
        UnmappableTargetWarnCollectionElementPropertyMapper.class,
        UnmappableTargetWarnValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = UnmappableTargetWarnDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"rgb\". Mapping from " + PROPERTY +
                    " \"Color house.roof.color\" to \"ColorDto house.roof.color\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepNestingMapper'."),
            @Diagnostic(type = UnmappableTargetWarnDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"left\". Mapping from " + COLLECTION_ELEMENT +
                    " \"Wheel car.wheels\" to \"WheelDto car.wheels\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepListMapper'."),
            @Diagnostic(type = UnmappableTargetWarnDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"pronunciation\". Mapping from " + MAP_KEY +
                    " \"Word dictionary.wordMap{:key}\" to \"WordDto dictionary.wordMap{:key}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapKeyMapper'."),
            @Diagnostic(type = UnmappableTargetWarnDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"pronunciation\". Mapping from " + MAP_VALUE +
                    " \"ForeignWord dictionary.wordMap{:value}\" to \"ForeignWordDto dictionary.wordMap{:value}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapValueMapper'."),
            @Diagnostic(type = UnmappableTargetWarnCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"color\". Mapping from " + PROPERTY +
                    " \"Info computers[].info\" to \"InfoDto computers[].info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseCollectionElementPropertyMapper'."),
            @Diagnostic(type = UnmappableTargetWarnValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"color\". Mapping from " + PROPERTY +
                    " \"Info catNameMap{:value}.info\" to \"InfoDto catNameMap{:value}.info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseValuePropertyMapper'.")
        }
    )
    public void testWarnUnmappedTargetProperties() {
    }

    @IssueKey( "2788" )
    @ProcessorTest
    @WithClasses({
        UnmappableSourceWarnDeepNestingMapper.class,
        UnmappableSourceWarnDeepListMapper.class,
        UnmappableSourceWarnDeepMapKeyMapper.class,
        UnmappableSourceWarnDeepMapValueMapper.class,
        UnmappableSourceWarnCollectionElementPropertyMapper.class,
        UnmappableSourceWarnValuePropertyMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = UnmappableSourceWarnDeepNestingMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped source property: \"cmyk\". Mapping from " + PROPERTY +
                    " \"Color house.roof.color\" to \"ColorDto house.roof.color\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepNestingMapper'."),
            @Diagnostic(type = UnmappableSourceWarnDeepListMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped source property: \"right\". Mapping from " + COLLECTION_ELEMENT +
                    " \"Wheel car.wheels\" to \"WheelDto car.wheels\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepListMapper'."),
            @Diagnostic(type = UnmappableSourceWarnDeepMapKeyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped source property: \"meaning\". Mapping from " + MAP_KEY +
                    " \"Word dictionary.wordMap{:key}\" to \"WordDto dictionary.wordMap{:key}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapKeyMapper'."),
            @Diagnostic(type = UnmappableSourceWarnDeepMapValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped source property: \"meaning\". Mapping from " + MAP_VALUE +
                    " \"ForeignWord dictionary.wordMap{:value}\" to \"ForeignWordDto dictionary.wordMap{:value}\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseDeepMapValueMapper'."),
            @Diagnostic(type = UnmappableSourceWarnCollectionElementPropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped source property: \"size\". Mapping from " + PROPERTY +
                    " \"Info computers[].info\" to \"InfoDto computers[].info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseCollectionElementPropertyMapper'."),
            @Diagnostic(type = UnmappableSourceWarnValuePropertyMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 16,
                message = "Unmapped source property: \"size\". Mapping from " + PROPERTY +
                    " \"Info catNameMap{:value}.info\" to \"InfoDto catNameMap{:value}.info\"." +
                    " Occured at 'UserDto userToUserDto(User user)' in 'BaseValuePropertyMapper'.")
        }
    )
    public void testWarnUnmappedSourceProperties() {
    }

    @ProcessorTest
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
