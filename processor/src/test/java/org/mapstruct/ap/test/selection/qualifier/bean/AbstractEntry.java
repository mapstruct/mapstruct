/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.selection.qualifier.bean;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public abstract class AbstractEntry {

    private String title;

    private List<String> keyWords;

    private Map<String, List<String>> facts;

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords( List<String> keyWords ) {
        this.keyWords = keyWords;
    }

    public Map<String, List<String> > getFacts() {
        return facts;
    }

    public void setFacts( Map<String, List<String> > facts ) {
        this.facts = facts;
    }


}
