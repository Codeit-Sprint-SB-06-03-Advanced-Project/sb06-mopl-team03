package org.codeit.sb06.team03.mopl.user.domain.exception;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageRegistrationFailedException extends UserException {
    
    public ImageRegistrationFailedException(MultipartFile image, IOException e) {
        super("Failed to register image: " + image.getOriginalFilename(), e);
    }
}
