import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class MainController {

    @FXML
    private JFXButton fileBtn, editBtn, aboutBtn;

    @FXML
    private JFXButton openEditorBtn, openFileBtn, saveFileBtn, saveAsBtn, exitBtn;

    @FXML
    private JFXButton colorBtn;

    @FXML
    private JFXButton loadBtn, clearBtn, saveBtn, closeBtn, deleteBtn, closeBtn1;

    @FXML
    private JFXTextField asciiTF;

    @FXML
    private TextField letterkeyTF;

    @FXML
    private AnchorPane editorPane, colorPickerPane;

    @FXML
    private ScrollPane aboutPane;

    @FXML
    private Canvas notepadCanvas;

    @FXML
    private VBox fileOption, editOption;

    @FXML
    private VBox fontsVbox;

    @FXML
    private Pane colourPane;

    @FXML
    private Label warning;

    @FXML
    private JFXSlider redSlider, greenSlider, blueSlider;

    @FXML
    private TextField redTF, greenTF, blueTF;

    @FXML
    private Button
            btn1x1, btn1x2, btn1x3, btn1x4, btn1x5, btn1x6, btn1x7, btn1x8,
            btn2x1, btn2x2, btn2x3, btn2x4, btn2x5, btn2x6, btn2x7, btn2x8,
            btn3x1, btn3x2, btn3x3, btn3x4, btn3x5, btn3x6, btn3x7, btn3x8,
            btn4x1, btn4x2, btn4x3, btn4x4, btn4x5, btn4x6, btn4x7, btn4x8,
            btn5x1, btn5x2, btn5x3, btn5x4, btn5x5, btn5x6, btn5x7, btn5x8,
            btn6x1, btn6x2, btn6x3, btn6x4, btn6x5, btn6x6, btn6x7, btn6x8,
            btn7x1, btn7x2, btn7x3, btn7x4, btn7x5, btn7x6, btn7x7, btn7x8,
            btn8x1, btn8x2, btn8x3, btn8x4, btn8x5, btn8x6, btn8x7, btn8x8;

    // ********************************************************************************

    final int CHARACTER_SIZE = 128;

    // current bitmap
    private final boolean[][] bitmap = new boolean[8][8];

    //font files
    private final BitMap[] fonts = new BitMap[CHARACTER_SIZE];
    private final BitMap[] bold = new BitMap[CHARACTER_SIZE];
    private final BitMap[] italics = new BitMap[CHARACTER_SIZE];

    //current text color
    private Color color = Color.color(1, 1, 1);

    // canvas initial position
    private int canvasX = 5, canvasY = 6;

    // text document info
    private String word = "", text = "";
    private int textCount = 0, wordCount = 0;
    private final ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<MyCharacter> textDocument = new ArrayList<>();

    // file selection and info
    private File SelectedFile;
    private String fileDestination = "Note.editorTxt";

    //if option is on
    private boolean optionKey = false;

    /* TO-DO LIST*/

/*
    Graphics Project #3 (Dec. 29th)
        -add math symbols
        -fix delete ‘/n’ cursor displacement
        -fix word return —> stack
        -add an about page to explain symbols & how to use this program.
        -add ascii table in about section
        -italics + bold
*/

    /*keyboard*/

    /*
    Unused keys: 4-7 , 14-23, 25-26, 28-31,
        1-31

    Shift 1-9 all lower case
   */

    /*keyboard mappings*/

/*

    / —> division sign
    + —> plus sign
    ? —> sum
    _ —> integral
*/

    public void initialize() {

        for (int i = 0; i < CHARACTER_SIZE; i++) {
            fonts[i] = new BitMap();
            bold[i] = new BitMap();
            italics[i] = new BitMap();
        }

        editorPane.setVisible(false);
        editOption.setVisible(false);
        fileOption.setVisible(false);
        aboutPane.setVisible(false);

        colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                + blueTF.getText() + "); -fx-border-style: solid; -fx-border-width: 0.1;");


        readFonts();
        clearBitmap();


        //coloring the drawing canvas to color white
        PixelWriter pixelWriter = notepadCanvas.getGraphicsContext2D().getPixelWriter();
        for (int i = 0; i < 387; i++)
            for (int j = 0; j < 454; j++)
                pixelWriter.setColor(i, j, color);

        btn1x1.setOnAction((ActionEvent e) -> {
            if (bitmap[0][0]) { // one
                bitmap[0][0] = false;
                btn1x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][0] = true;
            }
        });

        btn1x2.setOnAction((ActionEvent e) -> {
            if (bitmap[0][1]) { // one
                bitmap[0][1] = false;
                btn1x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][1] = true;
            }
        });

        btn1x3.setOnAction((ActionEvent e) -> {
            if (bitmap[0][2]) { // one
                bitmap[0][2] = false;
                btn1x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][2] = true;
            }
        });

        btn1x4.setOnAction((ActionEvent e) -> {
            if (bitmap[0][3]) { // one
                bitmap[0][3] = false;
                btn1x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][3] = true;
            }
        });


        btn1x5.setOnAction((ActionEvent e) -> {
            if (bitmap[0][4]) { // one
                bitmap[0][4] = false;
                btn1x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][4] = true;
            }
        });

        btn1x6.setOnAction((ActionEvent e) -> {
            if (bitmap[0][5]) { // one
                bitmap[0][5] = false;
                btn1x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][5] = true;
            }
        });

        btn1x7.setOnAction((ActionEvent e) -> {
            if (bitmap[0][6]) { // one
                bitmap[0][6] = false;
                btn1x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][6] = true;
            }
        });

        btn1x8.setOnAction((ActionEvent e) -> {
            if (bitmap[0][7]) { // one
                bitmap[0][7] = false;
                btn1x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn1x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[0][7] = true;
            }
        });

        btn2x1.setOnAction((ActionEvent e) -> {
            if (bitmap[1][0]) { // one
                bitmap[1][0] = false;
                btn2x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][0] = true;
            }
        });

        btn2x2.setOnAction((ActionEvent e) -> {
            if (bitmap[1][1]) { // one
                bitmap[1][1] = false;
                btn2x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][1] = true;
            }
        });

        btn2x3.setOnAction((ActionEvent e) -> {
            if (bitmap[1][2]) { // one
                bitmap[1][2] = false;
                btn2x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][2] = true;
            }
        });

        btn2x4.setOnAction((ActionEvent e) -> {
            if (bitmap[1][3]) { // one
                bitmap[1][3] = false;
                btn2x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][3] = true;
            }
        });

        btn2x5.setOnAction((ActionEvent e) -> {
            if (bitmap[1][4]) { // one
                bitmap[1][4] = false;
                btn2x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][4] = true;
            }
        });

        btn2x6.setOnAction((ActionEvent e) -> {
            if (bitmap[1][5]) { // one
                bitmap[1][5] = false;
                btn2x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][5] = true;
            }
        });

        btn2x7.setOnAction((ActionEvent e) -> {
            if (bitmap[1][6]) { // one
                bitmap[1][6] = false;
                btn2x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][6] = true;
            }
        });

        btn2x8.setOnAction((ActionEvent e) -> {
            if (bitmap[1][7]) { // one
                bitmap[1][7] = false;
                btn2x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn2x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[1][7] = true;
            }
        });

        btn3x1.setOnAction((ActionEvent e) -> {
            if (bitmap[2][0]) { // one
                bitmap[2][0] = false;
                btn3x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][0] = true;
            }
        });

        btn3x2.setOnAction((ActionEvent e) -> {
            if (bitmap[2][1]) { // one
                bitmap[2][1] = false;
                btn3x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][1] = true;
            }
        });

        btn3x3.setOnAction((ActionEvent e) -> {
            if (bitmap[2][2]) { // one
                bitmap[2][2] = false;
                btn3x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][2] = true;
            }
        });

        btn3x4.setOnAction((ActionEvent e) -> {
            if (bitmap[2][3]) { // one
                bitmap[2][3] = false;
                btn3x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][3] = true;
            }
        });

        btn3x5.setOnAction((ActionEvent e) -> {
            if (bitmap[2][4]) { // one
                bitmap[2][4] = false;
                btn3x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][4] = true;
            }
        });

        btn3x6.setOnAction((ActionEvent e) -> {
            if (bitmap[2][5]) { // one
                bitmap[2][5] = false;
                btn3x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][5] = true;
            }
        });

        btn3x7.setOnAction((ActionEvent e) -> {
            if (bitmap[2][6]) { // one
                bitmap[2][6] = false;
                btn3x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][6] = true;
            }
        });

        btn3x8.setOnAction((ActionEvent e) -> {
            if (bitmap[2][7]) { // one
                bitmap[2][7] = false;
                btn3x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn3x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[2][7] = true;
            }
        });

        btn4x1.setOnAction((ActionEvent e) -> {
            if (bitmap[3][0]) { // one
                bitmap[3][0] = false;
                btn4x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][0] = true;
            }
        });

        btn4x2.setOnAction((ActionEvent e) -> {
            if (bitmap[3][1]) { // one
                bitmap[3][1] = false;
                btn4x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][1] = true;
            }
        });

        btn4x3.setOnAction((ActionEvent e) -> {
            if (bitmap[3][2]) { // one
                bitmap[3][2] = false;
                btn4x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][2] = true;
            }
        });

        btn4x4.setOnAction((ActionEvent e) -> {
            if (bitmap[3][3]) { // one
                bitmap[3][3] = false;
                btn4x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][3] = true;
            }
        });

        btn4x5.setOnAction((ActionEvent e) -> {
            if (bitmap[3][4]) { // one
                bitmap[3][4] = false;
                btn4x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][4] = true;
            }
        });

        btn4x6.setOnAction((ActionEvent e) -> {
            if (bitmap[3][5]) { // one
                bitmap[3][5] = false;
                btn4x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][5] = true;
            }
        });

        btn4x7.setOnAction((ActionEvent e) -> {
            if (bitmap[3][6]) { // one
                bitmap[3][6] = false;
                btn4x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][6] = true;
            }
        });

        btn4x8.setOnAction((ActionEvent e) -> {
            if (bitmap[3][7]) { // one
                bitmap[3][7] = false;
                btn4x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn4x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[3][7] = true;
            }
        });

        btn5x1.setOnAction((ActionEvent e) -> {
            if (bitmap[4][0]) { // one
                bitmap[4][0] = false;
                btn5x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][0] = true;
            }
        });

        btn5x2.setOnAction((ActionEvent e) -> {
            if (bitmap[4][1]) { // one
                bitmap[4][1] = false;
                btn5x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][1] = true;
            }
        });

        btn5x3.setOnAction((ActionEvent e) -> {
            if (bitmap[4][2]) { // one
                bitmap[4][2] = false;
                btn5x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][2] = true;
            }
        });

        btn5x4.setOnAction((ActionEvent e) -> {
            if (bitmap[4][3]) { // one
                bitmap[4][3] = false;
                btn5x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][3] = true;
            }
        });

        btn5x5.setOnAction((ActionEvent e) -> {
            if (bitmap[4][4]) { // one
                bitmap[4][4] = false;
                btn5x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][4] = true;
            }
        });

        btn5x6.setOnAction((ActionEvent e) -> {
            if (bitmap[4][5]) { // one
                bitmap[4][5] = false;
                btn5x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][5] = true;
            }
        });

        btn5x7.setOnAction((ActionEvent e) -> {
            if (bitmap[4][6]) { // one
                bitmap[4][6] = false;
                btn5x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][6] = true;
            }
        });

        btn5x8.setOnAction((ActionEvent e) -> {
            if (bitmap[4][7]) { // one
                bitmap[4][7] = false;
                btn5x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn5x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[4][7] = true;
            }
        });

        btn6x1.setOnAction((ActionEvent e) -> {
            if (bitmap[5][0]) { // one
                bitmap[5][0] = false;
                btn6x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][0] = true;
            }
        });

        btn6x2.setOnAction((ActionEvent e) -> {
            if (bitmap[5][1]) { // one
                bitmap[5][1] = false;
                btn6x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][1] = true;
            }
        });

        btn6x3.setOnAction((ActionEvent e) -> {
            if (bitmap[5][2]) { // one
                bitmap[5][2] = false;
                btn6x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][2] = true;
            }
        });

        btn6x4.setOnAction((ActionEvent e) -> {
            if (bitmap[5][3]) { // one
                bitmap[5][3] = false;
                btn6x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][3] = true;
            }
        });

        btn6x5.setOnAction((ActionEvent e) -> {
            if (bitmap[5][4]) { // one
                bitmap[5][4] = false;
                btn6x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][4] = true;
            }
        });

        btn6x6.setOnAction((ActionEvent e) -> {
            if (bitmap[5][5]) { // one
                bitmap[5][5] = false;
                btn6x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][5] = true;
            }
        });

        btn6x7.setOnAction((ActionEvent e) -> {
            if (bitmap[5][6]) { // one
                bitmap[5][6] = false;
                btn6x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][6] = true;
            }
        });

        btn6x8.setOnAction((ActionEvent e) -> {
            if (bitmap[5][7]) { // one
                bitmap[5][7] = false;
                btn6x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn6x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[5][7] = true;
            }
        });

        btn7x1.setOnAction((ActionEvent e) -> {
            if (bitmap[6][0]) { // one
                bitmap[6][0] = false;
                btn7x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][0] = true;
            }
        });

        btn7x2.setOnAction((ActionEvent e) -> {
            if (bitmap[6][1]) { // one
                bitmap[6][1] = false;
                btn7x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][1] = true;
            }
        });

        btn7x3.setOnAction((ActionEvent e) -> {
            if (bitmap[6][2]) { // one
                bitmap[6][2] = false;
                btn7x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][2] = true;
            }
        });

        btn7x4.setOnAction((ActionEvent e) -> {
            if (bitmap[6][3]) { // one
                bitmap[6][3] = false;
                btn7x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][3] = true;
            }
        });

        btn7x5.setOnAction((ActionEvent e) -> {
            if (bitmap[6][4]) { // one
                bitmap[6][4] = false;
                btn7x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][4] = true;
            }
        });

        btn7x6.setOnAction((ActionEvent e) -> {
            if (bitmap[6][5]) { // one
                bitmap[6][5] = false;
                btn7x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][5] = true;
            }
        });

        btn7x7.setOnAction((ActionEvent e) -> {
            if (bitmap[6][6]) { // one
                bitmap[6][6] = false;
                btn7x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][6] = true;
            }
        });

        btn7x8.setOnAction((ActionEvent e) -> {
            if (bitmap[6][7]) { // one
                bitmap[6][7] = false;
                btn7x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn7x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[6][7] = true;
            }
        });

        btn8x1.setOnAction((ActionEvent e) -> {
            if (bitmap[7][0]) { // one
                bitmap[7][0] = false;
                btn8x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x1.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][0] = true;
            }
        });

        btn8x2.setOnAction((ActionEvent e) -> {
            if (bitmap[7][1]) { // one
                bitmap[7][1] = false;
                btn8x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x2.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][1] = true;
            }
        });

        btn8x3.setOnAction((ActionEvent e) -> {
            if (bitmap[7][2]) { // one
                bitmap[7][2] = false;
                btn8x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x3.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][2] = true;
            }
        });

        btn8x4.setOnAction((ActionEvent e) -> {
            if (bitmap[7][3]) { // one
                bitmap[7][3] = false;
                btn8x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x4.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][3] = true;
            }
        });

        btn8x5.setOnAction((ActionEvent e) -> {
            if (bitmap[7][4]) { // one
                bitmap[7][4] = false;
                btn8x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x5.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][4] = true;
            }
        });

        btn8x6.setOnAction((ActionEvent e) -> {
            if (bitmap[7][5]) { // one
                bitmap[7][5] = false;
                btn8x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x6.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][5] = true;
            }
        });

        btn8x7.setOnAction((ActionEvent e) -> {
            if (bitmap[7][6]) { // one
                bitmap[7][6] = false;
                btn8x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x7.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][6] = true;
            }
        });

        btn8x8.setOnAction((ActionEvent e) -> {
            if (bitmap[7][7]) { // one
                bitmap[7][7] = false;
                btn8x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
            } else {          // zero
                btn8x8.setStyle("-fx-background-color: BLACK; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
                bitmap[7][7] = true;
            }
        });

        redSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            redTF.setText(Integer.toString(newValue.intValue()));
            colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                    + blueTF.getText() + ") ; -fx-border-style: solid;; -fx-border-width: 0.1;");
        });

        redTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int x = Integer.parseInt(redTF.getText());
                if (x <= 255 && x >= 0) {
                    redTF.setStyle("-fx-text-inner-color: black;");
                    warning.setVisible(false);
                    redSlider.setValue((x));
                    colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                            + blueTF.getText() + "); -fx-border-style: solid;; -fx-border-width: 0.1;");
                } else {
                    warning.setVisible(true);
                    redTF.setText("0");
                    redTF.setStyle("-fx-text-inner-color: red;");
                }
            } catch (Exception e) {
                warning.setVisible(true);
                redTF.setText("0");
                redTF.setStyle("-fx-text-inner-color: red;");
            }
        });

        greenSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            greenTF.setText(Integer.toString(newValue.intValue()));
            colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                    + blueTF.getText() + "); -fx-border-style: solid;; -fx-border-width: 0.1;");
        });

        greenTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int x = Integer.parseInt(greenTF.getText());
                if (x <= 255 && x >= 0) {
                    greenTF.setStyle("-fx-text-inner-color: black;");
                    warning.setVisible(false);
                    greenSlider.setValue((x));
                    colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                            + blueTF.getText() + "); -fx-border-style: solid; -fx-border-width: 0.1;");
                } else {
                    warning.setVisible(true);
                    greenTF.setText("0");
                    greenTF.setStyle("-fx-text-inner-color: red;");
                }
            } catch (Exception e) {
                warning.setVisible(true);
                greenTF.setText("0");
                greenTF.setStyle("-fx-text-inner-color: red;");
            }
        });

        blueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            blueTF.setText(Integer.toString(newValue.intValue()));
            colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                    + blueTF.getText() + "); -fx-border-style: solid; -fx-border-width: 0.1;");
        });

        blueTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int x = Integer.parseInt(blueTF.getText());
                if (x <= 255 && x >= 0) {
                    blueTF.setStyle("-fx-text-inner-color: black;");
                    warning.setVisible(false);
                    blueSlider.setValue((x));
                    colourPane.setStyle("-fx-background-color: rgb(" + redTF.getText() + "," + greenTF.getText() + ","
                            + blueTF.getText() + "); -fx-border-style: solid; -fx-border-width: 0.1;");
                } else {
                    warning.setVisible(true);
                    blueTF.setText("0");
                    blueTF.setStyle("-fx-text-inner-color: red;");
                }
            } catch (Exception e) {
                warning.setVisible(true);
                blueTF.setText("0");
                blueTF.setStyle("-fx-text-inner-color: red;");
            }
        });

        notepadCanvas.setOnMousePressed((a) -> notepadCanvas.requestFocus() );

    }

    @FXML
    public void handleButtons(ActionEvent event) throws IOException {
        if (event.getSource() == fileBtn) {
            fileOption.setVisible(!fileOption.isVisible());
            editOption.setVisible(false);
        } else if (event.getSource() == editBtn) {
            fileOption.setVisible(false);
            editOption.setVisible(!editOption.isVisible());
        } else if (event.getSource() == aboutBtn) {
            aboutPane.setVisible(true);
            colorPickerPane.setVisible(false);
            editorPane.setVisible(false);
            fileOption.setVisible(false);
            editOption.setVisible(false);
        } else if (event.getSource() == openFileBtn) {
            fileOption.setVisible(false);
            singleFileChooser();
            readFile();
        } else if (event.getSource() == saveFileBtn) {
            fileOption.setVisible(false);
            saveFile();
        } else if (event.getSource() == saveAsBtn) {
            fileOption.setVisible(false);
            singleDirectoryChooser();
            saveFile();
        } else if (event.getSource() == exitBtn) {
            fileOption.setVisible(false);
            System.exit(0);
        } else if (event.getSource() == openEditorBtn) {
            editorPane.setVisible(true);
            colorPickerPane.setVisible(false);
            aboutPane.setVisible(false);
            editOption.setVisible(false);
        } else if (event.getSource() == colorBtn) {
            colorPickerPane.setVisible(true);
            editorPane.setVisible(false);
            aboutPane.setVisible(false);
            editOption.setVisible(false);
        } else if (event.getSource() == loadBtn) {
            loadFont();
        } else if (event.getSource() == clearBtn) {
            clearBitmap();
        } else if (event.getSource() == saveBtn) {
            addFont();
        } else if (event.getSource() == closeBtn || event.getSource() == closeBtn1) {
            editorPane.setVisible(false);
            colorPickerPane.setVisible(false);
        } else if (event.getSource() == deleteBtn) {
            deleteFont();
        }
    }

    public void singleFileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "+.editorTxt"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null)
            SelectedFile = selectedFile;
    }

    public void singleDirectoryChooser() {
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);
        if (selectedDirectory != null)
            fileDestination = selectedDirectory.getAbsolutePath() + "/Note" + ".editorTxt";
    }

    public void type(KeyEvent event) {
        char charTyped;
        if (!event.getText().equals("")) {
            String code = event.getCode().getName();
            if (code.equals("Enter")) {
                charTyped = 10;
            } else
                charTyped = event.getText().charAt(0);
        } else {
            // check key input command
            String code = event.getCode().getName();
            //System.out.println(code);

            if (code.equals("Esc"))
                charTyped = 27;
            else if (code.equals("Backspace"))
                charTyped = 8;
            else if (code.equals("Command"))
                charTyped = 1;
            else if (code.equals("Alt")) {
                charTyped = 2;
                optionKey = true;
            }
            else if (code.equals("Ctrl"))
                charTyped = 3;
            else if (code.equals("Shift"))
                charTyped = 4;
            else if (code.equals("Up"))
                charTyped = 5;
            else if (code.equals("Down"))
                charTyped = 6;
            else if (code.equals("Right"))
                charTyped = 7;
            else if (code.equals("Left"))
                charTyped = 8;
            else
                charTyped = 127;
        }

        color = Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255);
        drawChar(charTyped, color);

