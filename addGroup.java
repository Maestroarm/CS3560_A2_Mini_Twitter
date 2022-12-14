import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class addGroup extends JPanel {
    private User currentUser;
    private UserGroup root;
    private JTextField id;
    private JButton joinButton;

    public addGroup(){
        this.currentUser = UserView.currentUser;
        this.root = AdminControlPanel.root;
        this.joinButton = buttonJoin();
        this.id = textField();
        render();
    }

    private void render() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Join Group"));
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(300,50));
        add(id);
        add(joinButton);
    }

    private JButton buttonJoin(){
        JButton joinButton = new JButton("Join Group");
        joinButton.setSize(new Dimension(100,50));
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    join(id.getText());
                }catch(NullPointerException n){
                    groupError(false);
                }
            }
        });
        return joinButton;
    }

    private JTextField textField(){
        JTextField joinButton = new JTextField("Group ID");
        joinButton.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                joinButton.setText("");
            }
            public void focusLost(FocusEvent e) {
            }
        });
        return joinButton;
    }

    private void join(String input){
        int groupid = 0;
        if(input!=null){
            try {
                groupid = Integer.parseInt(input);
            } catch(NumberFormatException | NullPointerException e){
                groupError(false);
                return;
            }
            userOrGroup newGroup = root.findUserOrGroup(groupid);
            if(newGroup.isGroup()) {
                int currentGroup = currentUser.getGroupId();
                if(currentGroup==0){ 
                    root.removeAccount(currentUser);
                    ((UserGroup) newGroup).addAccount(currentUser);
                    root.update();
                }
                else if(groupPrompt()){ 
                    userOrGroup oldGroup = root.findUserOrGroup(currentGroup); 
                    ((UserGroup)oldGroup).removeAccount(currentUser);
                    ((UserGroup) newGroup).addAccount(currentUser);
                    root.update();
                }
                else{ 
                    groupError(true);
                }
            } else{
                groupError(false);
            }
        }
    }

    private boolean groupPrompt(){
        JFrame frame = new JFrame();
        int answer = JOptionPane.showConfirmDialog(frame, "Already in a group. Would you like to change?", "Group Selection" ,JOptionPane.YES_NO_CANCEL_OPTION);
        if(answer==JOptionPane.YES_OPTION){
            return true;
        } else{
            return false;
        }
    }

    private void groupError(boolean tf){
        JLabel errorMssg1;

        JFrame error = new JFrame("Error");
        error.setPreferredSize(new Dimension(400,75));
        error.setLayout(new FlowLayout());
        error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if(tf) {
            errorMssg1 = new JLabel("Already in a group");
        } else{
            errorMssg1 = new JLabel("No group with such ID");
        }
        error.add(errorMssg1);

        error.pack();
        error.setVisible(true);
    }
}