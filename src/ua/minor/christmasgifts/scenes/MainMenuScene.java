package ua.minor.christmasgifts.scenes;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import ua.minor.christmasgifts.managers.ResourcesManager;
import ua.minor.christmasgifts.managers.SceneManager;
import ua.minor.christmasgifts.managers.SceneManager.SceneType;

public class MainMenuScene extends BaseScene {
	
	ButtonSprite btnStart;
	ButtonSprite btnHigh;
	ButtonSprite btnExit;
	
	@Override
	public void createScene() {
		createMenu();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		unregisterTouchArea(btnStart);
		unregisterTouchArea(btnHigh);
		unregisterTouchArea(btnExit);
		ResourcesManager.getInstance().unloadGiftsTextures();
		ResourcesManager.getInstance().unloadMenuTextures();
	}

	private void createMenu() {
		Sprite background = new Sprite(0, 0,
				ResourcesManager.getInstance().mMenuBackground, vbom);
		attachChild(background);
		
		AnimatedSprite tree = new AnimatedSprite(583, 96,
				ResourcesManager.getInstance().mMenuThree, vbom);
		tree.animate(150, true);
		attachChild(tree);

		Sprite gifta = new Sprite(580, 360,
				ResourcesManager.getInstance().mGiftA, vbom);
		Sprite giftb = new Sprite(616, 371,
				ResourcesManager.getInstance().mGiftB, vbom);
		Sprite giftc = new Sprite(688, 363,
				ResourcesManager.getInstance().mGiftC, vbom);
		attachChild(gifta);
		attachChild(giftc);
		attachChild(giftb);

		btnStart = new ButtonSprite(203, 161,
				ResourcesManager.getInstance().mMenuButtonStart, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					ResourcesManager.getInstance().activity.runOnUiThread(new Runnable(){
						@Override public void run() {
							SceneManager.getInstance().loadGameScene(engine);
							} });
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		btnHigh = new ButtonSprite(203, 204,
				ResourcesManager.getInstance().mMenuButtonHigh, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					/*
					 * GameActivity.this.runOnUiThread(new Runnable(){
					 * 
					 * @Override public void run() {
					 * Toast.makeText(getApplicationContext(), "Highscore",
					 * Toast.LENGTH_SHORT).show(); } });
					 */
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		btnExit = new ButtonSprite(203, 247,
				ResourcesManager.getInstance().mMenuButtonExit, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					//ResourcesManager.getInstance().unloadGameTextures();
					ResourcesManager.getInstance().activity.finish();
					//ResourcesManager.getInstance().unloadSounds();
					// finish();
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		registerTouchArea(btnStart);
		registerTouchArea(btnHigh);
		registerTouchArea(btnExit);

		attachChild(btnStart);
		attachChild(btnHigh);
		attachChild(btnExit);
	}
}
