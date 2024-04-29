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

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		String cd = "";//科目コード
		String name = "";//科目名
		Subject subject = new Subject();//科目インスタンス
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		SubjectDao sDao = new SubjectDao();//科目Dao
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");//科目コード
		name = req.getParameter("name");//科目名

		//DBからデータ取得 3
		// リクエストパラメータの科目コードをもとに科目インスタンスを取得
		Subject old = sDao.get(cd, teacher.getSchool());

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐
		if(cd.length() != 3){//科目コードが3文字でない場合
			errors.put("subject_error1","科目コードは3文字で入力してください" );

		}else if(old != null){//科目コードが重複していた場合
			errors.put("subject_error2", "科目コードが重複しています");

		}else{//エラーが無かった場合
			//科目インスタンスに値をセット
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());
			sDao.save(subject);
		}

		//レスポンス値をセット 6
		//JSPへフォワード 7
		//エラーがあったかどうかで手順6~7の内容が分岐

		//科目登録画面にフォワード
		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}

		//登録完了画面にフォワード
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}
