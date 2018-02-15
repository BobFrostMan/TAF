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
     * @return instance of executing process
     */
    public static Process runJarFile(File file, String... flags) throws IOException {
        String[] commandArr = new String[]{PATH_TO_JAVA_EXEC, "-jar", file.getAbsolutePath()};
        String[] commands = concatAll(commandArr, flags);
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File(file.getParentFile().toURI()));
        pb.inheritIO();
        return pb.start();
    }

    public static Process execute(File directory, String... commands) throws IOException {
        return execute(directory, false, commands);
    }

    /**
     * Creates process to execute given commands in specified directory.
     * Shows process output in std output if silent set to false
     *
     * @param directory - directory to start command invocation
     * @param silent - hides commands output if  set to true
     * @param commands - commandline commands to be executed
     * @return process object created for command execution
     * @throws IOException if process can't be started
     */
    public static Process execute(File directory, boolean silent, String... commands) throws IOException {
        Logger.info(Arrays.toString(commands));
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(directory);
        if (!silent) {
            pb.inheritIO();
        }
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

    /**
     * Concat arrays to a single array
     *
     * @param first - first array to concatenate
     * @param rest - other arrays
     * @param <T> - stands for any object type
     * @return result of array concatenation
     */
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
