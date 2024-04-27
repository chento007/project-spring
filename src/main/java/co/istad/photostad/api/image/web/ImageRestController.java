package co.istad.photostad.api.image.web;

import co.istad.photostad.api.image.ImageService;
import co.istad.photostad.base.BaseRest;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageRestController {

    private final ImageService imageService;

    @PostMapping
    BaseRest<?> insertImage(@RequestBody ModifyImageDto modifyImageDto) {

        ImageDto imageDto = imageService.insertImage(modifyImageDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Image has been insert success.")
                .timestamp(LocalDateTime.now())
                .data(imageDto)
                .build();

    }

    @GetMapping("/{id}")
    public BaseRest<?> selectById(@PathVariable("id") Integer id) {

        ImageDto imageDto = imageService.selectById(id);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Image has been found success.")
                .timestamp(LocalDateTime.now())
                .data(imageDto)
                .build();

    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteById(@PathVariable("id") Integer id) {

        Integer imageId = imageService.deleteById(id);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Image has been delete success.")
                .timestamp(LocalDateTime.now())
                .data(imageId)
                .build();

    }

    @PutMapping("/{id}")
    public BaseRest<?> updateById(@PathVariable("id") Integer id, @RequestBody ModifyImageDto modifyImageDto) {

        ImageDto imageDto = imageService.updateImage(id, modifyImageDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Image has been update success.")
                .timestamp(LocalDateTime.now())
                .data(imageDto)
                .build();

    }

    @GetMapping("")
    public BaseRest<?> selectALl(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                 @RequestParam(required = false, name = "limit", defaultValue = "20") int limit,
                                 @RequestParam(required = false, name = "type", defaultValue = "") String type) {

        PageInfo<ImageDto> imageDtoPageInfo = imageService.selectAllImage(page, limit, type);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Images have been found success.")
                .timestamp(LocalDateTime.now())
                .data(imageDtoPageInfo)
                .build();

    }

}
