package com.m90.zero.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.m90.zero.R;

import com.m90.zero.databinding.ActivityProfileBinding;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.profile.adapter.DownlineAdapter;
import com.m90.zero.profile.api.CityApi;
import com.m90.zero.profile.api.ProfileApi;
import com.m90.zero.profile.api.StateApi;
import com.m90.zero.profile.model.DownlineResponse;
import com.m90.zero.profile.model.ProfileAllDetailsResponse;
import com.m90.zero.profile.model.ProfileDetailsResponse;
import com.m90.zero.profile.model.ProfileImageUpdateResponse;
import com.m90.zero.profile.model.ProfileResponse;
import com.m90.zero.profile.model.ProfileUpdateResponse;
import com.m90.zero.profile.model.ReferalResponse;
import com.m90.zero.profile.model.StateDetailsResponse;
import com.m90.zero.profile.model.StateResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.UriUtils;
import com.m90.zero.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "ProfileActivity";
    ActivityProfileBinding binding;
    ProgressDialog progressDialog;
    Activity activity;
    ArrayList<ReferalResponse> referalEntities;
    ArrayList<ProfileDetailsResponse> profileDetailsResponses;
    ArrayList<ProfileAllDetailsResponse> profileAllDetailsResponses;
    ArrayList<DownlineResponse> downlineResponses;
    DownlineAdapter downlineAdapter;

    public static final int RequestPermissionCode = 2;

    private int GALLERY = 1, CAMERA = 2;
    String imageType, profileGetImage ;
    public Bitmap bitmap;
    String selectedFilePath1;

    private int PICK_IMAGE_REQUEST = 1;

    private int REQUEST_CAMERA = 2;

    File path, path1;
    String fullpath, fullpath2;
    Uri uri;
    ArrayList<String> a;

    boolean isImageSelected = false;

    private int[] myImageList = new int[]{R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns};

    private String[] from = new String[]{"ZEEERO987634","ZEEERO987634","ZEEERO987634","ZEEERO987634","ZEEERO987634","ZEEERO987634",};
    private String[] dates = new String[]{"20-12-2020","20-12-2020","20-12-2020","20-12-2020","20-12-2020","20-12-2020",};
    private String[] rate = new String[]{"4.9", "4.9", "4.9", "4.9", "4.9", "4.9"};
    private String[] amount = new String[]{"288", "288", "288", "288", "288", "288"};

    int userid =0 ;
    int stateId=0 , stateIdFetch = 0;
    int cityIdP = 0, cityIdFetch = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        activity = ProfileActivity.this;
        a = new ArrayList<>();

        EnableRuntimePermission();
        progressDialog = new ProgressDialog(activity);
        binding.llTab1.setOnClickListener(this);
        binding.llTab2.setOnClickListener(this);
        binding.llTab3.setOnClickListener(this);
        binding.llTab4.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.cvWalletBalance.setOnClickListener(this);
        binding.cvSponsorBalance.setOnClickListener(this);
        binding.tvEdit.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.ivEdit.setOnClickListener(this);
        binding.btnOk.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
        binding.btnOpenDrawer.setOnClickListener(this);

        //binding.civProfile.setImageResource(R.drawable.logo);
        try {
            Log.e(TAG,"userID "+ Utilities.getSavedUserData(activity,"userid"));

            userid = Integer.parseInt(Utilities.getSavedUserData(activity, "userid"));
        }catch (Exception e){}

       getProfile(userid);
      //  getProfile(61);
        //getState();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ll_tab1:
                binding.llInheritTab1.setVisibility(View.VISIBLE);
                binding.llInheritTab2.setVisibility(View.GONE);
                binding.llInheritTab3.setVisibility(View.GONE);
                binding.llInheritTab4.setVisibility(View.GONE);
                binding.view1.setVisibility(View.VISIBLE);
                binding.view2.setVisibility(View.GONE);
                binding.view3.setVisibility(View.GONE);
                binding.view4.setVisibility(View.GONE);
                break;

            case R.id.ll_tab2:
                binding.llInheritTab1.setVisibility(View.GONE);
                binding.llInheritTab2.setVisibility(View.VISIBLE);
                binding.llInheritTab3.setVisibility(View.GONE);
                binding.llInheritTab4.setVisibility(View.GONE);
                binding.view1.setVisibility(View.GONE);
                binding.view2.setVisibility(View.VISIBLE);
                binding.view3.setVisibility(View.GONE);
                binding.view4.setVisibility(View.GONE);

                break;

            case R.id.ll_tab3:

                binding.llInheritTab1.setVisibility(View.GONE);
                binding.llInheritTab2.setVisibility(View.GONE);
                binding.llInheritTab3.setVisibility(View.VISIBLE);
                binding.llInheritTab4.setVisibility(View.GONE);
                binding.view1.setVisibility(View.GONE);
                binding.view2.setVisibility(View.GONE);
                binding.view3.setVisibility(View.VISIBLE);
                binding.view4.setVisibility(View.GONE);

                break;

            case R.id.ll_tab4:

                binding.llInheritTab1.setVisibility(View.GONE);
                binding.llInheritTab2.setVisibility(View.GONE);
                binding.llInheritTab3.setVisibility(View.GONE);
                binding.llInheritTab4.setVisibility(View.VISIBLE);
                binding.view1.setVisibility(View.GONE);
                binding.view2.setVisibility(View.GONE);
                binding.view3.setVisibility(View.GONE);
                binding.view4.setVisibility(View.VISIBLE);

                break;

            case R.id.btn_submit:

                String first_name = binding.tvFname.getText().toString().trim();
                String middle_name = binding.tvMname.getText().toString().trim();
                String last_name = binding.tvLname.getText().toString().trim();
                String mobile_number = binding.tvMobile.getText().toString().trim();
                String email = binding.tvEmail.getText().toString().trim();
                String pincode = binding.tvPincode.getText().toString().trim();
                String address = binding.tvAddress.getText().toString().trim();

                Log.e(TAG, "onClick: "+mobile_number.length() );

                if ( binding.tvFname.getText().toString().equals("")||binding.tvMname.getText().toString().equals("")
                        ||binding.tvLname.getText().toString().equals("")
                    || binding.tvMobile.getText().toString().equals("")|| binding.tvEmail.getText().toString().equals("")||
                    binding.tvPincode.getText().toString().equals("")|| binding.tvAddress.getText().toString().equals("")
                    ) {
                    Toast.makeText(getApplicationContext(),"Please enter All details",Toast.LENGTH_SHORT).show();
                }
                else if (!Utilities.emailValidate(email))
                {
                    Toast.makeText(getApplicationContext(),"Please enter valid emaild Id",Toast.LENGTH_SHORT).show();
                }/*else if (isValidPhoneNumber(binding.tvMobile.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();
                }*/
                /*else if(binding.tvPincode.length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();
                }*/else {
                     updateProfile(userid, first_name, middle_name, last_name, mobile_number, email,
                           String.valueOf(stateId), String.valueOf(cityIdP), pincode, address);
                }
                break;

            case R.id.cv_walletBalance:
                    Utilities.launchActivity(activity,WalletBalanceTableActivity.class,false);
                break;

             case R.id.cv_sponsorBalance:
                    Utilities.launchActivity(activity,SponsorTableActivity.class,false);
                break;

            case R.id.tv_edit:
                    binding.llShowInfo.setVisibility(View.GONE);
                    binding.llEditInfo.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_back:
                    binding.llEditInfo.setVisibility(View.GONE);
                    binding.llShowInfo.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_edit:
                    ShowImageChooser();
                break;

         case R.id.btn_ok:
                    updateImage(userid,fullpath);
                break;

            case R.id.iv_share:
                String message = binding.txtSponsorCode.getText().toString();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share Sponsor Code"));
                break;

              case R.id.btn_openDrawer:
               onBackPressed();
                  break;


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void getProfile(int user_id) {

        ProfileApi profileApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(ProfileApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        profileApi.getProfile(user_id).
                enqueue(new Callback<ProfileResponse>() {

                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                        ProfileResponse profileResponse = response.body();
                       //ArrayList<DownlineResponse> downline = profileResponse.data.downline;

                        if (profileResponse.status== 200) {

                            //http://api.eurekatalents.in/users/default.png
                            bindValuesToTextview(profileResponse);

                            Log.e(TAG,"userrrrrrr: " + profileResponse.data.profile.get(0).user.toString());
                            Log.e(TAG,"onResponseqq: " + profileResponse.data.profile.get(0).referal.sponsor_by.username);

                            Toast.makeText(activity, "" + profileResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"onResponseaa: " + profileResponse.message);
                        } else {
                            Toast.makeText(activity, "" + profileResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"onResponsezz: " + profileResponse.message);
                        }

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.se
                        //
                        // tError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e(TAG,"errorchk getProf "+ t.getMessage());

                    }
                });


    }

    void updateProfile(int user_id,String first_name,String middle_name,String last_name,String mobile_number,
                       String email, String state_id,String city_id,String pincode,String address) {

        ProfileApi profileApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(ProfileApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e(TAG, "onClick1111: "+userid+" "+first_name+" "+middle_name+" "+last_name+" "+
                mobile_number+" "+email+" "+stateId+" "+cityIdP+" "+pincode+" "+address);

        profileApi.updateProfile(user_id,first_name,
                middle_name,last_name,mobile_number,
                email,state_id,
                city_id,pincode,address).
                enqueue(new Callback<ProfileUpdateResponse>() {

                    @Override
                    public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {

                        ProfileUpdateResponse profileResponse = response.body();
                        //ArrayList<DownlineResponse> downline = profileResponse.data.downline;
                        Log.e(TAG,"onResponseaa: " + profileResponse.toString());

                        if (profileResponse.status== 200) {

                            //http://api.eurekatalents.in/users/default.png
                              Toast.makeText(activity, "" + profileResponse.message, Toast.LENGTH_SHORT).show();
                            binding.llEditInfo.setVisibility(View.GONE);
                            binding.llShowInfo.setVisibility(View.VISIBLE);
                            Log.e(TAG,"onResponseaa: " + profileResponse.message);
                        } else {
                            Toast.makeText(activity, "" + profileResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"onResponsezz: " + profileResponse.message);
                        }

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e(TAG,"errorchk "+ t.getMessage());

                    }
                });


    }

    private void updateImage(int userid,String imgpath) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Update in Process");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        path = new File(imgpath);

        //  Log.e(TAG,"imgggg "+profileImage);
        Log.e(TAG,"imgggg "+path+" "+userid+" uri "+uri);

        okhttp3.RequestBody requestFile =
                RequestBody.create(
                       okhttp3.MediaType.parse(getContentResolver().getType(uri)),
                        path
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("profile_image", path.getName(), requestFile);

        RequestBody id = RequestBody.create(okhttp3.MediaType.parse("text/plain"),
                String.valueOf(userid));

        ProfileApi profileApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(ProfileApi.class);

        Log.e(TAG,"imgggg ddd"+id+" "+body);

        profileApi.updateProfileImage(id,body).
                enqueue(new Callback<ProfileImageUpdateResponse>() {
                    @Override
                    public void onResponse(Call<ProfileImageUpdateResponse> call,
                                           Response<ProfileImageUpdateResponse> response) {
                        ProfileImageUpdateResponse profileImageUpdateResponse =  response.body();
                        Log.e(TAG,"gggg "+profileImageUpdateResponse.toString());

                        if (profileImageUpdateResponse.status==200)
                        {
                            Toast.makeText(activity,""+profileImageUpdateResponse.message,Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(activity,""+profileImageUpdateResponse.message,Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ProfileImageUpdateResponse> call, Throwable t) {
                        Log.e(TAG," "+t.getMessage());
                        progressDialog.dismiss();
                    }
                });

    }

    private void bindValuesToTextview(ProfileResponse profileResponse) {

        binding.txtMob.setText(Utilities.getSavedUserData(activity,"mobileNumber"));
        Log.e(TAG," "+profileResponse.data.profile.get(0).first_name+" address "+
                profileResponse.data.profile.get(0).address+" mob "+
                Utilities.getSavedUserData(activity,"mobileNumber")+" "+
                Utilities.getSavedUserData(activity,"email")+" "+
                Utilities.getSavedUserData(activity,"profilePic"));

        //http://api.eurekatalents.in/storage/users/201231-Capturepro.PNG
        Utilities.saveUserData(activity,"profilePic",profileResponse.data.profile.get(0).avatar.avatar);

        binding.txtName.setText(profileResponse.data.profile.get(0).first_name+" "+
                profileResponse.data.profile.get(0).middle_name+" "+
                profileResponse.data.profile.get(0).last_name);

        binding.tvCompleteName.setText(profileResponse.data.profile.get(0).first_name+" "+
                profileResponse.data.profile.get(0).middle_name+" "+
                profileResponse.data.profile.get(0).last_name);

        binding.tvShowAddress.setText(profileResponse.data.profile.get(0).address+","+
                profileResponse.data.profile.get(0).pincode+","+
                profileResponse.data.profile.get(0).city_id.name+","+
                profileResponse.data.profile.get(0).state_id.name);

        binding.tvFname.setText(profileResponse.data.profile.get(0).first_name);
        binding.tvMname.setText(profileResponse.data.profile.get(0).middle_name);
        binding.tvLname.setText(profileResponse.data.profile.get(0).last_name);
        binding.tvMobile.setText(profileResponse.data.profile.get(0).user.mobile_number);
        binding.tvEmail.setText(profileResponse.data.profile.get(0).user.email);

        if (profileResponse.data.profile.get(0).state_id!=null) {
            stateIdFetch = profileResponse.data.profile.get(0).state_id.id;
            cityIdFetch = profileResponse.data.profile.get(0).city_id.id;
        }
        Log.e(TAG, "bindValuesToTextview: "+stateIdFetch+" "+cityIdFetch );
        String email = Utilities.getSavedUserData(activity,"email");
        ;

        Log.e(TAG,""+profileResponse.data.profile.get(0).avatar.avatar);

       CircleImageView ivBasicImage = (CircleImageView) findViewById(R.id.civ_profile);
       Picasso.with(activity).load("http://api.eurekatalents.in/"+
               profileResponse.data.profile.get(0).avatar.avatar).into(ivBasicImage);

        profileGetImage = profileResponse.data.profile.get(0).avatar.avatar;
        Log.e(TAG," profileGetImage "+profileGetImage);
        binding.txtDesc.setText(Utilities.getSavedUserData(activity,"email"));
        binding.tvAddress.setText(profileResponse.data.profile.get(0).address);
        binding.tvPincode.setText(profileResponse.data.profile.get(0).pincode);
        //binding.tvCity.setText(profileResponse.data.profile.get(0).city_id);
        //binding.tvState.setText(profileResponse.data.profile.get(0).state_id);
        binding.txtSponsorCode.setText(profileResponse.data.profile.get(0).referal.sponsor_code);

        /* Account Section */
        binding.tvAmt.setText(HomeActivity.currency+" "+profileResponse.data.profile.get(0).wallet_amount.get(0).balance_amount);
        if (profileResponse.data.profile.get(0).sponsor_amount.size()!=0)
        {
            binding.tvSponsorBalance.setText(HomeActivity.currency+" "+profileResponse.data.profile.get(0).sponsor_amount.get(0));
        }else
        {
            Log.e(TAG,"else ok");
        }

        getState();

        /*              ---------------Downline---------------              */
        setDownline(profileResponse.data.downline);


    }

    void getState() {

        StateApi stateApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(StateApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        stateApi.getState().enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                StateResponse stateResponse = response.body();
               ArrayList<StateDetailsResponse> stateDetailsResponses = stateResponse.data;

               // Log.e(TAG, "onResponse: "+stateResponse.toString());

                if (stateResponse.status == 200) {
                    Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: "+stateResponse.toString()+" "+stateDetailsResponses.toString() );

                    ArrayList<String> name = new ArrayList<>();
                    name.add("Select State");

                    for (StateDetailsResponse dd : stateDetailsResponses)
                   {
                       name.add(dd.name);
                   }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,name);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    binding.tvState.setAdapter(dataAdapter);
                    binding.tvState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                            String item = adapterView.getItemAtPosition(pos).toString();
                            // Showing selected spinner item
                            stateId = pos+1;
                            Log.e(TAG,"var a"+stateId+" fet"+stateIdFetch);

                            if (stateIdFetch!=0)
                            {
                                Log.e(TAG,"var aaaa"+stateIdFetch);
                                //stateIdFetch = stateIdFetch - 1;

                                Log.e(TAG, String.valueOf(stateIdFetch-1));

                                binding.tvState.setSelection(stateIdFetch-1);
                                getCityFromState(String.valueOf(stateIdFetch-1));

                            }
                            else {
                                getCityFromState(String.valueOf(stateId));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                    Log.e("onResponse: ", " " + stateResponse.message);
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

                progressDialog.dismiss();
                //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                Log.e("errorchkupProf", t.getMessage());

            }
        });

    }

    void getCityFromState(String cityId) {
        Log.e("onResponse:ssss ", "called");

        CityApi cityApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CityApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        cityApi.getCity("http://api.eurekatalents.in/api/city/"+cityId).
                enqueue(new Callback<StateResponse>() {

                    @Override
                    public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                        StateResponse stateResponse = response.body();
                        ArrayList<StateDetailsResponse> stateDetailsResponses = stateResponse.data;

                          if (stateResponse.status == 200) {
                                Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                                //Log.e(TAG, "onResponse: "+stateResponse.toString()+" "+stateDetailsResponses.toString() );


                             ArrayList arrayList = new ArrayList <> ();
                             arrayList.add("Select City");

                              ArrayList<StateDetailsResponse> name = new ArrayList<>();
                                for (StateDetailsResponse dd : stateDetailsResponses)
                                {
                                    StateDetailsResponse building = new StateDetailsResponse(dd.id, dd.name);
                                    name.add(building);
                                    arrayList.add(dd.name);
                                }
                                ArrayAdapter<String> dataAdapter = new
                                        ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,arrayList);

                                // Drop down layout style - list view with radio button
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                // attaching data adapter to spinner
                                binding.tvCity.setAdapter(dataAdapter);
                                binding.tvCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                                        StateDetailsResponse item = name.get(pos);
                                        // Showing selected spinner item
                                        String name  =  item.getName();
                                        cityIdP = item.getId();
                                        //cityIdP = pos+1;
                                        Log.e(TAG,"varcityp "+cityIdFetch+" "+" "+item);

                                       /* if (item.getName().contains(""))
                                        {

                                        }*/

                                       /* binding.tvCity.setSelection(item.getId());
                                       if (cityIdFetch!=0)
                                        {
                                               binding.tvSetCity.setText(stateDetailsResponses.get(cityIdFetch).name);
                                        }
                                       else {
                                            cityIdFetch = item.getId();

                                        }*/
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }
                        else {
                            Toast.makeText(activity, "" + stateResponse.message.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + new Gson().toJson(stateResponse.message));
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<StateResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }

    private void setDownline(ArrayList<DownlineResponse> downline) {
        binding.rvDownline.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvDownline.setItemAnimator(new DefaultItemAnimator());
        binding.rvDownline.setHasFixedSize(true);

        downlineAdapter = new DownlineAdapter(this, downline,
                new DownlineAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.rvDownline.setAdapter(downlineAdapter);

    }

    private void ShowImageChooser() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[item].equals("Choose from Library")) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);

                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //1 img //3 audio //4 video //5 document
        Bitmap thumbnail = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST) {

                if (data.getData() != null) {
                    uri = data.getData();
                    Log.e(TAG, "uri " + uri);

                    //fullpath= getPath(uri);
                    fullpath = UriUtils.getPathFromUri(this, uri);

                    Log.e(TAG, "fullpath " + fullpath);
                    if (fullpath.endsWith("jpg") || fullpath.endsWith("jpeg") || fullpath.endsWith("png")) {
                        //Toast.makeText(AddCategoryActivity.this, "correct", Toast.LENGTH_SHORT).show();
                        a.add(fullpath);
                    } else {
                        Toast.makeText(activity, "Select only images", Toast.LENGTH_SHORT).show();
                    }

                }

                binding.civProfile.setImageURI(uri);
                updateImage(userid,fullpath);

                //et_attach.setText("You selected total " + 1 + " Images");
            }

            else if (requestCode == REQUEST_CAMERA) {

                Bitmap photo = null;

                if(!((Bitmap) data.getExtras().get("data") == null)) {
                    photo = (Bitmap) data.getExtras().get("data");
                }else
                {

                }
                 // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
               // Uri tempUri = getImageUri(getApplicationContext(), photo);
                uri = getImageUri(getApplicationContext(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(uri));

                //System.out.println(tempUri);
                Log.d(TAG, "pthth " + uri + " final " + finalFile);

                fullpath = String.valueOf(finalFile);
                binding.civProfile.setImageURI(uri);
                updateImage(userid,fullpath);

                //et_attach.setText(fullpath);;
                Log.d(TAG, "fullpath sss  " + fullpath);
            }

        }

        Log.e("onActivityResult:2 ", a.toString() + "full " + fullpath);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Toast.makeText(activity, "Storage permission allows us to Access Storage", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, RequestPermissionCode);

        }
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;

    }

}