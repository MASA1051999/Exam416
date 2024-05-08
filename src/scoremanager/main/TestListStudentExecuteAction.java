package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.Action;;

public class TestListStudentExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
		StudentDao sDao = new StudentDao();//科目Dao
		TestListStudentDao tDao = new TestListStudentDao();//テストDao初期化
		List<TestListStudent> test = new ArrayList<>();//検索結果を保存
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		Util util = new Util();

		util.setClassNumSet(req);
		util.setEntyearSet(req);
		util.setSubjects(req);
		util.setNumSet(req);

		//リクエストパラメータ―の取得 2
		String studentNum = req.getParameter("f4");
		Student student = new Student();

		//学生番号をもとに、学生インスタンスを取得
		 student = sDao.get(studentNum);


		//入力した学生番号の学生が存在した場合
		//DBからデータ取得 3
		if(student != null){

			// 入学年度、クラス、科目を元に科目別成績一覧を取得
			test = tDao.filter(student);

			//テスト結果一覧、学生インスタンス、学生番号をセット
			req.setAttribute("sTests",test);
			req.setAttribute("student", student);
			req.setAttribute("f4", studentNum);
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);

		}else{
			errors.put("error2", "成績情報が存在しませんでした");
			req.setAttribute("error", errors);
			req.setAttribute("f4", studentNum);

			req.getRequestDispatcher("test_list.jsp").forward(req, res);
		}

	}
}