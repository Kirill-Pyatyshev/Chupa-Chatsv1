package com.chupachats.services;

import com.chupachats.exception.ImageNullFoundException;
import com.chupachats.models.Image;
import com.chupachats.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image getImageById(Long id) throws ImageNullFoundException {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNullFoundException("Фотографиия с ID:" + id + " не найдена!"));
        return image;
    }

}
