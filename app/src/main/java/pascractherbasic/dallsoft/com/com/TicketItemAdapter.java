package pascractherbasic.dallsoft.com.com;

/**
 * Created by jkwest on 12/15/2014.
 */
/**
 * Created by jkwest on 11/21/2014.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pascractherbasic.dallsoft.com.pascratcherbasic.R;

public class TicketItemAdapter extends ArrayAdapter<TicketItem> {

    private Context context;

    private String prizeA;
    private String prizeB;
    private String priceC;
    private String valueA;
    private String valueB;
    private String valueC;


    public TicketItemAdapter(Context context, int textViewResourceId,
                             List<TicketItem> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ticket_list_view
                    , null);
        }

        TicketItem item = getItem(position);


        // have a drop down boolen if sort == by cost || by alphabet

        if (item != null) {
            // our layout has two TextView elements
            TextView gameNameView = (TextView) view.findViewById(R.id.gameNameText);
            TextView prizeInfoView = (TextView) view
                    .findViewById(R.id.prizeInfoText);


            gameNameView.setText(item.getTitle());
            prizeInfoView.setText((item.getPrizeInfo()));
            Log.d("descripOut", item.getPrizeInfo());




        }


        return view;
    }

    public String  prizeFormatter(String prizeInfoText){


        return "";

    }
}

