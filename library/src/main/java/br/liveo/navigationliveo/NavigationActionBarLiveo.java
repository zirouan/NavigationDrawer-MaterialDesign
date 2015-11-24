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
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.liveo.model.HelpItem;
import br.liveo.model.Navigation;
import br.liveo.adapter.NavigationLiveoAdapter;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;

public abstract class NavigationActionBarLiveo extends AppCompatActivity {

    public TextView userName;
    public TextView userEmail;
    public ImageView userPhoto;
    public ImageView userBackground;

    private View mHeader;

    private ListView mList;
    private TextView mTitleFooter;
    private ImageView mIconFooter;
    private int mColorName = 0;

    private TextView mTitleSecondFooter;
    private ImageView mIconSecondFooter;
    private RelativeLayout mFooterSecondDrawer;

    private int mColorIcon = 0;
    private int mNewSelector = 0;
    private int mColorCounter = 0;
    private int mColorSeparator = 0;
    private int mColorSubHeader = 0;

    private int mColorDefault = 0;
    private int mCurrentPosition = 1;
    private int mSelectorDefault = 0;
    private boolean mRemoveAlpha = false;
    private boolean mRemoveHeader = false;
    private boolean mCustomHeader = false;
    private boolean mRemoveColorFilter = false;

    private Toolbar mToolbar;
    private float mElevationToolBar = 15;

    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mRelativeDrawer;
    private RelativeLayout mFooterDrawer;
    private boolean isSaveInstance = false;

    private List<HelpItem> mHelpItem;
    private Navigation mNavigation = new Navigation();

    private NavigationLiveoAdapter mNavigationAdapter;
    private ActionBarDrawerToggleCompat mDrawerToggle;

    private OnItemClickListener mOnItemClickLiveo;
    private OnPrepareOptionsMenuLiveo mOnPrepareOptionsMenu;

    public static final String CURRENT_POSITION = "CURRENT_POSITION";

    /**
     * onCreate(Bundle savedInstanceState).
     * @param savedInstanceState onCreate(Bundle savedInstanceState).
     */
    public abstract void onInt(Bundle savedInstanceState);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mountListNavigation(savedInstanceState);

        if (savedInstanceState != null) {
            isSaveInstance = true;
            setCurrentPosition(savedInstanceState.getInt(CURRENT_POSITION));
        }

        if (savedInstanceState == null) {
            mOnItemClickLiveo.onItemClick(mCurrentPosition);
        }

