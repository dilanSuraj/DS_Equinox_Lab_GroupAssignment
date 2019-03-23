package org.atm.user.service;

import org.atm.user.User;

public interface UserService {
	
	public boolean login(String accNo, String PINNo);
	
	public boolean changepinNo(String accNo,String oldPINNo,String newPINNo);
	
	public User userDetails(String accNo);
}
