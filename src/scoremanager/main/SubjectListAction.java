package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
		List<Subject> subjects = null;//科目リスト
		SubjectDao sDao = new SubjectDao();//科目Dao

		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとに科目コード、科目名の一覧を取得
		subjects = sDao.filter(teacher.getSchool());


		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		// リクエストに科目リストをセット
		req.setAttribute("subjects", subjects);
		//JSPへフォワード 7
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}
