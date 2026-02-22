package main;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS

    final int origTileSize = 16; //16 x 16 tile
    final int scale = 3;

    final int tileSize = origTileSize * scale; // 48 x 48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    //Ratio ist 4/3;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //default player pos
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    Color playerColor = Color.white;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; //nanoseconds (0.01666 seconds)
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null){
//
//
//            //1. UPDATE: Update info wie character pos
//            update();
//            //2. DRAW: draw den Screen mit den upgedateten Infos
//            repaint();
//
//            //SLEEP METHOD
//            //übrige zeit vor dem nächsten update (Sleep Method akzeptiert nur long in try catch block)
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//
//                if(remainingTime < 0){
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            //Test System.out.println("game loop running");
//        }
//    }

    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void keyCheck() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        }

        if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }

        if (keyH.spacePressed) {
            playerColor = Color.blue;
        } else {
            playerColor = Color.white;
        }

        if (keyH.shiftPressed){
            playerSpeed = 8;
        } else{
            playerSpeed = 4;
        }
    }

    public void update() {
        keyCheck();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(playerColor);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}