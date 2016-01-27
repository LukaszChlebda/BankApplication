import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by LChlebda on 2016-01-26.
 */
public class BankDAOTest {
    Bank bank;

    @Before
    public void initBank () {
        bank = new Bank ();
        bank .setName ("My Bank");
        Client client = new Client ();
        client.setName ("Ivan Ivanov");
        client.setCity ("Kiev");
        client.addAccount (new CheckingAccount());
        try {
            bank.addClient(bank, client);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert () throws BankNotFoundException, DAOException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        bankDAO.saveBank(bank);
        Bank bank2 = null;
        bank2 = bankDAO.getBankByName("My Bank");
        assertTrue(TestService.isEquals(bank, bank2));
    }


    @ Test
    public void testUpdate () throws DAOException, ClientExistsException, BankNotFoundException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        bankDAO.saveBank(bank);
        Client client2 = new Client ();
        client2.setName ("Ivan Petrov");
        client2.setCity ("New York");
        client2.addAccount (new SavingAccount());
        bank.addClient(bank, new Client ("Alek"));
        bankDAO.saveBank(bank);
        Bank bank2 = null;
        bank2 = bankDAO.getBankByName("My Bank");
        assertTrue (TestService.isEquals(bank, bank2));
    }
}

