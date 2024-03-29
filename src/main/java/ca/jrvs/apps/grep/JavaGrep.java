package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * Top level search workflow
     *
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return files under the rootDir
     */
    List<File> listFiles(String rootDir);

    /**
     * Read a file and return all the lines
     * <p>
     * Explain FileReader, BufferedReader, and character encoding
     *
     * @param inputFile file to  be read
     * @return lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    List<String> readLines(File inputFile);

    /**
     * check if a line contains the reges pattern (passed by user)
     *
     * @param line input string
     * @return true if there is a match
     */
    boolean containsPattern(String line);


    /**
     * Write lines to a file
     * <p>
     * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
     *
     * @param lines matches line
     * @throws IOException if write failed
     */

    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootpath(String rootpath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}