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
	int x; //수정조건을 위한 변수
	
	//작성클릭시 생성자
	public PostContents(BoardFrame boardFrame) {
		this.bf=boardFrame;
		ContentsUI();
		tftitle.setEditable(true);
		taContents.setEditable(true);
		bDelete.setVisible(false);
		bUpdate.setVisible(false);
		bRec.setVisible(false);
	}
	//글을 클릭했을 때 생성자
	public PostContents(BoardFrame bf,String binfo) {
		ContentsUI();
		this.bf=bf;
		this.pl=pl;
		String []postInfo = binfo.split("/"); //[0]은 글번호, [1]은 유저아이디
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
		tftitle.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 25));
		
		lid= new JLabel();
		lid.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		ldate = new JLabel();
		ldate.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		lclicked = new JLabel();
		lclicked.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		lrec= new JLabel();
		lrec.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		
		
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
		bInsert = new JButton("작성");
		bInsert.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 20));
		bInsert.addActionListener(new ButtonListener());
		
		bUpdate = new JButton("수정");
		bUpdate.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 20));
		bUpdate.addActionListener(new ButtonListener());
		
		bDelete = new JButton("삭제");
		bDelete.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 20));
		bDelete.addActionListener(new ButtonListener());
		
		bRec = new JButton("추천");
		bRec.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 20));
		bRec.addActionListener(new ButtonListener());
		
		bBack = new JButton("뒤로가기");
		bBack.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 20));
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
	
	//화면에 게시글 정보를 세팅
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
		lid.setText("작성자: "+ uid +"  ");
		ldate.setText("작성일자: "+getDate()+"  ");
		lclicked.setText("조회수: "+String.valueOf(Integer.parseInt(clicked)+1)+"  ");
		lrec.setText("추천수: "+rec);
	}
	//화면에서 정보를 가져옴
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
					JOptionPane.showMessageDialog(PostContents.this, "작성이 완료되었습니다.");					
					bf.change("PostList");
				}else {
					JOptionPane.showMessageDialog(PostContents.this, "업로드를 실패하였습니다.");
				}
			}else if(e.getSource()==bDelete) {
				int x = JOptionPane.showConfirmDialog(PostContents.this, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
				if(x == JOptionPane.OK_OPTION) {
					BoardDatabase bdb = new BoardDatabase();
					Boolean ok = bdb.deletePost(postNum);
					if(ok) {
						JOptionPane.showMessageDialog(PostContents.this, "삭제되었습니다.");
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
					int x = JOptionPane.showConfirmDialog(PostContents.this, "수정하시겠습니까?", "수정", JOptionPane.YES_NO_OPTION);
					if (x == JOptionPane.OK_OPTION) {
						// 1. 화면의 정보를 얻는다.
						BoardDTO dto = getContents();
						// 2. 그정보로 DB를 수정
						BoardDatabase bdb = new BoardDatabase();
						boolean ok = bdb.updatePost(dto);

						if (ok) {
							JOptionPane.showMessageDialog(PostContents.this, "수정되었습니다.");
							bf.change("PostList");
						}
					}
				}
				
			}else if(e.getSource()==bRec) {
				BoardDatabase bdb = new BoardDatabase();
				BoardDTO dto = getContents();
				boolean ok = bdb.checkRec(CurrentUser.getUserID(), dto.getNum()); //false인 경우 이미 추천을 눌렀음
				if(ok) {
					bdb.updateRec(dto); //게시글의 추천수를 증가
					bdb.insertRec(CurrentUser.getUserID(), dto.getNum()); //추천을 했는지 확인
					lrec.setText("추천수: "+String.valueOf(Integer.parseInt(dto.getRec())+1));
				}else {
					JOptionPane.showMessageDialog(PostContents.this, "이미 추천한 게시물입니다.");
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


