package main.java.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppMainLayout extends Application {

  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 800;

  public static void main(String[] args) throws Exception {
    launch(args);
  }

  public void start(Stage stage) {
    GridPane contentSection = new GridPane();

    Label topLosersHeading = new Label("Top Losers");
    Label topWinnersHeading = new Label("Top Winners");

    VBox topLosersView = new VBox();
    VBox topWinnersView = new VBox();

    populateTopLosersView(topLosersView);

    contentSection.add(topLosersHeading, 0, 0);
    contentSection.add(topLosersView, 0, 1);
    contentSection.add(topWinnersHeading, 0, 2);
    contentSection.add(topWinnersView, 0, 3);

    Scene scene = new Scene(contentSection, WINDOW_WIDTH, WINDOW_HEIGHT);
//    scene.getStylesheets().add(this.getClass().getResource("/css/Base.css").toExternalForm());
    stage.setScene(scene);
    stage.setTitle("Layout Sample");
    stage.show();
  }

  private void populateTopLosersView(VBox topLosersView) {
    for (int i = 0; i < 20; i++) {
      Label topLoserElement = new Label("Top Losers: " + i);
      topLosersView.getChildren().add(topLoserElement);
      topLosersView.setAlignment(Pos.CENTER);
    }
  }
}
