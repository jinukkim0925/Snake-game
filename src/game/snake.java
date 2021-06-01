package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class snake extends JFrame implements Runnable {

	JPanel jp1, jp2, jp3, ap, np, cp, sp, wp, ep, snp;
	JLabel jl;
	Thread th = new Thread(this);
	LinkedList<Point> snake = new LinkedList<Point>();
	LinkedList<Integer> wheresnake = new LinkedList<Integer>();	
	LinkedList<Integer> goldPoint = new LinkedList<Integer>();
	LinkedList<Integer> goldPoint2 = new LinkedList<Integer>();
	LinkedList<Integer> goldPoint3 = new LinkedList<Integer>();
	LinkedList<Integer> goldPoint4 = new LinkedList<Integer>();
	LinkedList<Integer> goldPoint5 = new LinkedList<Integer>();
	int ti1 = 0, ti2 = 0;
	
	public static int where = 1, drawsnakex = 0, drawsnakey = 0, draw = 25, sub = 0, speed = 10, bestscore = 0;
	public static boolean run = true, turn = false, item = false, rainbow = false, gold = false;
	public static Point itemPoint = new Point();
	SimpleDateFormat ss = new SimpleDateFormat("mm:ss:SS");
	long smilli = 0, emilli = 0;
	Color snakeColor = Color.red, choose[] = { new Color(255, 255, 255), new Color(255, 153, 204), new Color(255, 0, 0),
			new Color(255, 153, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(56, 87, 35),
			new Color(207, 255, 229), new Color(102, 255, 255), new Color(0, 0, 255), // 9
			new Color(0, 0, 128), new Color(204, 153, 255), new Color(102, 0, 204), new Color(150, 75, 0),
			new Color(128, 128, 128), new Color(0, 0, 0), new Color(255, 215, 0), new Color(0, 0, 0) };
	JLabel chooseColor[] = new JLabel[18];
	int n1 = 1 , n2 = 1, n3 = 1, n4 = 1;
	
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
				tm.stop();
				System.exit(0);
			}
		});

		reset();

		snakecolor s = new snakecolor();

		add(cp = new JPanel(new BorderLayout()), BorderLayout.CENTER);
		add(wp = new JPanel(new BorderLayout()), BorderLayout.WEST);
		add(np = new JPanel(new BorderLayout()), BorderLayout.NORTH);
		add(sp = new JPanel(new BorderLayout()), BorderLayout.SOUTH);
		add(ep = new JPanel(new BorderLayout()), BorderLayout.EAST);

		size(cp, 500, 900);// 20*36
		size(wp, 200, 900);
		size(np, 700, 30);
		size(sp, 700, 30);
		size(ep, 30, 30);

		wp.setBackground(new Color(81, 140, 46));
		np.setBackground(new Color(81, 140, 46));
		sp.setBackground(new Color(81, 140, 46));
		ep.setBackground(new Color(81, 140, 46));

		wp.add(jp1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.white);
				g2.setFont(new Font("", Font.BOLD, 25));
				g2.drawString("최고점수 : " + bestscore + "점", 10, 50);
				g2.drawString("점수 : " + (snake.size() - 4) + "점", 10, 200);
				g2.drawString("시간 : " + ss.format(emilli - smilli), 10, 300);
				chooseColor[17].setBackground(snakecolor.rainbow);
				chooseColor[16].setBackground(snakecolor.gold);				
			}
		});

		size(jp1, 200, 900);
		jp1.setOpaque(false);
		jp1.setLayout(null);
		for (int j = 0; j < chooseColor.length; j++) {
			jp1.add(chooseColor[j] = new JLabel());
			chooseColor[j].setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
			final int ch = j;
			chooseColor[j].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getSource() == chooseColor[ch]) {
						snakeColor = choose[ch];
						if (ch == 17) {
							rainbow = true;
							gold = false;
						} else if (ch == 16) {
							rainbow = false;
							gold = true;
						} else {
							rainbow = false;
							gold = false;
						}
						repaint();
						revalidate();
					}
				}
			});
			chooseColor[j].setBounds(15 + (j % 3 * 60), 550 + j / 3 * 60, 50, 50);
			chooseColor[j].setBackground(choose[j]);
			chooseColor[j].setOpaque(true);
		}

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

				if (item) {
					g.setColor(Color.orange);
					g.fillRect(itemPoint.x * 25, itemPoint.y * 25, 25, 25);
					g.setColor(Color.black);
					g.drawRect(itemPoint.x * 25, itemPoint.y * 25, 25, 25);
				}

			}
		});
		size(ap, 500, 900);
		ap.add(snp = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				if (run) {
					Random r = new Random();
					
					for (int i = 0; i < snake.size(); i++) {
						// System.out.println(wheresnake.get(i));
						if (rainbow) {
							g.setColor(snakecolor.rainbow);
						} else if (gold) {
							g.setColor(snakecolor.gold);
						} else {
							g.setColor(snakeColor);
						}
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
						
						if (gold) {
							if (draw == 25 && ti1 != ti2) {
								ti2 = ti1;
								n1 = r.nextInt(3);
								n2 = r.nextInt(3);
								n3 = r.nextInt(3);
								n4 = r.nextInt(3);
								goldPoint.clear();
								goldPoint2.clear();
								goldPoint3.clear();
								goldPoint4.clear();
								goldPoint5.clear();
								for (int j = 0; j < snake.size()-3; j++) {
									goldPoint.add(r.nextInt(snake.size()));
									goldPoint2.add(r.nextInt(2));									
									goldPoint3.add(r.nextInt(2));									
									goldPoint4.add(r.nextInt(2));									
									goldPoint5.add(r.nextInt(2));									
								}
							}
							g.setColor(snakecolor.gold);
							for (int j = 0; j < goldPoint.size(); j++) {
								if (goldPoint2.get(j) == 0 && (wheresnake.get(i) == 1 || wheresnake.get(i) == 3)) {
									g.fillRect(snake.get(goldPoint.get(j)).x * 25 - drawsnakex, snake.get(goldPoint.get(j)).y * 25 - drawsnakey + 30, 7, 7);
								}
								if (goldPoint3.get(j) == 0 && (wheresnake.get(i) == 1 || wheresnake.get(i) == 3)) {
									g.fillRect(snake.get(goldPoint.get(j)).x * 25 - drawsnakex, snake.get(goldPoint.get(j)).y * 25 - drawsnakey - 10, 7, 7);
								}
								if (goldPoint4.get(j) == 0 && (wheresnake.get(i) == 0 || wheresnake.get(i) == 2)) {
									g.fillRect(snake.get(goldPoint.get(j)).x * 25 - drawsnakex + 30, snake.get(goldPoint.get(j)).y * 25 - drawsnakey, 7, 7);
								}
								if (goldPoint5.get(j) == 0 && (wheresnake.get(i) == 0 || wheresnake.get(i) == 2)) {
									g.fillRect(snake.get(goldPoint.get(j)).x * 25 - drawsnakex - 10, snake.get(goldPoint.get(j)).y * 25 - drawsnakey, 7, 7);
								}
							}
						}

					}
				}

			}
		});
		snp.setOpaque(false);
		size(snp, 500, 800);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				switch (e.getKeyCode()) {
				case 37: {
					if (where != 1) {
						turn = true;
						sub = 3;
					}
					break;
				}
				case 38: {
					if (where != 2) {
						turn = true;
						sub = 0;
					}
					break;
				}
				case 39: {
					if (where != 3) {
						turn = true;
						sub = 1;
					}
					break;
				}
				case 40: {
					if (where != 0) {
						turn = true;
						sub = 2;
					}
					break;
				}
				default:
					break;
				}
			}
		});
		th.start();
		tm.start();

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
				if (turn) {
					where = sub;
				}
				int fx = snake.get(snake.size() - 1).x;
				int fy = snake.get(snake.size() - 1).y;

				int whereback = wheresnake.get(0);
				Point back = new Point();
				back = snake.get(snake.size() - 1);
				// 아이템을 먹을시
				if (item && fx == itemPoint.x && fy == itemPoint.y) {
					// 임시값
					snake.add(back);
					wheresnake.add(0);

					for (int i = snake.size() - 1; i > 0; i--) {
						snake.set(i, snake.get(i - 1));
						wheresnake.set(i, wheresnake.get(i - 1));
					}
					snake.set(0, back);
					wheresnake.set(0, whereback);
					item = false;
					if (tm.isRunning()) {
						tm.restart();
					} else {
						tm.start();
					}
				}

				// 아웃 처리(벽사)
				fx = snake.get(snake.size() - 1).x;
				fy = snake.get(snake.size() - 1).y;
				if (fx < 0 || fx >= 500 / 25 || fy < 0 || fy >= 900 / 25) {
					gameOver();
					return;
				}

				// 이동 모션 처리
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
				// 머리이동
				snake.set(snake.size() - 1, new Point(x, y));

				// 아웃처리 (몸통사)
				fx = snake.get(snake.size() - 1).x;
				fy = snake.get(snake.size() - 1).y;
				for (int i = 0; i < snake.size() - 1; i++) {
					if (fx == snake.get(i).x && fy == snake.get(i).y) {
						gameOver();
						return;
					}
				}

				// 부드러운 이동 모션 처리

				for (int i = 25; i > -1; i--) {
					draw = i;
					repaint();
					emilli = System.currentTimeMillis();
					th.sleep(speed);
				}

				switch (snake.size()) {
				case 9:
					speed = 9;
					break;
				case 14:
					speed = 8;
					break;
				case 19:
					speed = 7;
					break;
				case 24:
					speed = 6;
					break;
				case 29:
					speed = 5;
					break;
				case 34:
					speed = 4;
					break;
				case 39:
					speed = 3;
					break;
				case 44:
					speed = 2;
					break;
				case 49:
					speed = 1;
					break;
				default:
					break;
				}
				repaint();
				revalidate();
				// th.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	// 사과배정
	Timer tm = new Timer(1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ti1++;
			if (!item) {
				Random r = new Random();
				int x = r.nextInt(20);
				int y = r.nextInt(36);
				t: while (true) {
					int ch = 0;
					for (int i = 0; i < snake.size(); i++) {
						if (snake.get(i).x == x && snake.get(i).y == y) {
							x = r.nextInt(20);
							y = r.nextInt(36);
							ch = 1;
						}
					}
					if (ch == 0) {
						break t;
					}
				}
				itemPoint.x = x;
				itemPoint.y = y;
				item = true;
				repaint();
				revalidate();
			}
		}
	});

	public void gameOver() {
		run = false;
		JOptionPane.showMessageDialog(null, "앗!");
		if (bestscore <= snake.size() - 4) {
			bestscore = snake.size() - 4;
		}

		snake.clear();
		wheresnake.clear();

		reset();
		if (tm.isRunning()) {
			tm.restart();
		} else {
			tm.start();
		}
		th = new Thread(this);
		th.start();
	}

	public void reset() {
		snake.add(new Point(2, 18));
		snake.add(new Point(3, 18));
		snake.add(new Point(4, 18));
		snake.add(new Point(5, 18));

		wheresnake.add(1);
		wheresnake.add(1);
		wheresnake.add(1);
		wheresnake.add(1);
		where = 1;
		drawsnakex = 0;
		drawsnakey = 0;
		draw = 25;
		sub = 0;
		run = true;
		turn = false;
		item = false;

		speed = 10;
		itemPoint.x = 15;
		itemPoint.y = 18;
		item = true;
		smilli = System.currentTimeMillis();

	}
}
