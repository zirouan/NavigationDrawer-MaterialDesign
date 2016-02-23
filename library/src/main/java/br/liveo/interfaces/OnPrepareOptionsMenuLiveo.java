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

/**
 * Created by Rudsonlive on 24/05/15.
 */
public interface OnPrepareOptionsMenuLiveo {
    /**
     * Prepare options menu navigation (onPrepareOptionsMenu(Menu menu))
     * @param menu menu.
     * @param position last position of the item that was clicked.
     * @param visible use to hide the menu when the navigation is open.
     */
    public void onPrepareOptionsMenu(Menu menu, int position, boolean visible);

}
