package scoremanager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String url = "";
		//入力された教員のインスタンスを初期化
		Teacher teacher = new Teacher();
		//照合する教員インスタンスの取得に使うDao
		TeacherDao tDao = new TeacherDao();
		//エラーメッセージ
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//リクエストパラメータ―の取得 2
		//確認するid、パスワードを取得
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		//DBからデータ取得 3
		//入力されたidをもとに教員インスタンスを取得 存在しない場合はnull
		teacher = tDao.login(id,password);

		//ビジネスロジック 4
		//ログインできたかどうかで分岐

		//教員idとpasswordが正しい場合(ログインできた場合)
		if(teacher != null){
			// 認証済みフラグを立てる
			teacher.setAuthenticated(true);
			//Sessionを有効にする
			HttpSession session = req.getSession(true);
			//セッションに"user"という変数名で値はteacher変数の中身
			session.setAttribute("user", teacher);

			//リダイレクト
			url = "main/Menu.action";
			res.sendRedirect(url);


		//ログイン出来なかった場合
		}else {
			//エラーメッセージを保存
			errors.put("error", "ログインに失敗しました。IDまたはパスワードが正しくありません。");
			req.setAttribute("errors", errors);

			//入力されたid。passwordをリクエスト属性に保存
			req.setAttribute("id", id);
			req.setAttribute("password", password);

			//JSPへフォワード 7
			req.getRequestDispatcher("login.jsp").forward(req, res);
		}
	}

}
