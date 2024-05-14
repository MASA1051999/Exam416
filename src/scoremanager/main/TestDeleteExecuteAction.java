package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestDeleteExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		Subject subject = new Subject();
		SubjectDao subDao = new SubjectDao();//科目Daoを初期化
		TestDao tDao = new TestDao();
		StudentDao stuDao = new StudentDao();
		Student student = new Student();
		List<Test> list = new ArrayList<Test>();
		Map<String, String> error = new HashMap<>();// エラーメッセージ
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		//科目コード、科目名を取得
		String studentcd = req.getParameter("studentcd");
		String subjectcd = req.getParameter("subjectcd");
		String num = req.getParameter("num");
		//DBからデータ取得 3
		//変更したい科目インスタンスを取得
		Test old = tDao.get(stuDao.get(studentcd), subDao.get(subjectcd, teacher.getSchool()),teacher.getSchool(), Integer.parseInt(num));

		//4～7の処理は条件によって分岐
		//削除する科目インスタンスが存在する場合のみ、削除処理
		if(old != null){
			//ビジネスロジック 4
			//選択された科目を削除
			list.add(old);
			tDao.delete(list);

			//JSPへフォワード 7
			req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);

		} else {
			//JSPへフォワード 7
			req.getRequestDispatcher("error.jsp").forward(req, res);
		}

	}
}
