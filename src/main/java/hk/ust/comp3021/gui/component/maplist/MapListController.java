package hk.ust.comp3021.gui.component.maplist;

import hk.ust.comp3021.game.GameMap;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * Controller logic for {@link  MapList}.
 */
public class MapListController implements Initializable {
    @FXML
    private ListView<MapModel> list;

    /**
     * Initialize the controller.
     * You should customize the items in the list view here.
     * Set the item in the {@link MapList} to be {@link MapListCell}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     * @see <a href="https://docs.oracle.com/javafx/2/ui_controls/list-view.htm">JavaFX ListView</a>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // list.setCellFactory(cell -> new MapListCell());
        System.out.printf("MapListController initialized with URL: %s \n", location.toString());
        try {

            var mapModelOne = MapModel.load(new URL("file:///Users/hscho/Desktop/College/4_1/COMP3021/PA2/src/main/resources/map00.map"));
            var mapModelTwo = MapModel.load(new URL("file:///Users/hscho/Desktop/College/4_1/COMP3021/PA2/src/main/resources/map01.map"));
            list.getItems().addAll(mapModelOne, mapModelTwo);
            // list.setCellFactory(cell -> new MapListCell());
            list.getItems().sort(new Comparator<MapModel>() {
                @Override
                public int compare(MapModel o1, MapModel o2) {
                    return o1.loadAt().compareTo(o2.loadAt());
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO
    }


}
