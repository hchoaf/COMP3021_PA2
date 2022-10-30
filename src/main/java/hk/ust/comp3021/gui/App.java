package hk.ust.comp3021.gui;

import hk.ust.comp3021.game.GameMap;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.component.maplist.MapModel;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartController;
import hk.ust.comp3021.gui.scene.start.StartScene;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The JavaFX application that launches the game.
 */
public class App extends Application {
    private Stage primaryStage;
    private Scene startScene;

    /**
     * Set up the primary stage and show the {@link StartScene}.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.startScene = new StartScene();
        this.startScene.addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, e-> {
            try {
                onOpenMap(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        this.primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
        this.primaryStage.setScene(this.startScene);

        this.primaryStage.show();
        // TODO
    }


    /**
     * Event handler for opening a map.
     * Swith to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     */
    public void onOpenMap(MapEvent event) throws Exception{

        System.out.println("onOpenMap triggered.");
        // TODO
        Scene gameScene = new GameScene(new GameState(event.getModel().gameMap()));
        gameScene.addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame);
        primaryStage.setScene(gameScene);

    }

    /**
     * Event handler for exiting the game.
     * Switch to the {@link StartScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to exiting the game.
     */
    public void onExitGame(ExitEvent event) {
        System.out.println("onExitGame triggered.");
        primaryStage.setScene(this.startScene);
        // primaryStage.setScene(new S)
        // TODO
    }
}
