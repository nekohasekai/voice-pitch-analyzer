package de.lilithwittmann.voicepitchanalyzer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import com.github.mikephil.charting.data.Entry;

import java.util.LinkedList;
import java.util.List;

import de.lilithwittmann.voicepitchanalyzer.R;
import de.lilithwittmann.voicepitchanalyzer.fragments.ReadingFragment;
import de.lilithwittmann.voicepitchanalyzer.fragments.RecordGraphFragment;
import de.lilithwittmann.voicepitchanalyzer.fragments.RecordingFragment;
import de.lilithwittmann.voicepitchanalyzer.models.Recording;
import de.lilithwittmann.voicepitchanalyzer.utils.PitchCalculator;

/***
 * activity containing RecordingFragment with which a new record can be made
 */

public class RecordingActivity extends AppCompatActivity implements RecordingFragment.OnFragmentInteractionListener, RecordGraphFragment.OnFragmentInteractionListener {
    private static final String LOG_TAG = RecordingActivity.class.getSimpleName();
    private FragmentTabHost tabHost;
    private PitchCalculator calculator = new PitchCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        this.addTab(ReadingFragment.class, getString(R.string.title_text));
        this.addTab(RecordGraphFragment.class, getString(R.string.realtime_graph));

        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Integer tabIndex = sharedPref.getInt(getString(R.string.recording_tab_index), 0);
        tabHost.setCurrentTab(tabIndex);
    }

    private void addTab(final Class view, String tag) {
        View tabview = this.createStyledTabView(tabHost.getContext(), tag);
        TabHost.TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview);

        tabHost.addTab(setContent, view, null);
    }

    private static View createStyledTabView(final Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);

        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text.toUpperCase());

        return view;
    }

    @Override
    public void onCancel() {
        Intent intent = new Intent(this, RecordingListActivity.class);
        startActivity(intent);
    }

    public void onPitchDetected(float pitchInHz) {
        boolean pitchAccepted = calculator.addPitch((double) pitchInHz);

        RecordGraphFragment graph = (RecordGraphFragment) getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.realtime_graph));

        if (graph != null && pitchAccepted)
            graph.addNewPitch(new Entry(pitchInHz, calculator.getPitches().size()));
    }

    @Override
    public void onRecordFinished(long recordingID) {
        savePreferences();
        ReadingFragment.incrementTextPage(this);

        Intent intent = new Intent(this, RecordViewActivity.class);
        intent.putExtra(Recording.KEY, recordingID);
        startActivity(intent);
    }

    public List<Entry> startingPitchEntries() {
        return getPitchEntries();
    }

    private List<Entry> getPitchEntries() {
        List<Entry> pitchEntries = new LinkedList<>();

        List<Double> pitches = calculator.getPitches();
        for (int i = 0; i < pitches.size(); i++) {
            Double pitchOn = pitches.get(i);
            pitchEntries.add(new Entry(pitchOn.floatValue(), i));
        }

        return pitchEntries;
    }

    private void savePreferences() {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        sharedPref.edit().putInt(getString(R.string.recording_tab_index), tabHost.getCurrentTab()).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        savePreferences();
    }
}
