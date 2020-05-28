package com.kuppu.currencyconverter;

import java.util.List;

public class Currency {

    private  String USDCurrencyCode;
    private String amount;
    private String usrEnterAmt;
    private String covetCurrRate;



    private List<Country> country;

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public String getUSDCurrencyCode() {
        return USDCurrencyCode;
    }

    public void setUSDCurrencyCode(String USDCurrencyCode) {
        this.USDCurrencyCode = USDCurrencyCode;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsrEnterAmt() {
        return usrEnterAmt;
    }

    public void setUsrEnterAmt(String usrEnterAmt) {
        this.usrEnterAmt = usrEnterAmt;
    }

    public String getCovetCurrRate() {
        return covetCurrRate;
    }

    public void setCovetCurrRate(String covetCurrRate) {
        this.covetCurrRate = covetCurrRate;
    }
}

