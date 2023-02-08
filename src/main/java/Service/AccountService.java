package Service;
import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {
    
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts(){
        return null;
    }
}
