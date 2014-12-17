package pascractherbasic.dallsoft.com.com;

/**
 * Created by jkwest on 12/15/2014.
 */
import java.util.*;

/**
 * Created by jkwest on 11/20/2014.
 */
public class TicketItem implements Comparable
{

    @Override
    public int compareTo(Object p1)
    {
        // TODO: Implement this method
        return 0;
    }


    private String link;
    private String cost;
    private String gameNumber;
    private String prizeInfo = "";
    private String title;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGameNumber() {
        return gameNumber;
    }

    public void setCost(String cost){
        this.cost = cost;

    }
    public String getCost(){
        return cost;
    }

    public void setGameNumber(String strGameNumber) {

        this.gameNumber = (strGameNumber);
    }

    public String getPrizeInfo() {
        return prizeInfo.trim();
    }

    public void setPrizeInfo(String prizeInfo) {
        this.prizeInfo = this.prizeInfo + prizeInfo + " ";
        //  this.prizeInfo = prizeInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }


    /*Comparator for sorting the list by Game Name*/
    public static Comparator<TicketItem> GameNameComparator = new Comparator<TicketItem>() {

        public int compare(TicketItem s1, TicketItem s2) {
            String GameName1 = s1.getTitle().toUpperCase();
            String GameName2 = s2.getTitle().toUpperCase();

            //ascending order
            return GameName1.compareTo(GameName2);

            //descending order
            //return GameName2.compareTo(GameName1);
        }};


    /*Comparator for sorting the list by Game Number... newest first*/
    public static Comparator<TicketItem> GameNumberComparator = new Comparator<TicketItem>() {

        public int compare(TicketItem s1, TicketItem s2) {
            int GameNumber1 = Integer.parseInt(s1.getGameNumber());
            int GameNumber2 = Integer.parseInt(s2.getGameNumber());



            //ascending order
            //	return GameNumber1.compareTo(GameNumber2);

            //descending order




            return GameNumber2-GameNumber1;

        }};

    public static Comparator<TicketItem> CostComparator = new Comparator<TicketItem>() {

        public int compare(TicketItem s1, TicketItem s2) {
            int gameCost1 = Integer.parseInt(s1.getCost());
            int gameCost2 = Integer.parseInt(s2.getCost());



            //ascending order
            //	return GameNumber1.compareTo(GameNumber2);

            //descending order




            return gameCost2-gameCost1;

        }};

}
