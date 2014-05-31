package ua.minor.christmasgifts.scenes;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.IParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import ua.minor.christmasgifts.managers.GameManager;
import ua.minor.christmasgifts.managers.SceneManager;
import ua.minor.christmasgifts.managers.SceneManager.SceneType;
import ua.minor.christmasgifts.managers.UserData;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.GLES20;
import android.text.InputFilter;
import android.widget.EditText;

public class GameScene extends BaseScene implements IOnMenuItemClickListener {

	private float targetAngle = -45;
	private Sprite cannonTube;
	private Sprite snowball;
	private Sprite backgroundTouch;
	private AnimatedSprite deer;
	private AnimatedSprite snowman;
	private int secondsElapsed = 60;

	private BatchedSpriteParticleSystem particleSystemA;
	private BatchedSpriteParticleSystem particleSystemB;
	private BatchedSpriteParticleSystem particleSystemC;

	private MenuScene mMenuScene;
	
	private Random random = new Random();
	private int factor = 30;

	private int[] deerRunFrame = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private long[] deerRunFrameDuration = { 2 * factor, 1 * factor, 8 * factor,
			2 * factor, 2 * factor, 2 * factor, 1 * factor, 2 * factor,
			1 * factor, 1 * factor };

	private int[] deerCryFrame = { 10, 11, 12, 13, 14, 15, 16, 17, 18, 17, 18,
			17, 18, 17, 18, 17, 18, 19, 20, 21, 22, 16, 23 };
	private long[] deerCryFrameDuration = { 12 * factor, 5 * factor,
			2 * factor, 2 * factor, 1 * factor, 1 * factor, 11 * factor,
			3 * factor, 2 * factor, 3 * factor, 3 * factor, 2 * factor,
			1 * factor, 2 * factor, 3 * factor, 2 * factor, 4 * factor,
			1 * factor, 1 * factor, 2 * factor, 1 * factor, 2 * factor,
			1 * factor };

	private int factorSman = 50;
	private int[] smanStayFrame = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	private long[] smanStayFrameDuration = {35 * factorSman, 4 * factorSman, 2 * factorSman, 2 * factorSman, 2 * factorSman, 3 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 2 * factorSman, 1 * factorSman, 1 * factorSman, 2 * factorSman};
	
	private int[] smanPointFrame = {13, 14};
	private long[] smanPointFrameDuration = {4 * factorSman, 3 * factorSman};
	
	private int[] smanAngryFrame = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	private long[] smanAngryFrameDuration = {1 * factorSman, 2 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 2 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 2 * factorSman, 1 * factorSman, 2 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman};
	
	private int[] santaThinkFrame = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
	private long[] santaThinkFrameDuration = {50 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman, 1 * factorSman};
	
