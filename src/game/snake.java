package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class snake extends JFrame implements Runnable {

	JPanel jp1, jp2, jp3, ap, np, cp, sp, wp, ep, snp;
	JLabel jl;
	Thread th = new Thread(this);
	ArrayList<Point> snake = new ArrayList<Point>();
	ArrayList<Integer> wheresnake = new ArrayList<Integer>();
	int where = 1, drawsnakex = 0, drawsnakey = 0, draw = 25, sub = 0;
	boolean run = true, turn = false;

	// 0 == up, 1 == right, 2 == bottom, 3 == left;
	public snake() {
		// TODO Auto-generated constructor stub
		setTitle("스네이크 게임");
		setDefaultCloseOperation(2);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				run = false;
			}
		});

		reset();

		add(cp = new JPanel(new BorderLayout()), BorderLayout.CENTER);
		size(cp, 500, 800);// 20*36
		cp.add(ap = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				int selc = 0;
				for (int i = 0; i < 500; i = i + 25) {
					for (int j = 0; j < 900; j = j + 25) {
						if (selc % 2 == 0) {
							g.setColor(new Color(170, 215, 81));
						} else {
							g.setColor(new Color(162, 209, 73));
						}
						g.fillRect(i, j, 25, 25);
						selc++;
					}
					selc++;
				}
			}
		});
		size(ap, 500, 800);
		ap.add(snp = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				g.setColor(Color.red);
				for (int i = 0; i < snake.size(); i++) {
					// System.out.println(wheresnake.get(i));
					switch (wheresnake.get(i)) {
					case 0: {
						drawsnakey = -draw;
						drawsnakex = 0;
						break;
					}
					case 1: {
						drawsnakex = draw;
						drawsnakey = 0;
						break;
					}
					case 2: {
						drawsnakey = draw;
						drawsnakex = 0;
						break;
					}
					case 3: {
						drawsnakex = -draw;
						drawsnakey = 0;
						break;
					}
					default:
						break;
					}
					g.fillRect(snake.get(i).x * 25 - drawsnakex, snake.get(i).y * 25 - drawsnakey, 25, 25);

				}
			}
		});
		snp.setOpaque(false);
		size(snp, 500, 800);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyCode());
				System.out.println(draw);
				turn = true;
				switch (e.getKeyCode()) {
				case 37:
					sub = 3;
					break;
				case 38:
					sub = 0;
					break;
				case 39:
					sub = 1;
					break;
				case 40:
					sub = 2;
					break;
				default:
					break;
				}
			}
		});

		th.start();
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void size(JComponent c, int x, int y) {
		c.setPreferredSize(new Dimension(x, y));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new snake();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (run) {
			try {
				for (int i = 0; i < snake.size() - 1; i++) {
					snake.set(i, snake.get(i + 1));
					wheresnake.set(i, wheresnake.get(i + 1));
				}
				int x = snake.get(snake.size() - 2).x;
				int y = snake.get(snake.size() - 2).y;
				switch (where) {
				case 0: {
					y--;
					wheresnake.set(wheresnake.size() - 1, 0);
					break;
				}
				case 1: {
					x++;
					wheresnake.set(wheresnake.size() - 1, 1);
					break;
				}
				case 2: {
					y++;
					wheresnake.set(wheresnake.size() - 1, 2);
					break;
				}
				case 3: {
					x--;
					wheresnake.set(wheresnake.size() - 1, 3);
					break;
				}
				default:
					break;
				}
				snake.set(snake.size() - 1, new Point(x, y));
				for (int i = 25; i > -1; i--) {
					draw = i;
					repaint();
					th.sleep(2);
				}
				if (turn) {
					where = sub;
				}
				if (snake.get(snake.size()-1).x < 0 || snake.get(snake.size()-1).x > 500/25 || snake.get(snake.size()-1).y < 0 || snake.get(snake.size()-1).y > 900/25) {
					run = false;
					JOptionPane.showMessageDialog(null, "앗!");
					snake.clear();
					reset();
				}
				repaint();
				revalidate();
				// th.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void reset() {
		snake.add(new Point(2, 18));
		snake.add(new Point(3, 18));
		snake.add(new Point(4, 18));
		snake.add(new Point(5, 18));
		snake.add(new Point(6, 18));

		wheresnake.add(1);
		wheresnake.add(1);
		wheresnake.add(1);
		wheresnake.add(1);
		wheresnake.add(1);
	}
}
