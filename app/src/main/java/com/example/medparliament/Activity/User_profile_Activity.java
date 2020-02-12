package  com.example.medparliament.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
public class User_profile_Activity extends Base_Activity implements onResult {
    MySharedPrefrence m;
     onResult onResult;
    LinearLayout l1,l2,l3,l4,l5,l6,l7,new_linearLayout,l3_new,l5_new;
    ImageView profile_category_image,home1,new_image;
    ImageButton bck;
    Segow_UI_Semi_Font expertises ,userName,u_address,userEmail,i_name,u_name,universityAddress,mobile,qualification,batch,interest,new_location;
    Segow_UI_Semi_Font l_expert,l_u_add, l_i_name,l_u_name,l_university_name,l_mobile,l_qualifiacation,l_batch_qualification,l_interest,l_new_location;
    ImageView edit;
    ProgressDialog progressDialog;
    CircleImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.onResult=this;
        m=MySharedPrefrence.instanceOf(User_profile_Activity.this);
        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Animatoo.animateSwipeLeft(User_profile_Activity.this);
        profileImage=findViewById(R.id.profile_image);
        progressDialog = new ProgressDialog(User_profile_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        userName=findViewById(R.id.user_name);
        userEmail=findViewById(R.id.user_email);
        new_linearLayout=findViewById(R.id.new_l3);
        l5_new=findViewById(R.id.l5_new);
        l_new_location=findViewById(R.id.new_l_u_location);
        new_location=findViewById(R.id.new_location);
        new_image=findViewById(R.id.new_location_img);
        profile_category_image=findViewById(R.id.profile_category_image);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        l5=findViewById(R.id.l5);
        l6=findViewById(R.id.l6);
        l7=findViewById(R.id.l7);
        l3_new =findViewById(R.id.l3_new);
        home1=findViewById(R.id.home1);
        l_expert=findViewById(R.id.l_expert);
        expertises =findViewById(R.id.expertises);
        l_u_add=findViewById(R.id.l_u_add);
        l_i_name=findViewById(R.id.l_i_name);
        l_u_name=findViewById(R.id.l_u_name);
        l_mobile=findViewById(R.id.l_mobile);
        l_university_name=findViewById(R.id.l_u_location);
        l_qualifiacation=findViewById(R.id.l_qualification);
        l_batch_qualification=findViewById(R.id.l_bach_of_qualification);
        l_interest=findViewById(R.id.l_interest);
        i_name=findViewById(R.id.i_name);
        interest=findViewById(R.id.interest);
        qualification=findViewById(R.id.oqualifiaction);
        batch=findViewById(R.id.batch);
        u_name=findViewById(R.id.u_name);
        u_address=findViewById(R.id.u_address);
        universityAddress=findViewById(R.id.universityAddress);
        mobile=findViewById(R.id.mobile);
        bck=findViewById(R.id.bck);
        edit=findViewById(R.id.edit);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_profile_Activity.this,Edit_Profile_Activity.class));
            }
        });
