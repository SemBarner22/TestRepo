package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Entity.EmptyScreen;
import com.mygdx.game.Screens.FightScreen;
import com.mygdx.game.Screens.MapScreen;

public class Strategy extends Game {
	public Config config;
	ImageButton start;
	ImageButton exit;
	Texture startButtonTexture;
	Texture exitButtonTexture;
	Stage stage;
	Table table;
	int Help_Guides = 12;
	int row_height;
	int col_width;
	public static int V_WIDTH;
	public static int V_HEIGHT;
	public static final float PPM = 100;
	public SpriteBatch batch;

	public Strategy(Config config) {
		this.config = config;
		if (config.desktop()) {
			V_WIDTH = 456;
			V_HEIGHT = 256;
		} else {
//			V_WIDTH = ;
//			V_HEIGHT = 256;
		}
	}

	@Override
	public void resize(int width, int height) {
//		row_height = width / 12;
//		col_width = height / 12;
		Gdx.graphics.setWindowedMode(width, height);
		stage.getViewport().update(width, height);
		//super.resize(width, height);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		startButtonTexture = new Texture(Gdx.files.internal("Interface\\start_button.png"));
		exitButtonTexture = new Texture(Gdx.files.internal("Interface\\exit_button.png"));
		start = new ImageButton(new TextureRegionDrawable(new TextureRegion(startButtonTexture)));
		exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitButtonTexture)));
		stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
		table = new Table();
		table.center();
		row_height = Gdx.graphics.getWidth() / 12;
		col_width = Gdx.graphics.getWidth() / 12;
		start.setSize(col_width*4,row_height);
		start.setPosition(col_width,Gdx.graphics.getHeight()-row_height*3);
		start.setTransform(true);
		start.scaleBy(0.5f);
		exit.setSize(col_width*4,row_height);
		exit.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
		exit.setTransform(true);
		exit.scaleBy(0.5f);
		stage.addActor(start);
		stage.addActor(exit);
		//table.add(exit).size(150, 200).center();
		//table.add(start).size(200, 400).center(); //Add the button to the stage to perform rendering and take input.
		Gdx.input.setInputProcessor(stage); //Start taking input from the ui
		start.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
				stage.clear();
				setScreen(new MapScreen(Strategy.this, 0, new EmptyScreen()));
				return true;

			}
		});
		exit.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("scroll", "Pressed"); //** Usually used to start Game, etc. **//
				stage.clear();
                setScreen(new FightScreen(Strategy.this, 0, new EmptyScreen()));
				return true;
			}
		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		batch.begin();
		stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
		stage.draw(); //Draw the ui
		batch.end();
	}

	@Override
	public void dispose () {
		startButtonTexture.dispose();
		exitButtonTexture.dispose();
		stage.dispose();
		batch.dispose();
	}
}
