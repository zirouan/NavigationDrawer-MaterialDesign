
package br.liveo.navigationliveo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.liveo.adapter.NavigationAdapter;
import br.liveo.fragment.FragmentMain;
import br.liveo.util.Constant;
import br.liveo.util.Menus;
import br.liveo.util.Utils;

public class NavigationMain extends ActionBarActivity {

    private TextView mUserName;
    private TextView mUserEmail;
    private ImageView mUserPhoto;
    private ImageView mUserBackground;

    private ListView mList;
    private int mLastPosition = 2;
	private DrawerLayout mDrawerLayout;
    private RelativeLayout mFooterDrawer;
    private RelativeLayout mRelativeDrawer;

	private FragmentManager mFragmentManager;
	private NavigationAdapter mNavigationAdapter;
	private ActionBarDrawerToggleCompat mDrawerToggle;	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_main);

        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(new DrawerItemClickListener());

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggleCompat(this, mDrawerLayout, mToolbar);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mFooterDrawer = (RelativeLayout) this.findViewById(R.id.footerDrawer);
        mFooterDrawer.setOnClickListener(onClickFooterDrawer);

        mRelativeDrawer = (RelativeLayout) this.findViewById(R.id.relativeDrawer);

		if (mList != null) {
            mountListNavigation();
		}

		if (savedInstanceState != null) { 			
			setLastPosition(savedInstanceState.getInt(Constant.LAST_POSITION));

			mNavigationAdapter.resetarCheck();		
			mNavigationAdapter.setChecked(mLastPosition, true);							
	    }else{
            showSelectedFragment(mLastPosition);
	    }

        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	private void showSelectedFragment(int position){

        if (position != 0){
            int mPosition = (position-1);//-1 Refers to the index 0 of the list header

            Fragment mFragment;
            mFragmentManager = getSupportFragmentManager();

            mFragment = new FragmentMain().newInstance(Utils.getTitleItem(NavigationMain.this, mPosition));

            if (mFragment != null){
                setLastPosition(mPosition);
                mNavigationAdapter.resetarCheck();
                mNavigationAdapter.setChecked(mPosition, true);
                mFragmentManager.beginTransaction().replace(R.id.frame_container, mFragment).commit();
            }
        }
	}

    private void hideMenus(Menu menu, int position) {
    	    	
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
    	
        switch (position) {
		case Constant.INBOX:
	        menu.findItem(Menus.ADD).setVisible(!drawerOpen);
	        menu.findItem(Menus.SEARCH).setVisible(!drawerOpen);	        
			break;
			
		case Constant.STARRED:
	        menu.findItem(Menus.ADD).setVisible(!drawerOpen);	
	        menu.findItem(Menus.SEARCH).setVisible(!drawerOpen);	        
			break;			
		}  
    }	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub		
		super.onSaveInstanceState(outState);		
		outState.putInt(Constant.LAST_POSITION, mLastPosition);					
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle != null) {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	hideMenus(menu, mLastPosition);
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
	 }

	private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle {

        public ActionBarDrawerToggleCompat(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar){
            super(
                    activity,
                    drawerLayout, toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close);
        }

		@Override
		public void onDrawerClosed(View view) {			
			supportInvalidateOptionsMenu();				
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			supportInvalidateOptionsMenu();			
		}		
	}
		  
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showSelectedFragment(position);
	    	mDrawerLayout.closeDrawer(mRelativeDrawer);
        }
    }

    public void setLastPosition(int position){
        this.mLastPosition = position;
    }

    private OnClickListener onClickUserPhoto = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
            Utils.toastShowShort(getApplicationContext(), R.string.open_user_profile);
			mDrawerLayout.closeDrawer(mRelativeDrawer);
		}
	};

    private OnClickListener onClickFooterDrawer = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.toastShowShort(getApplicationContext(), R.string.open_settings);
            mDrawerLayout.closeDrawer(mRelativeDrawer);
        }
    };

    private void mountListNavigation(){

        //Header list navigation
        mountListHeader();

        List<Integer> mListHeader = new ArrayList<>();
        mListHeader.add(Constant.MORE_MARKERS);

        SparseIntArray  mSparseCounter = new SparseIntArray();
        mSparseCounter.put(Constant.INBOX, 7);
        mSparseCounter.put(Constant.SPAM, 10);

        mNavigationAdapter = new NavigationAdapter(this, NavigationList.getNavigationAdapter(this, mListHeader, mSparseCounter));
        mList.setAdapter(mNavigationAdapter);
    }

    private void mountListHeader(){

        View mHeader = getLayoutInflater().inflate(R.layout.navigation_list_header, mList, false);

        mUserName = (TextView) mHeader.findViewById(R.id.userName);
        mUserEmail = (TextView) mHeader.findViewById(R.id.userEmail);

        mUserPhoto = (ImageView) mHeader.findViewById(R.id.userPhoto);
        mUserPhoto.setImageResource(R.drawable.ic_rudsonlive);
        mUserPhoto.setOnClickListener(onClickUserPhoto);

        mUserBackground = (ImageView) mHeader.findViewById(R.id.userBackground);
        mUserBackground.setImageResource(R.drawable.ic_user_background);

        mUserName.setText("Rudson Lima");
        mUserEmail.setText("rudsonlive@gmail.com");

        mList.addHeaderView(mHeader);
    }
}
