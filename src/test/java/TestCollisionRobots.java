import isep.godefroy.ricochet.ricochet_robot.Tile;
import isep.godefroy.ricochet.ricochet_robot.Token;
import org.junit.jupiter.api.Test;

import static isep.godefroy.ricochet.ricochet_robot.Token.Color.GREEN;
import static isep.godefroy.ricochet.ricochet_robot.Token.Color.RED;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCollisionRobots {
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
    public void deplacementGaucheTest(){
        //On va déplacer les deux robots dans le coin en haut à gauche, seul le rouge ira si la collision marche bien
        Token robotRouge = new Token(RED);
        generateBoard();

        int oldRougeLigne=robotRouge.getLig();
        int oldRougeCol=robotRouge.getCol();
        boardTile[robotRouge.getLig()][robotRouge.getCol()].setCenter(1);
        robotRouge.setPosition(deplacementGauche(robotRouge.getLig(), robotRouge.getCol()), robotRouge.getLig());
        boardTile[oldRougeLigne][oldRougeCol].setCenter(0);
        boardTile[robotRouge.getLig()][robotRouge.getCol()].setCenter(1);
        oldRougeLigne=robotRouge.getLig();
        oldRougeCol=robotRouge.getCol();
        robotRouge.setPosition(robotRouge.getCol(),deplacementHaut(robotRouge.getLig(), robotRouge.getCol()));
        boardTile[oldRougeLigne][oldRougeCol].setCenter(0);
        boardTile[robotRouge.getLig()][robotRouge.getCol()].setCenter(1);
        oldRougeLigne=robotRouge.getLig();
        oldRougeCol=robotRouge.getCol();

        Token robotVert = new Token(GREEN);
        int oldVertLigne=robotVert.getLig();
        int oldVertCol=robotVert.getCol();
        boardTile[robotVert.getLig()][robotRouge.getCol()].setCenter(1);
        robotVert.setPosition(deplacementGauche(robotVert.getLig(), robotVert.getCol()), robotVert.getLig());
        boardTile[oldVertLigne][oldVertCol].setCenter(0);
        boardTile[robotVert.getLig()][robotVert.getCol()].setCenter(1);
        oldVertLigne=robotVert.getLig();
        oldVertCol=robotVert.getCol();
        robotVert.setPosition(robotVert.getCol(),deplacementHaut(robotVert.getLig(), robotVert.getCol()));
        boardTile[oldVertLigne][oldVertCol].setCenter(0);
        boardTile[robotVert.getLig()][robotVert.getCol()].setCenter(1);
        oldVertLigne=robotVert.getLig();
        oldVertCol=robotVert.getCol();
        //On vérifie que le robot Vert n'est pas en (0,0), il y a déjà le rouge donc bien collision
        assertTrue((robotVert.getLig()!=0) || robotVert.getCol()!=0);
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
}
