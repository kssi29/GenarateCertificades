INSERT INTO unidad (nombre)
SELECT 'Universidad Mayor de San Andres'
    WHERE NOT EXISTS (
    SELECT 1 FROM unidad WHERE idunidad = 1
);