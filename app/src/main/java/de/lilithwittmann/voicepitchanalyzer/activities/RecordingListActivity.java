package de.lilithwittmann.voicepitchanalyzer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.lilithwittmann.voicepitchanalyzer.R;
import de.lilithwittmann.voicepitchanalyzer.fragments.RecordingListFragment;
import de.lilithwittmann.voicepitchanalyzer.fragments.WelcomeFragment;
import de.lilithwittmann.voicepitchanalyzer.models.Recording;


public class RecordingListActivity extends AppCompatActivity implements RecordingListFragment.OnFragmentInteractionListener {
    private static final String LOG_TAG = RecordingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);

        if (savedInstanceState == null) {

            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            if (sharedPref.getBoolean(getString(R.string.first_start), true))
            {
                // open welcome screen
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new WelcomeFragment())
                        .commit();

                getSupportActionBar().hide();

                // hide welcome fragment next time the app/this activity is opened
//                SharedPreferences.Editor editor = sharedPref.edit();
                sharedPref.edit().putBoolean(getString(R.string.first_start), false).apply();
            }

            else
            {
                // open list fragment
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new RecordingListFragment())
                        .commit();
            }
        }
    }

    public void onNextClick(View view)
    {
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, new RecordingFragment())
//                .commit();
        startActivity(new Intent(this, RecordingActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_record: {
                startActivity(new Intent(this, RecordingActivity.class));
                return true;
            }

            case R.id.action_progress:
            {
                startActivity(new Intent(this, ProgressActivity.class));
//                this.getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container, new ProgressFragment())
//                        .commit();
                return true;
            }

            case R.id.action_about: {
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(long recordID) {
        Intent intent = new Intent(this, RecordViewActivity.class);
        intent.putExtra(Recording.KEY, recordID);
        startActivity(intent);
    }
}
