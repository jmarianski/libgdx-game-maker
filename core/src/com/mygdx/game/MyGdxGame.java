package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javafx.scene.Scene;

public class MyGdxGame extends ApplicationAdapter {
	private Stage stage;
	private AssetLoader al;

	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		al = new AssetLoader();
		al.loadFolder("c:/Game dev/1/core/src/assets", -1);
	}

	public void resize(int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}
}