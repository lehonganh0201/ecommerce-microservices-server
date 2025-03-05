ALTER TABLE variants
ADD COLUMN image_id UUID,
ADD CONSTRAINT fk_variant_image FOREIGN KEY (image_id) REFERENCES product_images(id) ON DELETE SET NULL;
