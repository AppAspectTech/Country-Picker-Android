package com.country.picker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.country.picker.Utils.AppConstants;
import com.country.picker.model.CountryData;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private AppCompatEditText edit_country;
    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        manageOnClick();

    }

    private void init()
    {
         edit_country=findViewById(R.id.edit_country);


    }
    private void manageOnClick()
    {
        edit_country.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==AppConstants.SELECT_COUNTRY)
            {
                if(data!=null)
                {
                    Bundle bundle_data=data.getExtras();
                    if(bundle_data!=null)
                    {
                        String str_countryData=bundle_data.getString(AppConstants.ARGUMENT_1);
                        CountryData countryData_selected=  gson.fromJson(str_countryData,CountryData.class);

                        if(countryData_selected==null)
                        {
                            edit_country.setText("");
                        }
                        else
                        {
                            edit_country.setText(countryData_selected.getCountry_name());
                        }

                    }

                }

            }
        }

    }
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.edit_country)
        {
            Bundle bundle_data=new Bundle();
            bundle_data.putString(AppConstants.ARGUMENT_1,edit_country.getText().toString().trim());
            bundle_data.putInt(AppConstants.ARGUMENT_2,R.color.colorAccent);
            bundle_data.putInt(AppConstants.ARGUMENT_3,R.color.background_black_color);
            Intent intent=new Intent(MainActivity.this, Country_Activity.class);
            intent.putExtras(bundle_data);
            startActivityForResult(intent, AppConstants.SELECT_COUNTRY);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}