class ThreadClass extends Thread {
	public void run() {
		synchronized(ThreadClass.class) {
			try {
				System.out.println(Thread.currentThread().getName() + "123");
				ThreadClass.class.wait();
				System.out.println(Thread.currentThread().getName() + "12333333");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class AnotherThreadClass extends Thread {
	public void run() {

			System.out.println(Thread.currentThread().getName() + "123");
			
			System.out.println(Thread.currentThread().getName() + "12333333");

	}
}

public class WaitTest {
	
	public static void main(String[] args) {
		ThreadClass tc = new ThreadClass();
		tc.start();
		ThreadClass tc2 = new ThreadClass();
		tc2.start();
	}
}
