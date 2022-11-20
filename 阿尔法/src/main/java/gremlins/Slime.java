package gremlins;

import processing.core.PImage;

public class Slime extends whoCanMove {
    private boolean isexist;

    public boolean isIsexist() {
        return isexist;
    }

    public void setIsexist(boolean isexist) {
        this.isexist = isexist;
    }

    public Slime(int x, int y, PImage image, Direction d) {
        super(x, y, image);
        this.direction = d;
        isexist = true;

    }

    @Override
    public void tick() {

        if (direction == Direction.down && isHavewallDown()) {
            isexist = false;
        } else if (direction == Direction.up && isHavewallup()) {
            isexist = false;
        } else if (direction == Direction.left && isHavewallLeft()) {
            isexist = false;
        } else if (direction == Direction.right && isHavewallRight()) {
            isexist = false;
        } else if (isCollosionwithPlayer()) {
            if (isexist) {
                App.lives--;
                isexist = false;
            }
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

    private boolean isCollosionwithPlayer() {
        return y <= App.w.y + 20 && App.w.y <= y + 20 && x <= App.w.x + 20 && App.w.x <= x + 20;
    }

}
