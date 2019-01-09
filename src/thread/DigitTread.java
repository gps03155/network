package thread;

public class DigitTread extends Thread {

	// Runnable 구현은 Thread 클래스를 사용하는 것이 아님
	// start() 함수도 사용할 수 없음
	// Runnable 인터페이스를 구현하고 다르게 실행
	@Override
	public void run() {
		try {
			// thread 돌릴 때 실행할 코드
			for (int i = 0; i <= 9; i++) {
				// System.out.print("[" + getId() + "]" + i); // thread id
				System.out.print(i);

				Thread.sleep(1000); // sink 맞추기 위해서 - 1000 : 1초
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
