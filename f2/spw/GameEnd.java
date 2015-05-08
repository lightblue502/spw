package f2.spw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameEnd extends JFrame{
	private JPanel p;
	private JLabel l;
	private JLabel sLabel;
	public GameEnd(String title,double score){
		super(title);
		p = new JPanel();
		l = new JLabel(title);
		sLabel = new JLabel("SCORE : "+score);
		setLayout(new BorderLayout());
		p.add(l,BorderLayout.CENTER);
		p.add(sLabel,BorderLayout.CENTER);
		// make the frame half the height and width
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int height = screenSize.height;
	    int width = screenSize.width;
	    setSize(width/4, height/3);
	    // center the jframe on screen
	    setLocationRelativeTo(null);
		setContentPane(p);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
