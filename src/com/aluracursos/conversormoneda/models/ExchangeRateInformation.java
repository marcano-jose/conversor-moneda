package com.aluracursos.conversormoneda.models;

import java.util.Map;

public class ExchangeRateInformation {
    private long time_last_update_unix;
    private String base_code;
    private Map<String, Double> conversion_rates;

    public long getTime_last_update_unix() {
        return time_last_update_unix;
    }

    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }
}
