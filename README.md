🛒 Carrito de Compras - Microservicios con Spring Boot & PostgreSQL

📌 Descripción

Este proyecto consiste en el desarrollo de un Back-End de carrito de compras bajo una arquitectura de microservicios, implementado con Java (Spring Boot) y PostgreSQL.

La solución está compuesta por dos microservicios principales:

Microservicio Buscador: encargado de la gestión de productos (registro, actualización, eliminación lógica, validación de stock, asociación a categorías).

Microservicio Operador: responsable de la administración del carrito de compras (agregar, actualizar, eliminar ítems, calcular el total y procesar compras).


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


🌐 Mejoras futuras

Integración de autenticación y autorización con Spring Security + JWT.


💻 Pruebas

Todos los endpoints pueden ejecutarse desde Postman.

👨‍💻 Autor

Jorge Patricio Santamaría Cherrez
Máster en Ingeniería de Software y Sistemas Informáticos


---

