package com.example.decapay.configurations.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.decapay.exceptions.NotImageUploadException;
import com.example.decapay.models.User;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
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

public class CloudinaryConfig
{

    private  final Dotenv dotenv;

    private final Cloudinary cloudinary;

    private final String cloudinaryFolderName ="decapay";

    private MultipartFile imagePathDirectory;

//    private String userAvatar = "image-holder.jpeg";

    private Map setImageParameter(User user){
        return ObjectUtils.asMap
                (

                "use_filename", false,
                "public_id", cloudinaryFolderName +"/"+ user.getUserId(),
                "unique_filename", false,
                "overwrite", true

                );
    }


    public CloudinaryConfig()
    {
       dotenv= Dotenv.load();

       cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    }



    private boolean imageFileCheck()
    {
        if(Objects.requireNonNull(imagePathDirectory.getContentType()).contains("image") &&
                imagePathDirectory.getSize()<=1_000_000)
            return true;

        return false;


    }


    private String convertFileToString(MultipartFile multipartFile) throws IOException
    {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        FileOutputStream fos = new FileOutputStream(convFile);

        fos.write(multipartFile.getBytes());

        fos.close();

        return convFile.getPath();
    }



    private String uploadAndTransformImage(User user, String filePath)
    {
        String imageUrl = new String();
        if(imageFileCheck())
        {
            Map params = setImageParameter(user);
            try
            {
                String secreteKey = "secure_url";

                imageUrl =  cloudinary.uploader().upload(filePath, params).get(secreteKey).toString();

                cloudinary.url().transformation(new Transformation()
                                .crop("pad")
                                .width(300)
                                .height(400)
                                .background("auto:predominant"))
                     .imageTag(user.getUserId());
            }
            catch (Exception e)
            {
            e.getMessage();
             }
        }

        return imageUrl;
    }



    public String defaultImageUpload(User user)
    {
        String imageUrl = new String();

        Map params1 = setImageParameter(user);

        String avatarImagePath = "src/main/resources/cloudinary/image-holder.jpeg";

        return uploadAndTransformImage(user, avatarImagePath);
    }



    public String  createOrUpdateImage(MultipartFile file,User user) throws IOException
    {

        String filePath = convertFileToString(file);

        if(filePath==null || filePath =="")
            return defaultImageUpload(user);

        Map params1 = setImageParameter(user);

        return uploadAndTransformImage(user, filePath);
    }



    public String deleteImage(User user) throws IOException
    {
        Map params1 = setImageParameter(user);

        cloudinary.uploader().destroy(user.getUserId(), params1);

        return "Image Successfully deleted";
    }


}
