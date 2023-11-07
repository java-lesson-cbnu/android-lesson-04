package kr.easw.lesson04.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import kr.easw.lesson04.model.dto.AWSKeyDto;
import kr.easw.lesson04.model.dto.AWSPermissionKeyDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

// 이 클래스를 서비스로 지정합니다.
@Service
public class AWSService {
    // 실습을 위해 무작위 버킷을 생성합니다.
    private static final String BUCKET_NAME = "easw-random-bucket-" + UUID.randomUUID();
    private AmazonS3 s3Client = null;

    public void initAWSAPI(AWSKeyDto awsKey) {
        // AWS API를 초기화합니다.
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsKey.getApiKey(), awsKey.getApiSecretKey())))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
        // 기존에 존재하는 실습용 버킷을 제거합니다.
        for (Bucket bucket : s3Client.listBuckets()) {
            if (bucket.getName().startsWith("easw-random-bucket-")) {
                s3Client.listObjects(bucket.getName())
                        .getObjectSummaries()
                        .forEach(it -> s3Client.deleteObject(bucket.getName(), it.getKey()));
            }
        }
        // 새로운 실습용 버킷을 생성합니다.
        s3Client.createBucket(BUCKET_NAME);
    }

    public boolean isInitialized() {
        return s3Client != null;
    }

    public List<String> getFileList() {
        // 실습용 버킷의 파일 목록을 가져옵니다.
        return s3Client.listObjects(BUCKET_NAME).getObjectSummaries().stream().map(S3ObjectSummary::getKey).toList();
    }

    @SneakyThrows
    public void upload(MultipartFile file) {
        // 실습용 버킷에 파일을 업로드합니다.
        s3Client.putObject(BUCKET_NAME, file.getOriginalFilename(), new ByteArrayInputStream(file.getResource().getContentAsByteArray()), new ObjectMetadata());
    }
}
