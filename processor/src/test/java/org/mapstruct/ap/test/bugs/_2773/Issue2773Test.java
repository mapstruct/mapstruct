package org.mapstruct.ap.test.bugs._2773;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2773")
@WithClasses({ChartEntry.class,Issue2773Mapper.class,Studio.class,TestAnnotation.class})
public class Issue2773Test {

    @ProcessorTest
    public void shouldContainMethodAnnotations() throws NoSuchMethodException {
        Issue2773Mapper issue2773Mapper = Issue2773Mapper.INSTANCE;
        Class<? extends Issue2773Mapper> mapperClass = issue2773Mapper.getClass();
        Method toStudio = mapperClass.getMethod("toStudio", ChartEntry.class, ChartEntry.class);
        assertThat(toStudio.getAnnotation(Deprecated.class)).isNotNull();
        assertThat(toStudio.getAnnotation(TestAnnotation.class)).isNotNull();
    }

    @ProcessorTest
    public void shouldNotContainMethodAnnotations() throws NoSuchMethodException {
        Issue2773Mapper issue2773Mapper = Issue2773Mapper.INSTANCE;
        Class<? extends Issue2773Mapper> mapperClass = issue2773Mapper.getClass();
        Method toStudio = mapperClass.getMethod("toStudio", ChartEntry.class, ChartEntry.class);
        Method map = mapperClass.getMethod("map", ChartEntry.class);
        assertThat(toStudio.getAnnotation(Mapping.class)).isNull();
        assertThat(map.getAnnotation(Mappings.class)).isNull();
    }
}
