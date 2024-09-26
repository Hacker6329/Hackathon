package it.unimore.studenti.hackathon.utils;

import it.unimore.studenti.hackathon.Hackathon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public final class Defs {

    // Random
    public static final Random RANDOM = new Random();

    // Charset
    public static final String DEFAULT_CHARSET = "UTF-8";

    // CSV File Path
    public static final String CSV_FILE = "dataset/data.csv";

    // Name Pool
    public static final ArrayList<String> NAMEPOOL = new ArrayList<>(Arrays.asList(
            "Mario Rossi", "Luigi Bianchi", "Giovanni Verdi", "Francesca Neri", "Paolo Gialli",
            "Elena Blu", "Marco Viola", "Sara Rosa", "Luca Marrone", "Giulia Azzurri",
            "Alessandro Grigio", "Martina Gialli", "Simone Verde", "Chiara Viola", "Davide Nero",
            "Federica Rosa", "Giorgio Blu", "Valentina Marrone", "Riccardo Azzurri", "Laura Rossi",
            "Andrea Bianchi", "Silvia Verdi", "Matteo Neri", "Claudia Gialli", "Stefano Blu",
            "Roberta Viola", "Fabio Rosa", "Elisabetta Marrone", "Tommaso Azzurri", "Cristina Rossi",
            "Emanuele Bianchi", "Ilaria Verdi", "Francesco Neri", "Serena Gialli", "Lorenzo Blu",
            "Monica Viola", "Gabriele Rosa", "Daniela Marrone", "Pietro Azzurri", "Angela Rossi",
            "Nicola Bianchi", "Barbara Verdi", "Antonio Neri", "Marina Gialli", "Roberto Blu",
            "Patrizia Viola", "Alberto Rosa", "Giovanna Marrone", "Michele Azzurri", "Sabrina Rossi",
            "Vincenzo Bianchi", "Eleonora Verdi", "Massimo Neri", "Beatrice Gialli", "Giuseppe Blu",
            "Caterina Viola", "Enrico Rosa", "Gabriella Marrone", "Salvatore Azzurri", "Lucia Rossi",
            "Carlo Bianchi", "Annalisa Verdi", "Raffaele Neri", "Mariangela Gialli", "Giulio Blu",
            "Antonella Viola", "Dario Rosa", "Franca Marrone", "Gianluca Azzurri", "Rita Rossi",
            "Sergio Bianchi", "Margherita Verdi", "Vittorio Neri", "Teresa Gialli", "Edoardo Blu",
            "Lidia Viola", "Maurizio Rosa", "Giuseppina Marrone", "Alessia Azzurri", "Gianfranco Rossi",
            "Renato Bianchi", "Giuseppina Verdi", "Gianni Neri", "Rosa Gialli", "Giancarlo Blu",
            "Marianna Viola", "Giovanni Rosa", "Cinzia Marrone", "Giorgia Azzurri", "Massimiliano Rossi",
            "Giuliana Bianchi", "Eugenio Verdi", "Raffaella Neri", "Marta Gialli", "Gianmarco Blu",
            "Antonino Viola", "Giuliano Rosa", "Carmela Marrone", "Gianpaolo Azzurri", "Anna Neri",
            "Paolo Gialli"
    ));

    // Name Pool Extractor
    @Nullable
    public static String getRandomName() {
        if (NAMEPOOL.isEmpty()) return null;
        int pos = RANDOM.nextInt(NAMEPOOL.size());
        String name = NAMEPOOL.get(pos);
        NAMEPOOL.remove(pos);
        return name;
    }

    // Jar App Position
    public static final String JAR_POSITION;
    static {
        try {
            JAR_POSITION = new File(Hackathon.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Resources Location
    public static final class Resources {

        //Resource Getters
        public static URL get(@NotNull final String resourceConst) {
            return Objects.requireNonNull(Hackathon.class.getResource(resourceConst));
        }
        public static InputStream getAsStream(@NotNull final String resourceConst) {
            return Objects.requireNonNull(Hackathon.class.getResourceAsStream(resourceConst));
        }
    }
}
