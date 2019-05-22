/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1826;

public class SourceChild {

  private String content;
  private Boolean hasContent = false;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
    hasContent = true;
  }

  public Boolean hasContent() {
    return hasContent;
  }
}
