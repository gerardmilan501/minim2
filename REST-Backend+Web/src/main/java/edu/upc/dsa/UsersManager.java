package edu.upc.dsa;

import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;
import java.util.*;
import java.util.List;

public interface UsersManager {

    public int size();

    public User addUser(String username, String pwd);
    public User addUser(User u);
    public User getUser(String id);
    public List<User> findAll();
    public void deleteUser(String id);
    public User updateUser(User u);

    public boolean userExists(String username);
    public boolean checkPassword(String username, String password);


    public int sizePartidas();
    public Partida addPartida(String id_partida, String id_usuario);
    public Partida addPartida(Partida p);
    public Partida getPartida(String id_partida);
    public List<Partida> findAllPartidas();
    public void deletePartida(String id);
    public Partida updatePartida(Partida p);

    public boolean partidaExists(String id_partida);
    public boolean checkUsuario(String id_partida, String id_usuario);


}
