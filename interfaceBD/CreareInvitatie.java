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

public class CreareInvitatie extends JFrame {
    private ConnectionClass connec;
    private Jucator player;
    //true ptr echipa
    //false ptr individual
    private boolean afisare;


    public CreareInvitatie(ConnectionClass connec, Jucator p, boolean a) throws HeadlessException {
        this.connec = connec;
        this.player = p;
        this.afisare = a;
    }

    private JPanel masterPanel = new JPanel();
    private JPanel titluPanel = new JPanel();
    private JPanel alegeriPanel = new JPanel();
    private JPanel butoanePanel = new JPanel();

    private JLabel titluLabel = new JLabel();

    private JLabel numeLabel = new JLabel();
    private JLabel tipJocLabel = new JLabel("Jocuri disponibile");

    private JComboBox<String> numeCombo = new JComboBox<>();
    private JComboBox<String> tipJocCombo = new JComboBox<>();

    private JButton sendInviteButon = new JButton("Trimite invitatia");
    private JButton backButon = new JButton("Back");


    private ArrayList<String> tipuriDeJoc = new ArrayList<>();

    private ArrayList<String> numeEchipaJucator = new ArrayList<>();

    public void go() {

        boolean merge = true;


        Statement prepare;
        try {
            String cautare = "select tipdejoc.numeJoc from tipdejoc;";
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            String verificare = "nimic";
            while (rs.next()) {
                verificare = rs.getString(1);
                tipuriDeJoc.add(rs.getString(1));
            }
            if (verificare.equals("nimic")) {
                merge = false;
                JOptionPane.showMessageDialog(null, "Nu avem jocuri in baza de date!", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                MeniuUtilizatorBD m = new MeniuUtilizatorBD(connec, player);
                m.go();
            }
            if (merge) {
                if (afisare) {
                    cautare = "select echipa.nume from echipa where echipa.nume!='" +
                            "" + player.getNumeEchipa() + "';";
                    prepare = connec.getConnection().createStatement();
                    rs = prepare.executeQuery(cautare);
                    verificare = "nimic";
                    while (rs.next()) {
                        verificare = rs.getString(1);
                        numeEchipaJucator.add(rs.getString(1));
                    }
                    if (verificare.equals("nimic")) {
                        merge = false;
                        JOptionPane.showMessageDialog(null, "Nu avem echipe in baza de date!", "Error", JOptionPane.ERROR_MESSAGE);
                        this.dispose();
                        MeniuUtilizatorBD m = new MeniuUtilizatorBD(connec, player);
                        m.go();
                    }
                } else {

                    cautare = "select jucator.nume from jucator where jucator.nume!='" +
                            "" + player.getNume() + "';";
                    prepare = connec.getConnection().createStatement();
                    rs = prepare.executeQuery(cautare);
                    verificare = "nimic";
                    while (rs.next()) {
                        verificare = rs.getString(1);
                        numeEchipaJucator.add(rs.getString(1));
                    }
                    if (verificare.equals("nimic")) {
                        merge = false;
                        JOptionPane.showMessageDialog(null, "Nu avem jucatori in baza de date!", "Error", JOptionPane.ERROR_MESSAGE);
                        this.dispose();
                        MeniuUtilizatorBD m = new MeniuUtilizatorBD(connec, player);
                        m.go();
                    }
                }

            }

            for (String s1 : numeEchipaJucator) {
                numeCombo.addItem(s1);
            }
            for (String s : tipuriDeJoc) {
                tipJocCombo.addItem(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (merge) {
            if (afisare) {
                titluLabel.setText("Creare invitatie pentru echipa");
                numeLabel.setText("Echipele:");
            } else {
                titluLabel.setText("Creare invitatie pentru 1v1");
                numeLabel.setText("Jucatorii:");
            }
            titluLabel.setFont(new Font("Arial", Font.BOLD, 30));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(600, 400);
            this.setLocationRelativeTo(null);
            this.setResizable(false);

            masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));


            //titlu
            titluPanel.setLayout(new FlowLayout());
            titluPanel.add(titluLabel);

            //jcombobox
            alegeriPanel.setLayout(new FlowLayout());
            alegeriPanel.add(numeLabel);
            alegeriPanel.add(numeCombo);
            alegeriPanel.add(Box.createRigidArea(new Dimension(15, 0)));
            alegeriPanel.add(tipJocLabel);
            alegeriPanel.add(tipJocCombo);

            //butoane
            butoanePanel.setLayout(new BoxLayout(butoanePanel, BoxLayout.Y_AXIS));
            sendInviteButon.setAlignmentX(Component.CENTER_ALIGNMENT);
            butoanePanel.add(sendInviteButon);
            butoanePanel.add(Box.createRigidArea(new Dimension(0, 10)));
            backButon.setAlignmentX(Component.CENTER_ALIGNMENT);
            butoanePanel.add(backButon);


            titluPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            masterPanel.add(titluPanel);
            alegeriPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            masterPanel.add(alegeriPanel);
            butoanePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            masterPanel.add(butoanePanel);

            //actionlisteners
            sendInviteButon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String numeCautat = String.valueOf(getNumeCombo().getSelectedItem());
                        String tipJocCautat = String.valueOf(getTipJocCombo().getSelectedItem());
                        CallableStatement test;
                        if (!afisare) {
                            test = connec.getConnection().prepareCall(
                                    "{call invitatieJocIndividual(?,?,?,?)}");
                            test.setInt(1, player.getIdJucator());
                            test.setString(2, player.getNume());
                            test.setString(3, numeCautat);
                            test.setString(4, tipJocCautat);
                            test.execute();
                        } else {
                            test = connec.getConnection().prepareCall(
                                    "{call invitatieJocEchipa(?,?,?,?)}");
                            test.setInt(1, player.getIdEchipa());
                            test.setString(2, player.getNumeEchipa());
                            test.setString(3, numeCautat);
                            test.setString(4, tipJocCautat);
                            test.execute();
                        }
                        //System.out.println(player.getIdJucator());
                        //System.out.println(player.getNume());
                        //System.out.println(numeCautat);
                        //System.out.println(tipJocCautat);
                        CreareInvitatie.super.dispose();
                        MeniuUtilizatorBD meniu = new MeniuUtilizatorBD(connec, player);
                        meniu.go();
                        JOptionPane.showMessageDialog(null, "Invitatia a fost trimisa cu succes!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                }

            });

            this.setContentPane(masterPanel);
            this.setVisible(true);

            backButon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreareInvitatie.super.dispose();
                    MeniuUtilizatorBD meniu = new MeniuUtilizatorBD(connec, player);
                    meniu.go();
                }
            });
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

