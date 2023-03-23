package agh.project1.oop.gui;

import agh.project1.oop.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapElementBoxMaker {
    public VBox createBox(IMapElement element) {
        try {
            int sizeOfImg = 20;
            Image image = new Image(new FileInputStream("src/main/resources/EmptyField.png"));
            if (element != null)
                image = new Image(new FileInputStream(element.getImgPath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(sizeOfImg);
            imageView.setFitHeight(sizeOfImg);
            return new VBox(imageView);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return new VBox(new Label("el"));
    }

    public VBox createHighlightedAnimalBox() {
        try {
            int sizeOfImg = 20;
            Image image = new Image(new FileInputStream("src/main/resources/HighlightedAnimal.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(sizeOfImg);
            imageView.setFitHeight(sizeOfImg);
            return new VBox(imageView);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return new VBox(new Label("el"));
    }
}
