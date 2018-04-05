package com.icrisat.sbdm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FqConverter {

    static long lineNo = 1;
    static String operation = "0";

    public static void main(String[] args) {
        String fileName = args[0];
        operation = args[1];
        String newFileName = args[2];
        System.out.println(operation);
        try (Stream<String> stream = Files.lines(Paths.get(fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName))) {
            stream.forEach((String line) -> {
                try {
                    printMe(line, writer);
                } catch (IOException e) {
                    System.out.println("Issue on line " + lineNo);
                    e.printStackTrace();
                    System.exit(1);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void printMe(String line, BufferedWriter writer) throws IOException {
        char[] array = line.toCharArray();
        switch ((int) (lineNo % 4)) {
            case 0:
                long length = array.length;
                if (operation.equalsIgnoreCase("0")) {
                    for (int i = 0; i < length; i++) {
                        array[i] = (char) ((int) array[i] - 31);
                    }
                } else if (operation.equalsIgnoreCase("1")) {
                    for (int i = 0; i < length; i++) {
                        array[i] = (char) ((int) array[i] + 31);
                    }
                }
                break;
            case 1:
                if (array[0] != '@')
                    throw new IOException("Line does not start with @");
                break;
            case 2:
                break;
            case 3:
                if (array[0] != '+')
                    throw new IOException("Line does not start with +");
                break;
        }
        lineNo++;
        writer.write(array);
        writer.newLine();
    }
}