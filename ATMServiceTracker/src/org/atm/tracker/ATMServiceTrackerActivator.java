package org.atm.tracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.atm.cash.CashServiceActivator;
import org.atm.cash.service.CashService;
import org.atm.user.User;
import org.atm.user.UserServiceActivator;
import org.atm.user.service.UserService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class ATMServiceTrackerActivator {

	private BundleContext m_context = null;

	private ServiceTracker m_tracker_user = null;

	private ServiceTracker m_tracker_cash = null;

	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public void start(BundleContext context) throws Exception {
		m_context = context;

		// Create a service tracker to monitor dictionary services.
		m_tracker_user = new ServiceTracker(m_context, m_context
				.createFilter("(&(objectClass=" + UserServiceActivator.class.getName() + ")" + "(Language=*))"), null);
		m_tracker_user.open();

		m_tracker_cash = new ServiceTracker(m_context, m_context
				.createFilter("(&(objectClass=" + CashServiceActivator.class.getName() + ")" + "(Language=*))"), null);
		m_tracker_cash.open();

		try {
			System.out.println("Enter number 0 to exit.");
			String accountNumber = "";
			String PINNumber = "";

			String choice = " ";

			UserService userService = (UserService) m_tracker_user.getService();

			CashService cashService = (CashService) m_tracker_cash.getService();

			if (userService == null || cashService == null) {
				System.out.println("No service available.");
				return;
			}

			System.out.println("*****Welcome to our ATM*****");

			while (true) {

				System.out.println("**********Login*************");
				System.out.println("Please enter Account Number !");
				accountNumber = in.readLine();

				if (accountNumber.length() == 0) {
					break;
				}

				System.out.println("Please enter PIN Number !");
				PINNumber = in.readLine();

				if (userService.login(accountNumber, PINNumber)) {
					this.ATMServiceMenu();
					choice = in.readLine();

					while (!choice.equals("0")) {

					}

					break;
				} else {
					continue;
				}
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * Implements BundleActivator.stop(). Does nothing since the framework will
	 * automatically unget any used services.
	 * 
	 * @param context the framework context for the bundle.
	 **/
	public void stop(BundleContext context) {
	}

	public void ATMServiceMenu() {

		System.out.println(
				"Here are the services provided by us, please enter the relevant number to enjoy the service ");
		System.out.println("1. Cash Withdrawal");
		System.out.println("2. Cash Deposit");
		System.out.println("3. Balance Inquiry");
		System.out.println("4. Money Transfer");
		System.out.println("5. Change PIN Number");
		System.out.println("0. Exit");
	}

	public void ATMServiceHandler(String choice, UserService userService, CashService cashService, User user) {

		boolean serviceValidity = true;
		String amount = null;
		if (choice.equals("1")) {

			while (serviceValidity) {
				System.out.println("Please enter amount to withdraw !");

				try {
					
					amount = in.readLine();
					serviceValidity = cashService.cashWithdrawal(user, Double.parseDouble(amount));
					
				
				} catch (IOException e) {
					System.out.println("Please enter only numbers!");
					continue;

				}

			}

		}

		else if (choice.equals("2")) {

			while (serviceValidity) {
				System.out.println("Please enter amount to deposit !");

				try {
					amount = in.readLine();
					serviceValidity = cashService.cashDeposit(user, Double.parseDouble(amount));
				} catch (IOException e) {
					System.out.println("Please enter only numbers!");
					continue;

				}

			}

		}

		else if (choice.equals("4")) {

			while (serviceValidity) {
				System.out.println("Please enter amount to transfer !");
				String depositedAccNo;
				try {
					amount = in.readLine();
					System.out.println("Please enter the account number that needs to deposit");
					depositedAccNo = in.readLine();

					User depositeduser = userService.userDetails(depositedAccNo);

					if (depositeduser != null) {
						serviceValidity = cashService.transferCash(user, depositeduser, Double.parseDouble(amount));
					}

					serviceValidity = false;

				} catch (IOException e) {
					System.out.println("Please enter only numbers!");
					continue;

				}

			}

		}

		else if (choice.equals("3")) {

			while (serviceValidity) {

				cashService.viewAcctBalance(user);

				serviceValidity = false;

			}

		}

		else if (choice.equals("5")) {

			while (serviceValidity) {

				System.out.println("Please enter old PIN number  !");
				String oldPIN, newPIN1, newPIN2;
				try {
					oldPIN = in.readLine();

					if (oldPIN.equals(user.getPINNo())) {
						System.out.println("Please enter the new PIN number");
						newPIN1 = in.readLine();

						System.out.println("Please enter the new PIN number again to confirm");
						newPIN2 = in.readLine();

						if (newPIN1.equals(newPIN2)) {
							serviceValidity = userService.changepinNo(user.getAccNo(), oldPIN, newPIN1);
						}
                        
						else {
							continue;
						}

					}
					
					else {
						
						continue;
						
					}
				} catch (IOException e) {
					System.out.println("Please enter only numbers!");
					continue;

				}

			}

		}

	}

}
