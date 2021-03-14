package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Entity.EmptyScreen;
import com.mygdx.game.Screens.FightScreen;
import com.mygdx.game.Screens.MapScreen;

public class Strategy extends Game {
	public int goal = 1;
	public Config config;
	ImageButton start;
	ImageButton exit;
	Texture startButtonTexture;
	Texture exitButtonTexture;
	Stage stage;
	Table table;
	int Help_Guides = 12;
	Skin skin;
	int row_height;
	int col_width;
	public static int V_WIDTH;
	public static int V_HEIGHT;
	public static final float PPM = 16;
	public SpriteBatch batch;
	public static AssetManager manager;

	public static float MOVE_MUL = 64 * 64;

	public Strategy(Config config) {
		this.config = config;
//		if (config.desktop()) {
			V_WIDTH = 456;
			V_HEIGHT = 256;
//		} else {
//			V_WIDTH = ;
//			V_HEIGHT = 256;
//		}
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
	    manager = new AssetManager();
	    manager.load("music/audio/mainTheme.mp3", Music.class);
	    manager.load("music/audio/mapMusic.mp3", Music.class);
	    manager.load("music/sounds/bump.mp3", Sound.class);
	    manager.load("music/sounds/babax.mp3", Sound.class);
		manager.finishLoading();
	    batch = new SpriteBatch();
		startButtonTexture = new Texture(Gdx.files.internal("Interface/start_button.png"));
		exitButtonTexture = new Texture(Gdx.files.internal("Interface/exit.png"));
		start = new ImageButton(new TextureRegionDrawable(new TextureRegion(startButtonTexture)));
		exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitButtonTexture)));
		stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
		table = new Table();
		table.center();
		row_height = Gdx.graphics.getWidth() / 12;
		col_width = Gdx.graphics.getWidth() / 12;
		if (config.desktop()) {
			start.setSize(col_width * 2, row_height);
			start.scaleBy(2.5f);
		} else {
			start.setSize(col_width * 4, row_height);
			start.scaleBy(1.5f);
		}
		start.setPosition(col_width / 2,Gdx.graphics.getHeight()-row_height*2.7f);
		start.setTransform(true);
		exit.setSize(col_width*3,row_height);
		exit.setPosition(col_width*6,Gdx.graphics.getHeight()-row_height*3.7f);
		exit.setTransform(true);
		exit.scaleBy(0.5f);
		stage.addActor(start);
		stage.addActor(exit);

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Label label = new Label("LifeLine", skin);
		if (config.desktop()) {
			label.setPosition(col_width * 4, Gdx.graphics.getHeight() - row_height * 5f);
			label.setFontScale(2.5f);
		} else {
			label.setFontScale(5f);
			label.setPosition(col_width * 5, Gdx.graphics.getHeight() - row_height * 5f);
		}
		stage.addActor(label);
//		Table table = new Table();
//		stage.addActor(table);
//		table.addActor(new Label("LifeLine", exit.getSkin()));
//		stage.addActor(new Label("LifeLine", exit.getSkin()));
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
                Gdx.app.exit();
				return true;
			}
		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.36f, 0.72f, 0.96f, 1);
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