    public boolean isAfisare() {
        return afisare;
    }

    public void setAfisare(boolean afisare) {
        this.afisare = afisare;
    }

    public JPanel getMasterPanel() {
        return masterPanel;
    }

    public void setMasterPanel(JPanel masterPanel) {
        this.masterPanel = masterPanel;
    }

    public JPanel getTitluPanel() {
        return titluPanel;
    }

    public void setTitluPanel(JPanel titluPanel) {
        this.titluPanel = titluPanel;
    }

    public JPanel getAlegeriPanel() {
        return alegeriPanel;
    }

    public void setAlegeriPanel(JPanel alegeriPanel) {
        this.alegeriPanel = alegeriPanel;
    }

    public JPanel getButoanePanel() {
        return butoanePanel;
    }

    public void setButoanePanel(JPanel butoanePanel) {
        this.butoanePanel = butoanePanel;
    }

    public JLabel getTitluLabel() {
        return titluLabel;
    }

    public void setTitluLabel(JLabel titluLabel) {
        this.titluLabel = titluLabel;
    }

    public JLabel getNumeLabel() {
        return numeLabel;
    }

    public void setNumeLabel(JLabel numeLabel) {
        this.numeLabel = numeLabel;
    }

    public JLabel getTipJocLabel() {
        return tipJocLabel;
    }

    public void setTipJocLabel(JLabel tipJocLabel) {
        this.tipJocLabel = tipJocLabel;
    }

    public JComboBox<String> getNumeCombo() {
        return numeCombo;
    }

    public void setNumeCombo(JComboBox<String> numeCombo) {
        this.numeCombo = numeCombo;
    }

    public JComboBox<String> getTipJocCombo() {
        return tipJocCombo;
    }

    public void setTipJocCombo(JComboBox<String> tipJocCombo) {
        this.tipJocCombo = tipJocCombo;
    }

    public JButton getSendInviteButon() {
        return sendInviteButon;
    }

    public void setSendInviteButon(JButton sendInviteButon) {
        this.sendInviteButon = sendInviteButon;
    }

    public JButton getBackButon() {
        return backButon;
    }

    public void setBackButon(JButton backButon) {
        this.backButon = backButon;
    }
}
