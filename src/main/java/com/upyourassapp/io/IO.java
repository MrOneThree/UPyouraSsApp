package com.upyourassapp.io;

/**
 * @author Kirill Popov
 */
public interface IO {
    String getName();

    void init();

    String requestInput();

    void parseIO(String s);
}
