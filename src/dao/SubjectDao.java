package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	/**
	 * getメソッド 科目コード、学校コードを指定して科目インスタンスを1件取得する
	 *
	 * @param cd:String 科目コード
	 * @param school:School 学校コード
	 * @return 科目クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Subject get(String cd,School school) throws Exception{
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//結果を格納するsubjectを初期化
		Subject subject = new Subject();

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where subject_cd=? and school_cd=?");
			//プレースホルダに科目コード、学校コードをバインド
			statement.setString(1, cd);
			statement.setString(2, school.getCd());
			ResultSet rSet = statement.executeQuery();

			//Schoolの格納に使用
			SchoolDao schoolDao = new SchoolDao();

			if(rSet.next()){
				subject.setCd(rSet.getString("subject_cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				subject = null;
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
	return subject;

	}


	/**
	 * filterメソッド 学校を指定して科目の一覧を取得する
	 *
	 * @return 科目のリスト:List<Subject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String condition = " where school_cd=?";

		String order = " order by subject_cd asc";

		try{

			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject" + condition + order);

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());

			//プリペアードステートメントを実行
			//SQL文を実行する
			//結果はリザルトセット型となる
			rSet = statement.executeQuery();
				//Schoolの格納に使用
				SchoolDao schoolDao = new SchoolDao();

				//結果をリストに格納
				while (rSet.next()) {
					Subject subject = new Subject();

					subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
					subject.setCd(rSet.getString("subject_cd"));
					subject.setName(rSet.getString("name"));
					list.add(subject);
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
	return list;
	}


	/**
	 * saveメソッド 科目インスタンスをデータベースに保存する データが存在する場合は更新、存在しない場合は登録
	 *
	 * @param subject：Subject
	 *            学生
	 * @return 成功:true, 失敗:false
	 * @throws Exception
	 */
	public boolean save(Subject subject) throws Exception {
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
			if (old == null) {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("insert into subject(school_cd,subject_cd,name) values(?, ?, ?)");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());
			statement.setString(2, subject.getCd());
			statement.setString(3, subject.getName());
			} else {
				statement = connection.prepareStatement("update subject set name=? where school_cd=? and subject_cd=?");
				statement.setString(1, subject.getName());
				statement.setString(2, school.getCd());
				statement.setString(3, subject.getCd());

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

	/**
	 * deleteメソッド 科目インスタンスをデータベースから削除する データが存在しない場合は変更なし
	 *
	 * @param subject：Subject
	 *            学生
	 * @return 成功:true, 失敗:false
	 * @throws Exception
	 */
	public boolean delete(Subject subject) throws Exception {
		//学校コードの取得に使用
		School school = subject.getSchool();
		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		int count =0;

		try{
			Subject old = get(subject.getCd(),subject.getSchool());
			//データが存在するなら削除
			if (old != null) {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("delete from subject where school_cd=? and subject_cd=?");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());
			statement.setString(2, subject.getCd());
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

