INSERT INTO shipments (reference_number, shipment_date) VALUES
  ('SHIP-1001', '2025-01-15'),
  ('SHIP-2002', '2025-02-01');

INSERT INTO incomes (description, amount, shipment_id) VALUES
  ('CUSTOMER_PAYMENT', 500.00, 1),
  ('AGENT_COMMISSION', 100.00, 1),
  ('CUSTOMER_PAYMENT', 700.00, 2);

INSERT INTO costs (description, amount, shipment_id) VALUES
  ('FUEL', 200.00, 1),
  ('HANDLING', 50.00, 1),
  ('FUEL', 300.00, 2),
  ('TAXES', 100.00, 2);
