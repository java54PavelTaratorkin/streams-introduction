package telran.streams.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class TwoDimensionalArrayTest {
	int[][] array = {
			{10, 5, 6},
			{20, 20, 20},
			{10, 4}
	};
	
	@Test
	void sortTest() {
		int[][] expected = {
				{10, 4},
				{10, 5, 6},
				{20, 20, 20}
		};
		assertArrayEquals(expected, arraysSort(array));
	}
	
	@Test
	void sumTest() {
		assertEquals(95, sumArrays1(array)) ;
		assertEquals(95, sumArrays2(array));
		assertEquals(95, sumArrays3(array));
	}
	
	@Test
	void summaryStatisticsTest() {
		IntSummaryStatistics iss = Arrays.stream(array)
				.flatMapToInt(a -> Arrays.stream(a)).summaryStatistics();
		
		assertEquals(4, iss.getMin());
		assertEquals(20, iss.getMax());
		assertEquals(95, iss.getSum());
	}
	
	private static int sumArrays1(int[][] ar) {
		
		return Arrays.stream(ar).collect(Collectors.summingInt(a -> Arrays.stream(a).sum()));
	}
	
	private static int sumArrays2(int[][] ar) {
			
		return Arrays.stream(ar).mapToInt(a -> Arrays.stream(a).sum()).sum();
	}
	
	private static int sumArrays3(int[][] ar) {
		
		return Arrays.stream(ar).flatMapToInt(a -> Arrays.stream(a)).sum();
	}
	
	private static int[][] arraysSort(int[][] ar) {
		
		return Arrays.stream(ar).sorted((a1, a2) ->
		Integer.compare(Arrays.stream(a1).sum(), Arrays.stream(a2).sum()))
				.toArray(int[][]::new);
	}
	

}
