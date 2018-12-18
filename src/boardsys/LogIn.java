//Sign in panel
package boardsys;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LogIn extends JFrame {
	JPanel p, loginP, buttonP;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnOk, btnInsert;
	
	GridBagLayout Gbag; // GridBagLayout �� ����
	GridBagConstraints gbc; // GridBagLayout�� ���� �����̳ʿ� ����
	BoardFrame bf;
	public LogIn() {
		super("ȸ������ ���α׷�  v0.1.1");
		Gbag = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		getContentPane().setLayout(Gbag);
		setTitle("Sign Up");
		gbc.fill = GridBagConstraints.BOTH;
		
		p= new JPanel();
		JLabel title = new JLabel("�Խ���");
		title.setFont(new Font("1�ƻ������ R", Font.PLAIN, 30));
		p.add(title);
		gbAdd(p, 0,0,gbc.CENTER,1);
		
		JLabel bId = new JLabel("���̵� : ");
		bId.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		tfId = new JTextField(20);
		// �׸���鿡 ���̱� gbAdd�Լ�����, gbAdd(x,y,xwidth,ywidth)
		gbAdd(bId, 0, 1, 1, 1);
		gbAdd(tfId, 1, 1, 3, 1);

		
		JLabel bPwd = new JLabel("��й�ȣ: ");
		bPwd.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		pfPwd = new JPasswordField(20);
		gbAdd(bPwd, 0, 2, 1, 1);
		gbAdd(pfPwd, 1, 2, 3, 1);
		
		btnOk = new JButton("�α���");
		btnInsert = new JButton("ȸ������");
		btnOk.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		btnOk.addActionListener(new ActionListener() {
			String id;
			String pw;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnOk) {
					id= tfId.getText();
					pw= pfPwd.getText();
					database db = new database();
					MemberDTO vMem = db.getLoginInfo(id, pw);
					if(id.equals("manager")&& pw.equals("manager")) {
						CurrentUser.setUserID("manager");
						new memberList();
					}else if(id.equals(vMem.getId())&&pw.equals(vMem.getPwd())){
						System.out.println("�α��μ���");
						CurrentUser.setUserID(id);
						bf = new BoardFrame();
						dispose();
					}else {
						JOptionPane.showMessageDialog(p, "�α��� ������ Ȯ���ϼ���", "�α��� ����", 0);
					}
					
					
				}
			}
		});
		btnInsert.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		btnInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnInsert) {
					new board2();
				}
			}
		}); // ȸ�����Թ�ư ������ ���
		buttonP= new JPanel();
		buttonP.add(btnOk);
		buttonP.add(btnInsert);
		gbAdd(buttonP, 0, 3, gbc.CENTER, 1);
		
		
		setVisible(true);
		setSize(500,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w; // ������Ʈ�� ���η� ������ ���� ũ��
		gbc.gridheight = h; // ������Ʈ�� ���η� ������ ���� ũ��
		Gbag.setConstraints(c, gbc); // ������Ʈ�� gbc�� ������ �����Ѵ�
		gbc.insets = new Insets(20, 2, 2, 2);
		getContentPane().add(c, gbc);
	}// gbAdd
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LogIn();
	}

}

class CurrentUser{
	private static String userID;
	public static String getUserID() {
		return userID;
	}

	public static void setUserID(String userID) {
		CurrentUser.userID = userID;
	}
	
}
