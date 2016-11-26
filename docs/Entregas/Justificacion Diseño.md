# Documentación de las entregas.

## Entrega 0A

En esta entrega se creo la documentación inicial, modelando los casos de uso del modelo.

## Entrega 0B

## Entrega 1 y  2
Esta entrega tenia como objetivo :
1)Calculo de la cercanía de los pois, para este punto se aprovecha del polimorfismo declarando la clase cercaDe la cual se encarga de calcular la distancia entre dos POIs, teniendo en cuenta la particularidad de cada caso.
2)Calcular Disponibilidad en cierto momento de un poi , cada uno de estos tiene su propio calculo de disponibilidad, contemplado la particularidad de cada uno y devolviendo un boolean según si esta disponible o no.
3)Buscar puntos según un texto libre .Para esto se utiliza una clase Fuente de datos la cual se encarga de manejar la búsqueda , la baja de pois, búsqueda por nombre y actualización de locales . A su vez la clase fuente de datos tiene un atributo del tipo  Data base el cual sirve como adapter entre los distintos orígenes de datos, encapsulando la parte de obtención de datos ,lo que permite hacer búsquedas generales y dejando la particularidad de cada de cada fuente de datos al adapter.

## Entrega 3

El objetivo de esta entrega era Agregar al sistema usuarios que podían tener dos roles distintos el de Terminal y el de Administrador. Para esto se crea una clase usuario de la cual heredan Administrador y Terminal.
Para los usuarios administradores se pide que se le avise por mail si la búsqueda tarda más de una x cantidad de tiempo . Para esto se toma el tiempo que se tarda cuando se realiza una búsqueda y se compara si este supera un tiempo establecido en un archivo de configuración. 
Además de esto como se pide en el siguiente punto se almacenan las búsquedas (frase buscada, cantidad de resultados y tiempo de búsqueda)  Estos resultados se guardan en Repositorio de búsqueda el cual se encarga de manejar la búsqueda dentro del historial de búsquedas y de manejar el alta cuando se realiza la búsqueda.
(Falta la parte de reportes por fecha)
(Resultados parciales por terminal)
(Activar /desactivar  acciones por terminal)
(Esto no se si esta bien a que se refiere , ¿Que diferencia existen en identificar a la Terminal como un Usuario del Sistema o como un Dispositivo?)

## Entrega 4

(Explicar permisos administradores)
Se realizan cuatro procesos
Actualizar locales: Este proceso cuenta con un a instancia de la clase fuente de datos , la cual se encarga de realizar la actualización de locales en el cual a traves del archivo de config se llega al archivo con las actualizaciones y se las aplica a los locales.
Baja de POIs : Se encarga de darle de baja a una lista de POIs a través de una instancia de RepositorioPois, la cual a su  vez obtiene una lista de posi que tiene que dar de baja a través de un servicio rest, luego los busca en Data base y realiza un borrado físico.
Agregar acciones de usuario: Se le agregan una lista de acciones que puede cada usuario, (no entiendo bien que hace)
Procesos anidados: Esto produce que los procesos se desencadenen uno tras otro , según un orden determinado. 

## Entrega 5

Se realizan las vistas del sistema estas son 
Login: la cual se encarga de verificar si el usuario que se registra tiene acceso al sistema.
Busqueda: se ingresa una palabra clave por con la cual se realiza una búsqueda de los Pois que coinciden y se muestran en una tabla
historial de busqueda: Se le permite al usuario consultar por el historia de búsquedas que se realizaron en el sistema mostrando el detalle de estas.
Acciones de usuario:(Falta completar)

## Entrega 6

Se cambia el modo con el cual se guardan los POIs , Persistiéndolos en un Base de datos. Esto modifica las formas en las qu ese busca ya que se hace a través de querys , el guardado de los POIs se realiza en un a única tabla la cual identifica cada uno de los tipos. Además se declaran las tablas zona , coordenadas, rubro, dirección, servicio, horario y tag las cuales se relacionan a traves de Joins con la tabla de Pois.
También se realiza al persistencia de los usuarios y las acciones de los mismos

## Entrega 7 (persistencia a un medio no-relacional)
Se decidió utilizar la base de datos no relacional MongoDB debido a la alta disponibilidad de software de soporte escrito en java y su facilidad de uso.
