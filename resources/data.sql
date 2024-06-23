-- Verifica si existe algún registro y luego inserta si no existe
INSERT INTO unidad (nombre)
SELECT 'Universidad Mayor de San Andres'
WHERE NOT EXISTS (
    SELECT 1 FROM unidad WHERE idunidad = 1
);
