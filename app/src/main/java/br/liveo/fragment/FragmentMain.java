package br.liveo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;

import br.liveo.navigationliveo.R;
import br.liveo.util.Constant;
import br.liveo.util.Menus;
import br.liveo.util.Utils;

public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
	
	public FragmentMain newInstance(String text){
		FragmentMain mFragment = new FragmentMain();
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        TextView mTxtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        mTxtTitle.setText(getArguments().getString(Constant.TEXT_FRAGMENT));

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);		
		inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(Menus.SEARCH));
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(onQuerySearchView);

		menu.findItem(Menus.ADD).setVisible(true);
		menu.findItem(Menus.SEARCH).setVisible(true);		
  	    
		mSearchCheck = false;	
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {

		case Menus.ADD:
            Utils.toastShowShort(getActivity(), R.string.add);
			break;				
		
		case Menus.SEARCH:
			mSearchCheck = true;
            Utils.toastShowShort(getActivity(), R.string.search);
			break;
		}		
		return true;
	}	

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           if (mSearchCheck){
               // implement your search here
           }
           return false;
       }
   };
}
