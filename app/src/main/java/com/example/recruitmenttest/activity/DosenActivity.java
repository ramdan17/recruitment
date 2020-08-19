package com.example.recruitmenttest.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.recruitmenttest.R;
import com.example.recruitmenttest.activity.adapter.DosenAdapter;
import com.example.recruitmenttest.activity.model.ResponseDosen;
import com.example.recruitmenttest.activity.model.SemuadosenItem;
import com.example.recruitmenttest.activity.util.api.BaseApiService;
import com.example.recruitmenttest.activity.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DosenActivity extends AppCompatActivity {

    @BindView(R.id.rvDosen)
    RecyclerView rvDosen;
    ProgressDialog loading;

    Context mContext;
    List<SemuadosenItem> semuadosenItemList = new ArrayList<>();
    DosenAdapter dosenAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydosen);

        getSupportActionBar().setTitle("Dosen");

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        dosenAdapter = new DosenAdapter(this, semuadosenItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvDosen.setLayoutManager(mLayoutManager);
        rvDosen.setItemAnimator(new DefaultItemAnimator());

        getResultListDosen();
    }

    private void getResultListDosen(){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        mApiService.getSemuaDosen().enqueue(new Callback<ResponseDosen>() {
            @Override
            public void onResponse(Call<ResponseDosen> call, Response<ResponseDosen> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<SemuadosenItem> semuaDosenItems = response.body().getSemuadosen();

                    rvDosen.setAdapter(new DosenAdapter(mContext, semuaDosenItems));
                    dosenAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDosen> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
