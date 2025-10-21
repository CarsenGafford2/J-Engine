package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * Canvas Drawer Class, handles drawing on the canvas.
 * @author Carsen Gafford
 * @version v0.1.0
 */
public class CanvasDrawer {
    private final Canvas canvas;
    private final BorderPane root;

    public CanvasDrawer(Canvas canvas, BorderPane root) {
        this.canvas = canvas;
        this.root = root;
    }

    public void initialize() {
        canvas.widthProperty().bind(root.widthProperty().subtract(200));
        canvas.heightProperty().bind(canvas.widthProperty().multiply(0.5));

        canvas.widthProperty().addListener(e -> draw());
        canvas.heightProperty().addListener(e -> draw());

        draw();
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}