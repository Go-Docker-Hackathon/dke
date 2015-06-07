package daimajia.com.hackthon;

import android.content.Intent;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.FrameworkSampleSource;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.SampleSource;
import com.google.android.exoplayer.TrackRenderer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.alfred).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alfred:
                EditText text = (EditText) findViewById(R.id.command);
                if (text.getText().length() == 0) {
                    Toast.makeText(this, "得先下指令哟", Toast.LENGTH_LONG).show();
                    return;
                }

                String command = text.getText().toString();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("command", command);
                client.post(this, "http://daimajia-batman.daoapp.io/mission", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Result", response.toString());
                        try {
                            if (!response.getBoolean("err")) {
                                handleData(response.getJSONObject("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        throwable.printStackTrace();
                    }
                });
                break;
        }
    }

    public void handleData(JSONObject data) throws JSONException {
        if (data.getString("type").equals("music")) {
            playMusic(data);
        }
        if (data.getString("type").equals("list")) {
            showList(data);
        }
    }

    public void showList(JSONObject obj) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("data", obj.toString());
        startActivity(intent);
    }

    public void playMusic(JSONObject obj) throws JSONException {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        JSONObject music = obj.getJSONArray("musics").getJSONObject(0);
        String name = music.getString("name");
        String authro = music.getString("author");
        String url = music.getString("url");
        String cover = music.getString("cover");
        Glide.with(this).load(cover).into((ImageView) findViewById(R.id.cover));
        Uri uri = Uri.parse("http://youku.gank.io:3001/?url=" + url);
        SampleSource sampleSource =
                new FrameworkSampleSource(this, uri, null, 2);
        TrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(sampleSource, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        TrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
        exoPlayer = ExoPlayer.Factory.newInstance(2);
        exoPlayer.prepare(videoRenderer, audioRenderer);
        // Pass the surface to the video renderer.
        exoPlayer.sendMessage(videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, null);
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        super.onDestroy();
    }
}
