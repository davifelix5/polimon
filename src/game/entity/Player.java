package game.entity;

import game.animation.SpriteSheet;
import game.handlers.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

    private final KeyHandler movementKeyInput;

    public SpriteSheet sprites;

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

        if (movementKeyInput.upPressed) {
            direction = "Up";
        } else if (movementKeyInput.downPressed) {
            direction = "Down";
        }

        // Movimentação em y
        if (movementKeyInput.leftPressed) {
            direction = "Left";
        } else if (movementKeyInput.rightPressed) {
            direction = "Rigth";
        }

        if (!movementKeyInput.downPressed && !movementKeyInput.upPressed)
            setVelY(0);

        if (!movementKeyInput.rightPressed && !movementKeyInput.leftPressed)
            setVelX(0);

        // Movimentação em X
        if (getVelX() == 0) {
            if (movementKeyInput.upPressed) {
                setVelY(-2);
            } else if (movementKeyInput.downPressed) {
                setVelY(2);
            }
        }

        // Movimentação em y
        if (getVelY() == 0) {
            if (movementKeyInput.leftPressed) {
                setVelX(-2);
            } else if (movementKeyInput.rightPressed) {
                setVelX(2);
            }
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

            BufferedImage spriteSheet = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));

            this.sprites = new SpriteSheet(spriteSheet, 32, 41);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

        BufferedImage image = null;

        switch (direction) {
            case "Stop" -> image = sprites.getSprite(1 , 1);
            case "Up" -> {
                if (spriteNum == 1) {
                    image = sprites.getSprite(4 , 2);
                } else if (spriteNum == 2) {
                    image = sprites.getSprite(4, 4);
                }
            }
            case "Left" -> {
                if (spriteNum == 1) {
                    image = sprites.getSprite(2 , 2);
                } else if (spriteNum == 2) {
                    image = sprites.getSprite(2 , 4);
                }
            }
            case "Rigth" -> {
                if (spriteNum == 1) {
                    image = sprites.getSprite(3 , 2);
                } else if (spriteNum == 2) {
                    image = sprites.getSprite(3 , 4);
                }
            }
            case "Down" -> {
                if (spriteNum == 1) {
                    image = sprites.getSprite(1 , 2);
                } else if (spriteNum == 2) {
                    image = sprites.getSprite(1 , 4);
                }
            }
            case "RigthStop" -> image = sprites.getSprite(3 , 1);
            case "LeftStop" -> image = sprites.getSprite(2 , 1);
            case "UpStop" -> image = sprites.getSprite(4 , 1);
        }
        g.drawImage(image, getX(), getY(), 32, 32, null);

    }

}


