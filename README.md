# ParcialCorte2PracticaAplicada

## Detalles de la aplicación

La aplicación es un sistema desarrollado en Java para la gestión de tarjetas de crédito y débito asociadas a un titular.

El proyecto utiliza JavaFX para la interfaz gráfica y MySQL para el almacenamiento persistente de la información. También se utiliza una estructura basada en clases y DAO para separar el manejo de la información y las operaciones realizadas sobre la base de datos.

La aplicación permite crear y consultar tarjetas, almacenando información como:

- Número de tarjeta.
- Fecha de expiración.
- Titular.
- Tipo de tarjeta.
- Saldo para tarjetas débito.
- Límite para tarjetas crédito.
- Estado de la tarjeta.

Como parte de la práctica se implementó la Historia de Usuario HU-01, mediante la cual se agregó la funcionalidad para asignar y controlar el estado de las tarjetas.

Los estados disponibles son:

- ACTIVA
- BLOQUEADA
- CANCELADA

El estado hace parte de la información de la tarjeta desde su creación. También se almacena en la base de datos y puede ser consultado posteriormente junto con los demás datos de la tarjeta.

Para manejar los estados permitidos se creó el enumerado `EstadoTarjeta`. De esta manera, la aplicación trabaja únicamente con los estados establecidos en la historia de usuario.

También se agregaron métodos en la clase `Tarjeta` para consultar y modificar el estado, además de permitir el cambio de una tarjeta a estado bloqueado o activo.

En el formulario principal se agregó la selección del estado y se implementó la validación correspondiente para evitar que una tarjeta pueda ser creada sin un estado válido.



## Historia de Usuario HU-01

**Como analista de crédito, quiero asignar un estado a una tarjeta para controlar si puede ser utilizada.**

### Criterios de aceptación

1. La tarjeta deberá poder encontrarse en uno de los siguientes estados:
   - Activa
   - Bloqueada
   - Cancelada

2. El estado de la tarjeta deberá almacenarse de forma persistente en la base de datos.

3. El estado de la tarjeta deberá visualizarse en el formulario principal de la aplicación.

4. El estado será un campo obligatorio durante el proceso de creación de una tarjeta.

5. No será posible guardar una tarjeta sin un estado válido.



## Implementación de la HU-01

La implementación de esta historia de usuario implicó realizar cambios en el modelo de tarjetas, la interfaz gráfica, el controlador, la capa de acceso a datos y la base de datos.

### Modelo

Se creó el enumerado `EstadoTarjeta`, que contiene los tres estados definidos para las tarjetas

En la clase abstracta `Tarjeta` se agregó el atributo `estado`, permitiendo que todas las tarjetas tengan asociado uno de los estados definidos.

También se implementaron los métodos `getEstado()` y `setEstado()` para consultar y modificar el estado de una tarjeta.

Se agregaron los métodos `bloquear()` y `activar()`, que permiten cambiar el estado de la tarjeta según la operación realizada.

Las clases `Credito` y `Debito` mantienen la herencia de `Tarjeta`, por lo que ambas utilizan el nuevo atributo y los métodos relacionados con el estado.

### Interfaz gráfica

En el formulario principal se agregó un campo para seleccionar el estado de la tarjeta.

El usuario puede seleccionar entre `ACTIVA`, `BLOQUEADA` y `CANCELADA`. Las opciones se obtienen a partir del enumerado `EstadoTarjeta`.

El estado seleccionado se toma en cuenta durante el proceso de creación de la tarjeta y queda asociado al objeto que se va a guardar.

### Validación

El estado se estableció como un campo obligatorio.

Antes de crear una tarjeta, el controlador verifica que exista un estado seleccionado. Si el usuario intenta guardar la tarjeta sin seleccionar uno, el proceso se detiene y se muestra un mensaje indicando que debe seleccionar un estado.

También se realiza una validación en la capa de acceso a datos para evitar que una tarjeta sin estado válido sea almacenada en la base de datos.

### Base de datos

La tabla `tarjeta` fue modificada para incluir el estado de la tarjeta.

