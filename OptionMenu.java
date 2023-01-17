import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class OptionMenu {
	ArrayList<Account> accounts = new ArrayList<>();
	Scanner sc = new Scanner(System.in);
	public void mainMenu(){
		// The home page of the system
		while (true) {
			System.out.println("==================ATM System==================");
			System.out.println("Type 1 - Login");
			System.out.println("Type 2 - Create Account");

			System.out.println("Choice: ");
			int command = sc.nextInt();  // command 命令
			switch (command) {
				case 1:
					// login
					login(accounts, sc);
					break;
				case 2:
					// creat account
					reqister(accounts, sc);
					break;
				default:
					System.out.println("Invalid Choice.");
			}
		}
	}

	/**
	 *  login
	 * @param accounts
	 * @param sc
	 */
	private static void login(ArrayList<Account> accounts, Scanner sc) {
		System.out.println("=====================login=====================");
		//1.First judge whether there is an account object in the collection,
		// if it exists, scan it, if it does not exist, return does not exist
		if (accounts.size() == 0) {
			System.out.println("Sorry, there is no account in the current system," +
					" please create an account first, and then log in");
			return;
		}

		//2.Officially enter the login operation
		System.out.println("Please enter the login card number");
		String cardId = sc.next();
		//3.Judging whether the card number exists
		Account acc = getAccountByCardId(cardId,accounts);
		if (acc != null) {
			while (true) {
				System.out.println("Please enter your login password");
				String passWord = sc.next();
				//Judging whether the current account object password is consistent with the password entered by the user
				if (acc.getPassWord().equals(passWord)) {
					//login successful
					System.out.println("congratulations ‘"+acc.getUserName()+"’ Sir/Madam you have entered the system," +
							" your card number is" + acc.getCardId());
					//Display the operation page after login (functions)
					showUserCommand(sc,acc,accounts);
					return;
				} else {
					System.out.println("Sorry, the password you entered is wrong, please re-enter");
				}
			}
		} else {
			System.out.println("Sorry, the account card number is wrong! !");
		}
	}

	/**
	 * Display the operation page after login
	 */
	private static void showUserCommand(Scanner sc,Account acc,ArrayList<Account> accounts) {
		while (true) {
			System.out.println("=====================User operation page=====================");
			System.out.println(" Type 1 - Checking account");
			System.out.println(" Type 2 - Saving money");
			System.out.println("Type 3 - Withdraw money");
			System.out.println("Type 4 - Transfer");
			System.out.println("Type 5 - Modify password");
			System.out.println("Type 6 - Exit");
			System.out.println("Type 7 - Delete the current account (please proceed with caution!!)");
			System.out.println("Choice: ");
			int command = sc.nextInt();
			switch (command) {
				case 1:
					//Checking account
					showAccount(acc);
					break;
				case 2:
					//
					depositMoney(acc,sc);
					break;
				case 3:
					//
					withdrawMoney(acc, sc);
					break;
				case 4:
					//
					transferMoney(sc, acc, accounts);
					break;
				case 5:
					//
					modifyPassWord(sc, acc);
					return;
				case 6:
					//Exit
					System.out.println("Log out of the account successfully, welcome to visit next time!");
					return; //
				case 7:
					if (deleteAccount(acc, sc, accounts)) {
						return;
					}
					break;
				default:
					System.out.println("Invalid, please reselect your choice:");
			}
		}
	}

	/**
	 * delete
	 * @param acc
	 * @param sc
	 * @param accounts
	 */
	private static boolean deleteAccount(Account acc, Scanner sc, ArrayList<Account> accounts) {
		System.out.println("==================User delete account===================");
		//Delete current account
		//Delete the current account object from the current account collection
		System.out.println("Do you really want to delete the current account? y/n ");
		String rs = sc.next();
		switch (rs) {
			case "y":
				if (acc.getMoney() > 0) {
					System.out.println("There is still cash in your account, you cannot delete the account! ! !");
				} else {
					accounts.remove(acc);
					System.out.println("Your account has been deleted");
					return true;
				}
				break;
			default:
				System.out.println("OK, the current account will continue to be kept!");
		}
		return false;
	}

	/**
	 *  modifyPassword
	 * @param sc
	 * @param acc
	 */
	private static void modifyPassWord(Scanner sc, Account acc) {
		System.out.println("==============User modify password page==============");
		while (true) {
			System.out.println("Please enter your current password");
			String passWord = sc.next();
			if (acc.getPassWord().equals(passWord)) {
				while (true) {
					//The authentication is successful, enter the password reset
					System.out.println("Please enter your new password: ");
					String newPassWord = sc.next();
					System.out.println("Please confirm your new password:");
					String okPassWord = sc.next();
					if (newPassWord.equals(okPassWord)) {
						acc.setPassWord(newPassWord);
						System.out.println("Password reset complete! Please log in again~");
						return;
					} else {
						System.out.println("Your two passwords are inconsistent, please re-enter:");
					}
				}
			} else {
				System.out.println("The current password you entered is incorrect~~");
			}
		}
	}


	/**
	 * TransferMoney
	 * @param sc
	 * @param acc
	 * @param accounts
	 */
	private static void transferMoney(Scanner sc, Account acc, ArrayList<Account> accounts) {
		System.out.println("\"=====================Transfer Money page=====================\"");
		//Judging whether there are more than two accounts
		if (accounts.size() < 2) {
			System.out.println("There are less than two accounts in the current system, " +
					"please create an account first!");
			return;
		}
		//Judging whether the current account is eligible for transfer
		if (acc.getMoney() == 0) {
			System.out.println("The current account balance is insufficient and cannot be transferred!");
			return;
		}

		while (true) {
			//start transfer
			System.out.println("Please enter the card number of the other account:");
			String cardId = sc.next();

			//This card number cannot be your own card number
			if (cardId.equals(acc.getCardId())) {
				System.out.println("Sorry, you cannot transfer money to yourself! ! !");
				continue;
			}
			//Judging whether the card number exists; query the other party's account according to the card number
			Account account = getAccountByCardId(cardId, accounts);
			if (account == null) {
				System.out.println("Sorry, this card number does not exist");
			} else {
				//This account object exists, continue to verify the last name
				String userName = account.getUserName();
				String tip = "*" + userName.substring(1);
				System.out.println("Please enter[ "+tip+" ]last name：");
				String preName = sc.next();

				//Is the authentication correct?
				if (userName.startsWith(preName)) {
					while (true) {
						//After the authentication is passed, start the transfer
						System.out.println("Please enter the amount to transfer:");
						double money = sc.nextDouble();
						//Judging whether the balance is sufficient
						if (money > acc.getMoney()) {
							System.out.println("Sorry, your balance is insufficient. The maximum balance you can transfer is:" + acc.getMoney());
						} else {
							//
							acc.setMoney(acc.getMoney() - money);
							account.setMoney(account.getMoney() + money);
							System.out.println("The transfer is successful, your account balance is:" + acc.getMoney());
							return;
						}
					}
				} else {
					System.out.println("Sorry, the information you entered is wrong~~~");
				}
			}
		}
	}


	/**
	 *  withdrawMoney
	 * @param acc   account
	 * @param sc    withdrawal amount
	 */
	private static void withdrawMoney(Account acc, Scanner sc) {
		System.out.println("=====================withdraw money page=====================");
		if (acc.getMoney() < 10) {
			System.out.println("Sorry, the current account balance is less than 10 $, cannot withdraw");
			return;
		}
		while (true) {
			//The user enters the withdrawal amount:
			System.out.println("Please enter the amount of money withdrawn:");
			double money = sc.nextDouble();
			if (money > acc.getQuotaMoney()) {
				System.out.println("Sorry, the amount you withdraw exceeds the limit each time, " +
						"and the maximum withdrawal per time is:" + acc.getQuotaMoney());
			} else {
				//Judging whether the current balance is exceeded
				if (acc.getMoney() < money) {
					System.out.println("Insufficient balance, the remaining balance of your current account is:" + acc.getMoney());
				} else {

					System.out.println("withdraw: "+money+" success!");
					//update balance更新余额
					acc.setMoney(acc.getMoney() - money);
					//end of withdrawal
					showAccount(acc);
					return;
				}
			}
		}
	}

	/**
	 *  depositMoney
	 * @param acc
	 * @param sc
	 */
	private static void depositMoney(Account acc, Scanner sc) {
		System.out.println("=====================depositMoney page=====================");
		System.out.println("Please enter the deposited amount (integer multiples of 10):");
		double money = sc.nextDouble();

		//Update the account balance to ensure that the original money will not be affected
		acc.setMoney(acc.getMoney() + money);
		System.out.println("The deposit is successful, the account information is as follows:");
		showAccount(acc);
	}

	private static void showAccount(Account acc) {
		System.out.println("=====================The current account information is as follows=====================");
		System.out.println("cardId：" + acc.getCardId());
		System.out.println("username：" + acc.getUserName());
		System.out.println("money：" + acc.getMoney());
		System.out.println("quotaMoney：" + acc.getQuotaMoney());
	}

	/**
	 *  create account
	 * @param accounts
	 */
	private static void reqister(ArrayList<Account> accounts,Scanner sc) {
		System.out.println("=====================Create Account=====================");
		// 1.Create an account object for later encapsulation of account information
		Account account = new Account();

		// 2.Enter the current account information and inject it into the account object
		System.out.println("Please enter your account username:");
		String userName = sc.next();
		account.setUserName(userName);

		while (true) {
			System.out.println("Please enter the account password:");
			String passWord = sc.next();

			System.out.println("Please enter a confirmation password:");
			String okPassWord = sc.next();

			if (okPassWord.equals(passWord)) {
				account.setPassWord(passWord);
				break;
			} else {
				System.out.println("Sorry! The passwords you entered are inconsistent, please re-enter");

			}
		}
		System.out.println("Please set limits for your account:");
		Double quotaMoney = sc.nextDouble();
		account.setQuotaMoney(quotaMoney);

		// Randomly generate an 8-digit number for the account
		// that is not repeated with other account card numbers
		// (independent function, independent method)
		String cardId = getRandomCardId(accounts);
		account.setCardId(cardId);

		//3.Add the account object to the accounts collection
		accounts.add(account);
		System.out.println("congratulations, "+ userName + "Sir/Madam, you have successfully created an account," +
				" your card number is:"+cardId +"Please keep it safe!");

	}

	/**
	 *  Generate an 8-digit number for the account that is not duplicated with other accounts
	 * @return  unique number
	 */
	private static String getRandomCardId(ArrayList<Account> accounts) {
		Random r = new Random();
		while (true) {
			String cardId = "";
			for (int i = 0; i < 8; i++) {
				cardId += r.nextInt(10);
			}
			Account acc = getAccountByCardId(cardId, accounts);
			if (acc == null) {
				//acc is equal to null to prove that this card number is a new card number,
				// just return it directly
				return cardId;
			}
		}

	}

	/**
	 * Query an account object based on the card number
	 * @param cardId    card number
	 * @param accounts  A collection of all accounts
	 * @return  account object/NULL
	 */
	private static Account getAccountByCardId(String cardId, ArrayList<Account> accounts) {
		for (int i = 0; i < accounts.size(); i++) {
			Account acc = accounts.get(i);
			if (acc.getCardId().equals(cardId)) {
				return acc;
			}
		}
		return null; //There are no duplicate card numbers in the collection
	}

}
