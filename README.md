# Acme-Conference

A tener en cuenta:

1. La mejor conferencia para realizar pruebas de listados, navegar por sus 
actividades, etc. as� como realizar decisiones en sus submissions y dem�s
operaciones similares es la conferencia que se llama "British Aeroespace",
que es la que tiene m�s relaciones en el archivo 'PopulateDataBase.xml'.

2. Hemos considerado que una Activity solo puede crearse para una Conference cuyo
estado sea 'FINAL', ya que de esa manera la Conference ya no puede editarse, evitando
as� incompatibilidades con el horario que abarque una actividad en el caso de que el
horario de una conferencia pudiera cambiarse.

3. A la hora de enviar mensajes difundidos, entendemos y hemos contado con que el 
administrador, independientemente de cu�ntas registrations o submissions tenga una 
conferencia, si desea notificar a X posibles usuarios de algo en concreto, pueda 
seleccionar cualquier conferencia con estado FINAL que haya en la base de datos sin
ning�n tipo de restricci�n. En el caso de que dicha conferencia no tuviera ninguna 
registration o submission, no pasar�a nada y no se crear�a ning�n mensaje.
