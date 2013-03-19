package org.runio.testutil;

import java.util.Scanner;

public class ResourceUtils {

    private ResourceUtils() {
    }

    public static String readResourceToString(String name) {
        Scanner s = new Scanner(new ResourceUtils().getClass().getResourceAsStream(name)).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
