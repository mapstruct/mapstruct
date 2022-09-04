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
public class Teacher {
    private List<Student> students = new ArrayList<>();

    void addStudent(Student student) {
        students.add( student );
    }

    public List<Student> getStudents() {
        return students;
    }
}
