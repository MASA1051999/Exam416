package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao {

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		TestListSubject tls = new TestListSubject();

		try {
			while (rSet.next()) {

				tls.setEntYear(rSet.getInt("ent_year"));
				tls.setStudentNo(rSet.getString("no"));
				tls.setStudentName(rSet.getString("name"));
				tls.setClassNum(rSet.getString("class_num"));
				tls.setPoint(rSet.getInt("num"),rSet.getInt("point"));
				list.add(tls);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * filterメソッド 入学年度、クラス、科目名、（学校）を指定して科目別テスト結果の一覧を取得する
	 *test,student,subjectをjoinする。
	 * @return 科目別テスト結果のリスト:List<TestListSubject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		Dao dao = new Dao();
		//データベースへのコネクションを確立
		Connection connection = dao.getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		//リザルトセット
		ResultSet rSet = null;

		//studentとtestをstudent_noでjoinする。
		String baseSql = "select ent_year,student.no,name,test.class_num,test.no as num,point from student join test on student.no =test.student_no";

		//条件指定
		String condition = " where test.school_cd=? and test.class_num=? and subject_cd=? and ent_year=?";

		//学生コードの昇順
		String order = " order by num asc";

		try{

			//プリペアードステートメントにSQL文をセット
			//入学年度、クラス、科目で絞り込み
			statement = connection.prepareStatement(baseSql + condition + order);

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1, school.getCd());
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, entYear);

			//プリペアードステートメントを実行
			//SQL文を実行する
			//結果はリザルトセット型となる
			rSet = statement.executeQuery();

			//結果をリストに格納
			list= postFilter(rSet);

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
}