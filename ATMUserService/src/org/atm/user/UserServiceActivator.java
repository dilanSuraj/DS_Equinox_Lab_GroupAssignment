package org.atm.user;

import java.util.ArrayList;
import java.util.Hashtable;

import org.atm.user.service.UserService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;

public class UserServiceActivator implements BundleActivator {

	public void start(BundleContext context) {
		Hashtable<String, String> props = new Hashtable<String, String>();

		props.put("Language", "English");
		context.registerService(UserService.class.getName(), new UserServiceImpl(), props);
		System.out.println("User service available now");
	}

	public void stop(BundleContext context) {
		
		System.out.println("User service is stopped!!!");
	}

	private static class UserServiceImpl implements UserService {

		public ArrayList<User> getUserList() {

			ArrayList<User> userList = new ArrayList<User>();
			User user1 = new User("Navod", "ACC001", 1000, "12345");
			User user2 = new User("Vimanga", "ACC002", 2000, "23456");
			User user3 = new User("Yasiru", "ACC003", 3000, "34567");
			User user4 = new User("Dilan", "ACC004", 4000, "56789");

			userList.add(user1);
			userList.add(user2);
			userList.add(user3);
			userList.add(user4);

			return userList;
		}

		@Override
		public boolean login(String accNo, String PINNo) {

			ArrayList<User> userList = this.getUserList();

			for (User user : userList) {
				if (user.getAccNo().equals(accNo)) {
					if (user.getPINNo().equals(PINNo)) {
						System.out.println(user.toString());
					}

					System.out.println("PIN Number is incorrect!");
					return true;
				}
			}

			System.out.println("PIN Number is incorrect!");
			return false;
		}

		@Override
		public boolean changepinNo(String accNo, String oldPINNo, String newPINNo) {

			ArrayList<User> userList = this.getUserList();

			for (User user : userList) {

				if (user.getAccNo().equals(accNo)) {

					if (user.getPINNo().equals(oldPINNo)) {

						user.setPINNo(newPINNo);
						return true;
					}

					return false;
				}
			}

			return false;
		}

		@Override
		public User userDetails(String accNo) {

			ArrayList<User> userList = this.getUserList();

			for (User user : userList) {

				if (user.getAccNo().equals(accNo)) {

					return user;
				}
			}

			return null;
		}

	}

}