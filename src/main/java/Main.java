/**
 * Created by roberthumphres on 6/10/18.
 */



import org.apache.commons.net.telnet.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Helo world");

        AutomatedTelnetClient automatedTelnetClient =
                new AutomatedTelnetClient(
                        Constants.lutronMainRepeaterIP,
                        Constants.lutronMainRepeaterUser,
                        Constants.lutronMainRepeaterPassword
                );

//        automatedTelnetClient.write("#DEVICE,29,2,3<CR><LF>");
        boolean phoneHere = false;
        boolean firstPingFail = false;
        while (true) {
            boolean cantFind = true;
            for (int i = 0; i < 4; i++) {
                try {
                    if (InetAddress.getByName(Constants.robPhoneIP).isReachable(2500)) {
                        if (!phoneHere)
                            automatedTelnetClient.write("#DEVICE,29,2,3<CR><LF>");

                        System.out.println("Phones Here");
                        phoneHere = true;
                        cantFind = false;
                        i = 4;
                    }
                    System.out.println("Try " + i + " out of 4");
                } catch (Exception e) {
                    System.out.println("There was an exception");
                }
            }// for loop

            if (cantFind) {
                //If phone isn't able to be reached
                if (phoneHere)
                    automatedTelnetClient.write("#DEVICE,28,6,3<CR><LF>");

                System.out.println("No phones are here");
                phoneHere = false;
            }
            Thread.sleep(180000);
        }
    }

}