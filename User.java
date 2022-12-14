import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class User extends Observable implements Observer, userOrGroup {
    private int uniqueID;
    private int groupId;
    private String name;
    private ArrayList<String> newsFeed;
    private ArrayList<String> displayFeed;
    private ArrayList<userOrGroup> followings;
    private long creationTime;
    private long lastUpdateTime;

    public User(int id){
        this.uniqueID = id;
        this.newsFeed = new ArrayList<>();
        this.displayFeed = new ArrayList<>();
        this.followings = new ArrayList<>();
        addObserver(this);
    }

    public void followUser(User user){
        this.followings.add(user);
        user.addObserver(this);
        setChanged();
        notifyObservers();
    }

    public boolean isFollowing(int id){
        for(userOrGroup following: followings){
            if(following.getId() == id) return true;
        } return false;
    }

    public ArrayList<userOrGroup> getFollowing(){
        return followings;
    }

    public void postTweet(String tweet){
        String formattedTweet = String.format("%s: -%s\n", getName(), tweet);
        this.newsFeed.add(formattedTweet);
        setChanged();
        notifyObservers(formattedTweet);
    }

    public void addToDisplay(String tweet){
        this.displayFeed.add(tweet);
        setChanged();
        notifyObservers();
    }

    public ArrayList<String> getDisplayFeed(){
        return displayFeed;
    }

    public ArrayList<String> getTweets(){
        return newsFeed;
    }

    @Override
    public userOrGroup findUserOrGroup(int id){
        if(id==this.uniqueID) return this;
        else return null;
    }

    @Override
    public void setCreationTime(long time){
        this.creationTime = time;
    }

    @Override
    public long getCreationTime(){
        return creationTime;
    }

    public void setLastUpdateTime(long time){
        this.lastUpdateTime = time;
    }

    public long getLastUpdateTime(){
        return lastUpdateTime;
    }

    @Override
    public boolean isUser(){
        return true;
    }

    @Override
    public boolean isGroup(){
        return false;
    }

    @Override
    public int getId() {
        return uniqueID;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(int id) {
        this.groupId = id;
    }

    //@Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        if(getId()==0){
            return "Root";
        } else{
            return String.format("Username: %s (ID: %d)", name, getId());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg!=null){
            addToDisplay((String) arg);
        }
    }

    @Override
    public double accept(Visitor v){
        return v.visitUser(this);
    }
}