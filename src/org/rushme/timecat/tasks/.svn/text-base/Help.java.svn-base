package org.rushme.timecat.tasks;

import org.rushme.timecat.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Help extends Fragment{ //extends Activity implements View.OnClickListener{
	public static final String ARG_MODE_NUMBER = "mode_number";
	private TextView helpInfo;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.help, container, false);
		helpInfo = (TextView)rootView.findViewById(R.id.helpInfo);
		
		setHasOptionsMenu(true);

		int preWhere = getArguments().getInt("preWhere");
		if (preWhere == 0 || preWhere == 1) {
			helpInfo.setText(R.string.help_list);
		} else if (preWhere == 2) {
			helpInfo.setText(R.string.help_stats);
		} else if (preWhere == 3) {
			helpInfo.setText(R.string.help_settings);
		} else {
			helpInfo.append(this.getResources().getString(R.string.help_list) + "\n\n");
			helpInfo.append(this.getResources().getString(R.string.help_stats) + "\n\n");
			helpInfo.append(this.getResources().getString(R.string.help_settings) + "\n\n");
		}
		
		int i = getArguments().getInt(ARG_MODE_NUMBER);
        String planet = getResources().getStringArray(R.array.Mode_array)[i];
        getActivity().setTitle(planet);

        
		return rootView;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		//currentMenu = menu;
		return super.getActivity().onCreateOptionsMenu(menu);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		//boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//boolean drawerOpen = Main.getInstance().mDrawerLayout.isDrawerOpen(Main.getInstance().expListView);
		//if (drawerOpen){
			menu.findItem(R.id.action_refresh).setVisible(!true);
			menu.findItem(R.id.action_settings).setVisible(!true);
			menu.findItem(R.id.action_group).setVisible(!true);
			menu.findItem(R.id.action_new).setVisible(!true);
			menu.findItem(R.id.action_accept).setVisible(!true);
		//}
	}
}
