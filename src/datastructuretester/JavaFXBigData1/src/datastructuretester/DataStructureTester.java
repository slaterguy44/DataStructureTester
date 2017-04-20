package datastructuretester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static sort.ComplexSort.mergeSort;
import sort.SimpleSorts;
import static sort.SimpleSorts.bubbleSort;
import static sort.SimpleSorts.selectionSort;
import static sort.SimpleSorts.insertionSort;
import static sort.ComplexSort.quickSort;
/**
 * Add a selection sort for homework A JavaFX 8 program to help experiment with
 * data structures and algorithms.
 *
 * @author John Phillips
 */
public class DataStructureTester extends Application {

    Stage pStage;
    TextArea taStatus;
    ScrollPane spStatus;
    TextArea taData;
    ScrollPane spData;

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;

        taData = new TextArea();
        spData = new ScrollPane(taData);
        spData.setFitToWidth(true);
        spData.setFitToHeight(true);

        taStatus = new TextArea();
        spStatus = new ScrollPane(taStatus);
        spStatus.setFitToWidth(true);
        spStatus.setPrefViewportHeight(50);
//        spStatus.setFitToHeight(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(myMenuBar());
        borderPane.setCenter(spData);
        borderPane.setBottom(spStatus);

//        Scene scene = new Scene(borderPane, 800, 500);
        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Data Structures");
        primaryStage.setScene(scene);

//        primaryStage.setMaximized(true);
//        primaryStage.setFullScreen(true);
        primaryStage.hide();
        primaryStage.show();
    }

    /**
     * Displays a menu for this application.
     *
     * FYI: menu accelerator key codes are listed at:
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html
     *
     * @return
     */
    public MenuBar myMenuBar() {
        MenuBar myBar = new MenuBar();
        final Menu fileMenu = new Menu("File");
        final Menu dataMenu = new Menu("Data");
        final Menu sortMenu = new Menu("Sort");
        final Menu searchMenu = new Menu("Search");
        final Menu helpMenu = new Menu("Help");

        myBar.getMenus().addAll(fileMenu, dataMenu, sortMenu, searchMenu, helpMenu);

        /**
         * *********************************************************************
         * File Menu Section
         */
        MenuItem newCanvas = new MenuItem("New");
        newCanvas.setOnAction((ActionEvent e) -> {
            taData.clear();
        });
        fileMenu.getItems().add(newCanvas);

        MenuItem open = new MenuItem("Open");
        open.setOnAction((ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(pStage);
            if (file != null) {
                readFile(file);
            }
        });
        fileMenu.getItems().add(open);

        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent e) -> {

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(pStage);
            if (file != null) {
                writeFile(file);
            }
        });
        fileMenu.getItems().add(save);

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exit);

        /**
         * *********************************************************************
         * Data Menu Section
         */
        MenuItem miGenerateIntegers = new MenuItem("Generate Integers");
        miGenerateIntegers.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            String newLine = "\n";
            for (int i = 0; i < 10000; i++) {
                sb.append(i).append(newLine);
            }
            taData.setText(sb.toString());
        });
        dataMenu.getItems().add(miGenerateIntegers);

        MenuItem miRandom = new MenuItem("Randomize Data");
        miRandom.setOnAction(e -> {
            int[] nums = text2IntArray(taData.getText());
            Random gen = new Random();

            for (int i = 0; i < nums.length; i++) {
                int temp = nums[i];
                int j = gen.nextInt(nums.length);
                nums[i] = nums[j];
                nums[j] = temp;
            }
            taData.setText(intArray2Text(nums));
        });
        dataMenu.getItems().add(miRandom);

        /**
         * *********************************************************************
         * Sort Menu Section
         */
        MenuItem miBubbleSortAsc = new MenuItem("Bubble Sort Ascending");
        miBubbleSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            bubbleSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miBubbleSortAsc);

        MenuItem miBubbleSortDsc = new MenuItem("Bubble Sort Descending");
        miBubbleSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            bubbleSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });

        sortMenu.getItems().add(miBubbleSortDsc);

        MenuItem miSelectionSortAsc = new MenuItem("Selection Sort Ascending");
        miSelectionSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            selectionSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miSelectionSortAsc);

        MenuItem miSelectionSortDsc = new MenuItem("Selection Sort Descending");
        miSelectionSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            selectionSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });

        sortMenu.getItems().add(miSelectionSortDsc);

        MenuItem miMergeSortAsc = new MenuItem("Merge Sort Ascending");
            miMergeSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            mergeSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miMergeSortAsc);

        MenuItem miMergeSortDsc = new MenuItem("Merge Sort Descending");
        miMergeSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            mergeSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miMergeSortDsc);

        MenuItem miInsertionSortAsc = new MenuItem("Insertion Sort Ascending");
        miInsertionSortAsc.setOnAction(e ->{
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            insertionSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
            
        });
        sortMenu.getItems().add(miInsertionSortAsc);
        
        MenuItem miInsertionSortDsc = new MenuItem("Insertion Sort Descending");
        miInsertionSortDsc.setOnAction(e ->{
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            insertionSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
            
        });
        sortMenu.getItems().add(miInsertionSortDsc);
        
        
        
        MenuItem miQuickSortAsc = new MenuItem("Quick Sort Ascending");
        miQuickSortAsc.setOnAction(e ->{
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            quickSort(nums, "A");
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miQuickSortAsc);
        
        MenuItem miQuickSortDsc = new MenuItem("Quick Sort Descending");
        miQuickSortDsc.setOnAction(e ->{
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            quickSort(nums, "D");
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miQuickSortDsc);
        
        /**
         * *********************************************************************
         * Search Menu Section
         */
        MenuItem miSequentialSearch = new MenuItem("Sequential Search");
        searchMenu.getItems().add(miSequentialSearch);

        MenuItem miBinarySearch = new MenuItem("Binary Search");
        searchMenu.getItems().add(miBinarySearch);

        /**
         * *********************************************************************
         * Help Menu Section
         */
        MenuItem about = new MenuItem("About");
        about.setOnAction((ActionEvent e) -> {
            String message = "DATA STRUCTURES AND ALGORITHMS\n";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setTitle("About");
            alert.setHeaderText("v1.0 by John Phillips");
            alert.showAndWait();
        });
        helpMenu.getItems().add(about);

        return myBar;
    }

    /**
     * *************************************************************************
     * File helper methods
     */
    private void readFile(File myFile) {
        int y = 0;
        try (Scanner sc = new Scanner(myFile)) {
            taData.clear();
            while (sc.hasNextLine()) {
                taData.appendText(sc.nextLine() + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataStructureTester.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeFile(File myFile) {
        try (PrintWriter writer = new PrintWriter(myFile)) {
            for (String line : taData.getText().split("\\n")) {
                writer.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(DataStructureTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int[] text2IntArray(String s) {
        Scanner sc = new Scanner(s);
        int n = s.split("\n").length;
        int[] nums = new int[n];
        for (int i = 0; sc.hasNextInt(); i++) {
            nums[i] = sc.nextInt();
        }
        return nums;
    }

    public static String intArray2Text(int[] a) {
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        for (int value : a) {
            sb.append(Integer.toString(value)).append(newLine);
        }
        return sb.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
