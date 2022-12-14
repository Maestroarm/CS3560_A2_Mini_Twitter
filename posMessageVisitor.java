import java.util.ArrayList;

public class posMessageVisitor implements Visitor {
    private String[] posWords = {"good", "great", "excellent"};

    @Override
    public double visitUser(User user) {
        double posTotal = 0.0;
        ArrayList<String> userTweets = user.getTweets();
        for(String tweet : userTweets){
            for(String pos : posWords){
                if(tweet.contains(pos)){
                    posTotal++;
                    break;
                }
            }
        }
        return posTotal;
    }
    @Override
    public double visitGroup(UserGroup group) {
        double total = 0.0;
        for(userOrGroup acc : group.getGroup()){
            total+=acc.accept(this);
        }
        return total;
    }
}