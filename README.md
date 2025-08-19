🛒 Carrito de Compras - Microservicios con Spring Boot

📌 Descripción

Este proyecto consiste en el desarrollo de un Back-End de carrito de compras bajo una arquitectura de microservicios, implementado con Java (Spring Boot) y PostgreSQL.

---

La solución está compuesta por dos microservicios principales:

Microservicio Buscador

Encargado de la gestión de productos. Permite:

Registrar nuevos productos.

Actualizar información de productos existentes.

Realizar eliminación lógica (activación/desactivación).

Validar stock disponible.

Consultar productos por diferentes criterios: ID, categoría, nombre, precio máximo, calificación.


Este servicio expone endpoints REST que retornan respuestas estructuradas en JSON y códigos de estado HTTP adecuados (200, 201, 400, 404).

Microservicio Operador

Responsable de la administración del carrito de compras. Permite:

Crear y actualizar carritos.

Agregar y eliminar ítems.

Listar los productos añadidos al carrito.

Calcular el subtotal de los ítems.

Procesar compras y actualizar el estado del carrito.

Al igual que el Buscador, expone endpoints REST que devuelven códigos de estado HTTP apropiados (200, 201, 400, 404).
Este microservicio puede integrarse con el Buscador para validar la existencia de productos y disponibilidad de stock antes de completar una compra.

---

Además, se integran Spring Cloud Gateway como servidor perimetral y Eureka Server como registro de servicios para el descubrimiento dinámico.


---


⚙️ Tecnologías utilizadas

Java 17+

Spring Boot

Spring Cloud (Eureka, Gateway)

PostgreSQL

Maven

Postman (para pruebas de endpoints)


---

🚀 Endpoints principales

🔹 Microservicio Buscador (Productos)

POST /api/products → Registrar producto

GET /api/products → Listar productos

GET /api/products/{id} → Buscar producto por ID

GET /api/products/category/{id_category} → Listar productos por categoría

GET /api/products/name/{nombre} → Buscar producto por nombre

GET /api/products/price/{precio} → Listar productos con precio menor o igual

PUT /api/products → Actualizar producto

PUT /api/products/activate-deactivate → Activar/Desactivar producto

POST /api/products/stock → Validar stock disponible


🔹 Microservicio Operador (Carrito de Compras)

POST /api/shopping-cart → Crear o actualizar carrito

DELETE /api/shopping-cart/{id_producto} → Eliminar ítem del carrito

GET /api/shopping-cart/items → Listar ítems del carrito

PUT /api/payments → Realizar la compra y actualizar el estado del carrito

---

💻 Pruebas

Todos los endpoints pueden ejecutarse desde Postman.

---

En el repositorio se adjuntan:

- 📂 Archivos de base de datos
- 📂 Colección de **Postman** lista para importar y ejecutar las pruebas.  


---

👨‍💻 Autor

Jorge Patricio Santamaría Cherrez

Máster en Ingeniería de Software y Sistemas Informáticos
