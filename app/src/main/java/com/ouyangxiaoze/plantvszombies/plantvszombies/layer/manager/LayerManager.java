package com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager;

import java.util.HashMap;
import java.util.Map;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

public class LayerManager {
	private static LayerManager instance = new LayerManager();
	private CCScene scene;
	private static Map<String, BaseLayer> LAYERS = new HashMap<String, BaseLayer>();

	private LayerManager() {
		scene = CCScene.node();
	}

	public static LayerManager getInstance() {
		return instance;
	}

	public boolean changeLayer(BaseLayer targetLayer) {
		scene.removeAllChildren(true);
		scene.addChild(targetLayer);

		CCDirector director = CCDirector.sharedDirector();
		director.runWithScene(scene);
		return true;
	}

	public boolean changeLayer(Class<? extends BaseLayer> targetLayerClazz) {
		String simpleName = targetLayerClazz.getSimpleName();
		BaseLayer targetLayer = null;
		if (LAYERS.containsKey(simpleName)) {
			targetLayer = LAYERS.get(simpleName);
		} else {
			try {
				targetLayer = targetLayerClazz.newInstance();
				LAYERS.put(simpleName, targetLayer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		scene.removeAllChildren(true);
		scene.addChild(targetLayer);

		return false;
	}
	/**
	 * 场景的切换
	 * @param targetLayerClazz
	 * @param transport
	 * @return
	 */
	public boolean changeLayer(Class<? extends BaseLayer> targetLayerClazz, int transport) {
		String simpleName = targetLayerClazz.getSimpleName();
		BaseLayer targetLayer = null;
		if (LAYERS.containsKey(simpleName)) {
			targetLayer = LAYERS.get(simpleName);
		} else {
			try {
				targetLayer = targetLayerClazz.newInstance();
				LAYERS.put(simpleName, targetLayer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (targetLayer != null) {
			CCTransitionScene transitionScene = null;
			CCScene newScene=null;

			CCDirector director = CCDirector.sharedDirector();

			switch (transport) {
				case 0:
					break;
				default:
					newScene=CCScene.node();
					newScene.addChild(targetLayer);
					transitionScene = CCFadeTransition.transition(1, newScene);
					break;
			}
			if (transitionScene != null) {
				director.pushScene(transitionScene);
			} else {
				scene.removeAllChildren(true);
				scene.addChild(targetLayer);
				director.runWithScene(scene);
			}

			return true;
		}

		return false;
	}

	/**
	 * 场景的切换
	 * @param targetLayerClazz
	 * @param transport
	 * @return
	 */
	public boolean changeLayerByTransitionSceneType(Class<? extends BaseLayer> targetLayerClazz) {
		String simpleName = targetLayerClazz.getSimpleName();
		BaseLayer targetLayer = null;
		if (LAYERS.containsKey(simpleName)) {
			targetLayer = LAYERS.get(simpleName);
		} else {
			try {
				targetLayer = targetLayerClazz.newInstance();
				LAYERS.put(simpleName, targetLayer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (targetLayer != null) {
			CCTransitionScene transitionScene = null;
			CCScene newScene=null;

			CCDirector director = CCDirector.sharedDirector();

			switch (targetLayer.getTransitionSceneType()) {
				case 0:
					scene.removeAllChildren(true);
					scene.addChild(targetLayer);
					break;
				default:
					newScene=CCScene.node();
					newScene.addChild(targetLayer);
					transitionScene = CCFadeTransition.transition(1, newScene);
					director.pushScene(transitionScene);
					break;
			}
			return true;
		}

		return false;
	}
}
