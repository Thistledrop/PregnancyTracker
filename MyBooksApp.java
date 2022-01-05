import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Font;

public class MyBooksApp {

	private JFrame frame;
	private JTextField patientNameSearch;
	private JTextField searchVlue;
	private JLabel lblPleaseTypeSome;
	private JTextField patientID;
	private JTextField patientName;
	private JTextField dueDate;
	private JTextField delDate;
	private JTextField pregID;
	private JTextField type;
	private JTextField docID;
	private JTextField docName;
	private JTextField age;

	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyBooksApp window = new MyBooksApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application.
	public MyBooksApp() {
		initialize();
	}

	//Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuPanel.setBounds(0, 0, 175, 600);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton searchButton = new JButton("SEARCH PATIENTS");
		searchButton.setBounds(0, 80, 175, 40);
		menuPanel.add(searchButton);

		JButton pregButton = new JButton("SEARCH PREGNANCIES");
		pregButton.setBounds(0, 125, 175, 40);
		menuPanel.add(pregButton);

		JButton InsertPatient = new JButton("INSERT PATEINT");
		InsertPatient.setBounds(0, 170, 175, 40);
		menuPanel.add(InsertPatient);
		
		JButton InsertDoctor = new JButton("INSERT DOCTOR");
		InsertDoctor.setBounds(0, 215, 175, 40);
		menuPanel.add(InsertDoctor);
		
		JButton InsertPregnancy = new JButton("INSERT PREGNANCY");
		InsertPregnancy.setBounds(0, 260, 175, 40);
		menuPanel.add(InsertPregnancy);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(null);
		titlePanel.setBounds(144, 0, 710, 41);
		frame.getContentPane().add(titlePanel);
		
