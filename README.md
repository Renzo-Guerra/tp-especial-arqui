## Agregar un monopatin

POST http://localhost:8001/administracion/monopatines
{
  "latitud": 2,
  "longitud": 4,
  "gps": 545,
  "estado": "disponible"
}

////////////////////////////////////////////////////////

## Quitar monopatin
## Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso)
## Registrar fin de mantenimiento de monopatín

PUT http://localhost:8001/administracion/monopatines/{{id}}/estado/{{estado}}

estados validos => "disponible", "ocupado", "mantenimiento", "deshabilitado"

/////////////////////////////////////////////////////////
## Registrar parada

POST http://localhost:8001/administracion/paradas

{
  "longitud": -35.245,
  "latitud": -68.142,
  "isHabilitada": true
}

/////////////////////////////////////////////////////////
## Quitar parada

PUT http://localhost:8001/administracion/paradas/{{id}}/cambiarDisponibilidad

/////////////////////////////////////////////////////////
## Definir precio
## Definir tarifa extra para reinicio por pausa extensa

POST http://localhost:8001/administracion/tarifas

{
  "tarifa": 147,
  "porc_recargo": 1.7
}

/////////////////////////////////////////////////////////
## Anular cuenta
## Como administrador quiero poder anular cuentas para inhabilitar el uso momentáneo de la misma.

PUT http://localhost:8001/administracion/cuentas/{{id_cuenta}}?habilitada=opc

opc => true, false

/////////////////////////////////////////////////////////
## Generar reporte de uso de monopatines por kilómetros

GET http://localhost:8001/administracion/reportes/monopatines/kilometros?orden=opc

opc => desc, asc

opc es OPCIONAL (no requerido), en caso de no pasarlo, por defecto devuelve en orden descendente.

/////////////////////////////////////////////////////////
## Generar reporte de uso de monopatines por tiempo con pausas

GET http://localhost:8001/administracion/reportes/monopatines/tiempos/conPausas?orden=opc

opc => desc, asc

opc es OPCIONAL (no requerido), en caso de no pasarlo, por defecto devuelve en orden descendente.

/////////////////////////////////////////////////////////
## Generar reporte de uso de monopatines por tiempo sin pausas

GET http://localhost:8001/administracion/reportes/monopatines/tiempos/sinPausas?orden=opc

opc => desc, asc

opc es OPCIONAL (no requerido), en caso de no pasarlo, por defecto devuelve en orden descendente.

/////////////////////////////////////////////////////////
## Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.

GET http://localhost:8001/administracion/reportes/cantidadViajesMayorA/{{cantidad}}/año/{{anio}}

/////////////////////////////////////////////////////////
## Como administrador quiero consultar el total facturado en un rango de meses de cierto año.

GET http://localhost:8001/administracion/reportes/facturacionViajesDesde/{{mes1}}/hasta/{{mes2}}/año/{{anio}}

/////////////////////////////////////////////////////////
## Como usuario quiero lun listado de los monopatines cercanos a mi zona, para poder encontrar un monopatín cerca de mi ubicación

GET http://localhost:8004/usuarios/monopatinesCercanos/latitud/{{latitud}}/longitud/{{longitud}}/rango/{{rango}}

/////////////////////////////////////////////////////////
## Como encargado de mantenimiento quiero poder generar un reporte de uso de monopatines por kilómetros para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder configurarse para incluir (o no) los tiempos de pausa.

Esta hecho con tres end-point diferentes, esta a media :)
Tienen el inicio de /reportes en administracion
