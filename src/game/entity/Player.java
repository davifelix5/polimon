package game.entity;

import game.Game;
import game.animation.*;
import game.handlers.KeyHandler;
import game.map.LayerType;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Player extends Entity {

    private final KeyHandler movementKeyInput;

    private int movingRate; // Módulo da velocidade do jogador

    private int positionX, positionY; // Posição do jogador na tela
    private boolean colliding;

    private final IAnimationSet[] animationSets = new IAnimationSet[PlayerAnimations.values().length]; // Vetor com todas as animações possíveis.
    private PlayerAnimations currentAnimation;

    private final TileManager tileManager; // Informação sobre os tiles do mapa.

    public Player(int x, int y, int movingRate, KeyHandler movementKeyInput, PlayerAnimations currentAnimation, TileManager tileManager) {
        super(x, y);
        this.movingRate = movingRate;
        this.movementKeyInput = movementKeyInput;
        this.currentAnimation = currentAnimation;
        this.tileManager = tileManager;
        this.colliding = false;

        loadAnimations();

        // Inicializando a câmera
        this.positionX = Game.width / 2 - getWidth();
        this.positionY = Game.height / 2 - getHeight();
        tileManager.setReferenceX(getWorldX() - positionX);
        tileManager.setReferenceY(getWorldY() - positionY);
    }

    private void loadAnimations() {
        try {
            BufferedImage bikeSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
            BufferedImage walkSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            BufferedImage swimSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSwim.png"));
            SpriteSheet bikeSpritesheet = new SpriteSheet(bikeSprites, 48, 48);
            SpriteSheet walkSpriteSheet = new SpriteSheet(walkSprites, 32, 41);
            SpriteSheet swimSpritesheet = new SpriteSheet(swimSprites, 64, 82);
            this.animationSets[PlayerAnimations.Bike.getValue()] = new MoveAnimationSet(bikeSpritesheet, 0);
            this.animationSets[PlayerAnimations.Walk.getValue()] = new MoveAnimationSet(walkSpriteSheet, 0);
            this.animationSets[PlayerAnimations.Swimming.getValue()] = new MoveAnimationSet(swimSpritesheet,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        // Gerencia o movimento da câmera
        tileManager.setReferenceX(getWorldX() - positionX);
        tileManager.setReferenceY(getWorldY() - positionY);

        // Posição do jogador é, por padrão, no centro da tela
        this.positionX = Game.width / 2 - getWidth();
        this.positionY = Game.height / 2 - getHeight();

        // Mudando o comportamento nos extremos do mapa
        // Extremo direto
        if (getWorldX() + getWidth() + Game.width / 2 >= this.tileManager.getMaxWidth()) {
            int referenceX = tileManager.getMaxWidth() - Game.width;
            this.positionX = getWorldX() - referenceX;
            this.tileManager.setReferenceX(referenceX);
        }
        // Extremo inferior
        if (getWorldY() + getHeight() + Game.height / 2 >= this.tileManager.getMaxHeight()) {
            int referenceY = tileManager.getMaxHeight() - Game.height;
            this.positionY = getWorldY() - referenceY;
            this.tileManager.setReferenceY(referenceY);

        }
        // Extremo esquerdo
        if (getWorldX() + getWidth() - Game.width / 2 <= 0 && getWorldX() <= Game.width / 2) {
            tileManager.setReferenceX(0);
            this.positionX = getWorldX();
        }
        // Extremo superior
        if (getWorldY() + getHeight() - Game.height / 2 <= 0 && getWorldY() <= Game.height / 2) {
            tileManager.setReferenceY(0);
            this.positionY = getWorldY();
        }


        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações

        // Gerencia qual será a animação atual do jogador.
        if (movementKeyInput.bikeButtonPressed) {
            setCurrentAnimation(PlayerAnimations.Bike);
            this.setMovingRate(3);
        }
        // A animalçao de nadar deve ser preferencial em relação à da bicicleta.
        if (tileManager.searchLayers( getWorldRow(), getWorldCol(), LayerType.SWIMABLE) != null){
            setCurrentAnimation(PlayerAnimations.Swimming);
            this.setMovingRate(4);
        } else if (!movementKeyInput.bikeButtonPressed) {
            setCurrentAnimation(PlayerAnimations.Walk);
            this.setMovingRate(2);
        }

        // Lidando com a lógica de parada do jogador
        if (!movementKeyInput.downPressed && !movementKeyInput.upPressed || colliding){
            if (getCurrentAnimationSet().getCurrentIndex() == FOWARD || getCurrentAnimationSet().getCurrentIndex() == BACKWARD) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelY(0);
        }
        if (!movementKeyInput.rightPressed && !movementKeyInput.leftPressed || colliding) {
            if (getCurrentAnimationSet().getCurrentIndex() == RIGHT || getCurrentAnimationSet().getCurrentIndex() == LEFT) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelX(0);
        }

        // Movimentação em X
        if (getVelX() == 0) { // O jogador não pode se movimentar em duas direções simultaneamente
            if (movementKeyInput.upPressed) {
                getCurrentAnimationSet().setCurrentIndex(FOWARD);
                getAnimation().start();
                setVelY(-movingRate);
            } else if (movementKeyInput.downPressed) {
                getCurrentAnimationSet().setCurrentIndex(BACKWARD);
                getAnimation().start();
                setVelY(movingRate);
            }
        }

        // Movimentação em y
        if (getVelY() == 0) { // O jogador não pode se movimentar em duas direções simultaneamente
            if (movementKeyInput.leftPressed) {
                getCurrentAnimationSet().setCurrentIndex(LEFT);
                getAnimation().start();
                setVelX(-movingRate);
            } else if (movementKeyInput.rightPressed) {
                getCurrentAnimationSet().setCurrentIndex(RIGHT);
                getAnimation().start();
                setVelX(movingRate);
            }
        }

        if (!colliding) {
            getAnimation().tick();
            int nextPosX = getWorldX() + getVelX();
            int nextPosY = getWorldY() + getVelY();
            setWorldX(Game.clamp(nextPosX, 0, tileManager.getMaxWidth() - getWidth()));
            setWorldY(Game.clamp(nextPosY, 0, tileManager.getMaxHeight() - getHeight()));
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage image = getAnimation().nextSprite();

        g.drawImage(image, positionX, positionY, 32, 32, null);
    }

    /***
     * Método para identifcar a estratégia de animação atual
     *
     * @return a animação que corresponde ao índice atual
     */
    private Animation getAnimation() {
        return this.animationSets[this.currentAnimation.getValue()].getCurrentAnimation();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 10, 10);
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public void setMovingRate(int movingRate) {
        this.movingRate = movingRate;
    }

    public void setCurrentAnimation(PlayerAnimations currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public IAnimationSet getCurrentAnimationSet() {
        return this.animationSets[this.currentAnimation.getValue()];
    }

    /***
     * Identifica a largura do jogador a partir dos sprites da animação atual.
     * @return largura do jogador
     */
    public int getWidth() {
        return this.getCurrentAnimationSet().getSprites().spriteWidth;
    }

    /***
     * Identifica a largura do jogador a partir dos sprites da animação atual.
     * @return largura do jogador
     */
    public int getHeight() {
        return this.getCurrentAnimationSet().getSprites().spriteHeigth;
    }

}


