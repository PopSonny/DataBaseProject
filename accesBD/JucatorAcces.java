package accesBD;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dataBase.ConnectionClass;
import tabels.Jucator;

public class JucatorAcces {
    private ConnectionClass connec;

    public JucatorAcces(ConnectionClass connec) {
        this.connec = connec;
    }

    public boolean addJucator(Jucator player) {
        CallableStatement statement;
        try {
            statement = connec.getConnection().prepareCall(
                    "{call introducereJucator(?,?,?)}");
            statement.setString(1, player.getNume());
            statement.setString(2, player.getDataNasterii());
            statement.setString(3, player.getPassword());

            statement.execute();

            Statement prepare;
            try {
                prepare = connec.getConnection().createStatement();
                ResultSet rs = prepare.executeQuery("select max(id_jucator) from jucator;");
                while (rs.next()) {
                    int id = rs.getInt(1);
                    player.setIdJucator(id);
                }

                prepare = connec.getConnection().createStatement();
                rs = prepare.executeQuery("select max(data_inscrierii) from jucator;");
                //rs = prepare.executeQuery("select nume from jucator where id_jucator=300;");

                String dataN="nimic";

                while (rs.next()) {
                    dataN = rs.getString(1);
                    //System.out.println(dataN);
                    player.setDataInscrierii(dataN);
                }

                if(dataN.equals("nimic"))
                    System.out.println("am ajuns aici");


            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
