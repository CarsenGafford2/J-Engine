package org.example;

import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * File Manager Class, handles file operations and project management.
 * @author Carsen Gafford
 * @version v0.1.0
 */
public class FileManager {
    private final TreeView<String> fileView;

    public FileManager(TreeView<String> fileView) {
        this.fileView = fileView;
    }

    public void initialize() {
        String projectDirPath = System.getProperty("project.dir", "C:/IHATEONEDRIVE/deepQ");
        File projectDir = new File(projectDirPath);
        fileView.setRoot(createNode(projectDir));
    }

    /**
     * Creates a new project by selecting a directory and naming the project.
     * @param root The root BorderPane of the application.
     */
    public void createNewProject(BorderPane root) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select project location");
        File selectedDir = chooser.showDialog(root.getScene().getWindow());
        if (selectedDir == null) return;

        TextInputDialog dialog = new TextInputDialog("NewProject");
        dialog.setTitle("New Project");
        dialog.setHeaderText("Create new project");
        dialog.setContentText("Project name:");
        dialog.showAndWait().ifPresent(name -> {
            File newFolder = new File(selectedDir, name);
            if (!newFolder.exists() && !newFolder.mkdirs()) {
                System.err.println("Failed to create project folder: " + newFolder.getAbsolutePath());
                return;
            }
            cloneStartingFile("/beeMovieScript.txt", newFolder);
            fileView.setRoot(createNode(newFolder));
        });
    }

    /**
     * Clones a starting file from resources to the new project directory.
     * @param resourceName
     * @param projectDir
     */
    private void cloneStartingFile(String resourceName, File projectDir) {
        File exampleFile = new File(projectDir, "beeMovieScript.txt");
        try (var in = FileManager.class.getResourceAsStream(resourceName)) {
            if (in != null) {
                Files.copy(in, exampleFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } else if (!exampleFile.exists()) {
                exampleFile.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads an existing project by selecting a directory.
     * @param root The root BorderPane of the application.
     */
    public void loadProject(BorderPane root) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select project to load");
        File selectedDir = chooser.showDialog(root.getScene().getWindow());
        if (selectedDir != null && selectedDir.isDirectory()) {
            fileView.setRoot(createNode(selectedDir));
        }
    }

    /**
     * Creates a TreeItem node for the given file and its children.
     * @param file The file to create a node for.
     * @return TreeItem<String> representing the file and its children.
     */
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