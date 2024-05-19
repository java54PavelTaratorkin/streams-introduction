package telran.streams.students;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

class ColledgeTests {
	private static final String NAME1 = "Mark";
	private static final String NAME2 = "Lev";
	private static final String NAME3 = "Isaac";
	
	private static final int HOURS1 = 100;
	private static final int HOURS2 = 100;
	private static final int HOURS3 = 150;
	
	private static final int[] MARKS1 = {60, 70, 80};
	private static final int[] MARKS2 = {60, 60, 60};
	
	Student st1 = new Student(NAME1, HOURS1, MARKS1);
	Student st2 = new Student(NAME2, HOURS2, MARKS2);
	Student st3 = new Student(NAME3, HOURS3, MARKS2);
	
	Colledge colledge = new Colledge(new Student[] {st1, st2, st3});

	@Test
	void sortTest() {
		Student[] expected = {st1, st3, st2};
		
		Student[] actual0 = sortStudents0(colledge);
		assertArrayEquals(expected, actual0);
		
		Student[] actual = sortStudents1(colledge);
		assertArrayEquals(expected, actual);
		
		Student[] actual2 = sortStudents2(colledge);
		assertArrayEquals(expected, actual2);
		
		Student[] actual3 = sortStudents3(colledge);
		assertArrayEquals(expected, actual3);
	}
	
	@Test
	void summaryStatisticsHoursTest () {
		IntSummaryStatistics iss = getHoursStatistics(colledge);
		assertEquals(100, iss.getMin());
		assertEquals(150, iss.getMax());
		assertEquals(350, iss.getSum());
	}
	
	@Test 
	void summaryStatisticsMarks() {
		IntSummaryStatistics iss = getMarksStatistics(colledge);
		assertEquals(60, iss.getMin());
		assertEquals(80, iss.getMax());
	}
	
	private static IntSummaryStatistics getMarksStatistics(Colledge coll) {
		//returns summary statistics for marks of all colledge's students		
		return StreamSupport.stream(coll.spliterator(), false)
                .flatMapToInt(student -> Arrays.stream(student.marks()))
                .summaryStatistics();
	}
	
	static private IntSummaryStatistics getHoursStatistics(Colledge col) {
		//returns IntSummaryStatistics of hours for all colledge's students
		return StreamSupport.stream(col.spliterator(), false)
                .collect(Collectors.summarizingInt(Student::hours));
	}
	
	private static Student[] sortStudents0(Colledge col) {
		//consider getting or converting stream from Iterable Colledge
		//returns array of students sorted in descending order of the average marks
		//in the case average marks are equaled there will be compared hours
		//one code line
	    return StreamSupport.stream(col.spliterator(), false)
	            .sorted(Comparator.comparingDouble((Student s) -> Arrays.stream(s.marks()).average().orElse(0)).reversed()
	                    .thenComparing(Comparator.comparingInt(Student::hours).reversed()))
	            .toArray(Student[]::new);
	}

	private static Student[] sortStudents1(Colledge col) {
		//consider getting or converting stream from Iterable Colledge
		//returns array of students sorted in descending order of the average marks
		//in the case average marks are equaled there will be compared hours
		//one code line
		return StreamSupport.stream(col.spliterator(), false)
				.sorted(Comparator.comparingDouble((Student s) -> Arrays.stream(s.marks())
						.average().orElseThrow()
						).thenComparingInt(s -> s.hours()).reversed()).toArray(Student[]::new);
	}
	
	private static Student[] sortStudents2(Colledge col) {
		//consider getting or converting stream from Iterable Colledge
		//returns array of students sorted in descending order of the average marks
		//in the case average marks are equaled there will be compared hours
		//one code line
	    return StreamSupport.stream(col.spliterator(), false).sorted((s1, s2) -> { 
	                  double avg1 = Arrays.stream(s1.marks()).average().orElse(0);
	                  double avg2 = Arrays.stream(s2.marks()).average().orElse(0);
	                  return avg1 == avg2 ? Integer.compare(s2.hours(), s1.hours()) : 
	                	  Double.compare(avg2, avg1);
	              }).toArray(Student[]::new);  
	}
	
	private static Student[] sortStudents3(Colledge col) {
		//consider getting or converting stream from Iterable Colledge
		//returns array of students sorted in descending order of the average marks
		//in the case average marks are equaled there will be compared hours
		//one code line
	    return StreamSupport.stream(col.spliterator(), false).sorted()
	    		.toArray(Student[]::new);
	}

}
