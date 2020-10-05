package ru.javawebinar.basejava.util;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
//        String filePath = ".\\.gitignore";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }

        File dir = new File("./src/ru/");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        MainFile mainFile = new MainFile();
        mainFile.searchFile(dir, "");
    }

    public void searchFile(File directory, String counter) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(counter + " " + file.getName());
                if (file.isDirectory()) {
                    counter += "-";
                    searchFile(file, counter);
                }
            }
        }
    }
}
