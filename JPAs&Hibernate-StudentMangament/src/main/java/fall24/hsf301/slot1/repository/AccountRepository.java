package fall24.hsf301.slot1.repository;

import fall24.hsf301.slot1.dao.AccountDAO;
import fall24.hsf301.slot1.pojo.Accounts;

public class AccountRepository implements IAccountRepository {
	private AccountDAO accountDAO = null;
	
	public AccountRepository(String fileConfig) {
		accountDAO = new AccountDAO(fileConfig);
	}
	
	@Override
	public Accounts findByUserNameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		return accountDAO.findByUserNameAndPassword(userName, password);
	}
}
