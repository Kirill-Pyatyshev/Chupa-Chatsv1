package com.chupachats.repositories;

import com.chupachats.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByUserId(Long userid);
}
