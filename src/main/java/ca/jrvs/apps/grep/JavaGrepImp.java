package ca.jrvs.apps.grep;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepImp implements JavaGrep {

    private String rootPath;
    private String regex;
    private String outFile;

    public static void main(String[] args) {

        if (args.length != 3) {
            try {
                throw new IllegalAccessException("USAGE: regex rootPath outFile");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootpath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void process() throws IOException {
        List<File> files = listFilesLambda(this.getRootPath());
        LinkedList<String> lines = new LinkedList<>();
        for (File f : files) {
            for (String s : readLinesLambda(f)) {
                if (containsPattern(s)) {
                    lines.add(s);
                }
            }
        }
        writeToFile(lines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File root = new File(rootDir);
        LinkedList<File> files = new LinkedList<>();
        if (root.isDirectory()) {
            for (File f : root.listFiles()) {
                files.addAll(listFiles(f.getAbsolutePath()));
            }
        } else {
            files.add(root);
        }
        return files;
    }

    public List<File> listFilesLambda(String rootDir) {
        return Arrays.stream(new File(rootDir).listFiles())
                .flatMap(file -> file.isDirectory() ?
                        listFilesLambda(file.getAbsolutePath()).stream() : Arrays.asList(file).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException er) {
            er.printStackTrace();
        }
        return lines;
    }

    public List<String> readLinesLambda(File inputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new LinkedList<String>();
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
        for (String s : lines) {
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootpath(String rootpath) {
        rootPath = rootpath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}