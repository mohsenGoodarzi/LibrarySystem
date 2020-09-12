package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.File;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * JavaFX implementation of simple paint. User can either draw a line or a
 * trace. User can change colour, brush type and save his drawing or clear the
 * current screen.
 *
 * @author Martin Trifinov (965075)
 */
public class Drawing extends Pane {

    private final String SAVE_LOCATION = "./src/UserInterface/FXML/Avatar/";
    private final int CANVAS_HEIGHT = 500;
    private final int CANVAS_WIDTH = 500;
    private final int BUTTON_PREF_SIZE_WIDTH = 75;
    private final int BUTTON_PREF_SIZE_HEIGHT = 25;
    private final int FONT_SIZE = 12;
    private final Font FONT = new Font("Calibri", FONT_SIZE);

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    private double traceX;
    private double traceY;

    private Canvas canvas;
    private GraphicsContext gc;

    private int brushID = 0;

    private TaweLib master;

    /**
     * Constructor for Drawing. Creates all needed fields, creates toolbars and
     * buttons.
     *
     * @param master reference to main application.
     */
    public Drawing(TaweLib master) {
        this.master = master;

        canvas = new Canvas(CANVAS_HEIGHT, CANVAS_WIDTH);
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setFill(Color.WHITE);
        this.clear();

        BorderPane pane = new BorderPane();
        pane.setCenter(canvas);
        pane.setStyle("-fx-background-color: #ababab");

        ToolBar toolBar = new ToolBar();
        createToolBar(toolBar);
        Pane topStrip = new Pane();
        topStrip.setPrefSize(CANVAS_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        VBox vbox = new VBox();
        vbox.getChildren().add(toolBar);
        vbox.getChildren().add(topStrip);
        pane.setTop(vbox);
        super.getChildren().add(pane);

        ToolBar botBar = new ToolBar();
        createBotBar(botBar);
        Pane botStrip = new Pane();
        botStrip.setPrefSize(CANVAS_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        VBox botBox = new VBox();
        botBox.getChildren().add(botStrip);
        botBox.getChildren().add(botBar);
        pane.setBottom(botBox);

        super.setHeight(630);
        super.setWidth(560);

        canvas.setOnMousePressed(e -> {
            System.out.println(e.getX() + ":" + e.getY());
            if (brushID == 1) ///Line
            {
                startX = e.getX();
                startY = e.getY();
            }
            if (brushID == 0) {
                traceX = e.getX();
                traceY = e.getY();
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (brushID == 0) {
                gc.strokeLine(traceX, traceY, e.getX(), e.getY());
                traceX = e.getX();
                traceY = e.getY();
            }
        });
        canvas.setOnMouseReleased(e -> {
            if (brushID == 1) {
                endX = e.getX();
                endY = e.getY();
                gc.strokeLine(startX, startY, endX, endY);
            }
        });
    }

    /**
     * Changes the Stroke colour to the one passed.
     *
     * @param colour.
     */
    private void setColour(Color colour) {
        gc.setStroke(colour);
    }

    /**
     * Changes the brushId. 0 = particle trace 1 = line
     *
     * @param i id of the brush.
     */
    private void setBrushID(int i) {
        this.brushID = i;
    }

    /**
     * Creates an image in SAVED_LOCATION using the userId. Saves files as .png
     * .
     */
    private void save() {
        File file = new File(SAVE_LOCATION
                + +master.getUser().getUserId() + ".png");

        try {
            WritableImage writableImage
                    = new WritableImage(CANVAS_HEIGHT, CANVAS_WIDTH);

            canvas.snapshot(null, writableImage);
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null),
                    "png", file);

        } catch (IOException er) {
            System.out.println("Error::" + er);
        }
    }

    /**
     * Closes the current window. Also saves the picture in case the user
     * forgot.
     *
     */
    private void exit() {
        this.save();
        this.clear();
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
    }

    /**
     * Clears the drawing canvas. Add a white rectangle covering the whole
     * canvas.
     */
    private void clear() {
        gc.fillRect(0, 0, CANVAS_HEIGHT, CANVAS_WIDTH);
    }

    /**
     * Creates the Upper Toolbar. Creates all Buttons for colours and handles
     * their actions.
     *
     * @param toolBar reference to ToolBar in which we add.
     */
    private void createToolBar(ToolBar toolBar) {

        Button black = new Button("Black");
        black.setOnAction((ActionEvent e) -> {
            this.setColour(Color.BLACK);
        });
        black.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        black.setFont(FONT);
        toolBar.getItems().add(black);

        Button red = new Button("Red");
        red.setOnAction((ActionEvent e) -> {
            this.setColour(Color.RED);
        });
        red.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        red.setFont(FONT);
        toolBar.getItems().add(red);

        Button blue = new Button("Blue");
        blue.setOnAction((ActionEvent e) -> {
            this.setColour(Color.BLUE);
        });
        blue.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        blue.setFont(FONT);
        toolBar.getItems().add(blue);

        Button green = new Button("Green");
        green.setOnAction((ActionEvent e) -> {
            this.setColour(Color.GREEN);
        });
        green.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        green.setFont(FONT);
        toolBar.getItems().add(green);

        Button yellow = new Button("Yellow");
        yellow.setOnAction((ActionEvent e) -> {
            this.setColour(Color.YELLOW);
        });
        yellow.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        yellow.setFont(FONT);
        toolBar.getItems().add(yellow);

        Button purple = new Button("Purple");
        purple.setOnAction((ActionEvent e) -> {
            this.setColour(Color.PURPLE);
        });
        purple.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        purple.setFont(FONT);
        toolBar.getItems().add(purple);

        Button pink = new Button("Pink");
        pink.setOnAction((ActionEvent e) -> {
            this.setColour(Color.PINK);
        });
        pink.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        pink.setFont(FONT);
        toolBar.getItems().add(pink);
    }

    /**
     * Creates the Bottom Toolbar. Creates the Line/Brush selection, Size
     * selector and clear,save and exit.
     *
     * @param toolBar reference to ToolBar in which we add.
     */
    private void createBotBar(ToolBar toolBar) {

        Button brush = new Button("Brush");
        Button line = new Button("Line");

        line.setOnAction((ActionEvent e) -> {
            this.setBrushID(1);
            line.setDisable(true);
            brush.setDisable(false);
        });
        brush.setOnAction((ActionEvent e) -> {
            this.setBrushID(0);
            brush.setDisable(true);
            line.setDisable(false);
        });

        brush.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        brush.setFont(FONT);
        line.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        line.setFont(FONT);
        brush.setDisable(true);
        toolBar.getItems().add(brush);
        toolBar.getItems().add(line);

        Button clear = new Button("Clear");
        clear.setOnAction((ActionEvent e) -> {
            this.clear();
        });
        clear.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        clear.setFont(FONT);
        toolBar.getItems().add(clear);

        ToolBar size = new ToolBar();

        Label curentSize = new Label("Size:1");
        curentSize.setFont(FONT);

        Slider sizeSlider = new Slider(1, 25, 1);
        sizeSlider.setOrientation(Orientation.VERTICAL);
        sizeSlider.setPrefSize(1, BUTTON_PREF_SIZE_HEIGHT);
        sizeSlider.autosize();
        sizeSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                curentSize.textProperty().setValue("Size:"
                        + String.valueOf((int) sizeSlider.getValue()));
                gc.setLineWidth((int) sizeSlider.getValue());
            }
        });
        size.getItems().add(sizeSlider);
        size.getItems().add(curentSize);
        size.setTooltip(new Tooltip("Changes the Size of brush/line"));
        size.setMinWidth(BUTTON_PREF_SIZE_WIDTH);
        toolBar.getItems().add(size);

        Separator separator = new Separator();
        separator.setMaxHeight(BUTTON_PREF_SIZE_HEIGHT);
        toolBar.getItems().add(separator);

        Button save = new Button("Save");
        save.setOnAction((ActionEvent e) -> {
            this.save();
        });
        save.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        save.setFont(FONT);
        toolBar.getItems().add(save);

        Button exit = new Button("Exit");
        exit.setOnAction((ActionEvent e) -> {
            this.exit();
        });
        exit.setPrefSize(BUTTON_PREF_SIZE_WIDTH, BUTTON_PREF_SIZE_HEIGHT);
        exit.setFont(FONT);
        toolBar.getItems().add(exit);
    }
}
