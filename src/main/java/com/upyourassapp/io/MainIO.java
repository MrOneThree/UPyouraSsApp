package com.upyourassapp.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Kirill Popov
 */
public class MainIO implements IO{
    private static MainIO mainIO;   //Singleton
    private final List<IO> ios;     //List of available IOs. Better be autowired, but spring is not available.
    private IO currentIO = null;    //Current IO tracker. If null -> opens main IO.
    private boolean running = false;    //Running state. If false -> close app.

    private MainIO() {  //When singleton, constructor must be private.
        ios = new ArrayList<>(
                List.of(
                        CustomerIO.getInstance()
                //Insert new IOs HERE
                )                        //sorry no @autowired in java core ¯\_(ツ)_/¯
        );
        running = true;
    }

    public static MainIO getInstance() {    //Singleton impl. Returns the existing instance or instantiates new.
        if (Objects.isNull(mainIO)) {
            mainIO = new MainIO();
        }
        return mainIO;
    }

    @Override
    public String getName() {
        return null;
    }
    @Override
    public void init() {
        System.out.println("  _    _ _____                              _____        _____                  \n" +
                " | |  | |  __ \\                            / ____|      / ____|                 \n" +
                " | |  | | |__) |   _  ___  _   _ _ __ __ _| (___  ___  | |     ___  _ __ _ __   \n" +
                " | |  | |  ___/ | | |/ _ \\| | | | '__/ _` |\\___ \\/ __| | |    / _ \\| '__| '_ \\  \n" +
                " | |__| | |   | |_| | (_) | |_| | | | (_| |____) \\__ \\ | |___| (_) | |  | |_) | \n" +
                "  \\____/|_|    \\__, |\\___/ \\__,_|_|  \\__,_|_____/|___/  \\_____\\___/|_|  | .__(_)\n" +
                "                __/ |                                                   | |     \n" +
                "               |___/                                                    |_|     ");
        System.out.println("HI! This is an automatic delivery service application presented to you by UPyouraSs Corp.!");
        System.out.println("To return to this menu type \"#\" at any time");
        System.out.println("To stop the application type \"Exit\"");
        System.out.println("All inputs are case insensitive");

        while (running) {

            if (Objects.isNull(currentIO)) {
                System.out.println("Here's the list of available bases: ");
                listBases();
                parseIO(requestInput());
            } else {
                currentIO.init();
                parseIO(currentIO.requestInput());
            }


        }
    }

    private void listBases() {
        System.out.println("/--------------------/");
        ios.forEach(io -> System.out.println(io.getName()));
        System.out.println("/--------------------/");
    }
    @Override
    public String requestInput() {
        System.out.println("Please, enter name of desired base and press enter.");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    @Override
    public void parseIO(String s) {
        if (s.equalsIgnoreCase("exit")) {
            running = false;
            return;
        }

        if (s.equalsIgnoreCase("#")) {
            resetIO();
            return;
        }

        if (!Objects.isNull(currentIO)) {
            currentIO.parseIO(s);
        } else {
            ios.stream().filter(io -> io.getName().equalsIgnoreCase(s)).findFirst()
                    .ifPresentOrElse(io -> currentIO = io,
                    () -> {
                        System.err.println("No such base found!");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    private void resetIO() {
        currentIO = null;
    }
}
