package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Pattern pattern = Pattern.compile("[0-9]+");
		int intpoint = 0;
		List<Test> lists = new ArrayList<>();
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");//入学年度
		String classNum = req.getParameter("f2");//クラス番号
		String subjectCd = req.getParameter("f3");//科目コード
		String Num = req.getParameter("f4");//回数
		List<Test> list = tDao.filter(Integer.parseInt(entYearStr), classNum, subjectDao.get(subjectCd, teacher.getSchool()),Integer.parseInt(Num), teacher.getSchool());

		for (Test test : list) {
			String point =	req.getParameter("point_" + test.getStudent().getNo());
			Matcher matcher = pattern.matcher(point);

			//正規表現に一致するか確認
			if(matcher.find())

				//0以上100以下か確認
				intpoint = Integer.parseInt(point);
				if(intpoint>=0 && intpoint<=100){
					test.setPoint(intpoint);
				}
			else{
				req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			}

			lists.add(test);
		}


		//DBからデータ取得 3


		//ビジネスロジック 4
		//DBへデータ保存 5

		tDao.save(lists);

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}
