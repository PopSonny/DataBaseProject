package interfaceBD;

import accesBD.JucatorAcces;
import dataBase.ConnectionClass;
import tabels.Jucator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterBD extends JFrame {
    private ConnectionClass connec;

    public RegisterBD(ConnectionClass connec) throws HeadlessException {
        this.connec = connec;
    }

    private JLabel numeAplicatie = new JLabel("Register Your Account");

    private JLabel numeL = new JLabel("Nickname");
    private JLabel passL = new JLabel("Password");
    private JLabel passConfL = new JLabel("Re-type pass");
    private JLabel dataNasteriiL = new JLabel("Data nasterii YYYY-MM-DD");

    private JTextField numeT = new JTextField(10);
    private JTextField passT = new JTextField(10);
    private JTextField passConfT = new JTextField(10);
    private JTextField dataNasteriiT = new JTextField(10);

    private JButton registerB = new JButton("Register");
    private JButton backB = new JButton("Back");

    private JPanel panelMaster = new JPanel();
    private JPanel numeAplicatieP = new JPanel();
    private JPanel nicknameP = new JPanel();
    private JPanel passwordP = new JPanel();
    private JPanel passwordConfP = new JPanel();
    private JPanel dataNasteriiP = new JPanel();
    private JPanel butoaneP = new JPanel();


    public void go() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        panelMaster.setLayout(new BoxLayout(panelMaster, BoxLayout.Y_AXIS));

        //numele aplicatiei
        numeAplicatieP.add(numeAplicatie);
        panelMaster.add(numeAplicatieP);

        //nickanme
        nicknameP.setLayout(new FlowLayout());
        nicknameP.add(numeL);
        nicknameP.add(numeT);
        panelMaster.add(nicknameP);

        //password
        passwordP.setLayout(new FlowLayout());
        passwordP.add(passL);
        passwordP.add(passT);
        panelMaster.add(passwordP);

        //confirmpassword
        passwordConfP.setLayout(new FlowLayout());
        passwordConfP.add(passConfL);
        passwordConfP.add(passConfT);
        panelMaster.add(passwordConfP);

        //datanasterii
        dataNasteriiP.setLayout(new FlowLayout());
        dataNasteriiP.add(dataNasteriiL);
        dataNasteriiP.add(dataNasteriiT);
        panelMaster.add(dataNasteriiP);

        //butoane
        butoaneP.setLayout(new FlowLayout());
        butoaneP.add(registerB);
        butoaneP.add(backB);
        panelMaster.add(butoaneP);

        //actionlisteners

        backB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterBD.super.dispose();
                LogIn log = new LogIn(connec);
                log.go();
            }
        });

        registerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statement prepare;
                try {
                    boolean merge=true;
                    String nume = getNumeT().getText();
                    prepare = connec.getConnection().createStatement();
                    String cautare="select jucator.nume from jucator where jucator.nume='" + nume + "';";
                    ResultSet rs = prepare.executeQuery(cautare);
                    String numecautat = "nimic";
                    while (rs.next()) {
                        numecautat = rs.getString(1);
                    }
                    if (!numecautat.equals("nimic")) {
                        JOptionPane.showMessageDialog(null, "Username folosit introduceti unul nou", "Error", JOptionPane.ERROR_MESSAGE);
                        merge=false;
                    }
                    if(!getPassConfT().getText().equals(getPassT().getText())&& merge)
                    {
                        JOptionPane.showMessageDialog(null, "Parolele nu sunt identice", "Error", JOptionPane.ERROR_MESSAGE);
                        merge=false;
                    }
                    if(getNumeT().getText().equals("")||getPassConfT().getText().equals("") || getPassT().getText().equals("") || getDataNasteriiT().getText().equals("") && merge)
                    {
                        JOptionPane.showMessageDialog(null, "Completati toate casutele", "Error", JOptionPane.ERROR_MESSAGE);
                        merge=false;
                    }
                    if(merge)
                    {
                        JucatorAcces player=new JucatorAcces(connec);
                        //nume parola dataN
                        Jucator playeraux=new Jucator(nume,getPassT().getText(),getDataNasteriiT().getText());
                        player.addJucator(playeraux);
                        RegisterBD.super.dispose();
                        LogIn log=new LogIn(connec);
                        log.go();
                        JOptionPane.showMessageDialog(null, "Jucator inregistrat cu succes", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });

        this.setContentPane(panelMaster);
        this.setVisible(true);
    }

    public ConnectionClass getConnec() {
        return connec;
    }

    public void setConnec(ConnectionClass connec) {
        this.connec = connec;
    }

    public JLabel getNumeAplicatie() {
        return numeAplicatie;
    }

    public void setNumeAplicatie(JLabel numeAplicatie) {
        this.numeAplicatie = numeAplicatie;
    }

    public JLabel getNumeL() {
        return numeL;
    }

    public void setNumeL(JLabel numeL) {
        this.numeL = numeL;
    }

    public JLabel getPassL() {
        return passL;
    }

    public void setPassL(JLabel passL) {
        this.passL = passL;
    }

    public JLabel getPassConfL() {
        return passConfL;
    }

    public void setPassConfL(JLabel passConfL) {
        this.passConfL = passConfL;
    }

    public JLabel getDataNasteriiL() {
        return dataNasteriiL;
    }

    public void setDataNasteriiL(JLabel dataNasteriiL) {
        this.dataNasteriiL = dataNasteriiL;
    }

    public JTextField getNumeT() {
        return numeT;
    }

    public void setNumeT(JTextField numeT) {
        this.numeT = numeT;
    }

    public JTextField getPassT() {
        return passT;
    }

    public void setPassT(JTextField passT) {
        this.passT = passT;
    }

    public JTextField getPassConfT() {
        return passConfT;
    }

    public void setPassConfT(JTextField passConfT) {
        this.passConfT = passConfT;
    }

    public JTextField getDataNasteriiT() {
        return dataNasteriiT;
    }

    public void setDataNasteriiT(JTextField dataNasteriiT) {
        this.dataNasteriiT = dataNasteriiT;
    }

    public JButton getRegisterB() {
        return registerB;
    }

    public void setRegisterB(JButton registerB) {
        this.registerB = registerB;
    }

    public JButton getBackB() {
        return backB;
    }

    public void setBackB(JButton backB) {
        this.backB = backB;
    }

    public JPanel getPanelMaster() {
        return panelMaster;
    }

    public void setPanelMaster(JPanel panelMaster) {
        this.panelMaster = panelMaster;
    }

    public JPanel getNumeAplicatieP() {
        return numeAplicatieP;
    }

    public void setNumeAplicatieP(JPanel numeAplicatieP) {
        this.numeAplicatieP = numeAplicatieP;
    }

    public JPanel getNicknameP() {
        return nicknameP;
    }

    public void setNicknameP(JPanel nicknameP) {
        this.nicknameP = nicknameP;
    }

    public JPanel getPasswordP() {
        return passwordP;
    }

    public void setPasswordP(JPanel passwordP) {
        this.passwordP = passwordP;
    }

    public JPanel getPasswordConfP() {
        return passwordConfP;
    }

    public void setPasswordConfP(JPanel passwordConfP) {
        this.passwordConfP = passwordConfP;
    }

    public JPanel getDataNasteriiP() {
        return dataNasteriiP;
    }

    public void setDataNasteriiP(JPanel dataNasteriiP) {
        this.dataNasteriiP = dataNasteriiP;
    }

    public JPanel getButoaneP() {
        return butoaneP;
    }

    public void setButoaneP(JPanel butoaneP) {
        this.butoaneP = butoaneP;
    }
}
