import javax.swing.*;
import java.awt.*;

// centralized admin control panel to create usersand user groups
public class AdminControlPanel{
    private static AdminControlPanel instance = null;
    static UserGroup root;

    private AdminControlPanel() {
        root = new UserGroup(0);
        root.setName("Root");
        render();
    }

    public static AdminControlPanel getInstance() {
        if (instance == null) {
            synchronized (AdminControlPanel.class) {
                if (instance == null) {
                    instance = new AdminControlPanel();
                }
            }
        }
        return instance;
    }

    private static void render() {

        JFrame frame = new JFrame("Mini Twitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800,600));
        frame.setPreferredSize(new Dimension(800,600));
        frame.setLayout(new GridLayout(2,2));

        frame.add(userViewTree.getInstance());
        frame.add(addUser.getInstance());
        frame.add(analysis.getInstance());

        frame.pack();
        frame.setVisible(true);
    }
}