package daimajia.com.hackthon;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends Activity {

    private ArrayList<Member> members = new ArrayList<>();

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return members.size();
        }

        @Override
        public Object getItem(int position) {
            return members.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(ListActivity.this).inflate(R.layout.item, null);
            TextView t = (TextView) v.findViewById(R.id.name);
            t.setText(members.get(position).leader);
            t = (TextView) v.findViewById(R.id.phone);
            t.setText(members.get(position).wechat);
            return v;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        try {
            JSONObject object = new JSONObject(getIntent().getStringExtra("data"));
            members = Member.getTeams(object.getJSONArray("list"));
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
