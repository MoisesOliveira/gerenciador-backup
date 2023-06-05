package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import entity.Backup;
import entity.Cliente;
import entity.Computador;

public class BackupDAOImpl implements DAO<Backup>{

	private Connection connection;
	
	public BackupDAOImpl() throws ClassNotFoundException, SQLException {
		connection = DatabaseConnector.connectMSSQL();
	}
	
	@Override
	public Backup adicionar(Backup backup) throws SQLException {
		String sql = "INSERT INTO backup(pc_id, data_inicio, data_fim, descricao) "
				+ "VALUES (?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql, 
				PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setLong(1, backup.getComputador().getId());
		statement.setDate(2, Date.valueOf(backup.getDataInicio()));
		statement.setDate(3, Date.valueOf(backup.getDataFim()));
		statement.setString(4, backup.getDescricao());
		statement.executeUpdate();
		ResultSet set = statement.getGeneratedKeys();
		if(set.next()) {
			backup.setId(set.getLong("id"));
		}
		return backup;
	}
	@Override
	public void deletar(long id) throws SQLException {
		String sql = "DELETE FROM backup WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.executeUpdate();
	}
	@Override
	public Backup pesquisarPorNome(String texto) throws SQLException {
		Backup backup = new Backup();
		Computador computador = new Computador();
		String sql = "SELECT back_up.id, back_up.data_inicio, back_up.data_fim,\r\n"
				+ "back_up.descricacao, computador.descricao\r\n"
				+ "FROM back_up, computador\r\n"
				+ "WHERE back_up.pc_id = computador.cliente_id\r\n"
				+ "AND back_up.descricacao = ?";
		String Novotexto = "%" + texto + "%";
		PreparedStatement statement = connection
				.prepareStatement(sql);
		ResultSet res = statement.executeQuery();
		while(res.next()) {
			backup.setId(res.getLong("id"));
			backup.setDataInicio(res.getDate("data_inicio").
					toLocalDate());
			backup.setComputador(computador);
			backup.getComputador().setId(res.
					getLong("pc_id"));
		}
		return backup;
	}
	@Override
	public List<Backup> pesquisarTodos() throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT * FROM cliente";
		return null;
	}
	
}