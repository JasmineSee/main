package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Sends message to the phone number of the selected person in the address book.
 */
public class MessageCommand extends Command {


    public static final String MESSAGE_SUCCESS = "Message sent";

//    public void sendMessage() {
//
//        try {
//            String phoneNumber = "phone-number";
//            String appKey = "your-app-key";
//            String appSecret = "your-app-secret";
//            String message = "Hello, world!";
//            URL url = new URL("https://messagingapi.sinch.com/v1/sms/" + phoneNumber);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            String userCredentials = "application\\" + appKey + ":" + appSecret;
//            byte[] encoded = Base64.encodeBase64(userCredentials.getBytes());
//            String basicAuth = "Basic " + new String(encoded);
//            connection.setRequestProperty("Authorization", basicAuth);
//            String postData = "{\"Message\":\"" + message + "\"}";
//            OutputStream os = connection.getOutputStream();
//            os.write(postData.getBytes());
//            StringBuilder response = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = br.readLine()) != null)
//                response.append(line);
//            br.close();
//            os.close();
//            System.out.println(response.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
  //  }


    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
