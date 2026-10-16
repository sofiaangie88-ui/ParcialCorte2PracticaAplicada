package co.edu.poli.examen2_Soto.modelo;

/**
 * Representa una tarjeta de crédito y su límite disponible
 *
 * @author Sofia
 */
public class Credito extends Tarjeta {

	private double limite;

	/**
	 * Crea una tarjeta de crédito
	 *
	 * @param numero número de la tarjeta
	 * @param fechaExp fecha de expedición de la tarjeta
	 * @param estado estado inicial de la tarjeta
	 * @param titular titular de la tarjeta
	 * @param limite límite de crédito de la tarjeta
	 */
	public Credito(String numero, String fechaExp, EstadoTarjeta estado, Titular titular, double limite) {
		super(numero, fechaExp, estado, titular);
		this.limite = limite;
	}

	/**
	 * @return límite de crédito de la tarjeta
	 */
	public double getLimite() {
		return limite;
	}

	/**
	 * @param limite nuevo límite de crédito
	 */
	public void setLimite(double limite) {
		this.limite = limite;
	}

	@Override
	public String toString() {
		return "Credito [" + super.toString() + ", limite=" + limite + "]";
	}
}