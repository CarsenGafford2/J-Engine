package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * Main Controller Class, connects FXML elements to backend logic.
 * @author Carsen Gafford
 * @version v0.1.0
 */
public class MainController {
    @FXML
    private Canvas canvas;

    @FXML
    private TreeView<String> fileView;

    @FXML
    private BorderPane root;

    @FXML
    private MenuItem newProject;

    private FileManager fileManager;
    private CanvasDrawer canvasDrawer;

    @FXML
    public void initialize() {
        fileManager = new FileManager(fileView);
        canvasDrawer = new CanvasDrawer(canvas, root);

        canvasDrawer.initialize();
        fileManager.initialize();

        newProject.setOnAction(e -> fileManager.createNewProject(root));
    }
}