package gremlins;

import com.google.common.collect.Multimap;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public PFont font;

    public String configPath;

    public PImage brickwall;
    public PImage stonewall;

    public PImage gremlin;

    public PImage wizard0;

    public PImage wizard1;

    public PImage wizard2;
    public PImage wizard3;

    public PImage door;

    public static PImage slime;

    public static PImage brickwall_destroy0;
    public static PImage brickwall_destroy1;
    public static PImage brickwall_destroy2;
    public static PImage brickwall_destroy3;


    public static PImage fireball;
    public double enemy_cooldown = 3;
    public double enemy_time;

    public static int lives;


    public static ArrayList<Brickwall> brickwalls = new ArrayList<>();
    public static ArrayList<Stonewall> stonewalls = new ArrayList<>();

    public static ArrayList<Gremlin> gremlins = new ArrayList<>();

    public static ArrayList<Slime> slimes = new ArrayList<>();

    public static ArrayList<Fireball> fireballs = new ArrayList<>();

    public static ArrayList<String> space = new ArrayList<>();

    public static Wizard w;

    public static Door d;

    public static int levelN;

    public static int levelAmount;

    public static int readNum;

    public JSONObject conf;

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);
        levelN = 1;
        levelAmount = 2;
        readNum = 1;
        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.wizard0 = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.wizard1 = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizard2 = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizard3 = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.door = loadImage(this.getClass().getResource("door.png").getPath().replace("%20", " "));
        //this.font=loadFont("frostbite.ttf");
        this.brickwall_destroy0 = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.brickwall_destroy1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.brickwall_destroy2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.brickwall_destroy3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        this.font = createFont("frostbite", 18, true);
        textFont(font);
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.conf = conf;
        lives = conf.getInt("lives");
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        switch (this.keyCode) {
            case 37:
                w.pressLeft();
                break;
            case 38:
                w.pressUp();
                break;
            case 39:
                w.pressRight();
                break;
            case 40:
                w.pressDown();
                break;
            case 32:
                w.attack();
                break;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {
        w.keyReleased();
    }


    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        switch (levelN) {
            case 1:
                level1();
                break;
            case 2:
                level2();
                break;
            default:
                break;
        }
        // Then draw all the game objects
    }


    public void readtxt(String filename) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line;
            int x = 0, y = 0;
            slimes.clear();
            gremlins.clear();
            brickwalls.clear();
            stonewalls.clear();
            fireballs.clear();
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    switch (line.charAt(i)) {
                        case 'X':
                            stonewalls.add(new Stonewall(x, y, stonewall));
                            break;
                        case 'B':
                            brickwalls.add(new Brickwall(x, y, brickwall));
                            break;
                        case 'G':
                            gremlins.add(new Gremlin(x, y, gremlin));
                            break;
                        case 'W':
                            w = new Wizard(x, y, wizard1);
                            break;
                        case 'E':
                            d = new Door(x, y, door);
                            break;
                        case ' ':
                            space.add(x + " " + y);
                            break;
                    }
                    x += 20;
                }
                y += 20;
                x = 0;
            }
            readNum++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void drawBrickwall() {
        for (int i = 0; i < brickwalls.size(); i++) {
            if (brickwalls.get(i).isexist) {
                brickwalls.get(i).destroy();
                brickwalls.get(i).draw(this);
            } else {
                brickwalls.remove(brickwalls.get(i));
            }
        }
    }

    public void drawStonewall() {
        for (Stonewall sw : stonewalls) {
            sw.draw(this);
        }
    }

    public void drawGremlin() {
        for (Gremlin g : gremlins) {
            g.draw(this);
        }
    }

    public void drawWizard() {
        if (w.getDirection() == Direction.left) {
            w.draw(this, wizard0);
        } else if (w.getDirection() == Direction.right) {
            w.draw(this, wizard1);
        } else if (w.getDirection() == Direction.up) {
            w.draw(this, wizard2);
        } else if (w.getDirection() == Direction.down) {
            w.draw(this, wizard3);
        } else {
            if (!w.isLeft()) w.draw(this, wizard1);
            else w.draw(this, wizard0);
        }
    }

    public void gremlinTick() {
        for (int i = 0; i < gremlins.size(); i++) {
            gremlins.get(i).tick();
        }
    }

    public void gremlinAttack() {
        enemy_time++;
        if (enemy_time % (Gremlin.enemy_cooldown * FPS) == 0) {
            for (Gremlin g : gremlins) {
                g.attack();
            }
        }
    }

    public void drawSlime() {
        for (Slime s : slimes) {
            if (s.isIsexist())
                s.draw(this);
        }
    }

    public void slimesTick() {
        for (int i = 0; i < slimes.size(); i++) {
            if (slimes.get(i).isIsexist())
                slimes.get(i).tick();
            else slimes.remove(slimes.get(i));
        }
    }

    public void drawfireball() {
        for (Fireball f : fireballs) {
            if (f.isIsexist())
                f.draw(this);
        }
    }

    public void fireballsTick() {
        for (int i = 0; i < fireballs.size(); i++) {
            if (fireballs.get(i).isIsexist()) {
                fireballs.get(i).tick();
            } else fireballs.remove(fireballs.get(i));
        }
    }

    public void drawCondition() {
        textFont(font, 18);
        text("Lives:", 10, 695);
        int tempX = 45;
        for (int i = 0; i < lives; i++) {
            tempX += 20;
            this.image(wizard1, tempX, 680);
        }
        text("Level " + levelN + "/" + levelAmount, 200, 695);
        if (w.cd != 0)
            text("Charge: " + String.format("%.2f", (double) w.cd / ((double) (Wizard.wizard_cooldown * FPS)) * 100) + "%", 500, 695);
    }

    public void gameover() {
        if (lives <= 0) {
            background(191, 153, 114);
            textFont(font, 60);
            text("Game over", 210, 360);
        }
    }

    public boolean gamewin() {
        if (isCollosion(d, w) && levelN == levelAmount) {
            background(191, 153, 114);
            textFont(font, 60);
            text("You win", 240, 360);
            return true;
        }
        if (isCollosion(d, w) && levelN < levelAmount) {
            levelN++;
        }
        return false;
    }

    public static boolean isCollosion(unity u1, unity u2) {
        return u1.y <= u2.y + 20 && u2.y <= u1.y + 20 && u1.x <= u2.x + 20 && u2.x <= u1.x + 20;
    }

    public void level1() {
        background(191, 153, 114);

        if (levelN == 1 && readNum == 1) {
            readtxt(conf.getJSONArray("levels").get(0).toString().substring(15, 25));
            Wizard.wizard_cooldown = Double.parseDouble(conf.getJSONArray("levels").get(0).toString().substring(72, 78));
            Gremlin.enemy_cooldown = Double.parseDouble(conf.getJSONArray("levels").get(0).toString().substring(48, 49));
        }

        w.tick();

        gremlinAttack();

        gremlinTick();

        slimesTick();

        fireballsTick();

        drawSlime();

        drawfireball();

        drawBrickwall();

        drawStonewall();

        d.draw(this);

        drawGremlin();

        drawWizard();

        drawCondition();

        gameover();

        gamewin();
    }

    public void level2() {
        background(191, 153, 114);
        if (levelN == 2 && readNum == 2) {
            readtxt(conf.getJSONArray("levels").get(1).toString().substring(15, 25));
            Wizard.wizard_cooldown = Double.parseDouble(conf.getJSONArray("levels").get(1).toString().substring(72, 74));
            Gremlin.enemy_cooldown = Double.parseDouble(conf.getJSONArray("levels").get(1).toString().substring(48, 49));
        }

        if (!gamewin()) {

            w.tick();

            gremlinAttack();

            gremlinTick();

            slimesTick();

            fireballsTick();

            drawSlime();

            drawfireball();

            drawBrickwall();

            drawStonewall();

            d.draw(this);

            drawGremlin();

            drawWizard();

            drawCondition();

            gameover();

        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
