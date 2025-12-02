package dao;

import model.Direct;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectDAO {

    // ---------------------------------------------------------
    // MÉTODO 1 — Enviar Mensagem (implementa enviarMensagem())
    // ---------------------------------------------------------
    public boolean enviarMensagem(Direct direct) {

        // A data_envio no SQL deve ser DATE
        String sql = "INSERT INTO direct (remetente_id, destinatario_id, mensagem, data_envio) VALUES (?, ?, ?, CURDATE())";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, direct.getRemetenteId());
            stmt.setInt(2, direct.getDestinatarioId());
            stmt.setString(3, direct.getMensagem());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 2 — Listar Mensagens (implementa listarMensagensEntreUsuarios())
    // ---------------------------------------------------------
    public List<Direct> listarMensagensEntreUsuarios(int usuario1Id, int usuario2Id) {

        List<Direct> mensagens = new ArrayList<>();

        // Busca mensagens enviadas de U1 para U2 E de U2 para U1, ordenadas cronologicamente
        String sql = "SELECT * FROM direct " +
                "WHERE (remetente_id = ? AND destinatario_id = ?) OR " +
                "      (remetente_id = ? AND destinatario_id = ?) " +
                "ORDER BY data_envio ASC, id ASC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Para (U1 -> U2)
            stmt.setInt(1, usuario1Id);
            stmt.setInt(2, usuario2Id);
            // Para (U2 -> U1)
            stmt.setInt(3, usuario2Id);
            stmt.setInt(4, usuario1Id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mensagens.add(new Direct(
                            rs.getInt("id"),
                            rs.getInt("remetente_id"),
                            rs.getInt("destinatario_id"),
                            rs.getString("mensagem"),
                            rs.getDate("data_envio").toLocalDate()
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar mensagens: " + e.getMessage());
        }

        return mensagens;
    }
}