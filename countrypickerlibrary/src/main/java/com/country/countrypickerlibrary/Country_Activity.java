package com.country.countrypickerlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.country.countrypickerlibrary.Utils.CountryConstants;
import com.country.countrypickerlibrary.Utils.Utils;
import com.country.countrypickerlibrary.customcomponents.RoundedImageView;
import com.country.countrypickerlibrary.model.CountryData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressLint("LongLogTag")
public class Country_Activity extends AppCompatActivity implements View.OnClickListener
{

    private RecyclerView lst_country;
    private List_ItemsAdapter list_items_adapter;
    private Bundle bundle_data=null;
    private String str_country_name="";
    private Gson gson=new Gson();
    private int status_color=R.color.colorPrimaryDark;
    private int  cancel_color=R.color.colorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);


        Intent int_data=getIntent();
        bundle_data=int_data.getExtras();

        if(bundle_data!=null)
        {
           str_country_name= bundle_data.getString(CountryConstants.ARGUMENT_1);
            status_color= bundle_data.getInt(CountryConstants.ARGUMENT_2,R.color.colorPrimaryDark);
            cancel_color= bundle_data.getInt(CountryConstants.ARGUMENT_3,R.color.colorPrimary);
        }

        if(!TextUtils.isEmpty(str_country_name))
        {
            Log.e("str_country_name ", ""+str_country_name);
        }

            //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(status_color));
        }

        manageHeader();
        init();
        get_Country_list();

    }

    private void init()
    {
        lst_country = findViewById(R.id.lst_country);
        AppCompatEditText edt_search = findViewById(R.id.edt_search);

        LinearLayoutManager   layoutManager =new LinearLayoutManager(this);
        lst_country.setLayoutManager(layoutManager);
        lst_country.addItemDecoration(new DividerItemDecoration(lst_country.getContext(), LinearLayout.VERTICAL));


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //after the change calling the method and passing the search input
                list_items_adapter.getFilter().filter(editable.toString());
            }
        });

    }

    private void manageHeader()
    {
        AppCompatTextView txt_Cancel= findViewById(R.id.txt_Cancel);
        AppCompatTextView txt_Select=findViewById(R.id.txt_Select);

        txt_Cancel.setTextColor(this.getResources().getColor(cancel_color));
        txt_Select.setTextColor(this.getResources().getColor(cancel_color));
        txt_Select.setVisibility(AppCompatTextView.INVISIBLE);
        txt_Select.setOnClickListener(this);
        txt_Cancel.setOnClickListener(this);

    }


    private void get_Country_list()
    {
        ArrayList<CountryData> countryDataArrayList = new ArrayList<CountryData>();

        try
        {
            JSONArray jsonArray=new JSONArray(CountryConstants.COUNTRY_JSON_DATA);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                int number=i+1;
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                CountryData countryData=  gson.fromJson(jsonObject.toString(),CountryData.class);

                countryData.setChecked(false);

                if(!TextUtils.isEmpty(str_country_name))
                {
                    if(str_country_name.equalsIgnoreCase(countryData.getCountry_name()))
                    {
                        countryData.setChecked(true);
                    }
                }

                countryDataArrayList.add(countryData);

            }
        }
        catch (JSONException e)
        {
            Log.e("get_Country_list JSONException ", ""+e.toString());
        }
        catch (Exception e)
        {
            Log.e("get_Country_list Exception", ""+e.toString());
        }

        Collections.sort(countryDataArrayList, new Comparator<CountryData>(){
            public int compare(CountryData obj1, CountryData obj2)
            {
                // ## Ascending order
                return obj1.getCountry_name().compareToIgnoreCase(obj2.getCountry_name()); // To compare string values
                // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
            }
        });

        list_items_adapter = new List_ItemsAdapter(this,countryDataArrayList );
        lst_country.setAdapter(list_items_adapter);

    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == R.id.txt_Select || id == R.id.txt_Cancel)
        {
            onBackPressed();
        }
    }


    public class List_ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        private Context context;
        private ArrayList<CountryData> countryDataArrayList_adpt;
        private ArrayList<CountryData> countryDataArrayList_adpt_all;

        private List_ItemsAdapter(ArrayList<CountryData> countryDataArrayList)
        {

            countryDataArrayList_adpt = countryDataArrayList;
            countryDataArrayList_adpt_all = new ArrayList<>(countryDataArrayList_adpt);
        }

        private List_ItemsAdapter(Context context, ArrayList<CountryData> countryDataArrayList ) {


            countryDataArrayList_adpt = countryDataArrayList;
            countryDataArrayList_adpt_all = new ArrayList<>(countryDataArrayList_adpt);
            this.context = context;

        }

        private void updateData_Position(CountryData user_data, int position) {

            countryDataArrayList_adpt.set(position,user_data);
            notifyItemChanged(position);

        }

        private void updateData(ArrayList<CountryData> countryDataArrayList) {

            countryDataArrayList_adpt = countryDataArrayList;
            notifyDataSetChanged();

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_country_lists, parent, false);
                return new ItemViewHolder(view);
            } else
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                return new LoadingViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            if (viewHolder instanceof ItemViewHolder) {

                populateItemRows((ItemViewHolder) viewHolder, position);
            } else if (viewHolder instanceof LoadingViewHolder) {
                showLoadingView((LoadingViewHolder) viewHolder, position);
            }

        }

        @Override
        public int getItemCount() {
            return countryDataArrayList_adpt == null ? 0 : countryDataArrayList_adpt.size();
        }

        @Override
        public int getItemViewType(int position) {
            return countryDataArrayList_adpt.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }


        private class ItemViewHolder extends RecyclerView.ViewHolder
        {

            private AppCompatTextView txt_dial_code,txt_title;
            private View itemView;
            private RoundedImageView img_item;
            private AppCompatImageView img_item_arrow;
            private LinearLayout ll_list_item;

            private ItemViewHolder(View itemView)
            {

                super(itemView);
                this.itemView = itemView;
                txt_dial_code= itemView.findViewById(R.id.txt_dial_code);
                txt_title = itemView.findViewById(R.id.txt_title);
                img_item =  itemView.findViewById(R.id.img_item);
                img_item_arrow =  itemView.findViewById(R.id.img_item_arrow);
                ll_list_item= itemView.findViewById(R.id.ll_list_item);

            }
        }


        private class LoadingViewHolder extends RecyclerView.ViewHolder {

            ProgressBar progressBar;

            public LoadingViewHolder(@NonNull View itemView) {
                super(itemView);
                progressBar = itemView.findViewById(R.id.progressBar);
            }
        }

        private void showLoadingView(LoadingViewHolder viewHolder, int position) {
            //ProgressBar would be displayed

        }

        public int getImageId(Context context, String imageName)
        {
            return context.getResources().getIdentifier("mipmap/" + imageName, null, context.getPackageName());
        }

        private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
            Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
            Canvas canvas = new Canvas(bmpWithBorder);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bmp, borderSize, borderSize, null);
            return bmpWithBorder;
        }

        private void populateItemRows(final ItemViewHolder viewHolder, int position) {

            CountryData countryData = countryDataArrayList_adpt.get(position);

            boolean isChecked=countryData.isChecked();


            try
            {
                String getCountry_code =countryData.getCountry_code().toLowerCase();

                if(getCountry_code.equalsIgnoreCase("do"))
                {
                    getCountry_code=getCountry_code+"_";
                }

                int id = context.getResources().getIdentifier(getCountry_code, "mipmap", getPackageName());
                //id = getImageId(context,getCountry_code);


                viewHolder.img_item.setImageResource(id);

            }
            catch (Exception e)
            {
                Log.e("countryData_selected img_item ", ""+e.toString());
            }


            if(isChecked)
            {
                viewHolder.img_item_arrow.setVisibility(ImageView.VISIBLE);
            }
            else
            {
                viewHolder.img_item_arrow.setVisibility(ImageView.INVISIBLE);
            }

            viewHolder.txt_title.setText(countryData.getCountry_name());
            viewHolder.txt_dial_code.setText(countryData.getCountry_dial_code());


            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int pos_selected= Integer.parseInt(view.getTag().toString());

                    CountryData countryData_selected = countryDataArrayList_adpt.get(pos_selected);

                    String str_countryData=  gson.toJson(countryData_selected,CountryData.class);

                    String str_getName=countryData_selected.getCountry_name();
                    Log.e("countryData_selected str_getName ", ""+str_getName);
                    bundle_data.putString(CountryConstants.ARGUMENT_1,str_countryData);

                    Intent intent=new Intent();
                    intent.putExtras(bundle_data);
                    setResult(RESULT_OK,intent);
                    onBackPressed();

                    // do_ something when the corky is clicked


                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view)
                {
                    int pos_selected= Integer.parseInt(view.getTag().toString());

                    CountryData countryData_selected = countryDataArrayList_adpt.get(pos_selected);

                    Log.e("countryData_selected getName ", ""+countryData_selected.getCountry_name());

                    return false;
                }
            });


        }

        @Override
        public Filter getFilter()
        {
            return exampleFilter;
        }
        private Filter exampleFilter = new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                List<CountryData> filteredList = new ArrayList<CountryData>();
                if (constraint == null || constraint.length() == 0)
                {
                    filteredList.addAll(countryDataArrayList_adpt_all);
                } else
                    {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (CountryData item : countryDataArrayList_adpt_all)
                    {
                        if (item.getCountry_name().toLowerCase().contains(filterPattern))
                        {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryDataArrayList_adpt.clear();
                countryDataArrayList_adpt.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Utils.hideSoftKeyboard(Country_Activity.this);

        finish();
    }
}
