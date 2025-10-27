package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * Canvas Drawer Class, handles drawing on the canvas.
 * @author Carsen Gafford
 * @version v0.1.1
 */
public class CanvasDrawer {
    private final Canvas canvas;
    private final BorderPane root;

    public CanvasDrawer(Canvas canvas, BorderPane root) {
        this.canvas = canvas;
        this.root = root;
    }

    public void initialize() {
        canvas.widthProperty().unbind();
        canvas.heightProperty().unbind();

        final double aspect = 16.0 / 9.0;

        Runnable update = () -> {
            double availableWidth = Math.max(0, root.getWidth() - 200);
            double availableHeight = Math.max(0, root.getHeight());

            double widthBasedOnHeight = availableHeight * aspect;
            double heightBasedOnWidth = availableWidth / aspect;

            double newWidth, newHeight;

            if (widthBasedOnHeight <= availableWidth) {
                newWidth = widthBasedOnHeight;
                newHeight = availableHeight;
            } else {
                newWidth = availableWidth;
                newHeight = heightBasedOnWidth;
            }

            if (newWidth <= 0 || newHeight <= 0) return;

            if (canvas.getWidth() != newWidth) canvas.setWidth(newWidth);
            if (canvas.getHeight() != newHeight) canvas.setHeight(newHeight);
        };

        root.widthProperty().addListener((obs, o, n) -> update.run());
        root.heightProperty().addListener((obs, o, n) -> update.run());

        canvas.widthProperty().addListener(e -> draw());
        canvas.heightProperty().addListener(e -> draw());

        update.run();
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}