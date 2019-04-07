/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1457;

public class TargetBook {

    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;

    private boolean afterMappingWithoutAuthorName;
    private String afterMappingWithOnlyFirstName;
    private String afterMappingWithOnlyLastName;
    private boolean afterMappingWithDifferentVariableName;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public boolean isAfterMappingWithoutAuthorName() {
        return afterMappingWithoutAuthorName;
    }

    public void setAfterMappingWithoutAuthorName(boolean afterMappingWithoutAuthorName) {
        this.afterMappingWithoutAuthorName = afterMappingWithoutAuthorName;
    }

    public String getAfterMappingWithOnlyFirstName() {
        return afterMappingWithOnlyFirstName;
    }

    public void setAfterMappingWithOnlyFirstName(String afterMappingWithOnlyFirstName) {
        this.afterMappingWithOnlyFirstName = afterMappingWithOnlyFirstName;
    }

    public String getAfterMappingWithOnlyLastName() {
        return afterMappingWithOnlyLastName;
    }

    public void setAfterMappingWithOnlyLastName(String afterMappingWithOnlyLastName) {
        this.afterMappingWithOnlyLastName = afterMappingWithOnlyLastName;
    }

    public boolean isAfterMappingWithDifferentVariableName() {
        return afterMappingWithDifferentVariableName;
    }

    public void setAfterMappingWithDifferentVariableName(boolean afterMappingWithDifferentVariableName) {
        this.afterMappingWithDifferentVariableName = afterMappingWithDifferentVariableName;
    }
}
