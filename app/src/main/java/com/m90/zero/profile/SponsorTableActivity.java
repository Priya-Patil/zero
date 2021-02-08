package com.m90.zero.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityWalletBalanceTableBinding;
import com.m90.zero.login.LoginActivity;
import com.m90.zero.profile.adapter.SponsorTableAdapter;
import com.m90.zero.profile.api.SponsorApi;
import com.m90.zero.profile.model.SponsorResponse;
import com.m90.zero.profile.model.StateResponse;
import com.m90.zero.profile.model.WalletDetailsResponse;
import com.m90.zero.profile.model.WalletResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SponsorTableActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG="SponsorTableActivity";

    private int[] myImageList = new int[]{R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns};

    private String[] from = new String[]{"ZEEERO987634","ZEEERO987634","ZEEERO987634","ZEEERO987634","ZEEERO987634","ZEEERO987634",};
    private String[] dates = new String[]{"20-12-2020","20-12-2020","20-12-2020","20-12-2020","20-12-2020","20-12-2020",};
    private String[] rate = new String[]{"4.9", "4.9", "4.9", "4.9", "4.9", "4.9"};
    private String[] amount = new String[]{"288", "288", "288", "288", "288", "288"};

    ArrayList<WalletDetailsResponse> walletResponses;
    ArrayList<SponsorResponse> sponsorResponses;
    Activity activity;
    SponsorTableAdapter sponsorTableAdapter;

    ActivityWalletBalanceTableBinding binding;
    ProgressDialog progressDialog;

    int filter = 0;

    String sortType= "";
    private ArrayList<WalletDetailsResponse> displayedList;
    String[] sort = { "Sort","Date", "Sponsor Code", "Credit", "Debit", "Balance"};

    int userid = 0;
    static String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_balance_table);
        binding.tvTitle.setText("Level Sponsor Transaction History");
        activity = SponsorTableActivity.this;
        progressDialog = new ProgressDialog(activity);

        accessToken = Utilities.getSavedUserData(activity,"accesstoken");

        Log.e(TAG,"userID "+ Utilities.getSavedUserData(activity,"userid"));
        userid = Integer.parseInt(Utilities.getSavedUserData(activity,"userid"));

        getSponsorBalance(userid,accessToken);

        binding.ivMenu.setOnClickListener(this);
        binding.btnSearch.setOnClickListener(this);
        binding.ivAsc.setOnClickListener(this);
        binding.ivDesc.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,sort);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        binding.spinSort.setAdapter(dataAdapter);
        binding.spinSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String item = adapterView.getItemAtPosition(pos).toString();
                // Showing selected spinner item
                filter = pos;
                Log.e(TAG,"var "+filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    void filter(String text){
        Log.e(TAG,"d ddd "+text);
        ArrayList<WalletDetailsResponse> temp = new ArrayList();
        for(WalletDetailsResponse d: displayedList){
            Log.e(TAG,"d 1111 "+d);

            if(d.sponsor.sponsor_code.toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
                Log.e(TAG,"d 222 "+d);
            }
        }
        //update recyclerview
        sponsorTableAdapter.setFilter(temp);
    }

    void getSponsorBalance(int user_id,String accessToken) {

        if (Utilities.isNetworkAvailable(activity)){
        SponsorApi sponsorApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(SponsorApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sponsorApi.getSponsorBalance("Bearer "+accessToken,RetrofitClientInstance.BASE_URL+"customer/sponsor-balance/"+user_id).
        //sponsorApi.getSponsorBalance("http://api.eurekatalents.in/api/customer/sponsor-balance/"+user_id).
                enqueue(new Callback<WalletResponse>() {
                    @Override
                    public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                        WalletResponse walletResponse = response.body();
                        walletResponses = (walletResponse).data;

                        Log.e(TAG, "onResponse: "+walletResponse.toString());

                        if (walletResponse.status == 200) {
                            Toast.makeText(activity, "" + walletResponse.message, Toast.LENGTH_SHORT).show();


                            if (sortType.equals("")) {
                                attachResponse(walletResponse.data);
                            }
                            else if (sortType.equals("asc"))
                            {
                                attachAscResponse(walletResponse.data);
                            }
                            else if(sortType.equals("dsc"))
                            {
                                attachDescResponse(walletResponse.data);
                            }

                        }

                        if (walletResponse.status == 401)
                        {
                            Log.e(TAG,"onResponsezz: " + walletResponse.message);
                            authenticationDialog(walletResponse.message);
                            //Toast.makeText(activity, "" + profileResponse.message , Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<WalletResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());
                    }
                });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }

    }

    private void attachResponse(ArrayList<WalletDetailsResponse> data) {
        displayedList=data;

        binding.rvWalletBalanceTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvWalletBalanceTable.setItemAnimator(new DefaultItemAnimator());
        binding.rvWalletBalanceTable.setHasFixedSize(true);

        Log.e(TAG,""+data.get(0).balance_amount);

            //Normal
            sponsorTableAdapter = new SponsorTableAdapter(activity, data,
                    new SponsorTableAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }
                    });

        if (filter == 1) {
            //Ascending order
            ArrayList<WalletDetailsResponse> sortedList = new ArrayList();
            if (data.size() > 0) {
                Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                    @Override
                    public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                        return object1.sponsor.sponsor_code.compareTo(object2.sponsor.sponsor_code);//ascending
                        //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                    }
                });
            }

            sponsorTableAdapter = new SponsorTableAdapter(activity, sortedList,
                    new SponsorTableAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }
                    });

        }
        else if (filter==2){
            //Descending list

            ArrayList<WalletDetailsResponse> sortedList = new ArrayList();
            if (data.size() > 0) {
                Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                    @Override
                    public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                       // return object1.sponsor.sponsor_code.compareTo(object2.sponsor.sponsor_code);//ascending
                        return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                    }
                });
            }

            sponsorTableAdapter = new SponsorTableAdapter(activity, sortedList,
                    new SponsorTableAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }
                    });

        }

        binding.rvWalletBalanceTable.setAdapter(sponsorTableAdapter);

    }

    private void attachAscResponse(ArrayList<WalletDetailsResponse> data) {
        displayedList = data;

        binding.rvWalletBalanceTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvWalletBalanceTable.setItemAnimator(new DefaultItemAnimator());
        binding.rvWalletBalanceTable.setHasFixedSize(true);

        Log.e(TAG, "" + data.get(0).balance_amount);

        //Ascending order

            //Date Filter
            if (filter == 1) {
            if (data.size() > 0) {
                Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                    @Override
                    public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                        return object1.created_at.compareTo(object2.created_at);//ascending
                        //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                    }
                });
            }
        }

            //SponsorCode Filter
            else if (filter == 2) {
            if (data.size() > 0) {
                Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                    @Override
                    public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                        return object1.sponsor.sponsor_code.compareTo(object2.sponsor.sponsor_code);//ascending
                        //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                    }
                });
            }
        }

            //Credit
            else if (filter == 3) {
                if (data.size() > 0) {
                    Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                        @Override
                        public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                            return object1.credit_amount.compareTo(object2.credit_amount);//ascending
                            //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                        }
                    });
                }

            }

            //Debit
            else if (filter == 4) {
                if (data.size() > 0) {
                    Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                        @Override
                        public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                            return object1.debit_amount.compareTo(object2.debit_amount);//ascending
                            //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                        }
                    });
                }

            }

            //Balance
            else if (filter == 5) {
                if (data.size() > 0) {
                    Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                        @Override
                        public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                            return object1.balance_amount.compareTo(object2.balance_amount);//ascending
                            //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                        }
                    });
                }

            }

            sponsorTableAdapter = new SponsorTableAdapter(activity, data,
                    new SponsorTableAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                        }
                    });

            binding.rvWalletBalanceTable.setAdapter(sponsorTableAdapter);


    }

    private void attachDescResponse(ArrayList<WalletDetailsResponse> data) {
        displayedList = data;

        binding.rvWalletBalanceTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvWalletBalanceTable.setItemAnimator(new DefaultItemAnimator());
        binding.rvWalletBalanceTable.setHasFixedSize(true);

        Log.e(TAG, "" + data.get(0).balance_amount);

        //Ascending order
        ArrayList<WalletDetailsResponse> sortedList = new ArrayList();


            //Date Filter
            if (filter == 1) {
            if (data.size() > 0) {
                Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                    @Override
                    public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                        return object2.created_at.compareTo(object1.created_at);//ascending
                        //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                    }
                });
            }
        }

            //SponsorCode Filter
            else if (filter == 2) {
            if (data.size() > 0) {
                Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                    @Override
                    public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                        return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//ascending
                        //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                    }
                });
            }
        }

            //Credit
            else if (filter == 3) {
                if (data.size() > 0) {
                    Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                        @Override
                        public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                            return object2.credit_amount.compareTo(object1.credit_amount);//ascending
                            //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                        }
                    });
                }

            }

            //Debit
            else if (filter == 4) {
                if (data.size() > 0) {
                    Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                        @Override
                        public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                            return object2.debit_amount.compareTo(object1.debit_amount);//ascending
                            //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                        }
                    });
                }

            }

            //Balance
            else if (filter == 5) {
                if (data.size() > 0) {
                    Collections.sort(data, new Comparator<WalletDetailsResponse>() {
                        @Override
                        public int compare(final WalletDetailsResponse object1, final WalletDetailsResponse object2) {
                            return object2.balance_amount.compareTo(object1.balance_amount);//ascending
                            //return object2.sponsor.sponsor_code.compareTo(object1.sponsor.sponsor_code);//descending
                        }
                    });
                }

            }

            sponsorTableAdapter = new SponsorTableAdapter(activity, data,
                    new SponsorTableAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                        }
                    });

            binding.rvWalletBalanceTable.setAdapter(sponsorTableAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_menu:

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(activity,view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        switch (id)
                        {
                            case R.id.explore:
                                //Toast.makeText(mContext,"default",Toast.LENGTH_LONG).show();

                                return true;
                            case R.id.shopnow:
                                //Toast.makeText(mContext,"default",Toast.LENGTH_LONG).show();

                                return true;
                            default:
                                //Toast.makeText(mContext,"default",Toast.LENGTH_LONG).show();
                                return true;


                        }
                       // return true;
                    }
                });

                popup.show();//showing popup menu
                break;

            case R.id.btn_search:

                binding.tvSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filter(s.toString());
                    }
                });

            break;

            case R.id.iv_asc:
                sortType = "asc";
                getSponsorBalance(userid,accessToken);
                binding.ivAsc.setBackground(activity.getResources().getDrawable(R.drawable.ic_baseline_north_red_24));
                binding.ivDesc.setBackground(activity.getResources().getDrawable(R.drawable.ic_baseline_south_24));
                break;

            case R.id.iv_desc:
                sortType = "dsc";
                getSponsorBalance(userid,accessToken);
                binding.ivDesc.setBackground(activity.getResources().getDrawable(R.drawable.ic_baseline_south_red_24));
                binding.ivAsc.setBackground(activity.getResources().getDrawable(R.drawable.ic_baseline_north_24));
                break;

             case R.id.iv_back:
                    onBackPressed();
                     break;



        }
    }

    private void authenticationDialog(String errorMessage) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        mBuilder.setTitle(errorMessage);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                Utilities.launchActivity(activity, LoginActivity.class,true);

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}