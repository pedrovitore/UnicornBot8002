package test;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lowagie.text.pdf.codec.Base64.OutputStream;

import dao.RoutineDAO;
import model.Routine;

public class HomeForm {
	JFrame frame;
	JPanel panel;
	JTextArea console;

	private static ArrayList<JCheckBox> routineCheckBoxes = new ArrayList<>();
	
	public void show() {
		//frame
		frame = new JFrame("Unicorn Bot 8002");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

		JScrollPane leftPane = new JScrollPane(left);

		leftPane.setMaximumSize(new Dimension(300, Short.MAX_VALUE));
		
		panel.add(leftPane);
		panel.add(right);
		
		List<Routine> routines;
		try {
			routines = RoutineDAO.getRoutines();
		} catch (SQLException ex) {
    		GuiConsole.println(ex.getMessage());
    		JOptionPane.showMessageDialog(null, "Cause: "+ ex.getMessage(), "Robot Interrupted",JOptionPane.INFORMATION_MESSAGE);
    		return;
		}
		
		routines.forEach((Routine routine) -> {
			JCheckBox checkBox = new JCheckBox(routine.getName());
			routineCheckBoxes.add(checkBox);
			left.add(checkBox);
		});
		
		JButton button = new JButton("Run");
		left.add(button);

		console = new JTextArea();
		JScrollPane panelPane = new JScrollPane(console);
		right.add(panelPane);
    	
		frame.getContentPane().add(panel);

		frame.setMinimumSize(new Dimension(400, 400));
		frame.setVisible(true);
		

		button.addActionListener(new ActionListener() {
		
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
			    	List<Routine> routines = new ArrayList<>();
			    	
			    	//Loads routines from database
			    	routineCheckBoxes.forEach((JCheckBox checkBox) -> {
			    		if (checkBox.isSelected()) {
			    			try {
								routines.add(RoutineDAO.getRoutine(checkBox.getText()));
							} catch (SQLException ex) {
					    		GuiConsole.println(ex.getMessage());
					    		JOptionPane.showMessageDialog(null, "Cause: "+ ex.getMessage(), "Robot Interrupted",JOptionPane.INFORMATION_MESSAGE);
							}
			    		}
			    	});
			    	
			    	//runs the routines
			    	Iterator<Routine> iterator = routines.iterator();
					while (iterator.hasNext()) {
						Routine routine = iterator.next();
						
						routine.run();
			    	}
		    	} catch (Exception ex) {
		    		GuiConsole.println(ex.getMessage());
		    		JOptionPane.showMessageDialog(null, "Cause: "+ ex.getMessage(), "Robot Interrupted",JOptionPane.INFORMATION_MESSAGE);
		    	}
		    	
		        
		    }
		});
		
		//String nome = JOptionPane.showInputDialog("Digite o nome: ");
		//String sobreNome = JOptionPane.showInputDialog("Digite o sobrenome: ");
	     	
		//String nomeCompleto = nome + " " + sobreNome;
		
		//JOptionPane.showMessageDialog(null, "Nome Completo: "+nomeCompleto,"Informação",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
