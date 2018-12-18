package boardsys;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction;

import java.awt.*;
import java.awt.event.*;
import java.security.acl.Group;
import java.sql.Date;
import java.util.Calendar;

public class board2 extends JFrame {
	JPanel p;
	JTextField tfId, tfName, tfAddr, tfEmail;
	JTextField tfTel1, tfTel2, tfTel3; // 전화
	JComboBox cbYear, cbMonth, cbDate; // 생년월일
	JComboBox cbJob; // 직업
	JPasswordField pfPwd, pfPwd2; // 비밀번호
	JRadioButton rbMan, rbWoman; // 남, 여
	JTextArea taIntro;
	JButton btnInsert, btnCancel, btnUpdate, btnDelete, btnRedun; // 가입, 취소, 수정 , 탈퇴 버튼
	Calendar cal = Calendar.getInstance();

	GridBagLayout Gbag; // GridBagLayout 을 선언
	GridBagConstraints gbc; // GridBagLayout을 통해 컨테이너에 담을
	memberList mList;
	
	boolean redundancy=true; //아이디 중복확인용

	// 기본 생성자
	public board2() {
		signUpUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
	}

	// 회원가입 클릭 시 생성자
	public board2(memberList mList) {
		signUpUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;
	}

	// 수정 및 삭제용 생성자
	public board2(String id, memberList mList) {
		signUpUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		this.mList = mList;

		database db = new database();
		MemberDTO vMem = db.getMemberDTO(id);
		viewData(vMem);
	}

