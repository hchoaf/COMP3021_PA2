package hk.ust.comp3021.gui.scene.start;

import hk.ust.comp3021.game.GameMap;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.component.maplist.MapList;
import hk.ust.comp3021.gui.component.maplist.MapModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Control logic for {@link  StartScene}.
 */
public class StartController implements Initializable {
    @FXML
    private MapList mapList;

    @FXML
    private Button deleteButton;

    @FXML
    private Button openButton;

    private URL location;

    private ResourceBundle resources;

    /**
     * Initialize the controller.
     * Load the built-in maps to {@link this#mapList}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        this.location = location;
        this.resources = resources;

    }

    /**
     * Event handler for the open button.
     * Display a file chooser, load the selected map and add to {@link this#mapList}.
     * If the map is invalid or cannot be loaded, display an error message.
     *
     * @param event Event data related to clicking the button.
     */
    @FXML
    private void onLoadMapBtnClicked(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File targetFile = fileChooser.showOpenDialog(new Stage());

        if (!targetFile.getName().substring(targetFile.getName().lastIndexOf(".")).equals(".map")) {
            this.showInvalidMapAlert();
        } else {
            var pathString = "file://" + targetFile.getPath();
            try {
                mapList.getItems().remove(mapList.getItems().stream().filter(m -> m.file().toString().equals(targetFile.getPath())).findFirst().orElse(null));
                this.mapList.getItems().add(MapModel.load(new URL(pathString)));
            } catch (Exception e) {
                this.showInvalidMapAlert();
            }

        }
        // TODO

    }

    /**
     * Handle the event when the delete button is clicked.
     * Delete the selected map from the {@link this#mapList}.
     */
    @FXML
    public void onDeleteMapBtnClicked() {
        System.out.println("onDeleteMapBtnClicked");
        var selectedMapModel = mapList.getSelectionModel().getSelectedItem();
        if (selectedMapModel != null) {
            mapList.getItems().remove(selectedMapModel);
        }

        // TODO
    }

    /**
     * Handle the event when the map open button is clicked.
     * Retrieve the selected map from the {@link this#mapList}.
     * Fire an {@link MapEvent} so that the {@link hk.ust.comp3021.gui.App} can handle it and switch to the game scene.
     */
    @FXML
    public void onOpenMapBtnClicked() throws IOException {
        System.out.println("onOpenMapBtnClicked");
        var selectedMapModel = mapList.getSelectionModel().getSelectedItem();
        if (selectedMapModel != null) {
            System.out.println(selectedMapModel.name());
            openButton.fireEvent(new MapEvent(MapEvent.OPEN_MAP_EVENT_TYPE, selectedMapModel));

            // Event.fireEvent(mapEvent.getTarget(), mapEvent);
        }
        // mapEvent.
        // System.out.println("hhahahaha");
        // TODO
    }

    /**
     * Handle the event when a file is dragged over.
     *
     * @param event The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragOver(DragEvent event) {
        // TODO
        // System.out.println("onDragOver");
        if (event.getDragboard().hasFiles()) {

            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    /**
     * Handle the event when the map file is dragged to this app.
     * <p>
     * Multiple files should be supported.
     * Display error message if some dragged files are invalid.
     * All valid maps should be loaded.
     *
     * @param dragEvent The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        // TODO
        System.out.println("onDragDropped");
        Dragboard db = dragEvent.getDragboard();
        System.out.println(db);

        if (db.hasFiles()) {
            db.getFiles().forEach(targetFile -> {
                if (!targetFile.getName().substring(targetFile.getName().lastIndexOf(".")).equals(".map")) {
                    this.showInvalidMapAlert();
                } else {
                    var pathString = "file://" + targetFile.getPath();
                    try {
                        mapList.getItems().remove(mapList.getItems().stream().filter(mapModel -> mapModel.file().toString().equals(targetFile.getPath())).findFirst().orElse(null));
                        this.mapList.getItems().add(MapModel.load(new URL(pathString)));
                    } catch (IOException e ) {
                        this.showInvalidMapAlert();
                    }
                }

            });
        }

        dragEvent.setDropCompleted(true);
        dragEvent.consume();



    }

    public void showInvalidMapAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Map File");
        alert.showAndWait();
    }

}