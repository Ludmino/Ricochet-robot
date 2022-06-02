package isep.godefroy.ricochet.ricochet_robot;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;

import static isep.godefroy.ricochet.ricochet_robot.Token.Color.*;

public class HelloController {
    private static final Integer STARTTIME = 15;
    public final int TILE_SIZE = 44;
    private Timeline timeline;
    private int player1score = 0;
    private int player2score = 0;

    private Integer timeSeconds = STARTTIME;

    @FXML
    private Pane winScreen;
    @FXML
    private Button player2win;

    @FXML
    public GridPane boardPane;

    @FXML
    private Button buttonStart;

    @FXML
    private Label timer;
    @FXML
    private Pane playerName;
    @FXML
    private Pane menuScreen;
    @FXML
    private Label player2Label;
    @FXML
    private TextField player2Name;
    @FXML
    private TextField player1Name;
    @FXML
    private CheckBox enablePlayer2;
    @FXML
    private SplitPane gameScreen;
    @FXML
    private ImageView imgObjectif;

    @FXML
    private Label player1Display;
    @FXML
    private Label player2Display;

    @FXML
    private AnchorPane gamePane;

    @FXML
    private Pane corner1;
    @FXML
    private Pane corner2;
    @FXML
    private Pane corner3;
    @FXML
    private Pane corner4;

    @FXML
    protected void onStartButtonClick() {
        buttonStart.setDisable(true);
        buttonStart.setVisible(false);
        playerName.setVisible(true);
        playerName.setDisable(false);
    }

    @FXML
    protected void addPlayer2(){
        boolean disable = enablePlayer2.isSelected();
        player2Label.setDisable(!disable);
        player2Name.setDisable(!disable);
    }

    @FXML
    protected void startGame(){
        menuScreen.setDisable(true);
        menuScreen.setVisible(false);
        gameScreen.setDisable(false);
        gameScreen.setVisible(true);
        player1Display.setText("Joueur "+player1Name.getText()+" : "+player1score);
        if (enablePlayer2.isSelected()){
            player2Display.setVisible(true);
            player2Display.setDisable(false);
            player2Display.setText("Joueur "+player2Name.getText()+" : "+player2score);
        }
        timer.setText(timeSeconds.toString());

        visualBoard();
    }

    protected void visualBoard(){
        Game game = new Game();
        game.start();
        String[] Selection = game.newBoard();
        corner1.getStyleClass().add(Selection[0]);
        corner2.getStyleClass().add(Selection[1]);
        corner3.getStyleClass().add(Selection[2]);
        corner4.getStyleClass().add(Selection[3]);
        Image tile = new Image("cell.png", TILE_SIZE, TILE_SIZE, false, true);
        randomObjectif();
        // ... "cell.png" doit être placé à la racine de "resources/" (sinon PB)
        for (int col = 0; col < Game.SIZE; col ++) {
            for (int lig = 0; lig < Game.SIZE; lig++) {
                ImageView tileGui = new ImageView(tile);
                tileGui.setOpacity(0.1);
                final int lambdaCol = col;
                final int lambdaLig = lig;
                tileGui.setOnMouseClicked
                        (event -> {
                            String status = Game.context.processSelectTile
                                    (lambdaCol, lambdaLig);
                            if ( "MOVE".equals(status)) {
                                updateSelectedRobotPosition();
                            } else if ( "WIN".equals(status)){
                                win();
                            }
                            else if (status != null) {
                                System.out.println("Déplacement en diagonale interdit");
                            }
                        });
                boardPane.add(tileGui, col, lig);
            }
        }

        // Ajout des pièces
        addRobot(RED);
        addRobot(GREEN);
        addRobot(BLUE);
        addRobot(YELLOW);
    }
    public void randomObjectif(){
        imgObjectif.setImage(Game.newObjectif());
    }

    private void addRobot(Token.Color color) {
        Token robot = Game.context.getRobots().get(color);
        ImageView robotGui = new ImageView( new Image(
                color.name() + "_robot.png",
                TILE_SIZE, TILE_SIZE, false, true
        ) );
        robotGui.setOpacity(1);
        robotGui.setOnMouseClicked
                (event -> Game.context.processSelectRobot(color));
        boardPane.add(robotGui, robot.getCol(), robot.getLig());
        Game.initalizeRobot(robot.getLig(),robot.getCol());
        // Association de l' "ImageView" avec le robot stocké dans le jeu
        robot.setGui(robotGui);
    }

    private void updateSelectedRobotPosition() {
        Token robot = Game.context.getSelectedRobot();
        GridPane.setConstraints(robot.getGui(), robot.getCol(), robot.getLig());
    }

    public void timer30(ActionEvent actionEvent){
    }

    public void win() {
        if (enablePlayer2.isSelected()) {
            player2win.setVisible(true);
            player2win.setDisable(false);
        }
        winScreen.setDisable(false);
        winScreen.setVisible(true);
        gameScreen.setVisible(false);
        gameScreen.setDisable(true);
    }

    public void player1won(ActionEvent actionEvent) {
        player1score+=1;
        gameScreen.setDisable(false);
        gameScreen.setVisible(true);
        winScreen.setVisible(false);
        winScreen.setDisable(true);
        player1Display.setText("Joueur "+player1Name.getText()+" : "+player1score);
        randomObjectif();
        resetPosition();
    }

    public void player2won(ActionEvent actionEvent) {
        player2score+=1;
        player2Display.setText("Joueur "+player2Name.getText()+" : "+player2score);
        gameScreen.setDisable(false);
        gameScreen.setVisible(true);
        winScreen.setVisible(false);
        winScreen.setDisable(true);
        randomObjectif();
        resetPosition();
    }

    public void resetPosition(){
        Token.Color[] liCouleur={RED,BLUE,GREEN,YELLOW};
        for (int i = 0; i < liCouleur.length;i++){
            Token robot = Game.context.getRobots().get(liCouleur[i]);
            robot.resetPosition();
            GridPane.setConstraints(robot.getGui(), robot.getCol(), robot.getLig());
        }
    }
}