	@Override
	public void createScene() {
		GameManager.getInstance().initialize();
		addHud();
		createGame();
		registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				if (!GameManager.getInstance().paused) {
					secondsElapsed--;
					if (secondsElapsed == 0)
						gameOver();
				}
			}
		}));
		
		registerUpdateHandler(new TimerHandler(1, false, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				start();
			}
		}));
		
		mMenuScene = createMenuScene();
	}

	@Override
	public void onBackKeyPressed() {
		setChildScene(mMenuScene, false, true, true);
		pause();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {

	}

	public void addHud() {
		HUD hud = new HUD();
		Sprite background = new Sprite(0, 0,
				resourcesManager.mGameHud, vbom);
		hud.attachChild(background);

		Text balls = new Text(490, 8, resourcesManager.mFont, "0123", vbom) {
			int secprev = 0;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (secprev != secondsElapsed) {
					secprev = secondsElapsed;
					this.setText("" + secprev);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		Text points = new Text(600, 8, resourcesManager.mFont,
				"01234", vbom) {
			int pointssprev = 0;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (pointssprev != GameManager.getInstance().getScore()) {
					pointssprev = GameManager.getInstance().getScore();
					this.setText("" + pointssprev);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		hud.attachChild(balls);
		hud.attachChild(points);

		ButtonSprite btnPause = new ButtonSprite(0, 0,
				resourcesManager.mGamePause, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					Debug.d("Paused!");
					if (GameManager.getInstance().paused)
						start();
					else {
						setChildScene(mMenuScene, false, true, true);
						pause();
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		registerTouchArea(btnPause);
		hud.attachChild(btnPause);
		//attachChild(hud);
		camera.setHUD(hud);
	}

	public void createGame() {
		backgroundTouch = new Sprite(0, 0,
				resourcesManager.mGameBackground, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				final float cannonTubeX = cannonTube.getRotationCenterX()
						+ cannonTube.getX();
				final float cannonTubeY = cannonTube.getRotationCenterY()
						+ cannonTube.getY();

				if (pSceneTouchEvent.isActionDown()
						|| pSceneTouchEvent.isActionMove()) {
					final float dX = pTouchAreaLocalX - cannonTubeX;
					final float dY = pTouchAreaLocalY - cannonTubeY;

					final float angle = (float) Math.atan2(dY, dX);

					targetAngle = MathUtils.radToDeg(angle);

					if (targetAngle > 6)
						targetAngle = 6;
					else if (targetAngle < -60)
						targetAngle = -60;
				} else if (pSceneTouchEvent.isActionUp()) {
					if (GameManager.getInstance().canMakeShoot())
						shoot();
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		attachChild(backgroundTouch);

		// add cannon
		Sprite cannonBack = new Sprite(185, 325, resourcesManager.mCannonBack, vbom);
		Sprite cannonFront = new Sprite(81, 340, resourcesManager.mCannonFront, vbom);
		cannonTube = new Sprite(122, 325, resourcesManager.mCannonTube, vbom) {
			float prevAngle = 0;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (targetAngle != prevAngle) {
					// this.setRotation(targetAngle);
					this.clearEntityModifiers();
					this.registerEntityModifier(new RotationModifier(.3f,
							prevAngle, targetAngle));
					prevAngle = targetAngle;
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		cannonTube.setRotationCenter(61, 21);

		Sprite cannonTarget = new Sprite(0, 0,
				resourcesManager.mTarget, vbom) {

			final float cannonTubeX = cannonTube.getRotationCenterX()
					+ cannonTube.getX() - this.getWidth() / 2;
			final float cannonTubeY = cannonTube.getRotationCenterY()
					+ cannonTube.getY() - this.getHeight() / 2;
			float prevAngle = 0;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (targetAngle != prevAngle) {
					final float nevX = (float) Math.cos(MathUtils
							.degToRad(targetAngle)) * 180;
					final float newY = (float) Math.sin(MathUtils
							.degToRad(targetAngle)) * 180;
					this.setPosition(nevX + cannonTubeX, newY + cannonTubeY);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};

		final Rectangle snowmanBorder = new Rectangle(690, 205, 80, 256, vbom);
		attachChild(snowmanBorder);
		snowmanBorder.setVisible(false);
		
		snowball = new Sprite(0, 0, resourcesManager.mSnowball,
				vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (this.getX() > 800
						|| this.getY() < -this.getHeight()) {
					GameManager.getInstance().shooted(false);
					GameManager.getInstance().resetPrev();
					this.clearEntityModifiers();
					this.setPosition(0, 0);
					this.setVisible(false);
				}
				if (this.collidesWith(snowmanBorder)) {
					setVisible(false);
					clearEntityModifiers();
					setPosition(0, 0);
					GameManager.getInstance().shooted(false);
					engine.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							chSnowman(false);
						}
					});
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		snowball.setVisible(false);

		attachChild(cannonBack);
		attachChild(cannonTube);
		attachChild(cannonFront);
		attachChild(cannonTarget);
		attachChild(snowball);
		
		AnimatedSprite santa = new AnimatedSprite(5, 265, resourcesManager.getTiledTextureFromPack("santaA"), vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (!this.isAnimationRunning() && !GameManager.getInstance().paused)
					animate(santaThinkFrameDuration, santaThinkFrame, true);
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		attachChild(santa);
		//santa.animate(santaThinkFrameDuration, santaThinkFrame, true);

		deer = new AnimatedSprite(-143, 50, resourcesManager.mDeer, vbom) {
			private Random rnd = new Random();
			private float interval = 7 + rnd.nextInt(3);
			private float timeEllapsed = 0;
			private MoveXModifier runDeer;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (!GameManager.getInstance().paused) {
					timeEllapsed += pSecondsElapsed;
					if (timeEllapsed > interval) {
						
						if (!this.isAnimationRunning())
							animate(deerRunFrameDuration, deerRunFrame, true); // WTF
						
						timeEllapsed = 0;
						interval = 6 + rnd.nextInt(4);

						if (rnd.nextBoolean()) {
							runDeer = new MoveXModifier(5, -143, 833);
							this.setFlippedHorizontal(false);
						} else {
							runDeer = new MoveXModifier(5, 800, -143);
							this.setFlippedHorizontal(true);
						}

						runDeer.setAutoUnregisterWhenFinished(true);
						this.registerEntityModifier(runDeer);
						Debug.d("There is no time to explain, I must run!");
					}
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		attachChild(deer);
		//deer.animate(deerRunFrameDuration, deerRunFrame, true);
		deer.setIgnoreUpdate(true);
		
		final Rectangle deerBorder = new Rectangle(0, 20, 100, 135, vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (this.collidesWith(snowball)) {
					snowball.setVisible(false);
					snowball.clearEntityModifiers();
					snowball.setPosition(-50, -50);

					explosion(getParent().getX(), getParent().getY(), getWidthScaled());
					deerShooted();
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		deerBorder.setVisible(false);
		deer.attachChild(deerBorder);
		
		chSnowman(true);
		
		addParticle();
	}
	
	private void chSnowman(boolean state) {
		if (snowman != null) {
			snowman.stopAnimation();
			snowman.detachSelf();
			snowman.dispose();
			snowman = null;
		}
		if (state) {
			resourcesManager.reloadSmanAsA();
			
			snowman = new AnimatedSprite(605, 205, resourcesManager.mSmanA, vbom) {
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					if (!GameManager.getInstance().paused) {
						if (!isAnimationRunning())
							animate(smanStayFrameDuration, smanStayFrame, true);
					}
					super.onManagedUpdate(pSecondsElapsed);
				}
			};
			 
		} else {
			resourcesManager.reloadSmanAsB();
			
			snowman = new AnimatedSprite(605, 205, resourcesManager.mSmanA, vbom);
			snowman.animate(smanAngryFrameDuration, smanAngryFrame, true);
			registerUpdateHandler(new TimerHandler(3, false, new ITimerCallback() {
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler) {
					chSnowman(true);
				}
			}));
		}

		attachChild(snowman);
	}

	private void addParticle() {
		IParticleModifier<UncoloredSprite> shootedModifier = new IParticleModifier<UncoloredSprite>() {
			@Override
			public void onInitializeParticle(Particle<UncoloredSprite> pParticle) {
			}

			@Override
			public void onUpdateParticle(Particle<UncoloredSprite> pParticle) {
				Sprite particle = pParticle.getEntity();

				if (particle.collidesWith(snowball)) {
					snowball.setVisible(false);
					snowball.clearEntityModifiers();
					snowball.setPosition(-50, -50);
					GameManager.getInstance().shooted(true);
					pParticle.setExpired(true);

					explosion(particle.getX(), particle.getY(),
							particle.getWidthScaled());
				}
				if (pParticle.getEntity().getY() > 800) {
					pParticle.setExpired(true);
				}
			}
		};
		AccelerationParticleInitializer<UncoloredSprite> acceleration = new AccelerationParticleInitializer<UncoloredSprite>(
				2, -2, 5, 15);
		ScaleParticleInitializer<UncoloredSprite> scale = new ScaleParticleInitializer<UncoloredSprite>(
				.4f, .9f);

		RectangleParticleEmitter emitter = new RectangleParticleEmitter(450,
				-20, 350, 20);

		particleSystemA = new BatchedSpriteParticleSystem(emitter, 5, 10, 2,
				resourcesManager.mGiftA, vbom);
		particleSystemB = new BatchedSpriteParticleSystem(emitter, 5, 10, 2,
				resourcesManager.mGiftB, vbom);
		particleSystemC = new BatchedSpriteParticleSystem(emitter, 5, 10, 2,
				resourcesManager.mGiftC, vbom);

		particleSystemA.addParticleInitializer(acceleration);
		particleSystemA.addParticleInitializer(scale);
		particleSystemB.addParticleInitializer(acceleration);
		particleSystemB.addParticleInitializer(scale);
		particleSystemC.addParticleInitializer(acceleration);
		particleSystemC.addParticleInitializer(scale);

		particleSystemA.addParticleModifier(shootedModifier);
		particleSystemB.addParticleModifier(shootedModifier);
		particleSystemC.addParticleModifier(shootedModifier);

		attachChild(particleSystemA);
		attachChild(particleSystemB);
		attachChild(particleSystemC);

		particleSystemA.setIgnoreUpdate(true);
		particleSystemB.setIgnoreUpdate(true);
		particleSystemC.setIgnoreUpdate(true);
	}

	private void shoot() {
		GameManager.getInstance().makeShoot();

		final float[] shootCoord = cannonTube.convertLocalToSceneCoordinates(
				147, 15);
		snowball.setPosition(shootCoord[0] - snowball.getHeight() / 2,
				shootCoord[1] - snowball.getWidth() / 2);
		snowball.setVisible(true);

		final float cannonTubeX = cannonTube.getRotationCenterX()
				+ cannonTube.getX();
		final float cannonTubeY = cannonTube.getRotationCenterY()
				+ cannonTube.getY();

		final float nevX = (float) Math.cos(MathUtils.degToRad(targetAngle)) * 715 + 20;
		final float newY = (float) Math.sin(MathUtils.degToRad(targetAngle)) * 715 + 40;

		snowball.clearEntityModifiers();
		snowball.registerEntityModifier(new JumpModifier(1.75f, shootCoord[0]
				- snowball.getHeight() / 2, nevX + cannonTubeX, shootCoord[1]
				- snowball.getWidth() / 2, newY + cannonTubeY, 40));
	}

	private void explosion(final float x, final float y, final float w) {
		final int mNumPart = 15;
		final float mTimePart = 1.3f;

		PointParticleEmitter particleEmitter = new PointParticleEmitter(x + w / 2, y + w / 2);
		IEntityFactory<Sprite> recFact = new IEntityFactory<Sprite>() {
			@Override
			public Sprite create(float pX, float pY) {
				Sprite sprite = new Sprite(x + w / 2, y + w / 2, resourcesManager.mGiftExplode, vbom);
				return sprite;
			}
		};
		final ParticleSystem<Sprite> particleSystem = new ParticleSystem<Sprite>(
				recFact, particleEmitter, 500, 500, mNumPart);

		particleSystem
				.addParticleInitializer(new ColorParticleInitializer<Sprite>(
						.2f, .8f, .2f, .8f, .2f, .8f));
		particleSystem
				.addParticleInitializer(new VelocityParticleInitializer<Sprite>(
						-50, 50, -50, 50));
		particleSystem
				.addParticleInitializer(new ScaleParticleInitializer<Sprite>(
						.7f));

		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0,
				0.6f * mTimePart, 1, 0));
		particleSystem
				.addParticleModifier(new RotationParticleModifier<Sprite>(0,
						mTimePart, 0, 360));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0,
				mTimePart, 1, .3f));

		attachChild(particleSystem);
		registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				particleSystem.detachSelf();
				sortChildren();
				unregisterUpdateHandler(pTimerHandler);
			}
		}));
		
		if (random.nextBoolean() && random.nextBoolean()) {
			int bonus = random.nextInt(5) + GameManager.getInstance().getScore()/50 + 2;
			Text text = new Text(x + w / 2, y + w / 2, resourcesManager.mFont, "+"+bonus, vbom);
			GameManager.getInstance().addScore(bonus);
			text.registerEntityModifier(new MoveByModifier(1, 0, 50));
			text.registerEntityModifier(new AlphaModifier(1, 1, 0));
			attachChild(text);
		}
	}

	public void start() {
		GameManager.getInstance().paused = false;
		backgroundTouch.setChildrenIgnoreUpdate(false);
		particleSystemA.setIgnoreUpdate(false);
		particleSystemB.setIgnoreUpdate(false);
		particleSystemC.setIgnoreUpdate(false);
		cannonTube.setIgnoreUpdate(false);
		snowball.setIgnoreUpdate(false);
		deer.setIgnoreUpdate(false);
		registerTouchArea(backgroundTouch);
	}

	public void pause() {
		GameManager.getInstance().paused = true;
		backgroundTouch.setChildrenIgnoreUpdate(true);
		particleSystemA.setIgnoreUpdate(true);
		particleSystemB.setIgnoreUpdate(true);
		particleSystemC.setIgnoreUpdate(true);
		cannonTube.setIgnoreUpdate(true);
		snowball.setIgnoreUpdate(true);
		deer.setIgnoreUpdate(true);
		
		chSnowman(true);
		snowman.animate(smanPointFrameDuration, smanPointFrame, true);
		snowman.setX(snowman.getX()-55);
		
		unregisterTouchArea(backgroundTouch);
	}

	private void deerShooted() {
		GameManager.getInstance().paused = true;
		backgroundTouch.setChildrenIgnoreUpdate(true);
		particleSystemA.setIgnoreUpdate(true);
		particleSystemB.setIgnoreUpdate(true);
		particleSystemC.setIgnoreUpdate(true);
		cannonTube.setIgnoreUpdate(true);
		snowball.setIgnoreUpdate(true);
		unregisterTouchArea(backgroundTouch);
		deer.clearEntityModifiers();
		deer.clearUpdateHandlers();
		gameOver();
		deer.animate(deerCryFrameDuration, deerCryFrame, true);
		deer.setIgnoreUpdate(false);
	}

	private void gameOver() {
		pause();
		deer.clearEntityModifiers();
		deer.clearUpdateHandlers();

		camera.setHUD(null);
		Sprite gameOver = new Sprite(0, 0, resourcesManager.mGameOver, vbom);
		
		Rectangle btnOk = new Rectangle(260, 290, 260, 45, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							SceneManager.getInstance().loadMenuScene(engine);
						}
					});
					
				}
				return super.onAreaTouched(pSceneTouchEvent, x, y);
			}
		};
		
		Text userName = new Text(285, 205, resourcesManager.mFont, "AAAAAAAAA", vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y) {
				if (pSceneTouchEvent.isActionDown()) {
					getUserName(this);
				}
				return super.onAreaTouched(pSceneTouchEvent, x, y);
			}
		};
		userName.setText(UserData.getInstance().getUserName());

		attachChild(gameOver);
		attachChild(userName);
		attachChild(new Text(285, 245, resourcesManager.mFont, "" + GameManager.getInstance().getScore(), vbom));
		registerTouchArea(userName);
		registerTouchArea(btnOk);
	}
	
	void getUserName(final Text userName) {
		final EditText input = new EditText(activity);
		InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(9);
        input.setFilters(filters);
        
		final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				alert.setTitle("Enter new Nickname");
				alert.setMessage("New Nickname");
				alert.setView(input);
				alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								UserData.getInstance().setUserName(input.getText().toString());
								userName.setText(input.getText().toString());
							}
						});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {}
						});

				alert.show();
			}
		});
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case 0:
				mMenuScene.back();
				start();
				return true;
			case 1:
				camera.setHUD(null);
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						SceneManager.getInstance().loadMenuScene(engine);
					}
				});
				return true;
			default:
				return false;
		}
	}
	
	protected MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(camera);

		final IMenuItem resetMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, resourcesManager.mFont, "RESUME", vbom), new Color(0,.8f,.85f), new Color(1,1,1));
		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(resetMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1, resourcesManager.mFont, "QUIT", vbom), new Color(0,.8f,.85f), new Color(1,1,1));
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(quitMenuItem);

		menuScene.buildAnimations();

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		return menuScene;
	}
}
