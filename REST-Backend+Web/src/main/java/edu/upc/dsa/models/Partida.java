package edu.upc.dsa.models;
import java.util.*;
import edu.upc.dsa.util.RandomUtils;

public class Partida {

    String id_partida;
    String id_usuario;


    public Partida() {
    }

    public Partida(String id_partida, String id_usuario) {
        this();
        this.setId_partida(id_partida);
        this.setId_usuario(id_usuario);
    }

    public String getId_partida() {
        return id_partida;
    }
    public void setId_partida(String id_partida) {
        this.id_partida = id_partida;
    }


    public String getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "Partida [idPartida = "+id_partida+", idUsuario = " +id_usuario+"]";
    }

}
