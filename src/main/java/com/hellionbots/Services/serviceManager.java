package com.hellionbots.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class serviceManager {

    private static String url = "https://dog.ceo/api/breeds/image/random";

    public String getImageURL() {
        try {
            URL urlForGetRequest = new URL(url);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();
            //Map<String, String> map = new HashMap<>();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();

                String s = response.toString();

                JSONObject object = new JSONObject(s);
                return object.getString("message");
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return "ERROR 404";
    }
}
