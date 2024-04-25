package bean;

import java.io.Serializable;

public class Subject implements Serializable {

	/**
	 * 科目コード:String
	 */
	private String cd;

	/**
	 * 科目名:String
	 */
	private String name;

	/**
	 * 学校コード:School
	 */
	private School school;

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(bean.School school) {
		this.school = school;
	}
}