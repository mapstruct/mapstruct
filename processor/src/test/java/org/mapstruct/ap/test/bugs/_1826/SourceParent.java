/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1826;

public class SourceParent {

  private SourceChild sourceChild;
  private Boolean hasSourceChild = false;

  public SourceChild getSourceChild() {
    return sourceChild;
  }

  public void setSourceChild(SourceChild sourceChild) {
    this.sourceChild = sourceChild;
    this.hasSourceChild = true;
  }

  public Boolean hasSourceChild() {
    return hasSourceChild;
  }

}
