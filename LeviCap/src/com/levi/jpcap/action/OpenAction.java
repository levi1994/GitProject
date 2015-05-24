package com.levi.jpcap.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

import org.apache.log4j.Logger;

import com.levi.jpcap.bean.MyPacket;
import com.levi.jpcap.swing.LeviCatcher;
import com.levi.jpcap.util.ByteUtil;

public class OpenAction implements ActionListener {
	private JFileChooser jFileChooser;
	private JFrame jFrame;
	private JTable jTable_list;
	private DefaultTableModel defaultTableModel;
	private  Logger log = Logger.getLogger("log");
	public OpenAction(JFrame jFrame,JTable jTable_list,
			DefaultTableModel defaultTableModel) {
		this.jFrame = jFrame;
		this.jTable_list = jTable_list;
		this.defaultTableModel = defaultTableModel;
	}

	/*
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 使用该方法从文件中读入list对象
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		jFileChooser = new JFileChooser();
		log.info("正在加载文件选择窗口");
		int ret = jFileChooser.showOpenDialog(jFileChooser);
		log.info("ret="+ret);
		File file = null;
		if (ret == JFileChooser.APPROVE_OPTION) {
			file = jFileChooser.getSelectedFile();
		}
		//添加限制条件，避免报错
		if(file==null) {
			log.debug("尚未选择文件");
			return;
		}
		log.info("filepath:"+file.getPath());
		try {
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(in);
			Object readObject=null;
			try {
				readObject = ois.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			final List<MyPacket> list =(List<MyPacket>) readObject;
			LeviCatcher.packetList = list;
			log.debug("文件读取成功");
			in.close();
			ois.close();
			
		   /*刷新窗口*/
			log.debug("正在刷新窗口");
			Thread flush = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(MyPacket mypacket : list){
						Packet packet = mypacket.getPacket();
						/*将数据显示到列表*/
						Vector<String> vectorRow = new Vector<String>();
						/*以下逻辑存在问题，需要重写*/
						/*对单个包进行分析，根据类型获取信息*/
						vectorRow.addElement(mypacket.getIndex() + "");
						if (packet instanceof IPPacket) {
							IPPacket ipPacket = (IPPacket) packet;
							String src_ip;
							String dst_ip;
							try{
							src_ip = ipPacket.src_ip.toString().substring(1);
							}catch(NullPointerException e){
								log.error("src_ip无法获取");
								src_ip ="not found";
							}
							try{
								dst_ip = ipPacket.dst_ip.toString().substring(1);
								}catch(NullPointerException e){
									log.error("src_ip无法获取");
									dst_ip ="not fount";
								}
							
							EthernetPacket arpPacket = (EthernetPacket) packet.datalink;
							String src_mac = ByteUtil.macToString(arpPacket.src_mac);
							String dst_mac = ByteUtil.macToString(arpPacket.dst_mac);
							vectorRow.addElement(src_mac);
							vectorRow.addElement(dst_mac);
							vectorRow.addElement(src_ip);
							vectorRow.addElement(dst_ip);
						}
						defaultTableModel.addRow(vectorRow);
						jTable_list.setModel(defaultTableModel);
						jTable_list.revalidate();
						//核心逻辑执行完毕
						Thread.yield();
					}
				}
			});
			flush.start();
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
			log.error("文件读取失败");
			JOptionPane.showMessageDialog(
				jFrame,
				"Can't save file: " + file.getPath());
		}
	}

}
