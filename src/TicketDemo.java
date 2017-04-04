//class Ticket implements Runnable{
class Ticket extends Thread {
	private int num = 100;
	// 这个锁，放在run方法里，同步代码块就失效了。因为每个线程启动run方法的时候都会建一个锁，线程之间用的不是一个同步锁。
	Object obj = new Object();
	public void run() {
		while (num > 0) {
			/**
			 * 线程安全问题产生的原因：
			 * 多个线程操作共享数据 + 操作共享数居的代码有多条 --> 当一个线程执行操作共享数据的代码的过程中，
			 * 											  其他线程参与了运算
			 * 解决方法，使用synchronized lock。
			 * 同步锁的好处：解决线程安全问题
			 * 同步锁的坏处：相对降低了执行效率
			 * 同步的前提： 必须有多个线程并使用同一个锁
			 */
			synchronized(obj) {
				if (num > 0) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException ignored) {
						System.out.println("InterruptedException");
					}
					System.out.println(Thread.currentThread().getName() +"...." + num--);
				}
			}
		}
	}
}
public class TicketDemo {
	public static void main(String[] args) {
//		Ticket t1 = new Ticket();
//		Ticket t2 = new Ticket();
//		Ticket t3 = new Ticket();
//		Ticket t4 = new Ticket();

		Ticket t = new Ticket();
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		Thread t3 = new Thread(t);
		Thread t4 = new Thread(t);
		
		t1.start();	 
		t2.start();	t3.start();	t4.start();
	}
}
