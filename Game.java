package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
//import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable,KeyListener 
{
	public static JFrame frame;
	public final static int WIDHT = 480; 
	public final static int HEIGHT = 320;
	private final int SCALE = 2;
	private Thread thread;
	private boolean isRunning;
	private BufferedImage image;
	private Image background;
	
	
	
	public Game()
	{
		setPreferredSize(new Dimension(WIDHT*SCALE,HEIGHT*SCALE));
		image = new BufferedImage(WIDHT,HEIGHT,BufferedImage.TYPE_INT_RGB);
		ImageIcon reference = new ImageIcon("res\\background.png");
		background = reference.getImage();
		initFrame();
	}
	
	public void initFrame() 
	{
		frame = new JFrame("Game 1#");
		frame.addKeyListener(this);
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public synchronized void start() 
	{
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop() 
	{
		isRunning = false;
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
	
		}
	}
	public static void main(String[] args) 
	{
		Game game = new Game();
		game.start();
	}
	public void tick() 
	{
		if(hasFocus()) 
		{
			frame.requestFocus();
		}
	}

	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) 
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		//Background
		g2.drawImage(background,0,0,WIDHT,HEIGHT,null);
		//
		
		//
		g.dispose();
		g2.dispose();
		
		g = bs.getDrawGraphics();
		
		g.drawImage(image,0,0,WIDHT*SCALE,HEIGHT*SCALE,null);
		bs.show();
	}
	public void run() 
	{
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60;
		final double ns = 1000000000/ amountOfTicks;
		double delta = 0;
		while(isRunning) 
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) 
			{
				render();
				tick();
				delta--;
			}
		}
		stop();
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{

	}
}
