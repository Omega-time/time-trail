package paysafe.interns.helpers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import paysafe.interns.models.Doc;
import paysafe.interns.models.Project;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * This class contains helper methods for the {@link Doc} model.
 */
public final class DocUtilities {

    private DocUtilities(){
    }

    private static final int MAX_SIZE_OF_ALL_FILES_PER_PROJECT_IN_KB = 10240;

    public static Doc getDocFromProjectByName(String fileName, Project project) {
        Doc tempFile;
        Doc doc = null;
        Iterator<Doc> it = project.getFiles().iterator();
        while (it.hasNext()) {
            tempFile = it.next();
            if (fileName.equals(tempFile.getName())) {
                doc = tempFile;
                break;
            }
        }
        return doc;
    }

    public static boolean docExistsInProject(Doc file, Project project) {
        return file != null && project.getFiles().contains(file);
    }

    /**
     * Checks if the given input type is supported, currently:
     * jpeg, png, txt, docx, doc, pdf
     *
     * @param contentType the content type to be checked
     * @return true if the content type is supported, false otherwise
     */
    public static boolean docContentTypeIsAllowed(String contentType) {
        //TODO: add all desirable MimeTypes
        return contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE)
                || contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_PNG_VALUE)
                || contentType.equalsIgnoreCase(MimeTypeUtils.TEXT_PLAIN_VALUE)
                || contentType .equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || contentType.equalsIgnoreCase("application/msword")
                || contentType.equalsIgnoreCase("application/pdf");
    }

    public static long totalSizeInKbOfDocsInProject(Project project) {
        Set<Doc> files = project.getFiles();
        long size = 0;
        for (Doc doc : files) {
            size += doc.getFileSizeKb();
        }
        return size;
    }

    public static boolean multipartFileIsEligibleForUpload(MultipartFile multipartFile, Project project) {
        if (!docContentTypeIsAllowed(multipartFile.getContentType())) {
            return false;
        } else if (multipartFile.getSize() / 1024 + totalSizeInKbOfDocsInProject(project)
                > MAX_SIZE_OF_ALL_FILES_PER_PROJECT_IN_KB) {
            return false;
        }
        for (Doc doc : project.getFiles()) {
            if (doc.getName().equals(multipartFile.getOriginalFilename())) {
                return false;
            }
        }
        return true;
    }

    public static Doc createDocFromMultipartFile(MultipartFile multipartFile) {
        Doc doc = new Doc();
        doc.setName(multipartFile.getOriginalFilename());
        doc.setType(multipartFile.getContentType());
        try {
            doc.setFile(multipartFile.getBytes());
            doc.setFileSizeKb(multipartFile.getSize() / 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static JSONObject createJsonObjectFromDoc(Doc doc) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", doc.getName());
        jsonObject.put("size", doc.getFileSizeKb());
        jsonObject.put("type", doc.getType());
        return jsonObject;
    }
}
