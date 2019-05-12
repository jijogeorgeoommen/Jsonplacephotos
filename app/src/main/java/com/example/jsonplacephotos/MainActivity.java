package com.example.jsonplacephotos;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    RequestParams params;

    ArrayList<String>albumidlist;
    ArrayList<String>idlist;
    ArrayList<String>titlelist;
    ArrayList<String>urllist;

    String url="https://jsonplaceholder.typicode.com/photos";

    ListView listviewphotos;
    LayoutInflater inflte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewphotos=findViewById(R.id.photos);

        client=new AsyncHttpClient();
        params=new RequestParams();

        albumidlist=new ArrayList<String>();
        idlist=new ArrayList<String>();
        titlelist=new ArrayList<String>();
       urllist=new ArrayList<String>();

        client.get(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try{
                    JSONArray jarray=new JSONArray(content);
                    for (int i=0;i<jarray.length();i++){
                        JSONObject jobj=jarray.getJSONObject(i);
                        String albumidp=jobj.getString("albumId");
                        albumidlist.add("Album id:"+albumidp);
                        String idp=jobj.getString("id");
                        idlist.add("Id :"+idp);
                        String titlep=jobj.getString("title");
                        titlelist.add(titlep);
                        String urlp=jobj.getString("thumbnailUrl");
                        urllist.add(urlp);
                    }

                    Adapter adp=new Adapter();
                    listviewphotos.setAdapter(adp);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return albumidlist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflate=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflate.inflate(R.layout.photosxml,null);

            TextView albumidtxt=convertView.findViewById(R.id.albumidxml);
            TextView idtxt=convertView.findViewById(R.id.idxml);
            TextView titletxt=convertView.findViewById(R.id.titlexml);
           ImageView urlimage=convertView.findViewById(R.id.urlxml);

            albumidtxt.setText(albumidlist.get(position));
            idtxt.setText(idlist.get(position));
            titletxt.setText(titlelist.get(position));

            Glide.with(MainActivity.this).asBitmap().load(urllist.get(position)).into(urlimage);

           // Picasso.with(getApplicationContext()).load(urllist.get(position)).into(urlimage);


            return convertView;
        }
    }
}
