package de.adventure.game.savemanager;

import de.adventure.game.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveManager {
    private String path;

    private FileHandler fileHandler;
    private Main main;

    private ArrayList<String> valueList;

    public SaveManager(String path, Main main) {
        this.path = System.getProperty("user.dir") + "\\assets\\" + path;
        this.main = main;

        fileHandler = new FileHandler(this.path);
        System.out.println(this.path);
    }

    public void loadSave() {
        unloadSave();
        fileHandler.loadFile();
        valueList = fileHandler.filterValues();
    }

    public float savedXPos() {
        return Float.valueOf(valueList.get(0));
    }

    public float savedYPos() {
        return Float.valueOf(valueList.get(1));
    }

    public char savedOrientation() {
        return valueList.get(2).toCharArray()[0];
    }

    public float savedHP() {
        return Float.valueOf(valueList.get(3));
    }

    public float savedMP() {
        return Float.valueOf(valueList.get(4));
    }

    public float savedMoney() {
        return Float.valueOf(valueList.get(5));
    }

    public void unloadSave() {
    }

    public void saveGame(ArrayList<String> states) {
        if(fileHandler.doesFileExist()) {
            deleteSave(path);
            fileHandler.createFile();
            try{
                FileWriter writer = new FileWriter(path);
                for(String string : states) {
                    writer.write(string + "\n");
                    System.out.println(string);
                }
                writer.close();
            }catch(IOException exception) {
                exception.printStackTrace();
            }
        }else {
            fileHandler.createFile();
            try{
                FileWriter writer = new FileWriter(path);
                for(String string : states) {
                    writer.write(string + "\n");
                    System.out.println(string);
                }
                writer.close();
            }catch(IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void deleteSave(String path) {
        if(fileHandler.doesFileExist()) {
            fileHandler.deleteFile(path);
        }else {
            System.out.println("Save does not exist, create a new save");
        }
    }
}
