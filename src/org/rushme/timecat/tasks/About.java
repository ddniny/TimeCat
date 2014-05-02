package org.rushme.timecat.tasks;

import org.rushme.timecat.R;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class About extends Fragment{
	public static final String ARG_MODE_NUMBER = "mode_number";
	private TextView about;
	private String content;
	private String author, contributors, licensing;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.about, container, false);
		int i = getArguments().getInt(ARG_MODE_NUMBER);
        String planet = getResources().getStringArray(R.array.Mode_array)[i];
        getActivity().setTitle(planet);
        setHasOptionsMenu(true);

        author = "Danni Wu";
        contributors = "Prof. Florin B. Manolache";
        licensing = "";
        content = "Author: " + author + "\n\n" + 
        		  "Contributors: " + contributors + "\n\n" + 
        		  "Licensing: " + licensing + "\n\n" + 
        		  "Version: " + getVersion() + "\n\n\n\n\n\n\n\n";
        about = (TextView) rootView.findViewById(R.id.about);
    	about.setText(content);

		return rootView;
	}
	
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = this.getActivity().getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getActivity().getPackageName(), 0);
	        String version = info.versionName;
	        return this.getString(R.string.app_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return this.getString(R.string.can_not_find_version_name);
	    }
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
