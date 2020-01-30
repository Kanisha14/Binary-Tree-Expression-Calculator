import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MainGUI extends JFrame { 
	JTextArea write = new JTextArea();
	JTextField messages = new JTextField();
	JTextField xmessages = new JTextField();
	JTextField ymessages = new JTextField();
	JTextField zmessages = new JTextField();
	JTextField ansmessages = new JTextField();
	int x,y,z,ans;

	//	TreeWalker tree = new TreeWalker("((10 20 30) 40 (null 50 (60 70 80)))");		
	TreeWalker tree = new TreeWalker("x+y*z");		
	Position current = tree.root();

	public MainGUI(String string) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = makeGUI ();
		setContentPane (mainPanel);
		pack();
		setVisible(true);
		write.setText(tree.PrettyPrint());
	}

	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		MainGUI gui = new MainGUI("");
	}

	public JPanel makeGUI() {
		JPanel GUI = new JPanel ();
		GUI.setLayout (new BorderLayout ());
		write.setEditable(false);
		write.setLineWrap(false);
		write.setWrapStyleWord(false);
		write.setFont(new Font("monospaced", Font.PLAIN, 12));

		JScrollPane jsp = new JScrollPane(write);
		jsp.setPreferredSize(new Dimension(400, 400));
		GUI.add (jsp, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout (5,4));
		JButton root = new JButton ("root");
		root.addActionListener(new listener(1));
		buttons.add(root);
		JButton parent = new JButton ("parent");
		parent.addActionListener(new listener(2));
		buttons.add(parent);
		JButton leftChild = new JButton ("leftChild");
		leftChild.addActionListener(new listener(3));
		buttons.add(leftChild);
		JButton rightChild = new JButton ("rightChild");
		rightChild.addActionListener(new listener(4));
		buttons.add(rightChild);
		JButton first = new JButton ("first");
		first.addActionListener(new listener(8));
		buttons.add(first);
		JButton previous = new JButton ("previous");
		previous.addActionListener(new listener(11));
		buttons.add(previous);
		JButton next = new JButton ("next");
		next.addActionListener(new listener(10));
		buttons.add(next);
		JButton last = new JButton ("last");
		last.addActionListener(new listener(9));
		buttons.add(last);
		JButton rotateL = new JButton ("rotateL");
		rotateL.addActionListener(new listener(7));
		buttons.add(rotateL);
		JButton rotateR = new JButton ("rotateR");
		rotateR.addActionListener(new listener(6));
		buttons.add(rotateR);
		JButton insert = new JButton ("insert");
		insert.addActionListener(new listener(5));
		buttons.add(insert);
		JButton delete = new JButton ("delete");
		delete.addActionListener(new listener(12));
		buttons.add(delete);
		JButton diff = new JButton ("differentiate");
		diff.addActionListener(new listener(13));
		buttons.add(diff);
		JButton simp = new JButton ("simplify");
		simp.addActionListener(new listener(14));
		buttons.add(simp);
		JButton blank = new JButton ("");
		blank.addActionListener(new listener(15));
		buttons.add(blank);
		JButton blank2 = new JButton ("");
		blank2.addActionListener(new listener(16));
		buttons.add(blank2);

		xmessages.addActionListener(new messagesListener());
		xmessages.setText("0");
		buttons.add(xmessages);
		
		ymessages.addActionListener(new messagesListener());
		ymessages.setText("0");
		buttons.add(ymessages);
		
		zmessages.addActionListener(new messagesListener());
		zmessages.setText("0");
		buttons.add(zmessages);

//		ansmessages.addActionListener(new messagesListener());
//		ansmessages.setText("0");
		buttons.add(ansmessages);


		GUI.add(buttons, BorderLayout.SOUTH);


		messages.addActionListener(new messagesListener());
//		messages.setText("((10 20 30) 40 (null 50 (60 70 80)))");
		messages.setText("x+y*z");
		GUI.add(messages, BorderLayout.NORTH);
		return GUI;
	}

	public class listener implements ActionListener{
		int button;
		public listener(int i) {
			button=i;
		}
		public void actionPerformed(ActionEvent arg0) {
			tree.notCurrent(current);
			switch (button){
			case 1:	current = tree.root(current);  	  	break;
			case 2:	current = tree.parent(current);	   	break;
			case 3:	current = tree.leftChild(current); 	break;
			case 4:	current = tree.rightChild(current);	break;
			case 5:	current = tree.insert(current);	   	break;
			case 6:	current = tree.rotateR(current);	break;
			case 7:	current = tree.rotateL(current);	break;
			case 8:	current = tree.first(current);		break;
			case 9:	current = tree.last(current);		break;
			case 10:current = tree.next(current);		break;
			case 11:current = tree.previous(current);	break;
			case 12:current = tree.delete(current);	   	break;
			case 13:          tree.differentiate();	   	break;
			case 14:          tree.simplify();	   		break;
			case 15:   System.out.println("No Action 1"); break;
			case 16:   System.out.println("No Action 2"); break;
			}
			tree.makeCurrent(current);
			write.setText(tree.PrettyPrint());
		}
	}

	public class messagesListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			tree = new TreeWalker(messages.getText());	
			current = tree.root();
			write.setText(tree.PrettyPrint());			
			x = Integer.valueOf(xmessages.getText());	
			y = Integer.valueOf(ymessages.getText());	
			z = Integer.valueOf(zmessages.getText());
			ans = tree.evaluate(x,y,z);
			ansmessages.setText(Integer.toString(ans));
//			System.out.println("x = "+x+", y = "+y+", z = "+z+", ans = "+ans);
		}
	}

}
