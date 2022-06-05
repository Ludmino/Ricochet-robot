package isep.godefroy.ricochet.ricochet_robot;

import javafx.scene.image.Image;
import java.util.*;
import static isep.godefroy.ricochet.ricochet_robot.Game.Status.*;
import static isep.godefroy.ricochet.ricochet_robot.Token.Color.*;
import java.util.Random;

public class Game {
    private static int objectif;
    public static Game context;
    public static final int SIZE = 16;
    private static int[] listObjectif = {11,12,13,14,21,22,23,24,31,32,33,34,41,42,43,44,5};
    Integer[] coin= {1, 2, 3, 4};
    Integer[] typeCoin= {1, 2, 1, 2, 1, 2, 1, 2,};
    //On encode les différents coins
    int[][][][] coinPattern = {{{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0,12, 4, 3, 0, 0}, {0, 0, 0, 1, 0,43, 4, 0}, {0, 2, 34, 0, 3, 0, 0, 0}, {0, 0, 1, 2, 21, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 2, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0,12, 4, 0, 0, 0},{3, 0, 0, 1, 0, 3, 0, 0},{0, 0, 0, 0, 2,21, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{2,34, 0, 0, 0, 0, 3, 0},{0, 1, 0, 0, 0, 0,43, 4},{0, 0, 0, 0, 2, 0, 0, 0}}},

            {{{0, 0, 5, 0, 0, 0, 0, 0},{0, 0, 0, 0, 2, 23, 0, 0},{0, 32, 4, 0, 0, 1, 0, 0},{0, 1, 0, 0, 3, 0, 0, 0},{3, 0, 0, 0, 41, 4, 0, 0},{0, 0, 0, 0, 0, 0, 3, 0},{0, 0, 0, 0, 0, 2, 14, 0},{0, 0, 0, 2, 0, 0, 0, 0}},
                    {{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 2, 14, 0, 3, 0},{3, 0, 0, 0, 1, 2, 32, 0},{0, 0, 0, 0, 0, 0, 0, 3},{0, 3, 0, 0, 0, 0, 0, 5},{0, 41, 4, 0, 0, 0, 0, 0},{0, 0, 0, 23, 4, 0, 0, 0},{0, 0, 0, 1, 2, 0, 0, 0}}},

            {{{0, 0, 0, 3, 0, 0, 0, 0},{0, 0, 0, 44, 4, 0, 0, 0},{0, 0, 0, 0, 0, 0, 3, 0},{0, 0, 0, 0, 0, 2, 22, 0},{2, 13, 0, 0, 0, 0, 0, 0},{3, 1, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 31, 4, 0, 0},{0, 0, 0, 0, 1, 0, 2, 0}},
                    {{0, 0, 0, 3, 0, 0, 0, 0},{0, 0, 0, 44, 4, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{2, 31, 0, 0, 0, 0, 0, 0},{0, 1, 0, 0, 0, 0, 22, 4},{3, 0, 3, 0, 0, 0, 1, 0},{0, 2, 13, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 2, 0, 0}}},

            {{{0, 0, 0, 0, 0, 11, 4, 0},{0, 0, 3, 0, 0, 1, 0, 0},{0, 0, 42, 4, 0, 0, 0, 0},{3, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 3, 0, 0, 0},{0, 0, 0, 2, 33, 0, 0, 0},{0, 0, 0, 0, 0, 2, 24, 0},{0, 0, 0, 0, 2, 0, 1, 0}},
                    {{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{0, 3, 0, 0, 2, 24, 0, 0},{2, 33, 0, 0, 0, 1, 3, 0},{0, 0, 0, 0, 0, 0, 42, 4},{3, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 11, 4, 0, 0, 0},{0, 0, 0, 1, 0, 2, 0, 0}}}};

    private Token selectedRobot;
    public Token getSelectedRobot() { return this.selectedRobot; }
    private Map<Token.Color, Token> robots;
    public Map<Token.Color, Token> getRobots() { return this.robots; }
    // La cible
    private Token target;
    public Token getTarget() { return this.target; }

    //On ajoute les robots au plateau et dans une liste
    public Game() {
        robots = new HashMap<>();
        robots.put(RED, new Token(RED));
        robots.put(GREEN, new Token(GREEN));
        robots.put(BLUE, new Token(BLUE));
        robots.put(YELLOW, new Token(YELLOW));

    }

    //On lance la partie
    public static void start() {
        Game.context = new Game();
        Game.context.setStatus(CHOOSE_ROBOT);
        System.out.println("go");
    }

    private Status status;
    public Status getStatus() { return status; }
    public void setStatus(Status status) {
        this.status = status;
    }

    //On gère les status de la parties
    public enum Status {
        CHOOSE_ROBOT("Cliquez sur le robot à déplacer"),
        CHOOSE_TILE("Cliquez sur la case destination");
        Status(String toolTip) { this.toolTip = toolTip; }
        private String toolTip;
        public String getToolTip() { return this.toolTip; }
    }

    private int[][][] partialBoard = new int[4][][];

    public String[] newBoard(){
        //On tire au sort les coins du plateau qui seront utilisés et on les affiches
        List<Integer> intCoin = Arrays.asList(coin);
        Collections.shuffle(intCoin);
        intCoin.toArray(coin);

        List<Integer> intCoinType = Arrays.asList(typeCoin);
        Collections.shuffle(intCoinType);
        intCoinType.toArray(typeCoin);

        String[] choix = new String[4];
        for (int i=0; i<coin.length;i++){
            choix[i]=("corner"+coin[i]+typeCoin[i]);
            partialBoard[i]=coinPattern[coin[i]-1][typeCoin[i]-1];
        }
        generateBoard();
        return choix;
    }

    private int[][] finalBoard = new int[16][16];

    public void generateBoard(){
        //On utilise les coins 8*8 tirés pour en faire un plateau de 16*16
        partialBoard[0]=rotate(partialBoard[0]);
        partialBoard[1]=rotate(rotate(partialBoard[1]));
        partialBoard[3]=rotate(rotate(rotate(partialBoard[3])));
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++) {
                finalBoard[i][j] = partialBoard[0][i][j];
                finalBoard[i][j+8] = partialBoard[1][i][j];
            }
        }
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++) {
                finalBoard[i+8][j] = partialBoard[2][i][j];
                finalBoard[i+8][j+8] = partialBoard[3][i][j];
            }
        }
        createTiles();
    }

    public static Tile[][] boardTile = new Tile[16][16];

    public void createTiles(){
        //On transforme notre plateau de 16*16 en list bidimensionel d'éléments Tiles qui permettront de gérer collisions et objectifs
        System.out.println(Arrays.deepToString(finalBoard));
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                int haut = 0;
                int bas = 0;
                int gauche = 0;
                int droite = 0;
                int centre = 0;
                int objectif = 0;
                if (i==7 && j ==7 ){
                    haut =1;
                    gauche =1;
                }
                if (i==7 && j ==8 ){
                    haut =1;
                    droite =1;
                }
                if (i==8 && j ==7 ){
                    bas =1;
                    gauche =1;
                }
                if (i==8 && j ==8 ){
                    bas =1;
                    droite =1;
                }
                if (finalBoard[i][j]==1){
                    haut = 1;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else if (finalBoard[i][j]==2){
                    droite = 1;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                }
                else if (finalBoard[i][j]==3){
                    bas = 1;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                }
                else if (finalBoard[i][j]==4){
                    gauche = 1;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else if (finalBoard[i][j]==5){
                    droite = 1;
                    haut =1;
                    objectif = 5;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else if (finalBoard[i][j]==6){
                    droite = 1;
                    bas =1;
                    objectif = 5;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else if (finalBoard[i][j]==7){
                    gauche = 1;
                    bas =1;
                    objectif = 5;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else if (finalBoard[i][j]==8){
                    gauche = 1;
                    haut =1;
                    objectif = 5;
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else if (finalBoard[i][j]!=0){
                    objectif = finalBoard[i][j];
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else {
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                }
            }
        }
    }

    public static Image newObjectif(){
        //On tire au sort un objectif et on le renvoie pour affichage
        Random random = new Random();
        objectif=listObjectif[random.nextInt(17)];
        Image objectifVisuel = new Image(String.valueOf(objectif)+".png");
        return objectifVisuel;
    }

    public int[][] rotate(int[][] coin){
        //Les coins du plateau étant tous encodé avec le mur extérieur en bas à droite, il est nécessaire d'effectuer 1 ou plusieurs
        //rotations pour les utiliser correctement
        //Cette fonction sert à pivoter les matrices 8*8 de 90°
        int[][] newCoin = new int[8][8];
        for (int i = 0; i<coin[0].length;i++){
            for (int j = 0; j<coin[i].length;j++){
                if (coin[7-i][j]==1){
                    newCoin[j][i]=2;
                } else if (coin[7-i][j]==2){
                    newCoin[j][i]=3;
                } else if (coin[7-i][j]==3){
                    newCoin[j][i]=4;
                } else if (coin[7-i][j]==4){
                    newCoin[j][i]=1;
                } else if (coin[7-i][j]==5){
                    newCoin[j][i]=6;
                } else if (coin[7-i][j]==6){
                    newCoin[j][i]=7;
                } else if (coin[7-i][j]==7){
                    newCoin[j][i]=8;
                } else if (coin[7-i][j]==8){
                    newCoin[j][i]=5;
                } else {
                    newCoin[j][i]=coin[7-i][j];
                }
            }
        }
        return newCoin;
    }

    public void processSelectRobot(Token.Color color) {
        //On gère la séléction du robot
        if (this.status == CHOOSE_ROBOT) {
            this.selectedRobot = this.robots.get(color);
            // Action suivante attendue : choisir la case cible
            setStatus(CHOOSE_TILE);
        }
    }

    public String processSelectTile(int col, int lig) {
        //On gère le choix des cases, si un robot a été choisi, on le fait se déplacer
        if (this.status == CHOOSE_TILE) {
            if (
                    (this.selectedRobot.getCol() != col)
                            &&
                            (this.selectedRobot.getLig() != lig)
            ) {
                return "Les déplacements en diagonale sont interdits";
            } else {
                if (this.selectedRobot.getCol()<col){
                    col = deplacementDroite(this.selectedRobot.getLig(),this.selectedRobot.getCol());
                } else if (this.selectedRobot.getCol()>col){
                    col = deplacementGauche(this.selectedRobot.getLig(),this.selectedRobot.getCol());
                } else if (this.selectedRobot.getLig() < lig){
                    lig = deplacementBas(this.selectedRobot.getLig(),this.selectedRobot.getCol());
                } else if (this.selectedRobot.getLig() > lig){
                    lig = deplacementHaut(this.selectedRobot.getLig(),this.selectedRobot.getCol());
                } else {
                    System.out.println("Sur place");
                }
                boardTile[this.selectedRobot.getLig()][this.selectedRobot.getCol()].setCenter(0);
                this.selectedRobot.setPosition(col,lig);
                boardTile[lig][col].setCenter(1);
                // Action suivante attendue : choisir un robot
                setStatus(CHOOSE_ROBOT);

                //On vérifie s'il y a victoire après chaque déplacement
                boolean win = isWin(this.selectedRobot.getLig(),this.selectedRobot.getCol(),this.selectedRobot.getColor());
                if (win){
                    Token.Color[] liCouleur={RED,BLUE,GREEN,YELLOW};
                    for (int i = 0; i < liCouleur.length;i++){
                        Token robot = Game.context.getRobots().get(liCouleur[i]);
                        boardTile[robot.getLig()][robot.getCol()].setCenter(0);
                        boardTile[robot.getOriginalCol()][robot.getOriginalLigne()].setCenter(1);
                    }
                    return "WIN";
                }
                return "MOVE";
            }
        }
        return null;
    }

    public int deplacementBas(int ligne, int col){
        //Fonction de déplacement en bas
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
        //Fonction de déplacement en haut
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
        //Fonction de déplacement à gauche
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
        //Fonction de déplacement à droite
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

    //On ajoute les robots sur le plateau de Tile
    public static void initalizeRobot(int ligne, int col){
        boardTile[ligne][col].setCenter(1);
    }

    public boolean isWin(int ligne, int colonne, Token.Color couleur){
        //On vérifie si la case sur lequel le robot arrive a un objectif qui correspond à l'objectif atteint
        //Si ça correspond, on vérifie ensuite si l'objectif sur lequel le robot est correspond à la couleur du robot
        //Si oui victoire, sinon rien
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
