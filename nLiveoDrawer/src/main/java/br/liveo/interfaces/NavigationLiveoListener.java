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

package br.liveo.interfaces;

import android.view.Menu;
import android.view.View;

public interface NavigationLiveoListener {

    /**
     * Item click Navigation (ListView.OnItemClickListener)
     * @param position position of the item that was clicked.
     * @param layoutContainerId Default layout. Tell the replace - FragmentManager.beginTransaction().replace(layoutContainerId, yourFragment).commit()
     */
    public void onItemClickNavigation(int position, int layoutContainerId);

    /**
     * Prepare options menu navigation (onPrepareOptionsMenu(Menu menu))
     * @param menu menu.
     * @param position last position of the item that was clicked.
     * @param visible use to hide the menu when the navigation is open.
     */
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible);

    /**
     * Click footer item navigation
     * @param v view.
     */
    public void onClickFooterItemNavigation(View v);

    /**
     * Click user photo navigation
     * @param v view.
     */
    public void onClickUserPhotoNavigation(View v);
}
