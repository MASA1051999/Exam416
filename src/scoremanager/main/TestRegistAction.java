package scoremanager.main;

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

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
		List<Test> test = null;//テストリスト
		int entYear = 0;// 入学年度
		TestDao tDao = new TestDao();// クラス番号Daoを初期化
		SubjectDao sDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		String subjectName = "";


		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");//入学年度
		String classNum = req.getParameter("f2");//クラス番号
		String subjectCd = req.getParameter("f3");//科目コード
		int Num = Integer.parseInt(req.getParameter("f4"));//回数


		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにテストの一覧を取得
		List<Test> list = tDao.filter(entYearStr, classNum, sDao.get(subjectCd, teacher.getSchool()),Num, teacher.getSchool());
		subjectName = sDao.get(subjectCd, teacher.getSchool()).getName();

		//ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		// リクエストにテストリストをセット
		req.setAttribute("test_list", list);
		req.setAttribute("subject_name", subjectName);
		//JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
