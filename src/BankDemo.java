import java.util.*;
/**
 * 需求：储户两个，每个都到银行存100元，存3次。
 * @author DaXing
 *
 */
class Bank {
	private int sum;
//	private Object obj = new Object();
	public synchronized void add(int val) {
//		synchronized(obj) {
			sum += val;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "..." + "sum = " + sum);
//		}
	}
}

class Cus implements Runnable {
	private Bank b = new Bank();
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			b.add(100);
		}
	}
}
public class BankDemo {
	public static void main(String[] args) {
		Cus c = new Cus();
		Thread t1 = new Thread(c);
		Thread t2 = new Thread(c);
		t1.start(); t2.start();
	}
}
