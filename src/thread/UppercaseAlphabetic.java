package thread;

public class UppercaseAlphabetic {
	
	// Runnable을 사용해서 thread 구현
	public void print() {
		for(char c='A';c<='Z';c++) {
			System.out.print(c);
		}
	}
}
