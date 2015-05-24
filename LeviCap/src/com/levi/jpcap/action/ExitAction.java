package com.levi.jpcap.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

public class ExitAction implements ActionListener{
	private Logger log = Logger.getLogger("log");
    public ExitAction() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		log.debug("ÍË³ö³ÌÐò");
		System.exit(0);
	}

}
