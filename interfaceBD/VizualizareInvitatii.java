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
import java.util.Random;

public class VizualizareInvitatii extends JFrame {
    private ConnectionClass connec;
    private Jucator player;
    //true ptr echipa
    //false ptr individual
    private boolean afisare;


    public VizualizareInvitatii(ConnectionClass connec, Jucator p, boolean a) throws HeadlessException {
        this.connec = connec;
        this.player = p;
        this.afisare = a;
    }

    private JPanel masterPanel = new JPanel();
    private JPanel titluPanel = new JPanel();
    private JScrollPane tabelPanel;
    private JPanel alegereMeci = new JPanel();
    private JPanel backPanel = new JPanel();

    private JLabel titluLabel = new JLabel();

    private JTable tabelInvitatii;

    private JLabel idLabel = new JLabel("Id-uri:");
    private JComboBox<Integer> comboIduri = new JComboBox<>();
    private JButton invitaButon = new JButton("Chon ki tan fai");

    private JButton backButon = new JButton("Back");

    private ArrayList<Integer> iduriComboArray = new ArrayList<>();

    public void go() {
        String coloana1 = "Id-ul jocului";
        String coloana2;
        String coloana3;
        String coloana4 = "TipJoc";
        if (afisare) {
            titluLabel.setText("Invitatiile echipei");
            coloana2 = "Echipa1";
            coloana3 = "Echipa2 (me)";
        } else {
            titluLabel.setText("Invitatiile jucatorului");
            coloana2 = "Jucator1";
            coloana3 = "Jucator2 (me)";
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

        //tabel

        Statement prepare;
        try {
            String cautare = null;
            if (afisare) {

                cautare = "select idInvitatie,numeEchipa1,numeEchipa2,tipJoc from invitatieechipa " +
                        "where numeechipa2='" + player.getNumeEchipa() + "';";
            } else {
                cautare = "select idInvitatie,numeJucator1,numeJucator2,tipJoc from invitatieindividual " +
                        "where numeJucator2='" + player.getNume() + "';";
            }
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            String[][] data = new String[1000][4];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString(1);
                data[i][1] = rs.getString(2);
                data[i][2] = rs.getString(3);
                data[i][3] = rs.getString(4);
                i++;
            }
            String[] columnNames = new String[4];
            columnNames[0] = coloana1;
            columnNames[1] = coloana2;
            columnNames[2] = coloana3;
            columnNames[3] = coloana4;
            tabelInvitatii = new JTable(data, columnNames);
            tabelInvitatii.setDefaultEditor(Object.class,null);

        } catch (Exception f) {
            f.printStackTrace();
        }


        tabelPanel = new JScrollPane(tabelInvitatii);
        tabelPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //alegere

        try {
            String cautare = null;
            if (afisare) {
                cautare = "select idInvitatie from invitatieechipa " +
                        "where numeechipa2='" + player.getNumeEchipa() + "';";
            } else {
                cautare = "select idInvitatie from invitatieindividual " +
                        "where numeJucator2='" + player.getNume() + "';";
            }
            prepare = connec.getConnection().createStatement();
            ResultSet rs = prepare.executeQuery(cautare);
            while (rs.next()) {
                iduriComboArray.add(rs.getInt(1));
            }
            for (Integer s : iduriComboArray) {
                comboIduri.addItem(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        alegereMeci.setLayout(new FlowLayout());
        alegereMeci.add(idLabel);
        alegereMeci.add(comboIduri);
        alegereMeci.add(Box.createRigidArea(new Dimension(15, 0)));
        alegereMeci.add(invitaButon);

        //back
        backPanel.setLayout(new FlowLayout());
        backPanel.add(backButon);

        //actionListeners

        backButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VizualizareInvitatii.super.dispose();
                MeniuUtilizatorBD meniu = new MeniuUtilizatorBD(connec, player);
                meniu.go();
            }
        });

        invitaButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idCautat = 0;
                boolean merge = true;
                if (comboIduri.getSelectedItem() != null) {
                    idCautat = (int) comboIduri.getSelectedItem();
                    //System.out.println(idCautat);
                } else {
                    JOptionPane.showMessageDialog(null, "Nu ai invitatii!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    merge = false;
                }
                if (merge) {
                    Random rand = new Random();
                    int randomNumberpartide = rand.nextInt(99);
                    randomNumberpartide++;

                    int scor1 = randomNumberpartide;
                    int scor2 = 100 - scor1;
                    int invingatorScor;
                    int nrPartideJucate = rand.nextInt(99);
                    nrPartideJucate++;
                    int nrPartide = 100;

                    if (scor1 > scor2) {
                        invingatorScor = 1;
                    } else {
                        invingatorScor = 2;
                    }
                    Statement prepare;
                    int idObject1 = 0;
                    int idObject2 = 0;
                    String tipDeJoc = null;
                    try {
                        if (afisare) {
                            //echipa
                            prepare = connec.getConnection().createStatement();
                            String cautare = "select idEchipa1,idEchipa2,tipJoc from invitatieechipa " +
                                    "where idInvitatie=" + idCautat + ";";
                            ResultSet rs = prepare.executeQuery(cautare);

                            while (rs.next()) {
                                idObject1 = rs.getInt(1);
                                idObject2 = rs.getInt(2);
                                tipDeJoc = rs.getString(3);
                            }
                        } else {
                            prepare = connec.getConnection().createStatement();
                            String cautare = "select idJucator1,idJucator2,tipJoc from invitatieindividual " +
                                    "where idInvitatie=" + idCautat + ";";
                            ResultSet rs = prepare.executeQuery(cautare);

                            while (rs.next()) {
                                idObject1 = rs.getInt(1);
                                idObject2 = rs.getInt(2);
                                tipDeJoc = rs.getString(3);
                            }
                        }
                        int idInvingator = 0;
                        if (invingatorScor == 1) {
                            idInvingator = idObject1;
                        } else {
                            idInvingator = idObject2;
                        }
                        if (!afisare) {
                            //echipa
                            CallableStatement test;
                            test = connec.getConnection().prepareCall(
                                    "{call generareJocIndividual(?,?,?,?,?,?,?,?)}");
                            test.setInt(1, idObject1);
                            test.setInt(2, idObject2);
                            test.setInt(3, scor1);
                            test.setInt(4, scor2);
                            test.setInt(5, nrPartide);
                            test.setInt(6, nrPartideJucate);
                            test.setInt(7, idInvingator);
                            test.setString(8, tipDeJoc);
                            test.execute();
                        } else {
                            CallableStatement test;
                            test = connec.getConnection().prepareCall(
                                    "{call generareJocEchipa(?,?,?,?,?,?,?,?)}");
                            test.setInt(1, idObject1);
                            test.setInt(2, idObject2);
                            test.setInt(3, scor1);
                            test.setInt(4, scor2);
                            test.setInt(5, nrPartide);
                            test.setInt(6, nrPartideJucate);
                            test.setInt(7, idInvingator);
                            test.setString(8, tipDeJoc);
                            test.execute();
                        }
                        VizualizareInvitatii.super.dispose();
                        MeniuUtilizatorBD meniu = new MeniuUtilizatorBD(connec, player);
                        meniu.go();
                        if (invingatorScor == 1) {
                            JOptionPane.showMessageDialog(null, "Meci incheiat!, ai pierdut!", "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Meci incheiat!, ai castigat!", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                }
            }
        });

        titluPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(titluPanel);
        tabelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(tabelPanel);
        alegereMeci.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(alegereMeci);
        backPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterPanel.add(backPanel);

        this.setContentPane(masterPanel);
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

    public JScrollPane getTabelPanel() {
        return tabelPanel;
    }

    public void setTabelPanel(JScrollPane tabelPanel) {
        this.tabelPanel = tabelPanel;
    }

    public JPanel getAlegereMeci() {
        return alegereMeci;
    }

    public void setAlegereMeci(JPanel alegereMeci) {
        this.alegereMeci = alegereMeci;
    }

    public JPanel getBackPanel() {
        return backPanel;
    }

    public void setBackPanel(JPanel backPanel) {
        this.backPanel = backPanel;
    }

    public JLabel getTitluLabel() {
        return titluLabel;
    }

    public void setTitluLabel(JLabel titluLabel) {
        this.titluLabel = titluLabel;
    }

    public JTable getTabelInvitatii() {
        return tabelInvitatii;
    }

    public void setTabelInvitatii(JTable tabelInvitatii) {
        this.tabelInvitatii = tabelInvitatii;
    }

    public JLabel getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(JLabel idLabel) {
        this.idLabel = idLabel;
    }

    public JComboBox<Integer> getComboIduri() {
        return comboIduri;
    }

    public void setComboIduri(JComboBox<Integer> comboIduri) {
        this.comboIduri = comboIduri;
    }

    public JButton getInvitaButon() {
        return invitaButon;
    }

    public void setInvitaButon(JButton invitaButon) {
        this.invitaButon = invitaButon;
    }

    public JButton getBackButon() {
        return backButon;
    }

    public void setBackButon(JButton backButon) {
        this.backButon = backButon;
    }
}
