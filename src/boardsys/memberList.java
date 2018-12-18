package boardsys;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.Calendar;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class memberList extends JFrame {
	Vector v;
	Vector cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane pane;
	JPanel pbtn;
	JButton btnInsert, btnExcel, btnBoard;
	memberList mList;
	
	public memberList() {
		super("회원관리 프로그램  v0.1.1");
		cols = getColumn();
		database db = new database();
		v = db.getMemberList();
		model = new DefaultTableModel(v, cols) {
			public boolean isCellEditable(int r, int m) {
				return false;
			}
		};
		jTable = new JTable(model);
		resizeColumnWidth(jTable);
		pane = new JScrollPane(jTable);
		add(pane);

		pbtn = new JPanel(new FlowLayout());
		btnInsert = new JButton("회원가입");
		pbtn.add(btnInsert);
		btnExcel = new JButton("엑셀로 내보내기");
		pbtn.add(btnExcel);
		btnBoard = new JButton("게시판관리");
		pbtn.add(btnBoard);
		add(pbtn, BorderLayout.NORTH);

		jTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// mouseClicked 만 사용
				if (e.getClickCount() >= 2) {
					int r = jTable.getSelectedRow();
					String id = (String) jTable.getValueAt(r, 0);
					board2 mem = new board2(id, memberList.this); // 아이디를 인자로 수정창 생성
				}

			}

		});
		btnInsert.addActionListener(new ButtonListener());
		btnExcel.addActionListener(new ButtonListener());
		btnBoard.addActionListener(new ButtonListener());
		setSize(1000, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnInsert) {
				new board2(memberList.this);
			}else if(e.getSource()==btnExcel) {
				exportMemberList();
			}else if(e.getSource()==btnBoard) {
				new BoardFrame();
			}
		}
		
	}
	public Vector getColumn() {
		Vector col = new Vector();
		col.add("아이디");
		col.add("비밀번호");
		col.add("이름");
		col.add("전화");
		col.add("주소");
		col.add("생일");
		col.add("직업");
		col.add("성별");
		col.add("이메일");
		col.add("자기소개");

		return col;
	}// getColumn

	public void jTableRefresh() {
		database db = new database();
		DefaultTableModel model = new DefaultTableModel(db.getMemberList(), getColumn()) {
			public boolean isCellEditable(int r, int m) {
				return false;
			}
		};
		jTable.setModel(model);
		resizeColumnWidth(jTable);
	}

	public void resizeColumnWidth(JTable table) { 
		final TableColumnModel columnModel = table.getColumnModel(); 
		for (int column = 0; column < table.getColumnCount(); column++) 
		{ 	int width = 50; // Min width 
			for (int row = 0; row < table.getRowCount(); row++) 
			{ 	
				TableCellRenderer renderer = table.getCellRenderer(row, column); 
				Component comp = table.prepareRenderer(renderer, row, column); 
				width = Math.max(comp.getPreferredSize().width +1 , width);
			} 
			columnModel.getColumn(column).setPreferredWidth(width);
		} 
	}
	
	public static void main(String[] args) {
		new memberList();
	}
	
	//회원리스트를 엑셀로 출력
	public void exportMemberList(){
   
    	database db = new database();
        List<MemberDTO> list = db.getMemberList();
        useJxlWrite(list);
       
    }
   
   
    public void useJxlWrite(List<MemberDTO> list){
        Workbook workbook=null;      
        Workbook xlsWb = new HSSFWorkbook();
        //sheet 생성
        Sheet sheet1 = xlsWb.createSheet("firstSheet");
        Row row = null;
        Cell cell = null;
        //셀스타일 생성
        CellStyle cellStyle = xlsWb.createCellStyle();
        //줄바꿈 가능
        cellStyle.setWrapText(true);
        //첫번째 줄
        row=sheet1.createRow(0);
        //header 설정
        for(int i=0; i<getColumn().size(); i++)
        {
        	cell = row.createCell(i);
        	String value = (String)getColumn().get(i);
        	cell.setCellValue(value);
        }
        database db = new database();
        Vector data = db.getMemberList();
        for(int i=0; i<data.size(); i++) {
        	row=sheet1.createRow(i+1);
        	Vector person = (Vector) data.get(i);
        	for(int j=0; j<person.size(); j++) {
        		cell = row.createCell(j);
        		cell.setCellValue(String.valueOf(person.get(j)));
        	}
        };
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        String strToday = sdf.format(cal.getTime());
        String filename ="memberList_"+strToday+".xls";

        try  (OutputStream fileOut = new FileOutputStream("C:\\Users\\Jaekwan Chun\\Desktop\\"+filename)) {
            xlsWb.write(fileOut);
        } catch (Exception e) {
            System.out.println("예외발생: "+e.getMessage());
        } finally{
            try {
                if(workbook!=null) workbook.close();//닫기 , 처리 후 메모리에서 해제 처리
            } catch (Exception e) {
                System.out.println("예외발생: "+e.getMessage());
            }
        }//catch ----------------
       
    }//writeJxl()----------------

}
