package com.upyourassapp;

import com.upyourassapp.io.MainIO;

/**
 * @author Kirill Popov
 */
public class Main {

    //Simple app entry point. Serves to initialize main IO page.

    public static void main(String[] args) {
        MainIO mainIO = MainIO.getInstance();
        mainIO.init();
    }
}
