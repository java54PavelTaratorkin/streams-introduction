package telran.streams.students;

import java.util.Arrays;

public record Student(String name, int hours, int[] marks) 
	implements Comparable<Student> {
	
	public double getAverageMarks() {
		return Arrays.stream(marks).average().orElseThrow();
	}

	@Override
	public int compareTo(Student o) {
		int res  = Double.compare(o.getAverageMarks(), getAverageMarks());
		
		return res == 0 ? Integer.compare(o.hours, hours) : res;
	}

}
