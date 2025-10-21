package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
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
    private BorderPane root;

    @FXML
    private MenuItem newProject;

    @FXML
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 600, 600);
        canvas.widthProperty().bind(root.widthProperty().subtract(200));
        canvas.heightProperty().bind(root.heightProperty().subtract(100));

        canvas.widthProperty().addListener(e -> draw());
        canvas.heightProperty().addListener(e -> draw());

        File projectDir = new File("C:/IHATEONEDRIVE/deepQ");
        TreeItem<String> rootItem = createNode(projectDir);
        fileView.setRoot(rootItem);
    }

    private void draw() {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
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