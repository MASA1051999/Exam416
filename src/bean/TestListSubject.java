package bean;

import java.io.Serializable;
import java.util.Map;

public class TestListSubject implements Serializable{

	/**
	 * student
	 */
	private int entYear;


	/**
	 * student
	 */
	private String studentNo;


	/**
	 * student
	 */
	private String studentName;


	/**
	 * student
	 */
	private String classNum;


	/**
	 * test
	 */
	private Map<Integer, Integer> points;



	public TestListSubject() {

	}

	public int getEntYear() {
		return entYear;
	}


	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}


	public String getStudentNo() {
		return studentNo;
	}


	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public String getClassNum() {
		return classNum;
	}


	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}


	public Map<Integer, Integer> getPoints() {
		return points;
	}


	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	public String getPoint(int key){
		return classNum;

	}

	public void putPoint(int key, int value){

	}


}