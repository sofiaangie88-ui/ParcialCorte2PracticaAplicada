package co.edu.poli.examen2_Soto.servicios;

import java.sql.Connection;
import java.sql.DriverManager;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Administra la conexión a MySQL (patrón Singleton). Las credenciales
 * se leen del archivo .env (DB_URL, DB_USER y DB_PASSWORD).
 */
public class ConexionBD {

	private static ConexionBD instancia;
	private Connection conexion;

	private ConexionBD() throws Exception {

		Dotenv dotenv = Dotenv.load();

		String url = dotenv.get("DB_URL");
		String user = dotenv.get("DB_USER");
		String pass = dotenv.get("DB_PASSWORD");

		if (url == null || user == null || pass == null) {
			throw new RuntimeException("Faltan variables en el .env");
		}

		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(url, user, pass);
	}

	/**
	 * Obtiene la única instancia de la conexión.
	 *
	 * @return instancia de ConexionBD
	 * @throws Exception si no se puede establecer la conexión
	 */
	public static ConexionBD getInstancia() throws Exception {
		if (instancia == null) {
			instancia = new ConexionBD();
		}
		return instancia;
	}

	/**
	 * Obtiene la conexión activa, reabriéndola si estaba cerrada.
	 *
	 * @return conexión a la base de datos
	 * @throws Exception si no se puede establecer la conexión
	 */
	public Connection getConexion() throws Exception {
		if (conexion == null || conexion.isClosed()) {
			instancia = new ConexionBD();
			return instancia.conexion;
		}
		return conexion;
	}
}
