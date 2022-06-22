/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.mapstruct.ap.test.nestedbeans.recursive.RecursionMapper;
import org.mapstruct.ap.test.nestedbeans.recursive.TreeRecursionMapper;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

public class RecursionTest {

    @WithClasses({
        RecursionMapper.class
    })
    @ProcessorTest
    @IssueKey("1103")
    public void testRecursiveAutoMap() {
        RecursionMapper.RootDto rootDto = new RecursionMapper.RootDto(
            new RecursionMapper.ChildDto( "Sub Root", new RecursionMapper.ChildDto(
                "Sub child", null
            ) )
        );
        RecursionMapper.Root root = RecursionMapper.INSTANCE.mapRoot( rootDto );

        assertRootEquals( rootDto, root );
    }

    private void assertRootEquals(RecursionMapper.RootDto rootDto, RecursionMapper.Root root) {
        if (bothNull( root, rootDto )) {
            return;
        }
        assertNotNull( root );
        assertNotNull( rootDto );
        assertChildEquals( rootDto.getChild(), root.getChild() );
    }

    private void assertChildEquals(RecursionMapper.ChildDto childDto, RecursionMapper.Child child) {
        if ( bothNull( childDto, child ) ) {
            return;
        }
        assertNotNull( childDto );
        assertNotNull( child );
        assertEquals( childDto.getName(), child.getName() );
        assertChildEquals( childDto.getChild(), child.getChild() );
    }

    @WithClasses({
        TreeRecursionMapper.class
    })
    @ProcessorTest
    @IssueKey("1103")
    public void testRecursiveTreeAutoMap() {
        TreeRecursionMapper.RootDto rootDto = new TreeRecursionMapper.RootDto(
            Collections.singletonList( new TreeRecursionMapper.ChildDto(
                "Sub Root",
                Collections.singletonList( new TreeRecursionMapper.ChildDto(
                    "Sub child", null
                ) )
            ) )
        );
        TreeRecursionMapper.Root root = TreeRecursionMapper.INSTANCE.mapRoot( rootDto );

        assertTreeRootEquals(
            rootDto,
            root
        );
    }

    private void assertTreeRootEquals(TreeRecursionMapper.RootDto rootDto, TreeRecursionMapper.Root root) {
        if (bothNull( root, rootDto )) {
            return;
        }
        assertNotNull( root );
        assertNotNull( rootDto );
        assertChildrenEqual( rootDto.getChild(), root.getChild() );
    }

    private void assertChildrenEqual(List<TreeRecursionMapper.ChildDto> childrenDto,
                                     List<TreeRecursionMapper.Child> children) {
        if (bothNull( children, childrenDto )) {
            return;
        }
        assertNotNull( childrenDto );
        assertNotNull( children );
        assertEquals( children.size(), childrenDto.size() );
        Iterator<TreeRecursionMapper.Child> iterator = children.iterator();
        Iterator<TreeRecursionMapper.ChildDto> dtoIterator = childrenDto.iterator();
        while ( iterator.hasNext() ) {
            assertTreeTreeChildEquals( dtoIterator.next(), iterator.next() );
        }
    }

    private void assertTreeTreeChildEquals(TreeRecursionMapper.ChildDto childDto, TreeRecursionMapper.Child child) {
        assertNotNull( child );
        assertNotNull( childDto );
        assertEquals( child.getName(), childDto.getName() );
        assertChildrenEqual( childDto.getChild(), child.getChild() );
    }

    private boolean bothNull(Object o1, Object o2) {
        return o1 == null && o2 == null;
    }

}
