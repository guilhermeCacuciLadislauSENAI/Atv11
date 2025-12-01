package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    // ---------------------------------------------------------
    // METODO 1 — Verificar se email já existe
    // ---------------------------------------------------------
    public boolean emailExiste(String email) {

        String sql = "SELECT id FROM alunos WHERE email = ? LIMIT 1";

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
    // METODO 2 —Cadastrar com verificação
    // ---------------------------------------------------------
    public boolean cadastrar(Aluno aluno) {

        if (emailExiste(aluno.getEmail())) {
            System.out.println("Erro: E-mail já está cadastrado!");
            return false;
        }

        return inserir(aluno);
    }

    // ---------------------------------------------------------
    // METODO 3 — Inserir aluno (CREATE)
    // ---------------------------------------------------------
    public boolean inserir(Aluno aluno) {

        String sql = "INSERT INTO alunos (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getSenha());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao inserir aluno: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // METODO 4 — LISTAR TODOS
    // ---------------------------------------------------------
    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alunos";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                lista.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }

        return lista;
    }

    // ---------------------------------------------------------
    // METODO 5 — BUSCAR POR ID
    // ---------------------------------------------------------
    public Aluno buscarPorId(int id) {

        String sql = "SELECT * FROM alunos WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }

        return null;
    }

    // ---------------------------------------------------------
    // METODO 6 — ATUALIZAR ALUNO
    // ---------------------------------------------------------
    public boolean atualizar(Aluno aluno) {

        String sql = "UPDATE alunos SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getSenha());
            stmt.setInt(4, aluno.getId());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // METODO 7 — DELETAR ALUNO
    // ---------------------------------------------------------
    public boolean deletar(int id) {

        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao deletar aluno: " + e.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // METODO 8 — LOGIN (email + senha)
    // ---------------------------------------------------------
    public boolean login(String email, String senha) {

        String sql = "SELECT id FROM alunos WHERE email = ? AND senha = ? LIMIT 1";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // encontrou o usuário

        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
            return false;
        }
    }
}
