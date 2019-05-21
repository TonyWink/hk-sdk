package com.wink.sdk.hk.ui;

import java.awt.Color;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import com.wink.sdk.hk.util.SdkErrorUtil;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;

public class MessageBox {

	public static  boolean checkHCNetError(){
		String msg=SdkErrorUtil.getHCNetErrorMsg();
    	if(msg !=null){
    		MessageBox.error(msg);
    		return false;
    	}
    	return true;
	}
	
	public static  boolean checkHCSadpError(){
		String msg=SdkErrorUtil.getHCSadpErrorMsg();
    	if(msg !=null){
    		MessageBox.error(msg);
    		return false;
    	}
    	return true;
	}
	
	public static  void error(String msg,Object...params){
		;
		JOptionPane.showMessageDialog(null,StrFormatter.format(msg, params),"消息提示",JOptionPane.ERROR_MESSAGE);  
	    throw new RuntimeException(msg);
	}
	
	public static  void tip(String msg){
		JOptionPane.showMessageDialog(null,msg,"消息提示",JOptionPane.INFORMATION_MESSAGE);  
	}
	
	public static  void log(JTextPane console,String msg){
        try {
			console.getDocument()
			       .insertString(console.getDocument().getLength(),
			        getConsoleHeader()+msg+"\r\n", 
			        console.getStyle("def"));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public static  void logWarn(JTextPane console,String msg){
		 try {
				console.getDocument()
				       .insertString(console.getDocument().getLength(),
				        getConsoleHeader()+msg+"\r\n", 
				        console.getStyle("warn"));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
	}
	
	public static  void logError(JTextPane console,String msg){
		 try {
				console.getDocument()
				       .insertString(console.getDocument().getLength(),
				    	getConsoleHeader()+msg+"\r\n", 
				        console.getStyle("error"));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
	}
	
	public static void check(boolean condition,String msg,Object...params){
		if(!condition){
			error(msg,params);
		}
	}
	
	private static String getConsoleHeader(){
		return "Console("+DateUtil.format(new Date(),DatePattern.NORM_TIME_PATTERN)+"):";
	}
	
	public static void initJTextPaneStyle(JTextPane pane){
		//def
		Style noStyle = pane.getStyledDocument().addStyle(null, null);
        StyleConstants.setFontFamily(noStyle, "verdana");
        StyleConstants.setFontSize(noStyle, 12);
        Style def=pane.addStyle("def", noStyle);
        
		//warn
        Style warn = pane.addStyle("warn",def);
        StyleConstants.setForeground(warn, Color.blue);
        
		//error
        Style error = pane.addStyle("error",def);
        StyleConstants.setForeground(error, Color.red);
	}
}
