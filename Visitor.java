public interface Visitor {
    double visitUser(User user);
    double visitGroup(UserGroup group);
}