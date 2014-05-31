package ua.minor.christmasgifts.managers;

import java.io.IOException;
import java.util.HashMap;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import ua.minor.christmasgifts.GameActivity;

public class ResourcesManager {
	private static ResourcesManager INSTANCE;
	
	public Engine engine;
	public GameActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	public ITextureRegion mMenuBackground;
	public ITiledTextureRegion mMenuButtonStart;
	public ITiledTextureRegion mMenuButtonHigh;
	public ITiledTextureRegion mMenuButtonExit;
	public TiledTextureRegion mMenuThree;
	
	public ITextureRegion mGameHud;
	public ITiledTextureRegion mGamePause;
	public ITextureRegion mGameOver;
	public ITextureRegion mCloud;
	
	public ITextureRegion mGiftA;
	public ITextureRegion mGiftB;
	public ITextureRegion mGiftC;
	public ITextureRegion mGiftExplode;
	
	public ITextureRegion mSnowball;
	public ITiledTextureRegion mDeer;
	public ITiledTextureRegion mSmanA;
	
	public ITextureRegion mGameBackground;
	public ITextureRegion mCannonFront;
	public ITextureRegion mCannonBack;
	public ITextureRegion mCannonTube;
	public ITextureRegion mTarget;
	
	public Music mMenuMusic;
	public Music mGameMusicA;
	public Music mGameMusicB;
	
	public BitmapFont mFont;
	

	HashMap<String, TexturePack> packsLoaded;
	
	private ResourcesManager(){
		packsLoaded = new HashMap<String, TexturePack>();
	}

