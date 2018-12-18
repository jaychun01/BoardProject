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
		super("ȸ������ ���α׷�  v0.1.1");
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
		btnInsert = new JButton("ȸ������");
		pbtn.add(btnInsert);
		btnExcel = new JButton("������ ��������");
		pbtn.add(btnExcel);
		btnBoard = new JButton("�Խ��ǰ���");
		pbtn.add(btnBoard);
		add(pbtn, BorderLayout.NORTH);

		jTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// mouseClicked �� ���
				if (e.getClickCount() >= 2) {
					int r = jTable.getSelectedRow();
					String id = (String) jTable.getValueAt(r, 0);
					board2 mem = new board2(id, memberList.this); // ���̵� ���ڷ� ����â ����
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
		col.add("���̵�");
		col.add("��й�ȣ");
		col.add("�̸�");
		col.add("��ȭ");
		col.add("�ּ�");
		col.add("����");
		col.add("����");
		col.add("����");
		col.add("�̸���");
		col.add("�ڱ�Ұ�");

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
	
	//ȸ������Ʈ�� ������ ���
	public void exportMemberList(){
   
    	database db = new database();
        List<MemberDTO> list = db.getMemberList();
        useJxlWrite(list);
       
    }
   
   
    public void useJxlWrite(List<MemberDTO> list){
        Workbook workbook=null;      
        Workbook xlsWb = new HSSFWorkbook();
        //sheet ����
        Sheet sheet1 = xlsWb.createSheet("firstSheet");
        Row row = null;
        Cell cell = null;
        //����Ÿ�� ����
        CellStyle cellStyle = xlsWb.createCellStyle();
        //�ٹٲ� ����
        cellStyle.setWrapText(true);
        //ù��° ��
        row=sheet1.createRow(0);
        //header ����
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
            System.out.println("���ܹ߻�: "+e.getMessage());
        } finally{
            try {
                if(workbook!=null) workbook.close();//�ݱ� , ó�� �� �޸𸮿��� ���� ó��
            } catch (Exception e) {
                System.out.println("���ܹ߻�: "+e.getMessage());
            }
        }//catch ----------------
       
    }//writeJxl()----------------

}
