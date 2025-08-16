🛒 Carrito de Compras - Microservicios con Spring Boot

📌 Descripción

Este proyecto consiste en el desarrollo de un Back-End de carrito de compras bajo una arquitectura de microservicios, implementado con Java (Spring Boot) y PostgreSQL.

La solución está compuesta por dos microservicios principales:

Microservicio Buscador

Encargado de la gestión de productos. Permite:

Registrar nuevos productos.

Actualizar información de productos existentes.

Realizar eliminación lógica (activación/desactivación).

Validar stock disponible.

Consultar productos por diferentes criterios: ID, categoría, nombre, precio máximo, calificación.


Este servicio expone endpoints REST que retornan respuestas estructuradas en JSON y códigos de estado HTTP adecuados (200, 201, 400, 404).


---

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

Postman (para pruebas de endpoints)

---

💻 Pruebas

Todos los endpoints pueden ejecutarse desde Postman.

---

👨‍💻 Autor

Jorge Patricio Santamaría Cherrez
Máster en Ingeniería de Software y Sistemas Informáticos


---
