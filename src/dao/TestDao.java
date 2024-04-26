package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
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
		SubjectDao sub = new SubjectDao();

		Connection connection = getConnection();

		String condition = "where student_no=? and school_cd=? and subject_cd=? and no=?";


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
				test.setStudent(student);
				test.setSchool(school);
				test.setSubject(subject);
				test.setNo(no);
				test.setPoint(rSet.getInt("point"));

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
	/**
	 * 成績登録と更新
	 * @param rSet
	 * @param school
	 * @return
	 * @throws Exception
	 */
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				Test test = new Test();
				StudentDao  studentDao= new StudentDao();
				SubjectDao  SubjectDao= new SubjectDao();

				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setPoint(rSet.getInt("point"));
				test.setNo(rSet.getInt("no"));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(SubjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}


	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String condition = " and ent_year=? and class_num=? and subject_cd=? and no=? and school_cd=?";

		String order = " order by student_no asc";

		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition  + order );

			//プレースホルダー（？の部分）に値を設定
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			statement.setString(5, school.getCd());

			//プリペアードステートメントを実行
			//SQL文を実行する
			//結果はリザルトセット型となる
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);
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
	return list;
	}

	public boolean save(List<Test> list) throws Exception {
		//学校コードの取得に使用
		School school = subject.getSchool();
		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		int count =0;

		try{
			Subject old = get(subject.getCd(),subject.getSchool());
			//データが存在しないなら追加、存在するなら更新
			if (old != null) {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("insert into subject(school,subject,name) values(?, ?, ?)");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());
			statement.setString(2, subject.getCd());
			statement.setString(3, subject.getName());
			} else {
				statement = connection.prepareStatement("update subject set school_cd=?,subject_cd=?,name=? where school_cd=? and subject_cd=?");
				statement.setString(1, school.getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
				statement.setString(4, school.getCd());
				statement.setString(5, subject.getCd());

			}


		//プリペアードステートメントを実行
		//SQL文を実行する
		//結果は実行した列数
		count = statement.executeUpdate();
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
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}






