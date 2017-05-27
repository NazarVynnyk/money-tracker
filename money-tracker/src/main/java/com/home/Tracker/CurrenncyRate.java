package com.home.Tracker;

import com.google.gson.Gson;
import com.home.Entity.CurrencyConversionResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrenncyRate {
    // API Provider URL
    private static final String API_PROVDIER = "http://api.fixer.io/";

    public static double convert(String fromCurrency, String toCurrency) {

        if ((fromCurrency != null && !fromCurrency.isEmpty())
                && (toCurrency != null && !toCurrency.isEmpty())) {
            CurrencyConversionResponse response = getResponse(API_PROVDIER + "/latest?base=" + fromCurrency);

            if (response != null) {
                String rate = response.getRates().get(toCurrency);
                return Double.valueOf((rate != null) ? rate : "0.0");
            }
        }
        return 0.0;
    }

    // Method to get the response from API
    private static CurrencyConversionResponse getResponse(String strUrl) {
        CurrencyConversionResponse response = null;
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();

        if (strUrl == null || strUrl.isEmpty()) {
            System.out.println("Application Error");
            return null;
        }
        URL url;
        try {
            url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            int data = stream.read();
            while (data != -1) {
                sb.append((char) data);
                data = stream.read();
            }
            stream.close();
            response = gson.fromJson(sb.toString(), CurrencyConversionResponse.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

}
