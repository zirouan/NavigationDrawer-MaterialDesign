Navigation drawer (library)
===============================

It requires 8+ API and android support v7 21.0.3 (Toolbar)

<b>Google Play:</b> <a href="https://play.google.com/store/apps/details?id=br.liveo.navigationliveo" target="_blank">NavigationDrawer Live-O</a>

How to use? Very simple! : D

#How to add to your project

#<b>Gradle</b>

```groovy
dependencies {
        compile 'br.com.liveo:navigationdrawer-material:1.0.0'
}
```
#<b>Maven</b>

```groovy
<dependency>
  <groupId>br.com.liveo</groupId>
  <artifactId>navigationdrawer-material</artifactId>
  <version>1.0.0</version>
  <type>aar</type>
</dependency>
```

<b>In your styles.xml choose your version:</b>

    <!--Customize here the subject of your application-->
```groovy
    <style name="myTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/nliveo_blue_colorPrimary</item>
        <item name="colorPrimaryDark">@color/nliveo_blue_colorPrimaryDark</item>
        <item name="colorAccent">@color/nliveo_blue_colorPrimary</item>
    </style>
````
    <!--Here will be the theme of the class that will extends NavigationLiveo-->
```groovy
    <style name="nLiveoDrawer" parent="nLiveo.Theme.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/nliveo_blue_colorPrimary</item>
        <item name="colorPrimaryDark">@color/nliveo_blue_alpha_colorPrimaryDark</item>
        <item name="colorAccent">@color/nliveo_blue_colorPrimary</item>
    </style>
```
<b>Also there is nLiveo.Theme.Light theme<b><br>

<a href="https://gist.github.com/rudsonlive/5f4001ac00fcd4dfc1a4" target="_blank">Available colors</a>

note: colorPrimaryDark property theme "nLiveoDrawer" should receive a color with alpha eg # 80RRGGBB - The "# 80" will ensure the transparency of statusBar.

<b>Remember to set your theme in your AndroidManifest.xml:</b>

```groovy
    <application
    <!--Theme of your application-->
        android:theme="@style/myTheme" >
```      
```groovy
        <activity
    <!--Theme of the class that will extends NavigationLiveo-->        
            android:name=".MainActivity"
            android:theme="@style/nLiveoDrawer"
        </activity>
    </application>
````

#In your Activity...

<b>Create a class and it extends the NavigationLiveo and implement the NavigationLiveoListener.</b>

Ex: public class MainActivity extends NavigationLiveo implements NavigationLiveoListener {

<b>In the method "onUserInformation" report user data logged</b>

```groovy
    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText("Rudson Lima");
        this.mUserEmail.setText("rudsonlive@gmail.com");
        this.mUserPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.mUserBackground.setImageResource(R.drawable.ic_user_background);
    }
````

#Do not use the method "onCreate" and "setContentView" of your Activity, you will use the method "onInt"

<b>In the method "onInt" inform the items on your list</b>

```groovy
@Override
    public void onInt(Bundle savedInstanceState) {
        //Creation of the list items is here

        // set listener {required}
        this.setNavigationListener(this);

        // name of the list items
        List<String> mListNameItem = new ArrayList<>();
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
        mListIconItem.add(1, 0); //Item no icon set 0
        mListIconItem.add(2, 0); //Item no icon set 0
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
        mSparseCounterItem.put(6, 250);

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer(R.string.settings, R.drawable.ic_settings_black_24dp);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }
````

<b>More methods</b><br>
<a href="https://gist.github.com/rudsonlive/759a2c554a5d34d8dd05" target="_blank">Other methods</a> <br>


<br>In the method "onItemClickNavigation" you can get the position of the clicked item and the layout that you must inform the replace fragment</b>

```groovy
    @Override //The "layoutContainerId" should be used in "beginTransaction (). Replace"
    public void onItemClickNavigation(int position, int layoutContainerId) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = new FragmentMain().newInstance(mListNameItem.get(position));

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
        }
    }
````

It has the same functionality as public boolean onPrepareOptionsMenu(Menu menu) and the example was used to hide the fragment menus. <br>

```groovy
    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {

        //hide the menu when the navigation is opens
        switch (position) {
            case 0:
                menu.findItem(R.id.menu_add).setVisible(!visible);
                menu.findItem(R.id.menu_search).setVisible(!visible);
                break;

            case 1:
                menu.findItem(R.id.menu_add).setVisible(!visible);
                menu.findItem(R.id.menu_search).setVisible(!visible);
                break;
        }
    }
```    

User photo onClick <br>
```groovy
    @Override
    public void onClickUserPhotoNavigation(View v) {}
```
Footer onClick <br>
```groovy
    @Override
    public void onClickFooterItemNavigation(View v) {}
```

<img src="https://raw.githubusercontent.com/rudsonlive/NavigationDrawer-MaterialDesign/master/Screenshot/Screenshot_01.png"> 

<img src="https://raw.githubusercontent.com/rudsonlive/NavigationDrawer-MaterialDesign/master/Screenshot/Screenshot_02.png"> 

<b>Your app uses this library? You can promote it here! Just send your app that'll be happy to disclose.</b> <br>

<br>
<b>Duvida ou sugestões (Questions or suggestions)</b> <br>
Nome: Rudson Lima <br>
E-mail: rudsonlive@gmail.com<br>
Assunto: Navigation Drawer - Material Design
<br>

<b>When using the design please remove all images and strings referring to Live-O. Thank you: D <br></b>

#License
```
  Copyright 2015 Rudson Lima
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 
````

<br>
source:<br> 
<a href="http://www.google.com/design/spec/patterns/navigation-drawer.html" target="_blank">NavigationDrawer</a>
