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
	
	GridBagLayout Gbag; // GridBagLayout 을 선언
	GridBagConstraints gbc; // GridBagLayout을 통해 컨테이너에 담을
	BoardFrame bf;
	public LogIn() {
		super("회원관리 프로그램  v0.1.1");
		Gbag = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		getContentPane().setLayout(Gbag);
		setTitle("Sign Up");
		gbc.fill = GridBagConstraints.BOTH;
		
		p= new JPanel();
		JLabel title = new JLabel("게시판");
		title.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 30));
		p.add(title);
		gbAdd(p, 0,0,gbc.CENTER,1);
		
		JLabel bId = new JLabel("아이디 : ");
		bId.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		tfId = new JTextField(20);
		// 그리드백에 붙이기 gbAdd함수참조, gbAdd(x,y,xwidth,ywidth)
		gbAdd(bId, 0, 1, 1, 1);
		gbAdd(tfId, 1, 1, 3, 1);

		
		JLabel bPwd = new JLabel("비밀번호: ");
		bPwd.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		pfPwd = new JPasswordField(20);
		gbAdd(bPwd, 0, 2, 1, 1);
		gbAdd(pfPwd, 1, 2, 3, 1);
		
		btnOk = new JButton("로그인");
		btnInsert = new JButton("회원가입");
		btnOk.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
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
						System.out.println("로그인성공");
						CurrentUser.setUserID(id);
						bf = new BoardFrame();
						dispose();
					}else {
						JOptionPane.showMessageDialog(p, "로그인 정보를 확인하세요", "로그인 실패", 0);
					}
					
					
				}
			}
		});
		btnInsert.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		btnInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnInsert) {
					new board2();
				}
			}
		}); // 회원가입버튼 리스너 등록
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
		gbc.gridwidth = w; // 컴포넌트가 가로로 차지할 셀의 크기
		gbc.gridheight = h; // 컴포넌트가 세로로 차지할 셀의 크기
		Gbag.setConstraints(c, gbc); // 컴포넌트에 gbc의 조건을 설정한다
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
