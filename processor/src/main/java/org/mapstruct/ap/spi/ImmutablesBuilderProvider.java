/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

import org.mapstruct.util.Experimental;

/**
 * Builder provider for Immutables. A custom provider is needed because Immutables creates an implementation of an
 * interface and that implementation has the builder. This implementation would try to find the type created by
 * Immutables and would look for the builder in it. Only types annotated with the
 * {@code org.immutables.value.Value.Immutable} are considered for this discovery.
 *
 * @author Filip Hrisafov
 */
@Experimental("The Immutables builder provider might change in a subsequent release")
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );
    private static final String IMMUTABLE_FQN = "org.immutables.value.Value.Immutable";
    private static final String VALUE_ENCLOSING_FQN = "org.immutables.value.Value.Enclosing";
    private static final String VALUE_STYLE_FQN = "org.immutables.value.Value.Style";

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher( name ).matches() ) {
            return null;
        }
        TypeElement immutableAnnotation = elementUtils.getTypeElement( IMMUTABLE_FQN );
        if ( immutableAnnotation != null ) {
            BuilderInfo info = findBuilderInfoForImmutables( typeElement, immutableAnnotation );
            if ( info != null ) {
                return info;
            }
        }

        return super.findBuilderInfo( typeElement );
    }

    /**
     * Finds the builder info for the given type or returns null if not found.
     *
     * @param targetTypeElement   a type which may require a builder
     * @param immutableAnnotation type of the immutables annotation we're looking for
     * @return BuilderInfo or null if none found
     * @throws TypeHierarchyErroneousException if unable to process in this round
     */
    protected BuilderInfo findBuilderInfoForImmutables(TypeElement targetTypeElement,
                                                       TypeElement immutableAnnotation) {

        // we can avoid any reflection/type mirror inspection of the annotations
        // if the type we're dealing with now has a builder method on it.
        BuilderInfo fromType = super.findBuilderInfo( targetTypeElement );
        if ( fromType != null ) {
            return fromType;
        }

        // if there's no build method on the type, then look for the immutable annotation
        // since it may be accompanied by a Value.Style which provides info on the
        // name of the generated builder
        return findTypeWithImmutableAnnotation(
            targetTypeElement,
            immutableAnnotation.asType()
        ).map( typeElement -> {
            TypeElement immutableElement = asImmutableElement( typeElement );
            if ( immutableElement != null ) {
                return super.findBuilderInfo( immutableElement );
            }
            else {
                // Immutables processor has not run yet. Trigger a postpone to the next round for MapStruct
                throw new TypeHierarchyErroneousException( typeElement );
            }

        } )
            .orElse( null );
    }

    /**
     * This method looks for the <code>Value.Immutable</code> on the targetTypeElement
     * in the following order:
     * <p>
     * 1) directly on the element itself
     * 2) on an interface in the same package that the element implements
     * 3) on the superclass for the element
     * <p>
     *
     * We're looking for the immutable annotation since there could be additional annotations there
     * which affect the name of the generated immutable builder.
     *
     * @param targetTypeElement             element to analyze for the immutables annotation
     * @param immutableAnnotationTypeMirror type of the annotation we're looking for
     * @return first found element with the type or empty
     */
    protected Optional<TypeElement> findTypeWithImmutableAnnotation(TypeElement targetTypeElement,
                                                                    TypeMirror immutableAnnotationTypeMirror) {
        Predicate<Element> hasImmutableAnnotation = element ->
            elementUtils.getAllAnnotationMirrors( element )
                .stream()
                .anyMatch( am -> typeUtils.isSameType( am.getAnnotationType(), immutableAnnotationTypeMirror ) );


        // 1. If the TypeElement has the immutable annotation
        //   then use the targetTypeElement to find the builder
        //
        if ( hasImmutableAnnotation.test( targetTypeElement ) ) {
            return Optional.of( targetTypeElement );
        }

        String targetPackage = findPackage( targetTypeElement );
        return Stream.concat(
            // 2. we'll check interfaces second
            targetTypeElement.getInterfaces().stream(),
            // 3. if not found on an interface, check the super class
            Stream.of( targetTypeElement.getSuperclass() )
        )
            .filter( intf -> intf.getKind() == TypeKind.DECLARED )
            .map( DeclaredType.class::cast )
            .map( DeclaredType::asElement )
            .map( TypeElement.class::cast )
            .filter( intf -> targetPackage.equals( findPackage( intf ) ) )
            .filter( hasImmutableAnnotation )
            .findFirst();
    }

    /**
     * @param typeElement element that has the Value.Immutable annotation
     * @return type that should have the builder or null if none found
     */
    protected TypeElement asImmutableElement(TypeElement typeElement) {
        // the java package that the generated builder is in
        String packageQualifier;
        // optional enclosing qualifier if the generated builder is an inner class
        // the value.enclosing annotation customizes this qualifier
        String enclosingQualifier = "";
        // name of the builder, defaults to Immutable + non-abstract simple type name
        // the style annotation customizes the builder
        String builderName;

        AnnotationMirror style = null;

        Element enclosingElement = typeElement.getEnclosingElement();
        while ( enclosingElement.getKind() != ElementKind.PACKAGE ) {
            // look for the first enclosing element with Value.Enclosing
            if ( hasValueEnclosingAnnotation( enclosingElement ) && enclosingQualifier.isEmpty() ) {
                style = findStyle( enclosingElement );
                if ( style != null ) {
                    enclosingQualifier = enclosingQualifierFromStyle( style, enclosingElement );
                }
                else {
                    enclosingQualifier = "Immutable" + enclosingElement.getSimpleName();
                }
            }
            enclosingElement = enclosingElement.getEnclosingElement();
        }
        packageQualifier = ( (PackageElement) enclosingElement ).getQualifiedName().toString();

        builderName = builderFromStyle( style, typeElement, !enclosingQualifier.isEmpty() );

        // check for @Value.Enclosing
        // <builderQualifiedName> ::= <package> <opt_enclosingName> <builderName>
        String bqn = Stream.of( packageQualifier, enclosingQualifier, builderName )
            .filter( segment -> !segment.isEmpty() )
            .collect( Collectors.joining( "." ) );

        return elementUtils.getTypeElement( bqn );
    }

    protected String enclosingQualifierFromStyle(AnnotationMirror style, Element element) {
        // Value.Style influences the qualifier name through the typeAbstract, typeImmutable, and typeImmutableEnclosing
        return immutableNameFromStylePattern(
            nameWithoutAbstractPrefix( style, element ),
            getSingleAnnotationValue( "typeImmutable", style )
                .orElseGet( () -> getSingleAnnotationValue( "typeImmutableEnclosing", style )
                    .orElse( "Immutable*" ) )
        );
    }

    protected String builderFromStyle(AnnotationMirror style, TypeElement element, boolean valueEnclosingFound) {
        assert element != null;

        // if we're given a style, then use it. If not, then
        // keep walking up until we find one or run out of enclosing elements
        // If we don't find a style, then the naming behavior is driven
        // by defaults as documented by the immutables annotations
        AnnotationMirror resolvedStyle = Optional.ofNullable( style )
            .orElseGet( () -> {
                Element currentElement = element;
                AnnotationMirror found = null;
                while ( currentElement != null && found == null ) {
                    found = findStyle( currentElement );
                    currentElement = currentElement.getEnclosingElement();
                }
                return found;
            } );

        if ( resolvedStyle == null && !valueEnclosingFound ) {
            // no @Value.Style found, use the default behavior from immutables
            // no @Value.Enclosing
            return "Immutable" + element.getSimpleName();
        }

        if ( resolvedStyle == null ) {
            // no @Value.Style found, but there was a @Value.Enclosing, use the default behavior
            return element.getSimpleName().toString();
        }

        // style is present, see what it has to say about the names
        return immutableNameFromStylePattern(
            // trim the abstract portion from the name (defaults to "Abstract*")
            nameWithoutAbstractPrefix( resolvedStyle, element ),
            // use the value from typeImmutable
            getSingleAnnotationValue( "typeImmutable", resolvedStyle )
                // Note: typeImmutable is defined as having a default value so we shouldn't
                //       hit this orElse. Leaving this instead of throwing since
                //       it's a reasonable default (and currently matches their docs)
                .orElse( "Immutable*" )
        );
    }

    protected String nameWithoutAbstractPrefix(AnnotationMirror style, Element element) {
        final String simpleNameOfElement = element.getSimpleName().toString();
        return getTypeAbstractValues( style )
            .stream()
            .filter( p -> simpleNameOfElement.startsWith( p.substring( 0, p.length() - 1 ) ) )
            .map( p -> simpleNameOfElement.substring( p.length() - 1 ) )
            .findFirst()
            .orElseGet( () -> element.getSimpleName().toString() );
    }

    protected Optional<String> getSingleAnnotationValue(String annotationKey, AnnotationMirror style) {
        return elementUtils.getElementValuesWithDefaults( style )
            .entrySet()
            .stream()
            .filter( entry -> annotationKey.equals( entry.getKey().getSimpleName().toString() ) )
            .map( Map.Entry::getValue )
            .map( value -> value.accept( new SimpleAnnotationValueVisitor8<String, Void>() {
                @Override
                public String visitString(String s, Void unused) {
                    return s;
                }
            }, null ) )
            .findFirst();
    }

    protected List<String> getTypeAbstractValues(AnnotationMirror styleOrNull) {

        // this is the pattern if there is no style or if typeAbstract value not found
        Supplier<List<String>> noStyleOrMissingDefault = () -> Collections.singletonList( "Abstract*" );

        return Optional.ofNullable( styleOrNull )
            .map( style -> elementUtils.getElementValuesWithDefaults( style )
                .entrySet()
                .stream()
                .filter( entry -> "typeAbstract".equals( entry.getKey().getSimpleName().toString() ) )
                .map( Map.Entry::getValue )
                .map( value -> value.accept( new SimpleAnnotationValueVisitor8<List<String>, Void>() {
                    @Override
                    public List<String> visitArray(List<? extends AnnotationValue> values, Void unused) {
                        return values.stream()
                            .map( val -> val.accept( new SimpleAnnotationValueVisitor8<String, Void>() {
                                public String visitString(String s, Void param) {
                                    return s;
                                }
                            }, null ) )
                            .collect( Collectors.toList() );
                    }
                }, null ) ).findFirst().orElseGet( noStyleOrMissingDefault ) )
            .orElseGet( noStyleOrMissingDefault );
    }

    protected boolean hasValueEnclosingAnnotation(Element enclosingElement) {
        TypeElement typeElement = elementUtils.getTypeElement( VALUE_ENCLOSING_FQN );

        return Optional.ofNullable( typeElement )
            .map( Element::asType )
            .map( mirror ->
                elementUtils.getAllAnnotationMirrors( enclosingElement )
                    .stream()
                    .anyMatch( am -> typeUtils.isSameType( am.getAnnotationType(), mirror ) ) )
            .orElse( Boolean.FALSE );
    }

    protected AnnotationMirror findStyle(Element element) {
        TypeElement styleTypeElement = elementUtils.getTypeElement( VALUE_STYLE_FQN );
        if ( styleTypeElement == null ) {
            return null;
        }
        TypeMirror styleAnnotation = styleTypeElement.asType();
        return elementUtils.getAllAnnotationMirrors( element )
            .stream()
            .filter( am -> typeUtils.isSameType( am.getAnnotationType(), styleAnnotation ) )
            .findFirst()
            .orElse( null );
    }

    protected String immutableNameFromStylePattern(String simpleNameOfElement, String typeImmutablePattern) {
        String fixedPattern = typeImmutablePattern.substring( 0, typeImmutablePattern.length() - 1 );
        return fixedPattern + simpleNameOfElement;
    }

    protected String findPackage(Element element) {
        Element current = element;
        while ( current.getKind() != ElementKind.PACKAGE ) {
            current = current.getEnclosingElement();
        }
        return current.getSimpleName().toString();
    }

}
