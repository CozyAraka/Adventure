package de.adventure.game.savemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    protected String path;
    protected long filesize;

    protected File file;
    protected Scanner reader;

    protected ArrayList<String> fileContents;

    public FileHandler(String path){
        this.path = path;

        fileContents = new ArrayList<>();

        file = new File(path);
    }

    public void loadFile() {
        try {
            reader = new Scanner(file);

        }catch(FileNotFoundException exception) {
            System.out.println("Specified File was not found");
            exception.printStackTrace();
        }

        while(reader.hasNextLine()) {
            fileContents.add(reader.nextLine());
        }
        reader.close();
    }

    public void unloadFile() {
        fileContents = new ArrayList<>();
    }

    public ArrayList<String> filterValues() {
        ArrayList<String> current = new ArrayList<>();

        for(String string : fileContents) {
            String[] splitted = string.split(":");
            current.add(splitted[1]);
        }
        return current;
    }

    public ArrayList<String> getFileContents() {
        return fileContents;
    }

    public void deleteFile(String path) {
        File file1 = new File(path);
        file1.delete();
    }

    public boolean doesFileExist() {
        return file.exists();
    }

    public void createFile() {
        try {
            file.createNewFile();

        }catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
