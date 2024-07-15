package edu.kent.babelpages.rest.profiles;

import edu.kent.babelpages.lib.aws.AWSProperties;
import edu.kent.babelpages.rest.profiles.DTO.ProfileUpdateDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import edu.kent.babelpages.rest.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
public class ProfilesService {
    private final ProfilesDAO profilesDAO;
    private final S3Client s3Client;
    private final AWSProperties awsProperties;

    public ProfilesService(ProfilesDAO profilesDAO, S3Client s3Client, AWSProperties awsProperties) {
        this.profilesDAO = profilesDAO;
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;
    }

    public Profile getProfileFromUserId(UUID id) {
        return profilesDAO.findByUserId(id.toString());
    }

    /**
     * create a blank profile for user on user creation.
     * fields can be filled in later on another endpoint.
     */
    public Profile createForUser(User u){
        return profilesDAO.save(new Profile(null, u.getId(), null, null, null, null));
    }

    /**
     * updates profile for currently authenticated user.
     * NOTE: aws will automatically replace image with same key, since our key here is prefix + user id, profile picture
     * changes get handled automatically
     */
    public void updateProfile(ProfileUpdateDTO profileUpdateDTO, MultipartFile file){
        var userDetails = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(file != null){
            // todo: add verification that file height and width is NxN where N is number of pixels
            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(awsProperties.getBucketName())
                    .key(awsProperties.getProfilePicturesPrefix() + userDetails.getId().toString())
                    .contentType(file.getContentType())
                    .build();

            try {
                 s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