	public synchronized static ResourcesManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ResourcesManager();
		}
		return INSTANCE;
	}
	
	public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}
	
	public synchronized void loadMenuTextures(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		BuildableBitmapTextureAtlas mAtlasMenuBackground = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGB_565, TextureOptions.BILINEAR);
		BuildableBitmapTextureAtlas mAtlasMenuButtons = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 748, 129,
				BitmapTextureFormat.RGB_565);
		BuildableBitmapTextureAtlas mAtlasMenuThree = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 564, 305,
				BitmapTextureFormat.RGBA_4444, TextureOptions.BILINEAR);

		mMenuBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasMenuBackground, activity, "menu_background.png");
		mMenuButtonStart = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlasMenuButtons, activity, "btn_start.png", 2, 1);
		mMenuButtonHigh = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlasMenuButtons, activity, "btn_high.png", 2, 1);
		mMenuButtonExit = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlasMenuButtons, activity, "btn_exit.png", 2, 1);
		mMenuThree = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlasMenuThree, activity, "xmas_three.png", 3, 1);
		
		try {
			mAtlasMenuBackground.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			mAtlasMenuButtons.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			mAtlasMenuThree.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		mAtlasMenuBackground.load();
		mAtlasMenuButtons.load();
		mAtlasMenuThree.load();
	}
	
	public synchronized void loadGifts(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		BuildableBitmapTextureAtlas mAtlasGifts = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 190, 190,
				BitmapTextureFormat.RGBA_4444, TextureOptions.BILINEAR);
		
		mGiftA = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGifts, activity, "gift.png");
		mGiftB = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGifts, activity, "gift2.png");
		mGiftC = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGifts, activity, "gift3.png");
		mGiftExplode = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGifts, activity, "particle_square.png");
		
		try {
			mAtlasGifts.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		mAtlasGifts.load();
	}
	
	public synchronized void loadGameTextures(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		BuildableBitmapTextureAtlas mAtlasGameOver = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGBA_4444, TextureOptions.BILINEAR);
		BuildableBitmapTextureAtlas mAtlasGameBackground = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGB_565, TextureOptions.BILINEAR);
		BuildableBitmapTextureAtlas mAtlasGame = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 256, 256,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		BuildableBitmapTextureAtlas mAtlasGameHud = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 800, 48,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		BuildableBitmapTextureAtlas mAtlasGamePause = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 150, 48,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		
		mGameOver = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGameOver, activity, "game_over_screen.png");

		mGameBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGameBackground, activity, "game_background.png");
		mCloud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGame, activity, "cloud.png");
		mSnowball = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGame, activity, "snowball.png");
		mCannonFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGame, activity, "cannon_front.png");
		mCannonBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGame, activity, "cannon_back.png");
		mCannonTube = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGame, activity, "cannon_tube.png");
		mTarget = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGame, activity, "target.png");
		mGameHud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasGameHud, activity, "hud.png");
		mGamePause = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAtlasGamePause, activity, "btn_pause.png", 2, 1);
		
		try {
			mAtlasGameOver.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			mAtlasGameBackground.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			mAtlasGame.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			mAtlasGameHud.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			mAtlasGamePause.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		reloadSmanAsA();
		
		mDeer = getTiledTextureFromPack("deer1");

		mAtlasGameOver.load();
		mAtlasGameBackground.load();
		mAtlasGame.load();
		mAtlasGameHud.load();
		mAtlasGamePause.load();
	}
	
	public synchronized void reloadSmanAsA(){
		mSmanA = getTiledTextureFromPack("snowman1");
	}
	
	public synchronized void reloadSmanAsB(){
		mSmanA = getTiledTextureFromPack("snowman2");
	}
	
	public synchronized void loadFonts(){
		mFont = new BitmapFont(engine.getTextureManager(), activity.getAssets(), "font/BitmapFont.fnt", TextureOptions.BILINEAR);
		mFont.load();
	}
	
	public synchronized void unloadFonts(){
		mFont.unload();
	}
	
	public synchronized void unloadMenuTextures(){
		BuildableBitmapTextureAtlas mAtlasMenuBackground = (BuildableBitmapTextureAtlas) mMenuBackground.getTexture();
		BuildableBitmapTextureAtlas mAtlasMenuButtons = (BuildableBitmapTextureAtlas) mMenuButtonStart.getTexture();
		BuildableBitmapTextureAtlas mAtlasMenuThree = (BuildableBitmapTextureAtlas) mMenuThree.getTexture();
		mAtlasMenuBackground.unload();
		mAtlasMenuButtons.unload();
		mAtlasMenuThree.unload();
		
		System.gc();
	}
	
	public synchronized void unloadGiftsTextures(){
		BuildableBitmapTextureAtlas mAtlasGifts = (BuildableBitmapTextureAtlas) mGiftA.getTexture();
		mAtlasGifts.unload();
		
		System.gc();
	}
	
	public synchronized void unloadGameTextures(){
		BuildableBitmapTextureAtlas mAtlasGameBackground = (BuildableBitmapTextureAtlas) mGameBackground.getTexture();
		BuildableBitmapTextureAtlas mAtlasGame = (BuildableBitmapTextureAtlas) mCloud.getTexture();
		BuildableBitmapTextureAtlas mAtlasGameHud = (BuildableBitmapTextureAtlas) mGameHud.getTexture();
		BuildableBitmapTextureAtlas mAtlasGamePause = (BuildableBitmapTextureAtlas) mGamePause.getTexture();
		mAtlasGameBackground.unload();
		mAtlasGame.unload();
		mAtlasGameHud.unload();
		mAtlasGamePause.unload();
		
		System.gc();
	}

	public synchronized void loadSounds() {
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");
		
		try {
			mMenuMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "thetwelvedays.mp3");
			mGameMusicA = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "bellsukulele.mp3");
			mGameMusicB = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "wewishreggae.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void unloadSounds(){
		if(!mMenuMusic.isReleased()) mMenuMusic.release();
		if(!mMenuMusic.isReleased()) mGameMusicA.release();
		if(!mMenuMusic.isReleased()) mGameMusicB.release();
	}
	
	public synchronized TiledTextureRegion getTiledTextureFromPack(String name) {
		
		TexturePackTextureRegionLibrary packer = null;
		TexturePack spritesheetTexturePack = null;
		if (packsLoaded.get(name) == null) {
			try {
				spritesheetTexturePack = new TexturePackLoader(engine.getTextureManager(), "gfx/spritesheets/").
						loadFromAsset(activity.getAssets(), name + ".xml");
				spritesheetTexturePack.loadTexture();
				packer = spritesheetTexturePack.getTexturePackTextureRegionLibrary();
			} catch (final TexturePackParseException e) {
				Debug.e(e);
			}
			packsLoaded.put(name, spritesheetTexturePack);
		} else {
			spritesheetTexturePack = packsLoaded.get(name);
			packer = spritesheetTexturePack.getTexturePackTextureRegionLibrary();
		}
		TexturePackerTextureRegion[] obj = new TexturePackerTextureRegion[packer.getIDMapping().size()];

		for (int i = 0; i < packer.getIDMapping().size(); i++) {
			obj[i] = packer.get(i);
		}

		return new TiledTextureRegion(spritesheetTexturePack.getTexture(), obj);
	}
}
