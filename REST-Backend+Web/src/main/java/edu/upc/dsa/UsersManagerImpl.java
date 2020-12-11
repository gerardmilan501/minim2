package edu.upc.dsa;

import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class UsersManagerImpl implements UsersManager {
    private static UsersManager instance;

    protected List<User> users;
    protected List<Partida> partidas;

    final static Logger logger = Logger.getLogger(UsersManagerImpl.class);

    private UsersManagerImpl() {
        this.users = new LinkedList<>();
    }

    public static UsersManager getInstance() {
        if (instance==null) instance = new UsersManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.users.size();
        logger.info("size users =" + ret);

        return ret;
    }

    public User addUser(User t) {
        logger.info("new user to add: " + t);

        this.users.add (t);
        logger.info("new User added");
        return t;
    }

    public User addUser(String username, String pwd) {
        return this.addUser(new User(username, pwd));
    }

    public User getUser(String id) {
        logger.info("getUser("+id+")");

        for (User t: this.users) {
            if (t.getId().equals(id)) {
                logger.info("getUser("+id+"): "+t);

                return t;
            }
        }

        logger.warn("user not found with this id: " + id);
        return null;
    }

    public boolean userExists(String username) {

        for (User t: this.users) {

            if (t.getUsername().equals(username)) {

                return true;

            }

        }

        return false;
    }

    public boolean checkPassword(String username, String password) {

        for (User t : this.users) {

            if (t.getUsername().equals(username)) {

                if (t.getPwd().equals(password)) {

                    return true;

                } else {

                    return false;
                }

            }

        }

        return false;

    }

    public List<User> findAll() {
        return this.users;
    }

    @Override
    public void deleteUser(String id) {
        logger.info("Want to delete user with this id: " +id);
        User t = this.getUser(id);
        if (t==null) {
            logger.warn("user not found " +t);
        }
        else logger.info(t+"User deleted ");

        this.users.remove(t);

    }

    @Override
    public User updateUser(User p) {
        User u = this.getUser(p.getId());

        if (u!=null) {
            logger.info(p+" rebut!!!! ");

            u.setPwd(p.getPwd());
            u.setUsername(p.getUsername());

            logger.info(u+"User updated ");
        }
        else {
            logger.warn("User not found "+p);
        }

        return u;
    }
                                                                            //PARTIDAS//        //      //      //
    public int sizePartidas() {
        int retp = this.partidas.size();
        logger.info("size partidas " + retp);

        return retp;
    }

    public Partida addPartida(Partida p) {
        logger.info("new Partida to create: " + p);

        this.partidas.add (p);
        logger.info("new Partida created");
        return p;
    }

    public Partida addPartida(String id_partida, String id_usuario) {
        return this.addPartida(new Partida(id_partida, id_usuario));
    }

    public Partida getPartida(String id_partida) {
        logger.info("getPartida("+id_partida+")");

        for (Partida p: this.partidas) {
            if (p.getId_partida().equals(id_partida)) {
                logger.info("getPartida("+id_partida+"): "+p);

                return p;
            }
        }

        logger.warn("partida not found with this id: " + id_partida);
        return null;
    }

    public boolean partidaExists(String id_partida) {

        for (Partida p: this.partidas) {

            if (p.getId_partida().equals(id_partida)) {

                return true;

            }

        }

        return false;
    }





    public List<Partida> findAllPartidas() {
        return this.partidas;
    }

    @Override
    public void deletePartida(String id_partida) {
        logger.info("Want to delete 'Partida' with this id: " +id_partida);
        Partida p = this.getPartida(id_partida);
        if (p==null) {
            logger.warn("Partida not found " +p);
        }
        else logger.info(p+"Partida deleted ");

        this.partidas.remove(p);

    }

    @Override
    public Partida updatePartida(Partida p) {
        Partida u = this.getPartida(p.getId_partida());

        if (u!=null) {
            logger.info(p+" rebut!!!! ");

            u.setId_usuario(p.getId_usuario());
            u.setId_partida(p.getId_partida());

            logger.info(u+"Partida updated ");
        }
        else {
            logger.warn("Partida not found "+p);
        }

        return u;
    }

    public boolean checkUsuario(String id_partida, String id_usuario) {

        for (Partida p: this.partidas) {

            if (p.getId_partida().equals(id_partida)) {

                if (p.getId_usuario().equals(id_usuario)) {

                    return true;

                } else {

                    return false;
                }

            }

        }

        return false;

    }

}