import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.Observable;
import java.util.Observer;

public class userViewTree extends JPanel implements Observer {
    private static userViewTree instance = null;
    private UserGroup root;
    private JTree userList;
    private JButton userViewButton;
    private userOrGroup selectedUser = null;

    private userViewTree(){
        this.root = AdminControlPanel.root;
        this.userList = createTree();
        this.userViewButton = buttonUserView();
        root.addObserver(this); 
        render();
    }

    public static userViewTree getInstance() {
        if (instance == null) {
            synchronized (userViewTree.class) {
                if (instance == null) {
                    instance = new userViewTree();
                }
            }
        }
        return instance;
    }

    public void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Tree View"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 550));

        JPanel treePane = new JPanel();
        JScrollPane treeView = new JScrollPane(userList);
        treePane.setLayout(new BoxLayout(treePane, BoxLayout.PAGE_AXIS));
        treePane.add(treeView);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(userViewButton);

        add(treePane);
        add(buttonPane);
    }

    private JButton buttonUserView(){
        JButton userView = new JButton("Open User View");
        userView.setActionCommand("userView");
        userView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedUser.isUser()) new UserView((User) selectedUser);
            }
        });
        return userView;
    }

    public JTree createTree(){
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(AdminControlPanel.root);
        createNodes(top, AdminControlPanel.root);
        JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(node!=null){
                    userOrGroup nodeObject = (userOrGroup) node.getUserObject();
                    selectedUser = nodeObject;
                }
            }
        });
        return tree;
    }

    public void createNodes(DefaultMutableTreeNode top, userOrGroup root) {
        DefaultMutableTreeNode group = null;
        DefaultMutableTreeNode name = null;
        if(root.isGroup()){
            for(userOrGroup acc : ((UserGroup)root).getGroup()){
                if(acc.isGroup()){
                    group = new DefaultMutableTreeNode(acc);
                    top.add(group);
                    createNodes(group, acc);
                } else{ 
                    name = new DefaultMutableTreeNode(acc);
                    top.add(name);
                }
            }
        } else{
            name = new DefaultMutableTreeNode(root);
            top.add(name);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.userList = createTree();
        this.removeAll();
        this.revalidate();
        this.repaint();
        render();
    }
}
