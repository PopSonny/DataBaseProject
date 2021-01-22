package interfaceBD;

import dataBase.ConnectionClass;
import tabels.Jucator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class IntraInEchipa extends JFrame {
    private ConnectionClass connec;
    private Jucator player;

    public IntraInEchipa(ConnectionClass connec, Jucator p) throws HeadlessException {
        this.connec = connec;
        this.player = p;
    }

    private JPanel panelMaster = new JPanel();
    private JPanel echipePanel = new JPanel();
    private JPanel parolaPanel = new JPanel();
    private JPanel butoanePanel = new JPanel();

    private JLabel echipeleSuntT = new JLabel("Echipele sunt :");
    private JComboBox<String> echipeCombo = new JComboBox<>();

    private JLabel parolaLabel = new JLabel("Introduceti parola");
    private JPasswordField parolaField = new JPasswordField(10);

    private JButton intraInEchipa = new JButton("Intra in echipa");
    private JButton backButton = new JButton("Back");

    private JLabel titlu = new JLabel("Intra intr-o echipa");
    private JPanel titluPanel = new JPanel();

    private ArrayList<String> echipeStrings = new ArrayList<>();

    public void go() {
        boolean merge = true;
        Statement prepare;
        try {
            String cautare = "select echipa.nume from echipa;";
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            String verificare = "nimic";
            while (rs.next()) {
                verificare = rs.getString(1);
                echipeStrings.add(rs.getString(1));
            }
            if (verificare.equals("nimic")) {
                merge = false;
                JOptionPane.showMessageDialog(null, "Nu avem echipe in baza de date!", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                MeniuUtilizatorBD m = new MeniuUtilizatorBD(connec, player);
                m.go();
            }
            for (String s : echipeStrings) {
                echipeCombo.addItem(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (merge) {
            titlu.setFont(new Font("Arial", Font.BOLD, 30));
            parolaField.setEchoChar('*');
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(500, 400);
            this.setLocationRelativeTo(null);
            this.setResizable(false);

            panelMaster.setLayout(new BoxLayout(panelMaster, BoxLayout.Y_AXIS));

            //echipe
            echipePanel.setLayout(new FlowLayout());
            echipePanel.add(echipeleSuntT);
            echipePanel.add(echipeCombo);

            //parola
            parolaPanel.setLayout(new FlowLayout());
            parolaPanel.add(parolaLabel);
            parolaPanel.add(parolaField);

            //butoane
            butoanePanel.setLayout(new BoxLayout(butoanePanel, BoxLayout.Y_AXIS));
            intraInEchipa.setAlignmentX(Component.CENTER_ALIGNMENT);
            butoanePanel.add(intraInEchipa);
            butoanePanel.add(Box.createRigidArea(new Dimension(0,10)));
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            butoanePanel.add(backButton);


            //titlu
            titluPanel.add(titlu);


            //actionlisteners
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IntraInEchipa.super.dispose();
                    MeniuUtilizatorBD m=new MeniuUtilizatorBD(connec,player);
                    m.go();
                }
            });

            intraInEchipa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String numeEchipa=String.valueOf(getEchipeCombo().getSelectedItem());
                    String parolaEchipa=getParolaField().getText();
                    Statement prepare;
                    boolean merge=true;
                    try{
                        String cautare="select echipa.nume, echipa.passwordE " +
                                "from echipa " +
                                "where echipa.nume='"+numeEchipa+"' and " +
                                "echipa.passwordE='"+parolaEchipa+"';";
                        prepare = connec.getConnection().createStatement();
                        ResultSet rs = prepare.executeQuery(cautare);
                        String verificare = "nimic";
                        while (rs.next()) {
                            verificare=rs.getString(1);
                        }
                        if(verificare.equals("nimic"))
                        {
                            JOptionPane.showMessageDialog(null, "Parola sau echipa gresita!", "Error", JOptionPane.ERROR_MESSAGE);
                            merge=false;
                        }
                        if(merge)
                        {
                            player.setNumeEchipa(numeEchipa);
                            CallableStatement test;
                            test=connec.getConnection().prepareCall(
                                    "{call joinEchipa(?,?,?)}");
                            test.setString(1,numeEchipa);
                            test.setString(2,parolaEchipa);
                            test.setInt(3,player.getIdJucator());
                            test.execute();

                            int idptrjucatorEchipa=0;
                            cautare="select id_echipa from echipa " +
                                    "where nume='"+numeEchipa+"';";
                            prepare = connec.getConnection().createStatement();
                            rs = prepare.executeQuery(cautare);
                            while (rs.next()) {
                                idptrjucatorEchipa=rs.getInt(1);
                            }
                            player.setIdEchipa(idptrjucatorEchipa);


                            IntraInEchipa.super.dispose();
                            MeniuUtilizatorBD m=new MeniuUtilizatorBD(connec,player);
                            m.go();
                            JOptionPane.showMessageDialog(null, "Ai intrat in echipa "+numeEchipa,"Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    catch (Exception f)
                    {
                        f.printStackTrace();
                    }
                }
            });

            titluPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMaster.add(titluPanel);
            panelMaster.add(Box.createRigidArea(new Dimension(0, 20)));
            echipePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMaster.add(echipePanel);
            parolaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMaster.add(parolaPanel);
            butoanePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMaster.add(butoanePanel);
            this.setContentPane(panelMaster);
            this.setVisible(true);
        }
    }

    public ConnectionClass getConnec() {
        return connec;
    }

    public void setConnec(ConnectionClass connec) {
        this.connec = connec;
    }

    public Jucator getPlayer() {
        return player;
    }

    public void setPlayer(Jucator player) {
        this.player = player;
    }

    public JPanel getPanelMaster() {
        return panelMaster;
    }

    public void setPanelMaster(JPanel panelMaster) {
        this.panelMaster = panelMaster;
    }

    public JPanel getEchipePanel() {
        return echipePanel;
    }

    public void setEchipePanel(JPanel echipePanel) {
        this.echipePanel = echipePanel;
    }

    public JPanel getParolaPanel() {
        return parolaPanel;
    }

    public void setParolaPanel(JPanel parolaPanel) {
        this.parolaPanel = parolaPanel;
    }

    public JPanel getButoanePanel() {
        return butoanePanel;
    }

    public void setButoanePanel(JPanel butoanePanel) {
        this.butoanePanel = butoanePanel;
    }

    public JLabel getEchipeleSuntT() {
        return echipeleSuntT;
    }

    public void setEchipeleSuntT(JLabel echipeleSuntT) {
        this.echipeleSuntT = echipeleSuntT;
    }

    public JComboBox<String> getEchipeCombo() {
        return echipeCombo;
    }

    public void setEchipeCombo(JComboBox<String> echipeCombo) {
        this.echipeCombo = echipeCombo;
    }

    public JLabel getParolaLabel() {
        return parolaLabel;
    }

    public void setParolaLabel(JLabel parolaLabel) {
        this.parolaLabel = parolaLabel;
    }

    public JPasswordField getParolaField() {
        return parolaField;
    }

    public void setParolaField(JPasswordField parolaField) {
        this.parolaField = parolaField;
    }

    public JButton getIntraInEchipa() {
        return intraInEchipa;
    }

    public void setIntraInEchipa(JButton intraInEchipa) {
        this.intraInEchipa = intraInEchipa;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    public JLabel getTitlu() {
        return titlu;
    }

    public void setTitlu(JLabel titlu) {
        this.titlu = titlu;
    }

    public JPanel getTitluPanel() {
        return titluPanel;
    }

    public void setTitluPanel(JPanel titluPanel) {
        this.titluPanel = titluPanel;
    }

    public ArrayList<String> getEchipeStrings() {
        return echipeStrings;
    }

    public void setEchipeStrings(ArrayList<String> echipeStrings) {
        this.echipeStrings = echipeStrings;
    }
}
