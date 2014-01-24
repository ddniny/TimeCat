package org.rushme.timecat.tasks;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class settings extends Fragment{ //extends Activity implements View.OnClickListener{
	public static final String ARG_MODE_NUMBER = "mode_number";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings, container, false);
		int i = getArguments().getInt(ARG_MODE_NUMBER);
        String planet = getResources().getStringArray(R.array.Mode_array)[i];
        getActivity().setTitle(planet);

		return rootView;
	}

}
