/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3673;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3673")
@WithClasses({
    Cat.class,
    Dog.class,
    Details.class,
    Animal.class
})
class Issue3673Test {

    @ProcessorTest
    @WithClasses(Issue3673ConstantMapper.class)
    void shouldCorrectlyMapNestedPropertyConstant() {

        Animal cat = Issue3673ConstantMapper.INSTANCE.map(
            new Cat( new Details( "cat" ) )
        );

        Animal dog = Issue3673ConstantMapper.INSTANCE.map(
            new Dog( new Details( "dog" ) )
        );

        assertThat( cat ).isNotNull();
        assertThat( cat.getDetails() ).isNotNull();
        assertThat( cat.getDetails().getName() ).isEqualTo( "cat" );
        assertThat( cat.getDetails().getType() ).isEqualTo( Animal.Type.CAT );

        assertThat( dog ).isNotNull();
        assertThat( dog.getDetails() ).isNotNull();
        assertThat( dog.getDetails().getName() ).isEqualTo( "dog" );
        assertThat( dog.getDetails().getType() ).isEqualTo( Animal.Type.DOG );
    }

    @ProcessorTest
    @WithClasses(Issue3673ExpressionMapper.class)
    void shouldCorrectlyMapNestedPropertyExpression() {

        Animal cat = Issue3673ExpressionMapper.INSTANCE.map(
            new Cat( new Details( "cat" ) )
        );

        Animal dog = Issue3673ExpressionMapper.INSTANCE.map(
            new Dog( new Details( "dog" ) )
        );

        assertThat( cat ).isNotNull();
        assertThat( cat.getDetails() ).isNotNull();
        assertThat( cat.getDetails().getName() ).isEqualTo( "cat" );
        assertThat( cat.getDetails().getType() ).isEqualTo( Animal.Type.CAT );

        assertThat( dog ).isNotNull();
        assertThat( dog.getDetails() ).isNotNull();
        assertThat( dog.getDetails().getName() ).isEqualTo( "dog" );
        assertThat( dog.getDetails().getType() ).isEqualTo( Animal.Type.DOG );
    }
}
