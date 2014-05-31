package ua.minor.christmasgifts.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ua.minor.christmasgifts.managers.SceneManager.SceneType;

public class SplashScene extends BaseScene {
	private Sprite splash;

	@Override
	public void createScene() {
		splash = new Sprite(0, 0, resourcesManager.mMenuBackground, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splash.setPosition(0, 0);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}
}
