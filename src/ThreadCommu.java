import java.util.*;
class Resource {
	String name;
	String gender;
}

class Input implements Runnable {
	Resource r;
	Input(Resource r) {this.r = r;}
	public void run() {
		int x = 0;
		while (true) {
			// 加了同步，问题依旧，会出现Mike 女  丽丽 male的组合。
			synchronized(r) {
				if (x == 0) {
					r.name = "Mike";
					r.gender = "male";
				} else {
					r.name = "丽丽";
					r.gender = "女";
				}
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
				message = r.name + "......" + r.gender;
			}
			System.out.println(message);
			
		}
	}
}
public class ThreadCommu {
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
