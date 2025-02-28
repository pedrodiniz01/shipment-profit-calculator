INSERT INTO shipments (reference_number, shipment_date) VALUES
  ('SHIP-1001', '2025-01-15'),
  ('SHIP-2002', '2025-02-01');

INSERT INTO incomes (id, description, amount, shipment_id) VALUES
  (1, 'Customer Payment', 500.00, 1),
  (2, 'Additional Agent Fee', 100.00, 1),
  (3, 'Customer Payment', 700.00, 2);

INSERT INTO costs (id, description, amount, shipment_id) VALUES
  (1, 'Fuel', 200.00, 1),
  (2, 'Handling', 50.00, 1),
  (3, 'Fuel', 300.00, 2),
  (4, 'Taxes', 100.00, 2);
