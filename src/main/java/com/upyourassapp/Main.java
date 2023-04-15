package main.java.com.upyourassapp;

import com.upyourassapp.io.MainIO;

/**
 * @author Kirill Popov
 */
public class Main {


    public static void main(String[] args) {
        MainIO mainIO = MainIO.getInstance();
        mainIO.init();
    }
}
