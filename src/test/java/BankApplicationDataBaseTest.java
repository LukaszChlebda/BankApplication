import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by LChlebda on 2016-01-21.
 */
public class BankApplicationDataBaseTest {
    private ClientDAO clientDAO;
    private BankDAO bankDAO;
    private String clientName;
    private Bank bank, getBank;
    private Client clientToSave;
    private Client getClientFromDataBase;

    @Before
    public void prepareTest() {
        bank = new Bank(1, "MyBank");

        getClientFromDataBase = null;
        clientDAO = new ClientDAOImpl();
        clientName = "Lukasz";
        clientToSave = new Client(clientName);
        bankDAO = new BankDAOImpl();
    }

//    @Test
//    public void saveClientToDBTest() {
//
//        try {
//            clientDAO.save(clientToSave);
//            getClientFromDataBase = clientDAO.getClientByName(bank, clientName);
//        }catch (DAOException e) {
//            e.printStackTrace();
//        } catch (ClientNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(clientName, clientToSave.getName());
//    }
//
//    @Test
//    public void deleteClientFromDBTest() {
//        Client clientToRemove = new Client(1, "Lukasz");
//        Client getClient = null;
//        try {
//            clientDAO.remove(clientToRemove);
//            getClient = clientDAO.getClientByName(bank, clientName);
//        } catch (DAOException e) {
//            e.printStackTrace();
//        } catch (ClientNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(null, getClient);
//    }

    @Test
    public void getClientFromDBTest() {
        Bank bank = new Bank("UBS");
        Client clientToGet = null;
        Client clientTemp = new Client("Lukasz");

        try {
            clientToGet = clientDAO.getClientByName(bank, "Lukasz");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(clientTemp.getName(), clientToGet.getName());
    }

    @Test
    public void getBankTest() {
        Bank bankToCheck = new Bank("UBS");
        try {
            getBank = bankDAO.getBankByName("UBS");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(bankToCheck.getName(), getBank.getName());
    }

    public void saveBankTest() {
        Bank bankToSave = new Bank("mBank");
        Bank tempBank = null;
        Bank bankNull = null;
        try {
            bankDAO.saveBank(bankToSave);
            tempBank = bankDAO.getBankByName("mBank");
            bankNull = bankDAO.getBankByName("sasa");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(bankToSave.getName(), tempBank.getName());
        assertNull(bankNull);
    }

    public void removeBankTest() {
        Bank bankToRemove = new Bank("Ubuntu");
        Bank bankTemp1 = null;
        Bank bankTemp2 = null;

        try {
            bankDAO.saveBank(bankToRemove);
            bankTemp1 = bankDAO.getBankByName("ubuntu");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(bankToRemove.getName(), bankTemp1.getName());

        try {
            bankDAO.saveBank(bankToRemove);
            bankTemp2 = bankDAO.getBankByName("Ubuntu");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }

        assertNull(bankTemp2);
    }
}
