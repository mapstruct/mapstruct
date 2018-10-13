/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1608;

/**
 * @author Filip Hrisafov
 */
public class BookDto {

    private final String isbn;
    private final int issueYear;

    public BookDto(String isbn, int issueYear) {
        this.isbn = isbn;
        this.issueYear = issueYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getIssueYear() {
        return issueYear;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String isbn;

        private int issueYear;

        public String getIsbn() {
            return isbn;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public int getIssueYear() {
            return issueYear;
        }

        public Builder setIssueYear(int issueYear) {
            this.issueYear = issueYear;
            return this;
        }

        public BookDto build() {
            return new BookDto( isbn, issueYear );
        }
    }
}
