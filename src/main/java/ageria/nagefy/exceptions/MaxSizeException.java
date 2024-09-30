package ageria.nagefy.exceptions;

import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class MaxSizeException extends MaxUploadSizeExceededException {
    public MaxSizeException(Long fileSize) {
        super(fileSize);
    }
}
