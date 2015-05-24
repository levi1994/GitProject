package com.levi.jpcap.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.log4j.Logger;

public class StopAction implements ActionListener{
	Logger log = Logger.getLogger("log");
	public StopAction() {
       
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		StartAction.isLiveCapture =  false;
		log.debug("×¥°üÍ£Ö¹");
	}

}
