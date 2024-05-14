package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestDeleteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		Subject subject = new Subject();
		TestDao tDao = new TestDao();//テストDaoを初期化
		StudentDao sDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		Test test = new Test();
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		String studentcd = req.getParameter("studentcd");
		String subjectcd = req.getParameter("subjectcd");
		String num = req.getParameter("num");
	    String point = req.getParameter("point");

		//DBからデータ取得 3
		//test = tDao.get(sDao.get(studentcd), subDao.get(subjectcd, teacher.getSchool()), teacher.getSchool(), Integer.parseInt(num));

		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("studentcd",studentcd);
		req.setAttribute("subjectcd",subjectcd);
		req.setAttribute("num",num);
		req.setAttribute("point",point);

		req.getRequestDispatcher("test_delete.jsp").forward(req, res);
	}
}
