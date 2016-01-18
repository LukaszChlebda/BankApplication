import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankApplication;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by LChlebda on 2016-01-14.
 */
public class BankServerThreadedTest {

    List<Thread> t = new ArrayList<>();

    Bank bank = BankApplication.getBankInstance();
    int loopIteration = 1000;
    double amount, amount1;
    Client client;

    @Before
    public void prepereTest() {
        try {
            client = bank.getClient("Lukasz");
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        amount = client.getAccounts().get(0).getBalance();
        System.out.println("\n" + amount);


        for (int i = 0; i < loopIteration; i++) {
            t.add(new Thread(new BankClientMock()));
        }

        for (int i = 0; i < loopIteration; i++) {
            t.get(i).start();
            try {
                t.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Test
    public void testMockClient() {
        Client client1 = null;

        try {
            client1 = bank.getClient("Lukasz");
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        amount1 = client1.getAccounts().get(0).getBalance();

        System.out.println(amount1);
        assertEquals(amount-loopIteration, amount1);
        System.out.println("Test");
    }
}

