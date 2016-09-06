
public class Client {
	public static void main(String[] args) throws InterruptedException {
		final Processor processor = new Processor();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					//processor.produce(); //Wait and Notify Example 1
					processor.produceList(); //Wait and Notify Example 2
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					//processor.consume(); //Wait and Notify Example 1
					processor.consumeList(); //Wait and Notify Example 2
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
}
