package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.gui.component.maplist.MapModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Control logic for {@link MovementButtonGroup}.
 */
public class MovementButtonGroupController implements Initializable {
    @FXML
    private GridPane playerControl;

    @FXML
    private ImageView playerImage;

    private Player player = null;


    /**
     * Sets the player controller by the button group.
     *
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The URL to the profile image of the player.
     *
     * @param url The URL.
     */
    public void setPlayerImage(URL url) {

        this.playerImage.setImage(new Image(url.toExternalForm()));
        // TODO
    }

    @FXML
    private void moveUp() {
        System.out.printf("Player %d moveUp\n", player.getId());
        ControlPanelController.actionObservableList.add(new Move.Up(player.getId()));

        // TODO
    }

    @FXML
    private void moveDown() {
        // TODO
        System.out.printf("Player %d moveDown\n", player.getId());
        ControlPanelController.actionObservableList.add(new Move.Down(player.getId()));
    }

    @FXML
    private void moveLeft() {
        // TODO
        System.out.printf("Player %d moveLeft\n", player.getId());
        ControlPanelController.actionObservableList.add(new Move.Left(player.getId()));
    }

    @FXML
    private void moveRight() {
        // TODO
        System.out.printf("Player %d moveRight\n", player.getId());
        ControlPanelController.actionObservableList.add(new Move.Right(player.getId()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
}
