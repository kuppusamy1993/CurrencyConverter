package com.kuppu.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spin_currency;
    private EditText amount_value_edit;
    private RecyclerView recyclerView;
    List<Currency>currencylist=new ArrayList<>();
    private RecyclerviewAdapter recyclerviewAdapter;

    private RequestQueue requestQueue;

    List<Country>countryvalue=new ArrayList<>();
    ArrayList<String>spinner_list=new ArrayList<>();

    String user_entered_amount;
    List<Currency>listdata=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_list.add("Select Currency");
        getCurrencyList();
        spin_currency=findViewById(R.id.spinner_curr_value);
        amount_value_edit=findViewById(R.id.edit_amount);
        recyclerView=findViewById(R.id.recyclerview_currencylist);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


getCurrencyValues();


Log.e("Size",spinner_list.toString());

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinner_list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_currency.setAdapter(adapter);

        recyclerviewAdapter=new RecyclerviewAdapter(getApplicationContext(),currencylist);
        recyclerView.setAdapter(recyclerviewAdapter);


        spin_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                int pos=spin_currency.getSelectedItemPosition();
                        if(pos==0){


                        }else {
                            String usertext=amount_value_edit.getText().toString().trim();
                            if(usertext==null||usertext.equals("")) {
                                Toast.makeText(MainActivity.this, "Please enter the amount", Toast.LENGTH_SHORT).show();
                            }else {
                                CalculateExchange(pos);

                            }

                        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    private void getCurrencyList(){

        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, Api.currency_url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("mycurrency",response.toString());
                try {

                    JSONObject object=response.getJSONObject("currencies");
                    Iterator<?> itr=object.keys();
                    while (itr.hasNext()) {
                        String key = (String) itr.next();

                        String values=object.get(key).toString();

                        spinner_list.add(values);
                        Country country=new Country();
                        country.setCurrencyCode(key);
                        country.setCurrencyname(values);
                        countryvalue.add(country);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }

    private void getCurrencyValues() {

        JsonObjectRequest currencyvalueobject=new JsonObjectRequest(Request.Method.GET, Api.currency_value_url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("mycurrency",response.toString());
                try {
                    JSONObject object=response.getJSONObject("quotes");
                    Iterator<?> itr=object.keys();
                    while (itr.hasNext()) {
                        String key = (String) itr.next();

                        Log.e("mycurrency",key);
                        //object.get(key).toString();
                        String value=object.get(key).toString();
                        String keyusdremoved=key;
                        keyusdremoved=keyusdremoved.substring(3);
                        Log.e("Key",keyusdremoved);
                        Currency currency=new Currency();
                        currency.setUSDCurrencyCode(keyusdremoved);

                        currency.setAmount(value);
                        currency.setCountry(countryvalue);
                        currencylist.add(currency);

                        Log.e("mycurrency",value);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(currencyvalueobject);

    }
private void CalculateExchange(int pos){
    user_entered_amount = amount_value_edit.getText().toString();

    String countrycode = currencylist.get(pos).getUSDCurrencyCode();
    String amount = currencylist.get(pos).getAmount();

    int givenamt=Integer.parseInt(user_entered_amount.trim());
    float currency_val=Float.parseFloat(amount);
    float result=givenamt/currency_val;
    Log.e("MSg",String.valueOf(result));

    listdata.clear();
    for(int i=0;i<currencylist.size();i++){
        String s=currencylist.get(i).getAmount();
        String ccode=currencylist.get(i).getUSDCurrencyCode();
        float exchangeval=Float.parseFloat(s);

        float res=result*exchangeval;
        Log.e("Calc",String.valueOf(res));
        String exchangedoutput=String.valueOf(res);
      Currency  currency =new Currency();
        currency.setAmount(exchangedoutput);
        currency.setCountry(countryvalue);
        currency.setUSDCurrencyCode(ccode);
        listdata.add(currency);
    }


    recyclerviewAdapter.ExchangedCurrency(listdata);
}

}
