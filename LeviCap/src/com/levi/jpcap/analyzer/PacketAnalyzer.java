package com.levi.jpcap.analyzer;

import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

import com.levi.jpcap.bean.MyPacket;


public class PacketAnalyzer {
       public static String packetAnalyze(MyPacket mypacket){
    	   Packet packet = mypacket.getPacket();
    	   StringBuffer sb = new StringBuffer();
    	   sb.append("�� �� �� �� Ϣ "+"\n");
    	   sb.append("ץȡʱ��:"+mypacket.getDate()+"\n");
    	   sb.append("���ݰ���С:"+packet.len+"\n");
    	   sb.append("I P �� Ϣ "+"\n");
    	   if(packet instanceof IPPacket){
    	   IPPacket ip = (IPPacket) packet;
    	   sb.append("IP�汾:"+ip.version+"\n");
    	   sb.append("����ԴIP��ַ:"+ip.src_ip+"\n");
    	   sb.append("����Ŀ��IP��ַ��"+ip.dst_ip+"\n");
    	   }
    	   if(packet instanceof TCPPacket){
    		   TCPPacket tcp = (TCPPacket) packet;
    		   sb.append("T C P �� �� Ϣ "+"\n");
        	   sb.append("Դ�˿�"+tcp.src_port+"\n");
        	   sb.append("Ŀ��˿�:"+tcp.dst_port+"\n");
        	   sb.append("������"+tcp.length+"\n");
    	   }
    	   if(packet instanceof UDPPacket){
    		   UDPPacket udp = (UDPPacket) packet;
    		   sb.append("U D P �� �� Ϣ "+"\n");
        	   sb.append("Դ�˿�:"+udp.src_port+"\n");
        	   sb.append("Ŀ��˿�:"+udp.dst_port+"\n");
        	   sb.append("������"+udp.length+"\n");
    	   }
		return sb.toString(); 
       }
}
