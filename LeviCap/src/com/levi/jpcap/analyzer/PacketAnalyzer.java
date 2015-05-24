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
    	   sb.append("数 据 包 信 息 "+"\n");
    	   sb.append("抓取时间:"+mypacket.getDate()+"\n");
    	   sb.append("数据包大小:"+packet.len+"\n");
    	   sb.append("I P 信 息 "+"\n");
    	   if(packet instanceof IPPacket){
    	   IPPacket ip = (IPPacket) packet;
    	   sb.append("IP版本:"+ip.version+"\n");
    	   sb.append("发送源IP地址:"+ip.src_ip+"\n");
    	   sb.append("发送目标IP地址："+ip.dst_ip+"\n");
    	   }
    	   if(packet instanceof TCPPacket){
    		   TCPPacket tcp = (TCPPacket) packet;
    		   sb.append("T C P 包 信 息 "+"\n");
        	   sb.append("源端口"+tcp.src_port+"\n");
        	   sb.append("目标端口:"+tcp.dst_port+"\n");
        	   sb.append("包长度"+tcp.length+"\n");
    	   }
    	   if(packet instanceof UDPPacket){
    		   UDPPacket udp = (UDPPacket) packet;
    		   sb.append("U D P 包 信 息 "+"\n");
        	   sb.append("源端口:"+udp.src_port+"\n");
        	   sb.append("目标端口:"+udp.dst_port+"\n");
        	   sb.append("包长度"+udp.length+"\n");
    	   }
		return sb.toString(); 
       }
}
