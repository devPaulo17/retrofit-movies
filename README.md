# test-movies

# Estructura del proyecto.

# - Adapters
#   - DataAdapter.class: 
Esta clase proporciona acceso a los elemntos de datos, es responsable de hacer una vista para cada elemento en el         conjunto de datos. Para este caso es poder poblar una lista de datos(Peliculas) en una RecyclerView.
  
# - DataDB
#    - MovieContract.class
La Clase  nos  guarda como constantes todas las características de la base de datos, es decir, los nombre o atributos de la tabla.
#    - MoviesDbHelper.class
Se trata de una clase abstracta que nos provee los mecanismos básicos para la relación entre la aplicación Android y la información.
 

# - Fragment
#   - ListMoviesFragment.class
Clase donde nos muestra la lista de películas Mas populares

#   - MovieDetailActivityFragment.class
Clase donde nos permite visualizar el detalle de la película

#   - TopRatedFragment.class
Clase donde nos muestra la lista de películas Mas Votadas

#   - UpcomingFragment.class
Clase donde nos muestra la lista de películas Próximas a estreno.


# - Interfaces
#   - RequestInterface.class
Implementa una serie de métodos que nos permite realizar peticiones HTTP a una API por medio de la librería Retrofit

# - Models
#   - Movie.class
 Nos permite terner acceso a los datos de las películas.

#   - VideoMovie
Nos permite terner acceso a los datos de los videos(trailer) de cada película.

# - UI
#   - MovieDetailActivity.class
Clase donde contiene a través deun fragment el detalle de la película.

#   - VideoActivity.class
Clase donde nos permite visualizar el trailer de la pelìcula.

# - Utils
#   - Alert.class
Implementa una serie de métodos los cuales nos permite mostrar una serie de Alerts.

#   - CheckConexion.class
Nos permite verificar si hay o no conexión a Internet. Solo se instancia la clase donde es necesario compranoar la conexión.

#   - JSONResponse.class
Nos permite manejar la respuesta y manipulas los datos  de las películas.

#   - JSONResponseVideos.class
Nos permite manejar la respuesta y manipulas los datos de los videos(trailer) de las películas.


# - MainActivity
Es la actividad principal donde nos mostrá una serie de pelìculas por categorías(Popular, mas votados, Próximos.


# - ¿Preguntas?

# Responsabilidad única.
Desde mi punto de vista es la responsabilidad única que tiene cada clase(Alta cohesión y bajo acoplamiento). Va de la mano con los Principios SOLID, el buen uso de los patrones de diseño.

# Código Limpio.
Para mi concepto el còdigo limpio tiene las siguientes características.

* Debe ser simple de cambiar
* Los médotos no deberían hacer mas de una cosa.
* Debes ser legible
* Debemos ser capaces de hacer cambios sin tener mayores incovenientes.



