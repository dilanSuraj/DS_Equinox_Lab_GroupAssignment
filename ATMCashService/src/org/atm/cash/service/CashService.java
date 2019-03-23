package org.atm.cash.service;

import org.atm.user.User;

public interface CashService {

	public boolean cashWithdrawal(User user, double withdrawalAmt);
	
	public boolean cashDeposit(User user, double depositAmt);
	
	public void viewAcctBalance(User user);
	
	public boolean transferCash(User withdrwaedUser, User depositedUser, double transferAmt);
	
}
