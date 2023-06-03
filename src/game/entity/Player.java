package game.entity;

import game.handlers.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

    private final KeyHandler movementKeyInput;

    public BufferedImage stopDown, walkDown1, walkDown2, walkLeft1, walkLeft2, walkRigth1, walkRigth2, walkUp1, walkUp2,stopLeft, stopRigth, stopUp;
    public String direction;

    public Double spriteCounter = 0.0;
    public int spriteNum = 1;

    public Player(int x, int y, KeyHandler movementKeyInput) {
        super(x, y);
        this.movementKeyInput = movementKeyInput;
        getPlayerImage();
    }

    @Override
    public void tick() {
        if (direction == null) {
            direction = "Stop";
        } else {
            switch (direction) {
                case "Rigth" -> direction = "RigthStop";
                case "Left" -> direction = "LeftStop";
                case "Up" -> direction = "UpStop";
                case "Down" -> direction = "Stop";
            }
        }
        // Movimentação em X
        if (movementKeyInput.upPressed){
            setVelY(-2);
            direction = "Up";
        }
        else if (movementKeyInput.downPressed){
            setVelY(2);
            direction = "Down";
        }
        else{
            setVelY(0);
        }

        // Movimentação em y
        if (movementKeyInput.leftPressed){
            setVelX(-2);
            direction = "Left";
        }

        else if(movementKeyInput.rightPressed){
            setVelX(2);
            direction = "Rigth";
        }
        else{
            setVelX(0);
        }

        setX(getX() + getVelX());
        setY(getY() + getVelY());

        spriteCounter += 1;
        spriteChanger();
    }

    public void spriteChanger(){
        if (spriteCounter > 10){
            spriteCounter = 0.0;
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
        }
    }


    public void getPlayerImage(){

        try {
            stopDown = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite1.png"));
            walkDown1 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite2.png"));
            walkDown2 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite3.png"));
            stopLeft = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite4.png"));
            walkLeft1 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite5.png"));
            walkLeft2 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite6.png"));
            stopRigth = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite7.png"));
            walkRigth1 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite8.png"));
            walkRigth2 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite9.png"));
            stopUp = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite10.png"));
            walkUp1 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite11.png"));
            walkUp2 = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprite12.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

        BufferedImage image = null;

        switch (direction) {
            case "Stop" -> image = stopDown;
            case "Up" -> {
                if (spriteNum == 1) {
                    image = walkUp1;
                } else if (spriteNum == 2) {
                    image = walkUp2;
                }
            }
            case "Left" -> {
                if (spriteNum == 1) {
                    image = walkLeft1;
                } else if (spriteNum == 2) {
                    image = walkLeft2;
                }
            }
            case "Rigth" -> {
                if (spriteNum == 1) {
                    image = walkRigth1;
                } else if (spriteNum == 2) {
                    image = walkRigth2;
                }
            }
            case "Down" -> {
                if (spriteNum == 1) {
                    image = walkDown1;
                } else if (spriteNum == 2) {
                    image = walkDown2;
                }
            }
            case "RigthStop" -> image = stopRigth;
            case "LeftStop" -> image = stopLeft;
            case "UpStop" -> image = stopUp;
        }
        g.drawImage(image, getX(), getY(), 32, 32, null);

    }

}


