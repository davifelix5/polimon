package game.entity.player;

import game.Game;
import game.entity.animation.*;
import game.entity.Entity;
import game.ui.handlers.KeyHandler;
import game.map.factory.MapFactory;
import game.ui.sounds.SoundEffect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Random;

public class Player extends Entity {

    private final KeyHandler movementKeyInput;

    private int movingRate = 2; // Módulo da velocidade do jogador

    private int positionX, positionY; // Posição do jogador na tela
    private boolean colliding;
    private boolean swimming;

    private int pokeBallAmount = 0;
    private int hp = 100;
    private BufferedImage pokeballImage, pikachuImage;
    private BufferedImage itemImage;

    private final IAnimationSet[] animationSets = new IAnimationSet[PlayerAnimations.values().length]; // Vetor com todas as animações possíveis.
    private PlayerAnimations currentAnimation;
    private final SoundEffect walkSoundEffect = new SoundEffect("src/game/res/sound/grass.wav");
    private final SoundEffect bikeSoundEffect = new SoundEffect("src/game/res/sound/bike.wav");
    private final SoundEffect swimSoundEffect = new SoundEffect("src/game/res/sound/swim.wav");

    public static final int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações

    private int experience = 0;

    private IPlayerVelSetter velSetter;
    private int numItems;
    private final Random itemSorter;
    private boolean hasUsedItem;
    private final Pokedex pokedex;

    private MapFactory spritesFactory;

    public Player(int x, int y, KeyHandler movementKeyInput) {
        super(x, y);
        this.movementKeyInput = movementKeyInput;
        this.currentAnimation = PlayerAnimations.Walk;
        this.pokedex = new Pokedex();
        this.colliding = false;
        this.velSetter = new BaseVelSetter(this);
        this.numItems = 0;
        this.itemSorter = new Random();
        this.hasUsedItem = false;
    }

    public void stopPlayerSoundEffects(){
        walkSoundEffect.stop();
        bikeSoundEffect.stop();
        swimSoundEffect.stop();
    }

    public void setFactory(MapFactory spritesFactory) {
        this.spritesFactory = spritesFactory;
    }

