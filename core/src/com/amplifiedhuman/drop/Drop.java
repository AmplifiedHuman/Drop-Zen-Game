package com.amplifiedhuman.drop;

import com.amplifiedhuman.drop.screen.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Drop extends Game {
    public SpriteBatch sb;
    public BitmapFont font;

    @Override
    public void create() {
        sb = new SpriteBatch();
        //generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitfont.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        generator.dispose();
        //sets up main menu
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        sb.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}