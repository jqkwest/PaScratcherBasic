package pascractherbasic.dallsoft.com.com;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pascractherbasic.dallsoft.com.pascratcherbasic.R;


public class MainActivity extends Activity {

    private final String TAG = "DSoft";
    public ListView mainListView;
    public ArrayAdapter<TicketItem> adapter;
    public List<TicketItem> ticketItemList = new ArrayList<TicketItem>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        mainListView = (ListView) findViewById(R.id.ticketListView);

        adapter = new TicketItemAdapter(this, android.R.layout.simple_list_item_1, ticketItemList);
        mainListView.setAdapter(adapter);
        //  String siteURL = "http://xmlhost.x10host.com/xmllottery.xml";
       // String x = "http://xmlhost.savethis.net/xmllotteryBASIC.xml";
        String siteURL = "http://xmlhost.savethis.net/xmllotteryBASIC.xml";
        new RetrieveFeedTask().execute(siteURL);


        AdView adView =(AdView) this.findViewById(R.id.adMob);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                //test mode on DEVICE (this example code must be replaced with your device uniquq ID)
              //  .addTestDevice("2EAB96D84FE62876379A9C030AA6A0AC") // Nexus 5
                .addTestDevice("55703D931A2950C5BDC69164719D6146")
                .build();
        adView.loadAd(adRequest);


    }





    public List<TicketItem> parseRSS(URL feedURL)
            throws XmlPullParserException, IOException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(feedURL.openStream(), null);

        int eventType = parser.getEventType();

        boolean done = false;

        TicketItem currentTicketItem= new TicketItem();

        while (eventType != XmlPullParser.END_DOCUMENT && !done) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("game")) {
                        // a new item element
                        currentTicketItem = new TicketItem();
                    } else if (currentTicketItem != null) {
                        if (name.equalsIgnoreCase("link")) {
                            currentTicketItem.setLink(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("prizesLeft")) {

                            // currentTicketItem.setPrizeInfo(parser.nextText() + "  prizes at ");
                            currentTicketItem.setPrizeInfo(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("prizesLeftA")){
                            currentTicketItem.setPrizeA(parser.nextText());

                            }
                        else if (name.equalsIgnoreCase("prizesLeftB")){
                            currentTicketItem.setPrizeB(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("prizesLeftC")){
                            currentTicketItem.setPrizeC(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("prizeValueA")){
                            currentTicketItem.setValueA(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("prizeValueB")){
                            currentTicketItem.setValueB(parser.nextText());

                        }
                        else if (name.equalsIgnoreCase("prizeValueC")){
                            currentTicketItem.setValueC(parser.nextText());

                        }



                        else if (name.equalsIgnoreCase("cost")) {
                            currentTicketItem.setCost(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("prizeValue")){
                            currentTicketItem.setPrizeInfo(parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("gamenumber")) {
                            currentTicketItem.setGameNumber(parser.nextText());
                        } else if (name.equalsIgnoreCase("name")) {
                            currentTicketItem.setTitle(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("game") && currentTicketItem != null) {
                        ticketItemList.add(currentTicketItem);
                    } else if (name.equalsIgnoreCase("ticket")) {
                        done = true;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return ticketItemList;
    }



    //------- AsyncTask ------------//
    class RetrieveFeedTask extends AsyncTask<String, Integer, Integer> {

        protected Integer doInBackground(String... urls) {
            try {
                URL feedURL = new URL(urls[0]);
                ticketItemList = parseRSS(feedURL);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //  adapter.notifyDataSetChanged();   changing the adapter from the background thread not the UI thread.... handler perhaps?
            return 0;

        }

        protected void onPostExecute(Integer result) {
            adapter.notifyDataSetChanged();
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_newfirst:
                Toast.makeText(getApplicationContext(), "Sorting by Newest Tickets first" , Toast.LENGTH_LONG).show();

                Collections.sort(ticketItemList, TicketItem.GameNumberComparator);
                mainListView.smoothScrollToPosition(0);
                adapter.notifyDataSetChanged();
                // search action
                return true;
            case R.id.action_abc:
                // alphabetically


                Collections.sort(ticketItemList, TicketItem.GameNameComparator );
                mainListView.smoothScrollToPosition(0);

                adapter.notifyDataSetChanged();





                Toast.makeText(getApplicationContext(), "Sorting Alphabetically" , Toast.LENGTH_LONG).show();

                //	LocationFound();
                return true;
            case R.id.action_cost:


                Collections.sort(ticketItemList, TicketItem.CostComparator );
                mainListView.smoothScrollToPosition(0);

                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Sorting by Cost" , Toast.LENGTH_LONG).show();
                // refresh
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }





        //   return super.onOptionsItemSelected(item);
    }





    // Take appropriate action for each action item click





    /**
     * Launching new activity
     * */



   protected void onStart(){
       super.onStart();
       Log.d(TAG,"onStart()");


    }


    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
    }
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause()");


    }


    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop()");

    }
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
    }




}
