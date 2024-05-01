package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.Dao;
import dao.SchoolDao;

public class Util{

	/**
	 * getUserメソッド:リクエストのセッションから、ログインユーザーを取得するメソッド
	 *
	 * @param req
	 * @return teacher:教員インスタンス
	 * @throws Exception
	 */
	public Teacher getUser(HttpServletRequest req) throws Exception {
		//ローカル変数の宣言 1
		Teacher teacher = new Teacher();//教員インスタンスの初期化
		//リクエストパラメータ―の取得 2
		HttpSession session = req.getSession();//セッション
		teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		return teacher;
	}


	/**
	 * setClassNumSetメソッド:クラス番号の一覧を取得し、リクエスト属性に保存するメソッド
	 *
	 * @param req
	 * @return void
	 * @throws Exception
	 */
	public void setClassNumSet(HttpServletRequest req) throws Exception {
		Dao dao = new Dao();//Daoの初期化
		List<String> classNum = new ArrayList<String>();//クラス番号を保存するリスト

		Connection connection = dao.getConnection();

		PreparedStatement statement = null;

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select distinct class_num from student order by class_num asc");

			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				// リザルトセットが存在する場合
				// クラス番号をセット
				classNum.add(rSet.getString("class_num"));
			}

		} catch (Exception e){
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//リクエストパラメータに入学年度のリストを保存
		req.setAttribute("classNum", classNum);
	}


	/**
	 * setEntyearSetメソッド:入学年度の一覧を取得し、リクエスト属性に保存するメソッド
	 *
	 * @param req
	 * @return void
	 * @throws Exception
	 */
	public void setEntyearSet(HttpServletRequest req) throws Exception {
		Dao dao = new Dao();//Daoの初期化
		List<String> ent_year = new ArrayList<String>();//入学年度を保存するリスト

		Connection connection = dao.getConnection();

		PreparedStatement statement = null;

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select distinct ent_year from student order by ent_year asc");

			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				// リザルトセットが存在する場合
				// 入学年度をセット
				ent_year.add(rSet.getString("ent_year"));
			}

		} catch (Exception e){
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//リクエストパラメータに入学年度のリストを保存
		req.setAttribute("ent_year", ent_year);
	}


	/**
	 * setSubjectsメソッド:科目の一覧を取得し、リクエスト属性に保存するメソッド
	 *
	 * @param req
	 * @return void
	 * @throws Exception
	 */
	public void setSubjects(HttpServletRequest req)throws Exception{
		Dao dao = new Dao();//Daoの初期化
		Teacher teacher = getUser(req);//ログインユーザーの取得
		String school_cd = teacher.getSchool().getCd();//学校コードの取得
		List<Subject> subjects = new ArrayList<Subject>();//入学年度を保存するリスト

		SchoolDao sDao = new SchoolDao();//学校Daoの初期化

		Connection connection = dao.getConnection();

		PreparedStatement statement = null;

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd=? order by subject_cd asc");

			// プレースホルダに条件を追加
			statement.setString(1, school_cd);

			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				// リザルトセットが存在する場合
				// 科目インスタンスをリストに追加
				Subject subject = new Subject();
				subject.setCd(rSet.getString("subject_cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(sDao.get(rSet.getString("school_cd")));
				subjects.add(subject);
			}

		} catch (Exception e){
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//リクエストパラメータに入学年度のリストを保存
		req.setAttribute("subjects", subjects);
	}


	/**
	 * setNumSetメソッド:試験回数の一覧を取得し、リクエスト属性に保存するメソッド
	 *
	 * @param req
	 * @return void
	 * @throws Exception
	 */
	public void setNumSet(HttpServletRequest req) throws Exception {
		Dao dao = new Dao();//Daoの初期化
		List<String> numList = new ArrayList<String>();//試験回数を保存するリスト

		Connection connection = dao.getConnection();

		PreparedStatement statement = null;

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select distinct no from test order by no asc");

			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				// リザルトセットが存在する場合
				// 試験回数をリストに追加
				numList.add(rSet.getString("no"));
			}

		} catch (Exception e){
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//リクエストパラメータに入学年度のリストを保存
		req.setAttribute("numList", numList);
	}
}