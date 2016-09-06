import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * refer:
 * https://www.youtube.com/watch?v=fjMTaVykOpc
 */

public class Processor {	
	//Reentrant Lock Example
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	private int cnt = 0;
	
	private void increment() {
		for(int i = 0; i < 5000; i++) {
			cnt++;
		}
	}
	
	public void produce() throws InterruptedException {
		lock.lock();
		
		System.out.println("Waiting...");
		
		cond.await(); //relinquish reentrant lock and wait for a button to be pressed at consumer
		
		System.out.println("Producer gets back lock.");
		
		try {
			increment();
		}
		finally { //handle cases when an exception is thrown when running increment()
			lock.unlock();
		}
	}
	
	public void consume() throws InterruptedException {
		Thread.sleep(100); //ensure consumer thread runs after producer thread
		lock.lock();
		
		System.out.println("Press a button...");
		new Scanner(System.in).nextLine();
		System.out.println("Pressed button.");
		
		cond.signal(); //signal the waiting producer thread		
		
		try {
			increment();
		}
		finally {
			lock.unlock();
		}
	}
	
	public void finished() {
		System.out.println("\ncount: " + cnt);
	}
}
