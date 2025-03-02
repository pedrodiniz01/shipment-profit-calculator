INSERT INTO shipments (reference_number, shipment_date) VALUES
  ('SHIP-1001', '2025-01-15'),
  ('SHIP-2002', '2025-02-01');

INSERT INTO incomes (description, amount, shipment_id) VALUES
  ('Customer Payment', 500.00, 1),
  ('Additional Agent Fee', 100.00, 1),
  ('Customer Payment', 700.00, 2);

INSERT INTO costs (description, amount, shipment_id) VALUES
  ('Fuel', 200.00, 1),
  ('Handling', 50.00, 1),
  ('Fuel', 300.00, 2),
  ('Taxes', 100.00, 2);
