package edu.kent.babelpages.lib.aws;

import org.springframework.stereotype.Component;

@Component
public class AWSUtil {
    private final AWSProperties awsProperties;

    public AWSUtil(AWSProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    public String buildS3ObjectUrl(String folderPrefix, String userId){
        return "https://"+awsProperties.getBucketName()+".s3."+awsProperties.getRegion()+
                ".amazonaws.com/"+folderPrefix+"/"+userId;
    }
}
