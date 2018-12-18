//Panel for listing post
package boardsys;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
public class PostList extends JPanel{
	Vector v;
	Vector cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane pane;
	JPanel pHeader, pTail;
	JButton bInsert;
	BoardFrame bf;
	
	public PostList(BoardFrame boardFrame) {
		this.bf=boardFrame;
		ListUI();
	}
	public void ListUI() {
		setLayout(new BorderLayout());
		JLabel title = new JLabel("�Խ���");
		title.setFont(new Font("1�ƻ������ R", Font.PLAIN, 30));
		pHeader = new JPanel(new FlowLayout(1));
		pHeader.add(title);
		add(pHeader, BorderLayout.NORTH);
		
		//���̺� ����
		cols = getColumn();
		BoardDatabase bdb = new BoardDatabase();
		v = bdb.getPostList();
		model = new DefaultTableModel(v, cols) {
			public boolean isCellEditable(int r, int m) {
				return false;
			}
		};
		jTable = new JTable(model);
		TableColumnModel columnModel = jTable.getColumnModel();
		resizeColumnWidth(jTable);
		jTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// mouseClicked �� ���

				int r = jTable.getSelectedRow();
				String num = (String) jTable.getValueAt(r, 0);
				String uid=(String)jTable.getValueAt(r, 2);
				bf.change(num+"/"+uid);
			}

		});
		pane = new JScrollPane(jTable);
		add(pane);
		//��ư����
		bInsert = new JButton("�ۼ�");
		bInsert.setFont(new Font("1�ƻ������ R", Font.PLAIN, 15));
		bInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bf.change("PostContents");
			}
		});
		pTail = new JPanel(new FlowLayout(2));
		pTail.add(bInsert);
		add(pTail, BorderLayout.SOUTH);

	}
	//���̺� �÷��� ����
	public Vector getColumn() {
		Vector col = new Vector();
		col.add("No");
		col.add("����");
		col.add("�ۼ���");
		col.add("�ۼ���");
		col.add("��ȸ��");
		col.add("��õ��");
	
		return col;
	}// getColumn

	//���̺� �ֽ�ȭ
	public void jTableRefresh() {
		BoardDatabase bdb = new BoardDatabase();
		DefaultTableModel model = new DefaultTableModel(bdb.getPostList(), getColumn()) {
			public boolean isCellEditable(int r, int m) {
				return false;
			}
		};
		jTable.setModel(model);
		resizeColumnWidth(jTable);
	}
	
	public void resizeColumnWidth(JTable jTable) {
		TableColumnModel columnModel = jTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(30);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(30);
		columnModel.getColumn(3).setPreferredWidth(30);
		columnModel.getColumn(4).setPreferredWidth(30);
		columnModel.getColumn(5).setPreferredWidth(30);
	}

}
