//Database for member
package boardsys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class database {
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

	// ���̵�� ��й�ȣ�� ��ġ�ϴ� ������ �����´�
	public MemberDTO getLoginInfo(String uid, String upw) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from tb_member where id=? and Pwd =?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setString(2, upw);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("Pwd"));
				dto.setName(rs.getString("Name"));
				dto.setTel(rs.getString("tel"));
				dto.setAddr(rs.getString("addr"));
				dto.setBirth(rs.getString("birth"));
				dto.setJob(rs.getString("job"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				dto.setIntro(rs.getString("intro"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	/** �ѻ���� ȸ�� ������ ��� �޼ҵ� */
	public MemberDTO getMemberDTO(String uid) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from tb_member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("Pwd"));
				dto.setName(rs.getString("Name"));
				dto.setTel(rs.getString("tel"));
				dto.setAddr(rs.getString("addr"));
				dto.setBirth(rs.getString("birth"));
				dto.setJob(rs.getString("job"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				dto.setIntro(rs.getString("intro"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
	//�ش� ���̵�� ���Ե� ������ �ִ��� Ȯ��
	public boolean checkRedundancy(String uid) {

		Boolean ok = false;
		
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from tb_member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);

			rs = ps.executeQuery();

			if (rs.next()) {
				ok=true; //�ߺ��� ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// ȸ������ ��� ������
	public Vector getMemberList() {

		Vector data = new Vector();

		con = null; // ����
		pst = null; // ���
		rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from tb_member order by name asc";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				String uid = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String birth = rs.getString("birth");
				String gender = rs.getString("gender");
				String job = rs.getString("job");
				String email = rs.getString("email");
				String intro = rs.getString("intro");

				Vector row = new Vector();
				row.add(uid);
				row.add(pwd);
				row.add(name);
				row.add(tel);
				row.add(addr);
				row.add(birth);
				row.add(job);
				row.add(gender);
				row.add(email);
				row.add(intro);

				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList()

	// ȸ�����
	public boolean insertMember(MemberDTO dto) {

		boolean ok = false;

		con = null; // ����
		pst = null; // ���

		try {

			con = getConn();
			String sql = "insert into tb_member(" + "id,pwd,name,tel,addr,birth," + "job,gender,email,intro) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";

			pst = con.prepareStatement(sql);
			pst.setString(1, dto.getId());
			pst.setString(2, dto.getPwd());
			pst.setString(3, dto.getName());
			pst.setString(4, dto.getTel());
			pst.setString(5, dto.getAddr());
			pst.setString(6, dto.getBirth());
			pst.setString(7, dto.getJob());
			pst.setString(8, dto.getGender());
			pst.setString(9, dto.getEmail());
			pst.setString(10, dto.getIntro());
			int r = pst.executeUpdate(); // ���� -> ����

			if (r > 0) {
				System.out.println("���� ����");
				ok = true;
			} else {
				System.out.println("���� ����");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}// insertMmeber

	// update
	public boolean updateMember(MemberDTO vMem) {

		boolean ok = false;
		con = null;
		pst = null;
		try {

			con = getConn();
			String sql = "update tb_member set name=?, tel=?, addr=?, birth=?, job=?, gender=?" + ", email=?,intro=? "
					+ "where id=? and pwd=?";

			pst = con.prepareStatement(sql);

			pst.setString(1, vMem.getName());
			pst.setString(2, vMem.getTel());
			pst.setString(3, vMem.getAddr());
			pst.setString(4, vMem.getBirth());
			pst.setString(5, vMem.getJob());
			pst.setString(6, vMem.getGender());
			pst.setString(7, vMem.getEmail());
			pst.setString(8, vMem.getIntro());
			pst.setString(9, vMem.getId());
			pst.setString(10, vMem.getPwd());

			int r = pst.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������ �����Ǹ� ok���� true�� ����

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// ����
	public boolean deleteMember(String id, String pwd) {

		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from tb_member where id=? and pwd=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd);
			int r = ps.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������;

		} catch (Exception e) {
			System.out.println(e + "-> �����߻�");
		}
		return ok;
	}

	public void userSelectAll(DefaultTableModel model) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from tb_member order by name asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			// DefaultTableModel�� �ִ� ������ �����
			for (int i = 0; i < model.getRowCount();) {
				model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10) };

				model.addRow(data);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
