package dao;

import model.Curtida;
import util.Conexao;

import java.sql.*;

public class CurtidaDAO {

    // ---------------------------------------------------------
    // MÉTODO 1 — Curtir (implementa curtir())
    // ---------------------------------------------------------
    public boolean curtir(Curtida curtida) {

        // Verifica se a curtida já existe antes de inserir
        if (curtidaExiste(curtida.getPostagemId(), curtida.getUsuarioId())) {
            System.out.println("Erro: O usuário já curtiu esta postagem.");
            return false;
        }

        // A data_curtida no SQL deve ser DATE
        String sql = "INSERT INTO curtidas (postagem_id, usuario_id, data_curtida) VALUES (?, ?, CURDATE())";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, curtida.getPostagemId());
            stmt.setInt(2, curtida.getUsuarioId());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao registrar curtida: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para verificar se a curtida já existe
    private boolean curtidaExiste(int postagemId, int usuarioId) {
        String sql = "SELECT id FROM curtidas WHERE postagem_id = ? AND usuario_id = ? LIMIT 1";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postagemId);
            stmt.setInt(2, usuarioId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Erro ao verificar curtida: " + e.getMessage());
            return true; // Assume que existe para evitar duplicidade em caso de erro
        }
    }


    // ---------------------------------------------------------
    // MÉTODO 2 — Descurtir (implementa descurtir())
    // ---------------------------------------------------------
    public boolean descurtir(int postagemId, int usuarioId) {

        // Remove a curtida do usuário na postagem específica
        String sql = "DELETE FROM curtidas WHERE postagem_id = ? AND usuario_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postagemId);
            stmt.setInt(2, usuarioId);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (Exception e) {
            System.out.println("Erro ao remover curtida: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 3 — Contar Curtidas (implementa contarCurtidas())
    // ---------------------------------------------------------
    public int contarCurtidas(int postagemId) {

        String sql = "SELECT COUNT(id) AS total FROM curtidas WHERE postagem_id = ?";
        int total = 0;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postagemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao contar curtidas: " + e.getMessage());
        }

        return total;
    }
}