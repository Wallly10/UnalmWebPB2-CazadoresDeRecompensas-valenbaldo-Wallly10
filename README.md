# Agencia de Seguridad - Sistema de Gestión de Cazadores y Prófugos

## Integrantes : 

- Valentina Garcia Baldomar 
- Walter Pablo Jose Rodriguez 

## Descripción del Proyecto
 
El Jefe de una Agencia de Seguridad nos encargó desarrollar un sistema para organizar las operaciones de sus cazadores de recompensas, que actualmente considera un "caos total".  
 
El sistema gestiona tres tipos de cazadores:  
- **Cazadores Urbanos**: especialistas en entornos civiles.  
- **Cazadores Rurales**: expertos en zonas agrestes, rivales tradicionales de los urbanos.  
- **Cazadores Sigilosos**: expertos en capturas discretas.  
 
Cada prófugo posee:  
- Un nivel de inocencia (menor valor implica mayor culpabilidad).  
- Un nivel de habilidad (1 a 100) para evadir la captura.  
- Un atributo que indica si es nervioso o no.  
 
## Funcionalidades Principales
 
### Parte I - Proceso de Captura  
- La Agencia envía un cazador a una zona con prófugos.  
- El cazador intenta capturar a cada prófugo según condiciones generales y específicas:  
  - **Condición general:** experiencia del cazador > inocencia del prófugo.  
  - **Condición específica:** varía según tipo de cazador (ver detalles abajo).  
- Si falla en la captura, el cazador intimida al prófugo, reduciendo su inocencia y modificando atributos según el tipo de cazador.  
- El cazador gana experiencia tras la operación:  
  `experiencia += (mínimo nivel de habilidad de prófugos intimidados) + (2 * cantidad de prófugos capturados)`  
- Los prófugos capturados se eliminan de la zona.
 
### Parte II - Evolución de Prófugos  
- Prófugos pueden entrenarse y evolucionar:  
  - **Artes marciales avanzadas:** duplican habilidad hasta un máximo de 100.  
  - **Entrenamiento de élite:** no pueden ser nerviosos.  
  - **Protección legal:** inocencia mínima de 40.  
- Los entrenamientos se acumulan sin perder beneficios anteriores.
 
### Parte III - Reportes  
El sistema provee:  
- Listado de todos los prófugos capturados.  
- Prófugo más hábil capturado.  
- Cazador con mayor cantidad de capturas.
 
---
 
## Condiciones Específicas de Captura
 
| Tipo de Cazador  | Condición para capturar prófugo                  |
|------------------|-------------------------------------------------|
| Cazador Sigiloso | Habilidad del prófugo < 50                       |
| Cazador Rural    | Prófugo debe ser nervioso                        |
| Cazador Urbano   | Prófugo no debe ser nervioso                     |
 
---

## Tecnologías y Herramientas

- Lenguaje: **Java**
- IDE: **Eclipse**
- Framework de pruebas: **JUnit**  
- Control de versiones: **Git y GitHub**  
- Comunicación y reuniones: **Microsoft Teams**

---

## Trabajo en Equipo

Este proyecto fue desarrollado con énfasis en el trabajo colaborativo, fomentando la comunicación continua y el control de versiones efectivo.  

- **GitHub**:  
  - Uso de repositorios compartidos con rama principal con las ramas propias y pull requests para revisiones de código.
  - Tratamos de implementar buenas practicas a la hora de realizar los commits, mediante el uso de comentarios, para poder expresar lo que se realizo en cada uno . Con el objetivo de que el compañero este al tanto de los cambios y poder llevar un registro de los avances en el proyecto.

- **Microsoft Teams**:  
  - Reuniones diarias de seguimiento a partir del viernes 27 de junio para coordinar tareas, resolver dudas y mantener sincronizado el progreso.
 
Lo que más nos llevamos de esta materia, gracias a este trabajo práctico final, es que pudimos complementarnos el uno al otro, por ejemplo al usar colecciones, excepciones y también la herramienta GitHub. Pudimos resolver un trabajo muy interesante y le damos las gracias a ustedes por la oportunidad de poder haberlo llevado a cabo.