    public void loadAnimations() {
        try {
            this.animationSets[PlayerAnimations.Bike.getValue()] = new MoveAnimationSet(spritesFactory.getPlayerSpriteSheets(PlayerAnimations.Bike), 0, 10);
            this.animationSets[PlayerAnimations.Walk.getValue()] = new MoveAnimationSet(spritesFactory.getPlayerSpriteSheets(PlayerAnimations.Walk), 0, 10);
            this.animationSets[PlayerAnimations.Swimming.getValue()] = new MoveAnimationSet(spritesFactory.getPlayerSpriteSheets(PlayerAnimations.Swimming),0, 10);
            this.pokeballImage = ImageIO.read(new FileInputStream("src/game/res/sprites/pokemon/pokeball.png"));
            this.itemImage = ImageIO.read(new FileInputStream("src/game/res/sprites/item.png"));
            this.pikachuImage = ImageIO.read(new FileInputStream("src/game/res/sprites/pokemon/poke.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if (this.getCurrentAnimationSet() == null)
            return;

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

        // int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações

        // Gerencia qual será a animação atual do jogador.
        stopPlayerSoundEffects();
        if (movementKeyInput.isBikeButtonPressed() && !swimming) {
            setCurrentAnimation(PlayerAnimations.Bike);
            bikeSoundEffect.play();
            this.setMovingRate(3);
        } else if (swimming) {
            setCurrentAnimation(PlayerAnimations.Swimming);
            swimSoundEffect.play();
            this.setMovingRate(4);
        } else {
            setCurrentAnimation(PlayerAnimations.Walk);
            walkSoundEffect.play();
            this.setMovingRate(2);
        }

        // Lidando com a lógica de parada do jogador
        if (!movementKeyInput.isDownPressed() && !movementKeyInput.isUpPressed() || colliding){
            if (getCurrentAnimationSet().getCurrentIndex() == FOWARD || getCurrentAnimationSet().getCurrentIndex() == BACKWARD) {
                getAnimation().stop();
                getAnimation().reset();
                stopPlayerSoundEffects();
            }
            setVelY(0);
        }
        if (!movementKeyInput.isRightPressed() && !movementKeyInput.isLeftPressed() || colliding) {
            if (getCurrentAnimationSet().getCurrentIndex() == RIGHT || getCurrentAnimationSet().getCurrentIndex() == LEFT) {
                getAnimation().stop();
                getAnimation().reset();
                stopPlayerSoundEffects();
            }
            setVelX(0);
        }

        if (this.hasUsedItem) {
            this.hasUsedItem = movementKeyInput.isUseItemPressed();
        }
        else if (movementKeyInput.isUseItemPressed() && !movementKeyInput.isResetEffectsPressed() && this.numItems != 0 && !swimming && !movementKeyInput.isBikeButtonPressed()) {
            this.useItem();
            this.hasUsedItem = true;
        }

        if (movementKeyInput.isResetEffectsPressed()) {
            this.velSetter = new BaseVelSetter(this);
        }

        this.velSetter.setVel();

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

    /**
     * Renderiza informações sobre o Player.
     * @param g gráficos usados para renderização
     */
    public void renderPlayerStatus(Graphics g) {
        // Renderizando a quantidade de pokebolas
        int pokeballX = Game.width - 3*Game.tileSize;
        int pokeballY = Game.tileSize / 2;
        g.drawImage(pokeballImage, pokeballX, pokeballY, 25, 25, null);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("x " + this.getPokeballs(), pokeballX + Game.tileSize, pokeballY + 20);

        // Renderizando a quantidade de itens
        int itemX = Game.width - 3*Game.tileSize;
        int itemY = 3 * Game.tileSize / 2;
        g.drawImage(itemImage, itemX, itemY, 25, 25, null);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("x " + this.numItems, itemX + Game.tileSize, itemY + 20);

        // Renderizando a quantidade de pokemons
        int pokedexX = Game.width - 8*Game.tileSize;
        g.drawImage(pikachuImage, pokedexX, pokeballY, 25, 25, null);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("x " + this.pokedex.getPokemons().size(), pokedexX + Game.tileSize, pokeballY + 20);

    }

    public void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }

    /***
     * Método para identificar a estratégia de animação atual
     *
     * @return a animação que corresponde ao índice atual
     */
    public Animation getAnimation() {
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

    public int getMovingRate() {
        return this.movingRate;
    }

    public void setCurrentAnimation(PlayerAnimations currentAnimation) {
        this.currentAnimation = currentAnimation;

    }

    public IAnimationSet getCurrentAnimationSet() {
        return this.animationSets[this.currentAnimation.getValue()];
    }

    public void addPokeball() {
        final int maxPokeballs = 50;
        this.pokeBallAmount = Game.clamp(this.pokeBallAmount + 1, 0, maxPokeballs);
    }

    public int getPokeballs() {
        return this.pokeBallAmount;
    }

    public boolean hasPokeballs() {
        return this.pokeBallAmount != 0;
    }

    public void removePokeball() {
        this.pokeBallAmount--;
    }

    public void reduceHP(int amountToReduce) {
        this.hp = Game.clamp(this.hp - amountToReduce, 0, 100);
    }

    public int getHP() {
        return this.hp;
    }

    public void cure() {
        this.hp = 100;
    }

    public void increaseExpeciente(int addedExperience) {
        this.experience = Game.clamp(this.experience + addedExperience, 0, 100);
    }

    public int getExperience() {
        return experience;
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

    public KeyHandler getMovementKeyInput() {
        return this.movementKeyInput;
    }

    public void useItem() {
        switch (this.itemSorter.nextInt(3)) {
            case 0 -> this.velSetter = new IncrementVelDecorator(this.velSetter);
            case 1 -> this.velSetter = new DecrementVelDecorator(this.velSetter);
            case 2 -> this.velSetter = new ReverseVelDecorator(this.velSetter);
        }
        this.numItems--;
    }

    public void addItem() {
        this.numItems = Game.clamp(this.numItems + 1, 0, 50);
    }

    public Pokedex getPokedex() {
        return pokedex;
    }
}


