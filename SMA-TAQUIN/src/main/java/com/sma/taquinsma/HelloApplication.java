package com.sma.taquinsma;

import com.sma.taquinsma.model.Game;
import com.sma.taquinsma.model.sma.agent.Agent;
import com.sma.taquinsma.model.sma.world.World;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class HelloApplication extends Application implements Observer {
    final float sceneWidth = 600;
    final float sceneHeight = 600;
    GridPane grid = new GridPane();
    Game game;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //Game
        game = new Game(13, new World(5));
 /*       Button button = new Button("Rechercher");
        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });*/

        Group group = new Group();
        group.getChildren().addAll(grid);


        Scene scene = new Scene(group, sceneWidth, sceneHeight);

        primaryStage.setTitle("Jeu du taquin");
        primaryStage.setScene(scene);

        game.addObserver(this);
        game.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void update(Observable o, Object arg) {
        //maj
        Platform.runLater(new Runnable() {
            public void run() {
                grid.getChildren().clear();
                System.out.println("Update");
                Random rand = new Random();
                Color[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.RED};

                for (int row = 0; row < game.getWorld().size; row++) {
                    for (int col = 0; col < game.getWorld().size; col++) {
                        Rectangle agent = new Rectangle();
                        agent.setWidth(sceneWidth / game.getWorld().size);
                        agent.setHeight(sceneHeight / game.getWorld().size);
                        agent.setFill(Color.WHITE);
                        grid.getChildren().addAll(agent);
                    }
                }

                for(Agent a : game.getAgents()) {
                    int n = rand.nextInt(4);
                    Text text = new Text(Integer.toString(a.id));
                    text.setScaleX(2);
                    text.setScaleY(2);
                    text.setFill(Color.WHITE);

                    Rectangle agent = new Rectangle();
                    agent.setWidth(sceneWidth / game.getWorld().size);
                    agent.setHeight(sceneHeight / game.getWorld().size);
                    agent.setFill(Color.BLUE);

                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(agent, text);

                    GridPane.setRowIndex(stack, a.perception.currentPos.x);
                    GridPane.setColumnIndex(stack, a.perception.currentPos.y);

                    grid.getChildren().addAll(stack);
                }
            }
        });


    }
}