import java.awt.*;
import javax.swing.*;

public class UserView {
    static User currentUser;

    public UserView(){
        currentUser = null;
        renderError();
    }

    public UserView(User user){
        currentUser = user;
        render();
    }

    private void render() {
        JFrame frame = new JFrame(String.format("%s", currentUser));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800,600));
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLayout(new FlowLayout());

        frame.add(new followUser());
        frame.add(new addGroup());
        frame.add(new userViewFollowing());
        frame.add(new userViewPost());
        frame.add(new userViewFeed());

        frame.pack();
        frame.setVisible(true);
    }

    private static void renderError(){
        JFrame frame = new JFrame("User View");
        frame.setPreferredSize(new Dimension(400,75));
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel errorMssg1 = new JLabel("No user selected");
        frame.add(errorMssg1);

        frame.pack();
        frame.setVisible(true);
    }
}