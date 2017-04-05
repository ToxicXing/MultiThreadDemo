package WaitNotify;
/**
 * 从ThreadCommu修改过来的，目的是为了让名字交替更改，使用一个flag + wait + notify来实现这个功能。
 * 
 * 等待唤醒机制
 * 涉及的方法：
 * wait(): 让线程处于冻结状态，被冻结的线程会进到线程池中
 * notify(): 唤醒线程池中的任意一个线程
 * notifyAll(): 唤醒线程池中所有的线程
 * 这些方法都必须定义在同步中。
 * 因为这些方法是用于操作线程的方法，
 * 必须明确操作的是哪个锁上的线程。
 * 
 * why操作线程的方法wait，notify，notifyAll放在了Object类当中？
 * 应为这些方法是monitor的方法，monitor其实就是锁。 monitor这个词wait的官方文档的用词。
 * 锁可以是任意的对象。任意的对象调用的方法一定是定义在Object当中。
 * 官方文档里:monitor = 锁  wait set = 线程池。
 * 
 */

class Resource {
	String name;
	String gender;
	boolean flag;
}

class Input implements Runnable {
	Resource r;
	Input(Resource r) {this.r = r;}
	public void run() {
		int x = 0;
		while (true) {
			synchronized(r) {
				if (r.flag) {
					// r锁里的线程wait，所以是r.wait（）
					// r调用了wait，当前线程就在r的线程池中休眠
					try {
						r.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				if (x == 0) {
					r.name = "Mike";
					r.gender = "male";
				} else {
					r.name = "丽丽";
					r.gender = "女";
				}
				r.flag = true;
				// r锁里的线程唤醒线程池里任意一个线程，所以是r.notify
				// r调用了notify，就是唤醒了锁r的线程池中的任意一个休眠的线程。
				r.notify();
				// x flips between 0 and 1
				x = (x + 1) % 2;
			}
		}
	}
}
class Output implements Runnable {
	Resource r;
	Output(Resource r) {this.r = r;}
	public void run() {
		while(true) {
			String message = "";
			synchronized(r) {
				if(!r.flag) {
					try {
						r.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				message = r.name + "......" + r.gender;
				r.flag = false;
				r.notify();
			}
			System.out.println(message);
		}
	}
}
public class WaitNotifyDemo {
	public static void main(String[] args) {
		// Create a shared resource
		Resource r = new Resource();
		// Create two tasks
		Input in = new Input(r);
		Output out = new Output(r);
		// Create Threads
		Thread t1 = new Thread(in);
		Thread t2 = new Thread(out);
		t1.start(); t2.start();
	}
}
