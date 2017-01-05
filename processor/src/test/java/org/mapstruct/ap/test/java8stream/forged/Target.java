/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.java8stream.forged;

import java.util.Set;
import java.util.stream.Stream;

public class Target {

    private Set<Long> fooStream;
    private Stream<Long> fooStream2;
    private Stream<Long> fooStream3;

    public Set<Long> getFooStream() {
        return fooStream;
    }

    public void setFooStream(Set<Long> fooStream) {
        this.fooStream = fooStream;
    }

    public Stream<Long> getFooStream2() {
        return fooStream2;
    }

    public void setFooStream2(Stream<Long> fooStream2) {
        this.fooStream2 = fooStream2;
    }

    public Stream<Long> getFooStream3() {
        return fooStream3;
    }

    public void setFooStream3(Stream<Long> fooStream3) {
        this.fooStream3 = fooStream3;
    }
}
