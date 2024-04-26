package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.School;
import bean.Student;
//import javax.security.auth.Subject;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	private String baseSql = "select * from test";

	public Test get(Student student, Subject subject, School school, int no) throws Exception {

		//結果を格納するtestを初期化
		Test test = new Test();
		//結果を格納するschoolDaoを初期化
		SchoolDao sch = new SchoolDao();
		//結果を格納するstuを初期化
		StudentDao stu = new StudentDao();
		//結果を格納するsubjectを初期化
		Subject sub = new Subject();

		Connection connection = getConnection();

		String condition = "student_no=? and school_cd=? and subject_cd=? and no=?";


		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition);
			//プレースホルダにバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			ResultSet rSet = statement.executeQuery();



			if(rSet.next()){
				test.setStudent(stu.get(rSet.getString("student_no")));
				test.setSchool(sch.get(rSet.getString("school_cd")));
				test.setSubject(sub.get(rSet.getString("subject_cd")));
			} else {
				test = null;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

		//コネクションを閉じる
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
	}
	return test;

	}

	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {


		return null;
	}

}


