package fall24.hsf301.slot1.service;

import fall24.hsf301.slot1.pojo.Accounts;
import fall24.hsf301.slot1.repository.AccountRepository;
import fall24.hsf301.slot1.repository.IAccountRepository;

public class AccountService implements IAccountService {
	
	private IAccountRepository iAccountRepo = null;
	
	public AccountService(String fileName) {
		iAccountRepo = new AccountRepository(fileName);
	}
	@Override
	public Accounts findByUserNameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		return iAccountRepo.findByUserNameAndPassword(userName, password);
	}
}
