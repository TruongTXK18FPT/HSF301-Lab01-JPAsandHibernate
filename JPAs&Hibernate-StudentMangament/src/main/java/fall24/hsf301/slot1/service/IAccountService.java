package fall24.hsf301.slot1.service;

import fall24.hsf301.slot1.pojo.Accounts;

public interface IAccountService {
	public Accounts findByUserNameAndPassword(String userName, String password);
}
