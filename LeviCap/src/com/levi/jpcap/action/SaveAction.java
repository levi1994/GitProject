package com.levi.jpcap.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.levi.jpcap.swing.LeviCatcher;
public class SaveAction implements ActionListener {
	private JFileChooser jFileChooser;
	private JFrame jFrame;
	private  Logger log = Logger.getLogger("log");
	public SaveAction(JFrame jFrame) {
		// TODO Auto-generated constructor stub
		this.jFrame = jFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jFileChooser = new JFileChooser();
		int ret = jFileChooser.showSaveDialog(jFileChooser);
		File file = null;
		if (ret == JFileChooser.APPROVE_OPTION) {
			file = jFileChooser.getSelectedFile();
			if (file.exists()) {
				if (JOptionPane.showConfirmDialog(jFrame,
						"Overwrite " + file.getName() + "?", "Overwrite?",
						JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
					return;
				}
			}
		}
		if(file==null) {
			log.error("无法获取选择文件数据");
			return;
		}
		try {
			FileOutputStream os = new FileOutputStream(file);
			ObjectOutputStream oss = new ObjectOutputStream(os);
			oss.writeObject(LeviCatcher.packetList);
			os.close();
			oss.close();
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(
				jFrame,
				"Can't save file: " + file.getPath());
		}
	}

}
