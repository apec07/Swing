
import javax.swing.*;
import javax.swing.event.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;


public class JListTutorial implements ActionListener {
	
	//Data
	private List<Account> mAccountList;;
	private final static String[] mTextName ={"User","Password","Note","Button"};
	private final static String[] mButtonName = {"Update","Delete","New"};
	
	private JFrame f = new JFrame("MySQL List");
	private JList<Account> mList = new JList<>();
	private DefaultListModel<Account> model = new DefaultListModel<>();
	
	private JSplitPane splitPane = new JSplitPane();
	private JPanel rPanel = new JPanel();
	private JPanel[] panels = new JPanel[4];
	private JLabel[] labels = new JLabel[4];
	private JButton[] buttons = new JButton[3];
	private JTextField[] texts =  new JTextField[4];
	
	private static Account account ;
	
	@Override
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource()==buttons[0]){
				System.out.println("Click Update " + account.getId());
				Account updateAccount = new Account();
				updateAccount.setId(account.getId());
				updateAccount.setUser(texts[0].getText());
				updateAccount.setPassword(texts[1].getText());
				updateAccount.setNote(texts[2].getText());
				
				int updateNum = Utility.preparedWriteToMySQL(updateAccount,0);
				System.out.println("data num ="+updateList());
				
			}else if(e.getSource()==buttons[1]){
				System.out.println("Click Delete " + account.getId());
				int updateNum = Utility.preparedWriteToMySQL(account,1);
				System.out.println("data num ="+updateList());
				
			}else if(e.getSource()==buttons[2]){
				Account newAccount = new Account();
				newAccount.setUser(texts[0].getText());
				newAccount.setPassword(texts[1].getText());
				newAccount.setNote(texts[2].getText());
				
				int updateNum = Utility.preparedWriteToMySQL(newAccount,2);
				System.out.println("data num ="+updateList());
				System.out.println("Click New ");
			}else{
				System.out.println("Action undefined");
			}
			
		}
		
	private int updateList(){
		
		boolean isConnect = Utility.MySQLConnect();
		if(!isConnect) return 0;
		boolean isTableCreated = Utility.createAccount();
		if(!isTableCreated){
			System.out.println("isTableCreated = " + isTableCreated);
			return 0;
		}
		model = (DefaultListModel)mList.getModel();
		model.clear();
		/*
		int selectedIndex = mList.getSelectedIndex();
		if (selectedIndex != -1) {
    model.remove(selectedIndex);
		}
		*/
		mAccountList = Utility.readFromMySQL();
		System.out.println("mAccountList = "+mAccountList);
		if(mAccountList.size()==0) return 0;
	
		System.out.println(mAccountList);
		for(int i=0;i<mAccountList.size();i++){
			model.addElement(mAccountList.get(i));
			mAccountList.get(i).getUser();
		}
		return mAccountList.size();
	}

	public JListTutorial(){
		mList.setModel(model);
		/*
		 Query DATA! put on left panel
		*/
		System.out.println("data num ="+updateList());
		
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
			texts[i] = new JTextField(20);
			panels[i].add(labels[i]);
			panels[i].add(texts[i]);
			buttons[i] = new JButton(mButtonName[i]);
			panels[3].add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		
		mList.getSelectionModel().addListSelectionListener(e->{
			
			//Delete & Update : PASS - catch NullPointerException 
			
			account = mList.getSelectedValue(); // return null (update & delete)
			System.out.println("addListSelectionListener account = "+ account );
			String user,password,note;
		  try{
		  	user = account.getUser();
				password = account.getPassword();
				note = account.getNote();
		  }catch(NullPointerException ex){
		   System.err.println("delete - " + ex);
		   	user=password=note ="";
			}
			
			texts[0].setText(user);
			texts[1].setText(password);
			texts[2].setText(note);
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