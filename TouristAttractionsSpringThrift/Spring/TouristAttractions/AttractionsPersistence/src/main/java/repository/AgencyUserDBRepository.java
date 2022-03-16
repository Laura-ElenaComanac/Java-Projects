package repository;

import model.AgencyUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AgencyUserDBRepository implements AgencyUserRepository{

    private final static Logger log = LogManager.getLogger(AgencyUserDBRepository.class);
    private JdbcUtils jdbcUtils;

    public AgencyUserDBRepository(Properties props){
        log.info("Initializing TripDBRepository with properties: {} ", props);
        jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public void add(AgencyUser el){
        log.traceEntry("saving agencyUser {} ",el);
        Connection con=jdbcUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("INSERT INTO agencyUsers VALUES (?,?,?)")){
            preStmt.setInt(1, el.getId());
            preStmt.setString(2, el.getUserName());
            preStmt.setString(3, el.getPassword());
            int result=preStmt.executeUpdate();
            log.trace("Saved {} instances", result);
        }
        catch (SQLException ex){
            log.error(ex);
            System.out.println("Error DB "+ex);
        }

        log.traceExit();
    }

    @Override
    public void delete(AgencyUser el){
        log.traceEntry("DELETING agencyUser {} ", el);
        Connection con=jdbcUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("DELETE FROM agencyUsers WHERE id=?")){
            preStmt.setInt(1, el.getId());
            int result=preStmt.executeUpdate();
            log.trace("Deleted {} instances", result);
        }
        catch (SQLException ex){
            log.error(ex);
            System.out.println("Error DB "+ex);
        }

        log.traceExit();
    }

    @Override
    public void update(AgencyUser el, Integer id){
        log.traceEntry("updating agencyUser {} ",el);
        Connection con=jdbcUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement(
                "UPDATE agencyUsers SET id=?, userName=?, password=? WHERE id=?")){
            preStmt.setInt(1, el.getId());
            preStmt.setString(2, el.getUserName());
            preStmt.setString(3, el.getPassword());
            preStmt.setInt(4, id);
            int result=preStmt.executeUpdate();
            log.trace("Updated {} instances", result);
        }
        catch (SQLException ex){
            log.error(ex);
            System.out.println("Error DB "+ ex);
        }

        log.traceExit();
    }


    @Override
    public AgencyUser findById(Integer id){
        log.traceEntry("finding agencyUser by id {} ", id);
        Connection con=jdbcUtils.getConnection();
        AgencyUser agencyUser = null;

        try(PreparedStatement preStmt=con.prepareStatement("SELECT * FROM agencyUsers WHERE id=?")) {
            preStmt.setInt(1, id);

            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id2 = result.getInt("id");
                    String userName = result.getString("userName");
                    String password = result.getString("password");

                    agencyUser = new AgencyUser(id2, userName, password);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            System.out.println("Error DB "+e);
        }

        log.traceExit(agencyUser);
        return agencyUser;
    }

    @Override
    public Iterable<AgencyUser> findAll() {
        Connection con=jdbcUtils.getConnection();
        List<AgencyUser> agencyUsers=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("SELECT * FROM agencyUsers")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String userName = result.getString("userName");
                    String password = result.getString("password");

                    AgencyUser agencyUser = new AgencyUser(id, userName, password);
                    agencyUsers.add(agencyUser);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            System.out.println("Error DB "+e);
        }

        log.traceExit(agencyUsers);
        return agencyUsers;
    }

    @Override
    public int size() {
        log.traceEntry("SIZE of agencyUsers");
        Connection con=jdbcUtils.getConnection();
        int result;

        try(PreparedStatement preStmt=con.prepareStatement("SELECT COUNT(*) FROM agencyUsers")){
            result=preStmt.executeUpdate();
            log.trace("SIZE {}", result);
            return result;
        }
        catch (SQLException ex){
            log.error(ex);
            System.out.println("Error DB "+ex);
        }

        log.traceExit();
        return 0;
    }

    @Override
    public AgencyUser filterAgencyUserByUserNameAndPassword(String userName, String password) {
        Connection con=jdbcUtils.getConnection();
        AgencyUser agencyUser = null;

        try(PreparedStatement preStmt=con.prepareStatement("SELECT * FROM agencyUsers WHERE userName=? AND password=?")) {
            preStmt.setString(1, userName);
            preStmt.setString(2, password);

            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String userName2 = result.getString("userName");
                    String password2 = result.getString("password");

                    agencyUser = new AgencyUser(id, userName2, password2);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            System.out.println("Error DB "+e);
        }

        log.traceExit(agencyUser);
        return agencyUser;
    }
}
