package org.atm.user;

public class User {
	
	private String name;	
	private String accNo;	
	private double accBalanace;
	private String PINNo;
	
	
	public User(String name, String accNo, double accBalanace, String pINNo) {
		super();
		this.name = name;
		this.accNo = accNo;
		this.accBalanace = accBalanace;
		PINNo = pINNo;
	}

	
	public String getPINNo() {
		return PINNo;
	}


	public void setPINNo(String pINNo) {
		PINNo = pINNo;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public double getAccBalanace() {
		return accBalanace;
	}
	public void setAccBalanace(double accBalanace) {
		this.accBalanace = accBalanace;
	}
	
	public String toString() {
		return "Welcome "+ name;
	}

}
