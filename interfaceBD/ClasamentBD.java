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

public class ClasamentBD extends JFrame {
    private ConnectionClass connec;
    private Jucator player;
    //true ptr echipa
    //false ptr individual
    private boolean afisare;


    public ClasamentBD(ConnectionClass connec, Jucator p,boolean a) throws HeadlessException {
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
        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        panelMaster.setLayout(new BoxLayout(panelMaster,BoxLayout.Y_AXIS));

        String coloana1,coloana2="jocuriCastigate";

        //titlu
        titluPanel.setLayout(new FlowLayout());
        if(afisare)
        {
            titlu.setText("Clasament Echipe");
            coloana1="numeEchipe";
        }
        else
        {
            titlu.setText("Clasament Individual");
            coloana1="numeIndividual";
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
                cautare="select numeEchipa,jocuriCastigate from clasamentechipe " +
                        "order by jocuriCastigate desc;";
            }
            else
            {
                cautare="select numeJucator,jocuriCastigate from clasamentindividual " +
                        "order by jocuriCastigate desc;";
            }
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            String[][] data=new String[1000][2];
            int i=0;
            while (rs.next()) {
                data[i][0]=rs.getString(1);
                data[i][1]=rs.getString(2);
                i++;
            }
            String[] columnNames = new String[2];
            columnNames[0]=coloana1;
            columnNames[1]=coloana2;
            clasament=new JTable(data,columnNames);
            clasament.setDefaultEditor(Object.class,null);
            //clasament.setModel(new MyModel());

        }
        catch (Exception f)
        {
            f.printStackTrace();
        }

        //actionlistener
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClasamentBD.super.dispose();
                MeniuUtilizatorBD meniu=new MeniuUtilizatorBD(connec,player);
                meniu.go();
            }
        });

        clasamentPanel=new JScrollPane(clasament);

        clasamentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //clasamentPanel.setVerticalScrollBarPolicy(
        //        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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
