package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class GraphReader {
    // Variables to read input file
    static BufferedReader inp;
    private static int inpPos = 0;
    private static String inpLine;

    public GraphReader(String prefix, int testcase){
        try {
            inp = new BufferedReader(new FileReader(getFileFromResources(prefix + "_tc_" + testcase + ".in")));
            inpLine = inp.readLine();
        } catch (IOException _e) {
            throw new RuntimeException(_e);
        }
    }

    public String next() throws Exception {
        int nextPos = inpLine.indexOf(' ', inpPos + 1);
        String token = inpLine.substring(inpPos, nextPos == -1 ? inpLine.length() : nextPos);
        if (nextPos == -1)
            inpLine = inp.readLine();
        inpPos = nextPos + 1;
        return token;
    }

    private File getFileFromResources(String fileName) {
        ClassLoader classLoader = GraphReader.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        return new File(resource.getFile());
    }

}
