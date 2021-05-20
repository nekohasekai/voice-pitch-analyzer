package de.lilithwittmann.voicepitchanalyzer.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import de.lilithwittmann.voicepitchanalyzer.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment
{
    private static final String LOG_TAG = AboutFragment.class.getSimpleName();

    public AboutFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // enable text links for licences etc.
        TextView apacheLicence = (TextView) view.findViewById(R.id.apache);
        TextView apacheLicence2 = (TextView) view.findViewById(R.id.apache2);
        TextView gpl = (TextView) view.findViewById(R.id.gpl);
        TextView programming = (TextView) view.findViewById(R.id.programming);
        TextView design = (TextView) view.findViewById(R.id.design);
        TextView contributors = (TextView) view.findViewById(R.id.contributors);
        TextView github = (TextView) view.findViewById(R.id.github);

        apacheLicence.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        apacheLicence2.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        gpl.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        programming.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        design.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        contributors.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        github.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }
}
