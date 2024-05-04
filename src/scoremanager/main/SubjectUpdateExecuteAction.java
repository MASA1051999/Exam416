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

public class SubjectUpdateExecuteAction extends Action {
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
		String name = req.getParameter("name");

		//DBからデータ取得 3
		//変更したい科目インスタンスを取得
		Subject old = sDao.get(code, teacher.getSchool());


		//4～7の処理は条件によって分岐
		//変更する科目インスタンスが存在する場合のみ、変更処理
		if(old != null){
			//ビジネスロジック 4
			//パラメーターの値を、科目インスタンスにセット
			subject.setCd(code);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			//DBへデータ保存 5
			//作成した科目インスタンスを保存
			sDao.save(subject);

			//JSPへフォワード 7
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

		} else {
			//エラーメッセージをセット
			error.put("error", "科目が存在していません");

			//レスポンス値をセット 6
			req.setAttribute("error", error);

			//JSPへフォワード 7
			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
		}

	}
}
