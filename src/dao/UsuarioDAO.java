package dao;

import model.Usuario;
import util.Conexao;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // ---------------------------------------------------------
    // MÉTODO 1 — Verificar se email já existe
    // ---------------------------------------------------------
    public boolean emailExiste(String email) {

        String sql = "SELECT id FROM usuarios WHERE email = ? LIMIT 1";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Email encontrado

        } catch (Exception e) {
            System.out.println("Erro ao verificar email: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 2 — Cadastrar (implementa cadastrarUsuario())
    // ---------------------------------------------------------
    public boolean cadastrarUsuario(Usuario usuario) {

        if (emailExiste(usuario.getEmail())) {
            System.out.println("Erro: E-mail já está cadastrado!");
            return false;
        }

        // A data_cadastro no SQL deve ser DATE
        String sql = "INSERT INTO usuarios (nome, email, senha, data_cadastro) VALUES (?, ?, ?, CURDATE())";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 3 — Atualizar (implementa atualizarUsuario())
    // ---------------------------------------------------------
    public boolean atualizarUsuario(Usuario usuario) {

        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 4 — Deletar (implementa excluirUsuario())
    // ---------------------------------------------------------
    public boolean excluirUsuario(int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // MÉTODO 5 — Exibir Informações (Busca por ID)
    // ---------------------------------------------------------
    public Usuario exibirInformacoes(int id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getDate("data_cadastro").toLocalDate()
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }

        return null;
    }

    // ---------------------------------------------------------
    // MÉTODO 6 — Listar Todos (Novo)
    // ---------------------------------------------------------
    /**
     * Lista todos os usuários cadastrados no banco de dados.
     * @return Uma lista de objetos Usuario.
     */
    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nome ASC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getDate("data_cadastro").toLocalDate()
                ));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    // ---------------------------------------------------------
    // MÉTODO 7 — Login (email + senha) (Adaptado para Sessão)
    // ---------------------------------------------------------
    /**
     * Tenta logar um usuário. Se for bem-sucedido, retorna o objeto Usuario completo.
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @return O objeto Usuario se o login for válido, ou null caso contrário.
     */
    public Usuario login(String email, String senha) {

        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ? LIMIT 1";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getDate("data_cadastro").toLocalDate()
                );
            }

        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
        return null;
    }
}