package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class BugSquashModule extends ProcessingModule
{
    private static final int extMargin = 300;
    private static final int expFPS = 30;

    private Logger log = LogManager.getLogger(BugSquashModule.class);

    private List<Bug> bugs = new ArrayList<Bug>();
    private PImage bugSprite;

    int c = 0;

    @Override
    public void setup() {
        size(getWidth(), getHeight());
        bugSprite = loadImage("bug.png");
        frameRate(expFPS);
    }

    public void update() {
        c++;
        if(c%(expFPS)==0){
            spawnBug();
            c = 0;
        }
        List<Bug> deadBugs = new ArrayList<Bug>();
        for(Bug b : bugs){
            b.update();
            if(!inBounds(b)){
                deadBugs.add(b);
            }
        }
        for(Bug b : deadBugs){
            bugs.remove(b);
        }
    }

    private boolean inBounds(Bug b){
        return b.x > 0-extMargin && b.y > 0-extMargin && b.x < width+extMargin && b.y < height+extMargin;
    }

    private void spawnBug(){
        int x = (int) (random(1) * (width));
        int y = (int) (random(1) * (height));
        float heading = (float) (random(1) * Math.PI / 2);
        // random boundaries are irrelevant -- just a convenient way to do 50/50 chance
        // On the vertical rails
        if(random(1) > .5){
            // On the right
            if(random(1) > .5){
                float rot = (float) (heading - 3 * Math.PI / 4);
                bugs.add(new Bug(width+extMargin, y, rot, bugSprite));
            }
            // On the left
            else{
                float rot = (float) (heading + Math.PI / 4);
                bugs.add(new Bug(-extMargin, y, rot, bugSprite));
            }
        }
        // On the horizontal rails
        else{
            // On the bottom
            if(random(1) > .5){
                float rot = (float) (heading - Math.PI / 4);
                bugs.add(new Bug(x, height+extMargin, rot, bugSprite));
            }
            // On the top
            else{
                float rot = (float) (heading + 3 * Math.PI / 4);
                bugs.add(new Bug(x, -extMargin, rot, bugSprite));
            }
        }
    }

    @Override
    public void draw() {
        background(color(256, 256, 256));
        update();
        for(Bug b : bugs){
            b.draw(this);
        }
    }
}
