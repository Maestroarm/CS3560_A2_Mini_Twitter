import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class followUser extends JPanel {
    private JTextField id;
    private JButton followButton;
    private User currentUser;
    private userOrGroup root;

    public followUser(){
        this.currentUser = UserView.currentUser;
        this.root = AdminControlPanel.root;
        this.followButton = buttonFollow();
        this.id = followField();
        render();
    }

    private void render() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Follow User"));
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(300,50));
        add(id);
        add(followButton);
    }

    private JButton buttonFollow(){
        JButton followText = new JButton("Follow User");
        followText.setSize(new Dimension(100,50));
        followText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    follow(id.getText());
                }catch(NullPointerException n){
                    userNotFound();
                }
            }
        });
        return followText;
    }

    private JTextField followField(){
        JTextField followText = new JTextField("User ID");
        followText.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                followText.setText("");
            }
            public void focusLost(FocusEvent e) {
            }
        });
        return followText;
    }

    private void follow(String input){
        int inputVal = 0;
        if(input!=null){
            try {
                inputVal = Integer.parseInt(input);
            } catch(NullPointerException | NumberFormatException e){
                userNotFound();
                return;
            }
            userOrGroup foundUser = root.findUserOrGroup(inputVal); 
            if(foundUser.isUser() && foundUser.getId()!=currentUser.getId()){
                if(!currentUser.isFollowing(foundUser.getId())) currentUser.followUser((User) foundUser);
                else followError();
            }
            else{
                userNotFound();
            }
        }
    }

    private void userNotFound(){
        JFrame error = new JFrame("Error");
        error.setPreferredSize(new Dimension(400,75));
        error.setLayout(new FlowLayout());
        error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel errorMssg1 = new JLabel("No user with such ID");
        error.add(errorMssg1);

        error.pack();
        error.setVisible(true);
    }

    private void followError(){
        JFrame error = new JFrame("Error");
        error.setPreferredSize(new Dimension(400,75));
        error.setLayout(new FlowLayout());
        error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel errorMssg1 = new JLabel("Already following the user");
        error.add(errorMssg1);

        error.pack();
        error.setVisible(true);
    }
}