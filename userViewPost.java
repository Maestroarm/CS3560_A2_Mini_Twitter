import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class userViewPost extends JPanel {
    private JTextField contents;
    private JButton post;
    private User currentUser;

    public userViewPost(){
        this.contents = fieldTweet();
        this.post = buttonPostTweet();
        this.currentUser = UserView.currentUser;
        render();
    }

    private void render(){
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(500,50));
        add(contents);
        add(post);
    }

    private JTextField fieldTweet(){
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(300,50));
        input.setMaximumSize(new Dimension(250, 50));
        input.setMinimumSize(new Dimension(250, 50));
        return input;
    }

    private JButton buttonPostTweet(){
        JButton post = new JButton("Post Tweet");
        post.setSize(new Dimension(50,20));
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(contents.getText()!=null){
                    String newTweet = contents.getText();
                    currentUser.postTweet(newTweet);
                }
            }
        });
        return post;
    }
}
