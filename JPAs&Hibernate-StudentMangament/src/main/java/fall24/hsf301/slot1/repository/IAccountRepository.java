package fall24.hsf301.slot1.repository;

import fall24.hsf301.slot1.pojo.Accounts;

public interface IAccountRepository {
	public Accounts findByUserNameAndPassword(String userName, String password);
}
