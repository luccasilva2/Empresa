package empresa;

import java.sql.Connection;

import util.Conexao;

public class Principal {

	public static void main(String[] args) throws ClassNotFoundException {
			Connection con = Conexao.conectar();
			if (con != null) {
				System.out.println("Ok");
			}
			

	}

}
