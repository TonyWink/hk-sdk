
package com.wink.sdk.hk.ui;

import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.wink.sdk.hk.ui.handler.AbstractHandler;
import com.wink.sdk.hk.ui.handler.ActiveDeviceHandler;
import com.wink.sdk.hk.ui.handler.DeviceNetParamHandler;
import com.wink.sdk.hk.ui.vo.DeviceVo;
import com.wink.sdk.hk.ui.vo.NetParamVo;
import com.wink.sdk.hk.util.ConvertUtil;

import cn.hutool.poi.excel.ExcelUtil;

/**
 * 
 * @ClassName:  WinHCSadpActiveDevice   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: ShenYue 
 * @date:   2019年4月30日 上午11:12:01
 */
public class WinHCSadpActiveDevice extends javax.swing.JFrame {

	private static final long serialVersionUID = 2658391598049771347L;
	private List<DeviceVo> unactiveDevices;
	private List<NetParamVo> netParams;
	
	private static final String [] TABLE_HEADER_NAME= new String [] {"设备序列号", "IP", "网关", "子网掩码","MAC", "版本", "激活状态"};
	
	public WinHCSadpActiveDevice() {
        initComponents();
    }
                       
    private void initComponents() {

    	this.setResizable(false);
    	this.setLocation(400, 200);
    	
        panel = new javax.swing.JPanel();
        avctiveButton = new javax.swing.JButton();
        importConfigButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        deviceTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        avctiveButton.setEnabled(false);
        avctiveButton.setText("开始激活");
        avctiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	avctiveButtonEvt(evt);
            }
        });

        importConfigButton.setText("导入配置");
        importConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	importConfigButtonEvt(evt);
            }
        });

        deviceTable.setModel(new javax.swing.table.DefaultTableModel(
            ConvertUtil.toObjArray(unactiveDevices),
            TABLE_HEADER_NAME
        ));
        scrollPane.setViewportView(deviceTable);

        jLabel1.setText("设备列表");

        jScrollPane2.setViewportView(console);

        jLabel2.setText("实时日志");
        
        AbstractHandler.init(console);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(avctiveButton)
                            .addGap(36, 36, 36)
                            .addComponent(importConfigButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(avctiveButton)
                            .addComponent(importConfigButton))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)))
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
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

    private void avctiveButtonEvt(java.awt.event.ActionEvent evt) { 	
    	new Thread(new Runnable() {
			@Override
			public void run() {
		    	for(int device_index=0;device_index<unactiveDevices.size();device_index++){
	    		    ActiveDeviceHandler activeHandler = new ActiveDeviceHandler(unactiveDevices.get(device_index),netParams.get(device_index));
	    		    DeviceNetParamHandler netParamHandler = new DeviceNetParamHandler(unactiveDevices.get(device_index),netParams.get(device_index));
	    		    activeHandler.setNext(netParamHandler);
	    		    activeHandler.handleRequest(true);
		    	}
			}
		}).start();
    }
    
    private void importConfigButtonEvt(java.awt.event.ActionEvent evt) { 
      // 导入Excel配置
      fileChooser = new JFileChooser();
      //初始化当前路径
      FileSystemView fsv = FileSystemView.getFileSystemView();
      File homeFile =fsv.getHomeDirectory();
      fileChooser.setCurrentDirectory(homeFile);
      
      //初始化文件过滤器
      FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL文件","xls","xlsx");
      fileChooser.setFileFilter(filter);
 
      //初始化选择模式
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      //是否允许多选
      fileChooser.setMultiSelectionEnabled(false);
      //打开文件选择器
      int i = fileChooser.showDialog(this, "选择");
      if(i == JFileChooser.APPROVE_OPTION){
     	 netParams.clear();
     	 File file = fileChooser.getSelectedFile();
     	 List<NetParamVo> importNetParams=ExcelUtil.getReader(file,0).readAll(NetParamVo.class);
     	 if(!importNetParams.isEmpty()){
     	   avctiveButton.setEnabled(true);
     	   MessageBox.check(importNetParams.size()==unactiveDevices.size(),"配置数量不一致,导入配置{}项,未激活设备{}项",importNetParams.size(),unactiveDevices.size());
     	   MessageBox.tip("导入"+netParams.size()+" 个配置项");
     	 }
      }
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton avctiveButton;
    private javax.swing.JButton importConfigButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable deviceTable;
    private javax.swing.JTextPane console;
    private javax.swing.JFileChooser fileChooser;
    // End of variables declaration                   
}
