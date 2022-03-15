package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.lang.model.element.Element;

public class MetaAnnotationsUtil {
    private MetaAnnotationsUtil() {
        // Util class, do not instantiate.
    }

    public static <GEM, GEMS> Set<GEM> getGemsFromElement(Element element,
                                                          Function<Element, GEM> getSingular,
                                                          Function<Element, GEMS> getMultiple,
                                                          Function<GEMS, Collection<GEM>> getSingularsFromMultiple) {
        return getGemsFromElement( element, new ArrayList<>(), getSingular, getMultiple, getSingularsFromMultiple );
    }

    private static <GEM, GEMS> Set<GEM> getGemsFromElement(Element element,
                                                          List<String> handledStack,
                                                          Function<Element, GEM> getSingular,
                                                          Function<Element, GEMS> getMultiple,
                                                          Function<GEMS, Collection<GEM>> getSingularsFromMultiple) {
        handledStack.add( element.toString() );
        Set<GEM> additionalAnnotations = new LinkedHashSet<>();
        GEM annotationGem = getSingular.apply( element );
        if ( annotationGem != null ) {
            additionalAnnotations.add( annotationGem );
        }
        GEMS annotationsGem = getMultiple.apply( element );
        if ( annotationsGem != null ) {
            for ( GEM annotateWithGem : getSingularsFromMultiple.apply( annotationsGem ) ) {
                additionalAnnotations.add( annotateWithGem );
            }
        }
        additionalAnnotations.addAll(
                                 handleMetaAnnotations(
                                     element,
                                     handledStack,
                                     getSingular,
                                     getMultiple,
                                     getSingularsFromMultiple ) );
        return additionalAnnotations;
    }

    private static <GEM, GEMS> Set<GEM> handleMetaAnnotations(Element element,
                                                       List<String> handledStackInput,
                                                       Function<Element, GEM> getSingular,
                                                       Function<Element, GEMS> getMultiple,
                                                       Function<GEMS, Collection<GEM>> getSingularsFromMultiple) {
        List<String> handledStack = new ArrayList<>( handledStackInput );
        Set<GEM> additionalAnnotations = new LinkedHashSet<>();
        for ( javax.lang.model.element.AnnotationMirror mirror : element.getAnnotationMirrors() ) {
            Element asElement = mirror.getAnnotationType().asElement();
            if ( handledStack.contains( asElement.toString() ) ) {
                continue;
            }
            additionalAnnotations.addAll(
                getGemsFromElement(
                                         asElement,
                                         handledStack,
                                         getSingular,
                                         getMultiple,
                                         getSingularsFromMultiple ) );
        }
        return additionalAnnotations;
    }

}
