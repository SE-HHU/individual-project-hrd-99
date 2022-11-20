package gremlins;

import processing.core.PImage;

public class Brickwall extends whoCannotMove{
    private int cd=0;
    private boolean isdestroy=false;

    public boolean isIsdestroy() {
        return isdestroy;
    }

    public void setIsdestroy(boolean isdestroy) {
        this.isdestroy = isdestroy;
    }

    public Brickwall(int x, int y, PImage image) {
        super(x, y, image);
    }

    public void destroy() {
        if(isdestroy&&isexist)
        {
            if(cd<4)this.image=App.brickwall_destroy0;
            if(cd>=4&&cd<8)this.image=App.brickwall_destroy1;
            if(cd>=8&&cd<12)this.image=App.brickwall_destroy2;
            if(cd>12)this.image=App.brickwall_destroy3;
            if(cd>16){
                isdestroy=false;
                isexist=false;
            }
            cd++;
        }
    }
}
