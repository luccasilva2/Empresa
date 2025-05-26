package empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Conexao;

public class FuncionarioDAO {
    public boolean inserir(Funcionario funcionario) {
        // Regra de negócio 1: Verificar se o ID da pessoa existe
        PessoaDAO pessoaDAO = new PessoaDAO();
        if (pessoaDAO.buscarPorId(funcionario.getId()) == null) {
            System.err.println("Erro: Não existe pessoa com o ID " + funcionario.getId());
            return false;
        }

        // Validar formato da matrícula
        if (!funcionario.getMatricula().matches("F\\d{3}")) {
            System.err.println("Erro: Matrícula deve estar no formato FXXX (ex: F001)");
            return false;
        }

        String sql = "INSERT INTO funcionario (id, matricula, departamento) VALUES (?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, funcionario.getId());
            ps.setString(2, funcionario.getMatricula());
            ps.setString(3, funcionario.getDepartamento());
            ps.executeUpdate();
            
            System.out.println("Funcionário cadastrado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET matricula = ?, departamento = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, funcionario.getMatricula());
            ps.setString(2, funcionario.getDepartamento());
            ps.setInt(3, funcionario.getId());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Funcionário atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum funcionário encontrado com o ID fornecido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        // Regra de negócio 3: Verificar se funcionário está vinculado a projeto
        if (estaVinculadoAProjeto(id)) {
            System.err.println("Erro: Funcionário está vinculado a um projeto e não pode ser excluído.");
            return false;
        }

        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Funcionário deletado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum funcionário encontrado com o ID fornecido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar funcionário: " + e.getMessage());
            return false;
        }
    }

    private boolean estaVinculadoAProjeto(int idFuncionario) {
        String sql = "SELECT COUNT(*) FROM projeto WHERE id_funcionario = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idFuncionario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar vínculos: " + e.getMessage());
        }
        return false;
    }

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT p.*, f.matricula, f.departamento FROM pessoa p " +
                     "JOIN funcionario f ON p.id = f.id WHERE p.id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("departamento")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT p.*, f.matricula, f.departamento FROM pessoa p " +
                     "JOIN funcionario f ON p.id = f.id";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                funcionarios.add(new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("matricula"),
                    rs.getString("departamento")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }
}