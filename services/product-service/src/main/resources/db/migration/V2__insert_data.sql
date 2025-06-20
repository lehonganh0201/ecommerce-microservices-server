-- Thêm danh mục sản phẩm (quần áo và phụ kiện thời trang)
INSERT INTO categories (id, name, description, image_url, created_at, updated_at)
VALUES
(gen_random_uuid(), 'Áo thun', 'Áo thun nam và nữ, phong cách casual', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749045987/6-1544032750_dkxp2h.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Quần jeans', 'Quần jeans thời trang cho nam và nữ', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046070/4054_4_7de9b1d90a764bc5bd11b854fd3eb6a1_hss5fd.webp', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Áo sơ mi', 'Áo sơ mi công sở và casual', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046126/images_hinf9f.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Váy', 'Váy thời trang nữ, đa dạng kiểu dáng', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046225/pngtree-original-hand-drawn-dignified-red-evening-dress-dress-clothing-cartoon-elements-png-image_2626171_aqnwoc.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Phụ kiện thời trang', 'Mũ, khăn, thắt lưng, túi xách và phụ kiện khác', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046286/images_imy7kn.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Giày', 'Giày thời trang nam và nữ', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046353/pngtree-casual-shoes-png-image_2394294_bpylyn.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Áo khoác', 'Áo khoác mùa đông và thời trang', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046427/ao-khoac-nam-kaki-png-4-new_ldmlbk.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Quần short', 'Quần short năng động cho nam và nữ', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046463/images_the5mw.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Đồ thể thao', 'Trang phục thể thao, gym và yoga', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046501/download_m7txep.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Đồ lót', 'Đồ lót nam và nữ, thoải mái và thời trang', 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046505/images_b7oavm.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Thêm sản phẩm (quần áo và phụ kiện)
INSERT INTO products (id, name, description, price, category_id, creator_name, created_by, created_date, last_modified_date, is_active)
VALUES
(gen_random_uuid(), 'Áo thun unisex basic', 'Áo thun cotton thoáng mát, phù hợp cả nam và nữ', 19.99, (SELECT id FROM categories WHERE name = 'Áo thun'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo thun polo nam', 'Áo polo cổ bẻ, phong cách lịch lãm', 29.99, (SELECT id FROM categories WHERE name = 'Áo thun'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Quần jeans slim fit nam', 'Quần jeans nam phong cách slim fit, thời trang', 49.99, (SELECT id FROM categories WHERE name = 'Quần jeans'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Quần jeans nữ ống loe', 'Quần jeans nữ ống loe, phong cách vintage', 54.99, (SELECT id FROM categories WHERE name = 'Quần jeans'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo sơ mi trắng công sở', 'Áo sơ mi trắng nam, thanh lịch và chuyên nghiệp', 39.99, (SELECT id FROM categories WHERE name = 'Áo sơ mi'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo sơ mi nữ caro', 'Áo sơ mi nữ họa tiết caro, trẻ trung', 34.99, (SELECT id FROM categories WHERE name = 'Áo sơ mi'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Váy maxi bohemian', 'Váy maxi dài phong cách bohemian cho nữ', 59.99, (SELECT id FROM categories WHERE name = 'Váy'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Váy body công sở', 'Váy ôm sát phong cách công sở, sang trọng', 69.99, (SELECT id FROM categories WHERE name = 'Váy'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Mũ lưỡi trai thời trang', 'Mũ lưỡi trai unisex, phong cách đường phố', 15.99, (SELECT id FROM categories WHERE name = 'Phụ kiện thời trang'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Túi xách da nữ', 'Túi xách da cao cấp, phù hợp công sở', 89.99, (SELECT id FROM categories WHERE name = 'Phụ kiện thời trang'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Giày sneaker trắng', 'Giày sneaker thời trang, phù hợp mọi dịp', 69.99, (SELECT id FROM categories WHERE name = 'Giày'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Giày cao gót nữ', 'Giày cao gót 7cm, phong cách thanh lịch', 79.99, (SELECT id FROM categories WHERE name = 'Giày'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo khoác bomber', 'Áo khoác bomber unisex, phong cách năng động', 79.99, (SELECT id FROM categories WHERE name = 'Áo khoác'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo khoác parka mùa đông', 'Áo khoác parka giữ ấm, phù hợp mùa đông', 99.99, (SELECT id FROM categories WHERE name = 'Áo khoác'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Quần short kaki nam', 'Quần short kaki nam, thoải mái và năng động', 29.99, (SELECT id FROM categories WHERE name = 'Quần short'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Quần short nữ denim', 'Quần short denim nữ, phong cách trẻ trung', 34.99, (SELECT id FROM categories WHERE name = 'Quần short'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo tập gym nam', 'Áo thun thể thao nam, thấm hút mồ hôi', 24.99, (SELECT id FROM categories WHERE name = 'Đồ thể thao'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Quần legging nữ', 'Quần legging yoga, co giãn và thoải mái', 39.99, (SELECT id FROM categories WHERE name = 'Đồ thể thao'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Áo lót nam cotton', 'Áo lót nam cotton, thoải mái cả ngày', 12.99, (SELECT id FROM categories WHERE name = 'Đồ lót'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(gen_random_uuid(), 'Đồ lót nữ ren', 'Đồ lót nữ ren cao cấp, phong cách gợi cảm', 19.99, (SELECT id FROM categories WHERE name = 'Đồ lót'), 'Anh Le', '86bddf60-eae1-4910-8e9c-1e74df2b5916', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE);

-- Thêm ảnh sản phẩm
INSERT INTO product_images (id, image_url, product_id)
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046595/download_mlmyrg.jpg', id FROM products WHERE name = 'Áo thun unisex basic'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046599/download_wggq3x.jpg', id FROM products WHERE name = 'Áo thun polo nam'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046651/download_ceymwt.jpg', id FROM products WHERE name = 'Quần jeans slim fit nam'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046692/download_h5gfgm.jpg', id FROM products WHERE name = 'Quần jeans nữ ống loe'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046706/so-mi-trang-co-tui_bum2na.png', id FROM products WHERE name = 'Áo sơ mi trắng công sở'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046710/download_r4vlcs.jpg', id FROM products WHERE name = 'Áo sơ mi nữ caro'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046812/pngtree-bohemian-style-maxi-skirt-with-accessories-png-image_21011188_cnaztr.png', id FROM products WHERE name = 'Váy maxi bohemian'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046824/dam-om-body-cong-so-phoi-that-lung-kk162-21_r4zg1m.webp', id FROM products WHERE name = 'Váy body công sở'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046844/pngtree-brown-cap-fashion-hat-front-view-png-image_13321509_hhl2xf.png', id FROM products WHERE name = 'Mũ lưỡi trai thời trang'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046847/download_kohxca.jpg', id FROM products WHERE name = 'Túi xách da nữ'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046885/download_aodxmo.jpg', id FROM products WHERE name = 'Giày sneaker trắng'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046868/download_vmx405.jpg', id FROM products WHERE name = 'Giày cao gót nữ'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046898/download_wcybhn.jpg', id FROM products WHERE name = 'Áo khoác bomber'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046892/download_qmcz2r.jpg', id FROM products WHERE name = 'Áo khoác parka mùa đông'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749046967/images_mxyfoi.jpg', id FROM products WHERE name = 'Quần short kaki nam'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749047010/quan-shorts-mlb-denim-new-york-yankees-3fdph0133-50sbl-mau-xanh-nhat-64116dd1de5ad-15032023140345-1680518622148_s8rwqj.jpg', id FROM products WHERE name = 'Quần short nữ denim'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749047020/download_zqgguv.jpg', id FROM products WHERE name = 'Áo tập gym nam'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749047052/download_huqoyf.jpg', id FROM products WHERE name = 'Quần legging nữ'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749047048/download_p8jqsg.jpg', id FROM products WHERE name = 'Áo lót nam cotton'
UNION ALL
SELECT gen_random_uuid(), 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749047059/download_ang2z8.jpg', id FROM products WHERE name = 'Đồ lót nữ ren';

-- Thêm biến thể sản phẩm (variants)
INSERT INTO variants (id, stock, price, product_id)
SELECT gen_random_uuid(), 50, 19.99, id FROM products WHERE name = 'Áo thun unisex basic'
UNION ALL
SELECT gen_random_uuid(), 40, 29.99, id FROM products WHERE name = 'Áo thun polo nam'
UNION ALL
SELECT gen_random_uuid(), 30, 49.99, id FROM products WHERE name = 'Quần jeans slim fit nam'
UNION ALL
SELECT gen_random_uuid(), 25, 54.99, id FROM products WHERE name = 'Quần jeans nữ ống loe'
UNION ALL
SELECT gen_random_uuid(), 35, 39.99, id FROM products WHERE name = 'Áo sơ mi trắng công sở'
UNION ALL
SELECT gen_random_uuid(), 30, 34.99, id FROM products WHERE name = 'Áo sơ mi nữ caro'
UNION ALL
SELECT gen_random_uuid(), 20, 59.99, id FROM products WHERE name = 'Váy maxi bohemian'
UNION ALL
SELECT gen_random_uuid(), 15, 69.99, id FROM products WHERE name = 'Váy body công sở'
UNION ALL
SELECT gen_random_uuid(), 60, 15.99, id FROM products WHERE name = 'Mũ lưỡi trai thời trang'
UNION ALL
SELECT gen_random_uuid(), 10, 89.99, id FROM products WHERE name = 'Túi xách da nữ'
UNION ALL
SELECT gen_random_uuid(), 25, 69.99, id FROM products WHERE name = 'Giày sneaker trắng'
UNION ALL
SELECT gen_random_uuid(), 20, 79.99, id FROM products WHERE name = 'Giày cao gót nữ'
UNION ALL
SELECT gen_random_uuid(), 15, 79.99, id FROM products WHERE name = 'Áo khoác bomber'
UNION ALL
SELECT gen_random_uuid(), 10, 99.99, id FROM products WHERE name = 'Áo khoác parka mùa đông'
UNION ALL
SELECT gen_random_uuid(), 40, 29.99, id FROM products WHERE name = 'Quần short kaki nam'
UNION ALL
SELECT gen_random_uuid(), 35, 34.99, id FROM products WHERE name = 'Quần short nữ denim'
UNION ALL
SELECT gen_random_uuid(), 50, 24.99, id FROM products WHERE name = 'Áo tập gym nam'
UNION ALL
SELECT gen_random_uuid(), 45, 39.99, id FROM products WHERE name = 'Quần legging nữ'
UNION ALL
SELECT gen_random_uuid(), 60, 12.99, id FROM products WHERE name = 'Áo lót nam cotton'
UNION ALL
SELECT gen_random_uuid(), 50, 19.99, id FROM products WHERE name = 'Đồ lót nữ ren';

-- Thêm thuộc tính sản phẩm (product_attributes)
INSERT INTO product_attributes (id, type, value, variant_id)
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo thun unisex basic')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'M', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo thun unisex basic')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Navy', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo thun polo nam')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'L', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo thun polo nam')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Blue', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần jeans slim fit nam')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', '32', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần jeans slim fit nam')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Dark Blue', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần jeans nữ ống loe')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'S', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần jeans nữ ống loe')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'White', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo sơ mi trắng công sở')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'L', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo sơ mi trắng công sở')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Red', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo sơ mi nữ caro')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'M', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo sơ mi nữ caro')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Floral', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Váy maxi bohemian')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'L', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Váy maxi bohemian')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Váy body công sở')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'S', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Váy body công sở')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Mũ lưỡi trai thời trang')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Brown', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Túi xách da nữ')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'White', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Giày sneaker trắng')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', '39', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Giày sneaker trắng')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Red', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Giày cao gót nữ')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', '38', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Giày cao gót nữ')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Green', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo khoác bomber')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'M', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo khoác bomber')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo khoác parka mùa đông')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'L', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo khoác parka mùa đông')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Beige', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần short kaki nam')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'M', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần short kaki nam')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Blue', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần short nữ denim')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'S', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần short nữ denim')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo tập gym nam')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'L', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo tập gym nam')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Purple', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần legging nữ')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'M', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Quần legging nữ')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'White', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo lót nam cotton')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'M', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Áo lót nam cotton')
UNION ALL
SELECT gen_random_uuid(), 'COLOR', 'Black', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Đồ lót nữ ren')
UNION ALL
SELECT gen_random_uuid(), 'SIZE', 'S', id FROM variants WHERE product_id = (SELECT id FROM products WHERE name = 'Đồ lót nữ ren');