package gremlins;

import processing.core.PImage;

public class Fireball extends whoCanMove {

    private boolean isexist;

    public Fireball(int x, int y, PImage image, Direction d) {
        super(x, y, image);
        direction = d;
        isexist = true;
    }

    public boolean isIsexist() {
        return isexist;
    }

    @Override
    public void tick() {

        if (isCollosionwithSlime()) {
            isexist = false;
        } else if (isCollosionwithGremlin()) {
            isexist = false;
        } else if (isCollosionwithBrickwall()) {
            isexist = false;
        }

        if (direction == Direction.down && isHavewallDown()) {
            isexist = false;
        } else if (direction == Direction.up && isHavewallup()) {
            isexist = false;
        } else if (direction == Direction.left && isHavewallLeft()) {
            isexist = false;
        } else if (direction == Direction.right && isHavewallRight()) {
            isexist = false;
        }

        if (direction == Direction.down && !isHavewallDown()) {
            y += 4;
        } else if (direction == Direction.up && !isHavewallup()) {
            y -= 4;
        } else if (direction == Direction.left && !isHavewallLeft()) {
            x -= 4;
        } else if (direction == Direction.right && !isHavewallRight()) {
            x += 4;
        }
    }

    private boolean isCollosionwithSlime() {
        for (int i = 0; i < App.slimes.size(); i++) {
            if (y <= App.slimes.get(i).y + 20 && App.slimes.get(i).y <= y + 20 && x <= App.slimes.get(i).x + 20 && App.slimes.get(i).x <= x + 20) {
                isexist = false;
                App.slimes.get(i).setIsexist(false);
                return true;
            }
        }
        return false;
    }

    private boolean isCollosionwithGremlin() {
        for (int i = 0; i < App.gremlins.size(); i++) {
            if (y <= App.gremlins.get(i).y + 20 && App.gremlins.get(i).y <= y + 20 && x <= App.gremlins.get(i).x + 20 && App.gremlins.get(i).x <= x + 20) {
                isexist = false;
                App.gremlins.get(i).reset();
                return true;
            }
        }
        return false;
    }

    private boolean isCollosionwithBrickwall() {
        for (int i = 0; i < App.brickwalls.size(); i++) {
            if (y <= App.brickwalls.get(i).y + 20 && App.brickwalls.get(i).y <= y + 20 && x <= App.brickwalls.get(i).x + 20 && App.brickwalls.get(i).x <= x + 20) {
                if (((direction == Direction.down || direction == Direction.up) && x == App.brickwalls.get(i).x) || ((direction == Direction.left || direction == Direction.right) && y == App.brickwalls.get(i).y)) {
                    isexist = false;
                    App.brickwalls.get(i).setIsdestroy(true);
                    App.brickwalls.get(i).destroy();
                    return true;
                }
            }
        }
        return false;
    }

}
