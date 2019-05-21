package com.wink.sdk.hk.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.sun.jna.Pointer;
import com.wink.sdk.hk.func.HCSadpSdkFunc;
import com.wink.sdk.hk.sdk.HCSadpSdk.PDEVICE_FIND_CALLBACK;
import com.wink.sdk.hk.sdk.HCSadpSdk.SADP_DEVICE_INFO;
import com.wink.sdk.hk.ui.render.WindowRender;
import com.wink.sdk.hk.ui.vo.DeviceVo;
import com.wink.sdk.hk.util.ConvertUtil;

import cn.hutool.core.lang.Assert;

/**
 * 
 * @ClassName:  WinHCSadpLauncher    
 * @Description: 海康SADP sdk 启动类  
 * @author: ShenYue 
 * @date:   2018年6月29日 下午7:52:04
 */
public class WinHCSadpLauncher extends javax.swing.JFrame {

	private static final long serialVersionUID = -600334410958525284L;
	private boolean isSearching=false;
	private List<DeviceVo> devices=new ArrayList<>();
	
	private static final String [] TABLE_HEADER_NAME= new String [] {"设备序列号", "IP", "网关", "子网掩码","MAC", "版本", "激活状态"};
	
    public WinHCSadpLauncher() {
        initComponents();
    }
                     
    private void initComponents() {

    	this.setResizable(false);
    	this.setLocation(400, 200);
    	
        panel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        deviceTable = new javax.swing.JTable();
        startSearchButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        stopSearchButton = new javax.swing.JButton();
        batchConfigButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        deviceTable.setModel(new javax.swing.table.DefaultTableModel(
        	ConvertUtil.toObjArray(devices),
        	TABLE_HEADER_NAME
        ));
        scrollPane.setViewportView(deviceTable);

        startSearchButton.setText("搜索设备");
        startSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	startSearchButtonEvt(evt);
            }
        });

        refreshButton.setEnabled(false);
        refreshButton.setText("刷新");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	refreshButtonEvt(evt);
            }
        });

        stopSearchButton.setEnabled(false);
        stopSearchButton.setText("停止搜索");
        stopSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	stopSearchButtonEvt(evt);
            }
        });

        batchConfigButton.setText("批量激活");
        WindowRender.changeButtonStyle(batchConfigButton,BEButtonUI.NormalColor.lightBlue);
        batchConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	batchConfigButtonEvt(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(batchConfigButton)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startSearchButton)
                            .addGap(30, 30, 30)
                            .addComponent(refreshButton)
                            .addGap(28, 28, 28)
                            .addComponent(stopSearchButton))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(batchConfigButton)
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshButton)
                    .addComponent(stopSearchButton)
                    .addComponent(startSearchButton))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void batchConfigButtonEvt(java.awt.event.ActionEvent evt) { 
	   	List<DeviceVo> unactiveDevices=devices.stream().filter(d -> !"已激活".equals(d.getActiveStatus())).collect(Collectors.toList());
	    if(!unactiveDevices.isEmpty()){
	     	 MessageBox.tip("没有需要激活的设备！");
	    }else{
	    	java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new WinHCSadpActiveDevice().setVisible(true);
	            }
	        });
	    }
    }                                        

    private void startSearchButtonEvt(java.awt.event.ActionEvent evt) {                                         
	   	 // 设备搜索
	   	 boolean success=HCSadpSdkFunc.getInstance().startSearchDevices(new PDEVICE_FIND_CALLBACK() {
				@Override
				public void invoke(SADP_DEVICE_INFO lpDeviceInfo, Pointer pUserData) {
				   Map<String, Object> deviceInfo=ConvertUtil.struct2Map(lpDeviceInfo);
				   DeviceVo device=new DeviceVo(
						   (String)deviceInfo.get("szSerialNO"), 
						   (String)deviceInfo.get("szIPv4Address"), 
						   (String)deviceInfo.get("szIPv4Gateway"), 
						   (String)deviceInfo.get("szIPv4SubnetMask"), 
						   (String)deviceInfo.get("szMAC"),
						   (String)deviceInfo.get("szDeviceSoftwareVersion"), 
						   (byte)deviceInfo.get("byActivated"));
				   if(!devices.contains(device)){
				        devices.add(device);
				        refreshTable();
				   }
				}
	   	});
	   	if(!success){
	   		MessageBox.checkHCSadpError();
	   	}else{
	   		startSearch();
	   	}
    }   
   	
    private void refreshButtonEvt(java.awt.event.ActionEvent evt) {                                         
    	boolean success=HCSadpSdkFunc.getInstance().refreshDevices();
    	if(!success){
    		MessageBox.checkHCSadpError();
    	}
    }                                        

    private void stopSearchButtonEvt(java.awt.event.ActionEvent evt) {  
  	    boolean success=HCSadpSdkFunc.getInstance().stopSearchDevices();
	  	if(!success){
	  		MessageBox.checkHCSadpError();
	  	}else{
	  		devices.clear();
	  		refreshTable();
	  		stopSearch();
	  	}
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	WindowRender.render();
                new WinHCSadpLauncher().setVisible(true);
            }
        });
    }

   private void refreshTable(){
	   synchronized (this) {
			 deviceTable.removeAll();
			 deviceTable.setModel(new javax.swing.table.DefaultTableModel(
			        		ConvertUtil.toObjArray(devices),
			        		TABLE_HEADER_NAME
			 ));
	   }
    }
   
    private void startSearch(){
    	Assert.isFalse(isSearching,"设备搜索中.");
    	isSearching=true;
    	refreshButton.setEnabled(true);
    	stopSearchButton.setEnabled(true);
    }
    
    private void stopSearch(){
    	Assert.isTrue(isSearching,"设备搜索未开启.");
    	isSearching=false;
    	refreshButton.setEnabled(false);
    	stopSearchButton.setEnabled(false);
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton startSearchButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton stopSearchButton;
    private javax.swing.JButton batchConfigButton;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable deviceTable;
    // End of variables declaration                   
}
