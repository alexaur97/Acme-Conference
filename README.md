# Acme-Conference

A tener en cuenta:

1. La mejor conferencia para realizar pruebas de listados, navegar por sus 
actividades, etc. así como realizar decisiones en sus submissions y demás
operaciones similares es la conferencia que se llama "British Aeroespace",
que es la que tiene más relaciones en el archivo 'PopulateDataBase.xml'.

2. Hemos considerado que una Activity solo puede crearse para una Conference cuyo
estado sea 'FINAL', ya que de esa manera la Conference ya no puede editarse, evitando
así incompatibilidades con el horario que abarque una actividad en el caso de que el
horario de una conferencia pudiera cambiarse.

3. A la hora de enviar mensajes difundidos, entendemos y hemos contado con que el 
administrador, independientemente de cuántas registrations o submissions tenga una 
conferencia, si desea notificar a X posibles usuarios de algo en concreto, pueda 
seleccionar cualquier conferencia con estado FINAL que haya en la base de datos sin
ningún tipo de restricción. En el caso de que dicha conferencia no tuviera ninguna 
registration o submission, no pasaría nada y no se crearía ningún mensaje.

4. Las mejores Submissions para asignar son:
- La del autor Andrei, conferencia British Aerospace, ya que es la que se asigna a 3 
reviewers. 
- La del autor Robin, conferencia British Aerospace, ya que, después de asignar
la anterior, con esta aparecería que sólo se ha podido asignar a menos de 3 revisores.
- La del autor Marco, conferencia The Best Conference, con esta aparecería que sólo se
ha podido asignar a menos de 3 revisores.

5. Hemos considerado que el atributo targetUrl de la entidad Sponsorship es la URL
hacia donde el navegador debe dirigirse al clicar en el banner del Sponsorship. Aun así,
también mostramos como String el atributo targetUrl en la vista show.jsp de Sponsorship,
por si acaso.