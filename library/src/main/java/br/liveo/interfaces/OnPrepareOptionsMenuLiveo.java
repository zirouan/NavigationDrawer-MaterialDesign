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