/*        System.out.println(text + " string count--> "+textCount);
        System.out.println(word + " word count--> " + wordCount);
        System.out.println("colors--> " + colors);
        System.out.println("colors text document--> " + textDocument.toString());*/
    }

    private void drawChar(int key, Color color) {

        PixelWriter pixelWriter = notepadCanvas.getGraphicsContext2D().getPixelWriter();

        // space or tab
        if (key == 32 || key == 9) {
            word = "";
            wordCount = 0;
        }

        // tab key
        if (key == 9) {
            canvasX = canvasX + 8 * 4; // four spaces
            if (canvasX >= 379) {
                canvasY = canvasY + 8;
                canvasX = 5;
            }

            textCount = textCount + 3;
            textDocument.add(new MyCharacter((char)9, color));
            textDocument.add(new MyCharacter((char)9, color));
            textDocument.add(new MyCharacter((char)9, color));

            colors.add(Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255));
            colors.add(Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255));
            colors.add(Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255));
            return;
        }

        // return key
        else if (key == 10) {
            canvasX = 5;
            if (canvasY < 446)
                canvasY = canvasY + 8;
            else
                canvasY = 446;

            colors.add(Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255));
            textCount++;
            word = "";

            wordCount = 0;
            text = text.concat((char)key + "");
            textDocument.add(new MyCharacter((char)key, color));
            return;
        }

        // the esc and backspace key
        else if (key == 27 || key == 8) {
            canvasX = canvasX - 8;
            if (canvasX >= 5) {
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++)
                        pixelWriter.setColor(canvasX + j, canvasY + i, Color.WHITE);

                textCount--;
                text = text.substring(0, textCount);
                colors.remove(colors.size()-1);

                textDocument.remove(textDocument.size()-1);

                if (word.length() != 0) {
                    wordCount--;
                    word = word.substring(0, wordCount);
                }
                return;
            } else if (canvasX < 5 && canvasY != 6) {
                canvasY = canvasY - 8;
                canvasX = 379 - 8;
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++)
                        pixelWriter.setColor(canvasX + j, canvasY + i, Color.WHITE);

                textCount--;
                text = text.substring(0, textCount);
                colors.remove(colors.size()-1);

                textDocument.remove(textDocument.size()-1);

                if (word.length() != 0) {
                    wordCount--;
                    word = word.substring(0, wordCount);
                }
            } else
                canvasX = 5; // at the start
            return;
        }

        // command keys (1-8)
        else if(1<=key && key <=8) {
            System.out.println("command keys");
            return;
        }

        else if (optionKey) {
            // option key
            colors.add(Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255));
            textCount++;
            wordCount++;
            text = text.concat((char) key + "");
            word = word.concat((char) key + "");

            textDocument.add(new MyCharacter((char)key, color));

        } else {
            colors.add(Color.color(redSlider.getValue() / 255, greenSlider.getValue() / 255, blueSlider.getValue() / 255));
            textCount++;
            wordCount++;
            text = text.concat((char) key + "");
            word = word.concat((char) key + "");

            textDocument.add(new MyCharacter((char)key, color));
        }

        // small text
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (fonts[key].getBitmap()[i][j]) {
                    if (canvasX >= 379) {
                        canvasY = canvasY + 8;
                        canvasX = 5;
                    }
                    pixelWriter.setColor(canvasX + j, canvasY + i, color);
                }

        canvasX = canvasX + 8;

        if (canvasX >= 379) {
            canvasY = canvasY + 8;
            canvasX = 5;
        }

    /*    // bigger text

        // initial positions
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (fonts[0].getBitmap()[i][j]) {
                    if(canvasX+j >= 379) {
                        canvasY = canvasY + 8;
                        canvasX = 5;
                    }
                    pixelWriter.setColor(canvasX + j, canvasY + i, color);
                }

        canvasX = canvasX + 8;

        if(canvasX >= 379) {
            canvasY = canvasY + 8;
            canvasX = 5;
        }*/

    }

    private void loadFont() {

    }

    private void addFont() throws IOException {

        fonts[Integer.parseInt(asciiTF.getText())].setBitmap(bitmap);
        saveFonts();
    }

    private void deleteFont() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                fonts[Integer.parseInt(asciiTF.getText())].getBitmap()[i][j] = false;
    }

    private void saveFonts() throws IOException {

        BufferedWriter output = null;
        try {
            File file = new File("MyFonts.txt");
            output = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < CHARACTER_SIZE; i++) {
                for (int j = 0; j < 8; j++) {
                    String bytes = "";
                    for (int k = 0; k < 8; k++)
                        if (fonts[i].getBitmap()[j][k])
                            bytes = bytes.concat("1");
                        else
                            bytes = bytes.concat("0");
                    int decimal = Integer.parseInt(bytes, 2);
                    output.write((byte) decimal);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }

        output = null;
        try {
            File file = new File("MyBoldFonts.txt");
            output = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < CHARACTER_SIZE; i++) {
                for (int j = 0; j < 8; j++) {
                    String bytes = "";
                    for (int k = 0; k < 8; k++)
                        if (bold[i].getBitmap()[j][k])
                            bytes = bytes.concat("1");
                        else
                            bytes = bytes.concat("0");
                    int decimal = Integer.parseInt(bytes, 2);
                    output.write((byte) decimal);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }

        output = null;
        try {
            File file = new File("MyItalicsFonts.txt");
            output = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < CHARACTER_SIZE; i++) {
                for (int j = 0; j < 8; j++) {
                    String bytes = "";
                    for (int k = 0; k < 8; k++)
                        if (italics[i].getBitmap()[j][k])
                            bytes = bytes.concat("1");
                        else
                            bytes = bytes.concat("0");
                    int decimal = Integer.parseInt(bytes, 2);
                    output.write((byte) decimal);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    private void readFonts() {

        try {
            File fontFile = new File("MyFonts.txt");
            Scanner myReader;
            if (fontFile.exists())
                myReader = new Scanner(fontFile);
            else
                return;
            int count = 0;
            String data = "";
            String binary = "";
            while (myReader.hasNextLine()) {
                data = data.concat(myReader.nextLine());
            }
            myReader.close();

            //System.out.println(data);
            //System.out.println(data.length());

            for (int i = 0; i < data.length(); i++) {
                String temp = String.format("%8s", Integer.toBinaryString(data.charAt(i) & 0xFF)).replace(' ', '0');
                binary = binary.concat(temp);
                //System.out.println(temp);
                //System.out.println(i);
            }

            //System.out.println(binary);
            //System.out.println(binary.length());

            for (int i = 0; i < 8 * 8 * CHARACTER_SIZE; i++) { // binary.length()
                if (i % 8 == 0 && i != 0)
                    count++;
                fonts[i / 64].getBitmap()[count % 8][i % 8] = Integer.parseInt(binary.charAt(i) + "") == 1; // 64 bits = (8*8 bitmap size) = 1 font
            }

/*            // prints fonts values in binary
            for (int i = 0; i < CHARACTER_SIZE; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        if ((fonts[i].getBitmap()[j][k]))
                            System.out.print(1);
                        else
                            System.out.print(0);
                    }
                    System.out.println();
                }
                System.out.println();
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            File fontFile = new File("MyBoldFonts.txt");
            Scanner myReader;
            if (fontFile.exists())
                myReader = new Scanner(fontFile);
            else
                return;
            int count = 0;
            String data = "";
            String binary = "";
            while (myReader.hasNextLine()) {
                data = data.concat(myReader.nextLine());
            }

            myReader.close();

            //System.out.println(data);
            //System.out.println(data.length());

            for (int i = 0; i < data.length(); i++) {
                String temp = String.format("%8s", Integer.toBinaryString(data.charAt(i) & 0xFF)).replace(' ', '0');
                binary = binary.concat(temp);
                //System.out.println(temp);
            }

            //System.out.println(binary);
            //System.out.println(binary.length());

            for (int i = 0; i < 8 * 8 * CHARACTER_SIZE; i++) { // binary.length()
                if (i % 8 == 0 && i != 0)
                    count++;
                bold[i / 64].getBitmap()[count % 8][i % 8] = Integer.parseInt(binary.charAt(i) + "") == 1; // 64 bits = (8*8 bitmap size) = 1 font
            }

/*            // prints fonts values in binary
            for (int i = 0; i < CHARACTER_SIZE; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        if ((bold[i].getBitmap()[j][k]))
                            System.out.print(1);
                        else
                            System.out.print(0);
                    }
                    System.out.println();
                }
                System.out.println();
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            File fontFile = new File("MyItalicsFonts.txt");
            Scanner myReader;
            if (fontFile.exists())
                myReader = new Scanner(fontFile);
            else
                return;
            int count = 0;
            String data = "";
            String binary = "";
            while (myReader.hasNextLine()) {
                data = data.concat(myReader.nextLine());
            }
            myReader.close();

            //System.out.println(data);
            //System.out.println(data.length());

            for (int i = 0; i < data.length(); i++) {
                String temp = String.format("%8s", Integer.toBinaryString(data.charAt(i) & 0xFF)).replace(' ', '0');
                binary = binary.concat(temp);
                //System.out.println(temp);
            }

            //System.out.println(binary);
            //System.out.println(binary.length());

            for (int i = 0; i < 8 * 8 * CHARACTER_SIZE; i++) { // binary.length()
                if (i % 8 == 0 && i != 0)
                    count++;
                italics[i / 64].getBitmap()[count % 8][i % 8] = Integer.parseInt(binary.charAt(i) + "") == 1; // 64 bits = (8*8 bitmap size) = 1 font
            }

/*            // prints fonts values in binary
            for (int i = 0; i < CHARACTER_SIZE; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        if ((italics[i].getBitmap()[j][k]))
                            System.out.print(1);
                        else
                            System.out.print(0);
                    }
                    System.out.println();
                }
                System.out.println();
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() throws IOException {

        BufferedWriter output = null;
        try {
            File file = new File(fileDestination);
            output = new BufferedWriter(new FileWriter(file));

            // for text - 1 byte
            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < bytes.length; i++)
                System.out.println(bytes[i]);

            //for color - 3 bytes

            for (int i = 0; i < bytes.length; i++) {
                output.write(bytes[i]);

                int r = (int) Math.round(colors.get(i).getRed() * 255);
                int g = (int) Math.round(colors.get(i).getGreen() * 255);
                int b = (int) Math.round(colors.get(i).getBlue() * 255);

                //System.out.println(r);
                //System.out.println(g);
                //System.out.println(b);
                String hex = String.format("%02x%02x%02x", r, g, b);
                //System.out.println("Hexadecimal String: " + hex);

                String binary = "";
                String bin = "";
                for (int k = 0; k < 6; k++) {
                    int decimal = Integer.parseInt(hex.charAt(k) + "", 16);
                    //System.out.println(decimal);
                    bin = Integer.toBinaryString(decimal);
                    bin = String.format("%4s", bin).replace(" ", "0");
                    binary = binary.concat(bin);
                }

                //System.out.println(binary);
                //System.out.println(binary.length());

                byte[] bytearray = new BigInteger(binary, 2).toByteArray();

                if (bytearray.length > 3) {
/*                    System.out.println(bytearray[1] & 0xFF); // unsigned
                    System.out.println(bytearray[2] & 0xFF); // unsigned
                    System.out.println(bytearray[3] & 0xFF); // unsigned*/

                    output.write(bytearray[1] & 0xFF);
                    output.write(bytearray[2] & 0xFF);
                    output.write(bytearray[3] & 0xFF);
                } else {
                    bytearray = new byte[3];
                    bytearray[0] = Byte.parseByte(binary.substring(0, 8), 2);
                    bytearray[1] = Byte.parseByte(binary.substring(9, 16), 2);
                    bytearray[2] = Byte.parseByte(binary.substring(17, 24), 2);

/*                    System.out.println(bytearray[0] & 0xFF); // unsigned
                    System.out.println(bytearray[1] & 0xFF); // unsigned
                    System.out.println(bytearray[2] & 0xFF); // unsigned*/

                    output.write(bytearray[0] & 0xFF);
                    output.write(bytearray[1] & 0xFF);
                    output.write(bytearray[2] & 0xFF);
                }
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }

    }

    private void readFile() {
        word = "";
        text = "";
        colors.clear();
        canvasX = 5;
        canvasY = 6;
        textCount = 0;
        wordCount = 0;

        //coloring the drawing canvas to color white
        PixelWriter pixelWriter = notepadCanvas.getGraphicsContext2D().getPixelWriter();
        for (int i = 0; i < 387; i++)
            for (int j = 0; j < 454; j++)
                pixelWriter.setColor(i, j, Color.WHITE);


        try {
            Scanner myReader;
            if (SelectedFile != null && SelectedFile.exists())
                myReader = new Scanner(SelectedFile);
            else
                return;
            //System.out.println(SelectedFile.length());
            String data = "";
            data = data.concat(myReader.nextLine());
            while (myReader.hasNextLine()) {
                data = data.concat('\n'+"");
                data = data.concat(myReader.nextLine());
            }
            myReader.close();

            //System.out.println(data);
            //System.out.println("actual file length: " + data.length());

            for (int i = 0; i < data.length(); i = i + 4) {
                drawChar(data.charAt(i), Color.color(data.charAt(i + 1) / 255.0,
                        data.charAt(i + 2) / 255.0,
                        data.charAt(i + 3) / 255.0));
            }

            //System.out.println(text);
            //System.out.println(colors.size());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearBitmap() {
        bitmap[0][0] = false;
        bitmap[0][1] = false;
        bitmap[0][2] = false;
        bitmap[0][3] = false;
        bitmap[0][4] = false;
        bitmap[0][5] = false;
        bitmap[0][6] = false;
        bitmap[0][7] = false;
        bitmap[1][0] = false;
        bitmap[1][1] = false;
        bitmap[1][2] = false;
        bitmap[1][3] = false;
        bitmap[1][4] = false;
        bitmap[1][5] = false;
        bitmap[1][6] = false;
        bitmap[1][7] = false;
        bitmap[2][0] = false;
        bitmap[2][1] = false;
        bitmap[2][2] = false;
        bitmap[2][3] = false;
        bitmap[2][4] = false;
        bitmap[2][5] = false;
        bitmap[2][6] = false;
        bitmap[2][7] = false;
        bitmap[3][0] = false;
        bitmap[3][1] = false;
        bitmap[3][2] = false;
        bitmap[3][3] = false;
        bitmap[3][4] = false;
        bitmap[3][5] = false;
        bitmap[3][6] = false;
        bitmap[3][7] = false;
        bitmap[4][0] = false;
        bitmap[4][1] = false;
        bitmap[4][2] = false;
        bitmap[4][3] = false;
        bitmap[4][4] = false;
        bitmap[4][5] = false;
        bitmap[4][6] = false;
        bitmap[4][7] = false;
        bitmap[5][0] = false;
        bitmap[5][1] = false;
        bitmap[5][2] = false;
        bitmap[5][3] = false;
        bitmap[5][4] = false;
        bitmap[5][5] = false;
        bitmap[5][6] = false;
        bitmap[5][7] = false;
        bitmap[6][0] = false;
        bitmap[6][1] = false;
        bitmap[6][2] = false;
        bitmap[6][3] = false;
        bitmap[6][4] = false;
        bitmap[6][5] = false;
        bitmap[6][6] = false;
        bitmap[6][7] = false;
        bitmap[7][0] = false;
        bitmap[7][1] = false;
        bitmap[7][2] = false;
        bitmap[7][3] = false;
        bitmap[7][4] = false;
        bitmap[7][5] = false;
        bitmap[7][6] = false;
        bitmap[7][7] = false;

        btn1x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn1x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn2x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn2x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn3x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn3x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn4x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn4x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn5x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn5x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn6x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn6x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn7x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn7x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        btn8x1.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x2.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x3.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x4.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x5.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x6.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x7.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");
        btn8x8.setStyle("-fx-background-color: WHITE; -fx-background-radius: 0; -fx-border-color: BLACK; -fx-border-radius: 1px");

        asciiTF.clear();

    }

}