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

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.liveo.model.HelpItem;
import br.liveo.model.Navigation;
import br.liveo.adapter.NavigationLiveoItemAdapter;

public class NavigationLiveoList {

    public static List<NavigationLiveoItemAdapter> getNavigationAdapter(Context context, Navigation navigation) {

        List<NavigationLiveoItemAdapter> mList = new ArrayList<>();
        if (navigation.nameItem == null || navigation.nameItem.size() == 0) {
            throw new RuntimeException(context.getString(R.string.list_null_or_empty));
        }

        int icon;
        int count;
        boolean isHeader;

        for (int i = 0; i < navigation.nameItem.size(); i++) {

            String title = navigation.nameItem.get(i);

            NavigationLiveoItemAdapter mItemAdapter;

            icon = (navigation.iconItem != null ? navigation.iconItem.get(i) : 0);
            isHeader = (navigation.headerItem != null && navigation.headerItem.contains(i));
            count = (navigation.countItem != null ? navigation.countItem.get(i, -1) : -1);

            boolean isVisible = false;
            if(navigation.hideItem != null){
                isVisible = navigation.hideItem.contains(i);
            }

            if (isHeader && icon > 0){
                throw new RuntimeException(context.getString(R.string.value_icon_should_be_0));
            }

            if (!isHeader) {
                if (title == null) {
                    throw new RuntimeException(context.getString(R.string.enter_item_name_position) + i);
                }

                if (title.trim().equals("")) {
                    throw new RuntimeException(context.getString(R.string.enter_item_name_position) + i);
                }
            }else{
                if (title == null) {
                    title = "";
                }

                if (title.trim().equals("")) {
                    title = "";
                }
            }

            mItemAdapter = new NavigationLiveoItemAdapter(title, icon, isHeader, count, navigation.colorSelected, navigation.removeSelector, !isVisible);
            mList.add(mItemAdapter);
        }
        return mList;
    }

    public static List<NavigationLiveoItemAdapter> getNavigationAdapter(Context context, List<HelpItem> listHelpItem, int colorSelected, boolean removeSelector) {

        List<NavigationLiveoItemAdapter> mList = new ArrayList<>();
        if (listHelpItem == null || listHelpItem.size() == 0) {
            throw new RuntimeException(context.getString(R.string.list_null_or_empty));
        }

        int icon;
        int count;
        boolean isHeader;

        for (int i = 0; i < listHelpItem.size(); i++) {

            String title = listHelpItem.get(i).getName();

            NavigationLiveoItemAdapter mItemAdapter;

            icon = listHelpItem.get(i).getIcon();
            isHeader = listHelpItem.get(i).isHeader();
            count = listHelpItem.get(i).getCounter();

            boolean isVisible = listHelpItem.get(i).isHide();

            if (isHeader && icon > 0){
                throw new RuntimeException(context.getString(R.string.value_icon_should_be_0));
            }

            if (!isHeader) {
                if (title == null) {
                    throw new RuntimeException(context.getString(R.string.enter_item_name_position) + i);
                }

                if (title.trim().equals("")) {
                    throw new RuntimeException(context.getString(R.string.enter_item_name_position) + i);
                }
            }else{
                if (title == null) {
                    title = "";
                }

                if (title.trim().equals("")) {
                    title = "";
                }
            }

            mItemAdapter = new NavigationLiveoItemAdapter(title, icon, isHeader, count, colorSelected, removeSelector, !isVisible);
            mList.add(mItemAdapter);
        }
        return mList;
    }
}
