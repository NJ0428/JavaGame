package AwtSwing;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IWorldCop {
	static JPanel ponelNorth;
	static JPanel ponelCenter;
	static JLabel labelMessoge;
	static JLabel labelmessoge;
	static JLabel labeIVs;
	static JButton buttonLeft;
	static JButton buttonRight;
	static String images[] = {
    		"fruit01.png","fruit02.png","fruit03.png","fruit04.png",
    		"fruit05.png","fruit06.png","fruit07.png","fruit08.png",
    		"fruit09.png","fruit10.png","fruit11.png","fruit12.png",
    		"fruit13.png","fruit14.png","fruit15.png","fruit16.png",
    };

	static int imageIndex=2;
	
	static ImageIcon changeImage(String filenome) {
		ImageIcon icon = new ImageIcon("./img2/"+filenome);
		Image originImage = icon.getImage();
		Image changedImage = originImage.getScaledInstance(200,200, Image.SCALE_SMOOTH);
		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
	static class MyFrame extends JFrame implements ActionListener{
		public MyFrame(String title) {
			super(title);
			this.setSize(450,250);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			ponelNorth =new JPanel();
			labelMessoge= new JLabel("이상형월드컵");
			labelMessoge.setFont(new Font("D2coding",Font.BOLD,20));
			ponelNorth.add(labelMessoge);
			this.add("North",ponelNorth);
			
			ponelCenter=new JPanel();
			labeIVs= new JLabel("vs ");
			labeIVs.setFont(new Font("D2coding",Font.BOLD,20));
			buttonLeft = new JButton("LeftButton");
			buttonLeft.setIcon(changeImage("fruit01.png"));
			buttonLeft.setPreferredSize(new Dimension(200,200));
			buttonRight = new JButton("RightButton");
			buttonRight.setIcon(changeImage("fruit02.png"));
			buttonRight.setPreferredSize(new Dimension(200,200));
			
			buttonLeft.addActionListener(this);
			buttonRight.addActionListener(this);
			ponelCenter.add(buttonLeft);
			ponelCenter.add(labeIVs);
			ponelCenter.add(buttonRight);
			this.add("Center",ponelCenter);
			
			this.pack();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(imageIndex==16) {
				labelMessoge.setText("우승자");
				if(e.getActionCommand().equals("LeftButton")) {
					buttonRight.hide();
					labeIVs.hide();
				}else {
					buttonLeft.hide();
					labeIVs.hide();
				}
			}else{
				if(e.getActionCommand().equals("LeftButton")) {
					buttonRight.setIcon(changeImage(images[imageIndex]));
					imageIndex++;
				}else {
					buttonLeft.setIcon(changeImage(images[imageIndex]));
					imageIndex++;
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		new MyFrame("이상형월드컵");
	}

}
