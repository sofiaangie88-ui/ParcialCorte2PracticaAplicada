# ParcialCorte2PracticaAplicada

## Detalles de la aplicación

La aplicación es un sistema desarrollado en **Java** que permite gestionar tarjetas de crédito y débito asociadas a un titular.

Para su desarrollo se utilizan las siguientes tecnologías:

- **Java** para la lógica de la aplicación.
- **JavaFX** para la interfaz gráfica.
- **MySQL** para la persistencia de la información.
- **Maven** para la gestión y construcción del proyecto.
- **DAO** para las operaciones de acceso a la base de datos.

El sistema permite realizar operaciones relacionadas con la creación y consulta de tarjetas, conservando información como:

- Número de tarjeta.
- Fecha de expiración.
- Titular.
- Tipo de tarjeta.
- Saldo, para tarjetas débito.
- Límite, para tarjetas crédito.
- Estado de la tarjeta.

### Funcionalidad implementada

Como parte de este parcial se implementó la **Historia de Usuario HU-01**, incorporando el manejo del estado de las tarjetas.

Esta funcionalidad permite controlar la situación actual de cada tarjeta mediante tres estados:

> **ACTIVA**  
> **BLOQUEADA**  
> **CANCELADA**

El estado ahora forma parte de la información propia de cada tarjeta y se establece desde el momento de su creación.

Además, el estado seleccionado:

- Se muestra en el formulario de la aplicación.
- Es obligatorio para crear una tarjeta.
- Se almacena de forma persistente en MySQL.
- Se recupera nuevamente al consultar una tarjeta.
- Se encuentra asociado tanto a tarjetas de crédito como de débito.

Para controlar los valores permitidos se creó el enumerado `EstadoTarjeta`, evitando que se puedan utilizar estados diferentes a los definidos para la historia de usuario.

---

# Historia de Usuario HU-01

> **Como analista de crédito, quiero asignar un estado a una tarjeta para controlar si puede ser utilizada.**

## Criterios de aceptación

### 1. Estados disponibles

La tarjeta deberá poder encontrarse en uno de los siguientes estados:

- `ACTIVA`
- `BLOQUEADA`
- `CANCELADA`

### 2. Persistencia

El estado de la tarjeta deberá almacenarse de forma persistente en la base de datos.

### 3. Visualización

El estado de la tarjeta deberá visualizarse en el formulario principal de la aplicación.

### 4. Campo obligatorio

El estado será un campo obligatorio durante el proceso de creación de una tarjeta.

### 5. Validación

No será posible guardar una tarjeta sin un estado válido.

---

# Implementación de la HU-01

La implementación de la historia de usuario requirió modificaciones en el **modelo, interfaz gráfica, controlador, acceso a datos y base de datos**.

## Modelo de tarjetas

Se creó el enumerado `EstadoTarjeta`, encargado de definir los únicos estados permitidos:

```java
public enum EstadoTarjeta {
    ACTIVA,
    BLOQUEADA,
    CANCELADA
}
```

La clase abstracta `Tarjeta` fue modificada para incorporar el atributo:

```java
private EstadoTarjeta estado;
```

De esta manera, todas las tarjetas heredan el estado definido en la clase principal.

También se agregaron los métodos:

- `getEstado()`
- `setEstado()`
- `bloquear()`
- `activar()`

El método `bloquear()` permite cambiar el estado de la tarjeta a `BLOQUEADA`, mientras que `activar()` permite establecer nuevamente el estado `ACTIVA`.

Las clases `Credito` y `Debito` continúan heredando de `Tarjeta`, por lo que ambas utilizan la nueva funcionalidad de estados.

---

## Interfaz gráfica

En el formulario principal se agregó un campo de selección para el estado de la tarjeta.

El usuario puede seleccionar entre:

```text
ACTIVA
BLOQUEADA
CANCELADA
```

Los estados se cargan desde el enumerado `EstadoTarjeta`, evitando tener que escribir manualmente los valores.

Esto permite que el usuario seleccione el estado directamente desde la interfaz antes de realizar el registro de la tarjeta.

---

## Validación del estado

El estado se estableció como un campo obligatorio.

Antes de crear la tarjeta, el controlador verifica que el usuario haya seleccionado un estado.

Si no se selecciona ninguno, la aplicación:

- Detiene el proceso de creación.
- Evita enviar información incompleta a la base de datos.
- Muestra un mensaje indicando que se debe seleccionar un estado válido.

También se realiza una validación en la capa de acceso a datos para evitar que una tarjeta sin estado pueda almacenarse.

De esta forma, la validación se realiza tanto desde la interfaz como desde la lógica de persistencia.

---

## Persistencia en MySQL

La tabla `tarjeta` fue actualizada para almacenar el nuevo estado.

El estado seleccionado en la aplicación se guarda junto con la información de la tarjeta.

Al crear una tarjeta, el valor correspondiente al enumerado se envía a MySQL.

Por ejemplo:

```text
ACTIVA
BLOQUEADA
CANCELADA
```

Cuando posteriormente se consulta la tarjeta, el valor almacenado en MySQL se recupera y se convierte nuevamente en un valor de `EstadoTarjeta`.

