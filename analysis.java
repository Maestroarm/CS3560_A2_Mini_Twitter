import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class analysis extends JPanel{
    private static analysis instance = null;
    private UserGroup root; 
    protected JButton userTotal, groupTotal, messagesTotal, posPercent;

    public analysis(){
        this.root = AdminControlPanel.root;
        this.userTotal = buttonuserTotal();
        this.groupTotal = buttongroupTotal();
        this.messagesTotal = buttonMessageTotal();
        this.posPercent = buttonPosPercent();
        render();
    }

    public static analysis getInstance(){
        if(instance==null){
            synchronized (analysis.class) {
                if (instance == null) {
                    instance = new analysis();
                }
            }
        }
        return instance;
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Analysis Options"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMinimumSize(new Dimension(100, 100));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        buttons.add(userTotal);
        buttons.add(groupTotal);
        buttons.add(messagesTotal);
        buttons.add(posPercent);
        add(buttons);
    }

    private JButton buttonuserTotal(){
        JButton userTotal = new JButton("Show User Total");
        userTotal.setActionCommand("userTotal");
        userTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("Active user: %s ", visitRoot(new userVisitor()));
                showMessage(message);
            }
        });
        return userTotal;
    }

    private JButton buttongroupTotal(){
        JButton groupTotal = new JButton("Show User Group Total");
        groupTotal.setActionCommand("groupTotal");
        groupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("Active groups: %s ", visitRoot(new groupVisitor()));
                showMessage(message);
            }
        });
        return groupTotal;
    }

    private JButton buttonMessageTotal(){
        JButton messageTotal = new JButton("Show Messages Total");
        messageTotal.setActionCommand("messagesTotal");
        messageTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("Total tweets: %s ", visitRoot(new messageVisitor()));
                showMessage(message);
            }
        });
        return messageTotal;
    }

    private JButton buttonPosPercent(){
        JButton posPercent = new JButton("Show Positive Percentage");
        posPercent.setActionCommand("posPercentage");
        posPercent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double posMssg = visitRoot(new posMessageVisitor());
                double totalMssg = visitRoot(new messageVisitor());
                double total = (posMssg/totalMssg) * 100;
                String message = String.format("Positive messages: %s%s ", total, "%");
                showMessage(message);
            }
        });
        return posPercent;
    }

    private void showMessage(String message){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, message);
    }

    public double visitRoot(Visitor v){
        double total = 0.0;
        for(userOrGroup account : root.getGroup()){
            total += account.accept(v);
        }
        return total;
    }
}
