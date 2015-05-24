package com.levi.jpcap.swing;
import java.awt.EventQueue;
/**
 * @author levi
 */
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

import org.apache.log4j.Logger;

import com.levi.jpcap.action.ExitAction;
import com.levi.jpcap.action.OpenAction;
import com.levi.jpcap.action.SaveAction;
import com.levi.jpcap.action.StartAction;
import com.levi.jpcap.action.StopAction;
import com.levi.jpcap.analyzer.PacketAnalyzer;
import com.levi.jpcap.bean.MyPacket;
import com.levi.jpcap.util.ByteUtil;

public class LeviCatcher{
	private JFrame frame;
	private JComboBox<String> comboBox_filter;
	private JMenuBar menuBar;
	private JMenu menu_file;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem ;
	private JMenuItem exitMenuItem;
	private JMenu mnMenu_other ;
	private JMenuItem mntmNewMenuItem_3 ;
	private JMenuItem mntmNewMenuItem_4;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem_5;
	private JMenu menu_option ;
	private JComboBox<String> comboBox_device;
	private JButton button_start;
	private JButton button_stop ;
	private JButton button_save;
	private JButton button_read;
	private JTable jTable_list ;
	private JTextPane textPane_info ;
	private JTextPane textPane_data;
	private JLabel label_device ;
	private JLabel label_filter ;
	private DefaultTableModel defaultTableModel;
	private NetworkInterface[] devices;
	private String[] devicesname;
	public static JpcapCaptor jCaptor ;
	public static List<MyPacket> packetList = new ArrayList<MyPacket>();
	private  Logger log = Logger.getLogger("log");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeviCatcher window = new LeviCatcher();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	public LeviCatcher() {
		log.info("���ڼ��ؽӿ�����");
		initializeInterface();
		log.info("���ڼ��ؽӿ�������");
		initialize();
		log.info("����Ϊ��ʼ����ť�¼�");
		initializeButton();
	}
	/*Ϊ��ť��Ӽ����¼�*/
	private void initializeButton(){
		button_start.addActionListener(new StartAction(comboBox_device,comboBox_filter,devices,jTable_list,
				defaultTableModel));
		button_stop.addActionListener(new StopAction());
		button_save.addActionListener(new SaveAction(frame));
		button_read.addActionListener(new OpenAction(frame,jTable_list,defaultTableModel));
		openMenuItem.addActionListener(new OpenAction(frame,jTable_list,defaultTableModel));
		saveMenuItem.addActionListener(new SaveAction(frame));
		exitMenuItem.addActionListener(new ExitAction());
		
	}
	/* ��ʼ������ */
	private void initialize(){
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 546, 407);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		menu_file = new JMenu("�ļ�");
		menuBar.add(menu_file);
		
		openMenuItem = new JMenuItem("��");
		menu_file.add(openMenuItem);
		
		saveMenuItem = new JMenuItem("����");
		menu_file.add(saveMenuItem);
		
		exitMenuItem = new JMenuItem("�˳�");
		menu_file.add(exitMenuItem);
		
		mnMenu_other = new JMenu("����");
		menuBar.add(mnMenu_other);
		
		mntmNewMenuItem_3 = new JMenuItem("New menu item");
		mnMenu_other.add(mntmNewMenuItem_3);
		
		mntmNewMenuItem_4 = new JMenuItem("New menu item");
		mnMenu_other.add(mntmNewMenuItem_4);
		
		mnNewMenu = new JMenu("menu ");
		mnMenu_other.add(mnNewMenu);
		
		mntmNewMenuItem_5 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_5);
		
		menu_option = new JMenu("����");
		menuBar.add(menu_option);
		frame.getContentPane().setLayout(null);
		
		comboBox_device = new JComboBox<String>(devicesname);
		comboBox_device.setBounds(46, 0, 89, 21);
		frame.getContentPane().add(comboBox_device);
		
		button_start = new JButton("\u5F00\u59CB");
		button_start.setBounds(281, -1, 56, 23);
		
		frame.getContentPane().add(button_start);
		
		button_stop = new JButton("\u505C\u6B62");
		button_stop.setBounds(342, -1, 56, 23);
		
		frame.getContentPane().add(button_stop);
		
		button_save = new JButton("\u4FDD\u5B58");
		button_save.setBounds(397, -1, 56, 23);
		frame.getContentPane().add(button_save);
		
		button_read = new JButton("\u6253\u5F00");
		button_read.setBounds(455, -1, 56, 23);
		frame.getContentPane().add(button_read);
		
		
		jTable_list = new JTable(new Object[][]{},new Object[]{"NO.","ԴMAC","Ŀ��MAC","ԴIP","Ŀ��IP"});
		defaultTableModel = new DefaultTableModel(new Object[][]{}, new Object[]{"NO","ԴMAC","Ŀ��MAC","ԴIP","Ŀ��IP"});
		jTable_list.addMouseListener(new TableMouseEvent());
		JScrollPane jsp_list= new JScrollPane(jTable_list);
		jsp_list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jsp_list.setBackground(SystemColor.info);
		jsp_list.setBounds(10, 23, 520, 143);
		frame.getContentPane().add(jsp_list);
		
		textPane_info = new JTextPane();
		textPane_info.setEditable(false);
		JScrollPane jsp_info= new JScrollPane(textPane_info);
		textPane_info.setBackground(SystemColor.info);
		textPane_info.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jsp_info.setBounds(10, 176, 191, 171);
		frame.getContentPane().add(jsp_info);
		
		textPane_data = new JTextPane();
		StyledDocument doc = textPane_data.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(center, 18);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		textPane_data.setEditable(false);
		textPane_data.setBackground(SystemColor.info);
		JScrollPane jsp_data= new JScrollPane(textPane_data);
		jsp_data.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jsp_data.setBounds(207, 176, 323, 171);
		frame.getContentPane().add(jsp_data);
		
		String[] filter = {"","ip","tcp","arp","udp"};
		comboBox_filter = new JComboBox<String>(filter);
		comboBox_filter.setBounds(180, 0, 89, 21);
		frame.getContentPane().add(comboBox_filter);
		
		label_device = new JLabel("\u7F51\u5361\uFF1A");
		label_device.setBounds(10, 3, 40, 15);
		frame.getContentPane().add(label_device);
		
		label_filter = new JLabel("\u8FC7\u6EE4\u5668\uFF1A");
		label_filter.setBounds(136, 3, 54, 15);
		frame.getContentPane().add(label_filter);
	}
	//��ʼ���ӿ�
	private void initializeInterface(){
		devices = JpcapCaptor.getDeviceList();
		devicesname = new String[devices.length];
		for(int i=0;i<devices.length;i++){
			devicesname[i]=devices[i].description;
		}
	}
	
	//���ǽ�byte����ת��ΪString
	//�����б����¼�
	class TableMouseEvent implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			//��ȡѡ����
			int t = jTable_list.getSelectedRow();
			MyPacket mypacket = packetList.get(t);
			Packet packet = mypacket.getPacket();
			byte[] data = packet.data;
			String s = ByteUtil.byteToString(data).toUpperCase();
			textPane_data.setText(s);
			
			//��ʾ��ϸ�İ���Ϣ
			textPane_info.setText(PacketAnalyzer.packetAnalyze(mypacket));
		}
		public String getPacketInfo(Packet packet){
			StringBuffer sbf = new StringBuffer();
			sbf.append("len:"+packet.len+"\n");
			sbf.append("sec:"+packet.sec+"\n");
			return sbf.toString();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
}
