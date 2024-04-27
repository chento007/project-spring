package co.istad.photostad.api.image;

import co.istad.photostad.api.image.web.ImageDto;
import co.istad.photostad.api.image.web.ModifyImageDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapStruct {

    Image createImageDtoToImage(ModifyImageDto model);

    ImageDto imageToImageDto(Image model);

    PageInfo<ImageDto> imagePageInfoToImagePageInfoDto(PageInfo<Image> model);

}
