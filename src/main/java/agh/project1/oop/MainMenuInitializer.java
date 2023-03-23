package agh.project1.oop;


import agh.project1.oop.gui.MainMenu;

import static javafx.application.Application.launch;

public class MainMenuInitializer {
    public static void main(String[] args) {
        try {
            launch(MainMenu.class, args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
