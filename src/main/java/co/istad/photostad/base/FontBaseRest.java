package co.istad.photostad.base;

import lombok.Builder;

@Builder
public record FontBaseRest<T> (
        T fonts
){
}
