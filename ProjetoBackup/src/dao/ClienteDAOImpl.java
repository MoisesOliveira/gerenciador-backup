package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import entity.Cliente;

public class ClienteDAOImpl implements DAO<Cliente>{

	private static Connection connection;
	
	public ClienteDAOImpl() throws ClassNotFoundException, SQLException {
		connection = DatabaseConnector.connectMSSQL();
	}
	
	@Override
	public Cliente adicionar(Cliente cliente) throws SQLException {
		String sql = "INSERT INTO cliente(nome, data_nascimento) VALUES (?,?)";
		PreparedStatement statement = 
				connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setString(1, cliente.getNome());
		statement.setDate(2, Date.valueOf(cliente.getDataNascimento()));
		statement.executeUpdate();
		ResultSet result = statement.getGeneratedKeys();
		if(result.next()) {
			cliente.setId(result.getLong("id"));
		}
		return cliente;
	}
	
	@Override
	public void deletar(long id) throws SQLException {
		String sql = "DELETE FROM cliente WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.executeUpdate();
	}
	
	@Override
	public Cliente pesquisarPorNome(String texto) throws SQLException {
		Cliente cliente = new Cliente();
		String sql = "SELECT * FROM cliente WHERE nome LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		String novoTexto = "%" + texto + "%";
		statement.setString(1, novoTexto);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			cliente.setId(set.getLong("id"));
			cliente.setNome(set.getString("nome"));
			cliente.setDataNascimento(set.getDate("data_nascimento").
					toLocalDate());
		}
		return cliente;
	}
	
	@Override
	public List<Cliente> pesquisarTodos() throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT * FROM cliente";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			Cliente cliente = new Cliente();
			cliente.setId(set.getLong("id"));
			cliente.setDataNascimento(set.getDate("data_nascimento").
					toLocalDate());
			cliente.setNome(set.getString("nome"));
			clientes.add(cliente);
			
		}
		return clientes;
	}
	
	public int getQtdeComputadoresPorCliente(Cliente cliente) {
		return 0;
	}
	
	public int getLimiteDoPlano(Cliente cliente) {
		String sql = "SELECT plano.limite FROM plano, assinatura, cliente\r\n"
				+ "WHERE plano.id = assinatura.plano_id \r\n"
				+ "AND cliente.id = assinatura.cliente_id\r\n"
				+ "AND cliente.id = ?";
		return 0;
	}
}