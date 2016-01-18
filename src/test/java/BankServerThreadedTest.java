import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.server.GetAccountsInfoRequest;
import com.luxoft.bankapp.server.LoginReguest;
import com.luxoft.bankapp.server.LogoutRequest;
import com.luxoft.bankapp.server.Request;
import com.luxoft.bankapp.service.BankApplication;
import org.junit.After;
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
    Request loginRequest, balanceRequest, logoutRequest;
    BankClientMock client2;
    String message;

    @Before
    public void prepereTest() {

//        client2 = new BankClientMock();
//
//        loginRequest = new LoginReguest("Lukasz");
//        balanceRequest = new GetAccountsInfoRequest();
//        logoutRequest = new LogoutRequest();
//        client2.sendRequest(loginRequest);




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



        System.out.println(amount1);
        assertEquals(100, 100);
        System.out.println("Test");
        try {
            System.out.println("------------");
            System.out.println(bank.getClient("Lukasz").getAccounts().get(0).getBalance());
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void clean() {
        try {
            System.out.println("------------");
            System.out.println(bank.getClient("Przemek").getAccounts().get(0).getBalance());
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
    }
}