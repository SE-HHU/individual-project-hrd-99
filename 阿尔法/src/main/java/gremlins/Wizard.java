package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;


public class Wizard extends whoCanMove implements attack {

    private boolean isLeft;
    public int cd;
    public static double wizard_cooldown=180;
    public boolean cooldowntime;


    public Wizard(int x, int y, PImage image) {
        super(x, y, image);
        direction = Direction.rest;
        ori = Direction.right;
        isLeft = false;
        cd=0;
        cooldowntime=false;
    }

    public void draw(PApplet app, PImage image) {
        app.image(image, this.getX(), this.getY());
    }


    @Override
    public void tick() {
        if (direction == Direction.down && !isHavewallDown() && x % 20 == 0 && y % 20 == 0) {
            y += 2;
        } else if (direction == Direction.up && !isHavewallup() && x % 20 == 0 && y % 20 == 0) {
            y -= 2;
        } else if (direction == Direction.left && !isHavewallLeft() && x % 20 == 0 && y % 20 == 0) {
            x -= 2;
        } else if (direction == Direction.right && !isHavewallRight() && x % 20 == 0 && y % 20 == 0) {
            x += 2;
        } else {
            fix();
        }
        if(cooldowntime){
            cd++;
            if(cd>=wizard_cooldown*App.FPS)
            {
                cd=0;
                cooldowntime=false;
            }
        }
    }

    public void pressUp() {
        if (x % 20 == 0)
            ori = Direction.up;
        this.direction = Direction.up;
        isLeft = false;
    }

    public void pressDown() {
        if (x % 20 == 0)
            ori = Direction.down;
        this.direction = Direction.down;
        isLeft = false;
    }

    public void pressLeft() {
        if (y % 20 == 0)
            ori = Direction.left;
        this.direction = Direction.left;
        isLeft = true;
    }

    public void pressRight() {
        if (y % 20 == 0)
            ori = Direction.right;
        this.direction = Direction.right;
        isLeft = false;
    }

    public void keyReleased() {
        this.direction = Direction.rest;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isLeft() {
        return isLeft;
    }


    public void fix() {
        if (this.x % 20 != 0 && ori == Direction.right) x += 2;
        else if (this.y % 20 != 0 && ori == Direction.down) y += 2;
        else if (this.x % 20 != 0 && ori == Direction.left) x -= 2;
        else if (this.y % 20 != 0 && ori == Direction.up) y -= 2;
    }

    @Override
    public void attack() {
        if(cd>=wizard_cooldown*App.FPS) {
            cd = 0;
            cooldowntime = false;
        }
        if(cd==0) {
            App.fireballs.add(new Fireball(fixX(), fixY(), App.fireball, ori));
            cd++;
            cooldowntime=true;
        }

    }

    public int fixX() {
        int temp = x;
        if (x % 20 != 0 && direction == Direction.left) {
            temp = x - x % 20;
        }
        if (x % 20 != 0 && direction == Direction.right) {
            temp = x + (20 - x % 20);
        }
        return temp;
    }

    public int fixY() {
        int temp = y;
        if (y % 20 != 0 && direction == Direction.up) {
            temp = y - y % 20;
        }
        if (y % 20 != 0 && direction == Direction.down) {
            temp = y + (20 - y % 20);
        }
        return temp;
    }
}
