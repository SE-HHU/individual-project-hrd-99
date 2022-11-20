package gremlins;

import processing.core.PImage;

import java.util.Random;

public class Gremlin extends whoCanMove implements attack {

    public static double enemy_cooldown = 3;
    public Gremlin(int x, int y, PImage image) {
        super(x, y, image);
        int temp = App.randomGenerator.nextInt(4) % (4 - 1 + 1) + 1;
        if (temp == 1) {
            ori = Direction.down;
            direction = Direction.down;
        } else if (temp == 2) {
            ori = Direction.up;
            direction = Direction.up;
        } else if (temp == 3) {
            ori = Direction.left;
            direction = Direction.left;
        } else {
            ori = Direction.right;
            direction = Direction.right;
        }
    }


    @Override
    public void tick() {
        if (getChoice() == 1) {
            changeDirection();
        } else {
            changeDirection2();
        }

        if (direction == Direction.down && !isHavewallDown()) {
            y++;
        } else if (direction == Direction.up && !isHavewallup()) {
            y--;
        } else if (direction == Direction.left && !isHavewallLeft()) {
            x--;
        } else if (direction == Direction.right && !isHavewallRight()) {
            x++;
        }
        if (y <= App.w.y + 20 && App.w.y <= y + 20 && x <= App.w.x + 20 && App.w.x <= x + 20) {
            reset();
            App.lives--;
        }
    }

    public void changeDirection() {
        if (isHavewallDown() && direction == Direction.down) {
            randomDirection(Direction.down);
        }
        if (isHavewallup() && direction == Direction.up) {
            randomDirection(Direction.up);
        }
        if (isHavewallLeft() && direction == Direction.left) {
            randomDirection(Direction.left);
        }
        if (isHavewallRight() && direction == Direction.right) {
            randomDirection(Direction.right);
        }
    }

    public void changeDirection2() {
        if (isHavewallDown() && direction == Direction.down) {
            randomDirection(Direction.down, Direction.up);
        }
        if (isHavewallup() && direction == Direction.up) {
            randomDirection(Direction.up, Direction.down);
        }
        if (isHavewallLeft() && direction == Direction.left) {
            randomDirection(Direction.left, Direction.right);
        }
        if (isHavewallRight() && direction == Direction.right) {
            randomDirection(Direction.right, Direction.left);
        }
    }

    public void randomDirection(Direction d, Direction d2) {
        while (true) {
            int temp = App.randomGenerator.nextInt(4) % (4 - 1 + 1) + 1;
            if (temp == 1 && !isHavewallDown()) {
                ori = Direction.down;
                direction = Direction.down;
            } else if (temp == 2 && !isHavewallup()) {
                ori = Direction.up;
                direction = Direction.up;
            } else if (temp == 3 && !isHavewallLeft()) {
                ori = Direction.left;
                direction = Direction.left;
            } else if (temp == 4 && !isHavewallRight()) {
                ori = Direction.right;
                direction = Direction.right;
            }
            if (this.direction != d && this.direction != d2) {
                break;
            }
        }
    }

    public void randomDirection(Direction d) {
        while (this.direction == d) {
            int temp = App.randomGenerator.nextInt(4) % (4 - 1 + 1) + 1;
            if (temp == 1 && !isHavewallDown()) {
                ori = Direction.down;
                direction = Direction.down;
            } else if (temp == 2 && !isHavewallup()) {
                ori = Direction.up;
                direction = Direction.up;
            } else if (temp == 3 && !isHavewallLeft()) {
                ori = Direction.left;
                direction = Direction.left;
            } else if (temp == 4 && !isHavewallRight()) {
                ori = Direction.right;
                direction = Direction.right;
            }
        }
    }

    private int getChoice() {
        int count = 0;
        if (!isHavewallRight()) count++;
        if (!isHavewallup()) count++;
        if (!isHavewallLeft()) count++;
        if (!isHavewallDown()) count++;
        return count;
    }


    @Override
    public void attack() {
        App.slimes.add(new Slime(fixslimeX(), fixslimeY(), App.slime, this.direction));
    }


    private int fixslimeX() {
        int temp = x;
        if (x % 20 != 0 && direction == Direction.left) {
            temp = x - x % 20;
        }
        if (x % 20 != 0 && direction == Direction.right) {
            temp = x + (20 - x % 20);
        }
        return temp;
    }

    private int fixslimeY() {
        int temp = y;
        if (y % 20 != 0 && direction == Direction.up) {
            temp = y - y % 20;
        }
        if (y % 20 != 0 && direction == Direction.down) {
            temp = y + (20 - y % 20);
        }
        return temp;
    }

    public void reset() {
        while (true) {
            int min=0;
            int max=App.space.size()-1;
            int index = min + (int) (Math.random() * (max - min));
            String[] temp = App.space.get(index).split(" ");
            x=Integer.parseInt(temp[0]);
            y=Integer.parseInt(temp[1]);
            if(Math.sqrt((x-App.w.x) *(x-App.w.x)+(y-App.w.y)*(y-App.w.y))>=20*20)
                break;
        }


    }
}
