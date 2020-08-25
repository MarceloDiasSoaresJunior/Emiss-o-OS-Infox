/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;
import dal.ModuloConexao;
import java.awt.Component;
import java.sql.*;

import java.math.BigDecimal;


/**
 *
 * @author Marcelo Dias Soares Jr
 */
public class PegarInformacao {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public void informacao() {
        String responsavel;
        BigDecimal valor;
        String sql = "Select * from tbos";
        try {
            pst = conexao.prepareStatement(sql);
            pst.executeQuery();
            valor = rs.getBigDecimal(9);
            responsavel = rs.getString(8);
        } catch (SQLException e) {
        }
    }
    
}
