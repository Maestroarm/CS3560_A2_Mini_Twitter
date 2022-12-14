public class userVisitor implements Visitor{
    @Override
    public double visitUser(User user) {
        return 1.0;
    }

    @Override
    public double visitGroup(UserGroup group){
        double total = 0.0;
        for(userOrGroup acc : group.getGroup()){
            total+=acc.accept(this);
        }
        return total;
    }
}