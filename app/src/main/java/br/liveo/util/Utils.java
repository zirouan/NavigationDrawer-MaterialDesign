package br.liveo.util;

import android.content.Context;
import android.widget.Toast;

import br.liveo.navigationliveo.R;

public class Utils {

    public static int[] nameNavigation = new int[] {
            R.string.inbox, R.string.starred, R.string.sent_mail,
            R.string.drafts, R.string.more_markers, R.string.trash,
            R.string.spam};

	public static int[] iconNavigation = new int[] {
            R.drawable.ic_inbox_black_24dp, R.drawable.ic_star_black_24dp, R.drawable.ic_send_black_24dp,
            R.drawable.ic_drafts_black_24dp, 0, R.drawable.ic_delete_black_24dp, R.drawable.ic_report_black_24dp};


	public static String getTitleItem(Context context, int position){
		return context.getString(nameNavigation[position]);
	}

    public static void toastShowShort(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShowLong(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void toastShowShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShowLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
