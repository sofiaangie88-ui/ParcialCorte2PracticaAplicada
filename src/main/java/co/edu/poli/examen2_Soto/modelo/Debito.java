package co.edu.poli.examen2_Soto.modelo;

/**
 * Representa una tarjeta de débito y su saldo disponible
 *
 * @author Sofia
 */
public class Debito extends Tarjeta {

	private double saldo;

	/**
	 * Crea una tarjeta de débito
	 *
	 * @param numero número de la tarjeta
	 * @param fechaExp fecha de expedición de la tarjeta
	 * @param estado estado inicial de la tarjeta
	 * @param titular titular de la tarjeta
	 * @param saldo saldo inicial de la tarjeta
	 */
	public Debito(String numero, String fechaExp, EstadoTarjeta estado, Titular titular, double saldo) {
		super(numero, fechaExp, estado, titular);
		this.saldo = saldo;
	}

	/**
	 * @return saldo de la tarjeta
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo nuevo saldo de la tarjeta
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Debito [" + super.toString() + ", saldo=" + saldo + "]";
	}
}