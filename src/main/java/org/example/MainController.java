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
    private TreeView<String> fileView;

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

        String projectDirPath = System.getProperty("project.dir", "C:/IHATEONEDRIVE/deepQ");
        File projectDir = new File(projectDirPath);
        TreeItem<String> rootItem = createNode(projectDir);
        fileView.setRoot(rootItem);

        newProject.setOnAction(e -> {
            javafx.stage.DirectoryChooser chooser = new javafx.stage.DirectoryChooser();
            chooser.setTitle("Select project location");
            File selectedDir = chooser.showDialog(root.getScene().getWindow());
            if (selectedDir == null) {
                return;
            }

            var dialog = new javafx.scene.control.TextInputDialog("NewProject");
            dialog.setTitle("New Project");
            dialog.setHeaderText("Create new project");
            dialog.setContentText("Project name:");
            var result = dialog.showAndWait();
            result.ifPresent(name -> {
                File newFolder = new File(selectedDir, name);
                if (!newFolder.exists() && !newFolder.mkdirs()) {
                    System.err.println("Failed to create project folder: " + newFolder.getAbsolutePath());
                    return;
                }
                cloneStartingFile("/beeMovieScript.txt", newFolder);
                TreeItem<String> newRoot = createNode(newFolder);
                fileView.setRoot(newRoot);
            });
        });
    }

    private File cloneStartingFile(String resourceName, File projectDir) {
        if (!projectDir.exists() && !projectDir.mkdirs()) {
            System.err.println("Failed to create project directory: " + projectDir.getAbsolutePath());
        }

        File exampleStartingFile = new File(projectDir, "beeMovieScript.txt");
        try (var in = MainController.class.getResourceAsStream(resourceName)) {
            if (in != null) {
                java.nio.file.Files.copy(in, exampleStartingFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } else {
                if (!exampleStartingFile.exists()) {
                    exampleStartingFile.createNewFile();
                }
            }
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
        return exampleStartingFile;
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