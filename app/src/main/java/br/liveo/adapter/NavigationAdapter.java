package br.liveo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.liveo.navigationliveo.R;
import br.liveo.util.Constant;

public class NavigationAdapter extends BaseAdapter {
		
	private final Context mcontext;

	public int mInboxCounter = 0;
	private final List<NavigationItemAdapter> mList;	
	
	public NavigationAdapter(Context context,List<NavigationItemAdapter> list) {
		this.mList = list;		
		this.mcontext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public NavigationItemAdapter getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).isHeader ? 0 : 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return !getItem(position).isHeader;
	}

	public void setChecked(int pos, boolean checked) {
		mList.get(pos).checked = checked;
		notifyDataSetChanged();			
	}

	public void resetarCheck() {
		for (int i = 0; i < mList.size(); i++) {
			mList.get(i).checked = false;
		}
		this.notifyDataSetChanged();
	}

	public void setInboxCounter(int count){
		mInboxCounter = count;
		mList.get(Constant.INBOX).counter = mInboxCounter;
		notifyDataSetChanged();
	}
	
	class ViewHolder {
		public TextView title;
		public ImageView icon;
        public TextView counter;
        public LinearLayout listItem;

		public ViewHolder(){
		}
	}

    private void setAlpha(View v, float alpha) {
        v.setAlpha(alpha);
    }


	public View getView(int position, View convertView, ViewGroup parent) {

		NavigationItemAdapter item = mList.get(position);
		ViewHolder holder;
		
		if (convertView == null) {
			holder = new ViewHolder();
			
			int layout = ((item.isHeader) ? R.layout.navigation_list_item_sub_header
					                      : (item.icon != 0) ? R.layout.navigation_list_item_icon :
                                                               R.layout.navigation_list_item);
			
			convertView = LayoutInflater.from(mcontext).inflate(layout, null);			
			
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.counter = (TextView) convertView.findViewById(R.id.counter);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.listItem = (LinearLayout) convertView.findViewById(R.id.listItem);

            convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder.title != null){
			holder.title.setText(item.title);

            if (!item.isHeader) {
                setAlpha(holder.title, (item.checked ? 1f : 0.87f));
            }
		}

		if (holder.counter != null) {
			if (item.counter >= 0) {
				holder.counter.setVisibility(View.VISIBLE);
				holder.counter.setText(item.counter + "");
			} else {
				holder.counter.setVisibility(View.GONE);
			}
		}
		
		if (holder.icon != null) {
			if (item.icon != 0) {
				holder.icon.setVisibility(View.VISIBLE);
				holder.icon.setImageResource(item.icon);
                setAlpha(holder.icon, (!item.isHeader && item.checked ? 1f : 0.54f));

			} else {
				holder.icon.setVisibility(View.GONE);
			}
		}
	
		if (!item.isHeader) {			
			if (item.checked) {
                convertView.setBackgroundResource(R.drawable.seletor_check_item_navigation);
			} else {
                convertView.setBackgroundResource(R.drawable.seletor_no_check_item_navigation);
			}
		}

	    return convertView;		
	}
}
