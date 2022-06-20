/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2624;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue2624Mapper.class
})
class Issue2624Test {

    @ProcessorTest
    void shouldCorrectlyMapNestedTargetFromMap() {
        Map<String, String> map = new HashMap<>();
        map.put( "id", "1234" );
        map.put( "name", "Tester" );
        map.put( "did", "4321" ); //Department Id
        map.put( "dname", "Test" ); // Department name

        Issue2624Mapper.Employee employee = Issue2624Mapper.INSTANCE.fromMap( map );

        assertThat( employee ).isNotNull();
        assertThat( employee.getId() ).isEqualTo( "1234" );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        Issue2624Mapper.Department department = employee.getDepartment();
        assertThat( department ).isNotNull();
        assertThat( department.getId() ).isEqualTo( "4321" );
        assertThat( department.getName() ).isEqualTo( "Test" );
    }
}
