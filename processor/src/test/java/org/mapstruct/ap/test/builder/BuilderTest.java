package org.mapstruct.ap.test.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@WithClasses({ MutableSource.class, MutableParent.class, ImmutableTarget.class, ImmutableParent.class, BuilderMapper.class  })
@RunWith(AnnotationProcessorTestRunner.class)
public class BuilderTest {

    @Test
    public void testSimpleBuilder() {
        final BuilderMapper mapper = Mappers.getMapper(BuilderMapper.class);
        final MutableSource source = new MutableSource();
        source.setAge(3);
        source.setName("Bob");
        final ImmutableTarget targetObject = mapper.toImmutable(source);
        assertEquals(3, targetObject.getAge());
        assertEquals("Bob", targetObject.getName());
        assertNull(targetObject.getPops());
    }

    @Test
    @WithClasses({BuilderParentMapper.class})
    public void testParentChildBuilder() {
        MutableSource child = new MutableSource();
        child.setAge(3);
        child.setName("Bob");

        final BuilderParentMapper mapper = Mappers.getMapper(BuilderParentMapper.class);
        final MutableParent parent = new MutableParent();
        parent.setCount(33);
        final ArrayList<MutableSource> children = new ArrayList<MutableSource>();
        children.add(child);
        parent.setChildren(children);
        final ImmutableParent targetObject = mapper.toImmutable(parent);
        assertEquals(1, targetObject.getChildren().size());
    }

    @Test
    @WithClasses({ImmutableFlattened.class, FlattenedMapper.class})
    public void testNestedPropertyBuilder() {
        MutableSource child = new MutableSource();
        child.setAge(3);
        child.setName("Bob");

        MutableSource child2 = new MutableSource();
        child2.setAge(1);
        child2.setName("Charlie");

        final FlattenedMapper mapper = Mappers.getMapper(FlattenedMapper.class);
        final MutableParent parent = new MutableParent();
        parent.setCount(33);
        final ArrayList<MutableSource> children = new ArrayList<MutableSource>();
        children.add(child2);
        children.add(child);
        parent.setChildren(children);
        final ImmutableFlattened targetObject = mapper.toFlattened(parent);
        assertEquals(33, targetObject.getFirst().getAge());
        assertNull(targetObject.getSecond());
    }
}
