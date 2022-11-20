package gremlins;

import processing.core.PImage;

public abstract class whoCanMove extends unity{
    protected Direction ori;
    protected Direction direction;
    public whoCanMove(int x, int y, PImage image) {
        super(x, y, image);
    }

    private boolean isCollosionwithWAll(whoCannotMove wvm) {
        return y <= wvm.y + 20 && wvm.y <= y + 20 && x <= wvm.x + 20 && wvm.x <= x + 20;
    }

    protected boolean isHavewallDown() {

        for (Brickwall bw : App.brickwalls) {
            if (bw.isexist) {
                if (isCollosionwithWAll(bw) && bw.y - 20 >= this.y && bw.x == this.x) {
                    return true;
                }
            }
        }
        for (Stonewall sw : App.stonewalls) {
            if (sw.isexist) {
                if (isCollosionwithWAll(sw) && sw.y - 20 >= this.y && sw.x == this.x) return true;
            }
        }
        return false;
    }

    protected boolean isHavewallup() {
        for (Brickwall bw : App.brickwalls) {
            if (bw.isexist) {
                if (isCollosionwithWAll(bw) && bw.y + 20 <= this.y && bw.x == this.x) return true;

            }
        }
        for (Stonewall sw : App.stonewalls) {
            if (sw.isexist) {
                if (isCollosionwithWAll(sw) && sw.y + 20 <= this.y && sw.x == this.x) return true;
            }
        }
        return false;
    }

    protected boolean isHavewallRight() {
        for (Brickwall bw : App.brickwalls) {
            if (bw.isexist) {
                if (isCollosionwithWAll(bw) && bw.x - 20 >= this.x && bw.y == this.y)
                    return true;

            }
        }
        for (Stonewall sw : App.stonewalls) {
            if (sw.isexist) {
                if (isCollosionwithWAll(sw) && sw.x - 20 >= this.x && sw.y == this.y)
                    return true;
            }
        }
        return false;
    }

    protected boolean isHavewallLeft() {
        for (Brickwall bw : App.brickwalls) {
            if (bw.isexist) {
                if (isCollosionwithWAll(bw) && bw.x + 20 <= this.x && bw.y == this.y) return true;
            }
        }
        for (Stonewall sw : App.stonewalls) {
            if (sw.isexist) {
                if (isCollosionwithWAll(sw) && sw.x + 20 <= this.x && sw.y == this.y) return true;
            }
        }
        return false;
    }

    public abstract void tick();
}
