package com.example.huuph.myship.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.rest.RestClient;
import com.example.huuph.myship.uis.activities.WebViewFabook;
import com.example.huuph.myship.uis.fragment.FragmentNews;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewLvAdapter extends ArrayAdapter<Datum> {

    private Context context;
    private int resource;
    private String idUserPost;
    private String nameUserPost = "Name";
    private Datum datas;
    private List<Datum> listData;
    private OnPostItemClickListener itemFaceListener;
    private OnPostItemClickListener itemCallListener;
    private OnPostItemClickListener itemSaveListener;
    private String token;

    public NewLvAdapter(@NonNull Context context, int resource, @NonNull List<Datum> objects
            , OnPostItemClickListener listenerFace
            , OnPostItemClickListener listenerCall
            , OnPostItemClickListener listenerSave
            , String token) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listData = objects;
        this.token = token;

        this.itemFaceListener = listenerFace;
        this.itemCallListener = listenerCall;
        this.itemSaveListener = listenerSave;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPost = convertView.findViewById(R.id.tvPost);
            viewHolder.tvUser = convertView.findViewById(R.id.tvName);
            viewHolder.tvTimePost = convertView.findViewById(R.id.tvTimePost);
            viewHolder.btBinhLuon = convertView.findViewById(R.id.btBinhLuan);
            viewHolder.btCall = convertView.findViewById(R.id.btCall);
            viewHolder.btSave = convertView.findViewById(R.id.btSave);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Datum dataNew = listData.get(position);
     //   getUserInfo(viewHolder.tvUser, dataNew.getPostid(), token);
        viewHolder.tvPost.setText(dataNew.getMessage());
        viewHolder.tvTimePost.setText(dataNew.getUpdatedTime());
        viewHolder.tvUser.setText(dataNew.getName());
        //add listener
        viewHolder.btBinhLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemFaceListener.onPostItemClick(position);
                // Toast.makeText(context, "adsad"+position, Toast.LENGTH_SHORT).show();
            }
        });
        //add listener
        viewHolder.btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCallListener.onPostItemClick(position);
            }
        });
        viewHolder.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSaveListener.onPostItemClick(position);
            }
        });
        return convertView;

    }


    public class ViewHolder {
        TextView tvUser;
        TextView tvPost;
        TextView tvTimePost;
        Button btBinhLuon;
        Button btCall;
        Button btSave;
    }

    public interface OnPostItemClickListener {
        void onPostItemClick(int pos);
    }
//
//    public void getUserInfo(final TextView tv, String idfeed, String tokens) {
//        Call<JsonElement> jsonElementCall = RestClient.getAPIs().getUserid(idfeed, "from", tokens);
//        jsonElementCall.enqueue(new Callback<JsonElement>() {
//            @Override
//            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                JsonElement jsonElement = response.body();
//                JsonObject jsonObject1 = jsonElement.getAsJsonObject();
//
//                if (jsonObject1.getAsJsonObject("from") != null) {
//                    JsonObject from = jsonObject1.getAsJsonObject("from");
//                    String name = from.get("name").getAsString();
//                    Log.d("names", name);
//                    nameUserPost = name;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonElement> call, Throwable t) {
//                Log.d("TAG", "fail");
//            }
//        });
//        tv.setText(nameUserPost);
//    }
}
