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
package br.liveo.ndrawer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    public List<String> mListNameItem;

    @Override
    public void onInt(Bundle savedInstanceState) {

        // User Information
        this.userName.setText("Rudson Lima");
        this.userEmail.setText("rudsonlive@gmail.com");
        this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // name of the list items
        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, getString(R.string.inbox));
        mListNameItem.add(1, getString(R.string.starred));
        mListNameItem.add(2, getString(R.string.sent_mail));
        mListNameItem.add(3, getString(R.string.drafts));
        mListNameItem.add(4, getString(R.string.more_markers)); //This item will be a subHeader
        mListNameItem.add(5, getString(R.string.trash));
        mListNameItem.add(6, getString(R.string.spam));

        // icons list items
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, R.drawable.ic_inbox_black_24dp);
        mListIconItem.add(1, R.drawable.ic_star_black_24dp); //Item no icon set 0
        mListIconItem.add(2, R.drawable.ic_send_black_24dp); //Item no icon set 0
        mListIconItem.add(3, R.drawable.ic_drafts_black_24dp);
        mListIconItem.add(4, 0); //When the item is a subHeader the value of the icon 0
        mListIconItem.add(5, R.drawable.ic_delete_black_24dp);
        mListIconItem.add(6, R.drawable.ic_report_black_24dp);

        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(4);

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        mSparseCounterItem.put(0, 7);
        mSparseCounterItem.put(1, 123);
        mSparseCounterItem.put(6, 250);

        with(this).startingPosition(1) //Starting position in the list
                .nameItem(mListNameItem)
                .iconItem(mListIconItem)
                .headerItem(mListHeaderItem)
                .countItem(mSparseCounterItem)

                //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
                //.selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
                //.colorItemDefault(R.color.nliveo_gray) //Inform the standard color name, icon and counter
                //.colorItemSelected(R.color.nliveo_purple_colorPrimary) //State the name of the color, icon and meter when it is selected
                //.backgroundList(R.color.nliveo_black_light) //Inform the list of background color
                //.colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

                .footerItem(R.string.settings, R.drawable.ic_settings_black_24dp)
                .setOnClickUser(onClickPhoto)
                .setOnClickFooter(onClickFooter)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = new FragmentMain().newInstance(mListNameItem.get(position));

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
    }

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            closeDrawer();
        }
    };
}

