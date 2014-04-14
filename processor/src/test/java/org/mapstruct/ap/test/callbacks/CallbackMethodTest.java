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
package org.mapstruct.ap.test.callbacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for callback methods that are defined using {@link BeforeMapping} / {@link AfterMapping}
 *
 * @author Andreas Gudian
 */
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( { ClassContainingCallbacks.class, Invocation.class, Source.class, Target.class, SourceTargetMapper.class,
    SourceTargetCollectionMapper.class, BaseMapper.class } )
@IssueKey("14")
public class CallbackMethodTest {

    @Before
    public void reset() {
        ClassContainingCallbacks.reset();
        BaseMapper.reset();
    }

    @Test
    public void callbackMethodsForBeanMappingCalled() {
        SourceTargetMapper.INSTANCE.sourceToTarget( createSource() );

        assertBeanMappingInvocations( ClassContainingCallbacks.getInvocations() );
        assertBeanMappingInvocations( BaseMapper.getInvocations() );
    }

    @Test
    public void callbackMethodsForBeanMappingWithResultParamCalled() {
        SourceTargetMapper.INSTANCE.sourceToTarget( createSource(), createEmptyTarget() );

        assertBeanMappingInvocations( ClassContainingCallbacks.getInvocations() );
        assertBeanMappingInvocations( BaseMapper.getInvocations() );
    }

    @Test
    public void callbackMethodsForIterableMappingCalled() {
        SourceTargetCollectionMapper.INSTANCE.sourceToTarget( Arrays.asList( createSource() ) );

        assertIterableMappingInvocations( ClassContainingCallbacks.getInvocations() );
        assertIterableMappingInvocations( BaseMapper.getInvocations() );
    }

    @Test
    public void callbackMethodsForIterableMappingWithResultParamCalled() {
        SourceTargetCollectionMapper.INSTANCE.sourceToTarget(
            Arrays.asList( createSource() ), new ArrayList<Target>() );

        assertIterableMappingInvocations( ClassContainingCallbacks.getInvocations() );
        assertIterableMappingInvocations( BaseMapper.getInvocations() );
    }

    @Test
    public void callbackMethodsForMapMappingCalled() {
        SourceTargetCollectionMapper.INSTANCE.sourceToTarget( toMap( "foo", createSource() ) );

        assertMapMappingInvocations( ClassContainingCallbacks.getInvocations() );
        assertMapMappingInvocations( BaseMapper.getInvocations() );
    }

    @Test
    public void callbackMethodsForMapMappingWithResultParamCalled() {
        SourceTargetCollectionMapper.INSTANCE.sourceToTarget(
            toMap( "foo", createSource() ),
            new HashMap<String, Target>() );

        assertMapMappingInvocations( ClassContainingCallbacks.getInvocations() );
        assertMapMappingInvocations( BaseMapper.getInvocations() );
    }

    private void assertBeanMappingInvocations(List<Invocation> invocations) {
        Source source = createSource();
        Target target = createResultTarget();
        Target emptyTarget = createEmptyTarget();

        Assertions.assertThat( invocations ).isEqualTo( beanMappingInvocationList( source, target, emptyTarget ) );
    }

    private void assertIterableMappingInvocations(List<Invocation> invocations) {
        Source source = createSource();
        List<Source> sourceList = Arrays.asList( source );

        Target target = createResultTarget();
        Target emptyTarget = createEmptyTarget();

        List<Target> emptyTargetList = Collections.emptyList();
        List<Target> targetList = Arrays.asList( target );

        assertCollectionMappingInvocations(
            invocations,
            source,
            sourceList,
            target,
            emptyTarget,
            emptyTargetList,
            targetList,
            List.class );
    }

    private void assertMapMappingInvocations(List<Invocation> invocations) {
        Source source = createSource();
        Map<String, Source> sourceMap = toMap( "foo", source );

        Target target = createResultTarget();
        Target emptyTarget = createEmptyTarget();

        Map<String, Target> emptyTargetMap = Collections.emptyMap();
        Map<String, Target> targetMap = toMap( "foo", target );

        assertCollectionMappingInvocations(
            invocations,
            source,
            sourceMap,
            target,
            emptyTarget,
            emptyTargetMap,
            targetMap,
            Map.class );
    }

    private <T> Map<String, T> toMap(String string, T value) {
        Map<String, T> result = new HashMap<String, T>();
        result.put( string, value );
        return result;
    }

    private void assertCollectionMappingInvocations(List<Invocation> invocations, Object source,
                                                    Object sourceCollection, Object target, Object emptyTarget,
                                                    Object emptyTargetCollection, Object targetCollection,
                                                    Class<?> targetCollectionClass) {
        List<Invocation> expected =
            allBeforeMappingMethods( sourceCollection, emptyTargetCollection, targetCollectionClass );
        expected.addAll( beanMappingInvocationList( source, target, emptyTarget ) );
        expected.addAll( allAfterMappingMethods( sourceCollection, targetCollection, targetCollectionClass ) );

        Assertions.assertThat( invocations ).isEqualTo( expected );
    }

    private List<Invocation> beanMappingInvocationList(Object source, Object target, Object emptyTarget) {
        List<Invocation> invocations = new ArrayList<Invocation>();

        invocations.addAll( allBeforeMappingMethods( source, emptyTarget, Target.class ) );
        invocations.addAll( allAfterMappingMethods( source, target, Target.class ) );

        return invocations;
    }

    private List<Invocation> allAfterMappingMethods(Object source, Object target, Class<?> targetClass) {
        return new ArrayList<Invocation>( Arrays.asList(
            new Invocation( "noArgsAfterMapping" ),
            new Invocation( "withSourceAfterMapping", source ),
            new Invocation( "withSourceAsObjectAfterMapping", source ),
            new Invocation( "withSourceAndTargetAfterMapping", source, target ),
            new Invocation( "withTargetAfterMapping", target ),
            new Invocation( "withTargetAsObjectAfterMapping", target ),
            new Invocation( "withTargetAndTargetTypeAfterMapping", target, targetClass ) ) );
    }

    private List<Invocation> allBeforeMappingMethods(Object source, Object emptyTarget, Class<?> targetClass) {
        return new ArrayList<Invocation>( Arrays.asList(
            new Invocation( "noArgsBeforeMapping" ),
            new Invocation( "withSourceBeforeMapping", source ),
            new Invocation( "withSourceAsObjectBeforeMapping", source ),
            new Invocation( "withSourceAndTargetTypeBeforeMapping", source, targetClass ),
            new Invocation( "withSourceAndTargetBeforeMapping", source, emptyTarget ),
            new Invocation( "withTargetBeforeMapping", emptyTarget ),
            new Invocation( "withTargetAsObjectBeforeMapping", emptyTarget ) ) );
    }

    private Source createSource() {
        Source source = new Source();
        source.setFoo( "foo" );
        return source;
    }

    private Target createEmptyTarget() {
        return new Target();
    }

    private Target createResultTarget() {
        Target target = createEmptyTarget();
        target.setFoo( "foo" );
        return target;
    }
}
