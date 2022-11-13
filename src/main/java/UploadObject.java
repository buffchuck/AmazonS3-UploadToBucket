import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class UploadObject {

    public static void main(String[] args) {

        String clientRegion = "us-east-1";
        String bucketName = "com-rob-epps-arnold-test-bucket-3";
        String stringObjectKeyName = "robert-key-string";
        String fileObjectKeyName = "robert-key-file";
        String fileName = "/Users/rarnold/Documents/tehcodez/AmazonS3/AmazonS3-UploadToBucket/SampleUpload.txt";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();
            // Upload a text string as a new object.

            s3Client.putObject(bucketName, stringObjectKeyName, "Some hardcoded string text that could probably easily be a variable but we are going to roll with this.");

            // Upload a file as a new object with ContentType and title specified.

            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjectKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "Chuck\'s Object");
            request.setMetadata(metadata);
            s3Client.putObject(request);



        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.

            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.

            e.printStackTrace();
        }
    }

}
