package com.example.recruitmenttest.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmenttest.R;
import com.example.recruitmenttest.activity.adapter.MatkulAdapter;
import com.example.recruitmenttest.activity.model.ResponseMatkul;
import com.example.recruitmenttest.activity.model.SemuamatkulItem;
import com.example.recruitmenttest.activity.util.Constant;
import com.example.recruitmenttest.activity.util.RecyclerItemClickListener;
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

public class MatkulActivity extends AppCompatActivity {

    @BindView(R.id.btnTambahMatkul)
    Button btnTambahMatkul;
    @BindView(R.id.tvBelumMatkul)
    TextView tvBelumMatkul;
    @BindView(R.id.rvMatkul)
    RecyclerView rvMatkul;
    ProgressDialog loading;

    Context mContext;
    List<SemuamatkulItem> semuamatkulItemList = new ArrayList<>();
    MatkulAdapter matkulAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymatkul);

        getSupportActionBar().setTitle("Mata Kuliah");

        ButterKnife.bind(this);
        mApiService = UtilsApi.getAPIService();
        mContext = this;

        matkulAdapter = new MatkulAdapter(this, semuamatkulItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMatkul.setLayoutManager(mLayoutManager);
        rvMatkul.setItemAnimator(new DefaultItemAnimator());

        getDataMatkul();

        btnTambahMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatkulActivity.this, TambahMatkulActivity2.class));
            }
        });
    }

    private void getDataMatkul(){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getSemuaMatkul().enqueue(new Callback<ResponseMatkul>() {
            @Override
            public void onResponse(Call<ResponseMatkul> call, Response<ResponseMatkul> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    if (response.body().isError()) {
                        tvBelumMatkul.setVisibility(View.VISIBLE);
                    } else {
                        final List<SemuamatkulItem> semuamatkulItems = response.body().getSemuamatkul();
                        rvMatkul.setAdapter(new MatkulAdapter(mContext, semuamatkulItems));
                        matkulAdapter.notifyDataSetChanged();

                        initDataIntent(semuamatkulItems);
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mata kuliah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMatkul> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<SemuamatkulItem> matkulList){
        rvMatkul.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String id = matkulList.get(position).getId();
                        String namadosen = matkulList.get(position).getNamaDosen();
                        String matkul = matkulList.get(position).getMatkul();

                        Intent detailMatkul = new Intent(mContext, MatkulDetailActivity.class);
                        detailMatkul.putExtra(Constant.KEY_ID_MATKUL, id);
                        detailMatkul.putExtra(Constant.KEY_NAMA_DOSEN, namadosen);
                        detailMatkul.putExtra(Constant.KEY_MATKUL, matkul);
                        startActivity(detailMatkul);
                    }
                }));
    }
}
