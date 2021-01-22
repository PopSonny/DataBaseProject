package tabels;

public class Echipa {
    private int idEchipa;
    private String nume;
    private String password;
    private String dataInscrierii;

    public Echipa(int idEchipa, String nume, String password, String dataInscrierii) {
        this.idEchipa = idEchipa;
        this.nume = nume;
        this.password = password;
        this.dataInscrierii = dataInscrierii;
    }

    public int getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(int idEchipa) {
        this.idEchipa = idEchipa;
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

    @Override
    public String toString() {
        return "Echipa{" +
                "idEchipa=" + idEchipa +
                ", nume='" + nume + '\'' +
                ", password='" + password + '\'' +
                ", dataInscrierii='" + dataInscrierii + '\'' +
                '}';
    }
}
