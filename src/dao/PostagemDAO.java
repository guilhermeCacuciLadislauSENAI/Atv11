package dao;

import model.Postagem;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostagemDAO {

    // ---------------------------------------------------------
    // MÉTODO 1 — Criar Postagem (implementa criarPostagem())
    // ---------------------------------------------------------
    public boolean criarPostagem(Postagem postagem) {

        // A data_postagem no SQL deve ser DATE
        String sql = "INSERT INTO postagens (usuario_id, conteudo, data_postagem) VALUES (?, ?, CURDATE())";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postagem.getUsuarioId());
            stmt.setString(2, postagem.getConteudo());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao criar postagem: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 2 — Atualizar Conteúdo (implementa atualizarPostagem())
    // ---------------------------------------------------------
    public boolean atualizarPostagem(Postagem postagem) {

        String sql = "UPDATE postagens SET conteudo = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, postagem.getConteudo());
            stmt.setInt(2, postagem.getId());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar postagem: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 3 — Excluir Postagem (implementa excluirPostagem())
    // ---------------------------------------------------------
    public boolean excluirPostagem(int id) {

        String sql = "DELETE FROM postagens WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao excluir postagem: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 4 — Exibir Postagem (Busca por ID)
    // ---------------------------------------------------------
    public Postagem exibirPostagem(int id) {

        String sql = "SELECT * FROM postagens WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new Postagem(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("conteudo"),
                            rs.getDate("data_postagem").toLocalDate()
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar postagem: " + e.getMessage());
        }

        return null;
    }

    // ---------------------------------------------------------
    // MÉTODO 5 — Listar Postagens de um Usuário (Novo)
    // ---------------------------------------------------------
    /**
     * Lista todas as postagens feitas por um usuário específico.
     * @param usuarioId ID do usuário cujas postagens serão listadas.
     * @return Uma lista de objetos Postagem.
     */
    public List<Postagem> listarPostagensPorUsuario(int usuarioId) {

        List<Postagem> lista = new ArrayList<>();
        // Ordena da mais recente para a mais antiga
        String sql = "SELECT * FROM postagens WHERE usuario_id = ? ORDER BY data_postagem DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Postagem(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("conteudo"),
                            rs.getDate("data_postagem").toLocalDate()
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar postagens por usuário: " + e.getMessage());
        }

        return lista;
    }
}