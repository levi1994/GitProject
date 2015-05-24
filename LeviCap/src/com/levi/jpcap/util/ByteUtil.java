package com.levi.jpcap.util;

/**
 * 
 * @author levi
 *这是二进制文件处理工具类
 *
 */

public class ByteUtil {
	/*将二进制mac转换为String 类型的mac地址*/
	public static String macToString(byte[] dst_mac){
		StringBuffer sb=new StringBuffer();
		int flag2 = 0;
		for (byte b : dst_mac) {
			flag2++;
			if (flag2 < dst_mac.length) {
				sb.append(Integer.toHexString(b & 0xff) + ":");
			} else{
				sb.deleteCharAt(sb.length()-1);
			}
			
		}
		String string = sb.toString();
		String[] array = string.split(":");
		String result = "";
		for(int i=0;i<array.length;i++){
			if(array[i].length()<2)  array[i]="0"+array[i];
			result=result+array[i]+":";
		}
		
		return result.substring(0, result.length()-2).toUpperCase();
	}
	//将二进制文件转换为16进制便于查看
	public static String byteToString(byte[] bytes){
		StringBuffer sb=new StringBuffer();
		int flag = 0;
		for (byte b : bytes) {
			flag++;
			if (flag < bytes.length) {
				sb.append(Integer.toHexString(b & 0xff) + " ");
			} else{
				sb.deleteCharAt(sb.length()-1);
			}
		}
		String string = sb.toString();
		String[] array = string.split(" ");
		String result = "";
		for(int i=0;i<array.length;i++){
			if(array[i].length()<2)  array[i]="0"+array[i];
			if(i%10==0&&i!=0){
				result+="\n";
			}
			result=result+array[i]+" ";
		}
		return result;
	}
}
