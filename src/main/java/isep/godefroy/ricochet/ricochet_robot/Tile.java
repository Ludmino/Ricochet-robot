package isep.godefroy.ricochet.ricochet_robot;

public class Tile {
    protected int objectif;
    protected int up;
    protected int down;
    protected int left;
    protected int right;
    protected int center;

    //Up, down, left, right = murs en haut, bas, gauche, droite
    //centre = robot au centre
    //Objectif = objectif de la case
    public Tile(int up, int down, int left, int right, int center, int objectif) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.center = center;
        this.objectif = objectif;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int getObjectif() {
        return objectif;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getCenter() {
        return center;
    }



    public void setObjectif(int objectif) {
        this.objectif = objectif;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
