package com.example.decapay.configurations.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Getter

public class CloudinaryConfig
{
    private final Cloudinary cloudinary;

    @Value("${CLOUDINARY_URL}")
    private  String cloudinaryUrl;

    @Value("${FOLDER_NAME}")
    private  String cloudinaryFolderName;

    @Value("${DEFAULT_AVATAR}")
    private  String avatarImagePath;

    @Value("${SECRETE_KEY}")
    private  String secreteKey;
    public CloudinaryConfig()
    {
        cloudinary = new Cloudinary(cloudinaryUrl);
    }



}
