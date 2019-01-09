package thread;

public class AlphabeticThread extends Thread {

	@Override
	public void run() {
		try {
			// main 스레드는 선점이라서 한번 선점하면 계속 실행됨
			for (char c = 'a'; c <= 'z'; c++) {
				// System.out.print("[" + Thread.currentThread().getId() + "]" + c); // thread id
				System.out.print(c);

				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
