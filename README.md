## Country Picker

Country Picker is a library that helps developers add a **"Country Picker Display"** in the Activity to their apps.


If the user need display Country List witht the icons as well as Country Dial Code.


## Country Picker
<img src="screenshot2.jpg" alt="preview" width="300" height="533">


## Installation

How to

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file 


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}



Step 2. Add the dependency

	dependencies {
		implementation 'com.github.AppAspectTech:Country-Picker-Android:1.0.4'
	}

## Notes
The library is very simple, just note that :
* set Proper CountryCode("IN") or CountryPhoneCode("+91") or CountryName("India") for display that Country name selected.
* set Status Bar Color as per your theme.
* set Cancel text Color as per your theme.


## How to use

1. To use this library just add this snippet in the `onClick` of your fragment or activity.

Country Picker Example:

        
                    
                                 // Add as a startActivityForResult
                                Bundle bundle_data=new Bundle();
                                
                                            if(countryData_selected==null)
                                            {
                                
                                                bundle_data.putString(CountryConstants.ARGUMENT_COUNTRY_PHONE_CODE,edit_country.getText().toString().trim());   // for set the country phone code
                                
                                
                                                //"OR"
                                                //bundle_data.putString(CountryConstants.ARGUMENT_COUNTRY_CODE,edit_country.getText().toString().trim());       // for set the country code
                                
                                                //"OR"
                                                //bundle_data.putString(CountryConstants.ARGUMENT_COUNTRY_NAME,edit_country.getText().toString().trim());       // for set the country name
                                
                                
                                            }
                                            else
                                            {
                                                bundle_data.putString(CountryConstants.ARGUMENT_COUNTRY_PHONE_CODE,countryData_selected.getCountry_dial_code());        // for set the country phone code
                                
                              
                                                //"OR"
                                                //bundle_data.putString(CountryConstants.ARGUMENT_COUNTRY_CODE,countryData_selected.getCountry_code());                 // for set the country code
                                
                                                //"OR"
                                                //bundle_data.putString(CountryConstants.ARGUMENT_COUNTRY_NAME,countryData_selected.getCountry_name());                 // for set the country name
                                
                                            }
                                
                                            bundle_data.putInt(CountryConstants.ARGUMENT_STATUSBAR_COLOR,R.color.colorPrimary);             // for set the status bar color as per theme
                                            bundle_data.putInt(CountryConstants.ARGUMENT_CANCEL_COLOR,R.color.colorPrimaryDark);            // for set the Cancel color as per theme
                                            Intent intent=new Intent(MainActivity.this, Country_Activity.class);
                                            intent.putExtras(bundle_data);
                                            startActivityForResult(intent, CountryConstants.SELECT_COUNTRY);
                                            
                            
2. To get the result form this library just add this snippet in your fragment or activity.

                        
        @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data)
            {
                super.onActivityResult(requestCode, resultCode, data);
                // check if the request code is same as what is passed  here it is 2
                if(resultCode== Activity.RESULT_OK)
                {
                    if(requestCode== CountryConstants.SELECT_COUNTRY)
                    {
                        if(data!=null)
                        {
                            Bundle bundle_data=data.getExtras();
                            if(bundle_data!=null)
                            {
                                String str_countryData=bundle_data.getString(CountryConstants.ARGUMENT_COUNTRY_DATA);
                                countryData_selected=  gson.fromJson(str_countryData,CountryData.class);
                                if(countryData_selected==null)
                                {
                                    edit_country.setText("");
                                }
                                else
                                {
                                    edit_country.setText(countryData_selected.getCountry_dial_code());      // for set the country phone code
        
        
                                    //"OR"
                                    //edit_country.setText(countryData_selected.getCountry_name());          // for set the country name
        
                                    //"OR"
                                    //edit_country.setText(countryData_selected.getCountry_code());     // for set the country code
        
        
                                }
        
                            }
        
                        }
        
                    }
                }
        
            }                      
                            
## Select Country Screen:

<img src="screenshot1.jpg" alt="preview" width="300" height="533">

## Country List Screen:

<img src="screenshot2.jpg" alt="preview" width="300" height="533">

## Country Picker Search Example:

<img src="screenshot3.jpg" alt="preview" width="300" height="533">

## Selected Country Screen:

<img src="screenshot4.jpg" alt="preview" width="300" height="533">

## Country Picker Selected Tick Example:

<img src="screenshot5.jpg" alt="preview" width="300" height="533">


 
                                    
## Used by

If you use my library, please tell me at info@appaspect.com
So I can add your app here!


## License
Do what you want with this library.
