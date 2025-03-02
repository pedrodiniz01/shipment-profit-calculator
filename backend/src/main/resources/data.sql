INSERT INTO shipments (reference_number, shipment_date, net_amount, is_profit) VALUES
  ('SHIP-1001', '2025-01-15', 350.00, true),
  ('SHIP-2002', '2025-02-01', -500.00, false);

INSERT INTO incomes (description, amount, shipment_id) VALUES
  ('CUSTOMER_PAYMENT', 500.00, 1),
  ('AGENT_COMMISSION', 100.00, 1),
  ('CUSTOMER_PAYMENT', 700.00, 2);

INSERT INTO costs (description, amount, shipment_id) VALUES
  ('FUEL', 200.00, 1),
  ('HANDLING', 50.00, 1),
  ('FUEL', 300.00, 2),
  ('TAXES', 900.00, 2);
