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
}
