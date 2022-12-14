public interface userOrGroup {

    void setGroupId(int id);
    void setName(String name); 
    int getId(); 
    int getGroupId();
    double accept(Visitor v);
    String getName();
    String toString(); 
    boolean isUser();
    boolean isGroup();
    userOrGroup findUserOrGroup(int id);
}