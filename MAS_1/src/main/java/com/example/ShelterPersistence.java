package com.example;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Utility class responsible for writing/reading all class extents
 * to a single binary file.
 *
 * Only Inhabitant currently has an extent; if later other classes
 * get one, just add them to saveAll()/loadAll().
 */
public final class ShelterPersistence implements Serializable {

    private static final long serialVersionUID = 1L;

    /* --------- configuration --------- */
    public static final String DATA_FILE = "shelter.bin";

    /* Prevent instantiation */
    private ShelterPersistence() { }

    /* --------- public API --------- */

    public static void saveAll() {
        saveAll(DATA_FILE);
    }

    public static void loadAll() {
        loadAll(DATA_FILE);
    }

    /**
     * Serializes all extents (and extra static data) to given file.
     */
    public static void saveAll(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {

            /* --- write extents --- */
            oos.writeObject(Inhabitant.getExtent());

            /* --- write additional static fields if needed --- */
            oos.writeInt(Inhabitant.getMinAllowedAge());

        } catch (Exception e) {
            throw new PersistenceException("Error saving data: " + e.getMessage(), e);
        }
    }

    /**
     * Reads extents from given file and injects them back
     * into corresponding classes.
     */
    @SuppressWarnings("unchecked")
    public static void loadAll(String fileName) {
        if (!Files.exists(Path.of(fileName))) {
            // nothing to load, silent return
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {

            /* --- read extents --- */
            List<Inhabitant> inhabitants =
                    (List<Inhabitant>) ois.readObject();
            Inhabitant.replaceExtent(inhabitants);

            /* --- read additional static fields --- */
            int minAge = ois.readInt();
            Inhabitant.setMinAllowedAge(minAge);

        } catch (Exception e) {
            throw new PersistenceException("Error loading data: " + e.getMessage(), e);
        }
    }

    /* --------- helper exception --------- */

    /**
     * Simple unchecked wrapper for IO / class-format problems.
     */
    public static class PersistenceException extends RuntimeException {
        public PersistenceException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
