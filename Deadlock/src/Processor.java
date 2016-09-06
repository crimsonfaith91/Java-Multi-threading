import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * refer:
 * https://www.youtube.com/watch?v=ghCUBi41bGA
 */

public class Processor {	
	//Deadlock Example
	private Account acc1 = new Account();
	private Account acc2 = new Account();
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	private Random rand = new Random();
	
	private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {		
		while(true) {
			boolean gotFirstLock = false, gotSecondLock = false;
			
			try {
				gotFirstLock = firstLock.tryLock(); //check whether has acquired first lock successfully
				gotSecondLock = secondLock.tryLock();
			}
			finally {
				if(gotFirstLock && gotSecondLock) return; //if both locks are acquired successfully, return
				
				//otherwise, unlock the acquired lock to prevent deadlock
				if(gotFirstLock) firstLock.unlock();
				if(gotSecondLock) secondLock.unlock();
			}
			
			//if failed to acquire the locks, continue the process again
			Thread.sleep(1);
		}
	}
	
	public void firstThread() throws InterruptedException {
		for(int i = 0; i < 100; i++) {
			//changing order of acquiring the locks resulting in deadlock
			acquireLocks(lock1, lock2);		
			
			try {
				Account.transfer(acc1, acc2, rand.nextInt(100));
			}
			finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}
	
	public void secondThread() throws InterruptedException {
		for(int i = 0; i < 100; i++) {
			acquireLocks(lock2, lock1);
			
			try {
				Account.transfer(acc2, acc1, rand.nextInt(100));
			}
			finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}
	
	public void finished() {
		System.out.println("\nAccount 1's Balance: " + acc1.getBalance());
		System.out.println("\nAccount 2's Balance: " + acc2.getBalance());
		System.out.println("\nTotal Balance: " + (acc1.getBalance() + acc2.getBalance()));
	}
}
