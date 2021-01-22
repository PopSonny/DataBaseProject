package interfaceBD;

import dataBase.ConnectionClass;
import tabels.Jucator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class IstoricBD extends JFrame {
    private ConnectionClass connec;
    private Jucator player;
    //true ptr echipa
    //false ptr individual
    private boolean afisare;

    public IstoricBD(ConnectionClass connec, Jucator p,boolean a) throws HeadlessException {
        this.connec = connec;
        this.player = p;
        this.afisare=a;
    }

    private JPanel panelMaster=new JPanel();
    private JScrollPane clasamentPanel;
    private JPanel titluPanel=new JPanel();
    private JPanel butonPanel=new JPanel();


    private JTable clasament;
    private JButton back=new JButton("Back");
    private JLabel titlu=new JLabel();

    public void go()
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        panelMaster.setLayout(new BoxLayout(panelMaster,BoxLayout.Y_AXIS));

        String coloana1="IdJoc";
        String coloana2="TipJoc";
        String coloana3;
        String coloana4;
        String coloana5="NrPartide";
        String coloana6="NrPartideJucate";
        String coloana7="DataInceputJoc";
        String coloana8="DataSfarstiJoc";
        String coloana9;
        String coloana10;
        String coloana11="Invigator";

        //titlu
        titluPanel.setLayout(new FlowLayout());
        if(afisare)
        {
            titlu.setText("Istoric Echipe");
            coloana3="Echipa1";
            coloana4="Echipa2";
            coloana9="ScorEchipa1";
            coloana10="ScorEchipa2";
        }
        else
        {
            titlu.setText("Istoric Individual");
            coloana3="Jucator1";
            coloana4="Jucator2";
            coloana9="ScorJucator1";
            coloana10="ScorJucator2";
        }
        titlu.setFont(new Font("Arial",Font.BOLD,30));
        titluPanel.add(titlu);

        //back
        butonPanel.setLayout(new FlowLayout());
        butonPanel.add(back);
        //tabel

        Statement prepare;
        try{
            String cautare=null;
            if(afisare)
            {

                cautare="select * from jocechipa " +
                        "order by dataSfarsitJoc desc;";
            }
            else
            {
                cautare="select * from jocindividual " +
                        "order by dataSfarsitJoc desc;";
            }
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            String[][] data=new String[1000][11];
            int i=0;
            while (rs.next()) {
                data[i][0]=rs.getString(1);
                data[i][1]=rs.getString(2);
                data[i][2]=rs.getString(3);
                data[i][3]=rs.getString(4);
                data[i][4]=rs.getString(5);
                data[i][5]=rs.getString(6);
                data[i][6]=rs.getString(7);
                data[i][7]=rs.getString(8);
                data[i][8]=rs.getString(9);
                data[i][9]=rs.getString(10);
                data[i][10]=rs.getString(11);
                i++;
            }
            String[] columnNames = new String[11];
            columnNames[0]=coloana1;
            columnNames[1]=coloana2;
            columnNames[2]=coloana3;
            columnNames[3]=coloana4;
            columnNames[4]=coloana5;
            columnNames[5]=coloana6;
            columnNames[6]=coloana7;
            columnNames[7]=coloana8;
            columnNames[8]=coloana9;
            columnNames[9]=coloana10;
            columnNames[10]=coloana11;
            clasament=new JTable(data,columnNames);
            clasament.setDefaultEditor(Object.class,null);

        }
        catch (Exception f)
        {
            f.printStackTrace();
        }

        //actionlistener
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IstoricBD.super.dispose();
                MeniuUtilizatorBD meniu=new MeniuUtilizatorBD(connec,player);
                meniu.go();
            }
        });

        clasamentPanel=new JScrollPane(clasament);

        clasamentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        clasamentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        titluPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaster.add(titluPanel);


        clasamentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaster.add(clasamentPanel);

        butonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMaster.add(butonPanel);
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

    public boolean isAfisare() {
        return afisare;
    }

    public void setAfisare(boolean afisare) {
        this.afisare = afisare;
    }

    public JPanel getPanelMaster() {
        return panelMaster;
    }

    public void setPanelMaster(JPanel panelMaster) {
        this.panelMaster = panelMaster;
    }

    public JScrollPane getClasamentPanel() {
        return clasamentPanel;
    }

    public void setClasamentPanel(JScrollPane clasamentPanel) {
        this.clasamentPanel = clasamentPanel;
    }

    public JPanel getTitluPanel() {
        return titluPanel;
    }

    public void setTitluPanel(JPanel titluPanel) {
        this.titluPanel = titluPanel;
    }

    public JPanel getButonPanel() {
        return butonPanel;
    }

    public void setButonPanel(JPanel butonPanel) {
        this.butonPanel = butonPanel;
    }

    public JTable getClasament() {
        return clasament;
    }

    public void setClasament(JTable clasament) {
        this.clasament = clasament;
    }

    public JButton getBack() {
        return back;
    }

    public void setBack(JButton back) {
        this.back = back;
    }

    public JLabel getTitlu() {
        return titlu;
    }

    public void setTitlu(JLabel titlu) {
        this.titlu = titlu;
    }
}
