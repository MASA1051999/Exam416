package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;;

public class TestListSubjectExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
		SubjectDao sDao = new SubjectDao();//科目Dao
		TestListSubjectDao tDao = new TestListSubjectDao();//テストDao初期化
		List<TestListSubject> test = new ArrayList<>();
		Map<String, String> error = new HashMap<>();// エラーメッセージ
		Util util = new Util();

		String entyear = "";
		String classnum = "";
		Subject subject = new Subject();

		//リクエストパラメータ―の取得 2
		//入学年度、クラス、科目の取得
		entyear = req.getParameter("f1");
		classnum = req.getParameter("f2");
		subject = sDao.get(req.getParameter("f3"), teacher.getSchool());

		util.setClassNumSet(req);
		util.setEntyearSet(req);
		util.setSubjects(req);

		//全てに値が入力されていた場合
		//DBからデータ取得 3
		if(!entyear.equals("0") && !classnum.equals("0") && subject != null){

			// 入学年度、クラス、科目を元に科目別成績一覧を表示
			test = tDao.filter(Integer.parseInt(entyear), classnum, subject,teacher.getSchool());

			req.setAttribute("subjectTests",test);
			req.setAttribute("subjectName", subject.getName());

			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);

		}else{
			error.put("error", "入学年度とクラスと科目を選択してください");
			req.setAttribute("error", error);

			req.getRequestDispatcher("test_list.jsp").forward(req, res);
		}




		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		// リクエストに科目リストをセット
		//JSPへフォワード 7

	}
}