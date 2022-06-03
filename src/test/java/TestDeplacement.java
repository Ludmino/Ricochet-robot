import isep.godefroy.ricochet.ricochet_robot.Tile;
import isep.godefroy.ricochet.ricochet_robot.Token;
import org.junit.jupiter.api.Test;
import static isep.godefroy.ricochet.ricochet_robot.Token.Color.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDeplacement {
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
        int oldColonne;
        Token robot = new Token(RED);
        generateBoard();
        oldColonne = robot.getCol();
        int newColonne = deplacementGauche(robot.getLig(), robot.getCol());
        //On vérifie si la nouvelle colonne est plus à gauche que l'ancienne, sinon c'est que l'ancienne colonne est déjà la plus à gauche
        assertTrue((oldColonne>newColonne) || oldColonne==0);
    }

    @Test
    public void deplacementDroiteTest(){
        int oldColonne;
        Token robot = new Token(RED);
        generateBoard();
        oldColonne = robot.getCol();
        int newColonne = deplacementDroite(robot.getLig(), robot.getCol());
        //On vérifie si la nouvelle colonne est plus à gauche que l'ancienne, sinon c'est que l'ancienne colonne est déjà la plus à droite
        assertTrue((oldColonne<newColonne) || oldColonne==15);
    }

    @Test
    public void deplacementBasTest(){
        int oldLigne;
        Token robot = new Token(RED);
        generateBoard();
        oldLigne = robot.getCol();
        int newLigne = deplacementBas(robot.getLig(), robot.getCol());
        //On vérifie si la nouvelle ligne est plus à gauche que l'ancienne, sinon c'est que l'ancienne ligne est déjà la plus en bas
        assertTrue((oldLigne<newLigne) || oldLigne==15);
    }

    @Test
    public void deplacementHaut(){
        int oldLigne;
        Token robot = new Token(RED);
        generateBoard();
        oldLigne = robot.getCol();
        int newLigne = deplacementHaut(robot.getLig(), robot.getCol());
        //On vérifie si la nouvelle ligne est plus à gauche que l'ancienne, sinon c'est que l'ancienne ligne est déjà la plus en bas
        assertTrue((oldLigne>newLigne) || oldLigne==0);
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
