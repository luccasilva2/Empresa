package empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Conexao;

public class PessoaDAO {

    public boolean inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, email) VALUES (?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pessoa.setId(rs.getInt(1));
                }
            }

            System.out.println("Pessoa cadastrada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar pessoa: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, email = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getEmail());
            ps.setInt(3, pessoa.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pessoa atualizada com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma pessoa encontrada com o ID fornecido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pessoa: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pessoa deletada com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma pessoa encontrada com o ID fornecido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar pessoa: " + e.getMessage());
            return false;
        }
    }

    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        null, // matrícula (caso você ainda não tenha na tabela)
                        null  // departamento
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa: " + e.getMessage());
        }
        return null;
    }

    public List<Pessoa> listarTodos() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pessoa p = new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    null,
                    null
                );
                pessoas.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pessoas: " + e.getMessage());
        }
        return pessoas;
    }
}
