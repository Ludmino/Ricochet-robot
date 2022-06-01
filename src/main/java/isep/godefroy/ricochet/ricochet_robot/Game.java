package isep.godefroy.ricochet.ricochet_robot;

import java.util.*;

import static isep.godefroy.ricochet.ricochet_robot.Game.Status.*;
import static isep.godefroy.ricochet.ricochet_robot.Token.Color.*;

public class Game {

    public static Game context;
    public static final int SIZE = 16;
    Integer[] coin= {1, 2, 3, 4};
    Integer[] typeCoin= {1, 2, 1, 2, 1, 2, 1, 2,};
    int[][][][] coinPattern = {{{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0,12, 4, 3, 0, 0}, {0, 0, 0, 0, 0,43, 4, 0}, {0, 2, 34, 0, 3, 0, 0, 0}, {0, 0, 1, 2, 21, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 2, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0,12, 4, 0, 0, 0},{3, 0, 0, 1, 0, 3, 0, 0},{0, 0, 0, 0, 2,21, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{2,34, 0, 0, 0, 0, 3, 0},{0, 1, 0, 0, 0, 0,43, 4},{0, 0, 0, 0, 2, 0, 0, 0}}},

            {{{0, 0, 5, 0, 0, 0, 0, 0},{0, 0, 0, 0, 2, 23, 0, 0},{0, 32, 4, 0, 0, 1, 0, 0},{0, 1, 0, 0, 3, 0, 0, 0},{3, 0, 0, 0, 41, 4, 0, 0},{0, 0, 0, 0, 0, 0, 3, 0},{0, 0, 0, 0, 0, 2, 14, 0},{0, 0, 0, 2, 0, 0, 0, 0}},
                    {{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 2, 14, 0, 3, 0},{3, 0, 0, 0, 1, 2, 32, 0},{0, 0, 0, 0, 0, 0, 0, 3},{0, 3, 0, 0, 0, 0, 0, 5},{0, 41, 4, 0, 0, 0, 0, 0},{0, 0, 0, 23, 4, 0, 0, 0},{0, 0, 0, 1, 2, 0, 0, 0}}},

            {{{0, 0, 0, 3, 0, 0, 0, 0},{0, 0, 0, 44, 4, 0, 0, 0},{0, 0, 0, 0, 0, 0, 3, 0},{0, 0, 0, 0, 0, 2, 22, 0},{2, 13, 0, 0, 0, 0, 0, 0},{3, 1, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 31, 4, 0, 0},{0, 0, 0, 0, 1, 0, 2, 0}},
                    {{0, 0, 0, 3, 0, 0, 0, 0},{0, 0, 0, 44, 4, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{2, 31, 0, 0, 0, 0, 0, 0},{0, 1, 0, 0, 0, 0, 22, 4},{3, 0, 3, 0, 0, 0, 1, 0},{0, 2, 13, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 2, 0, 0}}},

            {{{0, 0, 0, 0, 0, 11, 4, 0},{0, 0, 3, 0, 0, 1, 0, 0},{0, 0, 42, 4, 0, 0, 0, 0},{3, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 3, 0, 0, 0},{0, 0, 0, 2, 33, 0, 0, 0},{0, 0, 0, 0, 0, 2, 24, 0},{0, 0, 0, 0, 2, 0, 1, 0}},
                    {{0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0},{0, 3, 0, 0, 2, 24, 0, 0},{2, 33, 0, 0, 0, 0, 3, 0},{0, 0, 0, 0, 0, 0, 42, 2},{3, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 11, 4, 0, 0, 0},{0, 0, 0, 1, 0, 2, 0, 0}}}};

    private Token selectedRobot;
    public Token getSelectedRobot() { return this.selectedRobot; }
    private Map<Token.Color, Token> robots;
    public Map<Token.Color, Token> getRobots() { return this.robots; }
    // La cible
    private Token target;
    public Token getTarget() { return this.target; }

    Game() {

        robots = new HashMap<>();
        robots.put(RED, new Token(RED));
        robots.put(GREEN, new Token(GREEN));
        robots.put(BLUE, new Token(BLUE));
        robots.put(YELLOW, new Token(YELLOW));

        Token.Color[] colors = Token.Color.values();
        int randomColorIndex = ( new Random() ).nextInt( colors.length );
        target = new Token( colors[randomColorIndex] );

    }

    public static void start() {
        if (Game.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        Game.context = new Game();
        Game.context.setStatus(CHOOSE_ROBOT);
        System.out.println("go");
    }

    private Status status;
    public Status getStatus() { return status; }
    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        CHOOSE_ROBOT("Cliquez sur le robot à déplacer"),
        CHOOSE_TILE("Cliquez sur la case destination");
        Status(String toolTip) { this.toolTip = toolTip; }
        private String toolTip;
        public String getToolTip() { return this.toolTip; }
    }

    private int[][][] partialBoard = new int[4][][];

    public String[] newBoard(){
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
                } else if (finalBoard[i][j]!=0){
                    objectif = finalBoard[i][j];
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                } else {
                    boardTile[i][j] = new Tile(haut,bas,gauche,droite,centre,objectif);
                }
            }
        }
    }

    public int[][] rotate(int[][] coin){
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
                } else {
                    newCoin[j][i]=coin[7-i][j];
                }
            }
        }
        return newCoin;
    }

    public void processSelectRobot(Token.Color color) {
        if (this.status == CHOOSE_ROBOT) {
            this.selectedRobot = this.robots.get(color);
            // Action suivante attendue : choisir la case cible
            setStatus(CHOOSE_TILE);
        }
    }

    public String processSelectTile(int col, int lig) {
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
                return "MOVE";
            }
        }
        return null;
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

    public static void initalizeRobot(int ligne, int col){
        boardTile[ligne][col].setCenter(1);
    }
}
