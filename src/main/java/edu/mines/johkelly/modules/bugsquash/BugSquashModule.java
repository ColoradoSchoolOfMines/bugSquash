package edu.mines.johkelly.modules.bugsquash;

import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class BugSquashModule extends ProcessingModule
{
    Logger log = LogManager.getLogger(BugSquashModule.class);

    @Override
    public void setup() {

        size(width, height);
    }

    public void update() {
        log.error("?");
    }

    @Override
    public void draw() {
        update();
        text("Hello World", 100, 100);
    }
}
