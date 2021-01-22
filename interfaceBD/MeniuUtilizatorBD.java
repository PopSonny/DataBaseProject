package interfaceBD;

import dataBase.ConnectionClass;
import tabels.Jucator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeniuUtilizatorBD extends JFrame {
    private ConnectionClass connec;
    private Jucator player;

    public MeniuUtilizatorBD(ConnectionClass connec, Jucator p) throws HeadlessException {
        this.connec = connec;
        this.player = p;
    }

    private JPanel panelMaster = new JPanel();
    private JPanel panelHeader = new JPanel();
    private JPanel panelStanga = new JPanel();
    private JPanel panelDreapta = new JPanel();
    private JPanel panelExit = new JPanel();

    //header
    private JLabel meniuL = new JLabel("Meniu");
    private JLabel numeJucatorL = new JLabel();
    private JLabel numeEchipaL = new JLabel();

    //exit
    private JButton exitB = new JButton("Exit");

    //stanga individual+ join echipa
    private JButton clasamentIndividualB = new JButton("Clasament individual");
    private JButton istoricIndividualB = new JButton("Istoric individual");
    private JButton creareInvitatieIndividualB = new JButton("Creare invitatie joc 1v1");
    private JButton invitatiiJocIndividualB = new JButton("Vizualizare invitatii 1v1");
    private JButton joinEchipaB = new JButton("Join echipa");

    //dreapta echipa
    private JButton clasamentEchipaB = new JButton("Clasament echipa");
    private JButton istoricEchipaB = new JButton("Istoric echipa");
    private JButton creareInvitatieEchipaB = new JButton("Creare invitatie joc echipa");
    private JButton invitatiiJocEchipaB = new JButton("Vizualizare invitatii echipa");
    private JButton creareEchipaB = new JButton("Creare echipa");

    public void go() {
        meniuL.setFont(new Font("Arial", Font.BOLD, 30));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 450);
        this.setLocationRelativeTo(null);
        this.setResizable(false);


        numeJucatorL.setText(player.getNume());
        numeEchipaL.setText(player.getNumeEchipa());

        panelMaster.setLayout(new BorderLayout());


        //exitpanel
        panelExit.setLayout(new FlowLayout());
        panelExit.add(exitB);

        //headerPanel
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        meniuL.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHeader.add(meniuL);
        panelHeader.add(Box.createRigidArea(new Dimension(0, 10)));
        numeJucatorL.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHeader.add(numeJucatorL);
        panelHeader.add(Box.createRigidArea(new Dimension(0, 10)));
        numeEchipaL.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHeader.add(numeEchipaL);
        panelHeader.add(Box.createRigidArea(new Dimension(0, 40)));


        //stanga
        panelStanga.setLayout(new BoxLayout(panelStanga, BoxLayout.Y_AXIS));
        clasamentIndividualB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelStanga.add(clasamentIndividualB);
        panelStanga.add(Box.createRigidArea(new Dimension(0, 10)));
        istoricIndividualB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelStanga.add(istoricIndividualB);
        panelStanga.add(Box.createRigidArea(new Dimension(0, 10)));
        creareInvitatieIndividualB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelStanga.add(creareInvitatieIndividualB);
        panelStanga.add(Box.createRigidArea(new Dimension(0, 10)));
        invitatiiJocIndividualB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelStanga.add(invitatiiJocIndividualB);
        panelStanga.add(Box.createRigidArea(new Dimension(0, 10)));
        joinEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelStanga.add(joinEchipaB);


        //dreapta
        panelDreapta.setLayout(new BoxLayout(panelDreapta, BoxLayout.Y_AXIS));
        clasamentEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDreapta.add(clasamentEchipaB);
        panelDreapta.add(Box.createRigidArea(new Dimension(0, 10)));
        istoricEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDreapta.add(istoricEchipaB);
        panelDreapta.add(Box.createRigidArea(new Dimension(0, 10)));
        creareInvitatieEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDreapta.add(creareInvitatieEchipaB);
        panelDreapta.add(Box.createRigidArea(new Dimension(0, 10)));
        invitatiiJocEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDreapta.add(invitatiiJocEchipaB);
        panelDreapta.add(Box.createRigidArea(new Dimension(0, 10)));
        creareEchipaB.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDreapta.add(creareEchipaB);


        //actionlisteners
        exitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                LogIn l = new LogIn(connec);
                l.go();
            }
        });

        joinEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                IntraInEchipa intra = new IntraInEchipa(connec, player);
                intra.go();
            }
        });

        creareEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                CreareaEchipaBD creare = new CreareaEchipaBD(connec, player);
                creare.go();
            }
        });

        clasamentIndividualB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                ClasamentBD cla = new ClasamentBD(connec, player, false);
                cla.go();
            }
        });

        clasamentEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                ClasamentBD cla = new ClasamentBD(connec, player, true);
                cla.go();
            }
        });

        istoricEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                IstoricBD is = new IstoricBD(connec, player, true);
                is.go();
            }
        });

        istoricIndividualB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                IstoricBD is = new IstoricBD(connec, player, false);
                is.go();
            }
        });

        creareInvitatieEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getNumeEchipa().equals("Nu face parte dintr-o echipa")) {
                    JOptionPane.showMessageDialog(null, "Nu faceti parte din nicio echipa!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    MeniuUtilizatorBD.super.dispose();
                    CreareInvitatie cre = new CreareInvitatie(connec, player, true);
                    cre.go();
                }
            }
        });

        creareInvitatieIndividualB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                CreareInvitatie cre = new CreareInvitatie(connec, player, false);
                cre.go();
            }
        });

        invitatiiJocEchipaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getNumeEchipa().equals("Nu face parte dintr-o echipa")) {
                    JOptionPane.showMessageDialog(null, "Nu faceti parte din nicio echipa!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    MeniuUtilizatorBD.super.dispose();
                    VizualizareInvitatii viz = new VizualizareInvitatii(connec, player, true);
                    viz.go();
                }
            }
        });

        invitatiiJocIndividualB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeniuUtilizatorBD.super.dispose();
                VizualizareInvitatii viz = new VizualizareInvitatii(connec, player, false);
                viz.go();
            }
        });


        panelMaster.add(panelHeader, BorderLayout.PAGE_START);
        panelMaster.add(panelExit, BorderLayout.PAGE_END);
        panelMaster.add(panelStanga, BorderLayout.LINE_START);
        panelMaster.add(panelDreapta, BorderLayout.LINE_END);
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

    public JPanel getPanelHeader() {
        return panelHeader;
    }

    public void setPanelHeader(JPanel panelHeader) {
        this.panelHeader = panelHeader;
    }

    public JPanel getPanelStanga() {
        return panelStanga;
    }

    public void setPanelStanga(JPanel panelStanga) {
        this.panelStanga = panelStanga;
    }

    public JPanel getPanelDreapta() {
        return panelDreapta;
    }

    public void setPanelDreapta(JPanel panelDreapta) {
        this.panelDreapta = panelDreapta;
    }

    public JPanel getPanelExit() {
        return panelExit;
    }

    public void setPanelExit(JPanel panelExit) {
        this.panelExit = panelExit;
    }

    public JLabel getMeniuL() {
        return meniuL;
    }

    public void setMeniuL(JLabel meniuL) {
        this.meniuL = meniuL;
    }

    public JLabel getNumeJucatorL() {
        return numeJucatorL;
    }

    public void setNumeJucatorL(JLabel numeJucatorL) {
        this.numeJucatorL = numeJucatorL;
    }

    public JLabel getNumeEchipaL() {
        return numeEchipaL;
    }

    public void setNumeEchipaL(JLabel numeEchipaL) {
        this.numeEchipaL = numeEchipaL;
    }

    public JButton getExitB() {
        return exitB;
    }

    public void setExitB(JButton exitB) {
        this.exitB = exitB;
    }

    public JButton getClasamentIndividualB() {
        return clasamentIndividualB;
    }

    public void setClasamentIndividualB(JButton clasamentIndividualB) {
        this.clasamentIndividualB = clasamentIndividualB;
    }

    public JButton getIstoricIndividualB() {
        return istoricIndividualB;
    }

    public void setIstoricIndividualB(JButton istoricIndividualB) {
        this.istoricIndividualB = istoricIndividualB;
    }

    public JButton getCreareInvitatieIndividualB() {
        return creareInvitatieIndividualB;
    }

    public void setCreareInvitatieIndividualB(JButton creareInvitatieIndividualB) {
        this.creareInvitatieIndividualB = creareInvitatieIndividualB;
    }

    public JButton getInvitatiiJocIndividualB() {
        return invitatiiJocIndividualB;
    }

    public void setInvitatiiJocIndividualB(JButton invitatiiJocIndividualB) {
        this.invitatiiJocIndividualB = invitatiiJocIndividualB;
    }

    public JButton getJoinEchipaB() {
        return joinEchipaB;
    }

    public void setJoinEchipaB(JButton joinEchipaB) {
        this.joinEchipaB = joinEchipaB;
    }

    public JButton getClasamentEchipaB() {
        return clasamentEchipaB;
    }

    public void setClasamentEchipaB(JButton clasamentEchipaB) {
        this.clasamentEchipaB = clasamentEchipaB;
    }

    public JButton getIstoricEchipaB() {
        return istoricEchipaB;
    }

    public void setIstoricEchipaB(JButton istoricEchipaB) {
        this.istoricEchipaB = istoricEchipaB;
    }

    public JButton getCreareInvitatieEchipaB() {
        return creareInvitatieEchipaB;
    }

    public void setCreareInvitatieEchipaB(JButton creareInvitatieEchipaB) {
        this.creareInvitatieEchipaB = creareInvitatieEchipaB;
    }

    public JButton getInvitatiiJocEchipaB() {
        return invitatiiJocEchipaB;
    }

    public void setInvitatiiJocEchipaB(JButton invitatiiJocEchipaB) {
        this.invitatiiJocEchipaB = invitatiiJocEchipaB;
    }

    public JButton getCreareEchipaB() {
        return creareEchipaB;
    }

    public void setCreareEchipaB(JButton creareEchipaB) {
        this.creareEchipaB = creareEchipaB;
    }
}
