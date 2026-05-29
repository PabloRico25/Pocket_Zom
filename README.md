INTEGRANTES:
1. Pablo Rico
2. Bastian Diaz

DESCRIPCION DEL PROYECTO: Nuestro proyecto se trata de un juego de cartas con temática de zombis y post-apocalíptico. Usara una reglas similares a los TCG. Incluye una función de roles, facciones y logros que se les asigna a los jugadores.

FUNCIONALIDADES IMPLEMENTADAS: Se implementan las 10 funciones.
1. Billetera (PORT:8086): Contiene una billetera con las monedas que tiene el jugador al que esta asociada, métodos para agregar o restar dinero y un listado de los movimientos que hace el jugador con la cantidad y el tipo de movimiento almacenado en una clase.
2. CartaCatalogo (PORT:8082):  Contiene un listado de las cartas disponibles sus atributos y su estado. Permite crear/eliminar cartas y asignarlas a los jugadores.
3. Compra (PORT:8034): Contiene una serie de sobres que poseen cartas aleatorios agrupadas por rareza y que una vez abierta consumen dinero del jugador y las cartas ganadas son agregadas al jugador.
4. Publicación (PORT:8080): Permite a un jugador crear una publicación con alguna carta que este desee indicando el precio de venta, para que otro jugador la compre.
5. Perfil (PORT:8091): Contiene toda la información sobre el perfil del jugador: sus datos, su rol y facción a la que pertenece.
6. Rango (PORT:8089): Clasifica a los jugadores en distintas categorías en base a sus victorias y derrotas. Permite listar los jugadores en cada categoría.
7. Logro (PORT:8090): Le da premios a los jugadores en base a acciones que vaya realizando mientras juga. Los premios generalmente son moneda de juego.
8. Partida (PORT:8085): Permite que dos jugadores se enfrenten en esta etapa lo único que permite es crear un registro de la partida, los jugadores y sus mazos usados, junto con la fecha. Al finalizar actualiza al registro indicando el ganador.
9. Inventario (PORT:8083): Corresponde a las cartas que un jugador a obtenido a lo largo del juego. No solo indica que cartas también indica la cantidad de cartas con el mismo código(repetidas).
10. Mazo (PORT:8084): Agrupa las cartas del inventario de un jugador en mazos con id única para ser usados en partidas. Mas adelante se implementara una función para limitar la cantidad de copias por carta y el numero máximo de cartas por mazo.

PASOS A EJECUTAR:
1. Se debe iniciar los 10 MicroServicio y el EurekaServer. Una vez hecho esto se crean automáticamente los Roles Jugador y Administrador.
2. Registrar dos jugadores usando el método POST en el endpoint /api/v1/jugadores/registro. En el cuerpo debe ir, en formato JSON, el nombre de usuario, email y contraseña.
3. Crear facción mediante el método POST en el endpoint /api/v1/facciones. En el cuerpo debe ir el nombre de la facción, la id del jugador líder, el nivel de infección y el bono atributo.
4. Unir a los jugadores a la facción usando el método POST en el endpoint /api/v1/jugador-faccion/{idJugador}/{idFaccion}. En este caso no se necesita un cuerpo.
5. Crear cartas mediante el método POST en el endpoint /api/v1/cartas. Se usa el cuerpo JSON con el código de la carta, nombre, raza, ataque, defensa, coste, habilidad y activa. Este ultimo indica si esta permitida limitada o prohibida.
6. Crear billetera para jugadores usando el método POST con el endpoint /api/v1/carteras/{idJugador}. No se necesita cuerpo alguno.
7. Ingresar dinero a la billetera usando el método POST con el endpoint /api/v1/movimientos/{idJugador}. Se debe tener en el cuerpo: tipo de movimiento, monto y algún mensaje.
8. Crear inventario del jugador usando el método POST con el endpoint /api/v1/inventarios/{idJugador}. No es necesario un cuerpo solo necesita especificar la id del jugador a crear el inventario en el.
9. Agregar una carta al inventario usando el método POST con el endpoint /api/v1/inventario/cartas/{idJugador}/agregar. El cuerpo necesita tener el código de carta y la cantidad de estas a agregar al inventario.
10. Crear un mazo mediante el método POST con el endpoint /api/v1/mazos/{idJugador}. El cuerpo incluye el nombre del mazo y si es el que esta usando el jugador.
11. Agregar cartas a mazo usando el método POST con el endpoint /api/v1/mazos/{idMazo}/cartas. El cuerpo debe indicar el código de la carta y la cantidad de esta.
12. Crea clasificación para un jugador con el método POST usando el endpoint /api/v1/ranking/{idJugador}. No se necesita cuerpo
13. Crear partida con el método POST en el endpoint /api/v1/partidas. El cuerpo debe contener la id de los jugadores y la id de sus respectivos mazos.
14. Finalizar la partida usando el método PUT en el endpoint /api/v1/partidas/{idPartida}/finalizar. EL cuerpo debe indicar la id del ganador.
15. Para publicar una carta usando el método POST en el endpoint /api/v1/publicaciones. El cuerpo indica la id del jugador que la vende, el código de la carta y el precio.
16. Comprar cartas usando el método POST con el endpoint /api/v1/transacciones/{idJugador}. El cuerpo debe indicar la id de la publicacon y le id del jugador será la id del comprador.
17. Crear un sobre de cartas usando el método POST en el endpoint /api/v1/suministro. Debe indicar el nombre del sobre, su costo, cantidad de cartas y probabilidad de cada tipo de carta según su rareze.
18. Abrir un sobre usando el método POST con el endpoint /api/v1/aperturas/1. Indicando la id del sobre, con el atributo suministroId.
19. Crear logo usando el método POST con en endpoint api/v1/logros. EL cuerpo debe contener la id, el nombre, la descipcion, la condición y la recompensa del logro.
20. Desbloquear el logro usando el método POST con el endpoint /api/v1/logros/jugador/{idJugador}/{idLogro}. Se le asigna el logro idLogro al jugador con idJugador.

