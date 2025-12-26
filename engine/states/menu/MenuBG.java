package engine.states.menu;

import engine.Main;
import engine.Utility;
import org.newdawn.slick.*;
import ui.display.Images;

public class MenuBG
{
    private Image background;
    private Image animatedBackground;

    final float SPEED = .15f;
    final float X_SCALE = 1.25f;
    final float Y_SCALE = 1.25f;
    final int SCREENSHOT_TIME = 10 * 60;
    final int MAIN_SCREEN_TIME = 0; //30 * 60;
    final float FADE_TIME = 40;

    int switchToAnimationTimer;
    int resetScreenshotTimer;

    float x;
    float y;

    float xAnim;
    float yAnim;
    float minX;
    float minY;
    float maxX;
    float maxY;

    float xSpeed;
    float ySpeed;

    boolean animationMode;


    public MenuBG()
    {
        minX = -(X_SCALE - 1) * Main.getScreenWidth();
        minY = -(Y_SCALE - 1) * Main.getScreenHeight();



        maxX = 0;
        maxY = 0;

        xSpeed = SPEED;
        ySpeed = SPEED;

        reset();
    }



    public void reset()
    {
        background = Images.bgPurpleBattle.getScaledCopy((int) (Main.getScreenWidth() * 1.3), (int) (Main.getScreenHeight() * 1.1f));
        animatedBackground = Images.getRandomScreenshot().getScaledCopy((int) (Main.getScreenWidth() * X_SCALE), (int) (Main.getScreenHeight() * Y_SCALE));
        resetScreenshotTimer = 0;

        // For starter menu
        x = -(1.3f - 1) * Main.getScreenWidth() * .5f;
        y =  -(1.1f - 1) * Main.getScreenHeight();

        // Skew slightly to the right due to menus
        xAnim = (maxX + minX) * .55f;
        yAnim = (maxY + minY) * .5f;

        ySpeed = Utility.random(-SPEED, SPEED);
    }

    public void update()
    {
        if(switchToAnimationTimer < MAIN_SCREEN_TIME)
        {
            switchToAnimationTimer++;
            x -= .1;
            y += .05f;
        }
        else
        {
            resetScreenshotTimer++;
            animationMode = true;

            xAnim += xSpeed;
            yAnim += ySpeed;

            if(resetScreenshotTimer == SCREENSHOT_TIME)
            {
                reset();
            }
//            if(xAnim <= minX || xAnim >= maxX)
//            {
//                reset();
//            }
//
//            if(yAnim <= minY || yAnim >= maxY)
//            {
//                reset();
//            }
        }
    }

    public void render(Graphics g)
    {
        if(!animationMode && background != null)
        {
            float timeLeft = MAIN_SCREEN_TIME - switchToAnimationTimer;
            if (timeLeft <= FADE_TIME)
            {
                float timeLeftPercent = timeLeft / FADE_TIME;
                background.draw(x, y, new Color(1, 1, 1, timeLeftPercent));
            }
            else
            {
                background.draw(x, y);
            }
        }
        else if(animationMode && animatedBackground != null)
        {
            float time = resetScreenshotTimer;
            float timeLeft = SCREENSHOT_TIME - resetScreenshotTimer;

            if(time <= FADE_TIME)
            {
                float timePercent = time / FADE_TIME;
                animatedBackground.draw(xAnim, yAnim, new Color(1, 1, 1, timePercent));
            }
            else if(timeLeft <= FADE_TIME)
            {
                float timeLeftPercent = timeLeft / FADE_TIME;
                animatedBackground.draw(xAnim, yAnim, new Color(1, 1, 1, timeLeftPercent));
            }
            else
            {
                animatedBackground.draw(xAnim, yAnim);
            }
        }

    }

    public void keyPressed(GameContainer gc, int key, char c)
    {

//        x = maxX;
//        y = maxY;

        if(key == Input.KEY_N)
        {
            switchToAnimationTimer = MAIN_SCREEN_TIME;
            reset();
        }

        if(key == Input.KEY_B && gc.getInput().isKeyPressed(Input.KEY_LSHIFT))
        {
            background = Images.news.getScaledCopy(Main.getScreenWidth(), Main.getScreenHeight());
        }
    }

}
