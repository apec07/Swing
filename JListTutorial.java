
import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;


public class JListTutorial implements ActionListener {
	
	//Data
	private List<Account> mAccountList;;
	private final static String[] mTextName ={"User","Password","Note","Button"};
	private final static String[] mButtonName = {"Update","Delete","Restore"};
	
	private JFrame f = new JFrame("MySQL List");
	private JList<Account> mList = new JList<>();
	private DefaultListModel<Account> model = new DefaultListModel<>();
	
	private JSplitPane splitPane = new JSplitPane();
	private JPanel rPanel = new JPanel();
	private JPanel[] panels = new JPanel[4];
	private JLabel[] labels = new JLabel[4];
	private JButton[] buttons = new JButton[3];
	private TextField[] texts =  new TextField[4];
	
	@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==buttons[0]){
				System.out.println("Click Update");
			}else if(e.getSource()==buttons[1]){
				System.out.println("Click Delete");
			}else if(e.getSource()==buttons[2]){
				System.out.println("Click Restore");
			}else{
				System.out.println("Action undefined");
			}
			
		}

	public JListTutorial(){
		mList.setModel(model);
		/*
		 Query DATA! put on left panel
		*/
		boolean isConnect = Utility.MySQLConnect();
		if(!isConnect) return;
		
		mAccountList = Utility.readFromMySQL();
		if(mAccountList.size()==0) return;
	
		System.out.println(mAccountList);
		for(int i=0;i<mAccountList.size();i++){
			model.addElement(mAccountList.get(i));
			mAccountList.get(i).getUser();
		}
		
		/*
			right panel show data with addListSelectionListener
		*/
		rPanel.setLayout(new GridLayout(4,1));
		for(int i =0 ; i<4 ; i++){
			panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			rPanel.add(panels[i]);
		}
		for(int i =0 ; i<3 ; i++){
			labels[i] = new JLabel(mTextName[i]);
			texts[i] = new TextField(20);
			panels[i].add(labels[i]);
			panels[i].add(texts[i]);
			buttons[i] = new JButton(mButtonName[i]);
			panels[3].add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		
		mList.getSelectionModel().addListSelectionListener(e->{
			Account account = mList.getSelectedValue();
			String user = account.getUser();
			String password = account.getPassword();
			texts[0].setText(user);
			texts[1].setText(password);
		});
		
		splitPane.setLeftComponent(new JScrollPane(mList));
		//rPanel.add(rLabel);
		splitPane.setRightComponent(rPanel);
		
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(splitPane);
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);
	}

	public static void main (String args[]){
		
	
		new JListTutorial();
		
	}
}