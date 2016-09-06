public class Account {
	private int balance = 10000;
	
	public void deposit(int amt) {
		balance += amt;
	}
	
	public void withdraw(int amt) {
		balance -= amt;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public static void transfer(Account acc1, Account acc2, int amt) {
		acc1.withdraw(amt);
		acc2.deposit(amt);
	}
}
