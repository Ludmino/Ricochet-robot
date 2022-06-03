import isep.godefroy.ricochet.ricochet_robot.Tile;
import isep.godefroy.ricochet.ricochet_robot.Token;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static isep.godefroy.ricochet.ricochet_robot.Token.Color.*;
import static isep.godefroy.ricochet.ricochet_robot.Token.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestVictoire {
    public static Tile[][] boardTile = new Tile[16][16];

    public static void generateBoard(){
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                int haut = 0;
                int bas = 0;
                int gauche = 0;
                int droite = 0;
                int centre = 0;
                int objectif = 0;
                boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
            }
        }
    }

    private static int objectif;

    public int deplacementHaut(int ligne, int col){
        if (boardTile[ligne][col].getUp()==1){
            return ligne;
        }
        for (int i = ligne-1; i>=0;i--){
            if (boardTile[i][col].getCenter() == 1 || boardTile[i][col].getDown()==1){
                return i+1;
            } else if (boardTile[i][col].getUp()==1){
                return i;
            }
        }
        return 0;
    }

    public int deplacementGauche(int ligne, int col){
        if (boardTile[ligne][col].getLeft()==1){
            return col;
        }
        for (int i = col-1; i>=0;i--){
            if (boardTile[ligne][i].getCenter() == 1 || boardTile[ligne][i].getRight()==1){
                return i+1;
            } else if (boardTile[ligne][i].getLeft()==1){
                return i;
            }
        }
        return 0;
    }

    @Test
    public void victoryTest(){
        objectif=21;
        generateBoard();
        boardTile[0][0].setObjectif(21);
        Token robot = new Token(RED);
        robot.setPosition(deplacementHaut(robot.getLig(),robot.getCol()), robot.getLig());
        robot.setPosition(robot.getCol(), deplacementGauche(robot.getLig(),robot.getCol()));
        boolean win = isWin(robot.getLig(),robot.getCol(),robot.getColor());
        assertTrue(win);
    }

    @Test
    public void LooseTest(){
        objectif=11;
        generateBoard();
        boardTile[0][0].setObjectif(11);
        Token robot = new Token(BLUE);
        robot.setPosition(deplacementHaut(robot.getLig(),robot.getCol()), robot.getLig());
        robot.setPosition(robot.getCol(), deplacementGauche(robot.getLig(),robot.getCol()));
        boolean win = isWin(robot.getLig(),robot.getCol(),robot.getColor());
        assertTrue(!win);
    }

    public boolean isWin(int ligne, int colonne, Token.Color couleur){
        Integer[] objR = {11,21,31,41,5};
        List<Integer> objRouge = Arrays.asList(objR);
        Integer[] objB = {12,22,32,42,5};
        List<Integer> objBleu = Arrays.asList(objB);
        Integer[] objV = {13,23,33,43,5};
        List<Integer> objVert = Arrays.asList(objV);
        Integer[] objJ = {14,24,34,44,5};
        List<Integer> objJaune = Arrays.asList(objJ);
        if (objectif==boardTile[ligne][colonne].getObjectif()){
            if (objRouge.contains(objectif) && couleur==RED){
                System.out.println("Victoire Rouge");
                return true;
            } else
            if (objBleu.contains(objectif) && couleur==BLUE){
                System.out.println("Victoire Bleu");
                return true;
            } else
            if (objVert.contains(objectif) && couleur==GREEN){
                System.out.println("Victoire Vert");
                return true;
            } else
            if (objJaune.contains(objectif) && couleur==YELLOW){
                System.out.println("Victoire Jaune");
                return true;
            } else {
                System.out.println("Mauvais pion");
            }
        }
        return false;
    }

}
