package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		Subject subject = new Subject();
		SubjectDao sDao = new SubjectDao();//科目Daoを初期化
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		String cd = req.getParameter("cd");

		//DBからデータ取得 3
		subject = sDao.get(cd, teacher.getSchool());//科目コードから、科目インスタンスを取得

		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("subject",subject);

		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	}
}
