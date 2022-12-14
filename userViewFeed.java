import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class userViewFeed extends JPanel implements Observer {
    private JScrollPane newsFeed;
    private User currentUser;
    private JLabel lastUpdate;

    public userViewFeed(){
        this.currentUser = UserView.currentUser;
        currentUser.addObserver(this);
        this.newsFeed = textFeed();
        this.lastUpdate = lastUpdate();
        render();
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "News Feed"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(500, 200));
        add(newsFeed);
        add(lastUpdate);
    }

    private JScrollPane textFeed(){
        JTextArea area = new JTextArea(10,35);
        area.setEditable(false);
        area.setPreferredSize(new Dimension(200,200));
        ArrayList<String> currentTweets = currentUser.getDisplayFeed();
        if(currentTweets != null){
            for(String tweet : currentTweets){
                area.append(tweet);
            }
        }
        JScrollPane tweetView = new JScrollPane(area);
        setLayout(new BoxLayout(tweetView, BoxLayout.LINE_AXIS));
        tweetView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return tweetView;
    }

    private JLabel lastUpdate(){
        SimpleDateFormat title = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
        Date date = new Date(currentUser.getLastUpdateTime());
        return new JLabel(String.format("Last Update: %s", title.format(date)));
    }

    @Override
    public void update(Observable o, Object tweet) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.newsFeed = textFeed();
        currentUser.setLastUpdateTime(System.currentTimeMillis());
        this.lastUpdate = lastUpdate();
        render();
    }
}