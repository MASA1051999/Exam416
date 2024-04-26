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
		StudentDao sDao = new StudentDao();
		try {
			while (rSet.next()) {
				tls.setEntYear(rSet.getInt("ent_year"));;
				tls.setStudentNo(rSet.getString("no"));
				tls.setStudentName(rSet.getString("name"));

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
	 * filterメソッド 入学年度、クラス、科目名、（学校）を指定して科目別テスト結果の一覧を取得する
	 *test,student,subjectをjoinする。
	 * @return 科目別テスト結果のリスト:List<TestListSubject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		SubjectDao sDao = new SubjectDao();

		//データベースへのコネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet rSet = null;

		//studentとtestをschool_cdでjoinする。
		String baseSql = "select * from student join test on student.no =test.student_no";

		//条件指定
		String condition = "where school_cd=? and class_num=? and subject_no=? and ent_year=? and";

		//学生コードの昇順
		String order = " order by school_cd asc";

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

}