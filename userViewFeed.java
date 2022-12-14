import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

public class userViewFeed extends JPanel implements Observer {
    private JScrollPane newsFeed;
    private User currentUser;

    public userViewFeed(){
        this.currentUser = UserView.currentUser;
        currentUser.addObserver(this);
        this.newsFeed = textFeed();
        render();
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "News Feed"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(500, 200));
        add(newsFeed);
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

    @Override
    public void update(Observable o, Object tweet) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.newsFeed = textFeed();
        render();
    }
}