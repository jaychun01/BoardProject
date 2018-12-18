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
	JTextField tfTel1, tfTel2, tfTel3; // ��ȭ
	JComboBox cbYear, cbMonth, cbDate; // �������
	JComboBox cbJob; // ����
	JPasswordField pfPwd, pfPwd2; // ��й�ȣ
	JRadioButton rbMan, rbWoman; // ��, ��
	JTextArea taIntro;
	JButton btnInsert, btnCancel, btnUpdate, btnDelete, btnRedun; // ����, ���, ���� , Ż�� ��ư
	Calendar cal = Calendar.getInstance();

	GridBagLayout Gbag; // GridBagLayout �� ����
	GridBagConstraints gbc; // GridBagLayout�� ���� �����̳ʿ� ����
	memberList mList;
	
	boolean redundancy=true; //���̵� �ߺ�Ȯ�ο�

	// �⺻ ������
	public board2() {
		signUpUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
	}

	// ȸ������ Ŭ�� �� ������
	public board2(memberList mList) {
		signUpUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;
	}

	// ���� �� ������ ������
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

		// ���̵�
		JLabel bId = new JLabel("���̵� * : ");
		tfId = new JTextField(15);
		btnRedun = new JButton("�ߺ�Ȯ��");
		btnRedun.addActionListener(new SignUpButtonListener());
		// �׸���鿡 ���̱� gbAdd�Լ�����, gbAdd(x,y,xwidth,ywidth)
		gbAdd(bId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 1, 1);
		gbAdd(btnRedun, 2,0,1,1);

		// ��й�ȣ
		JLabel bPwd = new JLabel("��й�ȣ * : ");
		pfPwd = new JPasswordField(20);
		gbAdd(bPwd, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		JLabel bPwd2 = new JLabel("��й�ȣ Ȯ�� * : ");
		pfPwd2 = new JPasswordField(20);
		gbAdd(bPwd2, 0, 2, 1, 1);
		gbAdd(pfPwd2, 1, 2, 3, 1);

		// �̸�
		JLabel bName = new JLabel("�̸� * :");
		tfName = new JTextField(20);
		gbAdd(bName, 0, 3, 1, 1);
		gbAdd(tfName, 1, 3, 3, 1);

		// ��ȭ
		JLabel bTel = new JLabel("��ȭ * :");
		JPanel pTel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // ���ʿ��� ���������� �帣�� �г�
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

		// �ּ�
		JLabel bAddr = new JLabel("�ּ�: ");
		tfAddr = new JTextField(20);
		gbAdd(bAddr, 0, 5, 1, 1);
		gbAdd(tfAddr, 1, 5, 3, 1);

		// ����
		JLabel bBirth = new JLabel("���� * : ");
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

		// ����
		JLabel bJob = new JLabel("���� : ");
		String[] arrJob = { "---", "�л�", "������", "�ֺ�" };
		cbJob = new JComboBox(arrJob);
		JPanel pJob = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pJob.add(cbJob);
		gbAdd(bJob, 0, 7, 1, 1);
		gbAdd(pJob, 1, 7, 3, 1);

		// ����
		JLabel bGender = new JLabel("���� : ");
		JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbMan = new JRadioButton("��", true);
		rbWoman = new JRadioButton("��", true);
		ButtonGroup group = new ButtonGroup();
		group.add(rbMan);
		group.add(rbWoman);
		pGender.add(rbMan);
		pGender.add(rbWoman);
		gbAdd(bGender, 0, 8, 1, 1);
		gbAdd(pGender, 1, 8, 3, 1);

		// �̸���
		JLabel bEmail = new JLabel("�̸��� * : ");
		tfEmail = new JTextField(20);
		gbAdd(bEmail, 0, 9, 1, 1);
		gbAdd(tfEmail, 1, 9, 3, 1);
		// �ڱ�Ұ�
		JLabel bIntro = new JLabel("�ڱ� �Ұ�: ");
		taIntro = new JTextArea(5, 20); // �� : ��
		JScrollPane pane = new JScrollPane(taIntro);
		gbAdd(bIntro, 0, 10, 1, 1);
		gbAdd(pane, 1, 10, 3, 1);

		// ��ư
		JPanel pButton = new JPanel();
		btnInsert = new JButton("����");
		btnUpdate = new JButton("����");
		btnDelete = new JButton("Ż��");
		btnCancel = new JButton("���");
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 11, 4, 1);

		// ��ư������
		btnInsert.addActionListener(new SignUpButtonListener());
		btnUpdate.addActionListener(new SignUpButtonListener());
		btnCancel.addActionListener(new SignUpButtonListener());
		btnDelete.addActionListener(new SignUpButtonListener());

		setSize(400, 600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //����â�� �ݴ´�.

	}

	private class SignUpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			boolean pwCheck = false;
			//pwCheck = pwConsist();
			if (e.getSource() == btnInsert) {
				if(redundancy) { //���̵� �ߺ�Ȯ��
					JOptionPane.showMessageDialog(p, "���̵� �ߺ�Ȯ���� ���ּ���.");
				}else { 
					pwCheck = pwConsist(); //��й�ȣ Ȯ��
					if (pwCheck == true &&!pfPwd.getText().equals("")) { //��й�ȣ�� �����ϰ� ������� �ʴ� ���
						MemberDTO dto = getViewData();
						if(!dto.getId().isEmpty()&&!dto.getPwd().isEmpty()&&!dto.getName().isEmpty()&&!dto.getTel().isEmpty()&&!dto.getBirth().isEmpty()&&!dto.getEmail().isEmpty()) {
							database db = new database(); 
							boolean ok = db.insertMember(dto); //�����ͺ��̽� ����
							if (ok) {
								JOptionPane.showMessageDialog(p, "������ �Ϸ�Ǿ����ϴ�.");
								dispose();
							}else {
								JOptionPane.showMessageDialog(p, "������ ���������� ó������ �ʾҽ��ϴ�.");
							}
						}else {
							JOptionPane.showMessageDialog(p, "�ʼ� �׸��� �Է����ּ���.");
						}
						
						
					}else {
						JOptionPane.showMessageDialog(p, "��й�ȣ�� Ȯ���ϼ���", "��й�ȣ Ȯ��", 0);
					}
				}
			} else if (e.getSource() == btnCancel) {
				int x = JOptionPane.showConfirmDialog(p, "��������� ������� �ʽ��ϴ�", "���", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					dispose();
				}

			} else if (e.getSource() == btnUpdate) {
				int x = JOptionPane.showConfirmDialog(p, "�����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					// 1. ȭ���� ������ ��´�.
					MemberDTO dto = getViewData();
					// 2. �������� DB�� ����
					database db = new database();
					boolean ok = db.updateMember(dto);

					if (ok && pwCheck == true) {
						JOptionPane.showMessageDialog(p, "�����Ǿ����ϴ�.");
						mList.jTableRefresh();
						dispose();
					} else {
						JOptionPane.showMessageDialog(p, "��й�ȣ�� Ȯ���ϼ���");
					}
				}
			} else if (e.getSource() == btnDelete) {
				int x = JOptionPane.showConfirmDialog(p, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					String id = tfId.getText();
					String pwd = pfPwd.getText();
					database dao = new database();
					boolean ok = dao.deleteMember(id, pwd);
					if (ok && pwCheck == true) { // ���̰� 0�̸�
						JOptionPane.showMessageDialog(p, "�����Ϸ�");
						mList.jTableRefresh();
						dispose();
					} else {
						JOptionPane.showMessageDialog(p, "��й�ȣ�� Ȯ���ϼ���");
						return; // �޼ҵ� ��
					}
				}
			}else if(e.getSource()==btnRedun) {
				database db = new database();
				redundancy = db.checkRedundancy(tfId.getText());
				if(!redundancy) {//false �� ��� �ߺ� ����
					JOptionPane.showMessageDialog(p, "����� �� �ִ� ���̵��Դϴ�.");
				}else {
					JOptionPane.showMessageDialog(p, "�̹� ������� ���̵��Դϴ�.");
				}
			}
		}
	}

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w; // ������Ʈ�� ���η� ������ ���� ũ��
		gbc.gridheight = h; // ������Ʈ�� ���η� ������ ���� ũ��
		Gbag.setConstraints(c, gbc); // ������Ʈ�� gbc�� ������ �����Ѵ�
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

		// ȭ�鿡 ����
		tfId.setText(id);
		tfId.setEditable(false); // ���� �ȵǰ�
		pfPwd.setText(""); // ��й�ȣ�� �Ⱥ����ش�.
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

		// ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
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

		// dto�� ��´�.
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
