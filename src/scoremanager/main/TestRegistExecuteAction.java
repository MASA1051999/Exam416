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
		int intpoint = 0;
		Util util = new Util();
		List<Test> lists = new ArrayList<>();
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");//入学年度
		String classNum = req.getParameter("f2");//クラス番号
		String subjectCd = req.getParameter("f3");//科目コード
		String Num = req.getParameter("f4");//回数

		//テスト結果一覧の取得
		List<Test> list = tDao.filter(Integer.parseInt(entYearStr), classNum, subjectDao.get(subjectCd, teacher.getSchool()),Integer.parseInt(Num), teacher.getSchool());

		//ビジネスロジック
		for (Test test : list) {
			//点数を文字列型で取得
			String point =	req.getParameter("point_" + test.getStudent().getNo());
			System.out.print(point.equals(""));

			//空文字列でないか確認
			if(point.equals("")){
				//エラーメッセージをセット
				errors.put("null_point", "値を入力してください");
				//属性名はerrors
				req.setAttribute("errors" , errors);

				//リクエストに値をセット
				util.setClassNumSet(req);
				util.setEntyearSet(req);
				util.setSubjects(req);
				util.setNumSet(req);

				//リクエストにテスト結果一覧、科目名、試験回数をセット
				req.setAttribute("test_list", list);
				req.setAttribute("subjectName", subjectDao.get(subjectCd, teacher.getSchool()).getName());
				req.setAttribute("num", Num);

				req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			}

			//入力された点数を数値型に変換
			intpoint = Integer.parseInt(point);
			//0より小さいか、100より大きい場合
			if(intpoint<0 || intpoint>100){
				//エラーメッセージをセット
				errors.put("point", "0～100の範囲で入力してください");
				//属性名はerrors
				req.setAttribute("errors" , errors);

				//リクエストに値をセット
				util.setClassNumSet(req);
				util.setEntyearSet(req);
				util.setSubjects(req);
				util.setNumSet(req);

				//リクエストにテスト結果一覧、科目名、試験回数をセット
				req.setAttribute("test_list", list);
				req.setAttribute("subjectName", subjectDao.get(subjectCd, teacher.getSchool()).getName());
				req.setAttribute("num", Num);

				req.getRequestDispatcher("test_regist.jsp").forward(req, res);

			}
			//入力値に異常がなかった場合
			else{
				//テストインスタンスに入力値をセット
				test.setPoint(intpoint);
				//テスト結果一覧にテストインスタンスを追加
				lists.add(test);
			}
		}

		//DBへデータ保存 5
		tDao.save(lists);

		//追加、更新完了画面へ遷移
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}
