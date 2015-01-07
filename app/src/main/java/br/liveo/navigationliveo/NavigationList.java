package br.liveo.navigationliveo;

import android.content.Context;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

import br.liveo.adapter.NavigationItemAdapter;
import br.liveo.util.Utils;

public class NavigationList {

	public static List<NavigationItemAdapter> getNavigationAdapter(
			Context context, List<Integer> listItensHeader, SparseIntArray sparceItensCount) {

        int[] mMenuItems = Utils.nameNavigation;
		List<NavigationItemAdapter> mList = new ArrayList<>();

		int count = -1;
		boolean isheader = false;
		
		for (int i = 0; i < mMenuItems.length; i++) {

			String title = context.getString(mMenuItems[i]);
			NavigationItemAdapter mItemAdapter;

            if (listItensHeader != null) {
                isheader = listItensHeader.contains(i);
            }

			if (sparceItensCount != null) {
				count = sparceItensCount.get(i, -1);
			}

            mItemAdapter = new NavigationItemAdapter(title, Utils.iconNavigation[i], isheader, count);

			mList.add(mItemAdapter);
		}

		return mList;
	}
}