	public void signUpUI() {
		Gbag = new GridBagLayout();
		gbc = new GridBagConstraints();
		setLayout(Gbag);
		setTitle("Sign Up");
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		// 아이디
		JLabel bId = new JLabel("아이디 * : ");
		tfId = new JTextField(15);
		btnRedun = new JButton("중복확인");
		btnRedun.addActionListener(new SignUpButtonListener());
		// 그리드백에 붙이기 gbAdd함수참조, gbAdd(x,y,xwidth,ywidth)
		gbAdd(bId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 1, 1);
		gbAdd(btnRedun, 2,0,1,1);

		// 비밀번호
		JLabel bPwd = new JLabel("비밀번호 * : ");
		pfPwd = new JPasswordField(20);
		gbAdd(bPwd, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		JLabel bPwd2 = new JLabel("비밀번호 확인 * : ");
		pfPwd2 = new JPasswordField(20);
		gbAdd(bPwd2, 0, 2, 1, 1);
		gbAdd(pfPwd2, 1, 2, 3, 1);

		// 이름
		JLabel bName = new JLabel("이름 * :");
		tfName = new JTextField(20);
		gbAdd(bName, 0, 3, 1, 1);
		gbAdd(tfName, 1, 3, 3, 1);

		// 전화
		JLabel bTel = new JLabel("전화 * :");
		JPanel pTel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 왼쪽에서 오른쪽으로 흐르는 패널
		tfTel1 = new JTextField(6);
		tfTel2 = new JTextField(6);
		tfTel3 = new JTextField(6);
		pTel.add(tfTel1);
		pTel.add(new JLabel(" - "));
		pTel.add(tfTel2);
		pTel.add(new JLabel(" - "));
		pTel.add(tfTel3);
		gbAdd(bTel, 0, 4, 1, 1);
		gbAdd(pTel, 1, 4, 3, 1);
		setVisible(true);

		// 주소
		JLabel bAddr = new JLabel("주소: ");
		tfAddr = new JTextField(20);
		gbAdd(bAddr, 0, 5, 1, 1);
		gbAdd(tfAddr, 1, 5, 3, 1);

		// 생일
		JLabel bBirth = new JLabel("생일 * : ");
		String[] arrYear = new String[51];
		String[] arrMonth = new String[13];
		String[] arrDay = new String[32];
		arrYear[0] = "---";
		arrMonth[0] = "---";
		arrDay[0] = "---";
		for (int i = 0; i < 50; i++) {
			arrYear[i + 1] = String.valueOf(cal.get(Calendar.YEAR) - i);
		}
		for (int i = 0; i < 12; i++) {
			arrMonth[i + 1] = String.valueOf(i + 1);
		}
		for (int i = 0; i < 31; i++) {
			arrDay[i + 1] = String.valueOf(i + 1);
		}
		cbYear = new JComboBox(arrYear);
		cbMonth = new JComboBox(arrMonth);
		cbDate = new JComboBox(arrDay);

		JPanel pBirth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pBirth.add(cbYear);
		pBirth.add(new JLabel("/"));
		pBirth.add(cbMonth);
		pBirth.add(new JLabel("/"));
		pBirth.add(cbDate);
		gbAdd(bBirth, 0, 6, 1, 1);
		gbAdd(pBirth, 1, 6, 3, 1);

		// 직업
		JLabel bJob = new JLabel("직업 : ");
		String[] arrJob = { "---", "학생", "직장인", "주부" };
		cbJob = new JComboBox(arrJob);
		JPanel pJob = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pJob.add(cbJob);
		gbAdd(bJob, 0, 7, 1, 1);
		gbAdd(pJob, 1, 7, 3, 1);

		// 성별
		JLabel bGender = new JLabel("성별 : ");
		JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbMan = new JRadioButton("남", true);
		rbWoman = new JRadioButton("여", true);
		ButtonGroup group = new ButtonGroup();
		group.add(rbMan);
		group.add(rbWoman);
		pGender.add(rbMan);
		pGender.add(rbWoman);
		gbAdd(bGender, 0, 8, 1, 1);
		gbAdd(pGender, 1, 8, 3, 1);

		// 이메일
		JLabel bEmail = new JLabel("이메일 * : ");
		tfEmail = new JTextField(20);
		gbAdd(bEmail, 0, 9, 1, 1);
		gbAdd(tfEmail, 1, 9, 3, 1);
		// 자기소개
		JLabel bIntro = new JLabel("자기 소개: ");
		taIntro = new JTextArea(5, 20); // 행 : 열
		JScrollPane pane = new JScrollPane(taIntro);
		gbAdd(bIntro, 0, 10, 1, 1);
		gbAdd(pane, 1, 10, 3, 1);

		// 버튼
		JPanel pButton = new JPanel();
		btnInsert = new JButton("가입");
		btnUpdate = new JButton("수정");
		btnDelete = new JButton("탈퇴");
		btnCancel = new JButton("취소");
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 11, 4, 1);

		// 버튼리스너
		btnInsert.addActionListener(new SignUpButtonListener());
		btnUpdate.addActionListener(new SignUpButtonListener());
		btnCancel.addActionListener(new SignUpButtonListener());
		btnDelete.addActionListener(new SignUpButtonListener());

		setSize(400, 600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //현재창만 닫는다.

	}

	private class SignUpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			boolean pwCheck = false;
			//pwCheck = pwConsist();
			if (e.getSource() == btnInsert) {
				if(redundancy) { //아이디 중복확인
					JOptionPane.showMessageDialog(p, "아이디 중복확인을 해주세요.");
				}else { 
					pwCheck = pwConsist(); //비밀번호 확인
					if (pwCheck == true &&!pfPwd.getText().equals("")) { //비밀번호가 동일하고 비어있지 않는 경우
						MemberDTO dto = getViewData();
						if(!dto.getId().isEmpty()&&!dto.getPwd().isEmpty()&&!dto.getName().isEmpty()&&!dto.getTel().isEmpty()&&!dto.getBirth().isEmpty()&&!dto.getEmail().isEmpty()) {
							database db = new database(); 
							boolean ok = db.insertMember(dto); //데이터베이스 삽입
							if (ok) {
								JOptionPane.showMessageDialog(p, "가입이 완료되었습니다.");
								dispose();
							}else {
								JOptionPane.showMessageDialog(p, "가입이 정상적으로 처리되지 않았습니다.");
							}
						}else {
							JOptionPane.showMessageDialog(p, "필수 항목을 입력해주세요.");
						}
						
						
					}else {
						JOptionPane.showMessageDialog(p, "비밀번호를 확인하세요", "비밀번호 확인", 0);
					}
				}
			} else if (e.getSource() == btnCancel) {
				int x = JOptionPane.showConfirmDialog(p, "변경사항이 저장되지 않습니다", "취소", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					dispose();
				}

			} else if (e.getSource() == btnUpdate) {
				int x = JOptionPane.showConfirmDialog(p, "수정하시겠습니까?", "수정", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					// 1. 화면의 정보를 얻는다.
					MemberDTO dto = getViewData();
					// 2. 그정보로 DB를 수정
					database db = new database();
					boolean ok = db.updateMember(dto);

					if (ok && pwCheck == true) {
						JOptionPane.showMessageDialog(p, "수정되었습니다.");
						mList.jTableRefresh();
						dispose();
					} else {
						JOptionPane.showMessageDialog(p, "비밀번호를 확인하세요");
					}
				}
			} else if (e.getSource() == btnDelete) {
				int x = JOptionPane.showConfirmDialog(p, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					String id = tfId.getText();
					String pwd = pfPwd.getText();
					database dao = new database();
					boolean ok = dao.deleteMember(id, pwd);
					if (ok && pwCheck == true) { // 길이가 0이면
						JOptionPane.showMessageDialog(p, "삭제완료");
						mList.jTableRefresh();
						dispose();
					} else {
						JOptionPane.showMessageDialog(p, "비밀번호를 확인하세요");
						return; // 메소드 끝
					}
				}
			}else if(e.getSource()==btnRedun) {
				database db = new database();
				redundancy = db.checkRedundancy(tfId.getText());
				if(!redundancy) {//false 의 경우 중복 없음
					JOptionPane.showMessageDialog(p, "사용할 수 있는 아이디입니다.");
				}else {
					JOptionPane.showMessageDialog(p, "이미 사용중인 아이디입니다.");
				}
			}
		}
	}

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w; // 컴포넌트가 가로로 차지할 셀의 크기
		gbc.gridheight = h; // 컴포넌트가 세로로 차지할 셀의 크기
		Gbag.setConstraints(c, gbc); // 컴포넌트에 gbc의 조건을 설정한다
		gbc.insets = new Insets(2, 2, 2, 2);
		add(c, gbc);
	}// gbAdd

	private boolean pwConsist() {
		String pw1 = String.valueOf(pfPwd.getPassword());
		String pw2 = String.valueOf(pfPwd2.getPassword());
		if (pw1.equals(pw2))
			return true;
		else
			return false;
	}

	private void viewData(MemberDTO vMem) {

		String id = vMem.getId();
		String pwd = vMem.getPwd();
		String name = vMem.getName();
		String tel = vMem.getTel();
		String addr = vMem.getAddr();
		String birth = vMem.getBirth();
		String job = vMem.getJob();
		String gender = vMem.getGender();
		String email = vMem.getEmail();
		String intro = vMem.getIntro();

		// 화면에 세팅
		tfId.setText(id);
		tfId.setEditable(false); // 편집 안되게
		pfPwd.setText(""); // 비밀번호는 안보여준다.
		tfName.setText(name);
		String[] tels = tel.split("-");
		tfTel1.setText(tels[0]);
		tfTel2.setText(tels[1]);
		tfTel3.setText(tels[2]);
		tfAddr.setText(addr);

		int year = cal.get(Calendar.YEAR) - Integer.parseInt(birth.substring(0, 4));
		cbYear.setSelectedIndex(year + 1);
		cbYear.enable(false);
		cbMonth.setSelectedIndex(Integer.parseInt(birth.substring(4, 6)));
		cbMonth.enable(false);
		cbDate.setSelectedIndex(Integer.parseInt(birth.substring(6, 8)));
		cbDate.enable(false);
		cbJob.setSelectedItem(job);

		if (gender.equals("M")) {
			rbMan.setSelected(true);
			rbWoman.enable(false);
		} else if (gender.equals("W")) {
			rbWoman.setSelected(true);
			rbMan.enable(false);
		}

		tfEmail.setText(email);
		taIntro.setText(intro);

	}// viewData

	public MemberDTO getViewData() {

		// 화면에서 사용자가 입력한 내용을 얻는다.
		MemberDTO dto = new MemberDTO();
		String id = tfId.getText();
		String pwd = pfPwd.getText();
		String name = tfName.getText();
		String tel1 = tfTel1.getText();
		String tel2 = tfTel2.getText();
		String tel3 = tfTel3.getText();
		String tel = tel1 + "-" + tel2 + "-" + tel3;
		String addr = tfAddr.getText();
		String birth1 = (String) cbYear.getSelectedItem();
		String birth2 = (String) cbMonth.getSelectedItem();
		if(birth2.length()==1)birth2="0"+birth2;
		String birth3 = (String) cbDate.getSelectedItem();
		if(birth3.length()==1)birth3="0"+birth3;
		// String birth = birth1+"/"+birth2+"/"+birth3;
		String birth = birth1 + birth2 + birth3;
		String job = (String) cbJob.getSelectedItem();
		String gender = "";
		if (rbMan.isSelected()) {
			gender = "M";
		} else if (rbWoman.isSelected()) {
			gender = "W";
		}

		String email = tfEmail.getText();
		String intro = taIntro.getText();

		// dto에 담는다.
		dto.setId(id);
		dto.setPwd(pwd);
		dto.setName(name);
		dto.setTel(tel);
		dto.setAddr(addr);
		dto.setBirth(birth);
		dto.setJob(job);
		dto.setGender(gender);
		dto.setEmail(email);
		dto.setIntro(intro);

		return dto;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new board2();
	}

}
