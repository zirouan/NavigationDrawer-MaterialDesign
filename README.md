Source code soon
===============================

Example Navigation drawer material design

Como usar? Muito simples! :D

Class "Utils" - package: br.liveo.util <br>
Altere o "nameNavigation" e "iconNavigation" pois ambos são itens importantes para montagem da lista. Lembrando que os nomes e ícones deveram ter a mesma posição no array. <br>

    public static int[] nameNavigation = new int[] {
            R.string.inbox, R.string.starred, R.string.sent_mail,
            R.string.drafts, R.string.more_markers, R.string.trash,
            R.string.spam}; 
<br>
A string "R.string.more_markers" é referente a uma subHeader, então todos os subHeader receberam 0 como podemos ver na posição 4 do iconNavigation; <br>

Caso queria um item normal sem ícone é só informar 0 no lugar de um ícone. <br>

	public static int[] iconNavigation = new int[] {
            R.drawable.ic_inbox_black_24dp, R.drawable.ic_star_black_24dp, R.drawable.ic_send_black_24dp,
            R.drawable.ic_drafts_black_24dp, 0, R.drawable.ic_delete_black_24dp, 
            R.drawable.ic_report_black_24dp}; <br>


Depois de ter criado o seu "nameNavigation" e "iconNavigation" chegou a hora de de dizer o que é subHeader e quais os itens que teram um contador.

Como podemos ver no método "mountListNavigation" da class NavigationMain, para informar que um item é um subHeader é só criarmos o seguinte: <br>

O código abaixo informa que o item "R.string.more_markers" que tem a posição 4 no "nameNavigation" será um subHeader. <br>

        List<Integer> mListHeader = new ArrayList<>();
        mListHeader.add(4); 

E caso eu queria que um item tenha um contador é só fazer o seguinte: <br>

"R.string.inbox"- posição = 0 - valor do contador = 7 <br>
"R.string.spam"- posição = 6 - valor do contador = 10

        SparseIntArray  mCounter = new SparseIntArray();
        mCounter.put(0, 7);
        mCounter.put(6, 10);
        
Quando precisar atualizar é só usar o método setInboxCounter que se encontrar na class NavigationAdapter. <br>

	public void setInboxCounter(int count){
		mInboxCounter = count;
		mList.get(Constant.INBOX).counter = mInboxCounter;
		notifyDataSetChanged();
	}

Você pode criar esse método para todos os outros item com contadores.<br>	
E pra finalizar é só adicionar o mListHeader e mCounter ao adapter <br>

        mNavigationAdapter = new NavigationAdapter(this, 
        NavigationList.getNavigationAdapter(this, mListHeader, mCounter));
        mList.setAdapter(mNavigationAdapter);

<br>
<img src="https://raw.githubusercontent.com/rudsonlive/NavigationDrawer-MaterialDesign/master/Screenshot/Screenshot_01.png"> 

<img src="https://raw.githubusercontent.com/rudsonlive/NavigationDrawer-MaterialDesign/master/Screenshot/Screenshot_02.png"> 

<br>
<br>
source: http://www.google.com/design/spec/patterns/navigation-drawer.html
