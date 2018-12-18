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

	// 연결확인
	public Connection getConn() {
		con = null;

		try {
			Class.forName(driver); // 1. 드라이버 로딩
			con = DriverManager.getConnection(url, id, pw); // 2. 드라이버 연결

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// 아이디와 비밀번호가 일치하는 정보만 가져온다
	public MemberDTO getLoginInfo(String uid, String upw) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

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

	/** 한사람의 회원 정보를 얻는 메소드 */
	public MemberDTO getMemberDTO(String uid) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

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
	//해당 아이디로 가입된 내용이 있는지 확인
	public boolean checkRedundancy(String uid) {

		Boolean ok = false;
		
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

		try {

			con = getConn();
			String sql = "select * from tb_member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);

			rs = ps.executeQuery();

			if (rs.next()) {
				ok=true; //중복이 존재
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// 회원정보 모두 들고오기
	public Vector getMemberList() {

		Vector data = new Vector();

		con = null; // 연결
		pst = null; // 명령
		rs = null; // 결과

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

	// 회원등록
	public boolean insertMember(MemberDTO dto) {

		boolean ok = false;

		con = null; // 연결
		pst = null; // 명령

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
			int r = pst.executeUpdate(); // 실행 -> 저장

			if (r > 0) {
				System.out.println("가입 성공");
				ok = true;
			} else {
				System.out.println("가입 실패");
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

			int r = pst.executeUpdate(); // 실행 -> 수정

			if (r > 0)
				ok = true; // 수정이 성공되면 ok값을 true로 변경

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	// 삭제
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
			int r = ps.executeUpdate(); // 실행 -> 삭제

			if (r > 0)
				ok = true; // 삭제됨;

		} catch (Exception e) {
			System.out.println(e + "-> 오류발생");
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

			// DefaultTableModel에 있는 데이터 지우기
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
