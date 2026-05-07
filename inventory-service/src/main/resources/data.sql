
INSERT INTO product (sku, name, category, current_stock, min_stock, unit_price)
VALUES ('ELEC-001', 'Monitor 24" LED', 'Electrónicos', 25, 10, 189.99),
       ('ELEC-002', 'Teclado mecánico RGB', 'Electrónicos', 8, 15, 79.95),
       ('ELEC-003', 'Mouse inalámbrico', 'Electrónicos', 0, 5, 24.50),
       ('ELEC-004', 'Cable HDMI 2m', 'Electrónicos', 40, 20, 12.99),
       ('ELEC-005', 'Base para laptop ajustable', 'Electrónicos', 3, 10, 45.00),
       ('ALIM-001', 'Café molido 500g', 'Alimentos', 30, 15, 8.75),
       ('ALIM-002', 'Leche entera 1L', 'Alimentos', 5, 10, 1.20),
       ('ALIM-003', 'Galletas integrales 200g', 'Alimentos', 0, 8, 2.99),
       ('ALIM-004', 'Aceite de oliva 750ml', 'Alimentos', 12, 6, 9.49),
       ('ROPA-001', 'Camiseta algodón unisex', 'Ropa', 50, 15, 14.99),
       ('ROPA-002', 'Jeans slim fit', 'Ropa', 10, 12, 39.99),
       ('ROPA-003', 'Chaqueta impermeable', 'Ropa', 0, 5, 89.95),
       ('HOG-001', 'Juego de sábanas matrim.', 'Hogar', 20, 10, 34.50),
       ('HOG-002', 'Toalla de baño 100% algodón', 'Hogar', 4, 8, 19.95),
       ('HOG-003', 'Vaso de cristal 6 uds.', 'Hogar', 15, 10, 22.00);

INSERT INTO movement (product_id, type, quantity, reason, timestamp)
VALUES (1, 'IN', 30, 'Recepción inicial', TIMESTAMPADD('DAY', -5, CURRENT_TIMESTAMP)),
       (1, 'OUT', 5, 'Venta a cliente X', TIMESTAMPADD('DAY', -3, CURRENT_TIMESTAMP)),
       (2, 'IN', 20, 'Carga de proveedor', TIMESTAMPADD('DAY', -10, CURRENT_TIMESTAMP)),
       (2, 'OUT', 12, 'Venta mayorista', TIMESTAMPADD('DAY', -7, CURRENT_TIMESTAMP)),
       (3, 'IN', 10, 'Compra urgente', TIMESTAMPADD('DAY', -6, CURRENT_TIMESTAMP)),
       (3, 'OUT', 10, 'Venta última unidad', TIMESTAMPADD('DAY', -1, CURRENT_TIMESTAMP)),
       (5, 'IN', 8, 'Entrada almacén', TIMESTAMPADD('DAY', -4, CURRENT_TIMESTAMP)),
       (5, 'OUT', 5, 'Pedido online', TIMESTAMPADD('DAY', -2, CURRENT_TIMESTAMP));

-- alertas iniciales (productos con stock <= minStock)
-- productId 2: Teclado, currentStock=8, minStock=15 -> CRITICAL
-- productId 3: Mouse, currentStock=0, minStock=5 -> CRITICAL
-- productId 5: Base laptop, currentStock=3, minStock=10 -> CRITICAL
-- productId 7: Leche, currentStock=10, minStock=10 -> LOW
-- productId 8: Galletas, currentStock=8, minStock=8 -> LOW
-- productId 11: Jeans, currentStock=12, minStock=12 -> LOW
-- productId 12: Chaqueta, currentStock=0, minStock=5 -> CRITICAL
-- productId 14: Toalla, currentStock=8, minStock=8 -> LOW
INSERT INTO stock_alert (product_id, product_name, current_stock, min_stock, severity)
VALUES (2, 'Teclado mecánico RGB', 8, 15, 'CRITICAL'),
       (3, 'Mouse inalámbrico', 0, 5, 'CRITICAL'),
       (5, 'Base para laptop ajustable', 3, 10, 'CRITICAL'),
       (7, 'Leche entera 1L', 10, 10, 'LOW'),
       (8, 'Galletas integrales 200g', 8, 8, 'LOW'),
       (11, 'Jeans slim fit', 12, 12, 'LOW'),
       (12, 'Chaqueta impermeable', 0, 5, 'CRITICAL'),
       (14, 'Toalla de baño 100% algodón', 8, 8, 'LOW');