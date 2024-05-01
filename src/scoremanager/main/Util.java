package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.Dao;

public class Util{

	public Teacher getUser(HttpServletRequest req) throws Exception {
		//ローカル変数の宣言 1
		Teacher teacher = new Teacher();//教員インスタンスの初期化
		//リクエストパラメータ―の取得 2
		HttpSession session = req.getSession();//セッション
		teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		return teacher;
	}



	@SuppressWarnings("null")
	public void setClassNumSet(HttpServletRequest req) throws Exception {
		Dao dao = new Dao();//Daoの初期化
		List<String> ent_year = null;//入学年度を保存するリスト

		Connection connection = dao.getConnection();

		PreparedStatement statement = null;

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select distinct ent_year from student order by ent_year asc");

			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
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

}