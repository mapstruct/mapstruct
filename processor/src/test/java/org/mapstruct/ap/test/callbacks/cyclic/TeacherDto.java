/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.cyclic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben Zegveld
 */
public class TeacherDto {
    private List<StudentDto> students = new ArrayList<>();

    void addStudent(StudentDto student) {
        students.add( student );
    }

    public List<StudentDto> getStudents() {
        return students;
    }
}
