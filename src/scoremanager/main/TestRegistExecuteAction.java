package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		TestDao tDao = new TestDao();// TestDaoを初期化
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		SubjectDao subjectDao = new SubjectDao();//科目Daoを初期化
		List<Test> lists = new ArrayList<>();
		Map<String, String> errors = new HashMap<>();//エラーメッセージ
		System.out.print("a");
		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");//入学年度
		String classNum = req.getParameter("f2");//クラス番号
		String subjectCd = req.getParameter("f3");//科目コード
		String Num = req.getParameter("f4");//回数
		List<Test> list = tDao.filter(Integer.parseInt(entYearStr), classNum, subjectDao.get(subjectCd, teacher.getSchool()),Integer.parseInt(Num), teacher.getSchool());

		for (Test test : list) {
		String point =	req.getParameter("point_" + test.getStudent().getNo());
		test.setPoint(Integer.parseInt(point));
		lists.add(test);
		}


		//DBからデータ取得 3


		//ビジネスロジック 4
		//DBへデータ保存 5
		tDao.save(lists);

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}
