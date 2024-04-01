package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.logic.FileUploadResultDto;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {
    private final S3Template s3Template; // s3

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public FileUploadResultDto uploadFile(MultipartFile file) throws IOException {
        String originFileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originFileName);
        String serverFileName = UUID.randomUUID().toString() + "." + extension;

        S3Resource s3Resource = s3Template.upload(
                bucketName,
                serverFileName,
                file.getInputStream(),
                ObjectMetadata.builder().contentType(extension).build()
        );
        return new FileUploadResultDto(originFileName, serverFileName, s3Resource.getURL().toString());
    }
}