		JLabel lblWelcomeToMy = new JLabel("WELCOME TO MY PREGNANCY SEARCH APP");
		lblWelcomeToMy.setFont(new Font("Tahoma", Font.BOLD, 18));
		titlePanel.add(lblWelcomeToMy);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(144, 42, 740, 569);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		JLabel lblPleaseChooseAn = new JLabel("Please choose an option from the menu");
		lblPleaseChooseAn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPleaseChooseAn.setBounds(192, 110, 351, 50);
		mainPanel.add(lblPleaseChooseAn);

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction(mainPanel);
			}
		});
		
		InsertPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPatientFunction(mainPanel);
			}
		});

		pregButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchOrders(mainPanel);
			}
		});
		
		InsertPregnancy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPregnancyFunction(mainPanel);
			}
		});
		
		InsertDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertDoctorFunction(mainPanel);
			}
		});
	}

	// Create the book search interface
	public void searchFunction(JPanel panel) {

		panel.removeAll();
		JLabel lblPleaseTypeA = new JLabel("Please type patient's name");
		lblPleaseTypeA.setBounds(300, 20, 200, 50);
		panel.add(lblPleaseTypeA);

		patientNameSearch = new JTextField();
		patientNameSearch.setBounds(275, 70, 200, 50);
		panel.add(patientNameSearch);
		patientNameSearch.setColumns(10);

		JButton lookup = new JButton("SUBMIT");
		lookup.setBounds(275, 133, 200, 50);
		panel.add(lookup);
		frame.repaint();
		JTextPane displaypane = new JTextPane();
		displaypane.setBounds(50, 220, 800, 600);

		lookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				displaypane.setText(searchPatient(patientNameSearch.getText()));
				panel.add(displaypane);
				frame.repaint();
			}
		});
	}

	// create the insert interface
	public void insertPatientFunction(JPanel mainPanel) {

		mainPanel.removeAll();
		JLabel lblIsbn = new JLabel("Patient ID");
		lblIsbn.setBounds(120, 91, 100, 50);
		mainPanel.add(lblIsbn);

		JLabel lblBookTitle = new JLabel("Patient Name");
		lblBookTitle.setBounds(120, 133, 100, 50);
		mainPanel.add(lblBookTitle);

		patientID = new JTextField();
		patientID.setBounds(236, 102, 192, 28);
		mainPanel.add(patientID);
		patientID.setColumns(10);

		patientName = new JTextField();
		patientName.setColumns(10);
		patientName.setBounds(236, 144, 192, 28);
		mainPanel.add(patientName);

		JButton btnInsertData = new JButton("Insert Data");
		btnInsertData.setBounds(236, 465, 192, 42);
		mainPanel.add(btnInsertData);

		frame.repaint();
		
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPatient(Integer.parseInt(patientID.getText()), patientName.getText());
				
				JOptionPane.showMessageDialog(null, "The patient was added");
				patientID.setText("");
				patientName.setText("");
				frame.repaint();
			}
		});
	}
	
	public void insertPregnancyFunction(JPanel mainPanel) {

		mainPanel.removeAll();
		JLabel pregIDL = new JLabel("Pregnancy ID");
		pregIDL.setBounds(120, 91, 100, 50);
		mainPanel.add(pregIDL);

		JLabel DueDateL = new JLabel("Due Date");
		DueDateL.setBounds(120, 133, 100, 50);
		mainPanel.add(DueDateL);
		
		JLabel typeL = new JLabel("Type");
		typeL.setBounds(120, 175, 100, 50);
		mainPanel.add(typeL);
		
		JLabel docIDL = new JLabel("Doctor ID");
		docIDL.setBounds(120, 217, 100, 50);
		mainPanel.add(docIDL);
		
		JLabel patientIDL = new JLabel("Patient ID");
		patientIDL.setBounds(120, 259, 100, 50);
		mainPanel.add(patientIDL);

		pregID = new JTextField();
		pregID.setBounds(236, 102, 192, 28);
		mainPanel.add(pregID);
		pregID.setColumns(10);

		dueDate = new JTextField();
		dueDate.setColumns(10);
		dueDate.setBounds(236, 144, 192, 28);
		mainPanel.add(dueDate);
		
		type = new JTextField();
		type.setColumns(10);
		type.setBounds(236, 186, 192, 28);
		mainPanel.add(type);
		
		docID = new JTextField();
		docID.setColumns(10);
		docID.setBounds(236, 228, 192, 28);
		mainPanel.add(docID);
		
		patientID = new JTextField();
		patientID.setColumns(10);
		patientID.setBounds(236, 270, 192, 28);
		mainPanel.add(patientID);

		JButton btnInsertData = new JButton("Insert Data");
		btnInsertData.setBounds(236, 465, 192, 42);
		mainPanel.add(btnInsertData);

		frame.repaint();
		
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPregnancy(Integer.parseInt(pregID.getText()), dueDate.getText(), Boolean.parseBoolean(type.getText()), Integer.parseInt(docID.getText()), Integer.parseInt(patientID.getText()));
				
				JOptionPane.showMessageDialog(null, "The pregnancy was added");
				pregID.setText("");
				dueDate.setText("");
				type.setText("");
				docID.setText("");
				patientID.setText("");
				frame.repaint();
			}
		});
	}
	
	public void insertDoctorFunction(JPanel mainPanel) {

		mainPanel.removeAll();
		JLabel docIDL = new JLabel("Doctor ID");
		docIDL.setBounds(120, 91, 100, 50);
		mainPanel.add(docIDL);

		JLabel nameL = new JLabel("Doctor Name");
		nameL.setBounds(120, 133, 100, 50);
		mainPanel.add(nameL);
		
		JLabel ageL = new JLabel("Age");
		ageL.setBounds(120, 175, 100, 50);
		mainPanel.add(ageL);

		docID = new JTextField();
		docID.setBounds(236, 102, 192, 28);
		mainPanel.add(docID);
		docID.setColumns(10);

		docName = new JTextField();
		docName.setColumns(10);
		docName.setBounds(236, 144, 192, 28);
		mainPanel.add(docName);
		
		age = new JTextField();
		age.setColumns(10);
		age.setBounds(236, 186, 192, 28);
		mainPanel.add(age);

		JButton btnInsertData = new JButton("Insert Data");
		btnInsertData.setBounds(236, 465, 192, 42);
		mainPanel.add(btnInsertData);

		frame.repaint();
		
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertDoctor(Integer.parseInt(docID.getText()), docName.getText(), Integer.parseInt(age.getText()));
				
				JOptionPane.showMessageDialog(null, "The doctor was added");
				docID.setText("");
				docName.setText("");
				age.setText("");
				frame.repaint();
			}
		});
	}

	// create pregnancy search interface
	public void searchOrders(JPanel pan) {

		pan.removeAll();
		JRadioButton due = new JRadioButton("Due Date");
		due.setBounds(62, 40, 200, 50);
		pan.add(due);
		
		JRadioButton del = new JRadioButton("Delivery Date");
		del.setBounds(62, 80, 200, 50);
		pan.add(del);

		JRadioButton pregID = new JRadioButton("Pregnancy ID");
		pregID.setBounds(62, 120, 200, 50);
		pan.add(pregID);
		
		JRadioButton docID = new JRadioButton("Doctor ID");
		docID.setBounds(62, 160, 200, 50);
		pan.add(docID);

		searchVlue = new JTextField();
		searchVlue.setBounds(288, 97, 200, 50);
		pan.add(searchVlue);
		searchVlue.setColumns(10);

		JButton search = new JButton("SUBMIT");
		search.setBounds(288, 160, 200, 50);
		pan.add(search);

		lblPleaseTypeSome = new JLabel("Please type some value");
		lblPleaseTypeSome.setBounds(288, 47, 200, 50);
		pan.add(lblPleaseTypeSome);
		ButtonGroup bg = new ButtonGroup();

		bg.add(due);
		bg.add(del);
		bg.add(pregID);
		bg.add(docID);

		frame.repaint();

		JTextPane displaypane = new JTextPane();
		displaypane.setBounds(50, 225, 1000, 800);
		JScrollPane jsp = new JScrollPane(displaypane);

		jsp.setBounds(50, 225, 1000, 800);

		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				due.addActionListener(this);
				del.addActionListener(this);
				pregID.addActionListener(this);
				docID.addActionListener(this);
				
				String column = "";

				if (due.isSelected()) {
					column = "DueDate";
					lblPleaseTypeSome.setText("Search pregnant patients(yyyy-mm-dd)");
					
				} else if (del.isSelected()) {
					column = "DeliveryDate";
					lblPleaseTypeSome.setText("Search previous pregnancies(yyyy-mm-dd)");
					
				} else if (pregID.isSelected()) {
					column = "PregnancyID";
					lblPleaseTypeSome.setText("Search by Pregnancy ID");

				} else if (docID.isSelected()) {
					column = "DoctorID";
					lblPleaseTypeSome.setText("Search by Doctor ID");
				}
				
				JLabel hidden = new JLabel(column);

				displaypane.setText(searchOrders(hidden.getText(), searchVlue.getText()));
				pan.add(jsp);
				frame.repaint();
			}
		});
	}
	
	//search patient methods
	public String searchPatient(String s) {
		String Query;
		String mystr;
		if (s.equalsIgnoreCase("all")) {
			Query = "select PatientID, Name from patient";
			
		} else {
			Query = "select PatientID, Name from patient where Name like \"%" + s + "%\"";
		}

		mystr = "Patients found in the database: \n \n";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy", "root", "NR6jm87K1D1xh-g");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next())
				mystr += rs.getString(1) + "\t | \t" + rs.getString(2) + "\n";
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (mystr.equals("Patients found in the database: \n \n")) {
			mystr = "No patients found";
		} return mystr;
	}

	// Search pregnancy method
	public String searchOrders(String col, String s) {
		
		String Query;
		String mystr;
		
		if (s.equalsIgnoreCase("all")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		Order BY p.PatientID";
			
		} else if (col.equalsIgnoreCase("DueDate")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND pr.DueDate = '" + s + "'\r\n" +
					"		AND " + s + " > CurDate()\r\n";

		} else if (col.equalsIgnoreCase("DeliveryDate")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND pr.DueDate = '" + s + "'\r\n" +
					"		AND " + s + " < CurDate()\r\n";
		
		} else if (col.equalsIgnoreCase("PregnancyID")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND pr.PregnancyID = " + s;
			
		} else if (col.equalsIgnoreCase("DoctorID")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND d.DoctorID = " + s;
		} else {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID";
		}

		mystr = "Pregnancies found in the database: \n \n";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy", "root", "NR6jm87K1D1xh-g");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(Query);

			while (rs.next())
				mystr += rs.getString(1) + "\t |   " + rs.getString(2) + "\t |   " + rs.getString(3) + "\t |   " + rs.getString(4) + "\n";
			
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(mystr);
		if (mystr.equals("Pregnancies found in the database: \n \n")) {
			mystr = "No patients found";
		} return mystr;
	}
	
	public void insertPatient(int patientID, String patientName) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy","root","NR6jm87K1D1xh-g");  
			
			Statement stmt=con.createStatement();  
			String Query = "insert into patient values('"+patientID+"','"+patientName+"', '0')";
			
			stmt.executeUpdate(Query);
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
	public void insertDoctor(int doctorID, String name, int age) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy","root","NR6jm87K1D1xh-g");  
			
			Statement stmt=con.createStatement();  
			String Query = "insert into doctor values('"+doctorID+"','"+name+"', '"+age+"')";
			
			stmt.executeUpdate(Query);
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
	public void insertPregnancy(int pregnancyID, String dueDate, Boolean type, int doctorID, int patientID) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy","root","NR6jm87K1D1xh-g");  
			
			Statement stmt=con.createStatement();  
			String Query = "insert into pregnancy values('"+pregnancyID+"','"+dueDate+"', '"+type+"', '"+doctorID+"', '"+patientID+"')";
			
			stmt.executeUpdate(Query);
			
			Query = "UPDATE patient SET CurrPreg = 1 WHERE PatientID = '" + patientID + "';";
			
			stmt.executeUpdate(Query);
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
}