package com.example.decapay.configurations.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.decapay.exceptions.NotImageUploadException;
import com.example.decapay.models.User;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
@AllArgsConstructor
@Getter

public class CloudinaryConfig
{

    private  final Dotenv dotenv;

    private final Cloudinary cloudinary;


    public CloudinaryConfig()
    {
        dotenv= Dotenv.load();

        cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    }



}
