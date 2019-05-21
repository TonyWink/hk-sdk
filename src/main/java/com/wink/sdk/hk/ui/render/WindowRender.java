package com.wink.sdk.hk.ui.render;

import javax.swing.JButton;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class WindowRender {

	public static void render(){
		//边框样式
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
		//移除默认设置按钮
		UIManager.put("RootPane.setupButtonVisible", false);
		//关闭透明效果
		BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
		try {
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void  changeButtonStyle(JButton button,BEButtonUI.NormalColor color){
		button.setUI(new BEButtonUI().setNormalColor(color));
	}
}
