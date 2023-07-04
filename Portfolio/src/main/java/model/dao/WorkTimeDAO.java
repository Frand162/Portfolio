package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import model.entity.WorkTime;

/**
 * @author Yoshiyuki Tonami
 * 画面表示のために出退勤時刻管理データベースと繋ぐDAOクラス。
 */
public class WorkTimeDAO {

	/**
	 * 唯一のインスタンスを生成する
	 */
	private static WorkTimeDAO instance = new WorkTimeDAO(); //唯一のインスタンスとする

	/**
	 * 特定のデータベースとの接続(セッション)。
	 */
	private Connection con;
	/**
	 * 静的SQL文を実行し、作成された結果を返すために使用されるオブジェクト。
	 */
	private Statement st;

	/**
	 * privateのため新規のインスタンスをつくらせない。
	 */
	private WorkTimeDAO() {
	}

	/**
	 * @return ViewListDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static WorkTimeDAO getInstance() {
		return instance;
	}

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 特定のデータベースとの接続(セッション)を生成する。
	 */
	public void dbConnect() throws SQLException {
		ConnectionManager cm = ConnectionManager.getInstance();
		con = cm.connect();
	}

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 静的SQL文を実行し、作成された結果を返すために使用されるオブジェクトを生成する。
	 */
	public void createSt() throws SQLException {
		st = con.createStatement();
	}

	/**
	 * 特定のデータベースとの接続(セッション)を切断する。
	 */
	public void dbDiscon() {
		try {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param employeeCode - 従業員コード。
	 * @return - 出勤情報が既に存在していたら文字列"disable"、存在しなかったらnull。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 出勤情報が既に存在しているかチェックする。
	 */
	public String selectStartTime(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM t_work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			return "disable";
		} else {
			return null;
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @return 退勤情報が既に存在していたら文字列"disble"、存在しなかったらnull。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 退勤情報が既に存在しているかチェックする。
	 */
	public String selectFinishTime(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM t_work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next() && rs.getString(4) != null) {
			return "disable";
		} else {
			return null;
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @return - 休憩開始情報が既に存在していたら文字列"disable"、存在しなかったらnull。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 休憩開始情報が既に存在しているかチェックする。
	 */
	public String selectStartBreak(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM t_work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next() && rs.getString(5) != null) {
			return "disable";
		} else {
			return null;
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @return 休憩終了情報が既に存在していたら文字列"disable"、存在しなかったらnull。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 休憩終了情報が既に存在しているかチェックする。
	 */
	public String selectFinishBreak(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM t_work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next() && rs.getString(6) != null) {
			return "disable";
		} else {
			return null;
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @param thisMonth 月。
	 * @return 出退勤時刻管理用モデルクラスのリスト。
	 * @throws SQLException。 データベース処理に問題があった場合。
	 * 従業員コードと月から勤務記録を抽出する。
	 */
	public List<WorkTime> selectWorkTimeThisMonthList(String employeeCode, String thisMonth) throws SQLException {
	    List<WorkTime> workTimeThisMonthList = new LinkedList<WorkTime>();
	    String sql = "SELECT * FROM t_work_time WHERE employee_code = '" + employeeCode +
	            "' AND DATE_FORMAT(work_date, '%Y-%m') = '" + thisMonth + "';";
	    ResultSet rs = st.executeQuery(sql);

	    while (rs.next()) {
	        WorkTime workTime = new WorkTime();
	        workTime.setWorkDate(LocalDate.parse(rs.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		    if(rs.getString(4) != null) {
		        LocalTime startTime = LocalTime.parse(rs.getString(4));
		        workTime.setStartTime(startTime);
		    }
		    if(rs.getString(5) != null) {
		        LocalTime finishTime = LocalTime.parse(rs.getString(5));
		        workTime.setFinishTime(finishTime);
		    }
		    if(rs.getString(6) != null) {
		        LocalTime breakStartTime = LocalTime.parse(rs.getString(6));
		        workTime.setBreakStartTime(breakStartTime);
		    }
		    if(rs.getString(7) != null) {
		        LocalTime breakFinishTime = LocalTime.parse(rs.getString(7));
		        workTime.setBreakFinishTime(breakFinishTime);
		    }
		    if(rs.getString(6) != null && rs.getString(7) != null) {
		        // 自動計算セットするメソッド
		        workTime.calcBreakTime();
//		     // データベースにbreak_timeを更新
//		        String updateBreakTimeSql = "UPDATE t_work_time SET break_time = '" + workTime.getBreakTime() +
//		                "' WHERE timecard_id = " + rs.getInt(1) + ";";
//		        st.executeUpdate(updateBreakTimeSql);
		    }
		    if(rs.getString(4) != null && rs.getString(5) != null) {
		        // 自動計算セットするメソッド
		        workTime.calcWorkingHours();
//		     // データベースにworking_hoursを更新
//		        String updateWorkingHoursSql = "UPDATE t_work_time SET working_hours = '" + workTime.getWorkingHours() +
//		                "' WHERE timecard_id = " + rs.getInt(1) + ";";
//		        st.executeUpdate(updateWorkingHoursSql);
		    }
		    workTimeThisMonthList.add(workTime);
		}
		return workTimeThisMonthList;
	}
}
