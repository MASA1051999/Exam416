package bean;

import java.io.Serializable;
import java.util.HashMap;
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
	 * 初期値は1、2回目ともに-1
	 */
	private Map<Integer, Integer> points = new HashMap<>();{
        points.put(1, -1);
        points.put(2, -1);
    }



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

	/**
	 * 回数と点数のmapを保存する
	 * @param points
	 */
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	/**
	 * 渡された回数に対応した点数を返す
	 */
	public int getPoint(int key){
	return points.get(key);

	}

	/**
	 * 渡された回数と点数をセットで保存
	 * @param key
	 * @param value
	 */
	public void setPoint(int key,int value){
		points.put(key, value);
	}

	/**
	 * 渡されたキーに対応する値をマップから削除
	 * @param key
	 */
	public void removePoint(int key){
		points.remove(key);
	}

}