Esto permite mantener la información sincronizada entre la aplicación y la base de datos.

---

## Acceso a datos

La clase `DAOTarjeta` fue modificada para incluir el estado en las operaciones relacionadas con las tarjetas.

### Creación

Durante la creación se obtiene el estado seleccionado y se almacena junto con los demás datos de la tarjeta.

### Consulta

Al consultar una tarjeta, el estado almacenado en MySQL es recuperado y asignado nuevamente al objeto correspondiente.

Esto permite que la información consultada conserve el estado original de la tarjeta.

---

# Flujo de la funcionalidad

La funcionalidad implementada sigue el siguiente flujo:

```text
Usuario
   ↓
Ingresa los datos de la tarjeta
   ↓
Selecciona el estado
   ↓
¿Estado válido?
   │
   ├── NO → Mostrar mensaje de validación
   │
   └── SÍ
        ↓
Crear tarjeta
        ↓
Guardar información en MySQL
        ↓
Tarjeta almacenada con su estado
        ↓
Consultar tarjeta
        ↓
Mostrar información y estado
```

De esta manera, el estado acompaña a la tarjeta durante todo el proceso de creación, almacenamiento y consulta.

---

# Estimación de la Historia de Usuario

La **Historia de Usuario HU-01** tiene una estimación de:

### 5 puntos de historia

La estimación contempla los cambios necesarios para incorporar la funcionalidad completa, incluyendo:

- Modificación del modelo.
- Creación del enumerado de estados.
- Actualización de la interfaz.
- Implementación de validaciones.
- Modificación del acceso a datos.
- Actualización de la base de datos.
- Persistencia del estado.
- Consulta del estado.
- Pruebas de la funcionalidad.

---

# Tareas técnicas necesarias

## 1. Incorporar el estado al modelo

- Crear el enumerado `EstadoTarjeta`.
- Definir los estados `ACTIVA`, `BLOQUEADA` y `CANCELADA`.
- Agregar el atributo `estado` a `Tarjeta`.
- Modificar el constructor de `Tarjeta`.
- Implementar `getEstado()` y `setEstado()`.
- Implementar `bloquear()` y `activar()`.
- Mantener la funcionalidad en las clases `Credito` y `Debito`.

---

## 2. Actualizar la base de datos

- Agregar el campo `estado` a la tabla `tarjeta`.
- Establecer el campo como obligatorio.
- Mantener los valores correspondientes a los tres estados permitidos.
- Actualizar el archivo `ScriptDB.sql`.
- Agregar información de prueba para verificar el funcionamiento.

---

## 3. Actualizar la capa DAO

Modificar `DAOTarjeta` para:

- Guardar el estado al crear una tarjeta.
- Validar que exista un estado antes de almacenar.
- Recuperar el estado durante las consultas.
- Convertir el valor recuperado a `EstadoTarjeta`.
- Asociar el estado nuevamente con el objeto `Tarjeta`.

---

## 4. Actualizar la interfaz gráfica

- Agregar el selector de estado.
- Cargar las opciones disponibles.
- Permitir seleccionar `ACTIVA`, `BLOQUEADA` o `CANCELADA`.
- Obtener el estado seleccionado al crear una tarjeta.
- Limpiar el selector después de una creación exitosa.

---

## 5. Implementar las validaciones

- Verificar que el estado haya sido seleccionado.
- Impedir la creación de tarjetas sin estado.
- Mostrar mensajes informativos al usuario.
- Validar nuevamente el estado antes de almacenarlo en la base de datos.
- Evitar valores diferentes a los definidos en `EstadoTarjeta`.

---

## 6. Actualizar las consultas

- Recuperar el estado desde MySQL.
- Asociarlo con la tarjeta consultada.
- Mostrar el estado junto con los demás datos.
- Mantener el funcionamiento para tarjetas de crédito y débito.

---

## 7. Realizar pruebas

- Comprobar los tres estados disponibles.
- Crear tarjetas con estado `ACTIVA`.
- Crear tarjetas con estado `BLOQUEADA`.
- Crear tarjetas con estado `CANCELADA`.
- Consultar tarjetas y verificar su estado.
- Comprobar que el estado permanezca almacenado después de guardar.
- Verificar los métodos `bloquear()` y `activar()`.
- Intentar crear una tarjeta sin estado y comprobar que la aplicación lo impida.

---

## 8. Documentar los cambios

- Agregar JavaDoc a las clases modificadas.
- Documentar el enumerado `EstadoTarjeta`.
- Documentar los métodos relacionados con el estado.
- Documentar las modificaciones realizadas en `DAOTarjeta`.
- Mantener documentada la funcionalidad implementada para la HU-01.

---

# Resultado de la implementación

Con las modificaciones realizadas, la aplicación permite que cada tarjeta tenga un estado definido entre **Activa, Bloqueada y Cancelada**.

El estado se selecciona durante la creación, se valida antes de guardar, se almacena de forma persistente en MySQL y puede recuperarse posteriormente al consultar la tarjeta.

De esta manera, la implementación cumple con los criterios de aceptación establecidos para la **Historia de Usuario HU-01**.
