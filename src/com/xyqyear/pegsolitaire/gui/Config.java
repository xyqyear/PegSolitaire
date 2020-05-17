package com.xyqyear.pegsolitaire.gui;

import java.awt.*;

public class Config {
    public static GraphicsConfiguration config =
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

    public static final int WINDOW_WIDTH = 616;
    public static final int WINDOW_HEIGHT = 639;
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;
    public static final int MAX_FPS = 60;

    public static final int BUTTON_START_X = 190;
    public static final int BUTTON_START_Y = 230;
    public static final int BUTTON_GAP = 80;
    public static final int BUTTON_SIZE = 40;
    public static final int BUTTON_HOVERING_SIZE = 46;
    public static final String BUTTON_FONT = "隶书";

    public static final int TITLE_X = 183;
    public static final int TITLE_Y = 90;
    public static final int TITLE_SIZE = 80;
    public static final String TITLE_FONT = "隶书";

    public static final int GAMEOVER_X = 230;
    public static final int GAMEOVER_Y = 140;
    public static final int GAMEOVER_SIZE = 35;
    public static final String GAMEOVER_FONT = "隶书";

    public static final int HIGHSCORE_X = 225;
    public static final int HIGHSCORE_Y = 190;
    public static final int HIGHSCORE_INT_X = 360;
    public static final int HIGHSCORE_INT_Y = 190;
    public static final int HIGHSCORE_SIZE = 30;
    public static final String HIGHSCORE_FONT = "隶书";
}
