package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;


public class TeacherDao extends Dao{
	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from teacher where teacher_id=?";


	/**
	 * getメソッド 教員IDを元に、教員インスタンスを1件取得する
	 *
	 * @param id:String 教員ID
	 * @return 教員クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Teacher get(String id) throws Exception{
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//結果を格納するTeacherを初期化
		Teacher teacher = new Teacher();

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql);

			//プレースホルダー（？の部分）に値を設定し、SQLを実行
			statement.setString(1,id);
			ResultSet rSet = statement.executeQuery();

			//対応するschool型を取得するために使用
			SchoolDao schoolDao = new SchoolDao();

			//取得した教員ID、パスワード、教員名、学校コードをteacherインスタンスに保存
			if(rSet.next()) {
				teacher.setId(rSet.getString("teacher_id"));
				teacher.setPassword("password");
				teacher.setName("name");
				teacher.setSchool(schoolDao.get("school_cd"));
			} else {
				//対応する教員がいない場合はnullを返す
				teacher = null;
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
	//検索した教員インスタンスを返す
	return teacher;
	}

	/**
	 * loginメソッド 教員ID、パスワードを元に、教員インスタンスを1件取得する
	 *
	 * @param id:String 教員ID
	 * @param password:String パスワード
	 * @return 教員クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Teacher login(String id,String password)throws Exception{
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		String condition = " and password=?";

		//結果を格納するTeacherを初期化
		Teacher teacher = new Teacher();

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition);

			//プレースホルダー（？の部分）に値を設定し、SQLを実行
			statement.setString(1,id);
			statement.setString(2, password);
			ResultSet rSet = statement.executeQuery();

			//対応するschool型を取得するために使用
			SchoolDao schoolDao = new SchoolDao();

			//取得した教員ID、パスワード、教員名、学校コードをteacherインスタンスに保存
			if(rSet.next()) {
				teacher.setId(rSet.getString("teacher_id"));
				teacher.setPassword("password");
				teacher.setName("name");
				teacher.setSchool(schoolDao.get("school_cd"));
			} else {
				//対応する教員がいない場合はnullを返す
				teacher = null;
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
	//検索した教員インスタンスを返す
	return teacher;

	}

}