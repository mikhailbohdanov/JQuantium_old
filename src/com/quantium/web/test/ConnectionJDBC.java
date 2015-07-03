package com.quantium.web.test;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 01.05.2015.
 */
public class ConnectionJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbforwork";
    private static final String USERNAME = "root";
    private static final String PASSVORD = "";


    private Connection connection;
    MessageFromServer  mes;

    public ConnectionJDBC(){}

    public void init() {
        Driver driver = null;
        try {
            driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSVORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error create driver!");
        }
    }

    public void addDate(MessageFromServer mes){
        init();
        try {
            Statement statement = connection.createStatement();
            String querySQL = "INSERT INTO message(Context, Date) VALUES (\""+mes.getContext()+
                    "\", \""+mes.getDate()+"\")";
            statement.executeUpdate(querySQL);
            JOptionPane.showMessageDialog(null, querySQL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error query1!");
        }
        close();
    }

    public ArrayList<MessageFromServer> getData(){
        init();
        ArrayList<MessageFromServer> list = new ArrayList<MessageFromServer>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM message");
            while (resultSet.next()){
                mes  = new MessageFromServer();
                mes.setContext(resultSet.getString("Context"));
                mes.setDate(resultSet.getString("Date"));
                list.add(mes);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error query!");
        }
        close();
        return list;
    }

    public void close(){
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error close connection!");
            }
        }
    }

//    public static void main(String[] args) {
//        ConnectionJDBC jdbc = new ConnectionJDBC();
//        MessageFromServer mes = new MessageFromServer();
//        mes.setContext(JOptionPane.showInputDialog(null, "Context"));
//        jdbc.addDate(mes);
//        Server server = new Server();
////        server.sendToDB(JOptionPane.showInputDialog(null));
//        jdbc.getData();
//    }
}