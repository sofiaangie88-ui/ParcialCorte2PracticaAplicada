package co.edu.poli.examen2_Soto.modelo;

/**
 * Representa una tarjeta bancaria y contiene la información común para las tarjetas de crédito y débito
 *
 * @author Sofia
 */
public abstract class Tarjeta {

    private String numero;

    private String fechaExp;

    /**
     * Estado actual de la tarjeta
     */
    private EstadoTarjeta estado;

    private Titular titular;

    /**
     * Crea una tarjeta con sus datos básicos
     *
     * @param numero número de la tarjeta
     * @param fechaExp fecha de expedición de la tarjeta
     * @param estado estado inicial de la tarjeta
     * @param titular titular asociado a la tarjeta
     */
    public Tarjeta(String numero, String fechaExp, EstadoTarjeta estado, Titular titular) {
        this.numero = numero;
        this.fechaExp = fechaExp;
        this.estado = estado;
        this.titular = titular;
    }

    /**
     * @return número de la tarjeta
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero nuevo número de la tarjeta
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return fecha de expedición de la tarjeta
     */
    public String getFechaExp() {
        return fechaExp;
    }

    /**
     * @param fechaExp nueva fecha de expedición
     */
    public void setFechaExp(String fechaExp) {
        this.fechaExp = fechaExp;
    }

    /**
     * Obtiene el estado actual de la tarjeta
     *
     * @return estado actual de la tarjeta
     */
    public EstadoTarjeta getEstado() {
        return estado;
    }

    /**
     * Modifica el estado de la tarjeta
     *
     * @param estado nuevo estado de la tarjeta
     */
    public void setEstado(EstadoTarjeta estado) {
        this.estado = estado;
    }

    /**
     * @return titular de la tarjeta
     */
    public Titular getTitular() {
        return titular;
    }

    /**
     * @param titular nuevo titular de la tarjeta
     */
    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    /**
     * Cambia el estado de la tarjeta a bloqueada
     *
     * @return mensaje indicando que la tarjeta fue bloqueada
     */
    public String bloquear() {
        this.estado = EstadoTarjeta.BLOQUEADA;
        return "Tarjeta " + numero + " BLOQUEADA.";
    }

    /**
     * Cambia el estado de la tarjeta a activa
     *
     * @return mensaje indicando que la tarjeta fue activada
     */
    public String activar() {
        this.estado = EstadoTarjeta.ACTIVA;
        return "Tarjeta " + numero + " ACTIVADA.";
    }

    @Override
    public String toString() {
        return "numero=" + numero
                + ", fechaExp=" + fechaExp
                + ", estado=" + estado
                + ", titular=" + titular;
    }
}