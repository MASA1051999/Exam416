package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class TestListAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
		Util util = new Util();//Utilの初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		util.setClassNumSet(req);
		util.setEntyearSet(req);
		util.setSubjects(req);

		//DBからデータを取得
		//入学年度、科目、クラスの一覧取得



		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}

}

