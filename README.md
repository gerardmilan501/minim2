

Lluís: Cuando tenga tiempo (a partir del sábado) seguiré con la App. Lo siguiente que intentaré añadir es:
-> Dashboard
-> Fragments y navigation
-> Splashscreen
-> Guardar usuario después de iniciar sesión por primera vez
-> Pulir un poco más todo el tema de la API
-> Fotos de perfil para los usuarios
-> Ordenar a los usuarios por puntuación en la lista (para que sea un ranking)
-> Añadir más cosas al register (rollo email y esas cosas que puedan faltar)
-> Añadir alguna pantalla más o menú    (Pantalla para que el usuario pueda modificar su config: username,mail,pwd...)
-> Añadir un "loading" mientras se descarga o envía información del/al servidor
-> Añadir opción de borrar usuario (darse de baja)                       (Pots posar-ho dins de la pantalla aquesta ^)
-> En la API cambiar Path por Query en el Login y el Register 
-> Añadir opción de cerrar sesión (logout) 
-> Añadir "screen" a la máquina remota para poder ejecutar el servidor en segundo plano, y que así no se cierre al cerrar la terminal desde la que nos conectamos remotamente.
-> Establecer una longitud mínima y máxima para los nombres de usuario y contraseñas (a la hora de registrarse)
-> Obligar a que las contraseñas introducidas tengan un cierto formato (por ejemplo mínimo 1 mayúscula y 1 minúscula, 1 carácter especial, 1 número...)
-> Si se añade el campo "email" al register o al login, añadir un sistema mediante el cual se compruebe que el formato del email introducido es el correcto (básicamente para que no se pueda escribir algo que no sea un email)

-> Cualquier otra cosa que vaya surgiendo o se os ocurra


En cuanto al backend:
-> Hay que retocar también la API (ya que si se retoca en la parte de Android, estos cambios deben ir acompañados también de sus respectivos cambios en el Backend)
-> Conectar el backend con la base de datos   ->     TINC LA BBDD.SQL PERO EL SERVIDOR DONA ERROR QUAN L'INTENTEM EXECUTAR//MVN EXEC:JAVA        !!!

Web:
-> Falta vincular la web con la API           ->      debido al mismo ERROR [^]
-> Diseñar vista catálogo de mapas, objetos...?
-> Vista para usuario modificar cualquiera de sus datos
-> Vista para el admin para poder modificar cualquier dato de cualquier usuario
-> Poner tambla para ver el ranking de Puntuación 
-> Vista para ver cesta de productos del usuario
-> Vista para introducir método de pago?¿
-> Vista ("Shop done!")

Backend Añadir:
-> Clase Enemigo ( Enemigo1, Enemigo2, Enemigo3)
-> Clase Objeto ( Objeto1,... )
-> Clase Arma ( Arma1,... )
-> Sesiones(Login, Logout)

-> ObjectSender
-> QueryHelper
-> Connectar bbdd

Part Botiga(Backend): 
- Clase Cistella (Lista)
- Clase productos (Hashmap?)
- Clase Orders (Cola) (SELECT Orders From User WHERE Username....)o millor amb ID?

Màquina
- BBDD.sql a máquina
- Exportar BBDD DESDE la máquina a IntellIJ
