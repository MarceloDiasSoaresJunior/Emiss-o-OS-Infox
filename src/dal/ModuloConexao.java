/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.*;

/**
 *
 * @author User
 */
public class ModuloConexao {

    //Método responsável por estabelecer a conexão com o banco.
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // A linha abaixo chama o driver que foi importado para a Biblioteca
        String driver = "com.mysql.jdbc.Driver";
        // Armazendando informações referentes ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "";
        //Estabelecendo conexão com o banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //A linha comentada abaixo serve de apoio p/ identificar um erro de conexão
            //System.out.println(e);
            return null;
        }
    }
}