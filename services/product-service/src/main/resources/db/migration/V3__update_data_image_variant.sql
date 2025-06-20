UPDATE variants v
SET image_url = CASE
                    WHEN p.name = 'Áo thun unisex basic' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207233/download_lnflqx.jpg'
                    WHEN p.name = 'Áo thun polo nam' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207323/download_hydonh.jpg'
                    WHEN p.name = 'Quần jeans slim fit nam' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207406/download_ckcj1e.jpg'
                    WHEN p.name = 'Quần jeans nữ ống loe' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207497/download_fcg5bu.jpg'
                    WHEN p.name = 'Áo sơ mi trắng công sở' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207557/mol450__4__e8e939a198914273a153ae2032e57cbc_master_hla0aq.webp'
                    WHEN p.name = 'Áo sơ mi nữ caro' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207632/download_qmlwxe.jpg'
                    WHEN p.name = 'Váy maxi bohemian' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207766/download_rhygzx.jpg'
                    WHEN p.name = 'Váy body công sở' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207837/download_ns6zcq.jpg'
                    WHEN p.name = 'Mũ lưỡi trai thời trang' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207917/vn-11134207-7r98o-lu1iu0xdcx0fa3_syacfm.jpg'
                    WHEN p.name = 'Túi xách da nữ' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749207980/download_th7mzv.jpg'
                    WHEN p.name = 'Giày sneaker trắng' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208035/download_fljbib.jpg'
                    WHEN p.name = 'Giày cao gót nữ' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208111/download_j6qhof.jpg'
                    WHEN p.name = 'Áo khoác bomber' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208167/download_rugkyf.jpg'
                    WHEN p.name = 'Áo khoác parka mùa đông' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208232/download_oqe61c.jpg'
                    WHEN p.name = 'Quần short kaki nam' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208304/download_jfmsul.jpg'
                    WHEN p.name = 'Quần short nữ denim' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208405/download_x22i5k.jpg'
                    WHEN p.name = 'Áo tập gym nam' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208459/download_tb2e1d.jpg'
                    WHEN p.name = 'Quần legging nữ' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208511/download_d4r7ox.jpg'
                    WHEN p.name = 'Áo lót nam cotton' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208561/download_lsl1sx.jpg'
                    WHEN p.name = 'Đồ lót nữ ren' THEN 'https://res.cloudinary.com/dmno1bjyt/image/upload/v1749208613/download_o1j53r.jpg'
                    ELSE NULL
    END
    FROM products p
WHERE v.product_id = p.id;