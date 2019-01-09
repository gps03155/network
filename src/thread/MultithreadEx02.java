package thread;

public class MultithreadEx02 {

	public static void main(String[] args) {
		Thread thread1 = new DigitTread();
		Thread thread2 = new AlphabeticThread();
		Thread thread3 = new DigitTread();
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		// main thread는 종결
	}

}
