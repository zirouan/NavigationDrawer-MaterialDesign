/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.liveo.navigationliveo;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.liveo.adapter.NavigationLiveoAdapter;
import br.liveo.interfaces.NavigationLiveoListener;

public abstract class NavigationLiveo extends ActionBarActivity {

    public TextView mUserName;
    public TextView mUserEmail;
    public ImageView mUserPhoto;
    public ImageView mUserBackground;

    private ListView mList;
    private Toolbar mToolbar;

    private TextView mTitleFooter;
    private ImageView mIconFooter;

    private int mColorSelected = 0;
    private int mCurrentPosition = 1;
    private int mNewSelector = 0;
    private boolean mRemoveSelector = false;

    private List<Integer> mListIcon;
    private List<Integer> mListHeader;
    private List<String> mListNameItem;
    private SparseIntArray mSparseCounter;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mRelativeDrawer;
    private RelativeLayout mFooterDrawer;

    private NavigationLiveoAdapter mNavigationAdapter;
    private ActionBarDrawerToggleCompat mDrawerToggle;
    private NavigationLiveoListener mNavigationListener;

    public static final String CURRENT_POSITION = "CURRENT_POSITION";

    public abstract void onInt(Bundle savedInstanceState);
    public abstract void onUserInformation();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_main);

        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(new DrawerItemClickListener());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggleCompat(this, mDrawerLayout, mToolbar);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mTitleFooter = (TextView) this.findViewById(R.id.titleFooter);
        mIconFooter = (ImageView) this.findViewById(R.id.iconFooter);

        mFooterDrawer = (RelativeLayout) this.findViewById(R.id.footerDrawer);
        mFooterDrawer.setOnClickListener(onClickFooterDrawer);

        mRelativeDrawer = (FrameLayout) this.findViewById(R.id.relativeDrawer);

		if (mList != null) {
            mountListNavigation(savedInstanceState);
		}

		if (savedInstanceState != null) {
			setCurrentPosition(savedInstanceState.getInt(CURRENT_POSITION));
	    }else{
            mNavigationListener.onItemClickNavigation(mCurrentPosition, R.id.container);
	    }

        this.setSupportActionBar(mToolbar);

        setCheckedItemNavigation(mCurrentPosition, true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Resources.Theme theme = this.getTheme();
                TypedArray typedArray = theme.obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
                mDrawerLayout.setStatusBarBackground(typedArray.getResourceId(0, 0));
            } catch (Exception e) {
                e.getMessage();
            }

            this.getToolbar().setElevation(10);
        }
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub		
		super.onSaveInstanceState(outState);		
		outState.putInt(CURRENT_POSITION, mCurrentPosition);
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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
    	mNavigationListener.onPrepareOptionsMenuNavigation(menu, mCurrentPosition, drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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

            int mPosition = (position - 1);

            if (position != 0) {
                mNavigationListener.onItemClickNavigation(mPosition, R.id.container);
                setCurrentPosition(mPosition);
                setCheckedItemNavigation(mPosition, true);
            }

	    	mDrawerLayout.closeDrawer(mRelativeDrawer);
        }
    }

    private OnClickListener onClickUserPhoto = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
            mNavigationListener.onClickUserPhotoNavigation(v);
			mDrawerLayout.closeDrawer(mRelativeDrawer);
		}
	};

    private OnClickListener onClickFooterDrawer = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mNavigationListener.onClickFooterItemNavigation(v);
            mDrawerLayout.closeDrawer(mRelativeDrawer);
        }
    };

    private void mountListNavigation(Bundle savedInstanceState){
        mountListHeader();
        onUserInformation();
        onInt(savedInstanceState);

        if (mNavigationListener == null){
            throw new RuntimeException("You must start the NavigationListener in onInit() method of its main activity. Example: this.setNavigationListener(this);");
        }

        mNavigationAdapter = new NavigationLiveoAdapter(this, NavigationLiveoList.getNavigationAdapter(mListNameItem, mListIcon,
                mListHeader, mSparseCounter, mColorSelected, mRemoveSelector), mNewSelector);

        mList.setAdapter(mNavigationAdapter);
    }

    private void mountListHeader() {
        View mHeader = getLayoutInflater().inflate(R.layout.navigation_list_header, mList, false);

        mUserName = (TextView) mHeader.findViewById(R.id.userName);
        mUserEmail = (TextView) mHeader.findViewById(R.id.userEmail);

        mUserPhoto = (ImageView) mHeader.findViewById(R.id.userPhoto);
        mUserPhoto.setOnClickListener(onClickUserPhoto);

//        public ImageView mUserPhotoTwo;
//        public ImageView mUserPhotoTree;
//        mUserPhotoTwo = (ImageView) mHeader.findViewById(R.id.userPhotoTwo);
//        mUserPhotoTree = (ImageView) mHeader.findViewById(R.id.userPhotoTree);

        mUserBackground = (ImageView) mHeader.findViewById(R.id.userBackground);
        mList.addHeaderView(mHeader);
    }

    /*{ Set adapter attributes }*/
    public void setNavigationAdapter(List<String> listNameItem, List<Integer> listIcon, List<Integer> listItensHeader, SparseIntArray sparceItensCount){
        this.mListNameItem = listNameItem;
        this.mListIcon = listIcon;
        this.mListHeader = listItensHeader;
        this.mSparseCounter = sparceItensCount;
    }

    /*{ Set adapter attributes }*/
    public void setNavigationAdapter(List<String> listNameItem, List<Integer> listIcon){
        this.mListNameItem = listNameItem;
        this.mListIcon = listIcon;
    }

    /*{ Starting listener navigation }*/
    public void setNavigationListener(NavigationLiveoListener navigationListener){
        this.mNavigationListener = navigationListener;
    };

    /*{ First item of the position selected from the list }*/
    public void setDefaultStartPositionNavigation(int position){
        this.mCurrentPosition = position;
    }

    /*{ Position in the last clicked item list }*/
    private void setCurrentPosition(int position){
        this.mCurrentPosition = position;
    }

    /*{ get position in the last clicked item list }*/
    public int getCurrentPosition(){
        return this.mCurrentPosition;
    }

    /*{ Select item clicked }*/
    private void setCheckedItemNavigation(int position, boolean checked){
        this.mNavigationAdapter.resetarCheck();
        this.mNavigationAdapter.setChecked(position, checked);
    }

    /*{ Information footer list item }*/
    public void setFooterInformationDrawer(String title, int icon){

        if (title == null){
            throw new RuntimeException("The title can not be null or empty");
        }

        if (title.trim().equals("")){
            throw new RuntimeException("The title can not be null or empty");
        }

        mTitleFooter.setText(title);

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);
        }
    };

    /*{ Information footer list item }*/
    public void setFooterInformationDrawer(int title, int icon){

        if (title == 0){
            throw new RuntimeException("The title can not be null or empty");
        }

        mTitleFooter.setText(getString(title));

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);
        }
    };

    /*{ If not want to use the footer item just put false }*/
    public void setFooterNavigationVisible(boolean visible){
        this.mFooterDrawer.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    /*{ Item color selected in the list - name and icon }*/
    public void setColorSelectedItemNavigation(int colorId){
        this.mColorSelected = colorId;
    }

    /*{ New selector navigation }*/
    public void setNewSelectorNavigation(int drawable){

        if (mRemoveSelector){
            throw new RuntimeException("The option to remove the select is active. Please remove the removeSelectorNavigation method so you can use the setNewSelectorNavigation");
        }

        this.mNewSelector = drawable;
    }

    /*{ Remove selector navigation }*/
    public void removeSelectorNavigation(){
        this.mRemoveSelector = true;
    }

    /*{ --- }*/
    public void setNewCounterValue(int position, int value){
        this.mNavigationAdapter.setNewCounterValue(position, value);
    }

    /*{ --- }*/
    public void setIncreasingCounterValue(int position, int value){
        this.mNavigationAdapter.setIncreasingCounterValue(position, value);
    }

    /*{ --- }*/
    public void setDecreaseCountervalue(int position, int value){
        this.mNavigationAdapter.setDecreaseCountervalue(position, value);
    }

    /*{ get toolbar }*/
    public Toolbar getToolbar() {
        return this.mToolbar;
    }

    /*{ get toolbar }*/
    public DrawerLayout getDrawerLayout() {
        return this.mDrawerLayout;
    }

    @Override
    public void onBackPressed() {

        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
        if (drawerOpen) {
            mDrawerLayout.closeDrawer(mRelativeDrawer);
        } else {
            super.onBackPressed();
        }
    }
}
