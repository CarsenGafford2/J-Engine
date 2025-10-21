package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * Main controller for handling inputs and things on the FXML application.
 * @author Carsen Gafford
 * @version v0.1.0
 */
public class MainController {
    @FXML
    private Canvas canvas;

    @FXML
    private TreeView fileView;

    @FXML
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 600, 600);

        File projectDir = new File("C:/IHATEONEDRIVE/deepQ");
        TreeItem<String> rootItem = createNode(projectDir);
        fileView.setRoot(rootItem);
    }

    private TreeItem<String> createNode(File file) {
        TreeItem<String> node = new TreeItem<>(file.getName());

        File[] files = file.listFiles();
        if (files != null) {
            for (File child : files) {
                node.getChildren().add(createNode(child));
            }
        }

        return node;
    }
}