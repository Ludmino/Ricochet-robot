package isep.godefroy.ricochet.ricochet_robot;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Token {

    //On reprend le token proposé dans l'amorce, on ajoute juste une position original, et on vérifie que le pion ne puisse pas
    //apparaitre au centre du plateau
    private int originalLigne;
    private int originalCol;

    public Token(Color color) {
        this.color = color;
        int[] pos = generateRandom();
        setPosition( pos[0], pos[1]);
        originalLigne = pos[0];
        originalCol = pos[1];
    }

    public void resetPosition(){
        setPosition(originalLigne,originalCol);
    }

    private int[] generateRandom(){
        //On empêche le robot d'apparaitre au centre
        Random random = new Random();
        int[] spawn = new int[2];
        int ligne = random.nextInt(Game.SIZE);
        int colonne = random.nextInt(Game.SIZE);
        while ((ligne==7 || ligne == 8) && (colonne==7 || colonne==8)){
            ligne = random.nextInt(Game.SIZE);
            colonne = random.nextInt(Game.SIZE);
        }
        spawn[0]=ligne;
        spawn[1]=colonne;
        return spawn;
    }

    private Color color;
    public Color getColor() { return this.color; }

    public int col;
    public int lig;

    public void setPosition(int col, int lig) {
        this.col = col;
        this.lig = lig;
    }

    public int getOriginalLigne() {
        return originalLigne;
    }
    public int getOriginalCol() {
        return originalCol;
    }
    public int getCol() { return col; }
    public int getLig() { return lig; }

    // Composant "JFX" associé
    Node gui;
    public void setGui(ImageView gui) { this.gui = gui; }
    public Node getGui() { return gui; }

    // * ---

    public enum Color {RED, GREEN, BLUE, YELLOW}

}