//        if(Api_Calling.QualificationList.size()==0)
//            Api_Calling.getQualificationListData(User_profile_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_QUALIFICATION);
//        if(Api_Calling.UniversityList.size()==0)
//            Api_Calling.getUniversityListData(User_profile_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_UNIVERSITY);
//        Api_Calling.getStudentIntrestList(User_profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon("1"));
    }
    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall(User_profile_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.USER_PROFILE,myPostJson(),"USer_Profile");
    }

    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(User_profile_Activity.this);
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        Log.d("user",jsonObject.toString());


        if(progressDialog!=null &&  progressDialog.isShowing())
        progressDialog.dismiss();
        ArrayList <String>arrayList=new ArrayList<>();
        if(jsonObject!=null && status)
        {
            JSONObject jo=new JSONObject();
            try {
                if(m.getUserTypeId().equalsIgnoreCase("9")){

                    jo=jsonObject.getJSONObject("result").getJSONArray("userProfile").getJSONObject(0);
                    new_linearLayout.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                    l3_new.setVisibility(View.VISIBLE);

                    userName.setText(Comman.getValueFromJsonObject(jo,"userName"));
                    userEmail.setText(Comman.getValueFromJsonObject(jo,"email"));
                    l_i_name.setText(getResources().getString(R.string.Comapny_Name));
                    i_name.setText(Comman.getValueFromJsonObject(jo,"companyName"));
                    l_university_name.setText(getResources().getString(R.string.CompanyAddress));
                    universityAddress.setText(Comman.getValueFromJsonObject(jo,"companyAddress"));
                    l_mobile.setText(getResources().getString(R.string.Mobile_Number));
                    mobile.setText(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    l_qualifiacation.setText(getResources().getString(R.string.Occupation));
                    qualification.setText(Comman.getValueFromJsonObject(jo,"occupation"));

                    l_batch_qualification.setText(getResources().getString(R.string.Designation));
                    batch.setText(Comman.getValueFromJsonObject(jo,"designation"));
                    l_u_add.setText(getResources().getString(R.string.Address));
                   u_address.setText(Comman.getValueFromJsonObject(jo,"address"));
                    l_interest.setText(getResources().getString(R.string.Interests));














//                    if(!isFinishing())
//                        Comman.setRoundedImage(User_profile_Activity.this,profileImage,Comman.getValueFromJsonObject(jo,"profilePic"));

                    if(jo.getJSONArray("userInterest")!=null && jo.getJSONArray("userInterest").length()>0)
                        for (int i=0;i<jo.getJSONArray("userInterest").length();i++)
                        {
                            arrayList.add(String.valueOf(jo.getJSONArray("userInterest").get(i)));
                        }
                    if(arrayList!=null && arrayList.size()>0)
                        interest.setText(arrayList.toString().replace("[","").replace("]",""));

                    m.setUserName(Comman.getValueFromJsonObject(jo,"userName"));
                    m.setUserEmail(Comman.getValueFromJsonObject(jo,"email"));
                    m.setCompanyName(Comman.getValueFromJsonObject(jo,"companyName"));
                    m.setUniversityAddress(Comman.getValueFromJsonObject(jo,"companyAddress"));
                    m.setMobili(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    m.setUserPersonalAddress(Comman.getValueFromJsonObject(jo,"address"));
                    m.setDasignation(Comman.getValueFromJsonObject(jo,"designation"));
                    m.setQualication(Comman.getValueFromJsonObject(jo,"occupation"));
                    m.setInterest(arrayList.toString().replace("[","").replace("]",""));
                }
               else   if(m.getUserTypeId().equalsIgnoreCase("8")){

                    jo=jsonObject.getJSONObject("result").getJSONArray("userProfile").getJSONObject(0);
                    new_linearLayout.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                    l5_new.setVisibility(View.VISIBLE);

                    userName.setText(Comman.getValueFromJsonObject(jo,"userName"));
                    userEmail.setText(Comman.getValueFromJsonObject(jo,"email"));
                    l_i_name.setText(getResources().getString(R.string.institute));
                    i_name.setText(Comman.getValueFromJsonObject(jo,"hospital"));
                    l_university_name.setText(getResources().getString(R.string.hos_add));
                    universityAddress.setText(Comman.getValueFromJsonObject(jo,"hospitalAddress"));
                    l_mobile.setText(getResources().getString(R.string.Mobile_Number));
                    mobile.setText(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    l_qualifiacation.setText(getResources().getString(R.string.Qualification));
                    qualification.setText(Comman.getValueFromJsonObject(jo,"qualificationName"));

                    l_batch_qualification.setText(getResources().getString(R.string.Designation));
                    batch.setText(Comman.getValueFromJsonObject(jo,"designation"));
                    l_expert.setText(getResources().getString(R.string.AreaofExperites));
                    expertises.setText(Comman.getValueFromJsonObject(jo,"areaOfExpertise"));

                    l_interest.setText(getResources().getString(R.string.Interests));














//                    if(!isFinishing())
//                        Comman.setRoundedImage(User_profile_Activity.this,profileImage,Comman.getValueFromJsonObject(jo,"profilePic"));

                    if(jo.getJSONArray("userInterest")!=null && jo.getJSONArray("userInterest").length()>0)
                        for (int i=0;i<jo.getJSONArray("userInterest").length();i++)
                        {
                            arrayList.add(String.valueOf(jo.getJSONArray("userInterest").get(i)));
                        }
                    if(arrayList!=null && arrayList.size()>0)
                        interest.setText(arrayList.toString().replace("[","").replace("]",""));

                    m.setUserName(Comman.getValueFromJsonObject(jo,"userName"));
                    m.setUserEmail(Comman.getValueFromJsonObject(jo,"email"));
                    m.setCompanyName(Comman.getValueFromJsonObject(jo,"hospital"));
                    m.setUniversityAddress(Comman.getValueFromJsonObject(jo,"hospitalAddress"));
                    m.setMobili(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    m.setUserPersonalAddress(Comman.getValueFromJsonObject(jo,"areaOfExpertise"));
                    m.setDasignation(Comman.getValueFromJsonObject(jo,"designation"));
                    m.setQualication(Comman.getValueFromJsonObject(jo,"qualificationName"));
                    m.setInterest(arrayList.toString().replace("[","").replace("]",""));
                }
                else if(m.getUserTypeId().equalsIgnoreCase("7")){
                jo=jsonObject.getJSONObject("result").getJSONArray("userProfile").getJSONObject(0);
                    new_linearLayout.setVisibility(View.GONE);
                    l_new_location.setText(getResources().getString(R.string.Address));
                    l_i_name.setText(getResources().getString(R.string.Institute_Name));
                    l_u_name.setText(getResources().getString(R.string.University));
                    l_university_name.setText(getResources().getString(R.string.University_Address));
                    l_mobile.setText(getResources().getString(R.string.Mobile_Number));
                    l_qualifiacation.setText(getResources().getString(R.string.Qualification));
                    l_batch_qualification.setText(getResources().getString(R.string.Batch_of_Qualification));
                    l_interest.setText(getResources().getString(R.string.Interests));
                    userName.setText(Comman.getValueFromJsonObject(jo,"userName"));
                    userEmail.setText(Comman.getValueFromJsonObject(jo,"email"));
                    i_name.setText(Comman.getValueFromJsonObject(jo,"institutionName"));
                    u_name.setText(Comman.getValueFromJsonObject(jo,"universityName"));
                    new_location.setText(Comman.getValueFromJsonObject(jo,"address"));
                    universityAddress.setText(Comman.getValueFromJsonObject(jo,"universityAddress"));
                    mobile.setText(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    if(!isFinishing())
                        Comman.setRoundedImage(User_profile_Activity.this,profileImage,Comman.getValueFromJsonObject(jo,"profilePic"));
                    batch.setText(Comman.getValueFromJsonObject(jo,"batchOfQualification"));
                    if(jo.getJSONArray("userInterest")!=null && jo.getJSONArray("userInterest").length()>0)
                        for (int i=0;i<jo.getJSONArray("userInterest").length();i++)
                        {
                            arrayList.add(String.valueOf(jo.getJSONArray("userInterest").get(i)));
                        }
                    if(arrayList!=null && arrayList.size()>0)
                        interest.setText(arrayList.toString().replace("[","").replace("]",""));
                     qualification.setText(Comman.getValueFromJsonObject(jo,"qualificationName"));
                    m.setUserName(Comman.getValueFromJsonObject(jo,"userName"));
                    m.setUserEmail(Comman.getValueFromJsonObject(jo,"email"));
                    m.setInstituteName(Comman.getValueFromJsonObject(jo,"institutionName"));
                    m.setUniversityName(Comman.getValueFromJsonObject(jo,"universityName"));
                    m.setUniversityAddress(Comman.getValueFromJsonObject(jo,"universityAddress"));
                    m.setMobili(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    m.setUserPersonalAddress(Comman.getValueFromJsonObject(jo,"address"));
                    m.setQualicationBatch(Comman.getValueFromJsonObject(jo,"batchOfQualification"));
                    m.setQualication(Comman.getValueFromJsonObject(jo,"qualificationName"));
                    m.setInterest(arrayList.toString().replace("[","").replace("]",""));
                }
                else if(m.getUserTypeId().equalsIgnoreCase("5")){
                         if(!isFinishing())
                    Comman.setRoundedImage(User_profile_Activity.this,profileImage,Comman.getValueFromJsonObject(jo,"profilePic"));
                    l5.setVisibility(View.GONE);
                    profile_category_image.setImageResource(R.drawable.ic_man_1);
                    l7.setVisibility(View.GONE);
                    home1.setImageResource(R.drawable.ic_building);
                    jo=jsonObject.getJSONObject("result").getJSONArray("userProfile").getJSONObject(0);
                    l_i_name.setText(getResources().getString(R.string.Designation));
                    l_u_name.setText(getResources().getString(R.string.Organisation));
                    l_mobile.setText(getResources().getString(R.string.Mobile_Number));
                    l_university_name.setText(getResources().getString(R.string.Country));
                    l_batch_qualification.setText(getResources().getString(R.string.About_Your_Profile));
                    userName.setText(Comman.getValueFromJsonObject(jo,"userName"));
                    userEmail.setText(Comman.getValueFromJsonObject(jo,"email"));
                    i_name.setText(Comman.getValueFromJsonObject(jo,"designation"));
                    batch.setText(Comman.getValueFromJsonObject(jo,"aboutProfile"));
                    mobile.setText(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    u_name.setText(Comman.getValueFromJsonObject(jo,"organization"));
                    universityAddress.setText(Comman.getValueFromJsonObject(jo,"countryName"));
                    m.setUserName(Comman.getValueFromJsonObject(jo,"userName"));
                    m.setUserEmail(Comman.getValueFromJsonObject(jo,"email"));
                    m.setDasignation(Comman.getValueFromJsonObject(jo,"designation"));
                    m.setOrganization(Comman.getValueFromJsonObject(jo,"organization"));
                    m.setMobili(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    m.setCountry(Comman.getValueFromJsonObject(jo,"countryName"));
                    m.setDescription(Comman.getValueFromJsonObject(jo,"aboutProfile"));
                }
                else if(m.getUserTypeId().equalsIgnoreCase("6")){
                    jo=jsonObject.getJSONObject("result").getJSONArray("userProfile").getJSONObject(0);
                    if(!isFinishing())
                        Comman.setRoundedImage(User_profile_Activity.this,profileImage,Comman.getValueFromJsonObject(jo,"profilePic"));
                    l2.setVisibility(View.GONE);
                    l5.setVisibility(View.GONE);
                    new_linearLayout.setVisibility(View.GONE);
                    l_new_location.setText(getResources().getString(R.string.Comapny_Name));
                    new_image.setImageResource(R.drawable.ic_building);
                    l_i_name.setText(getResources().getString(R.string.Designation));
                    l_mobile.setText(getResources().getString(R.string.Mobile_Number));
                    l_interest.setText(getResources().getString(R.string.Interests));
                    l_university_name.setText(getResources().getString(R.string.Area_of_Activity));
                    l_batch_qualification.setText(getResources().getString(R.string.Profile_Category));
                    userName.setText(Comman.getValueFromJsonObject(jo,"userName"));
                    userEmail.setText(Comman.getValueFromJsonObject(jo,"email"));
                    i_name.setText(Comman.getValueFromJsonObject(jo,"designation"));
                    batch.setText(Comman.getValueFromJsonObject(jo,"profileCategory"));
                    universityAddress.setText(Comman.getValueFromJsonObject(jo,"areaOfActivity"));
                    new_location.setText(Comman.getValueFromJsonObject(jo,"companyName"));
                    mobile.setText(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    profile_category_image.setImageResource(R.drawable.ic_man_1);
                    if(jo.getJSONArray("userInterest")!=null && jo.getJSONArray("userInterest").length()>0)
                        for (int i=0;i<jo.getJSONArray("userInterest").length();i++)
                        {
                            arrayList.add(String.valueOf(jo.getJSONArray("userInterest").get(i)));
                        }
                    if(arrayList!=null && arrayList.size()>0)
                        interest.setText(arrayList.toString().replace("[","").replace("]",""));
                    m.setUserName(Comman.getValueFromJsonObject(jo,"userName"));
                    m.setUserEmail(Comman.getValueFromJsonObject(jo,"email"));
                    m.setDasignation(Comman.getValueFromJsonObject(jo,"designation"));
                    m.setMobili(Comman.getValueFromJsonObject(jo,"mobileNo"));
                    m.setDescription(Comman.getValueFromJsonObject(jo,"areaOfActivity"));
                    m.setProfileCategory(Comman.getValueFromJsonObject(jo,"profileCategory"));
                    m.setCompanyName(Comman.getValueFromJsonObject(jo,"companyName"));
                    m.setInterest(arrayList.toString().replace("[","").replace("]",""));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public JSONObject interestJSon(String id)
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",""+id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Interst json",""+jsonObject);
        return jsonObject;
    }
}
