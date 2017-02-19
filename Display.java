
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Display extends JFrame implements ActionListener, KeyListener, Runnable{
	
	public static JToggleButton[][] buttons;
	private final int dimension = 9;
	private int[][] sudoku;
	private BruteForce bruteForce;
	private final int sudokuWidth = 510, sudokuHeight = 550;
	private JPanel sudokuPanel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem solve, clear;
	public static Thread thread;
	
	public Display(){
		this.setTitle("Sudoku Killer - Brute Force");
		this.setSize(sudokuWidth, sudokuHeight);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new FlowLayout());
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menu = new JMenu("Brute Force");
		menuBar.add(menu);
		solve = new JMenuItem("Solve");
		clear = new JMenuItem("Clear");
		clear.addActionListener(this);
		solve.addActionListener(this);
		menu.add(clear);
		menu.add(solve);
		sudokuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sudokuPanel.setPreferredSize(new Dimension(sudokuWidth, sudokuHeight));
		
		sudoku = new int[dimension][dimension];
		
		buttons = new JToggleButton[dimension][dimension];
		
		for(int row = 0; row < dimension; row++){
			for(int col = 0; col < dimension; col++){			
				buttons[row][col] = new JToggleButton();
				if(col > 2 && col < 6 || row > 2 && row < 6){
					Color yellow = new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen() - 50,
							Color.YELLOW.getBlue());
					buttons[row][col].setBackground(yellow);
				}else{
					Color red = new Color(Color.RED.getRed() - 150, Color.RED.getGreen() + 200,
							Color.RED.getBlue());
					buttons[row][col].setBackground(red);
				}
				buttons[row][col].addActionListener(this);
				buttons[row][col].addKeyListener(this);
				buttons[row][col].setPreferredSize(new Dimension(50, 50));
				this.add(buttons[row][col]);
			}
		}
		
		this.add(sudokuPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Clear")){
			for(int row = 0; row < dimension; row++){
				for(int col = 0; col < dimension; col++){
					buttons[row][col].setText("");
				}
			}
			return;
		}else if(e.getActionCommand().equals("Solve")){
			for(int row = 0; row < dimension; row++){
				for(int col = 0; col < dimension; col++){
					if(buttons[row][col].getText().equals("")){
						sudoku[row][col] = 0;
						continue;
					}
					int number = Integer.parseInt(buttons[row][col].getText());
					
					sudoku[row][col] = number;
				}
			}
			thread = new Thread(this);
			thread.start();
			
			return;
		}
		for(int row = 0; row < dimension; row++){
			for(int col = 0; col < dimension; col++){
				buttons[row][col].setSelected(false);
			}
		}
		((JToggleButton) e.getSource()).setSelected(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_1){
			typeNumber(1);
		}else if(e.getKeyCode() == KeyEvent.VK_2){
			typeNumber(2);
		}else if(e.getKeyCode() == KeyEvent.VK_3){
			typeNumber(3);
		}else if(e.getKeyCode() == KeyEvent.VK_4){
			typeNumber(4);
		}else if(e.getKeyCode() == KeyEvent.VK_5){
			typeNumber(5);
		}else if(e.getKeyCode() == KeyEvent.VK_6){
			typeNumber(6);
		}else if(e.getKeyCode() == KeyEvent.VK_7){
			typeNumber(7);
		}else if(e.getKeyCode() == KeyEvent.VK_8){
			typeNumber(8);
		}else if(e.getKeyCode() == KeyEvent.VK_9){
			typeNumber(9);
		}else if(e.getKeyCode() == KeyEvent.VK_0){
			typeNumber(0);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	private void typeNumber(int number){
		for(int row = 0; row < dimension; row++){
			for(int col = 0; col < dimension; col++){
				if(buttons[row][col].isSelected()){
					if(number == 0){
						buttons[row][col].setText("");
						return;
					}
					buttons[row][col].setText(""+number);
					buttons[row][col].validate();
					return;
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void run() {
		bruteForce = new BruteForce(sudoku);
		bruteForce.solve();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
