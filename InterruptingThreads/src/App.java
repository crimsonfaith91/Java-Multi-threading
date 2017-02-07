import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class App {
	public static void main(String[] args) throws InterruptedException {
		/*
		System.out.println("Starting.");
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				Random rand = new Random();
				
				for(int i = 0; i < 1E8; i++) {
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted!");
						break;
					}
					Math.sin(rand.nextDouble());
				}
			}
		});
		
		t1.start();
		Thread.sleep(500);
		t1.interrupt();
		t1.join();
		
		System.out.println("Finished.");
		*/
		
		System.out.println("Starting.");
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Future<?> future = exec.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				Random rand = new Random();
				
				for(int i = 0; i < 1E8; i++) {
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted!");
						break;
					}
					Math.sin(rand.nextDouble());
				}
				
				return null;
			}
			
		});
		
		exec.shutdown();
		
		Thread.sleep(500);
		
		//future.cancel(false); //interrupt only if the process has NOT started
								//if process has started, do nothing
		//future.cancel(true);
		exec.shutdownNow();
		
		exec.awaitTermination(1, TimeUnit.DAYS);
		
		System.out.println("Finished.");
	}
}
