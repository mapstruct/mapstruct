/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._843;

/**
 *
 * @author Sjaak Derksen
 */
public class GitlabTag {

    private static int callCounter = 0;

    private Commit commit;

    public Commit getCommit() {
        callCounter++;
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public static int getCallCounter() {
        return callCounter;
    }

    public static void resetCallCounter() {
        callCounter = 0;
    }

}
