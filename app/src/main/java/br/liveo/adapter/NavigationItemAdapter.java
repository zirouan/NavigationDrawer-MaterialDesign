package br.liveo.adapter;

public class NavigationItemAdapter {

	public String title;
	public int counter;
	public int icon;
	public boolean isHeader;
	public boolean checked = false;

	public NavigationItemAdapter(String title, int icon, boolean header, int counter) {
		this.title = title;
		this.icon = icon;
		this.isHeader = header;
		this.counter = counter;
	}
}
