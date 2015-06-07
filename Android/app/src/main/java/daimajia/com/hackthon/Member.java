package daimajia.com.hackthon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Member {
    public final String name, leader, wechat, count;

    public Member(JSONObject object) throws JSONException {
        name = object.getString("name");
        leader = object.getString("leader");
        wechat = object.getString("wechat");
        count = object.getString("cnt");
    }

    public static ArrayList<Member> getTeams(JSONArray array) throws JSONException {
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            members.add(new Member(array.getJSONObject(i)));
        }
        return members;
    }
}
