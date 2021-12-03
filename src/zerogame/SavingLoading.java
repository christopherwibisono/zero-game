package zerogame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class SavingLoading {
	protected static int nep = 0;
	protected static Scanner scan = new Scanner(System.in);
	protected static playerStatus p1 = new playerStatus(null, 0, 0);
	
	
	public static int getNep() {
		return nep;
	}
	public static void setNep(int nep) {
		SavingLoading.nep = nep;
	}
	
	public static void saveFile(String status) {
		try {
			FileOutputStream fos = new FileOutputStream("SavFile.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(status);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static playerStatus loadFile() {
		File f = new File("SavFile.txt");
		String loaded = (p1.Name + "?" + p1.Zerc + "|" + p1.Exp);
		if (!f.exists())
		{
			try {
				GenerateName();
				nep = 1;
				loaded = (p1.Name + "?" + p1.Zerc + "|" + p1.Exp);
				f.createNewFile();
				saveFile(loaded);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				FileInputStream fis = new FileInputStream("SavFile.txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				String x = (String) ois.readObject();
				p1.Name = x.substring(0,x.indexOf("?"));
				p1.Zerc = Integer.parseInt(x.substring(x.indexOf("?")+1, x.indexOf("|")));
				p1.Exp = Integer.parseInt(x.substring(x.indexOf("|")+1, x.length()));
				fis.close();
				ois.close();
				game.np = p1;
				game.runthread();
			} catch (IOException e) {
				JFrame cheater = new JFrame();
				JPanel siz = new JPanel();
				cheater.setResizable(false);
				cheater.setTitle("Cheat!");
				siz.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
				siz.setLayout(new GridLayout(0, 1));
				JLabel cheat1 = new JLabel("Cheater Detected!");
				JLabel cheat2 = new JLabel("Starting new game...");
				siz.add(cheat1); siz.add(cheat2); siz.updateUI();
				cheater.add(siz, BorderLayout.CENTER);
				cheater.setVisible(true);
				cheater.pack();
				try {
					GenerateName();
					nep = 1;
					loaded = (p1.Name + "?" + p1.Zerc + "|" + p1.Exp);
					f.createNewFile();
					saveFile(loaded);
				} catch (IOException ext) {
					ext.printStackTrace();
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		return p1;
	}
	
	public static void GenerateName() {
		JLabel nof = new JLabel("No File Detected!");
		JButton con = new JButton("Confirm");
		JLabel ll = new JLabel("Name [10 max] : ");
		JFrame trya = new JFrame();
		trya.setVisible(false);
		game.frame.add(nof, BorderLayout.BEFORE_FIRST_LINE);
		game.panel.add(ll);
		game.panel.add(game.txt);
		game.panel.add(con);
		game.panel.updateUI();
		game.panel.setVisible(true);
		con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.setName(game.txt.getText());
				if(!p1.getName().isEmpty() && p1.getName().length() <= 10)
				{
					game.frame.remove(nof);
					game.np = p1;
					game.runthread();
				}
				else
				{
					JPanel tryx = new JPanel();
					game.txt.setText(null);
					
					tryx.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
					tryx.setLayout(new GridLayout(0, 1));
					tryx.add(new JLabel("Name not available!"));
					tryx.add(new JLabel("Try Again!"));
					
					trya.add(tryx, BorderLayout.CENTER);
					trya.setTitle("Try Again!");
					trya.pack();
					trya.setVisible(true);
				}
			}
		});
	}
}
