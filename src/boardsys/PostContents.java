package boardsys;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostContents extends JPanel{
	JPanel pHeader, pTail;
	JButton bDelete, bUpdate,bRec, bInsert, bBack;
	JTextField tftitle;
	JTextArea taContents;
	JLabel ldate, lid, lclicked, lrec;
	String postNum;
	BoardFrame bf;
	PostList pl;
	private final int PADDING = 3;
	int x; //���������� ���� ����
	
	//�ۼ�Ŭ���� ������
	public PostContents(BoardFrame boardFrame) {
		this.bf=boardFrame;
		ContentsUI();
		tftitle.setEditable(true);
		taContents.setEditable(true);
		bDelete.setVisible(false);
		bUpdate.setVisible(false);
		bRec.setVisible(false);
	}
	//���� Ŭ������ �� ������
	public PostContents(BoardFrame bf,String binfo) {
		ContentsUI();
		this.bf=bf;
		this.pl=pl;
		String []postInfo = binfo.split("/"); //[0]�� �۹�ȣ, [1]�� �������̵�
		if(!postInfo[1].equals(CurrentUser.getUserID())) {
			tftitle.setEditable(false);
			taContents.setEditable(false);
			bInsert.setVisible(false);
			bDelete.setVisible(false);
			bUpdate.setVisible(false);
			bRec.setVisible(true);
			bBack.setVisible(true);
		}else {
			tftitle.setEditable(false);
			taContents.setEditable(false);
			bInsert.setVisible(false);
			bDelete.setVisible(true);
			bUpdate.setVisible(true);
			bRec.setVisible(true);
			bBack.setVisible(true);
		}
		x=0;
		BoardDatabase bdb = new BoardDatabase();
		BoardDTO bData = bdb.getPostDTO(postInfo[0]);
		setContents(bData);
	}
	public void ContentsUI() {
		setLayout(new BorderLayout());
		tftitle = new JTextField(20);
		tftitle.setEditable(true);
		tftitle.setFont(new Font("1�ƻ������ R", Font.PLAIN, 25));
		
		lid= new JLabel();
		lid.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		ldate = new JLabel();
		ldate.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		lclicked = new JLabel();
		lclicked.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		lrec= new JLabel();
		lrec.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		
		
		pHeader = new JPanel();
		pHeader.setLayout(new FlowLayout(FlowLayout.LEADING, PADDING, PADDING));
		pHeader.add(tftitle);
		pHeader.add(lid);
		pHeader.add(ldate);
		pHeader.add(lclicked);
		pHeader.add(lrec);
		
		pHeader.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

		add(pHeader, BorderLayout.NORTH);
		
		taContents = new JTextArea();
		taContents.setEditable(true);
		add(taContents);
		bInsert = new JButton("�ۼ�");
		bInsert.setFont(new Font("1�ƻ������ R", Font.PLAIN, 20));
		bInsert.addActionListener(new ButtonListener());
		
		bUpdate = new JButton("����");
		bUpdate.setFont(new Font("1�ƻ������ R", Font.PLAIN, 20));
		bUpdate.addActionListener(new ButtonListener());
		
		bDelete = new JButton("����");
		bDelete.setFont(new Font("1�ƻ������ R", Font.PLAIN, 20));
		bDelete.addActionListener(new ButtonListener());
		
		bRec = new JButton("��õ");
		bRec.setFont(new Font("1�ƻ������ R", Font.PLAIN, 20));
		bRec.addActionListener(new ButtonListener());
		
		bBack = new JButton("�ڷΰ���");
		bBack.setFont(new Font("1�ƻ������ R", Font.PLAIN, 20));
		bBack.addActionListener(new ButtonListener());
		pTail = new JPanel(new FlowLayout(2));
		pTail.add(bInsert);
		pTail.add(bUpdate);
		pTail.add(bDelete);
		pTail.add(bRec);
		pTail.add(bBack);
		pTail.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

		add(pTail, BorderLayout.SOUTH);	
	}
	
	//ȭ�鿡 �Խñ� ������ ����
	private void setContents(BoardDTO dto) {
		postNum= dto.getNum();
		String utitle= dto.getTitle();
		String contents = dto.getContents();
		String uid = dto.getId();
		String date= dto.getDate();
		String clicked = dto.getClicked();
		String rec = dto.getRec();
		
		BoardDatabase bdb = new BoardDatabase();
		bdb.updateClicked(dto);
		
		tftitle.setText(utitle);
		taContents.setText(contents);
		lid.setText("�ۼ���: "+ uid +"  ");
		ldate.setText("�ۼ�����: "+getDate()+"  ");
		lclicked.setText("��ȸ��: "+String.valueOf(Integer.parseInt(clicked)+1)+"  ");
		lrec.setText("��õ��: "+rec);
	}
	//ȭ�鿡�� ������ ������
	private BoardDTO getContents() {
		BoardDTO postInfo = new BoardDTO();
		String title = tftitle.getText();
		String contents =taContents.getText();
		String strToday=getDate();
        String id = CurrentUser.getUserID();
        String clicked=lclicked.getText();
        String rec =null;
        if(lrec.getText().length()>0) {
        	rec= lrec.getText().substring(5);
        }
        postInfo.setNum(postNum);
        postInfo.setTitle(title);
        postInfo.setContents(contents);
        postInfo.setId(id);
        postInfo.setDate(strToday);
        postInfo.setClicked(clicked);
        postInfo.setRec(rec);
        return postInfo;
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==bInsert) {
				BoardDTO dto = getContents();
				BoardDatabase bdb = new BoardDatabase();
				boolean ok = bdb.insertPost(dto);
				if(ok) {
					JOptionPane.showMessageDialog(PostContents.this, "�ۼ��� �Ϸ�Ǿ����ϴ�.");					
					bf.change("PostList");
				}else {
					JOptionPane.showMessageDialog(PostContents.this, "���ε带 �����Ͽ����ϴ�.");
				}
			}else if(e.getSource()==bDelete) {
				int x = JOptionPane.showConfirmDialog(PostContents.this, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if(x == JOptionPane.OK_OPTION) {
					BoardDatabase bdb = new BoardDatabase();
					Boolean ok = bdb.deletePost(postNum);
					if(ok) {
						JOptionPane.showMessageDialog(PostContents.this, "�����Ǿ����ϴ�.");
						bf.change("PostList");
					}
				}
				
			}else if(e.getSource()==bBack) {
				bf.change("PostList");
			}else if(e.getSource()==bUpdate) {
				bRec.setVisible(false);
				if(x==0) {
					tftitle.setEditable(true);
					taContents.setEditable(true);
					x++;
				}else {
					int x = JOptionPane.showConfirmDialog(PostContents.this, "�����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
					if (x == JOptionPane.OK_OPTION) {
						// 1. ȭ���� ������ ��´�.
						BoardDTO dto = getContents();
						// 2. �������� DB�� ����
						BoardDatabase bdb = new BoardDatabase();
						boolean ok = bdb.updatePost(dto);

						if (ok) {
							JOptionPane.showMessageDialog(PostContents.this, "�����Ǿ����ϴ�.");
							bf.change("PostList");
						}
					}
				}
				
			}else if(e.getSource()==bRec) {
				BoardDatabase bdb = new BoardDatabase();
				BoardDTO dto = getContents();
				boolean ok = bdb.checkRec(CurrentUser.getUserID(), dto.getNum()); //false�� ��� �̹� ��õ�� ������
				if(ok) {
					bdb.updateRec(dto); //�Խñ��� ��õ���� ����
					bdb.insertRec(CurrentUser.getUserID(), dto.getNum()); //��õ�� �ߴ��� Ȯ��
					lrec.setText("��õ��: "+String.valueOf(Integer.parseInt(dto.getRec())+1));
				}else {
					JOptionPane.showMessageDialog(PostContents.this, "�̹� ��õ�� �Խù��Դϴ�.");
				}
				
			}
		}
		
	}
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        String strToday = sdf.format(cal.getTime());
        return strToday;
	}
}


