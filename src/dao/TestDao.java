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

		Connection connection = getConnection();

		String condition = " where student_no=? and school_cd=? and subject_cd=? and no=?";


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
				SubjectDao  subjectDao= new SubjectDao();

				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setPoint(rSet.getInt("point"));
				test.setNo(rSet.getInt("no"));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
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

		String condition = " where ent_year=? and student.class_num=? and subject_cd=? and test.no=? and student.school_cd=?";

		String order = " order by student_no asc";

		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + " join student on test.student_no = student.no"+ condition  + order );

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

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		int count =0;
		//値がカウントされたら失敗する
		try{
			for (Test test:list){
				boolean bool = save(test, connection);
				if(bool != true){
					count++;
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}

			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}


	private boolean save(Test test, Connection connection) throws Exception {
		//データベースへのコネクションを確立

				//プリペアードステートメント
				PreparedStatement statement = null;

				int count =0;

				try{
					Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
					//データが存在しないなら追加、存在するなら更新
					if (old == null) {
					//プリペアードステートメントにSQL文をセット
					statement = connection.prepareStatement("insert into test(student_no,subject_cd,school_cd,no,point,class_num) values(?, ?, ?, ?, ?, ?)");

					//プレースホルダー（？の部分）に値を設定
					statement.setString(1,test.getStudent().getNo() );
					statement.setString(2, test.getSubject().getCd());
					statement.setString(3, test.getSchool().getCd());
					statement.setInt(4, test.getNo());
					statement.setInt(5, test.getPoint());
					statement.setString(6, test.getClassNum());
					} else {
						statement = connection.prepareStatement("update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=?");
						statement.setString(1,test.getStudent().getNo() );
						statement.setString(2, test.getSubject().getCd());
						statement.setString(3, test.getSchool().getCd());
						statement.setInt(4, test.getNo());
						statement.setInt(5, test.getPoint());
						statement.setString(6, test.getClassNum());

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
//	public boolean delete(List<Test> list) throws Exception {
//
//		//データベースへのコネクションを確立
//		Connection connection = getConnection();
//
//		//プリペアードステートメント
//		PreparedStatement statement = null;
//
//		int count =0;
//
//		try{
//			Test old = get(list.getClass());
//			//データが存在するなら削除
//			if (old != null) {
//			//プリペアードステートメントにSQL文をセット
//			statement = connection.prepareStatement("delete from subject where school_cd=? and subject_cd=?");
//
//			//プレースホルダー（？の部分）に値を設定
//			statement.setString(1, school.getCd());
//			statement.setString(2, subject.getCd());
//			}
//
//		//プリペアードステートメントを実行
//		//SQL文を実行する
//		//結果は実行した列数
//		count = statement.executeUpdate();
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			//プリペアードステートメントを閉じる
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//
//			//コネクションを閉じる
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//		}
//		if (count > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//}
//



