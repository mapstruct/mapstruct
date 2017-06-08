package org.mapstruct.ap.test.parser;

import javax.annotation.Generated;

@Generated(value = "hello")
public class Test {

    public Bar getFoo(String param) {
        if ( param == null ) {
            return null;
        }

        Bar bar = new Bar();

        bar.setLong( (long) param.getBytes().length );

        return bar;
    }
}
