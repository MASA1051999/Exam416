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

		String condition = " where student_no=? and subject_cd=? and school_cd=? and no=?";


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

		String condition = " where ent_year=? and student.class_num=? and student.school_cd=?";

		String order = " order by student_no asc";

		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select ent_year, student.class_num, student.no as student_no, coalesce(subject_cd, ?) as subject_cd, coalesce(test.no, ?) as no, coalesce(point, -1) as point"
					//テストの科目コードが 'A01' 行かつ、テスト番号が 1の行を結合対象にする
					+ " from student left outer join test on test.student_no = student.no and test.school_cd = student.school_cd and test.subject_cd = ? and test.no = ?"+ condition  + order );

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, subject.getCd());
			statement.setInt(2, num);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			statement.setInt(5, entYear);
			statement.setString(6, classNum);
			statement.setString(7, school.getCd());

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



		int count =0;
		//値がカウントされたら失敗する

			for (Test test:list){
				//データベースへのコネクションを確立
				Connection connection = getConnection();
				try{
					boolean bool = save(test, connection);
				if(bool != true){
					count++;
				}
				}catch (Exception e) {
					throw e;
				} finally {
					//プリペアードステートメントを閉じる
//					if (statement != null) {
//						try {
//							statement.close();
//						} catch (SQLException sqle) {
//							throw sqle;
//						}
//					}

					//コネクションを閉じる
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
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

					//-1が保存されてない場合
					if(test.getPoint() != -1){
						statement.setInt(5, test.getPoint());
					}
					else{//-1が保存されていた場合
						//点数にnullを保存
						statement.setObject(5, null);
					}

					statement.setString(6, test.getClassNum());
					} else {
						statement = connection.prepareStatement("update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=?");
						//-1が保存されてない場合
						if(test.getPoint() != -1){
							statement.setInt(1, test.getPoint());
						}
						else{//-1が保存されていた場合
							//点数にnullを保存
							statement.setObject(1, null);
						}

						statement.setString(2,test.getStudent().getNo() );
						statement.setString(3, test.getSubject().getCd());
						statement.setString(4, test.getSchool().getCd());
						statement.setInt(5, test.getNo());

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


	public boolean delete(List<Test> list) throws Exception {

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		int count =0;
		//値がカウントされたら失敗する
		try{
			for (Test test:list){
				boolean bool = delete(test, connection);
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



	private boolean delete(Test test, Connection connection) throws Exception {
		//データベースへのコネクションを確立

				//プリペアードステートメント
				PreparedStatement statement = null;

				int count =0;

				try{
					Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
					//データが存在しないなら追加、存在するなら更新
					if (old != null) {
					//プリペアードステートメントにSQL文をセット
					statement = connection.prepareStatement("delete from test where student_no=? and subject_cd=? and school_cd=? and no=?");

					//プレースホルダー（？の部分）に値を設定
					statement.setString(1,test.getStudent().getNo() );
					statement.setString(2, test.getSubject().getCd());
					statement.setString(3, test.getSchool().getCd());
					statement.setInt(4, test.getNo());

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