        setCheckedItemNavigation(mCurrentPosition, true);
	}

    private void configureFindView(){
        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(new DrawerItemClickListener());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggleCompat(this, mDrawerLayout, mToolbar);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mTitleFooter = (TextView) this.findViewById(R.id.titleFooter);
        mIconFooter = (ImageView) this.findViewById(R.id.iconFooter);

        mTitleSecondFooter = (TextView) this.findViewById(R.id.titleSecondFooter);
        mIconSecondFooter = (ImageView) this.findViewById(R.id.iconSecondFooter);

        mFooterDrawer = (RelativeLayout) this.findViewById(R.id.footerDrawer);
        mFooterSecondDrawer = (RelativeLayout) this.findViewById(R.id.footerSecondDrawer);
        mRelativeDrawer = (ScrimInsetsFrameLayout) this.findViewById(R.id.relativeDrawer);

        this.setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Resources.Theme theme = this.getTheme();
                TypedArray typedArray = theme.obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
                mDrawerLayout.setStatusBarBackground(typedArray.getResourceId(0, 0));
            } catch (Exception e) {
                e.getMessage();
            }

            this.setElevationToolBar(mElevationToolBar);
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

        if (mOnPrepareOptionsMenu != null){
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
            mOnPrepareOptionsMenu.onPrepareOptionsMenu(menu, mCurrentPosition, drawerOpen);
        }

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
                    drawerLayout,
                    toolbar,
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

            int mPosition = (!mRemoveHeader || !mCustomHeader ? position - 1 : position);

            if (position != 0 || (mRemoveHeader && mCustomHeader)) {
                mOnItemClickLiveo.onItemClick(mPosition);
                setCurrentPosition(mPosition);
                setCheckedItemNavigation(mPosition, true);
            }

	    	mDrawerLayout.closeDrawer(mRelativeDrawer);
        }
    }

    private void mountListNavigation(Bundle savedInstanceState){
        if (mOnItemClickLiveo == null){
            this.createUserDefaultHeader();
            this.onInt(savedInstanceState);
        }
    }

    private void addHeaderView() {
        if(!this.mRemoveHeader) {
            this.mList.addHeaderView(this.mHeader);
        }
    }

    /**
     * Remove elevation toolBar
     */
    public NavigationActionBarLiveo removeElevationToolBar(){
        this.mElevationToolBar = 0;
        return this;
    }

    /**
     * @deprecated
     */
    public void setAdapterNavigation(){

        if (mOnItemClickLiveo == null){
            throw new RuntimeException(getString(R.string.start_navigation_listener));
        }

        this.addHeaderView();
        List<Integer> mListExtra = new ArrayList<>();
        mListExtra.add(0, mNewSelector);
        mListExtra.add(1, mColorDefault);
        mListExtra.add(2, mColorIcon);
        mListExtra.add(3, mColorName);
        mListExtra.add(4, mColorSeparator);
        mListExtra.add(5, mColorCounter);
        mListExtra.add(6, mSelectorDefault);
        mListExtra.add(7, mColorSubHeader);

        List<Boolean> mListRemove = new ArrayList<>();
        mListRemove.add(0, mRemoveAlpha);
        mListRemove.add(1, mRemoveColorFilter);

        if (mHelpItem != null){
            mNavigationAdapter = new NavigationLiveoAdapter(this, NavigationLiveoList.getNavigationAdapter(this, mHelpItem, mNavigation.colorSelected, mNavigation.removeSelector), mListRemove, mListExtra);
        }else {
            mNavigationAdapter = new NavigationLiveoAdapter(this, NavigationLiveoList.getNavigationAdapter(this, mNavigation), mListRemove, mListExtra);
        }

        mList.setAdapter(mNavigationAdapter);
    }

    public void build(){

        if (mOnItemClickLiveo == null){
            throw new RuntimeException(getString(R.string.start_navigation_listener));
        }

        this.addHeaderView();
        List<Integer> mListExtra = new ArrayList<>();
        mListExtra.add(0, mNewSelector);
        mListExtra.add(1, mColorDefault);
        mListExtra.add(2, mColorIcon);
        mListExtra.add(3, mColorName);
        mListExtra.add(4, mColorSeparator);
        mListExtra.add(5, mColorCounter);
        mListExtra.add(6, mSelectorDefault);
        mListExtra.add(7, mColorSubHeader);

        List<Boolean> mListRemove = new ArrayList<>();
        mListRemove.add(0, mRemoveAlpha);
        mListRemove.add(1, mRemoveColorFilter);

        if (mHelpItem != null){
            mNavigationAdapter = new NavigationLiveoAdapter(this, NavigationLiveoList.getNavigationAdapter(this, mHelpItem, mNavigation.colorSelected, mNavigation.removeSelector), mListRemove, mListExtra);
        }else {
            mNavigationAdapter = new NavigationLiveoAdapter(this, NavigationLiveoList.getNavigationAdapter(this, mNavigation), mListRemove, mListExtra);
        }

        mList.setAdapter(mNavigationAdapter);
    }

    /**
     * Create user default header
     */
    private void createUserDefaultHeader() {
        mHeader = getLayoutInflater().inflate(R.layout.navigation_list_header, mList, false);

        userName = (TextView) mHeader.findViewById(R.id.userName);
        userEmail = (TextView) mHeader.findViewById(R.id.userEmail);
        userPhoto = (ImageView) mHeader.findViewById(R.id.userPhoto);
        userBackground = (ImageView) mHeader.findViewById(R.id.userBackground);
    }

    /**
     * Background ListView
     * @param color Default color - R.color.nliveo_white
     */
    public NavigationActionBarLiveo backgroundList(int color){
        this.mSelectorDefault = color;
        this.mList.setBackgroundResource(color);
        this.mFooterDrawer.setBackgroundResource(color);
        this.mFooterSecondDrawer.setBackgroundResource(color);
        return this;
    }

    /**
     * Background Footer
     * @param color Default color - R.color.nliveo_white
     */
    public NavigationActionBarLiveo footerBackground(int color){
        this.mFooterDrawer.setBackgroundResource(color);
        this.mFooterSecondDrawer.setBackgroundResource(color);
        return this;
    }

    /**
     * Starting listener navigation
     * @param listener listener.
     */
    public NavigationActionBarLiveo with(OnItemClickListener listener){
        setContentView(R.layout.navigation_main_actionbar_dark);
        this.mOnItemClickLiveo = listener;
        configureFindView();
        return this;
    };

    /**
     * Starting listener navigation
     * @param listener listener.
     * @param theme theme.
     */
    public NavigationActionBarLiveo with(OnItemClickListener listener, int theme){
        setContentView(theme == Navigation.THEME_DARK ? R.layout.navigation_main_actionbar_dark : R.layout.navigation_main_actionbar_light);
        this.mOnItemClickLiveo = listener;
        configureFindView();
        return this;
    };

    /**
     * @param listHelpItem list HelpItem.
     */
    public NavigationActionBarLiveo addAllHelpItem(List<HelpItem> listHelpItem){
        this.mHelpItem = listHelpItem;
        return this;
    }

    /**
     * @param listNameItem list name item.
     */
    public NavigationActionBarLiveo nameItem(List<String> listNameItem){
        this.mNavigation.nameItem = listNameItem;
        return this;
    }

    /**
     * @param listIcon list icon item.
     */
    public NavigationActionBarLiveo iconItem(List<Integer> listIcon){
        this.mNavigation.iconItem = listIcon;
        return this;
    }

    /**
     * @param listHeader list header name item.
     */
    public NavigationActionBarLiveo headerItem(List<Integer> listHeader){
        this.mNavigation.headerItem = listHeader;
        return this;
    }

    /**
     * @param sparceCount sparce count item.
     */
    public NavigationActionBarLiveo countItem(SparseIntArray sparceCount){
        this.mNavigation.countItem = sparceCount;
        return this;
    }

    /**
     * Set adapter attributes
     * @param listNameItem list name item.
     * @param listIcon list icon item.
     * @param listItensHeader list header name item.
     * @param sparceItensCount sparce count item.
     */
    public void setNavigationAdapter(List<String> listNameItem, List<Integer> listIcon, List<Integer> listItensHeader, SparseIntArray sparceItensCount){
        this.nameItem(listNameItem);
        this.iconItem(listIcon);
        this.headerItem(listItensHeader);
        this.countItem(sparceItensCount);
    }

    /**
     * Set adapter attributes
     * @param listNameItem list name item.
     * @param listIcon list icon item.
     */
    public void setNavigationAdapter(List<String> listNameItem, List<Integer> listIcon){
        this.nameItem(listNameItem);
        this.iconItem(listIcon);
    }

    /**
     * hide navigation item
     * @param listHide list hide item.
     */
    public NavigationActionBarLiveo hideItem(List<Integer> listHide){
        mNavigation.hideItem = listHide;
        return this;
    }

    /**
     * show navigation item
     * @param listShow list show item.
     */
    public void showNavigationItem(List<Integer> listShow){

        if (listShow == null){
            throw new RuntimeException(getString(R.string.list_hide_item));
        }

        for (int i = 0; i < listShow.size(); i++){
            setVisibleItemNavigation(listShow.get(i), true);
        }
    }

    /**
     * Starting listener navigation
     * @param onItemClick listener.
     * @deprecated
     */
    public void setNavigationListener(OnItemClickListener onItemClick){
        this.mOnItemClickLiveo = onItemClick;
    };

    /**
     * First item of the position selected from the list, use method startingPosition
     * @param position ...
     * @deprecated
     */
    public void setDefaultStartPositionNavigation(int position){
        if (!isSaveInstance) {
            this.mCurrentPosition = position;
        }
    }

    /**
     * First item of the position selected from the list
     * @param position ...
     */
    public NavigationActionBarLiveo startingPosition(int position){
        if (!isSaveInstance) {
            this.mCurrentPosition = position;
        }

        return this;
    }

    /**
     * Position in the last clicked item list
     * @param position ...
     */
    private void setCurrentPosition(int position){
        this.mCurrentPosition = position;
    }

    /**
     * get position in the last clicked item list
     */
    public int getCurrentPosition(){
        return this.mCurrentPosition;
    }

    /*{  }*/

    /**
     * Select item clicked
     * @param position item position.
     * @param checked true to check.
     */
    public void setCheckedItemNavigation(int position, boolean checked){

        if (this.mNavigationAdapter == null){
            throw new RuntimeException(getString(R.string.start_navigation_listener));
        }

        this.mNavigationAdapter.resetarCheck();
        this.mNavigationAdapter.setChecked(position, checked);
    }

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @deprecated
     */
    public void setFooterInformationDrawer(String title, int icon){

        if (title == null){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        if (title.trim().equals("")){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);
        }
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     */
    public NavigationActionBarLiveo footerItem(String title, int icon){

        if (title == null){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        if (title.trim().equals("")){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);
        }

        return this;
    };


    /**
     * Information footer second list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param color item footer name and icon color.
     */
    public NavigationActionBarLiveo footerSecondItem(String title, int icon, int color){

        if (title == null){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        if (title.trim().equals("")){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleSecondFooter.setText(title);

        if (color > 0){
            mTitleSecondFooter.setTextColor(ContextCompat.getColor(this, color));
        }

        if (icon == 0){
            mIconSecondFooter.setVisibility(View.GONE);
        }else{
            mIconSecondFooter.setImageResource(icon);

            if ( color > 0) {
                mIconSecondFooter.setColorFilter(ContextCompat.getColor(this, color));
            }
        }
        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param colorName item footer name color.
     * @param colorIcon item footer icon color.
     * @deprecated
     */
    public void setFooterInformationDrawer(String title, int icon, int colorName, int colorIcon){

        if (title == null){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        if (title.trim().equals("")){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (colorName > 0){
            mTitleFooter.setTextColor(ContextCompat.getColor(this, colorName));
        }

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);

            if ( colorIcon > 0) {
                mIconFooter.setColorFilter(ContextCompat.getColor(this, colorIcon));
            }
        }
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param colorName item footer name color.
     * @param colorIcon item footer icon color.
     */
    public NavigationActionBarLiveo footerInformationDrawer(String title, int icon, int colorName, int colorIcon){

        if (title == null){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        if (title.trim().equals("")){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (colorName > 0){
            mTitleFooter.setTextColor(ContextCompat.getColor(this, colorName));
        }

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);

            if ( colorIcon > 0) {
                mIconFooter.setColorFilter(ContextCompat.getColor(this, colorIcon));
            }
        }
        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @deprecated
     */
    public NavigationActionBarLiveo setFooterInformationDrawer(int title, int icon){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(getString(title));

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);
        }

        if (mColorDefault > 0){
            footerNameColor(mColorDefault);
            footerIconColor(mColorDefault);
        }

        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     */
    public NavigationActionBarLiveo footerItem(int title, int icon){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(getString(title));

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);
        }

        if (mColorDefault > 0){
            footerNameColor(mColorDefault);
            footerIconColor(mColorDefault);
        }

        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     */
    public NavigationActionBarLiveo footerSecondItem(int title, int icon){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleSecondFooter.setText(getString(title));

        if (icon == 0){
            mIconSecondFooter.setVisibility(View.GONE);
        }else{
            mIconSecondFooter.setImageResource(icon);
        }

        if (mColorDefault > 0){
            footerNameColor(mColorDefault);
            footerIconColor(mColorDefault);
        }

        mFooterSecondDrawer.setVisibility(View.VISIBLE);

        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param colorName item footer name color.
     * @param colorIcon item footer icon color.
     * @deprecated
     */
    public void setFooterInformationDrawer(int title, int icon, int colorName, int colorIcon){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (colorName > 0) {
            mTitleFooter.setTextColor(ContextCompat.getColor(this, colorName));
        }

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);

            if ( colorIcon > 0) {
                mIconFooter.setColorFilter(ContextCompat.getColor(this, colorIcon));
            }
        }
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param colorName item footer name color.
     * @param colorIcon item footer icon color.
     */
    public NavigationActionBarLiveo footerItem(int title, int icon, int colorName, int colorIcon){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (colorName > 0){
            mTitleFooter.setTextColor(ContextCompat.getColor(this, colorName));
        }

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);

            if ( colorIcon > 0) {
                mIconFooter.setColorFilter(ContextCompat.getColor(this, colorIcon));
            }
        }
        return this;
    };

    /**
     * Information footer second list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param colorName item footer name color.
     * @param colorIcon item footer icon color.
     */
    public NavigationActionBarLiveo footerSecondItem(int title, int icon, int colorName, int colorIcon){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleSecondFooter.setText(title);

        if (colorName > 0){
            mTitleSecondFooter.setTextColor(ContextCompat.getColor(this, colorName));
        }

        if (icon == 0){
            mIconSecondFooter.setVisibility(View.GONE);
        }else{
            mIconSecondFooter.setImageResource(icon);

            if ( colorIcon > 0) {
                mIconSecondFooter.setColorFilter(ContextCompat.getColor(this, colorIcon));
            }
        }

        mFooterSecondDrawer.setVisibility(View.VISIBLE);
        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param color item footer name and icon color.
     */
    public NavigationActionBarLiveo footerItem(int title, int icon, int color){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (color > 0){
            mTitleFooter.setTextColor(ContextCompat.getColor(this, color));
        }

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);

            if ( color > 0) {
                mIconFooter.setColorFilter(ContextCompat.getColor(this, color));
            }
        }
        return this;
    };

    /**
     * Information footer second list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param color item footer name and icon color.
     */
    public NavigationActionBarLiveo footerSecondItem(int title, int icon, int color){

        if (title == 0){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleSecondFooter.setText(title);

        if (color > 0){
            mTitleSecondFooter.setTextColor(ContextCompat.getColor(this, color));
        }

        if (icon == 0){
            mIconSecondFooter.setVisibility(View.GONE);
        }else{
            mIconSecondFooter.setImageResource(icon);

            if ( color > 0) {
                mIconSecondFooter.setColorFilter(ContextCompat.getColor(this, color));
            }
        }

        mFooterSecondDrawer.setVisibility(View.VISIBLE);
        return this;
    };

    /**
     * Information footer list item
     * @param title item footer name.
     * @param icon item footer icon.
     * @param color item footer name and icon color.
     */
    public NavigationActionBarLiveo footerItem(String title, int icon, int color){

        if (title == null){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        if (title.trim().equals("")){
            throw new RuntimeException(getString(R.string.title_null_or_empty));
        }

        mTitleFooter.setText(title);

        if (color > 0){
            mTitleFooter.setTextColor(ContextCompat.getColor(this, color));
        }

        if (icon == 0){
            mIconFooter.setVisibility(View.GONE);
        }else{
            mIconFooter.setImageResource(icon);

            if ( color > 0) {
                mIconFooter.setColorFilter(ContextCompat.getColor(this, color));
            }
        }
        return this;
    };

    /**
     * If not want to use the footer item just put false
     * @param visible true or false.
     */
    public void setFooterNavigationVisible(boolean visible){
        this.mFooterDrawer.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    /**
     * Remove footer
     */
    public NavigationActionBarLiveo removeFooter(){
        this.mFooterDrawer.setVisibility(View.GONE);
        return this;
    }

    /**
     * Item color selected in the list - name and icon (use before the setNavigationAdapter)
     * @param colorId color id.
     * @deprecated
     */
    public void setColorSelectedItemNavigation(int colorId){
        mNavigation.colorSelected = colorId;
    }

    /**
     * Item color selected in the list - name, icon and counter
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorItemSelected(int colorId){
        mNavigation.colorSelected = colorId;
        return this;
    }

    /**
     * Footer name color
     * @param colorId color id.
     * @deprecated
     */
    public void setFooterNameColorNavigation(int colorId){
        this.mTitleFooter.setTextColor(ContextCompat.getColor(this, colorId));
    }

    /**
     * Footer name color
     * @param colorId color id.
     */
    public NavigationActionBarLiveo footerNameColor(int colorId){
        this.mTitleFooter.setTextColor(ContextCompat.getColor(this, colorId));
        return this;
    }

    /**
     * Footer icon color
     * @param colorId color id.
     */
    public NavigationActionBarLiveo footerIconColor(int colorId) {
        this.mIconFooter.setColorFilter(ContextCompat.getColor(this, colorId));
        return this;
    }
    /**
     * Footer icon color
     * @param colorId color id.
     * @deprecated
     */
    public void setFooterIconColorNavigation(int colorId) {
        this.mIconFooter.setColorFilter(ContextCompat.getColor(this, colorId));
    }

    /**
     * Item color default in the list - name and icon (use before the setNavigationAdapter)
     * @param colorId color id.
     * @deprecated
     */
    public void setColorDefaultItemNavigation(int colorId){
        this.mColorDefault = colorId;
    }

    /**
     * Item color default in the list - name and icon (use before the setNavigationAdapter)
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorItemDefault(int colorId){
        this.mColorDefault = colorId;
        return this;
    }

    /**
     * Icon item color in the list - icon (use before the setNavigationAdapter)
     * @param colorId color id.
     * @deprecated
     */
    public void setColorIconItemNavigation(int colorId){
        this.mColorIcon = colorId;
    }

    /**
     * Icon item color in the list - icon (use before the setNavigationAdapter)
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorItemIcon(int colorId){
        this.mColorIcon = colorId;
        return this;
    }

    /**
     * Separator item subHeader color in the list - icon (use before the setNavigationAdapter)
     * @param colorId color id.
     * @deprecated
     */
    public void setColorSeparatorItemSubHeaderNavigation(int colorId){
        this.mColorSeparator = colorId;
    }

    /**
     * Name item subHeader color
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorNameSubHeader(int colorId){
        this.mColorSubHeader = colorId;
        return this;
    }

    /**
     * Separator item subHeader color in the list - icon
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorLineSeparator(int colorId){
        this.mColorSeparator = colorId;
        return this;
    }

    /**
     * Counter color in the list (use before the setNavigationAdapter)
     * @param colorId color id.
     * @deprecated
     */
    public void setColorCounterItemNavigation(int colorId){
        this.mColorCounter = colorId;
    }

    /**
     * Counter color in the list (use before the setNavigationAdapter)
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorItemCounter(int colorId){
        this.mColorCounter = colorId;
        return this;
    }

    /**
     * Name item color in the list - name (use before the setNavigationAdapter)
     * @param colorId color id.
     * @deprecated
     */
    public void setColorNameItemNavigation(int colorId){
        this.mColorName = colorId;
    }

    /**
     * Name item color in the list - name (use before the setNavigationAdapter)
     * @param colorId color id.
     */
    public NavigationActionBarLiveo colorItemName(int colorId){
        this.mColorName = colorId;
        return this;
    }

    /**
     * New selector navigation
     * @param resourceSelector drawable xml - selector.
     * @deprecated
     */
    public void setNewSelectorNavigation(int resourceSelector){

        if (mNavigation.removeSelector){
            throw new RuntimeException(getString(R.string.remove_selector_navigation));
        }

        this.mNewSelector = resourceSelector;
    }

    /**
     * New selector navigation
     * @param resourceSelector drawable xml - selector.
     */
    public NavigationActionBarLiveo selectorCheck(int resourceSelector){

        if (mNavigation.removeSelector){
            throw new RuntimeException(getString(R.string.remove_selector_navigation));
        }

        this.mNewSelector = resourceSelector;
        return this;
    }

    /**
     * Remove selector navigation
     * @deprecated
     */
    public void removeSelectorNavigation(){
        mNavigation.removeSelector = true;
    }

    /**
     * Remove selector navigation
     */
    public NavigationActionBarLiveo removeSelector(){
        mNavigation.removeSelector = true;
        return this;
    }

    /**
     * New name item
     * @param position item position.
     * @param name new name
     */
    public void setNewName(int position, String name){
        this.mNavigationAdapter.setNewName(position, name);
    }

    /**
     * New name item
     * @param position item position.
     * @param name new name
     */
    public void setNewName(int position, int name){
        this.mNavigationAdapter.setNewName(position, getString(name));
    }

    /**
     * New name item
     * @param position item position.
     * @param icon new icon
     */
    public void setNewIcon(int position, int icon){
        this.mNavigationAdapter.setNewIcon(position, icon);
    }

    /**
     * New information item navigation
     * @param position item position.
     * @param name new name
     * @param icon new icon
     * @param counter new counter
     */
    public void setNewInformationItem(int position, int name, int icon, int counter){
        this.mNavigationAdapter.setNewInformationItem(position, getString(name), icon, counter);
    }

    /**
     * New information item navigation
     * @param position item position.
     * @param name new name
     * @param icon new icon
     * @param counter new counter
     */

    public void setNewInformationItem(int position, String name, int icon, int counter){
        this.mNavigationAdapter.setNewInformationItem(position, name, icon, counter);
    }

    /**
     * New counter value
     * @param position item position.
     * @param value new counter value.
     */
    public void setNewCounterValue(int position, int value){
        this.mNavigationAdapter.setNewCounterValue(position, value);
    }

    /**
     * Increasing counter value
     * @param position item position.
     * @param value new counter value (old value + new value).
     */
    public void setIncreasingCounterValue(int position, int value){
        this.mNavigationAdapter.setIncreasingCounterValue(position, value);
    }

    /**
     * Decrease counter value
     * @param position item position.
     * @param value new counter value (old value - new value).
     */
    public void setDecreaseCountervalue(int position, int value){
        this.mNavigationAdapter.setDecreaseCountervalue(position, value);
    }

    /**
     * Make the item visible navigation or not (default value is true)
     * @param position item position.
     * @param visible true or false.
     */
    public void setVisibleItemNavigation(int position, boolean visible){
        this.mNavigationAdapter.setVisibleItemNavigation(position, visible);
    }

    /**
     * Remove alpha item navigation (use before the setNavigationAdapter)
     * @deprecated
     */
    public void removeAlphaItemNavigation(){
        this.mRemoveAlpha = !mRemoveAlpha;
    }

    /**
     * Remove alpha item navigation (use before the setNavigationAdapter)
     */
    public NavigationActionBarLiveo removeAlpha(){
        this.mRemoveAlpha = !mRemoveAlpha;
        return this;
    }

    /**
     * Remove color filter icon item navigation
     */
    public NavigationActionBarLiveo removeColorFilter(){
        this.mRemoveColorFilter = !mRemoveColorFilter;
        return this;
    }

    /**
     * public void setElevation (float elevation)
     * Added in API level 21
     * Default value is 15
     * @param elevation Sets the base elevation of this view, in pixels.
     */
    public void setElevationToolBar(float elevation){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mElevationToolBar = elevation;
            this.getToolbar().setElevation(elevation);
        }
    }

    /**
     * Remove default Header
     */
    public void showDefaultHeader() {
        if (mHeader == null){
            throw new RuntimeException(getString(R.string.header_not_created));
        }

        mList.addHeaderView(mHeader);
    }

    /**
     * Remove default Header
     */
    private void removeDefaultHeader() {
        if (mHeader == null){
            throw new RuntimeException(getString(R.string.header_not_created));
        }

        mList.removeHeaderView(mHeader);
    }

    /**
     * Remove Header
     */
    public NavigationActionBarLiveo removeHeader(){
        mRemoveHeader = true;
        mCustomHeader = true;
        return this;
    }

    /**
     * Add custom Header
     * @param v ...
     * @deprecated
     */
    public void addCustomHeader(View v) {
        if (v == null){
            throw new RuntimeException(getString(R.string.custom_header_not_created));
        }

        removeDefaultHeader();
        mList.addHeaderView(v);
    }

    /**
     * Add custom Header
     * @param view ...
     */
    public NavigationActionBarLiveo customHeader(View view) {
        if (view == null){
            throw new RuntimeException(getString(R.string.custom_header_not_created));
        }

        this.removeHeader();
        mCustomHeader = false;
        mRelativeDrawer.setFitsSystemWindows(true);
        mList.addHeaderView(view);
        return this;
    }

    /**
     * Remove default Header
     * @param v ...
     */
    public void removeCustomdHeader(View v) {
        if (v == null){
            throw new RuntimeException(getString(R.string.custom_header_not_created));
        }

        mList.removeHeaderView(v);
    }

    /**
     * get listview
     */
    public ListView getListView() {
        return this.mList;
    }

    /**
     * get toolbar
     */
    public Toolbar getToolbar() {
        return this.mToolbar;
    }

    /**
     * Open drawer
     */
    public void openDrawer() {
        mDrawerLayout.openDrawer(mRelativeDrawer);
    }

    /**
     * Close drawer
     */
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mRelativeDrawer);
    }

    public NavigationActionBarLiveo setOnItemClick(OnItemClickListener onItemClick){
        this.mOnItemClickLiveo = onItemClick;
        return this;
    }

    public NavigationActionBarLiveo setOnPrepareOptionsMenu(OnPrepareOptionsMenuLiveo onPrepareOptionsMenu){
        this.mOnPrepareOptionsMenu = onPrepareOptionsMenu;
        return this;
    }

    public NavigationActionBarLiveo setOnClickUser(View.OnClickListener listener){
        this.userPhoto.setOnClickListener(listener);
        return this;
    }

    public NavigationActionBarLiveo setOnClickFooter(View.OnClickListener listener){
        this.mFooterDrawer.setOnClickListener(listener);
        return this;
    }

    public NavigationActionBarLiveo setOnClickFooterSecond(View.OnClickListener listener){
        this.mFooterSecondDrawer.setOnClickListener(listener);
        return this;
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
