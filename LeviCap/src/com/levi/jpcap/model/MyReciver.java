package com.levi.jpcap.model;

import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import jpcap.PacketReceiver;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

import com.levi.jpcap.bean.MyPacket;
import com.levi.jpcap.swing.LeviCatcher;
import com.levi.jpcap.util.ByteUtil;

public class  MyReciver implements PacketReceiver {
	int t = 0;
	private JTable jTable_list;
	private DefaultTableModel defaultTableModel;
	private  Logger log = Logger.getLogger("log");

	public MyReciver() {
		// TODO Auto-generated constructor stub
	}

	public MyReciver(JTable jTable_list,
			DefaultTableModel defaultTableModel) {
       this.defaultTableModel = defaultTableModel;
       this.jTable_list = jTable_list;
	}

	@Override
	synchronized public void  receivePacket(Packet packet) {
		// TODO Auto-generated method stub
		t = LeviCatcher.packetList.size();
		log.info("���յ�һ�����ݰ�"+t);
		MyPacket pac = new MyPacket();
		pac.setPacket(packet);
		pac.setIndex(t);
		pac.setDate(new Date());
		t++;
		LeviCatcher.packetList.add(pac);
		DatalinkPacket d = packet.datalink;

		/*��������ʾ���б�*/
		Vector<String> vectorRow = new Vector<String>();
		/*�����߼��������⣬��Ҫ��д*/
		/*�Ե��������з������������ͻ�ȡ��Ϣ*/
		if (packet instanceof IPPacket) {
			IPPacket ipPacket = (IPPacket) packet;
			String src_ip;
			String dst_ip;
			try{
			src_ip = ipPacket.src_ip.toString().substring(1);
			}catch(NullPointerException e){
				log.error("src_ip�޷���ȡ");
				src_ip ="not found";
			}
			try{
				dst_ip = ipPacket.dst_ip.toString().substring(1);
				}catch(NullPointerException e){
					log.error("src_ip�޷���ȡ");
					dst_ip ="not fount";
				}
			
			EthernetPacket arpPacket = (EthernetPacket) d;
			String src_mac = ByteUtil.macToString(arpPacket.src_mac);
			String dst_mac = ByteUtil.macToString(arpPacket.dst_mac);
			vectorRow.addElement(pac.getIndex() + "");
			vectorRow.addElement(src_mac);
			vectorRow.addElement(dst_mac);
			vectorRow.addElement(src_ip);
			vectorRow.addElement(dst_ip);
		}
		defaultTableModel.addRow(vectorRow);
		jTable_list.setModel(defaultTableModel);
		jTable_list.revalidate();

	}
}
