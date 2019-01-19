package org.mapstruct.ap.test.bugs.nesting;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._1665.Issue1665Mapper;
import org.mapstruct.ap.test.bugs._1665.Source;
import org.mapstruct.ap.test.bugs._1665.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("-")
@WithClasses({
    User.class,
    UserDTO.class,
    UserMapper.class,
    ContactDataDTO.class
})
public class Tester {

    @Test
    public void testMe() {
        UserMapper userMapper = UserMapper.INSTANCE;

    }
}
