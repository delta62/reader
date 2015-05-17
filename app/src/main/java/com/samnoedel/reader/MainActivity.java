package com.samnoedel.reader;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samnoedel.librss.models.RssFeed;
import com.samnoedel.librss.GetFeedTask;

import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    private static final String TAG_MAIN_ACTIVITY = "MainActivity";

    private Button mAddButton;
    private EditText mAddText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddButton = (Button)findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlText = mAddText.getText().toString();
                Resources res = getResources();
                String toastText;

                if (URLParser.isValidURL(urlText)) {
                    URL url = URLParser.parseURL(urlText);
                    toastText = getFeed(url);
                } else {
                    toastText = res.getString(R.string.toast_invalid_url);
                }

                Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
            }
        });

        mAddText = (EditText)findViewById(R.id.add_text);
        mAddText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String getFeed(URL url) {
        GetFeedTask feedTask = new GetFeedTask();
        try {
            RssFeed feed = feedTask.execute(url).get();
            if (feed == null) {
                throw feedTask.mException;
            }
            return feed.getTitle();
        } catch (InterruptedException ex) {
            return "Interrupted while fetching feed";
        } catch (ExecutionException ex) {
            return "Execution exception while fetching feed";
        } catch (Exception ex) {
            return ex.getMessage();
        }
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
}
