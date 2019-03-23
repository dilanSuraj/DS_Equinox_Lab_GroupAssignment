package org.atm.cash;

import java.util.Hashtable;

import org.atm.cash.service.CashService;
import org.atm.user.User;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceListener;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.framework.ServiceEvent;

public class CashServiceActivator implements BundleActivator {
	private BundleContext m_context = null;
	// The service tacker object.
	private static ServiceTracker m_tracker = null;

	private static User user = null;

	public void start(BundleContext context) throws Exception {
		Hashtable<String, String> props = new Hashtable<String, String>();

		props.put("Language", "English");
		context.registerService(CashService.class.getName(), new CashServiceImpl(), props);
		System.out.println("Cash service registered and started successfully");

		

	}

	public void stop(BundleContext context) {
		// NOTE: The service is automatically unregistered.
	}

	private static class CashServiceImpl implements CashService {

		@Override
		public boolean cashWithdrawal(User user, double withdrawalAmt) {

			
			double acctBalance = user.getAccBalanace();

			if (acctBalance >= withdrawalAmt) {

				acctBalance -= withdrawalAmt;
				user.setAccBalanace(acctBalance);

				System.out.println("Successfully deducted Rs." + withdrawalAmt + ". You have now a balance of Rs." + acctBalance);
				return true;
			}
			System.out.println("Insufficient balance of amount Rs." + acctBalance);
			return false;
		}

		@Override
		public boolean cashDeposit(User user, double depositAmt) {

			
			double acctBalance = user.getAccBalanace();

			acctBalance += depositAmt;
			user.setAccBalanace(acctBalance);

			System.out.println("Successfully deposited Rs." + depositAmt + ". You have now a balance of Rs." + acctBalance);
			
			return true;

		}

		@Override
		public void viewAcctBalance(User user) {
             System.out.println("You have Rs."+user.getAccBalanace()+" amount of balance in your account");
		}

		@Override
		public boolean transferCash(User withdrwaedUser, User depositedUser, double transferAmt) {
             
			
			if(this.cashWithdrawal(withdrwaedUser, transferAmt)) {
				System.out.println("Successfully transfered to account "+depositedUser.getAccNo());
				depositedUser.setAccBalanace(depositedUser.getAccBalanace() + transferAmt);
				return true;
			}
			
		
		    System.out.println("Transfer Cancelled");
		    
			return false;
		}

	}

}