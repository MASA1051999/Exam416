package scoremanager;

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
		//
		//入力された教員のインスタンスを初期化
		Teacher teacher = new Teacher();
		//照合する教員インスタンスの取得に使うDao
		TeacherDao tDao = new TeacherDao();

		//リクエストパラメータ―の取得 2
		//確認するid、パスワードを取得
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		//DBからデータ取得 3
		//入力されたidをもとに教員インスタンスを取得 存在しない場合はnull
		teacher = tDao.login(id,password);

		//ビジネスロジック 4
		//教員idとpasswordが正しい場合(教員インスタンスが存在する場合)
		if(teacher != null){
			// 認証済みフラグを立てる
			teacher.setAuthenticated(true);
			//Sessionを有効にする
			HttpSession session = req.getSession(true);
			//セッションに"user"という変数名で値はteacher変数の中身
			session.setAttribute("user", teacher);

		}

		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		//なし
		//JSPへフォワード 7
		//req.getRequestDispatcher("main/Menu.action").forward(req, res);

		//リダイレクト
		url = "main/Menu.action";
		res.sendRedirect(url);
	}

}
