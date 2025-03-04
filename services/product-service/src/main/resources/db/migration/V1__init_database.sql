CREATE TABLE IF NOT EXISTS categories
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);


CREATE TABLE IF NOT EXISTS products
(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DOUBLE PRECISION NOT NULL,
  stock INTEGER,
  is_active BOOLEAN DEFAULT TRUE,
  category_id UUID,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS product_images
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    image_url TEXT NOT NULL,
    product_id UUID NOT NULL,
    CONSTRAINT fk_product_image FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Tạo bảng variants
CREATE TABLE IF NOT EXISTS variants
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stock INTEGER,
    price DOUBLE PRECISION,
    product_id UUID NOT NULL,
    CONSTRAINT fk_variant_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Tạo bảng product_attributes
CREATE TABLE IF NOT EXISTS product_attributes
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL,
    value TEXT NOT NULL,
    variant_id UUID NOT NULL,
    CONSTRAINT fk_attribute_variant FOREIGN KEY (variant_id) REFERENCES variants(id) ON DELETE CASCADE
);

-- Tạo enum type cho attribute_type (nếu cần, PostgreSQL hỗ trợ ENUM)
DO $$ BEGIN
    CREATE TYPE attribute_type AS ENUM ('COLOR', 'SIZE', 'MATERIAL', 'STORAGE', 'RAM', 'WEIGHT');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;