package com.example.decapay.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.decapay.configurations.cloudinary.CloudinaryConfig;
import com.example.decapay.exceptions.NotImageUploadException;
import com.example.decapay.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Data
@Setter

public class CloudinaryUtils {

    private final CloudinaryConfig config = new CloudinaryConfig();

    private final Cloudinary cloudinary = config.getCloudinary();

    private MultipartFile imagePathDirectory;


    private Map setImageParameter(User user) {
        return ObjectUtils.asMap
                (

                        "use_filename", false,
                        "public_id", config.getCloudinaryFolderName() + "/" + user.getUserId(),
                        "unique_filename", false,
                        "overwrite", true

                );
    }


    private boolean imageFileCheck() {
        if (Objects.requireNonNull(imagePathDirectory.getContentType()).contains("image") &&
                imagePathDirectory.getSize() <= 1_000_000)
            return true;

        throw new NotImageUploadException("An image type file expected or file size is too large");

    }


    private String convertFileToString(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        FileOutputStream fos = new FileOutputStream(convFile);

        fos.write(multipartFile.getBytes());

        fos.close();

        return convFile.getPath();
    }


    private String uploadAndTransformImage(User user, String filePath) {
        String imageUrl = new String();
        if (imageFileCheck()) {
            Map params = setImageParameter(user);
            try {

                imageUrl = cloudinary.uploader().upload(filePath, params).get(config.getSecreteKey()).toString();

                cloudinary.url().transformation(new Transformation()
                                .crop("pad")
                                .width(300)
                                .height(400)
                                .background("auto:predominant"))
                        .imageTag(user.getUserId());
            } catch (Exception e) {
                e.getMessage();
            }
        }

        return imageUrl;
    }


    public String defaultImageUpload(User user) {
        String imageUrl = new String();

        Map params1 = setImageParameter(user);


        return uploadAndTransformImage(user,config.getAvatarImagePath());
    }


    public String createOrUpdateImage(MultipartFile file, User user) throws IOException {

        String filePath = convertFileToString(file);

        if (filePath.equals(""))
            return defaultImageUpload(user);

        Map params1 = setImageParameter(user);

        return uploadAndTransformImage(user, filePath);
    }


    public String deleteImage(User user) throws IOException {
        Map params1 = setImageParameter(user);

        cloudinary.uploader().destroy(user.getUserId(), params1);

        return "Image Successfully deleted";
    }
}