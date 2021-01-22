package tabels;

public class Jucator {
    private int idJucator=0;
    private String nume;
    private String password;
    private String dataInscrierii="";
    private String dataNasterii;
    private int idEchipa = 0;
    private String numeEchipa=null;

    public Jucator() {
    }

    public Jucator(int idJucator, String nume, String password, String dataInscrierii, String dataNasterii) {
        this.idJucator = idJucator;
        this.nume = nume;
        this.password = password;
        this.dataInscrierii = dataInscrierii;
        this.dataNasterii = dataNasterii;
    }

    public Jucator(String nume, String password, String dataNasterii) {
        this.nume = nume;
        this.password = password;
        this.dataNasterii = dataNasterii;
    }



    public int getIdJucator() {
        return idJucator;
    }

    public void setIdJucator(int idJucator) {
        this.idJucator = idJucator;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataInscrierii() {
        return dataInscrierii;
    }

    public void setDataInscrierii(String dataInscrierii) {
        this.dataInscrierii = dataInscrierii;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public int getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(int idEchipa) {
        this.idEchipa = idEchipa;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    @Override
    public String toString() {
        return "Jucator{" +
                "idJucator=" + idJucator +
                ", nume='" + nume + '\'' +
                ", password='" + password + '\'' +
                ", dataInscrierii='" + dataInscrierii + '\'' +
                ", dataNasterii='" + dataNasterii + '\'' +
                ", idEchipa=" + idEchipa +
                ", numeEchipa='" + numeEchipa + '\'' +
                '}';
    }
}
