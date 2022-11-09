package hk.ust.comp3021.gui.component.board;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.gui.utils.Message;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import static hk.ust.comp3021.utils.StringResources.UNDO_QUOTA_TEMPLATE;
import static hk.ust.comp3021.utils.StringResources.UNDO_QUOTA_UNLIMITED;

/**
 * Control logic for a {@link GameBoard}.
 * <p>
 * GameBoardController serves the {@link RenderingEngine} which draws the current game map.
 */
public class GameBoardController implements RenderingEngine, Initializable {
    @FXML
    private GridPane map;

    @FXML
    private Label undoQuota;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Draw the game map in the {@link #map} GridPane.
     *
     * @param state The current game state.
     */
    @Override
    public void render(@NotNull GameState state) {

        // TODO
        for (int x = 0; x < state.getMapMaxWidth(); x++) {
            for (int y = 0; y < state.getMapMaxHeight(); y++) {
                int finalX = x;
                int finalY = y;
                Platform.runLater(() -> {

                    Cell cell = null;
                    try {
                        cell = new Cell();
                        var e = state.getEntity(Position.of(finalX, finalY));
                        String imageFilePath = switch (e) {
                            case Wall ignored1:
                                yield "wall.png";
                            case Box b:
                                if (state.getDestinations().contains(Position.of(finalX, finalY))) {
                                    cell.getController().markAtDestination();
                                }
                                yield String.format("box-%d.png", b.getPlayerId());
                            case Player p:
                                yield String.format("player-%d.png", p.getId());
                            case Empty ignored:
                                if (state.getDestinations().contains(Position.of(finalX, finalY))) {
                                    yield "destination.png";
                                } else {
                                    yield "empty.png";
                                }
                            case null:
                                yield "empty.png";
                        };
                        cell.getController().setImage(new URL("file://components/img/"+imageFilePath));
                        map.add(cell, finalX, finalY);
                        // map.add(new TextField("af"), finalX, finalY);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            }
        }
        Platform.runLater(() -> {
            if (state.getUndoQuota().isPresent()) {
                undoQuota.setText(String.format(UNDO_QUOTA_TEMPLATE, state.getUndoQuota().get()));
            } else {
                undoQuota.setText(String.format(UNDO_QUOTA_TEMPLATE, UNDO_QUOTA_UNLIMITED));
            }
        });




    }

    /**
     * Display a message via a dialog.
     *
     * @param content The message
     */
    @Override
    public void message(@NotNull String content) {
        Platform.runLater(() -> Message.info("Sokoban", content));
    }
}
