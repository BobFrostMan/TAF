package com.ufo.core.utils.common;

import com.ufo.core.utils.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Util class primary for command line interface (CLI)
 */
public class CliUtils {

    private static final String PATH_TO_JAVA_EXEC = System.getenv("JAVA_HOME") + "/bin/java";

    /**
     * Run jar file with local jdk, and returns it's process object
     *
     * @return Process instance of executing process
     */
    public static Process runJarFile(File file, String ... flags) throws IOException {
        String[] commandArr = new String[]{PATH_TO_JAVA_EXEC, "-jar", file.getAbsolutePath()};
        String[] commands = concatAll(commandArr, flags);
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File(file.getParentFile().toURI()));
        pb.inheritIO();
        return pb.start();
    }

    public static Process execute(String ... commands) throws IOException {
        Logger.info(Arrays.toString(commands));
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.inheritIO();
        return pb.start();
    }

    public static Process execute(File directory, String ... commands) throws IOException {
        Logger.info(Arrays.toString(commands));
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(directory);
        pb.inheritIO();
        return pb.start();
    }


    /**
     * Terminates given system process
     *
     * @param process - process to terminate
     * @return process exit code
     */
    public static int terminate(Process process) {
        try {
            process.destroy();
            process.waitFor();
        } catch (InterruptedException e) {
            Logger.error("Process didn't finished successfully!");
        }
        process.destroy();
        return process.exitValue();
    }

    private static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

}
