package thread;

public class UppercaseAlphabeticRunnableImpl extends UppercaseAlphabetic implements Runnable {

	// thread 객체 생성해서 start() 메소드 못 씀
	@Override
	public void run() {
		print();
	}

}