El estado seleccionado en la aplicación se almacena junto con los demás datos de la tarjeta. De esta forma, la información permanece disponible aunque la aplicación se cierre.

Cuando se consulta una tarjeta, el estado almacenado en MySQL se recupera y se asigna nuevamente al objeto correspondiente.

Esto permite mantener el mismo estado entre la información mostrada en la aplicación y la información almacenada en la base de datos.

### Acceso a datos

La clase `DAOTarjeta` fue actualizada para manejar el nuevo atributo.

Durante la creación de una tarjeta se incluye el estado seleccionado en la operación de almacenamiento.

En las consultas también se recupera el estado desde MySQL y se convierte nuevamente al tipo `EstadoTarjeta`.

De esta manera, tanto las tarjetas de crédito como las tarjetas débito pueden almacenar y recuperar correctamente su estado.



## Estimación de la Historia de Usuario

La Historia de Usuario HU-01 tiene una estimación de **5 puntos de historia**.

La estimación contempla los cambios necesarios para implementar la funcionalidad completa, incluyendo las modificaciones realizadas en el modelo, la interfaz gráfica, las validaciones, la capa de acceso a datos, la base de datos y las pruebas.



## Tareas técnicas necesarias

### 1. Modificar el modelo de tarjetas

- Crear el enumerado `EstadoTarjeta`.
- Definir los estados `ACTIVA`, `BLOQUEADA` y `CANCELADA`.
- Agregar el atributo `estado` a la clase `Tarjeta`.
- Modificar el constructor para recibir el estado.
- Implementar los métodos `getEstado()` y `setEstado()`.
- Implementar los métodos `bloquear()` y `activar()`.
- Mantener la funcionalidad para las clases `Credito` y `Debito`.

### 2. Actualizar la base de datos

- Agregar el campo `estado` a la tabla `tarjeta`.
- Establecer el campo como obligatorio.
- Actualizar el archivo `ScriptDB.sql`.
- Mantener los estados definidos para la historia de usuario.
- Agregar datos de prueba para comprobar el funcionamiento.

### 3. Actualizar la capa de acceso a datos

Modificar `DAOTarjeta` para:

- Guardar el estado al crear una tarjeta.
- Validar que exista un estado válido.
- Recuperar el estado durante las consultas.
- Convertir el valor almacenado en MySQL al tipo `EstadoTarjeta`.
- Asociar el estado recuperado al objeto `Tarjeta`.

### 4. Actualizar la interfaz gráfica

- Agregar el campo de selección del estado.
- Cargar los estados disponibles.
- Permitir seleccionar entre Activa, Bloqueada y Cancelada.
- Obtener el estado seleccionado durante la creación.
- Limpiar el campo después de una creación exitosa.

### 5. Implementar las validaciones

- Verificar que el estado haya sido seleccionado.
- Evitar la creación de tarjetas sin estado.
- Mostrar un mensaje cuando el estado sea obligatorio y no haya sido seleccionado.
- Validar el estado antes de almacenarlo en la base de datos.
- Evitar valores diferentes a los establecidos en `EstadoTarjeta`.

### 6. Actualizar las consultas

- Recuperar el estado almacenado en MySQL.
- Asociarlo con la tarjeta consultada.
- Mostrar el estado junto con la información de la tarjeta.
- Mantener el funcionamiento para tarjetas de crédito y débito.

### 7. Realizar pruebas

- Verificar los tres estados disponibles.
- Crear tarjetas con estado Activa.
- Crear tarjetas con estado Bloqueada.
- Crear tarjetas con estado Cancelada.
- Consultar tarjetas y verificar su estado.
- Comprobar que el estado permanezca almacenado después de guardar.
- Verificar los métodos `bloquear()` y `activar()`.
- Intentar crear una tarjeta sin estado y comprobar que la aplicación lo impida.

### 8. Documentar los cambios

- Agregar JavaDoc a las clases modificadas.
- Documentar el enumerado `EstadoTarjeta`.
- Documentar los métodos relacionados con el estado.
- Documentar las modificaciones realizadas en `DAOTarjeta`.
- Mantener documentada la funcionalidad implementada para la HU-01.
