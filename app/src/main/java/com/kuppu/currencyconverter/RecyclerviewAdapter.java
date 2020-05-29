package com.kuppu.currencyconverter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.CurrencyViewHolder> {

private Context context;
private List<Currency>currencyList;

    public RecyclerviewAdapter(Context ctx,List<Currency>currencylist){
        this.context=ctx;
        this.currencyList=currencylist;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.currency_list_item,null);

        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency=currencyList.get(position);
        List<Country>list=currency.getCountry();
        //Log.e("Size",list.);

            Country country=list.get(position);


        holder.currency_title.setText(country.getCurrencyname());
        holder.country_code.setText(currency.getUSDCurrencyCode());
        Double amt=Double.parseDouble(currency.getAmount());
        Log.e("Dcs",String.valueOf(amt));
        holder.amount.setText(String.valueOf(amt));


    }


    public void ExchangedCurrency(List<Currency>list){
        Log.e("Mss",list.get(0).getAmount());
        currencyList.clear();
        currencyList.addAll(list);

       // notifyItemRangeChanged(0,currencyList.size());
notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return currencyList.size();
    }
    class CurrencyViewHolder extends RecyclerView.ViewHolder{
    public     TextView currency_title,country_code,amount,exchangedamount;
        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);

            currency_title=itemView.findViewById(R.id.textViewTitle_currency);
            country_code=itemView.findViewById(R.id.textViewTitle_code);

            amount=itemView.findViewById(R.id.textViewTitle_value);


        }
    }
}
