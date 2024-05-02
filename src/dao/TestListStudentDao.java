package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao {

	/**
	 * filterメソッド:学生インスタンスを元に、テスト結果の一覧を取得
	 * @param student
	 * @return List<TestListStudent>
	 * @throws Exception
	 */
	public List<TestListStudent>filter(Student student) throws Exception{
		List<TestListStudent> list = new ArrayList<>();
		//(何故か)daoの初期化
		Dao dao = new Dao();

		//データベースへのコネクションを確立
		Connection connection = dao.getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		//リザルトセット
		ResultSet rSet = null;

		//testとsubjectをsubject_cdで結合
		String baseSql = "select * from subject join test on subject.subject_cd =test.subject_cd";

		String condition = " where student_no=?";

		String order = " order by subject_cd asc,test.no asc";

		try{

			//プリペアードステートメントにSQL文をセット
			//学生番号をもとに、テスト結果を絞り込み
			statement = connection.prepareStatement(baseSql + condition + order);

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, student.getNo());

			//プリペアードステートメントを実行
			//SQL文を実行する
			//結果はリザルトセット型となる
			rSet = statement.executeQuery();

			//結果をリストに格納
			list.addAll(postFilter(rSet));

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
	 * postFilterメソッド:検索結果をリストに詰めて返還するメソッド
	 * @param rSet
	 * @return List<TestListStudent>
	 */
	private List<TestListStudent> postFilter(ResultSet rSet){
		//リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		TestListStudent tls = new TestListStudent();

		try {
			while (rSet.next()) {//検索結果が存在するなら、リストに追加
				tls.setSubjectName(rSet.getString("name"));
				//どのsubject_cdか指定しないといけないかも
				tls.setSubjectCd(rSet.getString("subject_cd"));
				tls.setNum(rSet.getInt("no"));
				tls.setPoint(rSet.getInt("point"));

				list.add(tls);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
}