package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.logic.FileUploadResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    /*
     * 파일 업로드
     */
    @Transactional
    public FileUploadResultDto uploadFile(MultipartFile file, String directoryPath) throws IOException {
        // 파일명 구성
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String originFileName = makeOriginFileName(directoryPath, file.getOriginalFilename());
        String serverFileName = makeServerFileName(directoryPath, extension);

        // 파일업로드 요청 구성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName) // 버킷명
                .contentType(file.getContentType()) // 확장자
                .contentLength(file.getSize())  // 파일 사이즈
                .key(serverFileName)  // 업로드 파일명
                .build();
        
        // 파일업로드
        RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
        s3Client.putObject(putObjectRequest, requestBody);

        // 업로드된 파일 점근 URL 요청
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(serverFileName)
                .build();
        String url = s3Client.utilities().getUrl(getUrlRequest).toString();

        return new FileUploadResultDto(
                originFileName,
                serverFileName,
                url
        );
    }

    /*
     * 여러 파일 업로드
     */
    public List<FileUploadResultDto> uploadFiles(List<MultipartFile> files, String directoryPath) throws IOException {
        try {
            List<FileUploadResultDto> resultDto = new ArrayList<>();
            for (MultipartFile file : files) {
                resultDto.add(uploadFile(file, directoryPath));
            }
            return resultDto;
        } catch (IOException exception) {
            deleteAllFileInDirectory(directoryPath);
            throw new IOException("여러 파일을 업로드하는 중 예외발생");
        }
    }

    /*
     * 디렉토리의 모든 파일제거
     */
    public void deleteAllFileInDirectory(String directoryAddress) {
        // 디렉토리에 있는 모든 오브젝트 정보 조회
        ListObjectsResponse findReq = s3Client.listObjects(
                ListObjectsRequest
                        .builder()
                        .bucket(bucketName)
                        .prefix(directoryAddress)
                        .build()
        );
        List<S3Object> contents = findReq.contents();

        // 조회된 모든 오브젝트 제거
        ArrayList<ObjectIdentifier> keys = new ArrayList<>();
        for (S3Object content : contents) {
            DeleteObjectRequest deleteReq = DeleteObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(content.key())
                    .build();
            s3Client.deleteObject(deleteReq);
        }
    }

    private String makeOriginFileName(String directoryUrl, String fileName) {
        if(directoryUrl.endsWith("/")) {
            return directoryUrl + fileName;
        } else {
            return directoryUrl + "/" + fileName;
        }
    }

    private String makeServerFileName(String directoryUrl, String extension) {
        if(directoryUrl.endsWith("/")) {
            return directoryUrl + UUID.randomUUID().toString() + "." + extension;
        } else {
            return directoryUrl + "/" + UUID.randomUUID().toString() + "." + extension;
        }
    }
}
