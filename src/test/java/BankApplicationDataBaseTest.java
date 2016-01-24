import com.luxoft.bankapp.DBComands.DBBankCommander;
import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
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
        clientDAO = new ClientDaoImpl();
        clientName = "Lukasz";
        clientToSave = new Client(clientName);
        bankDAO = new BankDAOImpl();
    }

    @Test
    public void saveClientToDBTest() {
        clientToSave = new Client("Kamil", Gender.MALE, "kamil@gmail.com");
        try {
            clientDAO.save(DBBankCommander.activeBank, clientToSave);
            getClientFromDataBase = clientDAO.getClientByName(bank, clientName);
        }catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(getClientFromDataBase, clientToSave.getName());
    }

    @Test
    public void deleteClientFromDBTest() {
        Client clientToRemove = new Client(1, "Lukasz");
        Client getClient = null;
        try {
            clientDAO.remove(clientToRemove);
            getClient = clientDAO.getClientByName(bank, clientName);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(null, getClient);
    }


    @Test
    public void getAllClientsTest() {
        ClientDAO clientDAO = new ClientDaoImpl();
        Bank bank = new Bank("UBS");
        List<Client> listOfClientsToGet = new ArrayList<>();
        List<Client> listOfClientsTemp = new ArrayList<>();

        listOfClientsTemp.add(new Client("Lukasz"));
        listOfClientsTemp.add(new Client("Marek"));

        try {
            listOfClientsToGet = clientDAO.getAllClients(bank);
        } catch (DAOException e) {
            e.printStackTrace();
        }


        assertNotNull(listOfClientsTemp);
        assertEquals(listOfClientsTemp, listOfClientsToGet);

    }

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

    @Test
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
            System.out.printf(e.getMessage());
        }

        assertEquals(bankToSave.getName(), tempBank.getName());
        assertNull(bankNull);
    }

    @Test
    public void removeBankTest() {
        Bank bankToRemove = new Bank("Ubuntu");
        Bank bankTemp1 = null;
        Bank bankTemp2 = null;

        try {
            bankDAO.saveBank(bankToRemove);
            bankTemp1 = bankDAO.getBankByName("Ubuntu");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(bankToRemove.getName(), bankTemp1.getName());


        try {
            bankDAO.removeBank(bankTemp1);
            bankTemp2 = bankDAO.getBankByName("Ubuntu");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }

        assertNull(bankTemp2);
    }
}
