package com.home.Tracker;

import com.google.gson.Gson;
import com.home.Entity.CurrencyConversionResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This CurrencyRate class contains methods for receiving foreign exchange rates published by the European Central Bank
 *
 * @author Nazar Vynnyk
 */

public class CurrencyRate {

    private static final Logger LOGGER = Logger.getLogger(CurrencyRate.class);

    /**
     * Url for getting currency rates
     */
    private static final String API_PROVDIER = "http://api.fixer.io/";

    /**
     * Receives exchange rate for 'fromCurrency' against 'toCurrency' from site: ​http://fixer.io​,
     *
     * @param fromCurrency - base currency for which we receive exchange rate
     * @param toCurrency   - currency against which we receive request.
     * @return exchange rate
     */

    public double convert(String fromCurrency, String toCurrency) {

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

    /**
     * Receives all rates in JSON format and converts them to currencyConversionResponse
     *
     * @param strUrl - url for receiving JSON  with exchange rates
     * @return - CurrencyConversionResponse object that contains map of all rates
     */
    private CurrencyConversionResponse getResponse(String strUrl) {
        CurrencyConversionResponse response = null;
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();

        if (strUrl == null || strUrl.isEmpty()) {
            LOGGER.error("url in getResponse method fails");
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
            LOGGER.error(e.getMessage(), e);
        }
        return response;
    }

}
