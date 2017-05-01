package org.mapstruct.ap.test;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.testng.annotations.Test;

public class NetworkTest {

    @Test
    public void testReadUrl() throws Exception {
        URLConnection connection = new URL("http://heise.de").openConnection();
        String text = new Scanner(connection.getInputStream()).useDelimiter("\\Z").next();
        System.out.println(text);
    }
}
