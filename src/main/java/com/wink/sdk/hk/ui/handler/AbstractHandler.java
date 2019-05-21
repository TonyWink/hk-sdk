package com.wink.sdk.hk.ui.handler;

import javax.swing.JTextPane;

import com.wink.sdk.hk.func.HCNetSdkFunc;
import com.wink.sdk.hk.ui.MessageBox;
import com.wink.sdk.hk.util.SdkErrorUtil;

public abstract class AbstractHandler {
	
	protected AbstractHandler next;
	protected static JTextPane console;
	protected static HCNetSdkFunc hcSdk;
	
	public static void init(JTextPane console){
		AbstractHandler.console=console;
	}
	
	public void setNext(AbstractHandler next) {
		this.next = next;
	}
	
	public JTextPane getConsole() {
		return console;
	}
	
	public HCNetSdkFunc getNetSdk(){
		return hcSdk;
	}

	public void handleRequest(boolean success){
		if(success){
			success=doRequest();
			if(success){
			   MessageBox.log(console,"设备操作成功.");
			}
			if(next != null){
				next.handleRequest(success);
			}
		}else{
			MessageBox.logError(console,SdkErrorUtil.getHCSadpErrorMsg());
		}
	}
	
	abstract protected boolean doRequest();
}
