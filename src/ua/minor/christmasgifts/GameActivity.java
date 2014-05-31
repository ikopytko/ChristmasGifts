package ua.minor.christmasgifts;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import ua.minor.christmasgifts.managers.ResourcesManager;
import ua.minor.christmasgifts.managers.SceneManager;
import ua.minor.christmasgifts.managers.UserData;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {
	
	private Camera mCamera;

	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, 800, 480);

		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480),
				mCamera);

		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getRenderOptions().setMultiSampling(true);
		
		return engineOptions;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		/*ResourcesManager.getInstance().loadMenuTextures();
		ResourcesManager.getInstance().loadGifts();
		ResourcesManager.getInstance().loadGameTextures();
		ResourcesManager.getInstance().loadSounds();
		ResourcesManager.getInstance().loadFonts();*/

		ResourcesManager.prepareManager(mEngine, this, mCamera, getVertexBufferObjectManager());
		UserData.getInstance().init(this);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}


}
