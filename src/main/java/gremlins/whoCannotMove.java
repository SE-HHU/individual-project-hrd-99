package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class whoCannotMove extends unity{
   protected boolean isexist;

   public whoCannotMove(int x, int y, PImage image) {
      super(x, y, image);
      isexist=true;
   }
}
