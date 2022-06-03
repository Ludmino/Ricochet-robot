import isep.godefroy.ricochet.ricochet_robot.Tile;
import isep.godefroy.ricochet.ricochet_robot.Token;
import org.junit.jupiter.api.Test;

import static isep.godefroy.ricochet.ricochet_robot.Token.Color.RED;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDeplacementObstacle {
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

    @Test
    public void deplacementGaucheTestObstacle(){
        Token robot = new Token(RED);
        generateBoard();
        int oldColonne = deplacementDroite(robot.getLig(), robot.getCol());
        robot.setPosition(oldColonne, robot.getLig());
        for (int i=0;i<=15;i++){
            boardTile[i][7].setRight(1);
        }
        int newColonne = deplacementGauche(robot.getLig(), robot.getCol());
        //On vérifie que le robot est bien bloqué au centre du plateau
        assertTrue((newColonne==8));
    }

    @Test
    public void deplacementDroiteTestObstacle(){
        Token robot = new Token(RED);
        generateBoard();
        int oldColonne = deplacementGauche(robot.getLig(), robot.getCol());
        robot.setPosition(oldColonne, robot.getLig());
        for (int i=0;i<=15;i++){
            boardTile[i][7].setRight(1);
        }
        int newColonne = deplacementDroite(robot.getLig(), robot.getCol());
        //On vérifie que le robot est bien bloqué au centre du plateau
        assertTrue((newColonne==7));
    }

    @Test
    public void deplacementBasTestObstacle(){
        Token robot = new Token(RED);
        generateBoard();
        int oldLigne = deplacementHaut(robot.getLig(), robot.getCol());
        robot.setPosition(robot.getCol(), oldLigne);
        for (int i=0;i<=15;i++){
            boardTile[7][i].setUp(1);
        }
        int newLine = deplacementBas(robot.getLig(), robot.getCol());
        //On vérifie que le robot est bien bloqué au centre du plateau
        assertTrue((newLine==6));
    }

    @Test
    public void deplacementHautTestObstacle(){
        Token robot = new Token(RED);
        generateBoard();
        int oldLigne = deplacementBas(robot.getLig(), robot.getCol());
        robot.setPosition(robot.getCol(), oldLigne);
        for (int i=0;i<=15;i++){
            boardTile[7][i].setUp(1);
        }
        int newLine = deplacementHaut(robot.getLig(), robot.getCol());
        //On vérifie que le robot est bien bloqué au centre du plateau
        assertTrue((newLine==7));
    }


    public int deplacementBas(int ligne, int col){
        if (boardTile[ligne][col].getDown()==1){
            return ligne;
        }
        for (int i = ligne+1; i<=15;i++){
            if (boardTile[i][col].getCenter() == 1 || boardTile[i][col].getUp()==1){
                return i-1;
            } else if (boardTile[i][col].getDown()==1){
                return i;
            }
        }
        return 15;
    }

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

    public int deplacementDroite(int ligne, int col){
        if (boardTile[ligne][col].getRight()==1){
            return col;
        }
        for (int i = col+1; i<=15;i++){
            if (boardTile[ligne][i].getCenter() == 1 || boardTile[ligne][i].getLeft()==1){
                return i-1;
            } else if (boardTile[ligne][i].getRight()==1){
                return i;
            }
        }
        return 15;
    }
}
