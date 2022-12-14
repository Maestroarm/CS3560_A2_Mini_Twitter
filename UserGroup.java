import java.util.ArrayList;
import java.util.Observable;

public class UserGroup extends Observable implements userOrGroup {
    private int uniqueID;
    private int groupId;
    private String name;
    private ArrayList<userOrGroup> entries;
    private long creationTime;

    public UserGroup(){
        this.entries = new ArrayList<>();
    }

    public UserGroup(int id){
        this.entries = new ArrayList<>();
        this.uniqueID = id;
    }

    public void addAccount(userOrGroup acc){
        this.entries.add(acc);
        acc.setGroupId(this.getId());
        setChanged();
        notifyObservers();
    }

    public void removeAccount(userOrGroup acc){
        this.entries.remove(acc);
    }

    public void update(){
        setChanged();
        notifyObservers();
    }

    public ArrayList<userOrGroup> getGroup(){
        return entries;
    }

    @Override
    public userOrGroup findUserOrGroup(int id){
        userOrGroup search = null;
        if(id==this.uniqueID) search = this;
        else{
            for(userOrGroup acc: entries){
                if(acc.findUserOrGroup(id)!=null) search = acc.findUserOrGroup(id);
            }
        }
        return search;
    }

    @Override
    public boolean isUser(){
        return false;
    }

    @Override
    public boolean isGroup(){
        return true;
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

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }
    
    @Override
    public void setCreationTime(long time){
        this.creationTime = time;
    }

    @Override
    public long getCreationTime(){
        return creationTime;
    }

    @Override
    public String toString() {
        if(getId()==0){
            return "Root";
        } else{
            return String.format("Group: %s (id: %d)", name, getId());
        }
    }

    @Override
    public double accept(Visitor v){
        return v.visitGroup(this);
    }
}