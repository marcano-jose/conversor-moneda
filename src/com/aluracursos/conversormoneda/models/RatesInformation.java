package com.aluracursos.conversormoneda.models;

import java.util.Map;

public class RatesInformation {
    private long time_last_update_unix;
    private String base_code;
    private Map<String, Double> conversion_rates;
    private Double conversion_rate;


    public long getTime_last_update_unix() {
        return time_last_update_unix;
    }

    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }

    public Double getConversion_rate() {
        return conversion_rate;
    }
}
