package com.xyqyear.pegsolitaire.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main extends Thread {
    private boolean isRunning = true;
    private BufferStrategy strategy;
    private BufferedImage background;
    private Graphics2D graphics;
    private JFrame jFrame;

    // Game stuff
    private Game game = Game.getInstance();
    private Renderer renderer = Renderer.getInstance();

    // Setup
    public Main() {
        // JFrame
        jFrame = SingletonJFrame.getInstance();
        jFrame.setTitle("Peg Solitaire");
        jFrame.addWindowListener(new FrameClose());
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jFrame.setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        jFrame.setVisible(true);

        // Canvas
        Canvas canvas = new Canvas(Config.config);
        canvas.setSize(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);
        canvas.addMouseListener(MouseManager.getInstance());
        canvas.addMouseMotionListener(MouseManager.getInstance());
        canvas.addKeyListener(KeyboardManager.getInstance());
        jFrame.add(canvas, 0);

        // Background & Buffer
        background = Utils.create(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT, false);
        canvas.createBufferStrategy(2);
        do {
            strategy = canvas.getBufferStrategy();
        } while (strategy == null);

        start();
    }

    private class FrameClose extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }

    // Screen and buffer stuff
    private Graphics2D getBuffer() {
        if (graphics == null) {
            try {
                graphics = (Graphics2D) strategy.getDrawGraphics();
            } catch (IllegalStateException e) {
                return null;
            }
        }
        return graphics;
    }

    private boolean updateScreen() {
        graphics.dispose();
        graphics = null;
        try {
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
            return (!strategy.contentsLost());

        } catch (NullPointerException | IllegalStateException e) {
            return true;
        }
    }

    public void run() {
        Graphics2D backgroundGraphics = (Graphics2D) background.getGraphics();
        long fpsWait = (long) (1.0 / Config.MAX_FPS * 1000);
        main: while (isRunning) {
            long renderStart = System.nanoTime();
            if (!game.loop()) {
                break;
            }

            // Update Graphics
            do {
                Graphics2D bg = getBuffer();
                if (!isRunning) {
                    break main;
                }
                renderer.renderGame(backgroundGraphics);
                bg.drawImage(background, 0, 0, null);
                bg.dispose();
            } while (!updateScreen());

            // Better do some FPS limiting here
            long renderTime = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, fpsWait - renderTime));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
        jFrame.dispose();
    }

    public static void main(final String[] args) {
        new Main();
    }
}