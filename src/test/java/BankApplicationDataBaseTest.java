import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by LChlebda on 2016-01-21.
 */
public class BankApplicationDataBaseTest {
    private ClientDAO clientDAO;
    private String clientName;
    private Bank bank;
    private Client clientToSave;
    private Client getClientFromDataBase;

    @Before
    public void prepareTest() {
        bank = new Bank(1, "MyBank");
        getClientFromDataBase = null;
        clientDAO = new ClientDAOImpl();
        clientName = "Lukasz";
        clientToSave = new Client(clientName);
    }

    @Test
    public void saveClientToDBTest() {

        try {
            clientDAO.save(clientToSave);
            getClientFromDataBase = clientDAO.getClientByName(bank, clientName);
        }catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(clientName, clientToSave.getName());
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
}
