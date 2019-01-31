package com.amplifiedhuman.drop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.amplifiedhuman.drop.Drop;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Drop game;

    private Texture dropImage;
    private Texture bucketImage;

    private Sound dropSound;
    private Music rainMusic;

    private OrthographicCamera camera;
    private Rectangle bucket;
    private Vector3 touchPos;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private long score;


    public GameScreen(Drop game) {
        this.game = game;
        //Loads the droplet and bucket image
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        //Loads sound and music
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        //enabled looping, play when screen is showed
        rainMusic.setLooping(true);
        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        bucket = new Rectangle(800 / 2 - 64 / 2, 20, 64, 64);

        touchPos = new Vector3();

        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        score = 0;
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //clears screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.sb.setProjectionMatrix(camera.combined);
        game.sb.begin();
        game.sb.draw(bucketImage, bucket.x, bucket.y);
        game.font.draw(game.sb, "Score: " + score, 500, 440);

        for (Rectangle raindrop : raindrops) {
            game.sb.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.sb.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }
    }

    private void update() {
        handleInput();
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
        //spawns raindrop every second
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }
        //used iterator because we need to remove raindrops
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            //update position of every raindrop
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(bucket)) {
                score++;
                dropSound.play();
                iter.remove();
            }
        }
    }

    private void spawnRaindrop() {
        //generate new raindrop
        int randomPosition = MathUtils.random(0, 800 - 64);
        raindrops.add(new Rectangle(randomPosition, 480, 64, 64));
        //updates lastDropTime
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    //the rest are unused methods
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

}
