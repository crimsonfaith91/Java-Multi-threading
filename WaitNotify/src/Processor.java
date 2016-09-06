import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/*
 * refer:
 * https://www.youtube.com/watch?v=gx_YUORX5vk
 * https://www.youtube.com/watch?v=wm1O8EE0X8k
 */

public class Processor {
	//Wait and Notify Example 1
	/*
	public void produce() throws InterruptedException {
		synchronized(this) {
			System.out.println("Start producer...");
			wait();
			System.out.println("Producer gets back lock.");
		}
	}
	
	public void consume() throws InterruptedException {
		System.out.println("Press a button...");
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);
		
		synchronized(this) {
			scanner.nextLine();
			
			System.out.println("Button pressed!");
			notify();
			System.out.println("Note: notify() does not release lock.");
		}
	}
	*/
	
	//Wait and Notify Example 2
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private int CAPACITY = 20;
	private Object lock = new Object();
	
	public void produceList() throws InterruptedException {
		int cnt = 0;
		
		while(true) {
			synchronized(lock) {
				while(list.size() == CAPACITY) {
					lock.wait();
				}
				
				list.add(cnt++);
				lock.notify();
			}
		}
	}
	
	public void consumeList() throws InterruptedException {
		Random rand = new Random();
		
		while(true) {
			synchronized(lock) {
				while(list.size() == 0) {
					lock.wait();
				}
				
				int val = list.removeFirst();
				System.out.println("List size: " + list.size() + ", value: " + val);
				lock.notify();
			}
			
			Thread.sleep(rand.nextInt(100));
		}
	}
}
