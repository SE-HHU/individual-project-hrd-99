package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class unity {
    protected int x;
    protected int y;
    protected PImage image;
    public void draw(PApplet app) {
        app.image(this.image, this.x, this.y);
    }

    public unity(int x, int y, PImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
