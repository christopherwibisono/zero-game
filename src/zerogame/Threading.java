package zerogame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Threading extends SavingLoading {
	protected static int yes = 1;
	protected static JButton exitg = new JButton("Exit");
	protected static String x;
	protected static String adex, adze;
	protected static JLabel adexp = new JLabel(adex);
	protected static JLabel adzer = new JLabel(adex);
	protected static JFrame frEnd = new JFrame();
	protected static JPanel pnEnd = new JPanel();
	protected static JLabel imgb = new JLabel();
	protected static JLabel playerz = new JLabel();
	protected static JLabel playerx = new JLabel();
	private static Account account = new Account();
	Scanner scan = new Scanner(System.in);
	public Threading() {
		JLabel plname = new JLabel(game.np.Name);
		JLabel yrnm = new JLabel("Player Name : ");
		game.panel.add(yrnm);
		game.panel.add(plname);
		game.panel.updateUI();
		frEnd.setResizable(false);
		JPanel btl = new JPanel();
		JButton stats = new JButton("Player Status");
		pnEnd.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		pnEnd.setLayout(new GridLayout(0, 1));
		pnEnd.updateUI();
		
		String setz = ("Zerc : ");
		setz = setz.concat(String.format("%05d", game.np.getZerc()));
		playerz.setText(setz);
		String setx = ("Exp  : ");
		setx = setx.concat(String.format("%05d", game.np.getExp()));
		playerx.setText(setx);

		pnEnd.add(playerx);
		pnEnd.add(playerz);
		
		frEnd.add(pnEnd, BorderLayout.CENTER);
		frEnd.setTitle("Player Status");
		frEnd.pack();
		
		frEnd.setVisible(false);
		btl.setOpaque(true);
		btl.setBackground(Color.green);
		btl.setBorder(BorderFactory.createEmptyBorder(20, 150, 10, 100));
		btl.setLayout(new GridLayout(0, 1));
		game.panel.setBorder(BorderFactory.createEmptyBorder(10, 300, 50, 300));
		game.panel.setLayout(new GridLayout(0, 1));
		
		btl.add(imgb);
		btl.updateUI();
		
		game.frame.add(btl, BorderLayout.CENTER);
		game.frame.add(game.panel, BorderLayout.AFTER_LAST_LINE);
		Image ig = new ImageIcon(this.getClass().getResource("/btl1.png")).getImage();
		imgb.setIcon(new ImageIcon(ig));
		
		game.panel.add(adexp);
		game.panel.add(adzer);
		game.panel.add(stats);
		game.panel.add(exitg);
		game.panel.updateUI();
		stats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frEnd.setVisible(true);
			}
		});
		pnEnd.updateUI();
		exitg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				yes = 0;
				pnEnd.removeAll();
				if(nep == 1)
					getEnd(0);
				else
					getEnd();
				
				String stats = (game.np.Name + "?" + game.np.Zerc + "|" + game.np.Exp);
				saveFile(stats);
			}
		});
		game.panel.updateUI();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new AddZerc());
		executor.execute(new AddExp());
		executor.shutdown();
	}
	
	public void getEnd(int x) {
		JLabel lsn = new JLabel("Name : " + game.np.Name);
		JLabel lsz = new JLabel("Zerc : " + game.np.Zerc);
		JLabel lse = new JLabel("Exp : " + game.np.Exp);
		JLabel nwpl = new JLabel("Achievement : Started Doing Nothing");
		JLabel sv = new JLabel("Save Successful!");
		JLabel end = new JLabel("Thank You For Playing!");
		nwpl.setOpaque(true);
		nwpl.setForeground(Color.darkGray);
		nwpl.setBackground(Color.cyan);
		
		pnEnd.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		pnEnd.setLayout(new GridLayout(0, 1));
		pnEnd.add(nwpl); pnEnd.add(lsn); pnEnd.add(lse); pnEnd.add(lsz);
		pnEnd.add(sv); pnEnd.add(end);
		
		frEnd.add(pnEnd, BorderLayout.CENTER);
		frEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frEnd.setTitle("End Game");
		frEnd.pack();
		frEnd.setVisible(true);
	}
	
	public void getEnd() {
		JLabel lsn = new JLabel("Name : " + game.np.Name);
		JLabel lsz = new JLabel("Zerc : " + game.np.Zerc);
		JLabel lse = new JLabel("Exp : " + game.np.Exp);
		JLabel sv = new JLabel("Save Successful!");
		JLabel end = new JLabel("Thank You For Playing!");
		
		pnEnd.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		pnEnd.setLayout(new GridLayout(0, 1));
		pnEnd.add(lsn); pnEnd.add(lse); pnEnd.add(lsz);
		pnEnd.add(sv); pnEnd.add(end);
		
		frEnd.add(pnEnd, BorderLayout.CENTER);
		frEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frEnd.setTitle("End Game");
		frEnd.pack();
		frEnd.setVisible(true);
	}

	public static class AddZerc implements Runnable {
		public void run(){
			try {
				while(yes == 1) {
					account.addzerc((int)(Math.random()*10) + 1);
					Thread.sleep(3000);
				}
			} catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}

	public static class AddExp implements Runnable {
		public void run(){
			try {
				while(yes == 1) {
					account.addexp((int)(Math.random()*10) + 1);
					Thread.sleep(3000);
				}
			} catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}

	private static class Account{
		private static Lock lock = new ReentrantLock();
		public void addexp(int amount){
			lock.lock();
			try {
				game.np.Exp += amount;
				adex = ("You got " + amount + " EXP!");
				adexp.setText(adex);
				pnEnd.remove(playerx);
				String setx = ("Exp  : ");
				setx = setx.concat(String.format("%05d", game.np.getExp()));
				playerx.setText(setx);
				pnEnd.add(playerx);
				pnEnd.updateUI();
				game.panel.updateUI();
			} finally {
				lock.unlock();
			}
		}
		
		public void addzerc(int amount){
			lock.lock();
			try {
				game.np.Zerc += amount;
				adze = ("You got " + amount + " Zerc!");
				adzer.setText(adze);
				pnEnd.remove(playerz);
				String setz = ("Zerc : ");
				setz = setz.concat(String.format("%05d", game.np.getZerc()));
				playerz.setText(setz);
				pnEnd.add(playerz);
				pnEnd.updateUI();
				game.panel.updateUI();
			} finally {
				lock.unlock();
			}
		}
	}
}