package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		Subject subject = new Subject();
		SubjectDao sDao = new SubjectDao();//科目Daoを初期化
		Map<String, String> error = new HashMap<>();// エラーメッセージ
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		//科目コード、科目名を取得
		String code = req.getParameter("code");

		//DBからデータ取得 3
		//変更したい科目インスタンスを取得
		Subject old = sDao.get(code, teacher.getSchool());

		//4～7の処理は条件によって分岐
		//削除する科目インスタンスが存在する場合のみ、削除処理
		if(old != null){
			//ビジネスロジック 4
			//選択された科目を削除
			sDao.delete(old);

			//JSPへフォワード 7
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

		} else {
			//JSPへフォワード 7
			req.getRequestDispatcher("error.jsp").forward(req, res);
		}

	}
}
