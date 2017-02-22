package main;

import java.util.ArrayList;

/**
 * Created by NW on 22.02.2017.
 */
public class Functions {

    /**
     * Converts Array of Strings to single String with newline separator
     * @param list
     * @return
     */
    public static String ArrayToString(String[] list){
        String s = "";
        for(String current: list){
            s += current + "\n";
        }
        return s;
    }
}
