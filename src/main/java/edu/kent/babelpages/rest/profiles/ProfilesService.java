package edu.kent.babelpages.rest.profiles;

import edu.kent.babelpages.lib.aws.AWSProperties;
import edu.kent.babelpages.lib.aws.AWSUtil;
import edu.kent.babelpages.rest.profiles.DTO.ProfileUpdateDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import edu.kent.babelpages.rest.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class ProfilesService {
    private final ProfilesDAO profilesDAO;
    private final S3Client s3Client;
    private final AWSProperties awsProperties;
    private final AWSUtil awsUtil;

    public ProfilesService(ProfilesDAO profilesDAO, S3Client s3Client, AWSProperties awsProperties, AWSUtil awsUtil) {
        this.profilesDAO = profilesDAO;
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;
        this.awsUtil = awsUtil;
    }

    public Profile getProfileFromUserId(String id) {
        return profilesDAO.findByUserId(id);
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
                    .key(awsProperties.getProfilePicturesPrefix() + "/" + userDetails.getId().toString())
                    .contentType(file.getContentType())
                    .build();

            try {
                 s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Profile oldProfile = profilesDAO.findByUserId(userDetails.getId().toString());

        profilesDAO.update(new Profile(
                oldProfile.getId(),
                userDetails.getId(),
                file != null ? awsUtil.buildS3ObjectUrl(awsProperties.getProfilePicturesPrefix(), userDetails.getId().toString()) : oldProfile.getProfilePictureUrl(),
                profileUpdateDTO.getCountry() == null ? oldProfile.getCountry() : profileUpdateDTO.getCountry(),
                profileUpdateDTO.getBio() == null ? oldProfile.getBio() : profileUpdateDTO.getBio(),
                profileUpdateDTO.getOccupation() == null ? oldProfile.getOccupation() : profileUpdateDTO.getOccupation()
        ));

    }
}
