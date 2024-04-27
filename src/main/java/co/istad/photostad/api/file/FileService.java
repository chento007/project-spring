package co.istad.photostad.api.file;


import co.istad.photostad.api.file.web.FileDto;
import co.istad.photostad.api.file.web.FolderDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    /**
     * use to upload single file
     *
     * @param file request data from client
     * @return File Dto
     */
    FileDto uploadSingle(MultipartFile file);

    /**
     * use to upload multiple file
     *
     * @param files request data from client
     * @return List File Dto
     */
    List<FileDto> uploadMultiple(List<MultipartFile> files);

    /**
     * use to find all file in server
     *
     * @return list of files in server
     */
    List<FileDto> findAll();

    /**
     * use to find file in server by name
     *
     * @param fileName search file name
     * @return a file from sever
     */
    FileDto findByName(String fileName);

    /**
     * use to delete file by filename
     *
     * @param filename search filename then delete
     * @return a file name after delete
     */
    String deleteByName(String filename);

    /**
     * use to remove all file from a folder sever
     *
     * @return if success return true
     */

    boolean removeAllFiles();


    /**
     * use to upload image with file base4
     * @param image text file image bae63
     * @return fileDto is data that already prepare to response
     */
    FileDto uploadFileBase64(String image);

    /**
     * use to download image find by name
     * @param name name to download
     * @return Resource from server to download
     */
    Resource downloadImageFindByName(String name);

    /**
     * use to upload image then generate folder
     * @param imageList is list of image to generate
     * @return folderDto to generate folder name and size of folder
     */
    FolderDto uploadImageGenerateFolder(List<MultipartFile> imageList);

    FileDto uploadFont(MultipartFile font);
}
