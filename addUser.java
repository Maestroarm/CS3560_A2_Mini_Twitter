import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class addUser extends JPanel{
    private UserGroup root;
    private static addUser instance = null;
    private JTextField name, group;
    private JButton addUser, addGroup;

    private addUser(){
        this.root = AdminControlPanel.root;
        this.name = nameField();
        this.group = groupField();
        this.addUser = buttonAddUser();
        this.addGroup = buttonAddGroup();
        render();
    }

    public static addUser getInstance(){
        if (instance == null) {
            synchronized (addUser.class) {
                if (instance == null) {
                    instance = new addUser();
                }
            }
        }
        return instance;
    }

    private void render() {
        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(300,650));

        JPanel topField = new JPanel();
        JPanel bottomField = new JPanel();

        topField.add(name);
        topField.add(addUser);
        bottomField.add(group);
        bottomField.add(addGroup);
        add(topField);
        add(bottomField);
    }

    private JTextField nameField(){
        JTextField username = new JTextField("Username");
        username.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                username.setText("");
            }
            public void focusLost(FocusEvent e) {
            }
        });
        username.setPreferredSize(new Dimension(200,50));
        username.setMaximumSize(new Dimension(500, 50));
        username.setMinimumSize(new Dimension(300, 50));
        return username;
    }

    private JTextField groupField(){
        JTextField groupname = new JTextField("Group Name");
        groupname.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                groupname.setText("");
            }
            public void focusLost(FocusEvent e) {
            }
        });
        groupname.setPreferredSize(new Dimension(200,50));
        groupname.setMaximumSize(new Dimension(500, 50));
        groupname.setMinimumSize(new Dimension(300, 50));
        return groupname;
    }

    private JButton buttonAddUser(){
        JButton addUser = new JButton("Add User");
        addUser.setSize(new Dimension(100,50));
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUser(name.getText());
            }
        });
        return addUser;
    }

    private JButton buttonAddGroup(){
        JButton addGroup = new JButton("Add Group");
        addGroup.setSize(new Dimension(100,50));
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGroup(group.getText());
            }
        });
        return addGroup;
    }

    private void setUser(String name){
        Random rand = new Random();
        int id = 0;
        userOrGroup user = null;

        do{
            id = rand.nextInt(9000);
            user = root.findUserOrGroup(id);
        } while(user!=null);

        user = new User(id);
        user.setName(name);
        root.addAccount(user);
        user.setCreationTime(System.currentTimeMillis());
        ((User) user).setLastUpdateTime(System.currentTimeMillis());
    }

    private void setGroup(String name){
        Random rand = new Random();
        int id = 0;
        userOrGroup group = null;

        do{
            id = rand.nextInt(9000); 
            group = root.findUserOrGroup(id);
        } while(group!=null);

        group = new UserGroup(id);
        group.setName(name);
        root.addAccount(group);
        root.addAccount(group);
        group.setCreationTime(System.currentTimeMillis());
    }
}
