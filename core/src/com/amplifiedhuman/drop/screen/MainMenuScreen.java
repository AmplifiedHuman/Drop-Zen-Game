package com.amplifiedhuman.drop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.amplifiedhuman.drop.Drop;

public class MainMenuScreen implements Screen {

    final Drop game;
    private OrthographicCamera camera;

    public MainMenuScreen(final Drop game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.sb.setProjectionMatrix(camera.combined);
        game.sb.begin();
        game.font.draw(game.sb, "Welcome to Drop.", camera.position.x - 180, camera.position.y + 100);
        game.font.draw(game.sb, "Touch Anywhere to Begin", camera.position.x - 250, camera.position.y);
        game.sb.end();
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    //unimplemented methods
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

    @Override
    public void dispose() {
    }
}
