package co.istad.photostad.api.image;

import co.istad.photostad.api.image.web.ImageDto;
import co.istad.photostad.api.image.web.ModifyImageDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImageService {

    /**
     * use to insert image in server and database
     * @param modifyImageDto field to update
     * @return imageDto data necessary to insert
     */
    ImageDto insertImage(ModifyImageDto modifyImageDto);

    /**
     * use to select image by id
     * @param id which id to be search
     * @return imageDto data necessary to
     */
    ImageDto selectById(Integer id);

    /**
     * use to delete image by id
     * @param id  which id search to delete
     * @return id after deleted
     */
    Integer deleteById(Integer id);

    /**
     * use to update image by id and field in modifyImage
     * @param id search id to update
     * @param modifyImageDto data need update update
     * @return field response to client
     */
    ImageDto updateImage(Integer id, ModifyImageDto modifyImageDto);


    /**
     * use to select all image from server and database with pagination
     * @param page location page to select
     * @param limit size of pagination to select
     * @param type what type should select if it doesn't have will search normal
     * @return pagination of data imageDto
     */
    PageInfo<ImageDto> selectAllImage(int page, int limit, String type);


}
