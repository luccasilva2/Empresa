package empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Conexao;

public class ProjetoDAO {
    public boolean inserir(Projeto projeto) {
        // Regra de negócio 2: Verificar se funcionário existe
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if (funcionarioDAO.buscarPorId(projeto.getIdFuncionario()) == null) {
            System.err.println("Erro: Não existe funcionário com o ID " + projeto.getIdFuncionario());
            return false;
        }

        String sql = "INSERT INTO projeto (nome, descricao, id_funcionario) VALUES (?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, projeto.getNome());
            ps.setString(2, projeto.getDescricao());
            ps.setInt(3, projeto.getIdFuncionario());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    projeto.setId(rs.getInt(1));
                }
            }
            
            System.out.println("Projeto cadastrado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar projeto: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Projeto projeto) {
        // Verificar se funcionário existe
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if (funcionarioDAO.buscarPorId(projeto.getIdFuncionario()) == null) {
            System.err.println("Erro: Não existe funcionário com o ID " + projeto.getIdFuncionario());
            return false;
        }

        String sql = "UPDATE projeto SET nome = ?, descricao = ?, id_funcionario = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, projeto.getNome());
            ps.setString(2, projeto.getDescricao());
            ps.setInt(3, projeto.getIdFuncionario());
            ps.setInt(4, projeto.getId());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Projeto atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum projeto encontrado com o ID fornecido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar projeto: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM projeto WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Projeto deletado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum projeto encontrado com o ID fornecido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar projeto: " + e.getMessage());
            return false;
        }
    }

    public Projeto buscarPorId(int id) {
        String sql = "SELECT * FROM projeto WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id_funcionario")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar projeto: " + e.getMessage());
        }
        return null;
    }

    public List<Projeto> listarTodos() {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT * FROM projeto";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                projetos.add(new Projeto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getInt("id_funcionario")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar projetos: " + e.getMessage());
        }
        return projetos;
    }
}