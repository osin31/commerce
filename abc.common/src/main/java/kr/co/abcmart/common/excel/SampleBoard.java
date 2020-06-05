package kr.co.abcmart.common.excel;
import lombok.Data;

@Data
public class SampleBoard extends Test {

	private String sample;
	private int age;
	private long money = 10233333333333L;
	private float avg = 1.2f;
	private double doubleAvg = 222222221.0d;

}
