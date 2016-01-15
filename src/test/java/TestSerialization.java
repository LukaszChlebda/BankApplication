import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Łukasz on 28.12.15.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSerialization {

    BankService bService;
    Bank bank;
    Client client;
    String clientName = "Przemek";
    String clientObject = clientName+".object";
    @Before
    public void peeperTest() {
        bService = new BankServiceImpl();
        bank = BankApplication.initialize(bService);
        client = null;
    }

    @Test
    public void testAObjectSerialize() throws ClientNotFoundException {
        bService.saveClient(bank.getClient(clientName));
        File file = new File(clientObject);
        assertTrue(file.exists());
    }

    @Test
    public void testBObjectDeserialize() throws ClientNotFoundException, ClientExistsException {
        bService.removeClient(bank, bank.getClient(clientName));
        assertEquals(null, bank.getClient(clientName));
        client = bService.loadClient(clientObject);
        bService.addClient(bank, client);
        assertEquals(bank.getClient(clientName), client);
        client.printReport();
    }
}
