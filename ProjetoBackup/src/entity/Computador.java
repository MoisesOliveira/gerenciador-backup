package entity;

public class Computador {

	private long id;
	private String descricao;
	private Cliente cliente;
	private String clienteNome;
	private String IP;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getClienteNome() {
		return cliente.getNome();
	}
	
	public void setIP(String iP) {
		this.IP = iP;
	}
	public String getIP() {
		return IP;
	}
}
