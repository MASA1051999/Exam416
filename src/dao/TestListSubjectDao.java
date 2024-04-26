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
import bean.TestListSubject;

public class TestListSubjectDao {

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from subject";


	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
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
	 * filterメソッド 学校を指定して科目別テスト結果の一覧を取得する
	 *
	 * @return 科目別テスト結果のリスト:List<TestListSubject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String condition = "school_cd=?";

		String order = " order by school_cd asc,subject_cd asc";

		try{

			//プリペアードステートメントにSQL文をセット
			//絞り込みは入学年度、クラス（入学年度に付属）、科目の組み合わせの５パターン必要
			//絞り込み(なし),(入学),(科目),(入学,クラス),(入学,科目),(入学,クラス,科目)
			statement = connection.prepareStatement(baseSql + condition + order);

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());

			//プリペアードステートメントを実行
			//SQL文を実行する
			//結果はリザルトセット型となる
			rSet = statement.executeQuery();
				//Schoolの格納に使用
				SchoolDao schoolDao = new SchoolDao();

				//結果をリストに格納
				//入学年度、クラス、学生番号、氏名、１回目、２回目の点数を格納
				while (rSet.next()) {
					TestListSubject testlistsubject = new TestListSubject();
					subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
					subject.setCd(rSet.getString("subject_co"));
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


	private Connection getConnection() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
			if (old == null) {
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
			if (old == null) {
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

}
