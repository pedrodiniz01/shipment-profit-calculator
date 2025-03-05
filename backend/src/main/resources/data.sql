INSERT INTO shipments (reference_number, shipment_date, net_amount, is_profit) VALUES
  ('SHIP-1001', '2025-01-15', 350.00, true),
  ('SHIP-2002', '2025-02-01', -500.00, false),
  ('SHIP-3003', '2025-03-01', 300.00, false),
  ('SHIP-4004', '2025-03-02', 700.00, false);

INSERT INTO incomes (description, amount, shipment_id) VALUES
  ('CUSTOMER_PAYMENT', 500.00, 1),
  ('AGENT_COMMISSION', 100.00, 1),
  ('CUSTOMER_PAYMENT', 700.00, 2),
  ('AGENT_COMMISSION', 100.00, 3),
  ('CUSTOMER_PAYMENT', 400.00, 3),
  ('CUSTOMER_PAYMENT', 800.00, 4),
  ('AGENT_COMMISSION', 400.00, 4);

INSERT INTO costs (description, amount, shipment_id) VALUES
  ('FUEL', 200.00, 1),
  ('HANDLING', 50.00, 1),
  ('FUEL', 300.00, 2),
  ('TAXES', 900.00, 2),
   ('TAXES', 200.00, 3),
  ('TAXES', 500.00, 4);
