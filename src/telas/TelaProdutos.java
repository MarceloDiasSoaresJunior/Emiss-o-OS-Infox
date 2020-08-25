/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import dal.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Marcelo Dias Soares Jr
 */
public class TelaProdutos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaProdutos
     */
    public TelaProdutos() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar_produto() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja incluir este produto?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String validarCodigo = "Select cod_mov as A from tbprodutos where cod_mov =?";
            try {
                pst = conexao.prepareStatement(validarCodigo);
                pst.setString(1, txtCodigoItem.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "O Código a ser inserido já existe. Por Favor Verificar!");
                } else {
                    String sql = "INSERT INTO TBPRODUTOS(PRODUTO,QUANTIDADE,FORNECEDOR,FORNEFONE,COD_MOV) VALUES (?,?,?,?,?)";
                    try {
                        pst = conexao.prepareStatement(sql);
                        pst.setString(1, txtItem.getText());
                        pst.setString(2, txtQuantidade.getText().replace(" ", "").replace(".", "").replace(",", "."));
                        pst.setString(3, txtFornecedor.getText());
                        pst.setString(4, txtFoneForn.getText());
                        pst.setString(5, txtCodigoItem.getText());
                        if (txtItem.getText().isEmpty() || txtCodigoItem.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "O nome e o código do produto são obrigatórios.");
                        } else {
                            int adicionado = pst.executeUpdate();
                            if (adicionado > 0) {
                                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
                                txtItem.setText(null);
                                txtQuantidade.setText(null);
                                txtFornecedor.setText(null);
                                txtFornecedor.setText(null);
                                txtCodigoItem.setText(null);
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);

                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    private void pesquisar_produto() {
        String sql = "SELECT ID AS ID, PRODUTO AS Produto, quantidade as Quantidade, fornecedor as Fornecedor,fornefone as Fone, cod_mov as Código from tbprodutos where produto like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarProduto.getText() + "%");
            rs = pst.executeQuery();
            tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void setar_campos() {
        int setar = tblProdutos.getSelectedRow();
        txtItem.setText(tblProdutos.getModel().getValueAt(setar, 1).toString());
        txtQuantidade.setText(tblProdutos.getModel().getValueAt(setar, 2).toString().replace(".", ","));
        txtFornecedor.setText(tblProdutos.getModel().getValueAt(setar, 3).toString());
        txtFoneForn.setText(tblProdutos.getModel().getValueAt(setar, 4).toString());
        txtCodigoItem.setText(tblProdutos.getModel().getValueAt(setar, 5).toString());
        btnAdicionarProduto.setEnabled(false);
        btnDeletarProduto.setEnabled(true);
        txtCodigoItem.setEnabled(false);
    }

    private void alterar_produtos() {
        String sql = "UPDATE TBPRODUTOS SET PRODUTO = ?,QUANTIDADE=?,FORNECEDOR = ?, FORNEFONE=?, WHERE ID=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtItem.getText());
            pst.setString(2, txtQuantidade.getText().replace(" ", "").replace(".", "").replace(",", "."));
            pst.setString(3, txtFornecedor.getText());
            pst.setString(4, txtFoneForn.getText());
            int setar = tblProdutos.getSelectedRow();
            pst.setString(5, (tblProdutos.getModel().getValueAt(setar, 0).toString()));
            if (txtItem.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do produto é obrigatório");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(this, "Produto alterado com sucesso");
                    txtItem.setText(null);
                    txtQuantidade.setText(null);
                    txtFornecedor.setText(null);
                    txtFoneForn.setText(null);
                    btnAdicionarProduto.setEnabled(true);
                    btnDeletarProduto.setEnabled(false);
                    pesquisar_produto();
                }
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Não há nenhum produto para ser alterado. Escolha um produto pesquisando por ele.");
            //System.out.println(e);
        } catch (Exception i) {
            System.out.println(i);
        }
    }

    private void deletar() {
        int confirma = JOptionPane.showConfirmDialog(this, "Confirma a exlusão deste produto?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM TBPRODUTOS WHERE ID = ?";
            try {
                pst = conexao.prepareStatement(sql);
                int setar = tblProdutos.getSelectedRow();
                pst.setString(1, (tblProdutos.getModel().getValueAt(setar, 0).toString()));
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso.");
                    txtItem.setText(null);
                    txtQuantidade.setText(null);
                    txtFornecedor.setText(null);
                    txtFoneForn.setText(null);
                    btnAdicionarProduto.setEnabled(true);
                    btnDeletarProduto.setEnabled(false);
                    pesquisar_produto();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFornecedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFoneForn = new javax.swing.JTextField();
        txtPesquisarProduto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        btnAdicionarProduto = new javax.swing.JButton();
        btnAlterarProduto = new javax.swing.JButton();
        btnDeletarProduto = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoItem = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produtos");
        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setText("*ITEM");

        jLabel2.setText("QUANTIDADE");

        jLabel3.setText("FORNECEDOR");

        jLabel4.setText("FONE");

        txtFoneForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoneFornActionPerformed(evt);
            }
        });

        txtPesquisarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarProdutoActionPerformed(evt);
            }
        });
        txtPesquisarProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarProdutoKeyReleased(evt);
            }
        });

        jLabel5.setText("Pesquisar");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisar.png"))); // NOI18N

        tblProdutos.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Produto", "Quantidade", "Fornecedor", "Fone", "Código"
            }
        ));
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProdutos);

        btnAdicionarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        btnAdicionarProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarProdutoActionPerformed(evt);
            }
        });

        btnAlterarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        btnAlterarProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarProdutoActionPerformed(evt);
            }
        });

        btnDeletarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btnDeletarProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarProduto.setEnabled(false);
        btnDeletarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarProdutoActionPerformed(evt);
            }
        });

        jLabel7.setText("*CÓDIGO");

        txtCodigoItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoItemFocusLost(evt);
            }
        });
        txtCodigoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(btnAdicionarProduto)
                        .addGap(18, 18, 18)
                        .addComponent(btnAlterarProduto)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeletarProduto))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtItem)
                                    .addComponent(txtQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFoneForn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtPesquisarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel6)
                            .addGap(124, 124, 124))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCodigoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFoneForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAlterarProduto)
                    .addComponent(btnAdicionarProduto)
                    .addComponent(btnDeletarProduto))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        setBounds(0, 0, 640, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFoneFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoneFornActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoneFornActionPerformed

    private void txtPesquisarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisarProdutoActionPerformed

    private void btnAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarProdutoActionPerformed
        // TODO add your handling code here:
        adicionar_produto();
    }//GEN-LAST:event_btnAdicionarProdutoActionPerformed

    private void txtPesquisarProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarProdutoKeyReleased
        // TODO add your handling code here:
        pesquisar_produto();
    }//GEN-LAST:event_txtPesquisarProdutoKeyReleased

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        // TODO add your handling code here:
        setar_campos();
        txtPesquisarProduto.setText(null);
    }//GEN-LAST:event_tblProdutosMouseClicked

    private void btnAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarProdutoActionPerformed
        // TODO add your handling code here:
        alterar_produtos();
    }//GEN-LAST:event_btnAlterarProdutoActionPerformed

    private void btnDeletarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarProdutoActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnDeletarProdutoActionPerformed

    private void txtCodigoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoItemActionPerformed

    private void txtCodigoItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoItemFocusLost
        // TODO add your handling code here:
        String a = txtCodigoItem.getText().toString();
        if (a.isEmpty()){
            JOptionPane.showMessageDialog(this,"O código do item é OBRIGATÓRIO.");
        }
    }//GEN-LAST:event_txtCodigoItemFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarProduto;
    private javax.swing.JButton btnAlterarProduto;
    private javax.swing.JButton btnDeletarProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtCodigoItem;
    private javax.swing.JTextField txtFoneForn;
    private javax.swing.JTextField txtFornecedor;
    private javax.swing.JTextField txtItem;
    private javax.swing.JTextField txtPesquisarProduto;
    private javax.swing.JTextField txtQuantidade;
    // End of variables declaration//GEN-END:variables
}
