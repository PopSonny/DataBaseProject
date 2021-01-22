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

public class LogIn extends JFrame {
    private ConnectionClass connec;

    public LogIn(ConnectionClass connec) throws HeadlessException {
        this.connec = connec;
    }

    private JLabel numeAplicatie =new JLabel("ProiectBD4");

    private JLabel numeL=new JLabel("Nickname");
    private JLabel passL=new JLabel("Password");

    private JTextField numeT=new JTextField("admin",10);
    private JPasswordField passT=new JPasswordField("admin",10);
    private JButton loginB=new JButton("Login");
    private JButton registerB=new JButton("Register");

    private JButton exit=new JButton("exit");

    private JPanel panelMaster=new JPanel();
    private JPanel numeAplicatieP=new JPanel();
    private JPanel nicknameP=new JPanel();
    private JPanel passwordP=new JPanel();
    private JPanel butoaneP=new JPanel();
    private JPanel exitP=new JPanel();
    public void go()
    {

        passT.setEchoChar('*');
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        panelMaster.setLayout(new BoxLayout(panelMaster,BoxLayout.Y_AXIS));

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

        //butoane
        butoaneP.setLayout(new FlowLayout());
        butoaneP.add(loginB);
        butoaneP.add(registerB);
        panelMaster.add(butoaneP);

        //exit
        exitP.add(exit);
        panelMaster.add(exitP);

        //addActionListeners

        registerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterBD regis=new RegisterBD(connec);
                LogIn.super.dispose();
                regis.go();
            }
        });

        loginB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statement prepare;
                try {
                    boolean merge=true;
                    String nume = getNumeT().getText();
                    String parola=getPassT().getText();

                    if(nume.equals("admin")&&parola.equals("admin"))
                    {
                        LogIn.super.dispose();
                        AdminInterfaceBD admin1=new AdminInterfaceBD(connec);
                        admin1.go();
                        merge=false;
                    }

                    String cautare="select jucator.nume, jucator.passwordUser " +
                            "from jucator " +
                            "where jucator.nume='"+nume+"' and jucator.passwordUser='"+parola+"';";

                    //System.out.println(cautare);

                    prepare = connec.getConnection().createStatement();
                    ResultSet rs = prepare.executeQuery(cautare);
                    String verificare = "nimic";
                    while (rs.next()) {
                        verificare = rs.getString(1);
                    }
                    if(verificare.equals("nimic")&&merge)
                    {
                        JOptionPane.showMessageDialog(null, "Username sau parola gresita!", "Error", JOptionPane.ERROR_MESSAGE);
                        merge=false;
                    }
                    if(merge)
                    {
                        Jucator player=new Jucator();
                        player.setNume(nume);
                        player.setPassword(parola);

                        cautare="select jucator.id_jucator, jucator.id_echipa " +
                                "from jucator " +
                                "where jucator.nume='"+nume+"';";
                        rs = prepare.executeQuery(cautare);
                        int idJucatorCautat=0;
                        int idEchipaCautat=0;
                        while (rs.next()) {
                            idJucatorCautat=rs.getInt(1);
                            idEchipaCautat=rs.getInt(2);
                        }

                        String numeEchipa="Nu face parte dintr-o echipa";
                        if(idEchipaCautat!=0)
                        {
                            cautare="select echipa.nume from echipa " +
                                    "where echipa.id_echipa="+idEchipaCautat+";";
                            rs = prepare.executeQuery(cautare);
                            while (rs.next()) {
                                numeEchipa=rs.getString(1);
                            }
                        }

                        player.setIdJucator(idJucatorCautat);
                        player.setIdEchipa(idEchipaCautat);
                        player.setNumeEchipa(numeEchipa);
                        LogIn.super.dispose();
                        MeniuUtilizatorBD m=new MeniuUtilizatorBD(connec,player);
                        m.go();
                        //test aiciiiiiiiiiiiiiiii
                        //System.out.println(player);

                    }

                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogIn.super.dispose();
            }
        });

        this.setContentPane(panelMaster);
        this.setVisible(true);
    }

    /*public static void main(String[] args) {
        LogIn test=new LogIn(new ConnectionClass("proiectbd4"));
        test.go();
    }*/

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

    public JTextField getNumeT() {
        return numeT;
    }

    public void setNumeT(JTextField numeT) {
        this.numeT = numeT;
    }

    public JTextField getPassT() {
        return passT;
    }

    public void setPassT(JPasswordField passT) {
        this.passT = passT;
    }

    public JButton getLoginB() {
        return loginB;
    }

    public void setLoginB(JButton loginB) {
        this.loginB = loginB;
    }

    public JButton getRegisterB() {
        return registerB;
    }

    public void setRegisterB(JButton registerB) {
        this.registerB = registerB;
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

    public JPanel getButoaneP() {
        return butoaneP;
    }

    public void setButoaneP(JPanel butoaneP) {
        this.butoaneP = butoaneP;
    }
}
