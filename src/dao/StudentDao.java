package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao  extends Dao{

	/**
	 * getメソッド 学生番号を指定して学生インスタンスを1件取得する
	 *
	 * @param no:String
	 *            学生番号
	 * @return 学生クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Student get(String no) throws Exception {
		Student student = new Student();
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;


		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where no=?");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, no);
			ResultSet rSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				student = null;
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
	return student;
	}


	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from student where school_cd=? ";

	/**
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @param school:School
	 *            学校
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		//リストを初期化
		List<Student> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				Student student = new Student();

				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * filterメソッド 学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @param entYear:int
	 *            入学年度
	 * @param classNum:String
	 *            クラス番号
	 * @param isAttend:boolean
	 *            在学フラグ
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String condition = " and ent_year=? and class_num=?";

		String order = " order by no asc";

		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}
		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order );

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);

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


	/**
	 * filterメソッド 学校、入学年度、在学フラグを指定して学生の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @param entYear:int
	 *            入学年度
	 * @param isAttend:boolean
	 *            在学フラグ
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();

		//データベースへのコネクションを確率
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;
		String condition = " and ent_year=?";


		String order = " order by no asc";

		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}
		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order );

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);

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

	/**
	 * filterメソッド 学校、在学フラグを指定して学生の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @param isAttend:boolean
	 *            在学フラグ
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();

		//データベースへのコネクションを確率
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;


		String order = " order by no asc";

		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}
		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order );

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());

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


	/**
	 * saveメソッド 学生インスタンスをデータベースに保存する データが存在する場合は更新、存在しない場合は登録
	 *
	 * @param student：Student
	 *            学生
	 * @return 成功:true, 失敗:false
	 * @throws Exception
	 */
	public boolean save(Student student) throws Exception {
		//データベースへのコネクションを確率
				Connection connection = getConnection();

				//プリペアードステートメント
				PreparedStatement statement = null;

				int count =0;

				try{
					Student old = get(student.getNo());
					if (old == null) {
					//プリペアードステートメントにSQL文をセット
					statement = connection.prepareStatement("insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ? ,?, ?, ?)");


					//プレースホルダー（？の部分）に値を設定
					statement.setString(1, student.getNo());
					statement.setString(2, student.getName());
					statement.setInt(3, student.getEntYear());
					statement.setString(4, student.getClassNum());
					statement.setBoolean(5, student.isAttend());
					statement.setString(6, student.getSchool().getCd());
					} else {
						statement = connection.prepareStatement("update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
						statement.setString(1, student.getName());
						statement.setInt(2, student.getEntYear());
						statement.setString(3, student.getClassNum());
						statement.setBoolean(4, student.isAttend());
						statement.setString(5, student.getNo());
					}


					//プリペアードステートメントを実行
					//SQL文を実行する
					//結果はリザルトセット型となる
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
