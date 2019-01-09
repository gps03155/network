package thread;

public class MultithreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new AlphabeticThread();
		Thread thread2 = new DigitTread();
		Thread thread3 = new Thread(new UppercaseAlphabeticRunnableImpl()); // Thread 클래스가 Runnable 객체를 받을 수 있도록 되어 있음
		// Runnable 인터페이스를 구현하여 Thread를 생성함
		// 상속받아서 Thread를 사용하지 못할 때 Runnable 사용 - 다중상속의 경우
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
