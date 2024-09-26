package it.unimore.studenti.hackathon;

import it.italiandudes.idl.common.InfoFlags;
import it.italiandudes.idl.common.Logger;
import it.unimore.studenti.hackathon.javafx.Client;

import java.io.IOException;

public class Hackathon {

    // Main Method
    public static void main(String[] args) {

        // Initializing the logger
        try {
            Logger.init();
        } catch (IOException e) {
            Logger.log("An error has occurred during Logger initialization, exit...");
            return;
        }

        // Configure the shutdown hooks
        Runtime.getRuntime().addShutdownHook(new Thread(Logger::close));

        // Start the client
        try {
            Client.start(args);
        } catch (NoClassDefFoundError e) {
            Logger.log("ERROR: TO RUN THIS JAR YOU NEED JAVA 8 WITH BUILT-IN JAVAFX!", new InfoFlags(true, true, true, true));
            Logger.log(e);
            System.exit(0);
        }
    }
}
