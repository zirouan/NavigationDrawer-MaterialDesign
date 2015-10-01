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

package br.liveo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.liveo.navigationliveo.R;


public class NavigationLiveoAdapter extends BaseAdapter {

    private int mColorIcon = 0;
    private int mColorName = 0;
    private int mColorCounter = 0;
    private int mColorSeparator = 0;
    private int mColorSubHeader = 0;

    private int mNewSelector = 0;
    private int mColorDefault = 0;
	private final Context mcontext;
    private int mSelectorDefault = 0;
    private boolean mRemoveAlpha = false;
    private boolean mRemoveColorFilter = false;
    private final List<NavigationLiveoItemAdapter> mList;
	
	public NavigationLiveoAdapter(Context context, List<NavigationLiveoItemAdapter> list, List<Boolean> extraRemove, List<Integer> extra) {
		this.mList = list;		
		this.mcontext = context;
        this.mNewSelector = extra.get(0);
        this.mColorDefault = extra.get(1);
        this.mColorIcon = extra.get(2);
        this.mColorName = extra.get(3);
        this.mColorSeparator = extra.get(4);
        this.mColorCounter = extra.get(5);
        this.mSelectorDefault = extra.get(6);
        this.mColorSubHeader = extra.get(7);
        this.mRemoveAlpha = extraRemove.get(0);
        this.mRemoveColorFilter = extraRemove.get(1);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public NavigationLiveoItemAdapter getItem(int position) {
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
		return mList.get(position).isVisible ? 0 : 1;
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

    public void setNewName(int position, String name){
        mList.get(position).title = name;
        notifyDataSetChanged();
    }

    public void setNewIcon(int position, int icon){
        mList.get(position).icon = icon;
        notifyDataSetChanged();
    }

    public void setNewCounterValue(int position, int value){
        mList.get(position).counter = value;
        notifyDataSetChanged();
    }

    public void setNewInformationItem(int position, String name, int icon, int counter){
        mList.get(position).title = name;
        mList.get(position).icon = icon;
        mList.get(position).counter = counter;
        notifyDataSetChanged();
    }

	public void setIncreasingCounterValue(int position, int value){
		mList.get(position).counter = ( mList.get(position).counter + value);
		notifyDataSetChanged();
	}

    public void setDecreaseCountervalue(int position, int value){
        mList.get(position).counter = ( mList.get(position).counter - value);
        notifyDataSetChanged();
    }

    public void setVisibleItemNavigation(int position, boolean visible){
        mList.get(position).isVisible = visible;
        notifyDataSetChanged();
    }

    class ViewHolder {
		public TextView title;
		public ImageView icon;
        public TextView counter;

        public View separator;

        public RelativeLayout layoutDados;
        public LinearLayout layoutSeparator;

		public ViewHolder(){
		}
	}

    private void setAlpha(View v, float alpha) {
        v.setAlpha(!mRemoveAlpha ? alpha : 1f);
    }

	@SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        holder = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.navigation_list_item, parent, false);

        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.counter = (TextView) convertView.findViewById(R.id.counter);
        holder.icon = (ImageView) convertView.findViewById(R.id.icon);

        holder.separator = convertView.findViewById(R.id.separator);

        holder.layoutDados = (RelativeLayout) convertView.findViewById(R.id.layoutDados);
        holder.layoutSeparator = (LinearLayout) convertView.findViewById(R.id.layoutSeparator);

        convertView.setTag(holder);

        NavigationLiveoItemAdapter item = mList.get(position);

		if (holder.title != null){
            holder.title.setText(item.title);

            if (!item.isHeader) {
                holder.layoutSeparator.setVisibility(View.GONE);

                int type = getItemViewType(position);
                holder.layoutDados.setVisibility(type == 0 ? View.VISIBLE : View.GONE);

                setAlpha(holder.title, (item.checked ? 1f : 0.87f));
            }else{
                holder.layoutSeparator.setVisibility(View.VISIBLE);

                if (mColorSeparator > 0){
                    holder.separator.setBackgroundResource(mColorSeparator);
                }

                if (item.title.equals("")){
                    holder.title.setVisibility(View.GONE);
                    holder.layoutDados.setVisibility(View.GONE);
                }else{
                    holder.layoutDados.setVisibility(View.VISIBLE);
                }
            }

            holder.title.setTextColor((!item.isHeader && item.checked && item.colorSelected > 0 ?
                    ContextCompat.getColor(mcontext, item.colorSelected) :
                    mColorDefault != 0 ? ContextCompat.getColor(mcontext, mColorDefault) :
                    mColorName > 0 ? ContextCompat.getColor(mcontext, mColorName) :
                    (item.isHeader && mColorSubHeader > 0) ? ContextCompat.getColor(mcontext, mColorSubHeader) :
                                    ContextCompat.getColor(mcontext, R.color.nliveo_black)));
		}

		if (holder.counter != null) {
			if (item.counter >= 1) {
                setAlpha(holder.counter, (item.checked ? 1f : 0.87f));
				holder.counter.setVisibility(View.VISIBLE);
                holder.counter.setText((item.counter > 99) ? "99+" : item.counter + "");

                holder.counter.setTextColor((!item.isHeader && item.checked && item.colorSelected > 0 ?
                        ContextCompat.getColor(mcontext, item.colorSelected) :
                        mColorDefault != 0 ? ContextCompat.getColor(mcontext, mColorDefault) :
                                mColorCounter > 0 ?
                                        ContextCompat.getColor(mcontext, mColorCounter) :
                                        ContextCompat.getColor(mcontext, R.color.nliveo_black)));

            } else {
				holder.counter.setVisibility(View.GONE);

                if (item.counter < 0){
                    setNewCounterValue(position, 0);
                }
			}
		}
		
		if (holder.icon != null) {
			if (item.icon != 0) {
				holder.icon.setVisibility(View.VISIBLE);
				holder.icon.setImageResource(item.icon);
                setAlpha(holder.icon, (!item.isHeader && item.checked ? 1f : 0.54f));

                if (!this.mRemoveColorFilter) {
                    holder.icon.setColorFilter((!item.isHeader && item.checked && item.colorSelected > 0 ?
                            ContextCompat.getColor(mcontext, item.colorSelected) :
                            (mColorDefault != 0 ? ContextCompat.getColor(mcontext, mColorDefault) :
                                    mColorIcon > 0 ? ContextCompat.getColor(mcontext, mColorIcon) :
                                            ContextCompat.getColor(mcontext, R.color.nliveo_black))));
                }
			} else {
				holder.icon.setVisibility(View.GONE);
			}
		}
	
		if (!item.isHeader) {			
			if (item.checked) {
                convertView.setBackgroundResource((!item.removeSelector ? ( mNewSelector == 0 ? R.drawable.selector_check_item_navigation : mNewSelector) : R.drawable.selector_no_item_navigation));
			} else {
                convertView.setBackgroundResource(mSelectorDefault == 0 ? R.drawable.selector_no_check_item_navigation : R.drawable.selector_no_item_navigation);
			}
		}else{
            convertView.setBackgroundResource(mSelectorDefault == 0 ? R.drawable.selector_no_check_item_navigation : mSelectorDefault);
        }

	    return convertView;		
	}
}
