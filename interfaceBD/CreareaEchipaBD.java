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

public class CreareaEchipaBD extends JFrame {
    private ConnectionClass connec;
    private Jucator player;

    public CreareaEchipaBD(ConnectionClass connec, Jucator p) throws HeadlessException {
        this.connec = connec;
        this.player = p;
    }

    private JPanel panelMaster=new JPanel();
    private JPanel echipePanel=new JPanel();
    private JPanel parolaPanel=new JPanel();
    private JPanel butoanePanel=new JPanel();


    private JLabel numeEchipaL=new JLabel("Nume echipa:");
    private JTextField numeEchipaT =new JTextField(10);

    private JLabel parolaLabel=new JLabel("Introduceti parola");
    private JTextField parolaField=new JTextField(10);

    private JButton creazaEchipaB=new JButton("Creaza echipa");
    private JButton backButton=new JButton("Back");

    private JLabel titlu=new JLabel("Creaza o echipa");
    private JPanel titluPanel=new JPanel();

    public void go() {
        titlu.setFont(new Font("Arial",Font.BOLD,30));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        panelMaster.setLayout(new BoxLayout(panelMaster,BoxLayout.Y_AXIS));

        //echipe
        echipePanel.setLayout(new FlowLayout());
        echipePanel.add(numeEchipaL);
        echipePanel.add(numeEchipaT);

        //parola
        parolaPanel.setLayout(new FlowLayout());
        parolaPanel.add(parolaLabel);
        parolaPanel.add(parolaField);

        //butoane
        butoanePanel.setLayout(new BoxLayout(butoanePanel,BoxLayout.Y_AXIS));
        creazaEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        butoanePanel.add(creazaEchipaB);
        butoanePanel.add(Box.createRigidArea(new Dimension(0,10)));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        butoanePanel.add(backButton);



        //titlu
        titluPanel.add(titlu);


        //actionlisteners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreareaEchipaBD.super.dispose();
                MeniuUtilizatorBD m=new MeniuUtilizatorBD(connec,player);
                m.go();
            }
        });

        creazaEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume= getNumeEchipaT().getText();
                String parola=getParolaField().getText();
                Statement prepare;
                boolean merge=true;
                try{
                    String cautare="select echipa.nume from echipa where " +
                            "echipa.nume='"+nume+"';";
                    prepare = connec.getConnection().createStatement();
                    ResultSet rs = prepare.executeQuery(cautare);
                    String verificare = "nimic";
                    while (rs.next()) {
                        verificare=rs.getString(1);
                    }
                    if(!verificare.equals("nimic"))
                    {
                        JOptionPane.showMessageDialog(null, "Nume de echipa folosit deja!", "Error", JOptionPane.ERROR_MESSAGE);
                        merge=false;
                    }
                    if(merge)
                    {
                        player.setNumeEchipa(nume);
                        CallableStatement test;
                        test=connec.getConnection().prepareCall(
                                "{call introducereEchipa(?,?)}");
                        test.setString(1,nume);
                        test.setString(2,parola);
                        test.execute();


                        //test sa intre direct in ehipa


                        test=connec.getConnection().prepareCall(
                                "{call joinEchipa(?,?,?)}");
                        test.setString(1,nume);
                        test.setString(2,parola);
                        test.setInt(3,player.getIdJucator());
                        test.execute();

                        int idptrjucatorEchipa=0;
                        cautare="select id_echipa from echipa " +
                                "where nume='"+nume+"';";
                        prepare = connec.getConnection().createStatement();
                        rs = prepare.executeQuery(cautare);
                        while (rs.next()) {
                            idptrjucatorEchipa=rs.getInt(1);
                        }
                        player.setIdEchipa(idptrjucatorEchipa);

                        //System.out.println(player);


                        CreareaEchipaBD.super.dispose();
                        MeniuUtilizatorBD m=new MeniuUtilizatorBD(connec,player);
                        m.go();
                        JOptionPane.showMessageDialog(null, "Echipa "+nume+" a fost creata si ai intrat in ea cu succes!", "Info", JOptionPane.INFORMATION_MESSAGE);
                        //JOptionPane.showMessageDialog(null, "Echipa "+nume+" a fost creata !", "Info", JOptionPane.INFORMATION_MESSAGE);
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
        panelMaster.add(Box.createRigidArea(new Dimension(0,25)));
        echipePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaster.add(echipePanel);
        parolaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaster.add(parolaPanel);
        butoanePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaster.add(butoanePanel);
        this.setContentPane(panelMaster);
        this.setVisible(true);
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

    public JLabel getNumeEchipaL() {
        return numeEchipaL;
    }

    public void setNumeEchipaL(JLabel numeEchipaL) {
        this.numeEchipaL = numeEchipaL;
    }

    public JTextField getNumeEchipaT() {
        return numeEchipaT;
    }

    public void setNumeEchipaT(JTextField numeEchipaT) {
        this.numeEchipaT = numeEchipaT;
    }

    public JLabel getParolaLabel() {
        return parolaLabel;
    }

    public void setParolaLabel(JLabel parolaLabel) {
        this.parolaLabel = parolaLabel;
    }

    public JTextField getParolaField() {
        return parolaField;
    }

    public void setParolaField(JTextField parolaField) {
        this.parolaField = parolaField;
    }

    public JButton getCreazaEchipaB() {
        return creazaEchipaB;
    }

    public void setCreazaEchipaB(JButton creazaEchipaB) {
        this.creazaEchipaB = creazaEchipaB;
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
}
