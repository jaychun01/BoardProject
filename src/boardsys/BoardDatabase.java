//Database for board
package boardsys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class BoardDatabase {
	private static final String url = "jdbc:mysql://localhost:3306/bdsys?serverTimezone = UTC";
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String id = "guest";
	private static final String pw = "00001111";
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// ����Ȯ��
	public Connection getConn() {
		con = null;

		try {
			Class.forName(driver); // 1. ����̹� �ε�
			con = DriverManager.getConnection(url, id, pw); // 2. ����̹� ����

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// �Խñ۸���Ʈ ������
	public Vector getPostList() {

		Vector data = new Vector();

		con = null; // ����
		pst = null; // ���
		rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from tb_board order by num desc";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				String num = rs.getString("num");
				String title = rs.getString("title");
				String id = rs.getString("id");
				String ymd = rs.getString("ymd");
				String clicked = rs.getString("clicked");
				String rec = rs.getString("rec");
				Vector row = new Vector();
				row.add(num);
				row.add(title);
				row.add(id);
				row.add(ymd);
				row.add(clicked);
				row.add(rec);

				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList()

	// �Ѱ��� �Խñ� ������ ������
	public BoardDTO getPostDTO(String num) {

		BoardDTO dto = new BoardDTO();

		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from tb_board where num = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, num);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setId(rs.getString("id"));
				dto.setClicked(rs.getString("clicked"));
				dto.setDate(rs.getString("ymd"));
				dto.setRec(rs.getString("rec"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	// �Խñ۵��
	public boolean insertPost(BoardDTO dto) {

		boolean ok = false;

		con = null; // ����
		pst = null; // ���

		try {

			con = getConn();
			String sql = "insert into tb_board(title,contents,id,ymd,clicked,rec) " + "values(?,?,?,?,?,?)";

			pst = con.prepareStatement(sql);
			pst.setString(1, dto.getTitle());
			pst.setString(2, dto.getContents());
			pst.setString(3, dto.getId());
			pst.setString(4, dto.getDate());
			pst.setString(5, "0");
			pst.setString(6, "0");
			int r = pst.executeUpdate(); // ���� -> ����

			if (r > 0) {
				ok = true;
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}// insertMmeber

	// �Խù� ����
	public boolean updatePost(BoardDTO pInfo) {

		boolean ok = false;
		con = null;
		pst = null;
		try {

			con = getConn();
			String sql = "update tb_board set title=?, contents=?" + "where num=?";

			pst = con.prepareStatement(sql);

			pst.setString(1, pInfo.getTitle());
			pst.setString(2, pInfo.getContents());
			pst.setString(3, pInfo.getNum());

			int r = pst.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������ �����Ǹ� ok���� true�� ����

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// ��ȸ�� ������Ʈ
	public boolean updateClicked(BoardDTO pInfo) {
		String clicked = pInfo.getClicked();
		int num = Integer.parseInt(clicked) + 1;
		clicked = String.valueOf(num);
		boolean ok = false;
		con = null;
		pst = null;
		try {

			con = getConn();
			String sql = "update tb_board set clicked=?" + "where num=?";

			pst = con.prepareStatement(sql);

			pst.setString(1, clicked);
			pst.setString(2, pInfo.getNum());

			int r = pst.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������ �����Ǹ� ok���� true�� ����

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// ��õ�� ������Ʈ
	public boolean updateRec(BoardDTO pInfo) {
		String rec = pInfo.getRec();
		int num = Integer.parseInt(rec) + 1;
		rec = String.valueOf(num);
		boolean ok = false;
		con = null;
		pst = null;
		try {

			con = getConn();
			String sql = "update tb_board set rec=?" + "where num=?";

			pst = con.prepareStatement(sql);

			pst.setString(1, rec);
			pst.setString(2, pInfo.getNum());

			int r = pst.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������ �����Ǹ� ok���� true�� ����

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// ����
	public boolean deletePost(String num) {

		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from tb_board where num=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, num);
			int r = ps.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������;

		} catch (Exception e) {
			System.out.println(e + "-> �����߻�");
		}
		return ok;
	}

	// ������ ��õ�� �Խñ��� �����Ͽ� ����
	public void insertRec(String id, String num) {
		con = null; // ����
		pst = null; // ���

		try {

			con = getConn();
			String sql = "insert into tb_reccheck(id, num, recCheck) " + "values(?,?,?)";

			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, num);
			pst.setInt(3, 1);
			
			int r = pst.executeUpdate(); // ���� -> ����

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	//�ش� �Խñ��� ��õ�� Ŭ���ߴ��� Ȯ���ϴ� �޼ҵ�
	public boolean checkRec(String id, String num) {
		boolean ok = true;
		con = null; // ����
		pst = null; // ���

		try {

			con = getConn();
			String sql = "select recCheck from tb_recCheck where id=? and num=?";

			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, num);
			
			rs= pst.executeQuery(); // ���� -> ����
			int i=0;
			if(rs.next()) {
				i=rs.getInt("recCheck");
			}
			if (i == 1) {
				ok = false;
			} 
						

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}	
}
