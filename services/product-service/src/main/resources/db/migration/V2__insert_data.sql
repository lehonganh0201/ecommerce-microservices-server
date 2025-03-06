-- Thêm danh mục sản phẩm
INSERT INTO categories (id, name, description) VALUES
(gen_random_uuid(), 'Điện thoại', 'Các loại điện thoại thông minh'),
(gen_random_uuid(), 'Laptop', 'Máy tính xách tay và phụ kiện'),
(gen_random_uuid(), 'Phụ kiện', 'Các loại phụ kiện điện tử'),
(gen_random_uuid(), 'Tablet', 'Máy tính bảng các hãng'),
(gen_random_uuid(), 'Smartwatch', 'Đồng hồ thông minh'),
(gen_random_uuid(), 'Âm thanh', 'Loa, tai nghe và thiết bị âm thanh');

-- Thêm sản phẩm
INSERT INTO products (id, name, description, price, stock, category_id,creator_name, created_by)
VALUES
(gen_random_uuid(), 'iPhone 14 Pro', 'Điện thoại Apple cao cấp', 1099.99, 50, (SELECT id FROM categories WHERE name = 'Điện thoại'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916'),
(gen_random_uuid(), 'Samsung Galaxy S23 Ultra', 'Điện thoại Samsung flagship', 1199.99, 40, (SELECT id FROM categories WHERE name = 'Điện thoại'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916'),
(gen_random_uuid(), 'MacBook Pro 16"', 'Laptop mạnh mẽ từ Apple', 2399.99, 30, (SELECT id FROM categories WHERE name = 'Laptop'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916'),
(gen_random_uuid(), 'Dell XPS 15', 'Laptop Dell hiệu năng cao', 1899.99, 20, (SELECT id FROM categories WHERE name = 'Laptop'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916'),
(gen_random_uuid(), 'iPad Pro 12.9"', 'Máy tính bảng mạnh mẽ từ Apple', 1299.99, 25, (SELECT id FROM categories WHERE name = 'Tablet'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916'),
(gen_random_uuid(), 'Apple Watch Ultra', 'Đồng hồ thông minh cao cấp từ Apple', 799.99, 15, (SELECT id FROM categories WHERE name = 'Smartwatch'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916'),
(gen_random_uuid(), 'Sony WH-1000XM5', 'Tai nghe chống ồn hàng đầu từ Sony', 399.99, 35, (SELECT id FROM categories WHERE name = 'Âm thanh'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916');

-- Thêm ảnh sản phẩm
INSERT INTO product_images (id, image_url, product_id)
SELECT gen_random_uuid(), '\resource\images\product-images\G03.jpg', id FROM products WHERE name = 'iPhone 14 Pro'
UNION ALL
SELECT gen_random_uuid(), '\resource\images\product-images\REGEN-GalaxyS23Ultra-Lavender-2.webp', id FROM products WHERE name = 'Samsung Galaxy S23 Ultra'
UNION ALL
SELECT gen_random_uuid(), '\resource\images\product-images\macbook-16.jpg', id FROM products WHERE name = 'MacBook Pro 16"'
UNION ALL
SELECT gen_random_uuid(), '\resource\images\product-images\Dell-xps-9530-3.png', id FROM products WHERE name = 'Dell XPS 15';

-- Thêm biến thể sản phẩm (variants)
INSERT INTO variants (id, stock, price, product_id)
SELECT gen_random_uuid(), 20, 1099.99, id FROM products WHERE name = 'iPhone 14 Pro'
UNION ALL
SELECT gen_random_uuid(), 15, 1199.99, id FROM products WHERE name = 'Samsung Galaxy S23 Ultra'
UNION ALL
SELECT gen_random_uuid(), 10, 2399.99, id FROM products WHERE name = 'MacBook Pro 16"'
UNION ALL
SELECT gen_random_uuid(), 5, 1899.99, id FROM products WHERE name = 'Dell XPS 15';

-- Thêm thuộc tính sản phẩm (product_attributes)
INSERT INTO product_attributes (id, type, value, variant_id)
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'iPhone 14 Pro')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'White', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Samsung Galaxy S23 Ultra')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', '16-inch', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'MacBook Pro 16"')
UNION ALL
SELECT gen_random_uuid(), 'RAM', '32GB', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Dell XPS 15');
