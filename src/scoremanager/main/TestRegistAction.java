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
		TestDao tDao = new TestDao();// クラス番号Daoを初期化
		SubjectDao subjectDao = new SubjectDao();//科目Daoを初期化
		Util util = new Util();//utilの初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		String subjectName = "";//科目名を初期化
		int testNum = 0;


		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");//入学年度
		String classNum = req.getParameter("f2");//クラス番号
		String subjectCd = req.getParameter("f3");//科目コード
		String Num = req.getParameter("f4");//回数


		//DBからデータ取得 3
		//検索条件の指定によって、3、6の処理を行うか分岐する
		//クラス番号、入学年度、科目、試験回数を取得し、リクエスト属性に保存
		util.setClassNumSet(req);
		util.setEntyearSet(req);
		util.setSubjects(req);
		util.setNumSet(req);


		//全てに値が入力されていた場合
		if(entYearStr != null && classNum != null && subjectCd != null && Num != null){
			//テスト回数を整数に直す
			testNum = Integer.parseInt(Num);
			//科目名を取得
			subjectName = subjectDao.get(subjectCd, teacher.getSchool()).getName();
			// リクエストパラメータの情報をもとにテストの一覧を取得
			List<Test> list = tDao.filter(Integer.parseInt(entYearStr), classNum, subjectDao.get(subjectCd, teacher.getSchool()),testNum, teacher.getSchool());

			//リクエストにテストリスト、科目名、試験回数をセット
			req.setAttribute("test_list", list);
			req.setAttribute("subjectName", subjectName);
			req.setAttribute("num", Num);

		}//1つでも値が入力されていたとき…入力が不足しているとき
		else if(entYearStr != null && classNum != null && subjectCd != null && Num != null){
			errors.put("test", "入学年度とクラスと科目と回数を選択してください");
		}


		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		//リクエストに値をセット
		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", Num);


		//JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
