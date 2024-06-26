package ru.netology.diplomcloud.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplomcloud.dto.request.EditFileNameRequestDto;
import ru.netology.diplomcloud.dto.response.FileListResponseDto;
import ru.netology.diplomcloud.dto.response.ResponseMessageDto;
import ru.netology.diplomcloud.facade.CloudFacade;

import java.io.IOException;
import java.util.List;

import static ru.netology.diplomcloud.util.AppConstant.AUTH_TOKEN;
import static ru.netology.diplomcloud.util.AppConstant.FILENAME;
import static ru.netology.diplomcloud.util.AppConstant.LIMIT;

@RestController
@RequiredArgsConstructor
public class CloudController {
    private final CloudFacade cloudFacade;

    @GetMapping("${myapp.alias.api.list}")
    public List<FileListResponseDto> getAllFiles(@RequestHeader(AUTH_TOKEN) String authToken,
                                                 @RequestParam(LIMIT) Integer limit) {
        return cloudFacade.getFileList(authToken, limit);
    }

    @GetMapping("${myapp.alias.api.file}")
    public ResponseEntity<Resource> downloadFileFromCloud(@RequestHeader(AUTH_TOKEN) String authToken,
                                                          @RequestParam(FILENAME) String filename) {
        return ResponseEntity.ok().body(new ByteArrayResource(cloudFacade.downloadFile(authToken, filename)));
    }

    @PostMapping("${myapp.alias.api.file}")
    public ResponseEntity<ResponseMessageDto> uploadFile(@RequestHeader(AUTH_TOKEN) String authToken,
                                                         @RequestParam(FILENAME) String filename,
                                                         MultipartFile file) throws IOException {
        return ResponseEntity.ok(cloudFacade.uploadFile(authToken, filename, file));
    }

    @PutMapping("${myapp.alias.api.file}")
    public ResponseEntity<ResponseMessageDto> editFileName(@RequestHeader(AUTH_TOKEN) String authToken,
                                                           @RequestParam(FILENAME) String filename,
                                                           @RequestBody EditFileNameRequestDto editFileNameRequest) {
        return ResponseEntity.ok(cloudFacade.editFileName(authToken, filename, editFileNameRequest));
    }

    @DeleteMapping("${myapp.alias.api.file}")
    public ResponseEntity<Object> deleteFile(@RequestHeader(AUTH_TOKEN) String authToken,
                                             @RequestParam(FILENAME) String filename) {
        return ResponseEntity.ok(cloudFacade.deleteFile(authToken, filename));
    }
}
