package interfaceBD;

import dataBase.ConnectionClass;
import tabels.Jucator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminInterfaceBD extends JFrame {
    private ConnectionClass connec;

    public AdminInterfaceBD(ConnectionClass connec) throws HeadlessException {
        this.connec = connec;
    }

    private JPanel masterPanel = new JPanel();

    private JPanel titluPanel = new JPanel();
    private JPanel jucatoriPanel = new JPanel();
    private JPanel echipePanel = new JPanel();
    private JPanel tipDeJocStergere = new JPanel();
    private JPanel tipdeJocIntroducere = new JPanel();
    private JPanel exitPanel = new JPanel();

    private JLabel titluAdmin = new JLabel("Meniu ADMIN");

    private JComboBox<String> jucatorCombo = new JComboBox<>();
    private JComboBox<String> echipaCombo = new JComboBox<>();
    private JComboBox<String> tipDeJocCombo = new JComboBox<>();

    private JTextField tipDeJocIntrodusTEXT = new JTextField(10);

    private JButton jucatorButon = new JButton("Sterge jucator");
    private JButton echipaButon = new JButton("Sterge echipa");
    private JButton stergeTipDeJoc = new JButton("Sterge jocul");
    private JButton introduTipDeJoc = new JButton("Introdu jocul");
    private JButton backButon = new JButton("Back");

    public void go() {
        Statement prepare;
        try {
            String cautare = "select echipa.nume from echipa;";
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            while (rs.next()) {
                echipaCombo.addItem(rs.getString(1));
            }

            cautare = "select nume from jucator";
            prepare = connec.getConnection().createStatement();
            rs = prepare.executeQuery(cautare);
            while (rs.next()) {
                jucatorCombo.addItem(rs.getString(1));
            }

            cautare = "select numeJoc from tipdejoc";
            prepare = connec.getConnection().createStatement();
            rs = prepare.executeQuery(cautare);
            while (rs.next()) {
                tipDeJocCombo.addItem(rs.getString(1));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        titluAdmin.setFont(new Font("Arial", Font.BOLD, 30));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));

        //titlu
        titluPanel.setLayout(new FlowLayout());
        titluPanel.add(titluAdmin);

        //jucatorCombo
        jucatoriPanel.setLayout(new FlowLayout());
        jucatoriPanel.add(jucatorCombo);
        jucatoriPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        jucatoriPanel.add(jucatorButon);

        //echipeCombo
        echipePanel.setLayout(new FlowLayout());
        echipePanel.add(echipaCombo);
        echipePanel.add(Box.createRigidArea(new Dimension(15, 0)));
        echipePanel.add(echipaButon);

        //tipdejocCombo
        tipDeJocStergere.setLayout(new FlowLayout());
        tipDeJocStergere.add(tipDeJocCombo);
        tipDeJocStergere.add(Box.createRigidArea(new Dimension(15, 0)));
        tipDeJocStergere.add(stergeTipDeJoc);

        //tipdeJOcintroducere
        tipdeJocIntroducere.setLayout(new FlowLayout());
        tipdeJocIntroducere.add(tipDeJocIntrodusTEXT);
        tipdeJocIntroducere.add(Box.createRigidArea(new Dimension(15, 0)));
        tipdeJocIntroducere.add(introduTipDeJoc);


        //exit
        exitPanel.setLayout(new FlowLayout());
        exitPanel.add(backButon);


        titluPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(titluPanel);

        jucatoriPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(jucatoriPanel);

        echipePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(echipePanel);

        tipDeJocStergere.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(tipDeJocStergere);

        tipdeJocIntroducere.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(tipdeJocIntroducere);

        exitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(exitPanel);


        //actionListeners
        backButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInterfaceBD.super.dispose();
                LogIn log = new LogIn(connec);
                log.go();
            }
        });


        echipaButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeEchipa = String.valueOf(getEchipaCombo().getSelectedItem());
                Statement test;
                try {
                    String cautare = "delete from echipa where nume='" + numeEchipa + "';";
                    test = connec.getConnection().createStatement();
                    test.executeUpdate(cautare);
                } catch (Exception f) {
                    f.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Echipa stearsa!", "Info", JOptionPane.INFORMATION_MESSAGE);
                AdminInterfaceBD.super.dispose();
                AdminInterfaceBD admin = new AdminInterfaceBD(connec);
                admin.go();
            }
        });

        jucatorButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numejucator = String.valueOf(getJucatorCombo().getSelectedItem());
                Statement test;
                try {
                    String cautare = "delete from jucator where nume='" + numejucator + "';";
                    test = connec.getConnection().createStatement();
                    test.executeUpdate(cautare);
                } catch (Exception f) {
                    f.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Jucator sters!", "Info", JOptionPane.INFORMATION_MESSAGE);
                AdminInterfaceBD.super.dispose();
                AdminInterfaceBD admin = new AdminInterfaceBD(connec);
                admin.go();
            }
        });

        stergeTipDeJoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipdejoc = String.valueOf(getTipDeJocCombo().getSelectedItem());
                Statement test;
                try {
                    String cautare = "delete from tipdejoc where numeJoc='" + tipdejoc + "';";
                    test = connec.getConnection().createStatement();
                    test.executeUpdate(cautare);
                } catch (Exception f) {
                    f.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Joc sters!", "Info", JOptionPane.INFORMATION_MESSAGE);
                AdminInterfaceBD.super.dispose();
                AdminInterfaceBD admin = new AdminInterfaceBD(connec);
                admin.go();
            }
        });

        introduTipDeJoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeJoc = getTipDeJocIntrodusTEXT().getText();
                if (numeJoc.equals("")) {
                    JOptionPane.showMessageDialog(null, "Introduceti un nume valid!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Statement test;
                    try {
                        String cautare = "insert into tipdejoc values('" + numeJoc + "');";
                        test = connec.getConnection().createStatement();
                        test.executeUpdate(cautare);
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Joc introdus!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    AdminInterfaceBD.super.dispose();
                    AdminInterfaceBD admin = new AdminInterfaceBD(connec);
                    admin.go();
                }
            }

        });


        this.setContentPane(masterPanel);
        this.setVisible(true);
    }

    public JPanel getTitluPanel() {
        return titluPanel;
    }

    public void setTitluPanel(JPanel titluPanel) {
        this.titluPanel = titluPanel;
    }

    public ConnectionClass getConnec() {
        return connec;
    }

    public void setConnec(ConnectionClass connec) {
        this.connec = connec;
    }

    public JPanel getMasterPanel() {
        return masterPanel;
    }

    public void setMasterPanel(JPanel masterPanel) {
        this.masterPanel = masterPanel;
    }

    public JPanel getJucatoriPanel() {
        return jucatoriPanel;
    }

    public void setJucatoriPanel(JPanel jucatoriPanel) {
        this.jucatoriPanel = jucatoriPanel;
    }

    public JPanel getEchipePanel() {
        return echipePanel;
    }

    public void setEchipePanel(JPanel echipePanel) {
        this.echipePanel = echipePanel;
    }

    public JPanel getTipDeJocStergere() {
        return tipDeJocStergere;
    }

    public void setTipDeJocStergere(JPanel tipDeJocStergere) {
        this.tipDeJocStergere = tipDeJocStergere;
    }

    public JPanel getTipdeJocIntroducere() {
        return tipdeJocIntroducere;
    }

    public void setTipdeJocIntroducere(JPanel tipdeJocIntroducere) {
        this.tipdeJocIntroducere = tipdeJocIntroducere;
    }

    public JPanel getExitPanel() {
        return exitPanel;
    }

    public void setExitPanel(JPanel exitPanel) {
        this.exitPanel = exitPanel;
    }

    public JLabel getTitluAdmin() {
        return titluAdmin;
    }

    public void setTitluAdmin(JLabel titluAdmin) {
        this.titluAdmin = titluAdmin;
    }

    public JComboBox<String> getJucatorCombo() {
        return jucatorCombo;
    }

    public void setJucatorCombo(JComboBox<String> jucatorCombo) {
        this.jucatorCombo = jucatorCombo;
    }

    public JComboBox<String> getEchipaCombo() {
        return echipaCombo;
    }

    public void setEchipaCombo(JComboBox<String> echipaCombo) {
        this.echipaCombo = echipaCombo;
    }

    public JComboBox<String> getTipDeJocCombo() {
        return tipDeJocCombo;
    }

    public void setTipDeJocCombo(JComboBox<String> tipDeJocCombo) {
        this.tipDeJocCombo = tipDeJocCombo;
    }

    public JTextField getTipDeJocIntrodusTEXT() {
        return tipDeJocIntrodusTEXT;
    }

    public void setTipDeJocIntrodusTEXT(JTextField tipDeJocIntrodusTEXT) {
        this.tipDeJocIntrodusTEXT = tipDeJocIntrodusTEXT;
    }

    public JButton getJucatorButon() {
        return jucatorButon;
    }

    public void setJucatorButon(JButton jucatorButon) {
        this.jucatorButon = jucatorButon;
    }

    public JButton getEchipaButon() {
        return echipaButon;
    }

    public void setEchipaButon(JButton echipaButon) {
        this.echipaButon = echipaButon;
    }

    public JButton getStergeTipDeJoc() {
        return stergeTipDeJoc;
    }

    public void setStergeTipDeJoc(JButton stergeTipDeJoc) {
        this.stergeTipDeJoc = stergeTipDeJoc;
    }

    public JButton getIntroduTipDeJoc() {
        return introduTipDeJoc;
    }

    public void setIntroduTipDeJoc(JButton introduTipDeJoc) {
        this.introduTipDeJoc = introduTipDeJoc;
    }

    public JButton getBackButon() {
        return backButon;
    }

    public void setBackButon(JButton backButon) {
        this.backButon = backButon;
    }
}