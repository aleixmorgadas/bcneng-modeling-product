CREATE OR REPLACE VIEW product_view AS
    SELECT p.id, p.name, c.name as category FROM product p, category c WHERE p.category_id = c.id;